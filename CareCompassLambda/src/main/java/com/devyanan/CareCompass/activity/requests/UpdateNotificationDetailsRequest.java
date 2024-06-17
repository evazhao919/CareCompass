package com.devyanan.CareCompass.activity.requests;

import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UpdateNotificationDetailsRequest.Builder.class)
public class UpdateNotificationDetailsRequest {
    private final String patientId;
    private final String notificationId;
    private final String notificationTitle;
    private final Notification.REMINDER_TYPE reminderType;
    private final String reminderContent;
    private final String scheduledTime;

    public UpdateNotificationDetailsRequest(String patientId, String notificationId, String notificationTitle, Notification.REMINDER_TYPE reminderType, String reminderContent,  String scheduledTime) {
        this.patientId = patientId;
        this.notificationId = notificationId;
        this.notificationTitle = notificationTitle;
        this.reminderType = reminderType;
        this.reminderContent = reminderContent;
        this.scheduledTime = scheduledTime;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public Notification.REMINDER_TYPE getReminderType() {
        return reminderType;
    }

    public String getReminderContent() {
        return reminderContent;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    @Override
    public String toString() {
        return "UpdateNotificationDetailsRequest{" +
                "patientId='" + patientId + '\'' +
                ", notificationId='" + notificationId + '\'' +
                ", notificationTitle='" + notificationTitle + '\'' +
                ", reminderType=" + reminderType +
                ", reminderContent='" + reminderContent + '\'' +
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
        private String notificationId;
        private String notificationTitle;
        private Notification.REMINDER_TYPE reminderType;
        private String reminderContent;
        private String scheduledTime;
        public Builder withPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }
        public Builder withNotificationId(String notificationId) {
            this.notificationId = notificationId;
            return this;
        }
        public Builder withNotificationTitle(String notificationTitle) {
            this.notificationTitle = notificationTitle;
            return this;
        }

        public Builder withReminderType(Notification.REMINDER_TYPE reminderType) {
            this.reminderType = reminderType;
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

        public UpdateNotificationDetailsRequest build() {
            return new UpdateNotificationDetailsRequest(patientId, notificationId, notificationTitle, reminderType, reminderContent, scheduledTime);
        }
    }
}