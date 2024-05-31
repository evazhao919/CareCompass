package com.devyanan.CareCompass.activity.requests;

import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = AddNotificationRequest.Builder.class)
public class AddNotificationRequest {
    private final String patientId;
//    private final String notificationId;
    private final String notificationTitle;
    private final String reminderContent;
    private final String scheduledTime;
    private final Notification.REMINDER_TYPE reminderType;

    public AddNotificationRequest(String patientId, String notificationTitle, String reminderContent, String scheduledTime, Notification.REMINDER_TYPE reminderType) {
        this.patientId = patientId;
//        this.notificationId = notificationId;
        this.notificationTitle = notificationTitle;
        this.reminderContent = reminderContent;
        this.scheduledTime = scheduledTime;
        this.reminderType = reminderType;
    }

    public String getPatientId() {
        return patientId;
    }

//    public String getNotificationId() {
//        return notificationId;
//    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public String getReminderContent() {
        return reminderContent;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public Notification.REMINDER_TYPE getReminderType() {
        return reminderType;
    }

    @Override
    public String toString() {
        return "AddNotificationRequest{" +
                "patientId='" + patientId + '\'' +
//                ", notificationId='" + notificationId + '\'' +
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
//        private String notificationId;
        private String notificationTitle;
        private String reminderContent;
        private String scheduledTime;
        private Notification.REMINDER_TYPE reminderType;
        public Builder withPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }

//        public Builder withNotificationId(String notification) {
//            this.notificationId = notification;
//            return this;
//        }

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

        public Builder withReminderType(Notification.REMINDER_TYPE reminderType) {
            this.reminderType = reminderType;
            return this;
        }

        public AddNotificationRequest build() {
            return new AddNotificationRequest(patientId, notificationTitle, reminderContent, scheduledTime, reminderType);
        }
    }
}
