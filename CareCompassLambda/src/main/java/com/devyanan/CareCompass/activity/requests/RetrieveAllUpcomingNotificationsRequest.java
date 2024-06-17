package com.devyanan.CareCompass.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = RetrieveAllUpcomingNotificationsRequest.Builder.class)
public class RetrieveAllUpcomingNotificationsRequest {
    private final String patientId;
    private final String scheduledTime;

    public RetrieveAllUpcomingNotificationsRequest(String patientId, String scheduledTime) {
        this.patientId = patientId;
        this.scheduledTime = scheduledTime;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    @Override
    public String toString() {
        return "RetrieveAllUpcomingNotificationsRequest{" +
                "patientId='" + patientId + '\'' +
                ", scheduledTime='" + scheduledTime + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String patientId;
        private String scheduledTime;
        public Builder withPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }
        public Builder withScheduledTime(String scheduledTime) {
            this.scheduledTime = scheduledTime;
            return this;
        }

        public RetrieveAllUpcomingNotificationsRequest build() {
            return new RetrieveAllUpcomingNotificationsRequest(patientId, scheduledTime);
        }
    }
}