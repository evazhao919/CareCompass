package com.devyanan.CareCompass.activity.results;

import com.devyanan.CareCompass.models.NotificationModel;

public class UpdateNotificationsResult {
    private final NotificationModel notificationModel;

    public UpdateNotificationsResult(NotificationModel notificationModel) {
        this.notificationModel = notificationModel;
    }

    public NotificationModel getNotificationModel() {
        return notificationModel;
    }

    @Override
    public String toString() {
        return "UpdateNotificationsResult{" +
                "notificationModel=" + notificationModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private NotificationModel notificationModel;

        public Builder withNotificationModel(NotificationModel notificationModel) {
            this.notificationModel = notificationModel;
            return this;
        }

        public UpdateNotificationsResult build() {
            return new UpdateNotificationsResult(notificationModel);
        }
    }
}
