package com.devyanan.CareCompass.models;

import com.devyanan.CareCompass.dynamodb.models.Notification;

import java.util.Objects;

/**
 * Represents a model for notifications.
 */
public class NotificationModel {
    private final String patientId;
    private final String notificationTitle;
    private final String reminderContent;
    private final String scheduledTime;
    private final Notification.ReminderType ReminderType;

    /**
     * Private constructor for the NotificationModel.
     * Use the Builder pattern to construct instances of this class.
     */
    private NotificationModel(String patientId, String notificationTitle, String reminderContent, String scheduledTime, Notification.ReminderType reminderType) {
        this.patientId = patientId;
        this.notificationTitle = notificationTitle;
        this.reminderContent = reminderContent;
        this.scheduledTime = scheduledTime;
        ReminderType = reminderType;
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
        return ReminderType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationModel that = (NotificationModel) o;
        return Objects.equals(patientId, that.patientId) && Objects.equals(notificationTitle, that.notificationTitle) && Objects.equals(reminderContent, that.reminderContent) && Objects.equals(scheduledTime, that.scheduledTime) && ReminderType == that.ReminderType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, notificationTitle, reminderContent, scheduledTime, ReminderType);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

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

        public NotificationModel build() {
            return new NotificationModel(patientId, notificationTitle, reminderContent, scheduledTime, reminderType);
        }
    }
}
