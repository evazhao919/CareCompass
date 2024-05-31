package com.devyanan.CareCompass.models;

import com.devyanan.CareCompass.dynamodb.models.Notification;

import java.util.Objects;

/**
 * Represents a model for notifications.
 */
public class NotificationModel {
    private final String patientId;
    private final String medicationId;
    private final String notificationTitle;
    private final String reminderContent;
    private final String scheduledTime;
    private final Notification.REMINDER_TYPE ReminderType;

    /**
     * Private constructor for the NotificationModel.
     * Use the Builder pattern to construct instances of this class.
     */
    private NotificationModel(String patientId, String medicationId, String notificationTitle, String reminderContent, String scheduledTime, Notification.REMINDER_TYPE reminderType) {
        this.patientId = patientId;
        this.medicationId = medicationId;
        this.notificationTitle = notificationTitle;
        this.reminderContent = reminderContent;
        this.scheduledTime = scheduledTime;
        ReminderType = reminderType;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getMedicationId() {
        return medicationId;
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

    public Notification.REMINDER_TYPE getReminderType() {
        return ReminderType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationModel that = (NotificationModel) o;
        return Objects.equals(patientId, that.patientId) && Objects.equals(medicationId, that.medicationId) && Objects.equals(notificationTitle, that.notificationTitle) && Objects.equals(reminderContent, that.reminderContent) && Objects.equals(scheduledTime, that.scheduledTime) && ReminderType == that.ReminderType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, medicationId, notificationTitle, reminderContent, scheduledTime, ReminderType);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String patientId;
        private String medicationId;
        private String notificationTitle;
        private String reminderContent;
        private String scheduledTime;
        private Notification.REMINDER_TYPE reminderType;
        public Builder withPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder withMedicationId(String medicationId) {
            this.medicationId = medicationId;
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
        public Builder withReminderType(Notification.REMINDER_TYPE reminderType) {
            this.reminderType = reminderType;
            return this;
        }

        public NotificationModel build() {
            return new NotificationModel(patientId, medicationId, notificationTitle, reminderContent, scheduledTime, reminderType);
        }
    }
}
