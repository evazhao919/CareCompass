package com.devyanan.CareCompass.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

/**
 * Represents a medication entity stored in DynamoDB.
 */
@DynamoDBTable(tableName = "medications")
public class Medication {
    private final Logger log = LogManager.getLogger();
    private String patientId;
    private String medicationId;
    private String medicationName;
    private String prescription;
    private String instructions;
    private MEDICATION_STATUS medicationStatus;

    public enum MEDICATION_STATUS {
        ACTIVE, DISCONTINUED, ON_HOLD, TEMPORARY_STOP
    }
    @DynamoDBHashKey(attributeName = "patientId")
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    @DynamoDBRangeKey(attributeName = "medicationId")
    public String getMedicationId() {
        return medicationId;
    }
    
    public void setMedicationId(String medicationId) {
        this.medicationId = medicationId;
    }

    @DynamoDBAttribute(attributeName = "medicationName")
    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    @DynamoDBAttribute(attributeName = "prescription")
    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    @DynamoDBAttribute(attributeName = "instructions")
    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "medicationStatus")
    public MEDICATION_STATUS getMedicationStatus() {
        return medicationStatus;
    }
    public void setMedicationStatus(MEDICATION_STATUS medicationStatus) {
        this.medicationStatus = medicationStatus;
    }

}
