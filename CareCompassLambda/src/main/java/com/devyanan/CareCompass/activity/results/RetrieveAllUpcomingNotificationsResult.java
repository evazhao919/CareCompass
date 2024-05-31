package com.devyanan.CareCompass.activity.results;

import com.devyanan.CareCompass.models.NotificationModel;
import java.util.List;

public class RetrieveAllUpcomingNotificationsResult {
    private final List<NotificationModel> notifications;

    public RetrieveAllUpcomingNotificationsResult(List<NotificationModel> notifications) {
        this.notifications = notifications;
    }

    public List<NotificationModel> getNotifications() {
        return notifications;
    }

    @Override
    public String toString() {
        return "RetrieveAllUpcomingNotificationsResult{" +
                "notifications=" + notifications +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private List<NotificationModel> notifications;
        public Builder withNotifications(List<NotificationModel> notifications) {
            this.notifications = notifications;
            return this;
        }
        public RetrieveAllUpcomingNotificationsResult build() {
            return new RetrieveAllUpcomingNotificationsResult(notifications);
        }
    }
}

