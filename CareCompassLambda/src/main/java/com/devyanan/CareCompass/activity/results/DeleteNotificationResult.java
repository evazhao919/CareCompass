package com.devyanan.CareCompass.activity.results;

import com.devyanan.CareCompass.models.NotificationModel;

public class DeleteNotificationResult {
    private final NotificationModel notificationModel;

    public DeleteNotificationResult(NotificationModel notificationModel) {
        this.notificationModel = notificationModel;
    }

    public NotificationModel getNotificationModel() {
        return notificationModel;
    }

    @Override
    public String toString() {
        return "DeleteNotificationResult{" +
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

        public DeleteNotificationResult build() {
            return new DeleteNotificationResult(notificationModel);
        }
    }
}
