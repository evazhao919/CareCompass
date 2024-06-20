package com.devyanan.CareCompass.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.devyanan.CareCompass.dynamodb.models.VitalSigns;
import com.devyanan.CareCompass.exceptions.CustomDynamoDBException;
import com.devyanan.CareCompass.exceptions.DatabaseAccessException;
import com.devyanan.CareCompass.exceptions.VitalSignsNotFoundException;
import com.devyanan.CareCompass.metrics.MetricsConstants;
import com.devyanan.CareCompass.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.*;

/**
 * DAO class for managing VitalSigns data in DynamoDB.
 */
@Singleton
public class VitalSignsDao {
    private final DynamoDBMapper dynamoDBMapper;
    private final MetricsPublisher metricsPublisher;
    private final Logger log = LogManager.getLogger();
    @Inject
    public VitalSignsDao(DynamoDBMapper dynamoDBMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Method to add vital signs data.
     *
     * @param vitalSigns The vital signs data to add.
     * @return The added vital signs data.
     * @throws IllegalArgumentException If the vital signs object is null or empty.
     * @throws CustomDynamoDBException If there is a DynamoDB-specific error while adding the vital signs data.
     * @throws DatabaseAccessException If there is an error accessing the database.
     */
    public VitalSigns saveVitalSigns(VitalSigns vitalSigns){
        if(vitalSigns == null){
            metricsPublisher.addCount(MetricsConstants.ADD_VITAL_SIGNS_NULL_OR_EMPTY_COUNT,1);
            log.info("Attempted to add a null vitalSigns.");
            throw new IllegalArgumentException("VitalSigns object or name cannot be null or empty.");
        }
        log.info("add vitalSigns for patientId with id: {}",vitalSigns.getPatientId());
        metricsPublisher.addCount(MetricsConstants.ADD_VITAL_SIGNS_TOTAL_COUNT,1);
        try {
            log.info("Attempting to add a vitalSigns: {}", vitalSigns);
            dynamoDBMapper.save(vitalSigns);
            metricsPublisher.addCount(MetricsConstants.ADD_VITAL_SIGNS_SUCCESS_COUNT,1);
            log.info("VitalSigns added successfully for user: {}", vitalSigns.getPatientId());
        } catch (AmazonDynamoDBException e) {
            log.error("DynamoDB-specific error occurred while adding vitalSigns: {}", vitalSigns, e);
            throw new CustomDynamoDBException("Failed to add vitalSigns to the database due to DynamoDB-specific error", e);
        }catch (Exception e) {
            log.error("Failed to add vitalSigns for user: {}", vitalSigns.getPatientId(), e);
            throw new DatabaseAccessException("Failed to add vitalSigns to the database", e);
        }

        return vitalSigns;
    }

    /**
     * DAO method to delete a single vital signs data.
     *
     * @param patientId       The ID of the patient.
     * @param actualCheckTime The actual check time of the vital signs data.
     * @return The deleted vital signs data.
     * @throws VitalSignsNotFoundException If the vital signs data is not found.
     */
    public VitalSigns deleteSingleVitalSignsByActualCheckTime(String patientId, LocalDateTime actualCheckTime) { //TODO   ？？？？？？应该是LocalDateTime,Dao 里面不应该使用时间
        log.info("Attempting to delete vital signs with patientId: {} and actualCheckTime: {}", patientId, actualCheckTime);
        metricsPublisher.addCount(MetricsConstants.DELETE_SINGLE_VITAL_SIGNS_TOTAL_COUNT, 1);

        VitalSigns vitalSignsToDelete = new VitalSigns();
        vitalSignsToDelete.setPatientId(patientId);
        vitalSignsToDelete.setActualCheckTime(actualCheckTime);

        VitalSigns existingVitalSigns = dynamoDBMapper.load(VitalSigns.class, patientId, actualCheckTime);

        if (existingVitalSigns != null) {
            dynamoDBMapper.delete(vitalSignsToDelete);
            log.info("Vital signs deleted successfully for patientId: {} and actualCheckTime: {}", patientId, actualCheckTime);
            return existingVitalSigns;
        } else {
            log.warn("Vital signs not found for patientId: {} and actualCheckTime: {}", patientId, actualCheckTime);
            throw new VitalSignsNotFoundException("Vital signs not found for patientId: " + patientId + " and actualCheckTime: " + actualCheckTime);
        }
    }

    /**
     * DAO method to retrieve all vital signs data for a patient.
     *
     * @param patientId The ID of the patient.
     * @return A list of vital signs data for the specified patient.
     * @throws DatabaseAccessException If there is an error accessing the database.
     */
    public List<VitalSigns> getAllVitalSigns(String patientId) {
        try {
            log.info("Attempting to get all vital signs for user: {}", patientId);
            metricsPublisher.addCount(MetricsConstants.GET_ALL_VITAL_SIGNS_TOTAL_COUNT, 1);
            log.info("Attempting to get all vitalSigns for user: {}", patientId);
            VitalSigns vitalSigns = new VitalSigns();
            vitalSigns.setPatientId(patientId);

            DynamoDBQueryExpression<VitalSigns> queryExpression = new DynamoDBQueryExpression<VitalSigns>()
                    .withHashKeyValues(vitalSigns);
            QueryResultPage<VitalSigns> results = dynamoDBMapper
                    .queryPage(VitalSigns.class, queryExpression);

            if (results.getResults().isEmpty()) {
                metricsPublisher.addCount(MetricsConstants.GET_ALL_VITAL_SIGNS_NULL_OR_EMPTY_COUNT, 1);
                log.warn("No vital signs found for user: {}", patientId);
                return Collections.emptyList();
            }
            metricsPublisher.addCount(MetricsConstants.GET_ALL_VITAL_SIGNS_FOUND_COUNT, 1);
            return results.getResults();
        } catch (Exception e) {
            log.error("Failed to access the database for user: {}", patientId, e);
            throw new DatabaseAccessException("Failed to access the database", e);
        }
    }

    public VitalSigns getVitalSigns(String patientId, LocalDateTime actualCheckTime) {
        try {
            log.info("Attempting to get a single vitalSigns with actual check time: {}", actualCheckTime);

            VitalSigns vitalSigns = new VitalSigns();
            vitalSigns.setPatientId(patientId);
            vitalSigns.setActualCheckTime(actualCheckTime);

            VitalSigns singleVitalSigns = this.dynamoDBMapper.load(vitalSigns);

            if (singleVitalSigns == null) {
                metricsPublisher.addCount(MetricsConstants.GET_SINGLE_VITALSIGNS_BY_PATIENT_ID_AND_ACTUAL_CHECK_TIME_NULL_OR_EMPTY_COUNT, 1);
                log.warn("No VitalSigns found for user: {} and actualCheckTime: {}", patientId, actualCheckTime);
                throw new VitalSignsNotFoundException("No VitalSigns found for user: " + patientId + " and actualCheckTime: " + actualCheckTime);
            } else {
                metricsPublisher.addCount(MetricsConstants.GET_SINGLE_VITALSIGNS_BY_PATIENT_ID_AND_ACTUAL_CHECK_TIME_FOUND_COUNT, 1);
                log.info("Successfully retrieved a single VitalSigns for actual check time: {}", actualCheckTime);
                return singleVitalSigns;
            }
        } catch (DatabaseAccessException e) {
            log.error("Failed to access the database for user: {} and actualCheckTime: {}", patientId, actualCheckTime, e);
            throw new DatabaseAccessException("Failed to access the database", e);
        }
    }


//    /**
//     * DAO method to retrieve vital signs data for a specified date range.
//     *
//     * @param patientId The ID of the patient.
//     * @param startDate The start date of the date range.
//     * @param endDate   The end date of the date range.
//     * @return A list of vital signs data for the specified patient within the date range.
//     * @throws IllegalArgumentException      If any of the parameters are null.
//     * @throws VitalSignsNotFoundException If no vital signs data is found for the specified date range.
//     */
//    public List<VitalSigns> getVitalSignsForDateRange(String patientId, LocalDate startDate, LocalDate endDate){ ////TODO   ？？？？？？应该是LocalDateTime
//        if (patientId == null) {
//            throw new IllegalArgumentException("Patient ID cannot be null");
//        }
//        if (startDate == null || endDate == null) {
//            throw new IllegalArgumentException("Start date and end date cannot be null");
//        }
//        log.info("Get vitalSigns for patientId: {} between dates: {} and {}", patientId, startDate, endDate);
//        metricsPublisher.addCount(MetricsConstants.GET_VITAL_SIGNS_FOR_DATE_RANGE_TOTAL_COUNT,1);
//
//        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
//        expressionAttributeValues.put(":startDate", new AttributeValue().withS(startDate.toString()));
//        expressionAttributeValues.put(":endDate", new AttributeValue().withS(endDate.toString()));
//
//        String rangeKeyConditionExpression = "#actualCheckTime BETWEEN :startDate AND :endDate";
//        Map<String, String> expressionAttributeNames = Collections.singletonMap("#actualCheckTime", "actualCheckTime");
//
//        DynamoDBQueryExpression<VitalSigns> queryExpression = new DynamoDBQueryExpression<VitalSigns>()
//                .withIndexName("vitalSignsDateIndex")
//                .withConsistentRead(false)
//                .withKeyConditionExpression("patientId = :patientId and " + rangeKeyConditionExpression)
//                .withExpressionAttributeNames(expressionAttributeNames)
//                .withExpressionAttributeValues(expressionAttributeValues);
//
//        List<VitalSigns> vitalSignsList = dynamoDBMapper.query(VitalSigns.class, queryExpression);
//
//        if (!vitalSignsList.isEmpty()) {
//            metricsPublisher.addCount(MetricsConstants.GET_VITAL_SIGNS_FOR_DATE_RANGE_FOUND_COUNT, 1);
//            return vitalSignsList;
//        } else {
//            metricsPublisher.addCount(MetricsConstants.GET_VITAL_SIGNS_FOR_DATE_RANGE_NOT_FOUND_COUNT, 1);
//            throw new VitalSignsNotFoundException("No measurements found for the specified date range");
//        }
//    }
}