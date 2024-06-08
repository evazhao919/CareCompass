package com.devyanan.CareCompass.activity.requests;

import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UpdateBloodGlucoseMeasurementRequest.Builder.class)
public class UpdateBloodGlucoseMeasurementRequest {
    private final String patientId;
    private final String actualCheckTime;
    private final double glucoseLevel;
    private final BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT glucoseContext;
    private final String comments;

    public UpdateBloodGlucoseMeasurementRequest(String patientId, String actualCheckTime, double glucoseLevel, BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT glucoseContext, String comments) {
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
    public String toString() {
        return "UpdateBloodGlucoseMeasurementRequest{" +
                "patientId='" + patientId + '\'' +
                ", actualCheckTime=" + actualCheckTime +
                ", glucoseLevel=" + glucoseLevel +
                ", glucoseContext=" + glucoseContext +
                ", comments='" + comments + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
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

        public UpdateBloodGlucoseMeasurementRequest build() {
            return new UpdateBloodGlucoseMeasurementRequest(patientId, actualCheckTime, glucoseLevel, glucoseContext, comments);
        }
    }
}
