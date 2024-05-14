package com.devyanan.CareCompass.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = ViewAllMedicationsRequest.Builder.class)
public class ViewAllMedicationsRequest {
    private final String userId;

    public ViewAllMedicationsRequest(String userId) {
        this.userId = userId;
    }
    @Override
    public String toString() {
        return "GetAllMedicationsRequest{" +
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
        public ViewAllMedicationsRequest build() {
            return new ViewAllMedicationsRequest(userId);
        }
    }
}
