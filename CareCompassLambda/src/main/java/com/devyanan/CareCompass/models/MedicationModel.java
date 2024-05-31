package com.devyanan.CareCompass.models;

import com.devyanan.CareCompass.dynamodb.models.Medication;

import java.util.Objects;

/**
 * Represents a model for medication information.
 */
public class MedicationModel {
    private final String patientId;
    private final String medicationId;
    private final String medicationName;
    private final String prescription;
    private final String instructions;
    private final Medication.MEDICATION_STATUS medicationStatus;

    /**
     * Private constructor for the MedicationModel.
     * Use the Builder pattern to construct instances of this class.
     */
    private MedicationModel(String patientId, String medicationId, String medicationName, String prescription, String instructions, Medication.MEDICATION_STATUS medicationStatus) {
        this.patientId = patientId;
        this.medicationId = medicationId;
        this.medicationName = medicationName;
        this.prescription = prescription;
        this.instructions = instructions;
        this.medicationStatus = medicationStatus;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getMedicationId() {
        return medicationId;
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

    public Medication.MEDICATION_STATUS getMedicationStatus() {
        return medicationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicationModel that = (MedicationModel) o;
        return Objects.equals(patientId, that.patientId) && Objects.equals(medicationId, that.medicationId) && Objects.equals(medicationName, that.medicationName) && Objects.equals(prescription, that.prescription) && Objects.equals(instructions, that.instructions) && medicationStatus == that.medicationStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, medicationId, medicationName, prescription, instructions, medicationStatus);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String patientId;
        private String medicationId;
        private String medicationName;
        private String prescription;
        private String instructions;
        private Medication.MEDICATION_STATUS medicationStatus;

           public Builder withPatientId(String patientId) {
               this.patientId = patientId;
               return this;
            }

           public Builder withMedicationId(String medicationId) {
               this.medicationId = medicationId;
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

           public Builder withMedicationStatus(Medication.MEDICATION_STATUS medicationStatus) {
                this.medicationStatus = medicationStatus;
                return this;
            }

            public MedicationModel build() {
                return new MedicationModel(patientId, medicationId, medicationName, prescription, instructions, medicationStatus);
            }
        }
    }
