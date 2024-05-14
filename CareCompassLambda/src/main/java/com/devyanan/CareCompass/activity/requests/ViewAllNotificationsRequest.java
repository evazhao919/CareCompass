package com.devyanan.CareCompass.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = ViewAllNotificationsRequest.Builder.class)
public class ViewAllNotificationsRequest {
    private final String userId;

    public ViewAllNotificationsRequest(String userId) {
        this.userId = userId;
    }
    @Override
    public String toString() {
        return "GetUserNotificationsRequest" +
                "userId='" + userId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public ViewAllNotificationsRequest build() {
            return new ViewAllNotificationsRequest(userId);
        }
    }
}