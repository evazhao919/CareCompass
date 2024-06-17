package com.devyanan.CareCompass.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = DeleteVitalSignsRequest.Builder.class)
public class DeleteVitalSignsRequest {
    private final String patientId;
    private final String actualCheckTime;

    public DeleteVitalSignsRequest(String patientId, String actualCheckTime) {
        this.patientId = patientId;
        this.actualCheckTime = actualCheckTime;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getActualCheckTime() {
        return actualCheckTime;
    }

    @Override
    public String toString() {
        return "DeleteVitalSignsRequest{" +
                "patientId='" + patientId + '\'' +
                ", actualCheckTime='" + actualCheckTime + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String patientId;
        private String actualCheckTime;
        public Builder withPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder withActualCheckTime(String actualCheckTime) {
            this.actualCheckTime = actualCheckTime;
            return this;
        }

        public DeleteVitalSignsRequest build() {
            return new DeleteVitalSignsRequest(patientId, actualCheckTime);
        }
    }
}