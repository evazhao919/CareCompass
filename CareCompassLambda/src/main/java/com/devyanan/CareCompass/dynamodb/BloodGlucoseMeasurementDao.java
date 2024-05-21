package com.devyanan.CareCompass.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import com.devyanan.CareCompass.exceptions.BloodGlucoseMeasurementNotFoundException;
import com.devyanan.CareCompass.exceptions.CustomDynamoDBException;
import com.devyanan.CareCompass.exceptions.DatabaseAccessException;
import com.devyanan.CareCompass.metrics.MetricsConstants;
import com.devyanan.CareCompass.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BloodGlucoseMeasurementDao {
    private final DynamoDBMapper dynamoDBMapper;
    private final MetricsPublisher metricsPublisher;
    private final Logger log = LogManager.getLogger();

    public BloodGlucoseMeasurementDao(DynamoDBMapper dynamoDBMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.metricsPublisher = metricsPublisher;
    }
    public BloodGlucoseMeasurement addBloodGlucoseMeasurement(BloodGlucoseMeasurement bloodGlucoseMeasurement){
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

    public BloodGlucoseMeasurement deleteBloodGlucoseMeasurement(BloodGlucoseMeasurement bloodGlucoseMeasurement){
        if (bloodGlucoseMeasurement == null) {
            log.warn("Attempted to delete a null bloodGlucoseMeasurement object.");
            metricsPublisher.addCount(MetricsConstants.DELETE_BLOOD_GLUCOSE_MEASUREMENT_NULL_OR_EMPTY_COUNT,1);
            return bloodGlucoseMeasurement;
        }
        log.info("Delete medication for patientId with id: {}",bloodGlucoseMeasurement.getPatientId());
        metricsPublisher.addCount(MetricsConstants.DELETE_BLOOD_GLUCOSE_MEASUREMENT_TOTAL_COUNT,1);
        try {
            log.info("Attempting to delete bloodGlucoseMeasurement: {}", bloodGlucoseMeasurement);
            this.dynamoDBMapper.delete(bloodGlucoseMeasurement);
            metricsPublisher.addCount(MetricsConstants.DELETE_BLOOD_GLUCOSE_MEASUREMENT_SUCCESS_COUNT,1);
            log.info("BloodGlucoseMeasurement deleted successfully: {}", bloodGlucoseMeasurement);
            return bloodGlucoseMeasurement;
        }catch (AmazonDynamoDBException e) {
            log.error("DynamoDB-specific error occurred while deleting bloodGlucoseMeasurement: {}", bloodGlucoseMeasurement, e);
            throw new CustomDynamoDBException("Failed to delete bloodGlucoseMeasurement from the database due to DynamoDB-specific error", e);
        } catch (Exception e) {
            log.error("Failed to delete bloodGlucoseMeasurement: {}", bloodGlucoseMeasurement, e);
            throw new DatabaseAccessException("Failed to delete bloodGlucoseMeasurement from the database", e);
        }
    }
    public BloodGlucoseMeasurement deleteSingleBloodGlucoseMeasurement(String patientId, String actualCheckTime){
        metricsPublisher.addCount(MetricsConstants.DELETE_SINGLE_BLOOD_GLUCOSE_MEASUREMENT_TOTAL_COUNT,1);
        BloodGlucoseMeasurement result = this.dynamoDBMapper.load(BloodGlucoseMeasurement.class,patientId,actualCheckTime);
        if(result == null ){
            log.warn("Attempted to get a null bloodGlucoseMeasurement object with patientId {}.", patientId);
            metricsPublisher.addCount(MetricsConstants.DELETE_SINGLE_BLOOD_GLUCOSE_MEASUREMENT_NULL_OR_EMPTY_COUNT,1);
            throw new BloodGlucoseMeasurementNotFoundException("BloodGlucoseMeasurement actualCheckTime can not be null.");
        } else {
            metricsPublisher.addCount(MetricsConstants.DELETE_SINGLE_BLOOD_GLUCOSE_MEASUREMENT_SUCCESS_COUNT,1);
            return result;
        }


    }
    public List<BloodGlucoseMeasurement> getBloodGlucoseMeasurementsForDateRange(String patientId, LocalDate startDate, LocalDate endDate){
        if (patientId == null) {
            throw new IllegalArgumentException("Patient ID cannot be null");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
            log.info("Get blood glucose measurements for patientId: {} between dates: {} and {}", patientId, startDate, endDate);
            metricsPublisher.addCount(MetricsConstants.GET_BLOOD_GLUCOSE_FOR_DATE_RANGE_TOTAL_COUNT,1);
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":patientId", new AttributeValue().withS(patientId));
        valueMap.put(":startDate", new AttributeValue().withS(startDate.toString()));
        valueMap.put(":endDate", new AttributeValue().withS(endDate.toString()));

        DynamoDBQueryExpression<BloodGlucoseMeasurement> queryExpression = new DynamoDBQueryExpression<BloodGlucoseMeasurement>()
                .withIndexName("bloodGlucoseMeasurementsIndex")
                .withConsistentRead(false)
                .withKeyConditionExpression("patientId = :patientId")
                .withFilterExpression("#actualCheckTime BETWEEN :startDate AND :endDate")
                .withExpressionAttributeNames(Collections.singletonMap("#actualCheckTime", "actualCheckTime"))
                .withExpressionAttributeValues(valueMap);

        PaginatedQueryList<BloodGlucoseMeasurement> measurements =
                dynamoDBMapper.query(BloodGlucoseMeasurement.class, queryExpression);

        if (!measurements.isEmpty()) {
            metricsPublisher.addCount(MetricsConstants.GET_BLOOD_GLUCOSE_FOR_DATE_RANGE_FOUND_COUNT, 1);
            return measurements;
        } else {
            metricsPublisher.addCount(MetricsConstants.GET_BLOOD_GLUCOSE_FOR_DATE_RANGE_NOT_FOUND_COUNT, 1);
            throw new BloodGlucoseMeasurementNotFoundException("No measurements found for the specified date range");
        }
        }

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
}
