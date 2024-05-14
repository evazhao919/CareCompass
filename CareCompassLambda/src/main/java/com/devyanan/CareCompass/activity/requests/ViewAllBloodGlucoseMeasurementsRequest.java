package com.devyanan.CareCompass.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = ViewAllBloodGlucoseMeasurementsRequest.Builder.class)
public class ViewAllBloodGlucoseMeasurementsRequest {
    private final String userId;

    public ViewAllBloodGlucoseMeasurementsRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "GetUserBloodGlucoseMeasurementRequest{" +
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
            this.userId= userId;
            return this;
        }
        public ViewAllBloodGlucoseMeasurementsRequest build() {
            return new ViewAllBloodGlucoseMeasurementsRequest(userId);
        }
    }
}
