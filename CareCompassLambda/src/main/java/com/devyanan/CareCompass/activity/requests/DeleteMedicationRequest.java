package com.devyanan.CareCompass.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = DeleteMedicationRequest.Builder.class)
public class DeleteMedicationRequest {
    private final String patientId;
    private final String medicationName;

    public DeleteMedicationRequest(String patientId, String medicationName) {
        this.patientId = patientId;
        this.medicationName = medicationName;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getMedicationName() {
        return medicationName;
    }

    @Override
    public String toString() {
        return "DeleteMedicationRequest{" +
                "patientId='" + patientId + '\'' +
                ", medicationName='" + medicationName + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String patientId;
        private String medicationName;

        public Builder withPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder withMedicationName(String medicationName) {
            this.medicationName = medicationName;
            return this;
        }

        public DeleteMedicationRequest build() {
            return new DeleteMedicationRequest(patientId, medicationName);
        }
    }
}