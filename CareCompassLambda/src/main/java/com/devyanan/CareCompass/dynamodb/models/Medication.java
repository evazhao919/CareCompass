package com.devyanan.CareCompass.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Objects;

@DynamoDBTable(tableName = "medications")
public class Medication {
    private String patientId;
    private String medicationName;
    private String prescription;
    private String instructions;

    @DynamoDBHashKey(attributeName = "patientId")
    @DynamoDBIndexHashKey(globalSecondaryIndexNames = {"medicationIndex", "vitalSignsIndex","userNotificationsIndex","vitalSignsIndex"}, attributeName = "patientId")
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    @DynamoDBRangeKey(attributeName = "medicationName")
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "medicationIndex", attributeName = "medicationName")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medication that = (Medication) o;
        return Objects.equals(patientId, that.patientId) && Objects.equals(medicationName, that.medicationName) && Objects.equals(prescription, that.prescription) && Objects.equals(instructions, that.instructions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, medicationName, prescription, instructions);
    }
}
