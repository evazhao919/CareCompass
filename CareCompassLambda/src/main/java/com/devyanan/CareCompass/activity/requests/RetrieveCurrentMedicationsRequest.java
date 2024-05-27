package com.devyanan.CareCompass.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

public class RetrieveCurrentMedicationsRequest {
    private final String medicationStatus;

    public RetrieveCurrentMedicationsRequest(String medicationStatus) {
        this.medicationStatus = medicationStatus;
    }

    public String getMedicationStatus() {
        return medicationStatus;
    }

    @Override
    public String toString() {
        return "RetrieveCurrentMedicationsRequest{" +
                "medicationStatus='" + medicationStatus + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {

        private String medicationStatus;

        public Builder withMedicationStatus(String medicationStatus) {
            this.medicationStatus = medicationStatus;
            return this;
        }
        public RetrieveCurrentMedicationsRequest build() {
            return new RetrieveCurrentMedicationsRequest(medicationStatus);
        }
    }
}
