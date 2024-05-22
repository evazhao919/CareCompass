package com.devyanan.CareCompass.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = GetAllNotificationsRequest.Builder.class)
public class GetAllNotificationsRequest {
    private final String patientId;

    public GetAllNotificationsRequest(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientId() {
        return patientId;
    }
    @Override
    public String toString() {
        return "GetAllNotificationsRequest{" +
                "patientId='" + patientId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String patientId;
        public Builder withPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }
        public GetAllNotificationsRequest build() {
            return new GetAllNotificationsRequest(patientId);
        }
    }
}