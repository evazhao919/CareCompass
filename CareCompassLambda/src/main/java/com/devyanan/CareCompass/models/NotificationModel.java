package com.devyanan.CareCompass.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class NotificationModel {
    private final String patientId;
    private final String notificationId;
    private final String notificationTitle;
    private final String reminderContent;
    private final LocalDateTime reminderTime;

    public NotificationModel(String patientId, String notificationId, String notificationTitle, String reminderContent, LocalDateTime reminderTime) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationModel that = (NotificationModel) o;
        return Objects.equals(patientId, that.patientId) && Objects.equals(notificationId, that.notificationId) && Objects.equals(notificationTitle, that.notificationTitle) && Objects.equals(reminderContent, that.reminderContent) && Objects.equals(reminderTime, that.reminderTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, notificationId, notificationTitle, reminderContent, reminderTime);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

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

        public NotificationModel build() {
            return new NotificationModel(patientId, notificationId, notificationTitle, reminderContent, reminderTime);
        }
    }
}
