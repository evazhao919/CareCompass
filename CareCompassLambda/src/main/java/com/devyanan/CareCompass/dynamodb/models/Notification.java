package com.devyanan.CareCompass.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a notification entity stored in DynamoDB.
 */
@DynamoDBTable(tableName = "notifications")
public class Notification {
    private String patientId;
    private String notificationId;
    private String notificationTitle;
    private String reminderContent;
    private LocalDateTime scheduledTime;
    private REMINDER_TYPE reminderType;

    public enum REMINDER_TYPE {
        GLUCOSE_MEASUREMENT, MEDICATION, VITAL_SIGNS, APPOINTMENT, GENERAL
    }

    @DynamoDBHashKey(attributeName = "patientId")
    @DynamoDBIndexHashKey(globalSecondaryIndexNames = {"notificationsIndex"}, attributeName = "patientId")
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    @DynamoDBRangeKey(attributeName = "notificationId")
    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    @DynamoDBAttribute(attributeName = "notificationTitle")
    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }
    @DynamoDBAttribute(attributeName = "reminderContent")
    public String getReminderContent() {
        return reminderContent;
    }

    public void setReminderContent(String reminderContent) {
        if (reminderContent == null || reminderContent.equals("")) {
            this.reminderContent = "";
        } else {
            this.reminderContent = reminderContent;
        }
    }

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "reminderType")
    public Notification.REMINDER_TYPE getReminderType() {
        return reminderType;
    }

    public void setReminderType(REMINDER_TYPE reminderType) {
        this.reminderType = reminderType;
    }

    @DynamoDBAttribute(attributeName = "scheduledTime")
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "notificationsIndex", attributeName = "scheduledTime")
    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(patientId, that.patientId) && Objects.equals(notificationId, that.notificationId) && Objects.equals(notificationTitle, that.notificationTitle) && Objects.equals(reminderContent, that.reminderContent) && Objects.equals(scheduledTime, that.scheduledTime) && reminderType == that.reminderType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, notificationId, notificationTitle, reminderContent, scheduledTime, reminderType);
    }
}
