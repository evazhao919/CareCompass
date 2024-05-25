package com.devyanan.CareCompass.models;

import com.devyanan.CareCompass.dynamodb.models.Notification;

import java.util.Objects;

/**
 * Represents a model for notifications.
 */
public class NotificationModel {
    private final String patientId;
    private final String notificationId;
    private final String notificationTitle;
    private final String reminderContent;
    private final String reminderTime;
    private final Notification.ReminderType ReminderType;

    /**
     * Private constructor for the NotificationModel.
     * Use the Builder pattern to construct instances of this class.
     */
    private NotificationModel(String patientId, String notificationId, String notificationTitle, String reminderContent, String reminderTime, Notification.ReminderType reminderType) {
        this.patientId = patientId;
        this.notificationId = notificationId;
        this.notificationTitle = notificationTitle;
        this.reminderContent = reminderContent;
        this.reminderTime = reminderTime;
        ReminderType = reminderType;
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

    public Notification.ReminderType getReminderType() {
        return ReminderType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationModel that = (NotificationModel) o;
        return Objects.equals(patientId, that.patientId) && Objects.equals(notificationId, that.notificationId) && Objects.equals(notificationTitle, that.notificationTitle) && Objects.equals(reminderContent, that.reminderContent) && Objects.equals(reminderTime, that.reminderTime) && ReminderType == that.ReminderType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, notificationId, notificationTitle, reminderContent, reminderTime, ReminderType);
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
        private String reminderTime;
        private Notification.ReminderType reminderType;
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

        public Builder withReminderTime(String reminderTime) {
            this.reminderTime = reminderTime;
            return this;
        }
        public Builder withReminderType(Notification.ReminderType reminderType) {
            this.reminderType = reminderType;
            return this;
        }

        public NotificationModel build() {
            return new NotificationModel(patientId, notificationId, notificationTitle, reminderContent, reminderTime, reminderType);
        }
    }
}
