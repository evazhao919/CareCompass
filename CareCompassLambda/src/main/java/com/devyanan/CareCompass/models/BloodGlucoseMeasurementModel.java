package com.devyanan.CareCompass.models;

import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;

import java.time.LocalDateTime;
import java.util.Objects;

public class BloodGlucoseMeasurementModel {
    private final String userId;
    private final LocalDateTime measurementTime;
    private final double glucoseLevel;
    private final BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext;
    private final String comments;

    public BloodGlucoseMeasurementModel(String userId, LocalDateTime measurementTime, double glucoseLevel, BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext, String comments) {
        this.userId = userId;
        this.measurementTime = measurementTime;
        this.glucoseLevel = glucoseLevel;
        this.glucoseContext = glucoseContext;
        this.comments = comments;
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

    public String getComments() {
        return comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BloodGlucoseMeasurementModel that = (BloodGlucoseMeasurementModel) o;
        return Double.compare(glucoseLevel, that.glucoseLevel) == 0 && Objects.equals(userId, that.userId) && Objects.equals(measurementTime, that.measurementTime) && glucoseContext == that.glucoseContext && Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, measurementTime, glucoseLevel, glucoseContext, comments);
    }

    public static class Builder {
        private String userId;
        private LocalDateTime measurementTime;
        private double glucoseLevel;
        private BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext;
        private String comments;
        public Builder withUserId(String userId) {
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

        public Builder withComments(String comments) {
            this.comments = comments;
            return this;
        }
        public BloodGlucoseMeasurementModel build() {
            return new BloodGlucoseMeasurementModel(userId, measurementTime, glucoseLevel, glucoseContext, comments);
        }
    }
}
