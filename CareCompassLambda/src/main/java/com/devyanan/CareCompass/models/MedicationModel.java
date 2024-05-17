package com.devyanan.CareCompass.models;

import com.devyanan.CareCompass.dynamodb.models.Medication;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class MedicationModel {
    private final String patientId;
    private final String medicationName;
    private final String prescription;
    private final String instructions;

    public MedicationModel(String patientId, String medicationName, String prescription, String instructions) {
        this.patientId = patientId;
        this.medicationName = medicationName;
        this.prescription = prescription;
        this.instructions = instructions;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public String getPrescription() {
        return prescription;
    }

    public String getInstructions() {
        return instructions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicationModel that = (MedicationModel) o;
        return Objects.equals(patientId, that.patientId) && Objects.equals(medicationName, that.medicationName) && Objects.equals(prescription, that.prescription) && Objects.equals(instructions, that.instructions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, medicationName, prescription, instructions);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String patientId;
        private String medicationName;
        private String prescription;
        private String instructions;
            public Builder withPatientId(String patientId) {
                this.patientId = patientId;
                return this;
            }

            public Builder withMedicationName(String medicationName) {
                this.medicationName = medicationName;
                return this;
            }

            public Builder withPrescription(String prescription) {
                this.prescription = prescription;
                return this;
            }

            public Builder withInstructions(String instructions) {
                this.instructions= instructions;
                return this;
            }

            public MedicationModel build() {
                return new MedicationModel(patientId, medicationName, prescription, instructions);
            }
        }
    }
