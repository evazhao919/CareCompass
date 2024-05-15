package com.devyanan.CareCompass.activity.requests;

import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDateTime;
@JsonDeserialize(builder = DeleteNotificationRequest.Builder.class)
public class DeleteNotificationRequest {
    private final String userId;
    private final String notificationId;
    private final String notificationTitle;
    private final Notification.ReminderType reminderType;
    private final String reminderContent;
    private final String additionalNotes;
    private final LocalDateTime reminderTime;

    public DeleteNotificationRequest(String userId, String notificationId, String notificationTitle, Notification.ReminderType reminderType, String reminderContent, String additionalNotes, LocalDateTime reminderTime) {
        this.userId = userId;
        this.notificationId = notificationId;
        this.notificationTitle = notificationTitle;
        this.reminderType = reminderType;
        this.reminderContent = reminderContent;
        this.additionalNotes = additionalNotes;
        this.reminderTime = reminderTime;
    }

    @Override
    public String toString() {
        return "RemoveNotificationRequest{" +
                "userId='" + userId + '\'' +
                ", notificationId='" + notificationId + '\'' +
                ", notificationTitle='" + notificationTitle + '\'' +
                ", reminderType=" + reminderType +
                ", reminderContent='" + reminderContent + '\'' +
                ", additionalNotes='" + additionalNotes + '\'' +
                ", reminderTime=" + reminderTime +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static DeleteNotificationRequest.Builder builder() {
        return new DeleteNotificationRequest.Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private String notificationId;
        private String notificationTitle;
        private Notification.ReminderType reminderType;
        private String reminderContent;
        private String additionalNotes;
        private LocalDateTime reminderTime;
        public Builder withUserId(String userId) {
            this.userId = userId;
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
        public DeleteNotificationRequest build() {
            return new DeleteNotificationRequest(userId, notificationId, notificationTitle, reminderType, reminderContent, additionalNotes, reminderTime);
        }
    }
}
