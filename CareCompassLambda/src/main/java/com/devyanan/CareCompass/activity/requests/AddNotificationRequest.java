package com.devyanan.CareCompass.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDateTime;
@JsonDeserialize(builder = AddNotificationRequest.Builder.class)
public class AddNotificationRequest {
    private final String patientId;
    private final String notificationId;
    private final String notificationTitle;
    private final String reminderContent;
    private final LocalDateTime reminderTime;

    public AddNotificationRequest(String patientId, String notificationId, String notificationTitle, String reminderContent,  LocalDateTime reminderTime) {
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

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }

    @Override
    public String toString() {
        return "CreateNotificationRequest{" +
                "patientId='" + patientId + '\'' +
                ", notificationId='" + notificationId + '\'' +
                ", notificationTitle='" + notificationTitle + '\'' +
                ", reminderContent='" + reminderContent + '\'' +
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
        private String notificationId;
        private String notificationTitle;
        private String reminderContent;
        private LocalDateTime reminderTime;
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

        public Builder withReminderContent(String reminderContent) {
            this.reminderContent = reminderContent;
            return this;
        }

        public Builder withReminderTime(LocalDateTime reminderTime) {
            this.reminderTime = reminderTime;
            return this;
        }

        public AddNotificationRequest build() {
            return new AddNotificationRequest(patientId, notificationId, notificationTitle, reminderContent, reminderTime);
        }
    }
}
