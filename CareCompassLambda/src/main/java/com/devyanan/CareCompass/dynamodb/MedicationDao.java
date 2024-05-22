package com.devyanan.CareCompass.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import com.devyanan.CareCompass.exceptions.*;
import com.devyanan.CareCompass.metrics.MetricsConstants;
import com.devyanan.CareCompass.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class MedicationDao {
    private final DynamoDBMapper dynamoDBMapper;
    private final MetricsPublisher metricsPublisher;
    private final Logger log = LogManager.getLogger();

    public MedicationDao(DynamoDBMapper dynamoDBMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.metricsPublisher = metricsPublisher;
    }

    public Medication addMedication(Medication medication){

        if(medication == null || medication.getMedicationName() == null || medication.getMedicationName().isEmpty()){
            metricsPublisher.addCount(MetricsConstants.ADD_MEDICATION_NULL_OR_EMPTY_COUNT,1);
            log.info("Attempted to add a medication with null or empty name.");
            throw new IllegalArgumentException("Medication object or name cannot be null or empty.");
        }
        log.info("add medication for patientId with id: {}",medication.getPatientId());
        metricsPublisher.addCount(MetricsConstants.ADD_MEDICATION_TOTAL_COUNT,1);
        try {
            log.info("Attempting to add a medication: {}", medication);
            dynamoDBMapper.save(medication);
            metricsPublisher.addCount(MetricsConstants.ADD_MEDICATION_SUCCESS_COUNT,1);
            log.info("Medication added successfully for user: {}", medication.getPatientId());
        } catch (AmazonDynamoDBException e) {
            log.error("DynamoDB-specific error occurred while adding medication: {}", medication, e);
            throw new CustomDynamoDBException("Failed to add medication to the database due to DynamoDB-specific error", e);
        }catch (Exception e) {
            log.error("Failed to add medication for user: {}", medication.getPatientId(), e);
            throw new DatabaseAccessException("Failed to add medication to the database", e);
        }

        return medication;
    }

    public Medication deleteMedication(Medication medication){
        if (medication == null) {
            log.warn("Attempted to delete a null medication object.");
            metricsPublisher.addCount(MetricsConstants.DELETE_MEDICATION_NULL_OR_EMPTY_COUNT,1);
            return medication;
        }
        log.info("Delete medication for patientId with id: {}",medication.getPatientId());
        metricsPublisher.addCount(MetricsConstants.DELETE_MEDICATION_TOTAL_COUNT,1);
        try {
            log.info("Attempting to delete medication: {}", medication);
            this.dynamoDBMapper.delete(medication);
            metricsPublisher.addCount(MetricsConstants.DELETE_MEDICATION_SUCCESS_COUNT,1);
            log.info("Medication deleted successfully: {}", medication);
            return medication;
        }catch (AmazonDynamoDBException e) {
            log.error("DynamoDB-specific error occurred while deleting medication: {}", medication, e);
            throw new CustomDynamoDBException("Failed to delete medication from the database due to DynamoDB-specific error", e);
        } catch (Exception e) {
            log.error("Failed to delete medication: {}", medication, e);
            throw new DatabaseAccessException("Failed to delete medication from the database", e);
        }
    }

    public Medication getSingleMedication(String patientId, String medicationName) {
        try{
            log.info("Get medication for patientId with id: {}",patientId);
            metricsPublisher.addCount(MetricsConstants.GET_SINGLE_MEDICATION_TOTAL_COUNT,1);
            log.info("Attempting to get medication: {}", medicationName);
            Medication singleMedication = this.dynamoDBMapper.load(Medication.class, patientId, medicationName);

        if (singleMedication == null || singleMedication.getMedicationName() == null || singleMedication.getMedicationName().isEmpty()) {
            metricsPublisher.addCount(MetricsConstants.GET_SINGLE_MEDICATION_NULL_OR_EMPTY_COUNT, 1);
            log.warn("No medication found for user: {} and medication name: {}", patientId, medicationName);
            throw new MedicationNotFoundException("No medications found for user: " + patientId + " and medication name: " + medicationName);
        } else {
            metricsPublisher.addCount(MetricsConstants.GET_SINGLE_MEDICATION_FOUND_COUNT, 1);
            log.info("Get a single medication successfully: {}", medicationName);
            return singleMedication;
        }
        } catch (DatabaseAccessException e){
            log.error("Failed to access the database for user: {} and medication name: {}", patientId, medicationName, e);
            throw new DatabaseAccessException("Failed to access the database", e);
        }
    }

    public List<Medication> getAllMedications(String patientId){
       try {
           log.info("Get medication for patientId with id: {}",patientId);
           metricsPublisher.addCount(MetricsConstants.GET_ALL_MEDICATIONS_TOTAL_COUNT,1);
           log.info("Attempting to get all medications for user: {}", patientId);
           Medication medication = new Medication();
           medication.setPatientId(patientId);

           DynamoDBQueryExpression<Medication> queryExpression = new DynamoDBQueryExpression<Medication>()
                    .withHashKeyValues(medication);
           QueryResultPage<Medication> results = dynamoDBMapper
                   .queryPage(Medication.class, queryExpression);

            if (results.getResults().isEmpty()) {
            metricsPublisher.addCount(MetricsConstants.GET_ALL_MEDICATIONS_NULL_OR_EMPTY_COUNT, 1);
            log.warn("No medications found for user: {}", patientId);
            return Collections.emptyList();
        }
            metricsPublisher.addCount(MetricsConstants.GET_ALL_MEDICATIONS_FOUND_COUNT, 1);
            return results.getResults();
        } catch (Exception e) {
            log.error("Failed to access the database for user: {}", patientId, e);
            throw new DatabaseAccessException("Failed to access the database", e);
        }
    }
}
