package com.devyanan.CareCompass.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = DeleteNotificationRequest.Builder.class)
public class DeleteNotificationRequest {
    private final String patientId;
    private final String reminderTime;

    public DeleteNotificationRequest(String patientId, String reminderTime) {
        this.patientId = patientId;
        this.reminderTime = reminderTime;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    @Override
    public String toString() {
        return "DeleteNotificationRequest{" +
                "patientId='" + patientId + '\'' +
                ", reminderTime=" + reminderTime +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static DeleteNotificationRequest.Builder builder() {
        return new DeleteNotificationRequest.Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String patientId;
        private String reminderTime;
        public Builder withPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder withReminderTime(String reminderTime) {
            this.reminderTime = reminderTime;
            return this;
        }
        public DeleteNotificationRequest build() {
            return new DeleteNotificationRequest(patientId, reminderTime);
        }
    }
}
