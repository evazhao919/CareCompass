package com.devyanan.CareCompass.activity.requests;

import com.devyanan.CareCompass.dynamodb.models.Medication;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = AddMedicationRequest.Builder.class)
public class AddMedicationRequest {
    private final String patientId;
//    private final String medicationId;
    private final String medicationName;
    private final String prescription;
    private final String instructions;
    private final Medication.MEDICATION_STATUS medicationStatus;

    public AddMedicationRequest(String patientId, String medicationName, String prescription, String instructions, Medication.MEDICATION_STATUS medicationStatus) {
        this.patientId = patientId;
//        this.medicationId = medicationId;
        this.medicationName = medicationName;
        this.prescription = prescription;
        this.instructions = instructions;
        this.medicationStatus = medicationStatus;
    }

    public String getPatientId() {
        return patientId;
    }

//    public String getMedicationId() {
//        return medicationId;
//    }

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
    public String toString() {
        return "AddMedicationRequest{" +
                "patientId='" + patientId + '\'' +
//                ", medicationId='" + medicationId + '\'' +
                ", medicationName='" + medicationName + '\'' +
                ", prescription='" + prescription + '\'' +
                ", instructions='" + instructions + '\'' +
                ", medicationStatus=" + medicationStatus +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String patientId;
//        private String medicationId;
        private  String medicationName;
        private String prescription;
        private String instructions;
        private Medication.MEDICATION_STATUS medicationStatus;

        public Builder withPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }

//        public Builder withMedicationId(String medicationId) {
//            this.medicationId = medicationId;
//            return this;
//        }

        public Builder withMedicationName(String medicationName) {
            this.medicationName = medicationName;
            return this;
        }

        public Builder withPrescription(String prescription) {
            this.prescription = prescription;
            return this;
        }

        public Builder withInstructions(String instructions) {
            this.instructions = instructions;
            return this;
        }

        public Builder withMedicationStatus(Medication.MEDICATION_STATUS medicationStatus) {
            this.medicationStatus = medicationStatus;
            return this;
        }

        public AddMedicationRequest build() {
            return new AddMedicationRequest(patientId, medicationName, prescription,instructions, medicationStatus);
        }
    }
}
