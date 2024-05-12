package com.devyanan.CareCompass.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;

import java.time.LocalDateTime;
import java.util.Objects;
@DynamoDBTable(tableName = "notifications")
public class Notification {
    private String userId;
    private String notificationId;
    private String notificationTitle;
    private ReminderType reminderType;
    private String reminderContent;
    private String additionalNotes;
    private LocalDateTime reminderTime;

    public enum ReminderType {
        MEDICATION, APPOINTMENT, GLUCOSE_MEASUREMENT, GENERAL
    }

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBIndexHashKey(globalSecondaryIndexNames = {"medicationNameIndex", "vitalSignsTrackingIndex","userNotificationsIndex"}, attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNotificationId() {
        return notificationId;
    }
    @DynamoDBRangeKey(attributeName = "notificationId")
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
    @DynamoDBAttribute(attributeName = "reminderType")
    public ReminderType getReminderType() {
        return reminderType;
    }

    public void setReminderType(ReminderType reminderType) {
        this.reminderType = reminderType;
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
    @DynamoDBAttribute(attributeName = "additionalNotes")
    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        if (additionalNotes == null || additionalNotes.equals("")) {
            this.additionalNotes = "";
        } else {
            this.additionalNotes = additionalNotes;
        }
    }

    @DynamoDBAttribute(attributeName = "reminderTime")
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "userNotificationsIndex", attributeName = "reminderTime")
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    public LocalDateTime getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(LocalDateTime reminderTime) {
        this.reminderTime = reminderTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(userId, that.userId) && Objects.equals(notificationId, that.notificationId) && Objects.equals(notificationTitle, that.notificationTitle) && reminderType == that.reminderType && Objects.equals(reminderContent, that.reminderContent) && Objects.equals(additionalNotes, that.additionalNotes) && Objects.equals(reminderTime, that.reminderTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, notificationId, notificationTitle, reminderType, reminderContent, additionalNotes, reminderTime);
    }
}
