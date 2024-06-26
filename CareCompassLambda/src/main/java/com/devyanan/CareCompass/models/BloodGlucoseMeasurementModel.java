package com.devyanan.CareCompass.models;

import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;

import java.util.Objects;

/**
 * Represents a model for blood glucose measurements.
 */
public class BloodGlucoseMeasurementModel {
    private final String patientId;
    private final String actualCheckTime;
    private final double glucoseLevel;
    private final BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT glucoseContext;
    private final String comments;

    /**
     * Private constructor for the BloodGlucoseMeasurementModel.
     * Use the Builder pattern to construct instances of this class.
     */
    private BloodGlucoseMeasurementModel(String patientId, String actualCheckTime, double glucoseLevel, BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT glucoseContext, String comments) {
        this.patientId = patientId;
        this.actualCheckTime = actualCheckTime;
        this.glucoseLevel = glucoseLevel;
        this.glucoseContext = glucoseContext;
        this.comments = comments;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getActualCheckTime() {
        return actualCheckTime;
    }

    public double getGlucoseLevel() {
        return glucoseLevel;
    }

    public BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT getGlucoseContext() {
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

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String patientId;
        private String actualCheckTime;
        private double glucoseLevel;
        private BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT glucoseContext;
        private String comments;

        public Builder withPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder withActualCheckTime(String actualCheckTime) {
            this.actualCheckTime = actualCheckTime;
            return this;
        }

        public Builder withGlucoseLevel(double glucoseLevel) {
            this.glucoseLevel = glucoseLevel;
            return this;
        }

        public Builder withGlucoseContext(BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT glucoseContext) {
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