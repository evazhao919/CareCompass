package com.devyanan.CareCompass.models;

import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;

import java.time.LocalDateTime;
import java.util.Objects;

public class BloodGlucoseMeasurementModel {
    private final String patientId;
    private final LocalDateTime actualCheckTime;
    private final double glucoseLevel;
    private final BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext;
    private final String comments;

    public BloodGlucoseMeasurementModel(String patientId, LocalDateTime actualCheckTime, double glucoseLevel, BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext, String comments) {
        this.patientId = patientId;
        this.actualCheckTime = actualCheckTime;
        this.glucoseLevel = glucoseLevel;
        this.glucoseContext = glucoseContext;
        this.comments = comments;
    }

    public String getPatientId() {
        return patientId;
    }

    public LocalDateTime getActualCheckTime() {
        return actualCheckTime;
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
        return Double.compare(glucoseLevel, that.glucoseLevel) == 0 && Objects.equals(patientId, that.patientId) && Objects.equals(actualCheckTime, that.actualCheckTime) && glucoseContext == that.glucoseContext && Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, actualCheckTime, glucoseLevel, glucoseContext, comments);
    }

    public static class Builder {
        private String patientId;
        private LocalDateTime actualCheckTime;
        private double glucoseLevel;
        private BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext;
        private String comments;

        public Builder withPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder withActualCheckTime(LocalDateTime actualCheckTime) {
            this.actualCheckTime = actualCheckTime;
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
            return new BloodGlucoseMeasurementModel(patientId, actualCheckTime, glucoseLevel, glucoseContext, comments);
        }
    }
}
