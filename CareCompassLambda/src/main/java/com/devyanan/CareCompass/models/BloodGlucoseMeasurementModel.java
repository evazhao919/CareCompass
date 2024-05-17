package com.devyanan.CareCompass.models;

import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;

import java.time.LocalTime;
import java.util.Objects;

public class BloodGlucoseMeasurementModel {
    private final String patientId;
    private final String frequency;
    private final LocalTime actualCheckTime;
    private final double glucoseLevel;
    private final BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext;
    private final String comments;

    public BloodGlucoseMeasurementModel(String patientId, String frequency, LocalTime actualCheckTime, double glucoseLevel, BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext, String comments) {
        this.patientId = patientId;
        this.frequency = frequency;
        this.actualCheckTime = actualCheckTime;
        this.glucoseLevel = glucoseLevel;
        this.glucoseContext = glucoseContext;
        this.comments = comments;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getFrequency() {
        return frequency;
    }

    public LocalTime getActualCheckTime() {
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
        return Double.compare(glucoseLevel, that.glucoseLevel) == 0 && Objects.equals(patientId, that.patientId) && Objects.equals(frequency, that.frequency) && Objects.equals(actualCheckTime, that.actualCheckTime) && glucoseContext == that.glucoseContext && Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, frequency, actualCheckTime, glucoseLevel, glucoseContext, comments);
    }

    public static class Builder {
        private String patientId;
        private LocalTime actualCheckTime;
        private String frequency;
        private double glucoseLevel;
        private BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext;
        private String comments;

        public Builder withPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder withActualCheckTime(LocalTime actualCheckTime) {
            this.actualCheckTime = actualCheckTime;
            return this;
        }
        public Builder withFrequency(String frequency) {
            this.frequency = frequency;
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
            return new BloodGlucoseMeasurementModel(patientId, frequency,actualCheckTime, glucoseLevel, glucoseContext, comments);
        }
    }
}
