package com.devyanan.CareCompass.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = DeleteNotificationRequest.Builder.class)
public class DeleteNotificationRequest {
    private final String patientId;
    private final String notificationId;;

    public DeleteNotificationRequest(String patientId, String notificationId) {
        this.patientId = patientId;
        this.notificationId = notificationId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getNotificationId() {
        return notificationId;
    }

    @Override
    public String toString() {
        return "DeleteNotificationRequest{" +
                "patientId='" + patientId + '\'' +
                ", notificationId='" + notificationId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String patientId;
        private String notificationId;;
        public Builder withPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder withNotificationId(String notificationId) {
            this.notificationId = notificationId;
            return this;
        }
        public DeleteNotificationRequest build() {
            return new DeleteNotificationRequest(patientId, notificationId);
        }
    }
}
