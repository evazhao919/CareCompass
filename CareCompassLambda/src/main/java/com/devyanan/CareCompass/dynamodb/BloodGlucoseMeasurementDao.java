package com.devyanan.CareCompass.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import com.devyanan.CareCompass.exceptions.CustomDynamoDBException;
import com.devyanan.CareCompass.exceptions.DatabaseAccessException;
import com.devyanan.CareCompass.metrics.MetricsConstants;
import com.devyanan.CareCompass.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;

public class BloodGlucoseMeasurementDao {
//    String patientId;
//    LocalTime actualCheckTime;
//    double glucoseLevel;
//    BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext;
//    String comments;
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
        try {
            log.info("Attempting to add bloodGlucoseMeasurement : {}", bloodGlucoseMeasurement);
            dynamoDBMapper.save(bloodGlucoseMeasurement);
            metricsPublisher.addCount(MetricsConstants.ADD_BLOOD_GLUCOSE_MEASUREMENT_SUCCESS_COUNT,1);
            //log.info("BloodGlucoseMeasurement added successfully for user: {}", bloodGlucoseMeasurement.getPatientId());
        } catch (AmazonDynamoDBException e) {
            //log.error("DynamoDB-specific error occurred while adding bloodGlucoseMeasurement: {}", bloodGlucoseMeasurement, e);
            throw new CustomDynamoDBException("Failed to add notification to the database due to DynamoDB-specific error", e);
        }catch (Exception e) {
            //log.error("Failed to add bloodGlucoseMeasurement for user: {}", bloodGlucoseMeasurement.getPatientId(), e);
            throw new DatabaseAccessException("Failed to add notification to the database", e);
        }

        return bloodGlucoseMeasurement;
    }

    public BloodGlucoseMeasurement deleteBloodGlucoseMeasurement(BloodGlucoseMeasurement bloodGlucoseMeasurement){
        if (bloodGlucoseMeasurement == null) {
            log.warn("Attempted to delete a null or empty bloodGlucoseMeasurement object.");
            metricsPublisher.addCount(MetricsConstants.DELETE_MEDICATION_NULL_OR_EMPTY_COUNT,1);
            return bloodGlucoseMeasurement;
        }

        try {
            log.info("Attempting to delete bloodGlucoseMeasurement: {}", bloodGlucoseMeasurement;
            this.dynamoDBMapper.delete(bloodGlucoseMeasurement);
            metricsPublisher.addCount(MetricsConstants.DELETE_MEDICATION_SUCCESS_COUNT,1);
            //log.info("BloodGlucoseMeasurement deleted successfully: {}", bloodGlucoseMeasurement);
            return bloodGlucoseMeasurement;
        }catch (AmazonDynamoDBException e) {
            //log.error("DynamoDB-specific error occurred while deleting bloodGlucoseMeasurement: {}", bloodGlucoseMeasurement, e);
            throw new CustomDynamoDBException("Failed to delete bloodGlucoseMeasurement from the database due to DynamoDB-specific error", e);
        } catch (Exception e) {
            //log.error("Failed to delete bloodGlucoseMeasurement: {}", bloodGlucoseMeasurement, e);
            throw new DatabaseAccessException("Failed to delete bloodGlucoseMeasurement from the database", e);
        }
    }


}
