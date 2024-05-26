package com.devyanan.CareCompass.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = DeleteNotificationRequest.Builder.class)
public class DeleteNotificationRequest {
    private final String patientId;
    private final String scheduledTime;

    public DeleteNotificationRequest(String patientId, String scheduledTime) {
        this.patientId = patientId;
        this.scheduledTime = scheduledTime;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getscheduledTime() {
        return scheduledTime;
    }

    @Override
    public String toString() {
        return "DeleteNotificationRequest{" +
                "patientId='" + patientId + '\'' +
                ", scheduledTime=" + scheduledTime +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static DeleteNotificationRequest.Builder builder() {
        return new DeleteNotificationRequest.Builder();
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
        public DeleteNotificationRequest build() {
            return new DeleteNotificationRequest(patientId, scheduledTime);
        }
    }
}
