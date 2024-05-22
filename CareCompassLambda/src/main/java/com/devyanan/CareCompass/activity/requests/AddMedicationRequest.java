package com.devyanan.CareCompass.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = AddMedicationRequest.Builder.class)
public class AddMedicationRequest {
    private final String patientId;
    private final String medicationName;
    private final String prescription;
    private final String instructions;

    public AddMedicationRequest(String patientId, String medicationName, String prescription, String instructions) {
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
    public String toString() {
        return "AddMedicationRequest{" +
                "patientId='" + patientId + '\'' +
                ", medicationName='" + medicationName + '\'' +
                ", prescription='" + prescription + '\'' +
                ", instructions='" + instructions + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String patientId;
        private  String medicationName;
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
            this.instructions = instructions;
            return this;
        }

        public AddMedicationRequest build() {
            return new AddMedicationRequest(patientId, medicationName, prescription,instructions);
        }
    }
}
