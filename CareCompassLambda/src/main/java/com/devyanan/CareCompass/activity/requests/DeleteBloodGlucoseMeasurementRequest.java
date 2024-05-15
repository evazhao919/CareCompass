package com.devyanan.CareCompass.activity.requests;

import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDateTime;
@JsonDeserialize(builder = DeleteBloodGlucoseMeasurementRequest.Builder.class)
public class DeleteBloodGlucoseMeasurementRequest {
    private final String userId;
    private final LocalDateTime measurementTime;
    private final double glucoseLevel;
    private final BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext;

    public DeleteBloodGlucoseMeasurementRequest(String userId, LocalDateTime measurementTime, double glucoseLevel, BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext) {
        this.userId = userId;
        this.measurementTime = measurementTime;
        this.glucoseLevel = glucoseLevel;
        this.glucoseContext = glucoseContext;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getMeasurementTime() {
        return measurementTime;
    }

    public double getGlucoseLevel() {
        return glucoseLevel;
    }

    public BloodGlucoseMeasurement.GlucoseMeasurementContext getGlucoseContext() {
        return glucoseContext;
    }
    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private LocalDateTime measurementTime;
        private double glucoseLevel;
        private BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext;

        public DeleteBloodGlucoseMeasurementRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withMeasurementTime(LocalDateTime measurementTime) {
            this.measurementTime = measurementTime;
            return this;
        }

        public Builder withGlucoseLevel(double glucoseLevel) {
            this.glucoseLevel = glucoseLevel;
            return this;
        }

        public Builder withGlucoseContext(BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext) {
            this.glucoseContext = glucoseContext;
            return this;
        }

        public DeleteBloodGlucoseMeasurementRequest build() {
            return new DeleteBloodGlucoseMeasurementRequest(userId, measurementTime, glucoseLevel, glucoseContext);
        }
}
}
