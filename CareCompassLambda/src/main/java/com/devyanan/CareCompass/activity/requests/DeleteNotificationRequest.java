package com.devyanan.CareCompass.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = DeleteNotificationRequest.Builder.class)
public class DeleteNotificationRequest {
    private final String patientId;
    private final String notificationId;
    private final String notificationTitle;
    private final String reminderContent;
    private final String reminderTime;

    public DeleteNotificationRequest(String patientId, String notificationId, String notificationTitle, String reminderContent, String reminderTime) {
        this.patientId = patientId;
        this.notificationId = notificationId;
        this.notificationTitle = notificationTitle;
        this.reminderContent = reminderContent;
        this.reminderTime = reminderTime;
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

    public String getReminderContent() {
        return reminderContent;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    @Override
    public String toString() {
        return "DeleteNotificationRequest{" +
                "patientId='" + patientId + '\'' +
                ", notificationId='" + notificationId + '\'' +
                ", notificationTitle='" + notificationTitle + '\'' +
                ", reminderContent='" + reminderContent + '\'' +
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
        private String notificationId;
        private String notificationTitle;
        private String reminderContent;
        private String reminderTime;
        public Builder withPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder withNotificationId(String notificationId) {
            this.notificationId = notificationId;
            return this;
        }
        public Builder withNotificationTitle(String notificationTitle) {
            this.notificationTitle= notificationTitle;
            return this;
        }

        public Builder withReminderContent(String reminderContent) {
            this.reminderContent = reminderContent;
            return this;
        }

        public Builder withReminderTime(String reminderTime) {
            this.reminderTime = reminderTime;
            return this;
        }
        public DeleteNotificationRequest build() {
            return new DeleteNotificationRequest(patientId, notificationId, notificationTitle, reminderContent, reminderTime);
        }
    }
}
