package com.devyanan.CareCompass.models;

import com.devyanan.CareCompass.dynamodb.models.Notification;

import java.time.LocalDateTime;
import java.util.Objects;

public class NotificationModel {
    private final String patientId;
    private final String notificationId;
    private final String notificationTitle;
    private final Notification.ReminderType reminderType;
    private final String reminderContent;
    private final String additionalNotes;
    private final LocalDateTime reminderTime;

    public NotificationModel(String patientId, String notificationId, String notificationTitle, Notification.ReminderType reminderType, String reminderContent, String additionalNotes, LocalDateTime reminderTime) {
        this.patientId = patientId;
        this.notificationId = notificationId;
        this.notificationTitle = notificationTitle;
        this.reminderType = reminderType;
        this.reminderContent = reminderContent;
        this.additionalNotes = additionalNotes;
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

    public Notification.ReminderType getReminderType() {
        return reminderType;
    }

    public String getReminderContent() {
        return reminderContent;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationModel that = (NotificationModel) o;
        return Objects.equals(patientId, that.patientId) && Objects.equals(notificationId, that.notificationId) && Objects.equals(notificationTitle, that.notificationTitle) && reminderType == that.reminderType && Objects.equals(reminderContent, that.reminderContent) && Objects.equals(additionalNotes, that.additionalNotes) && Objects.equals(reminderTime, that.reminderTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, notificationId, notificationTitle, reminderType, reminderContent, additionalNotes, reminderTime);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String patientId;
        private String notificationId;
        private String notificationTitle;
        private Notification.ReminderType reminderType;
        private String reminderContent;
        private String additionalNotes;
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

        public Builder withReminderTime(LocalDateTime reminderTime) {
            this.reminderTime = reminderTime;
            return this;
        }

        public NotificationModel build() {
            return new NotificationModel(patientId, notificationId, notificationTitle, reminderType, reminderContent, additionalNotes, reminderTime);
        }
    }
}
