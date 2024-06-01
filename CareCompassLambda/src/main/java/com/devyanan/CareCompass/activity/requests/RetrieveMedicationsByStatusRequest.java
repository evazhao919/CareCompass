package com.devyanan.CareCompass.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

public class RetrieveMedicationsByStatusRequest {
    private final String patientId;
    private final String medicationStatus;

    public RetrieveMedicationsByStatusRequest(String patientId, String medicationStatus) {
        this.patientId = patientId;
        this.medicationStatus = medicationStatus;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getMedicationStatus() {
        return medicationStatus;
    }

    @Override
    public String toString() {
        return "RetrieveMedicationsByStatusRequest {" +
                "patientId='" + patientId + '\'' +
                ", medicationStatus='" + medicationStatus + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String patientId;
        private String medicationStatus;
        public Builder withPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder withMedicationStatus(String medicationStatus) {
            this.medicationStatus = medicationStatus;
            return this;
        }
        public RetrieveMedicationsByStatusRequest build() {
            return new RetrieveMedicationsByStatusRequest(patientId, medicationStatus);
        }
    }
}
