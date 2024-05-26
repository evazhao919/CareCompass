package com.devyanan.CareCompass.activity.requests;

import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = AddNotificationRequest.Builder.class)
public class AddNotificationRequest {
    private final String patientId;
    private final String notificationTitle;
    private final String reminderContent;
    private final String scheduledTime;
    private final Notification.ReminderType reminderType;

    public AddNotificationRequest(String patientId, String notificationTitle, String reminderContent, String scheduledTime, Notification.ReminderType reminderType) {
        this.patientId = patientId;
        this.notificationTitle = notificationTitle;
        this.reminderContent = reminderContent;
        this.scheduledTime = scheduledTime;
        this.reminderType = reminderType;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public String getReminderContent() {
        return reminderContent;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public Notification.ReminderType getReminderType() {
        return reminderType;
    }

    @Override
    public String toString() {
        return "AddNotificationRequest{" +
                "patientId='" + patientId + '\'' +
                ", notificationTitle='" + notificationTitle + '\'' +
                ", reminderContent='" + reminderContent + '\'' +
                ", scheduledTime='" + scheduledTime + '\'' +
                ", reminderType=" + reminderType +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String patientId;
        private String notificationTitle;
        private String reminderContent;
        private String scheduledTime;
        private Notification.ReminderType reminderType;
        public Builder withPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder withNotificationTitle(String notificationTitle) {
            this.notificationTitle = notificationTitle;
            return this;
        }

        public Builder withReminderContent(String reminderContent) {
            this.reminderContent = reminderContent;
            return this;
        }

        public Builder withScheduledTime(String scheduledTime) {
            this.scheduledTime = scheduledTime;
            return this;
        }

        public Builder withReminderType(Notification.ReminderType reminderType) {
            this.reminderType = reminderType;
            return this;
        }

        public AddNotificationRequest build() {
            return new AddNotificationRequest(patientId, notificationTitle, reminderContent, scheduledTime, reminderType);
        }
    }
}
