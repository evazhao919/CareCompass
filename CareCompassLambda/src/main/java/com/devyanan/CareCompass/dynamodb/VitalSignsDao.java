package com.devyanan.CareCompass.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import com.devyanan.CareCompass.dynamodb.models.VitalSigns;
import com.devyanan.CareCompass.exceptions.CustomDynamoDBException;
import com.devyanan.CareCompass.exceptions.DatabaseAccessException;
import com.devyanan.CareCompass.exceptions.VitalSignsNotFoundException;
import com.devyanan.CareCompass.metrics.MetricsConstants;
import com.devyanan.CareCompass.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VitalSignsDao {
private final DynamoDBMapper dynamoDBMapper;
    private final MetricsPublisher metricsPublisher;
    private final Logger log = LogManager.getLogger();

    public VitalSignsDao(DynamoDBMapper dynamoDBMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.metricsPublisher = metricsPublisher;
    }

    public VitalSigns addVitalSigns(VitalSigns vitalSigns){
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

    public VitalSigns deleteVitalSigns(VitalSigns vitalSigns){
        if (vitalSigns == null) {
            log.warn("Attempted to delete a null vitalSigns object.");
            metricsPublisher.addCount(MetricsConstants.DELETE_VITAL_SIGNS_NULL_OR_EMPTY_COUNT,1);
            return vitalSigns;
        }
        log.info("Delete vitalSigns for patientId with id: {}",vitalSigns.getPatientId());
        metricsPublisher.addCount(MetricsConstants.DELETE_VITAL_SIGNS_TOTAL_COUNT,1);
        try {
            log.info("Attempting to delete vitalSigns: {}", vitalSigns);
            this.dynamoDBMapper.delete(vitalSigns);
            metricsPublisher.addCount(MetricsConstants.DELETE_VITAL_SIGNS_SUCCESS_COUNT,1);
            log.info("VitalSigns deleted successfully: {}", vitalSigns);
            return vitalSigns;
        }catch (AmazonDynamoDBException e) {
            log.error("DynamoDB-specific error occurred while deleting vitalSigns: {}", vitalSigns, e);
            throw new CustomDynamoDBException("Failed to delete vitalSigns from the database due to DynamoDB-specific error", e);
        } catch (Exception e) {
            log.error("Failed to delete vitalSigns: {}", vitalSigns, e);
            throw new DatabaseAccessException("Failed to delete vitalSigns from the database", e);
        }
    }
    public VitalSigns deleteSingleVitalSigns(String patientId, String actualCheckTime) {
        metricsPublisher.addCount(MetricsConstants.DELETE_SINGLE_VITAL_SIGNS_TOTAL_COUNT, 1);
        VitalSigns result = this.dynamoDBMapper.load(VitalSigns.class, patientId, actualCheckTime);
        if (result == null) {
            log.warn("Attempted to get a null VitalSigns object with patientId {}.", patientId);
            metricsPublisher.addCount(MetricsConstants.DELETE_SINGLE_VITAL_SIGNS_NULL_OR_EMPTY_COUNT, 1);
            throw new VitalSignsNotFoundException("VitalSigns actualCheckTime can not be null.");
        } else {
            metricsPublisher.addCount(MetricsConstants.DELETE_SINGLE_VITAL_SIGNS_SUCCESS_COUNT, 1);
            return result;
        }
    }
        public List<VitalSigns> getAllVitalSigns(String patientId) {
            try {
                log.info("Get vitalSigns for patientId with id: {}", patientId);
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
                    log.warn("No vitalSigns found for user: {}", patientId);
                    return Collections.emptyList();
                }
                metricsPublisher.addCount(MetricsConstants.GET_ALL_VITAL_SIGNS_FOUND_COUNT, 1);
                return results.getResults();
            } catch (Exception e) {
                log.error("Failed to access the database for user: {}", patientId, e);
                throw new DatabaseAccessException("Failed to access the database", e);
            }
        }

    public List<VitalSigns> getVitalSignsForDateRange(String patientId, LocalDate startDate, LocalDate endDate){
        if (patientId == null) {
            throw new IllegalArgumentException("Patient ID cannot be null");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        log.info("Get vitalSigns for patientId: {} between dates: {} and {}", patientId, startDate, endDate);
        metricsPublisher.addCount(MetricsConstants.GET_VITAL_SIGNS_FOR_DATE_RANGE_TOTAL_COUNT,1);
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":patientId", new AttributeValue().withS(patientId));
        valueMap.put(":startDate", new AttributeValue().withS(startDate.toString()));
        valueMap.put(":endDate", new AttributeValue().withS(endDate.toString()));

        DynamoDBQueryExpression<VitalSigns> queryExpression = new DynamoDBQueryExpression<VitalSigns>()
                .withIndexName("vitalSignsIndex")
                .withConsistentRead(false)
                .withKeyConditionExpression("patientId = :patientId")
                .withFilterExpression("#actualCheckTime BETWEEN :startDate AND :endDate")
                .withExpressionAttributeNames(Collections.singletonMap("#actualCheckTime", "actualCheckTime"))
                .withExpressionAttributeValues(valueMap);

        PaginatedQueryList<VitalSigns> measurements =
                dynamoDBMapper.query(VitalSigns.class, queryExpression);

        if (!measurements.isEmpty()) {
            metricsPublisher.addCount(MetricsConstants.GET_VITAL_SIGNS_FOR_DATE_RANGE_FOUND_COUNT, 1);
            return measurements;
        } else {
            metricsPublisher.addCount(MetricsConstants.GET_VITAL_SIGNS_FOR_DATE_RANGE_NOT_FOUND_COUNT, 1);
            throw new VitalSignsNotFoundException("No measurements found for the specified date range");
        }
    }
    }
