package com.devyanan.CareCompass.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = DeleteMedicationRequest.Builder.class)
public class DeleteMedicationRequest {
    private final String patientId;
    private final String medicationId;

    public DeleteMedicationRequest(String patientId, String medicationId) {
        this.patientId = patientId;
        this.medicationId = medicationId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getMedicationId() {
        return medicationId;
    }

    @Override
    public String toString() {
        return "DeleteMedicationRequest{" +
                "patientId='" + patientId + '\'' +
                ", medicationId='" + medicationId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String patientId;
        private String medicationId;

        public Builder withPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder withMedicationId(String medicationId) {
            this.medicationId = medicationId;
            return this;
        }

        public DeleteMedicationRequest build() {
            return new DeleteMedicationRequest(patientId, medicationId);
        }
    }
}