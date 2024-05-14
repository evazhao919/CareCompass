package com.devyanan.CareCompass.activity.results;

import com.devyanan.CareCompass.models.NotificationModel;

import java.util.List;

public class ViewAllNotificationsResult {
    private final List<NotificationModel> notificationModelList;

    public ViewAllNotificationsResult(List<NotificationModel> notificationModelList) {
        this.notificationModelList = notificationModelList;
    }

    public List<NotificationModel> getNotificationModelList() {
        return notificationModelList;
    }

    @Override
    public String toString() {
        return "ViewAllNotificationsResult{" +
                "notificationModelList=" + notificationModelList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<NotificationModel> notificationModelList;
        public Builder withNotificationModelList(List<NotificationModel> notificationModelList){
            this.notificationModelList = notificationModelList;
            return this;
        }
        public ViewAllNotificationsResult build(){
            return new ViewAllNotificationsResult (notificationModelList);
        }
    }
}
