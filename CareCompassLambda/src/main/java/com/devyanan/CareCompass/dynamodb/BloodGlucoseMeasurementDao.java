package com.devyanan.CareCompass.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import com.devyanan.CareCompass.exceptions.BloodGlucoseMeasurementNotFoundException;
import com.devyanan.CareCompass.exceptions.CustomDynamoDBException;
import com.devyanan.CareCompass.exceptions.DatabaseAccessException;
import com.devyanan.CareCompass.metrics.MetricsConstants;
import com.devyanan.CareCompass.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.*;

/**
 * DAO class for managing BloodGlucoseMeasurement data in DynamoDB.
 */
@Singleton
public class BloodGlucoseMeasurementDao {
    private final DynamoDBMapper dynamoDBMapper;
    private final MetricsPublisher metricsPublisher;
    private final Logger log = LogManager.getLogger();
    @Inject
    public BloodGlucoseMeasurementDao(DynamoDBMapper dynamoDBMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Method to add a blood glucose measurement.
     *
     * @param bloodGlucoseMeasurement The blood glucose measurement to add.
     * @return The added blood glucose measurement.
     * @throws IllegalArgumentException  If the blood glucose measurement object is null.
     * @throws CustomDynamoDBException   If there is a DynamoDB-specific error while adding the blood glucose measurement.
     * @throws DatabaseAccessException  If there is an error accessing the database.
     */
    public BloodGlucoseMeasurement saveBloodGlucoseMeasurement(BloodGlucoseMeasurement bloodGlucoseMeasurement){
        if(bloodGlucoseMeasurement == null){
            metricsPublisher.addCount(MetricsConstants.ADD_BLOOD_GLUCOSE_MEASUREMENT_NULL_OR_EMPTY_COUNT,1);
            log.info("Attempted to add a bloodGlucoseMeasurement with null.");
            throw new IllegalArgumentException("bloodGlucoseMeasurement object can not be null.");
        }
        log.info("add bloodGlucoseMeasurement for patientId with id: {}",bloodGlucoseMeasurement.getPatientId());
        metricsPublisher.addCount(MetricsConstants.ADD_BLOOD_GLUCOSE_MEASUREMENT_TOTAL_COUNT,1);
        try {
            log.info("Attempting to add bloodGlucoseMeasurement : {}", bloodGlucoseMeasurement);
            dynamoDBMapper.save(bloodGlucoseMeasurement);
            metricsPublisher.addCount(MetricsConstants.ADD_BLOOD_GLUCOSE_MEASUREMENT_SUCCESS_COUNT,1);
            log.info("BloodGlucoseMeasurement added successfully for user: {}", bloodGlucoseMeasurement.getPatientId());
        } catch (AmazonDynamoDBException e) {
            log.error("DynamoDB-specific error occurred while adding bloodGlucoseMeasurement: {}", bloodGlucoseMeasurement, e);
            throw new CustomDynamoDBException("Failed to add notification to the database due to DynamoDB-specific error", e);
        }catch (Exception e) {
            log.error("Failed to add bloodGlucoseMeasurement for user: {}", bloodGlucoseMeasurement.getPatientId(), e);
            throw new DatabaseAccessException("Failed to add notification to the database", e);
        }

        return bloodGlucoseMeasurement;
    }

    /**
     * Method to delete a single blood glucose measurement.
     *
     * @param patientId       The ID of the patient.
     * @param actualCheckTime The actual check time of the blood glucose measurement.
     * @return The deleted blood glucose measurement.
     * @throws BloodGlucoseMeasurementNotFoundException If the blood glucose measurement is not found.
     */
    public BloodGlucoseMeasurement deleteSingleBloodGlucoseMeasurementByActualCheckTime(String patientId, LocalDateTime actualCheckTime){//TODO   ？？？？？？应该是LocalDateTime

            metricsPublisher.addCount(MetricsConstants.DELETE_SINGLE_VITAL_SIGNS_TOTAL_COUNT, 1);

            BloodGlucoseMeasurement bloodGlucoseMeasurementToDelete = new BloodGlucoseMeasurement();
            bloodGlucoseMeasurementToDelete.setPatientId(patientId);
            bloodGlucoseMeasurementToDelete.setActualCheckTime(actualCheckTime);

                dynamoDBMapper.delete(bloodGlucoseMeasurementToDelete);
        return bloodGlucoseMeasurementToDelete;
    }


    /**
     * DAO method to retrieve all blood glucose measurements data for a patient.
     *
     * @param patientId The ID of the patient.
     * @return A list of all blood glucose measurements data for the specified patient.
     * @throws DatabaseAccessException If there is an error accessing the database.
     */
    public List<BloodGlucoseMeasurement> getAllBloodGlucoseMeasurements(String patientId) {
        try {
            log.info("Get blood glucose measurements for patientId with id: {}",patientId);
            metricsPublisher.addCount(MetricsConstants.GET_ALL_BLOOD_GLUCOSE_MEASUREMENT_TOTAL_COUNT,1);
            log.info("Attempting to get all blood glucose measurements for user: {}", patientId);
            BloodGlucoseMeasurement bloodGlucoseMeasurement = new BloodGlucoseMeasurement();
            bloodGlucoseMeasurement.setPatientId(patientId);

            DynamoDBQueryExpression<BloodGlucoseMeasurement> queryExpression = new DynamoDBQueryExpression<BloodGlucoseMeasurement>()
                    .withHashKeyValues(bloodGlucoseMeasurement);
            QueryResultPage<BloodGlucoseMeasurement> results = dynamoDBMapper
                    .queryPage(BloodGlucoseMeasurement.class, queryExpression);

            if (results.getResults().isEmpty()) {
                metricsPublisher.addCount(MetricsConstants.GET_ALL_BLOOD_GLUCOSE_MEASUREMENT_NULL_OR_EMPTY_COUNT, 1);
                log.warn("No blood glucose measurements found for user: {}", patientId);
                return Collections.emptyList();
            }
            metricsPublisher.addCount(MetricsConstants.GET_ALL_BLOOD_GLUCOSE_MEASUREMENT_FOUND_COUNT, 1);
            return results.getResults();
        } catch (Exception e) {
            log.error("Failed to access the database for user: {}", patientId, e);
            throw new DatabaseAccessException("Failed to access the database", e);
        }
    }

//    /**
//     * Retrieves the blood glucose masurement recorded for the last three days for a specified patient.
//     *
//     * @param patientId The ID of the patient.
//     * @return A list of blood glucose masurement recorded for the last three days.
//     */
//    public List<BloodGlucoseMeasurement> getBloodGlucoseMeasurementForLastThreeDays(String patientId) {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime threeDaysAgo = now.minusDays(3);
//
//        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
//        expressionAttributeValues.put(":patientId", new AttributeValue().withS(patientId));
//        expressionAttributeValues.put(":threeDaysAgo", new AttributeValue().withS(threeDaysAgo.toString()));
//
//        DynamoDBQueryExpression<BloodGlucoseMeasurement> queryExpression = new DynamoDBQueryExpression<BloodGlucoseMeasurement>()
//                .withKeyConditionExpression("patientId = :patientId AND actualCheckTime > :threeDaysAgo")
//                .withExpressionAttributeValues(expressionAttributeValues);
//
//        PaginatedQueryList<BloodGlucoseMeasurement> queryResult = dynamoDBMapper.query(BloodGlucoseMeasurement.class, queryExpression);
//
//        List<BloodGlucoseMeasurement> BloodGlucoseMeasurementForLastThreeDays = new ArrayList<>(queryResult);
//
//        return BloodGlucoseMeasurementForLastThreeDays;
//    }


    //    /**
//     * DAO method to retrieve blood glucose measurements data for a specified date range.
//     *
//     * @param patientId The ID of the patient.
//     * @param startDate The start date of the date range.
//     * @param endDate   The end date of the date range.
//     * @return A list of blood glucose measurements data for the specified patient within the date range.
//     * @throws IllegalArgumentException           If any of the parameters are null.
//     * @throws BloodGlucoseMeasurementNotFoundException If no blood glucose measurements data is found for the specified date range.
//     */
//    public List<BloodGlucoseMeasurement> getBloodGlucoseMeasurementsForDateRange(String patientId, LocalDate startDate, LocalDate endDate){
//        if (patientId == null) {
//            throw new IllegalArgumentException("Patient ID cannot be null");
//        }
//        if (startDate == null || endDate == null) {
//            throw new IllegalArgumentException("Start date and end date cannot be null");
//        }
//            log.info("Get blood glucose measurements for patientId: {} between dates: {} and {}", patientId, startDate, endDate);
//            metricsPublisher.addCount(MetricsConstants.GET_BLOOD_GLUCOSE_FOR_DATE_RANGE_TOTAL_COUNT,1);
//        Map<String, AttributeValue> valueMap = new HashMap<>();
//        valueMap.put(":patientId", new AttributeValue().withS(patientId));
//        valueMap.put(":startDate", new AttributeValue().withS(startDate.toString()));
//        valueMap.put(":endDate", new AttributeValue().withS(endDate.toString()));
//
//        DynamoDBQueryExpression<BloodGlucoseMeasurement> queryExpression = new DynamoDBQueryExpression<BloodGlucoseMeasurement>()
//                .withIndexName("bloodGlucoseMeasurementsIndex")
//                .withConsistentRead(false)
//                .withKeyConditionExpression("patientId = :patientId")
//                .withFilterExpression("#actualCheckTime BETWEEN :startDate AND :endDate")
//                .withExpressionAttributeNames(Collections.singletonMap("#actualCheckTime", "actualCheckTime"))
//                .withExpressionAttributeValues(valueMap);
//
//        PaginatedQueryList<BloodGlucoseMeasurement> measurements =
//                dynamoDBMapper.query(BloodGlucoseMeasurement.class, queryExpression);
//
//        if (!measurements.isEmpty()) {
//            metricsPublisher.addCount(MetricsConstants.GET_BLOOD_GLUCOSE_FOR_DATE_RANGE_FOUND_COUNT, 1);
//            return measurements;
//        } else {
//            metricsPublisher.addCount(MetricsConstants.GET_BLOOD_GLUCOSE_FOR_DATE_RANGE_NOT_FOUND_COUNT, 1);
//            throw new BloodGlucoseMeasurementNotFoundException("No measurements found for the specified date range");
//        }
//    }

    //    /**
//     * Method to delete a blood glucose measurement.
//     *
//     * @param bloodGlucoseMeasurement The blood glucose measurement to delete.
//     * @return The deleted blood glucose measurement.
//     * @throws CustomDynamoDBException If there is a DynamoDB-specific error while deleting the blood glucose measurement.
//     * @throws DatabaseAccessException If there is an error accessing the database.
//     */
//    public BloodGlucoseMeasurement deleteBloodGlucoseMeasurement(BloodGlucoseMeasurement bloodGlucoseMeasurement){
//        if (bloodGlucoseMeasurement == null) {
//            log.warn("Attempted to delete a null bloodGlucoseMeasurement object.");
//            metricsPublisher.addCount(MetricsConstants.DELETE_BLOOD_GLUCOSE_MEASUREMENT_NULL_OR_EMPTY_COUNT,1);
//            return bloodGlucoseMeasurement;
//        }
//        log.info("Delete medication for patientId with id: {}",bloodGlucoseMeasurement.getPatientId());
//        metricsPublisher.addCount(MetricsConstants.DELETE_BLOOD_GLUCOSE_MEASUREMENT_TOTAL_COUNT,1);
//        try {
//            log.info("Attempting to delete bloodGlucoseMeasurement: {}", bloodGlucoseMeasurement);
//            this.dynamoDBMapper.delete(bloodGlucoseMeasurement);
//            metricsPublisher.addCount(MetricsConstants.DELETE_BLOOD_GLUCOSE_MEASUREMENT_SUCCESS_COUNT,1);
//            log.info("BloodGlucoseMeasurement deleted successfully: {}", bloodGlucoseMeasurement);
//            return bloodGlucoseMeasurement;
//        }catch (AmazonDynamoDBException e) {
//            log.error("DynamoDB-specific error occurred while deleting bloodGlucoseMeasurement: {}", bloodGlucoseMeasurement, e);
//            throw new CustomDynamoDBException("Failed to delete bloodGlucoseMeasurement from the database due to DynamoDB-specific error", e);
//        } catch (Exception e) {
//            log.error("Failed to delete bloodGlucoseMeasurement: {}", bloodGlucoseMeasurement, e);
//            throw new DatabaseAccessException("Failed to delete bloodGlucoseMeasurement from the database", e);
//        }
//    }

}
