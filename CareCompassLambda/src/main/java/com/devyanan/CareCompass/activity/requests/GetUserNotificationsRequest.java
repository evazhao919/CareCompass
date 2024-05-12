package com.devyanan.CareCompass.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetUserNotificationsRequest.Builder.class)
public class GetUserNotificationsRequest{
    private final String userId;

    public GetUserNotificationsRequest(String userId) {
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
        public GetUserNotificationsRequest build() {
            return new GetUserNotificationsRequest(userId);
        }
    }
}