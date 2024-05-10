package com.devyanan.CareCompass.dynamodb.models;

import java.time.LocalDateTime;
import java.util.Objects;
public class notification {
    private String notificationId;
    private String userId;
    private String reminder;
    private String additionalInfo;
    private LocalDateTime reminderTime;

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

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
        notification that = (notification) o;
        return Objects.equals(notificationId, that.notificationId) && Objects.equals(userId, that.userId) && Objects.equals(reminder, that.reminder) && Objects.equals(additionalInfo, that.additionalInfo) && Objects.equals(reminderTime, that.reminderTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationId, userId, reminder, additionalInfo, reminderTime);
    }

    @Override
    public String toString() {
        return "notification{" +
                "notificationId='" + notificationId + '\'' +
                ", userId='" + userId + '\'' +
                ", reminder='" + reminder + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", reminderTime=" + reminderTime +
                '}';
    }
}
