package com.devyanan.CareCompass.activity.results;

import com.devyanan.CareCompass.models.NotificationModel;

import java.util.List;

public class GetAllNotificationsResult {
    private final List<NotificationModel> notificationModelList;

    public GetAllNotificationsResult(List<NotificationModel> notificationModelList) {
        this.notificationModelList = notificationModelList;
    }

    public List<NotificationModel> getNotificationModelList() {
        return notificationModelList;
    }

    @Override
    public String toString() {
        return "GetAllNotificationsResult{" +
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
        public GetAllNotificationsResult build(){
            return new GetAllNotificationsResult(notificationModelList);
        }
    }
}
