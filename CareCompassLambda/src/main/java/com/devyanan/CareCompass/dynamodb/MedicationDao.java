package com.devyanan.CareCompass.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import com.devyanan.CareCompass.exceptions.*;
import com.devyanan.CareCompass.metrics.MetricsConstants;
import com.devyanan.CareCompass.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO class for managing Medication data in DynamoDB.
 */
@Singleton
public class MedicationDao {
    private final DynamoDBMapper dynamoDBMapper;
    private final MetricsPublisher metricsPublisher;
    private final Logger log = LogManager.getLogger();
    @Inject
    public MedicationDao(DynamoDBMapper dynamoDBMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.metricsPublisher = metricsPublisher;
    }
    /**
     * Method to add a medication record.
     *
     * @param medication The medication object to add.
     * @return The added medication object.
     * @throws IllegalArgumentException If the medication object or name is null or empty.
     * @throws CustomDynamoDBException If there is a DynamoDB-specific error while adding the medication.
     * @throws DatabaseAccessException If there is an error accessing the database.
     */
    public Medication saveMedication(Medication medication){

//        if(medication == null || medication.getMedicationName() == null || medication.getMedicationName().isEmpty()){
//            metricsPublisher.addCount(MetricsConstants.ADD_MEDICATION_NULL_OR_EMPTY_COUNT,1);
//            log.info("Attempted to add a medication with null or empty name.");
//            throw new IllegalArgumentException("Medication object or name cannot be null or empty.");
//        }
//        log.info("add medication for patientId with id: {}",medication.getPatientId());
//        metricsPublisher.addCount(MetricsConstants.ADD_MEDICATION_TOTAL_COUNT,1);
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
    public Medication deleteMedication(Medication medication) {
        log.info("Attempting to delete medication with ID: {}", medication.getMedicationId());

        if (medication == null) {
            metricsPublisher.addCount(MetricsConstants.GET_SINGLE_MEDICATION_BY_MEDICATION_ID_NOT_FOUND_COUNT, 1);
            log.warn("No medication ID provided for user: {}", medication.getMedicationId());
            throw new IllegalArgumentException("medication ID cannot be empty");
        }
        dynamoDBMapper.delete(medication);
        metricsPublisher.addCount(MetricsConstants.GET_SINGLE_MEDICATION_BY_MEDICATION_ID_AND_PATIENT_ID_FOUND_COUNT, 1);
        return medication;
    }
    /**
     * Retrieves a single medication record for a specified patient and medication name.
     *
     * @param patientId      The ID of the patient.
     * @param medicationId The name of the medication.
     * @return The medication object.
     * @throws MedicationNotFoundException If no medication is found for the specified patient and medication name.
     * @throws DatabaseAccessException    If there is an error accessing the database.
     */
    public Medication deleteSingleMedicationByMedicationId(String patientId, String medicationId){
        metricsPublisher.addCount(MetricsConstants.DELETE_SINGLE_MEDICATION_TOTAL_COUNT, 1);

        Medication medicationDelete = new Medication();
        medicationDelete.setPatientId(patientId);
        medicationDelete.setMedicationId(medicationId);

        Medication existingMedication = dynamoDBMapper.load(Medication.class, patientId, medicationId);

        if (existingMedication != null) {

            dynamoDBMapper.delete(medicationDelete);

            return existingMedication;
        } else {
            throw new MedicationNotFoundException(("Medication not found for patientId: " + patientId + " and medicationId: " + medicationId));
        }
    }

    /**
     * Retrieves all medications for a specified patient.
     *
     * @param patientId The ID of the patient.
     * @return A list of medication objects.
     * @throws DatabaseAccessException If there is an error accessing the database.
     */
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

    /**
     * Update a single medication record for a specified patient and medication name.
     *
     * @param patientId      The ID of the patient.
     * @param medicationId The name of the medication.
     * @return The medication object.
     * @throws MedicationNotFoundException If no medication is found for the specified patient and medication name.
     * @throws DatabaseAccessException    If there is an error accessing the database.
     */
    public Medication getMedication(String patientId, String medicationId) {
        log.info("Update medication for patientId with id: {}", patientId);
        metricsPublisher.addCount(MetricsConstants.UPDATE_SINGLE_MEDICATION_TOTAL_COUNT, 1);
        log.info("Attempting to update medication: {}", medicationId);
        Medication singleMedication = this.dynamoDBMapper.load(Medication.class, patientId, medicationId);

        if (singleMedication == null || singleMedication.getMedicationName() == null || singleMedication.getMedicationName().isEmpty()) {
            metricsPublisher.addCount(MetricsConstants.UPDATE_SINGLE_MEDICATION_NULL_OR_EMPTY_COUNT, 1);
            log.warn("No medication found for user: {} and medication name: {}", patientId, medicationId);
            throw new MedicationNotFoundException("No medications found for user: " + patientId + " and medication name: " + medicationId);
        } else {
            metricsPublisher.addCount(MetricsConstants.UPDATE_SINGLE_MEDICATION_FOUND_COUNT, 1);
            log.info("Update a single medication successfully: {}", medicationId);
            return singleMedication;
        }
    }


    public List<Medication> retrieveMedicationsByMedicationStatus(String patientId, Medication.MEDICATION_STATUS medicationStatus) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":medicationStatus", new AttributeValue().withS(medicationStatus.name()));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("medicationStatus = :medicationStatus")
                .withExpressionAttributeValues(valueMap);

        PaginatedScanList<Medication> medications = dynamoDBMapper.scan(Medication.class, scanExpression);
//        if (medications == null || medications.isEmpty()) {
//            metricsPublisher.addMetric(RETRIEVE_BY_MEDICATION_STATUS_MEDICATION_NOT_FOUND_COUNT, 1, StandardUnit.Count);
//            throw new MedicationNotFoundException("No medications found in database for status: " + medicationStatus);
//        } else {
//            metricsPublisher.addMetric(RETRIEVE_BY_MEDICATION_STATUS_MEDICATION_FOUND_COUNT, 1, StandardUnit.Count);
//        }
        return medications;
    }
}
