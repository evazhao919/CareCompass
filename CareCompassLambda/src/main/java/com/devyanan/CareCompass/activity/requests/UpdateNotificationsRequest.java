package com.devyanan.CareCompass.activity.requests;

import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UpdateNotificationsRequest.Builder.class)
public class UpdateNotificationsRequest {
    private final String patientId;
    private final String notificationTitle;
    private final Notification.ReminderType reminderType;
    private final String reminderContent;
    private final String additionalNotes;
    private final String reminderTime;

    public UpdateNotificationsRequest(String patientId, String notificationTitle, Notification.ReminderType reminderType, String reminderContent, String additionalNotes, String reminderTime) {
        this.patientId = patientId;
        this.notificationTitle = notificationTitle;
        this.reminderType = reminderType;
        this.reminderContent = reminderContent;
        this.additionalNotes = additionalNotes;
        this.reminderTime = reminderTime;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public Notification.ReminderType getReminderType() {
        return reminderType;
    }

    public String getReminderContent() {
        return reminderContent;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    @Override
    public String toString() {
        return "UpdateNotificationsRequest{" +
                "patientId='" + patientId + '\'' +
                ", notificationTitle='" + notificationTitle + '\'' +
                ", reminderType=" + reminderType +
                ", reminderContent='" + reminderContent + '\'' +
                ", additionalNotes='" + additionalNotes + '\'' +
                ", reminderTime=" + reminderTime +
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
        private Notification.ReminderType reminderType;
        private String reminderContent;
        private String additionalNotes;
        private String reminderTime;
        public UpdateNotificationsRequest.Builder withPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder withNotificationTitle(String notificationTitle) {
            this.notificationTitle = notificationTitle;
            return this;
        }

        public Builder withReminderType(Notification.ReminderType reminderType) {
            this.reminderType = reminderType;
            return this;
        }

        public Builder withReminderContent(String reminderContent) {
            this.reminderContent = reminderContent;
            return this;
        }

        public Builder withAdditionalNotes(String additionalNotes) {
            this.additionalNotes = additionalNotes;
            return this;
        }

        public Builder withReminderTime(String reminderTime) {
            this.reminderTime = reminderTime;
            return this;
        }

        public UpdateNotificationsRequest build() {
            return new UpdateNotificationsRequest(patientId, notificationTitle, reminderType, reminderContent,  additionalNotes, reminderTime);
        }
    }
}
