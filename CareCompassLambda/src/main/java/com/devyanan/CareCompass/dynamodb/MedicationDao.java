package com.devyanan.CareCompass.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import com.devyanan.CareCompass.exceptions.*;
import com.devyanan.CareCompass.metrics.MetricsConstants;
import com.devyanan.CareCompass.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
            metricsPublisher.addCount(MetricsConstants.ADD_MEDICATION_UNSUCCESS_COUNT,1);
            log.warn("Attempted to add a medication with null or empty name.");
            throw new InvalidAttributeValueException("Medication object or name cannot be null or empty.");
        }

        try {
            dynamoDBMapper.save(medication);
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

    public boolean deleteMedication(Medication medication){
        if (medication == null || medication.getMedicationName() == null || medication.getMedicationName().isEmpty()) {
            log.warn("Attempted to delete a null or empty medication object.");
            return false;
        }

        try {
            log.info("Attempting to delete medication: {}", medication);
            this.dynamoDBMapper.delete(medication);
            log.info("Medication deleted successfully: {}", medication);
            return true;
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
        Medication singleMedication = this.dynamoDBMapper.load(Medication.class, patientId, medicationName);

        if (singleMedication == null) {
            metricsPublisher.addCount(MetricsConstants.GETSINGLEMEDICATION_MEDICATIONNOTFOUND_COUNT, 1);
            log.warn("No medication found for user: {} and medication name: {}", patientId, medicationName);
            throw new MedicationNotFoundException("No medications found for user: " + patientId + " and medication name: " + medicationName);
        }
            metricsPublisher.addCount(MetricsConstants.GETSINGLEMEDICATION_MEDICATIONFOUND_COUNT, 1);
            return singleMedication;
        } catch (Exception e){
            log.error("Failed to access the database for user: {} and medication name: {}", patientId, medicationName, e);
            throw new DatabaseAccessException("Failed to access the database", e);
        }
    }

    public List<Medication> getAllMedications(String patientId){
       try {
           Medication medication = new Medication();
           medication.setPatientId(patientId);

           DynamoDBQueryExpression<Medication> queryExpression = new DynamoDBQueryExpression<Medication>()
                    .withHashKeyValues(medication);
           List<Medication> medicationList = dynamoDBMapper.query(Medication.class, queryExpression);

            if (medicationList.isEmpty()) {
            metricsPublisher.addCount(MetricsConstants.GETALLMEDICATIONS_MEDICATIONNOTFOUND_COUNT, 1);
            log.warn("No medications found for user: {}", patientId);
            throw new MedicationsNotFoundException("No medications found for user: " + patientId);
        }
            metricsPublisher.addCount(MetricsConstants.GETALLMEDICATIONS_MEDICATIONFOUND_COUNT, 1);
            return medicationList;
        } catch (Exception e) {
            log.error("Failed to access the database for user: {}", patientId, e);
            throw new DatabaseAccessException("Failed to access the database", e);
        }
    }
}