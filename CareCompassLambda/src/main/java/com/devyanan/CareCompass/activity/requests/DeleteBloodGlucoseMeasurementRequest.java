package com.devyanan.CareCompass.activity.requests;

import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalTime;

@JsonDeserialize(builder = DeleteBloodGlucoseMeasurementRequest.Builder.class)
public class DeleteBloodGlucoseMeasurementRequest {
    private final String patientId;
    private final String frequency;
    private final LocalTime actualCheckTime;
    private final double glucoseLevel;
    private final BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext;
    private final String comments;

    public DeleteBloodGlucoseMeasurementRequest(String patientId, String frequency, LocalTime actualCheckTime, double glucoseLevel, BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext, String comments) {
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
    public String toString() {
        return "DeleteBloodGlucoseMeasurementRequest{" +
                "patientId='" + patientId + '\'' +
                ", frequency='" + frequency + '\'' +
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
        private String frequency;
        private LocalTime actualCheckTime;
        private double glucoseLevel;
        private BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext;
        private String comments;
        public Builder withPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder withFrequency(String frequency) {
            this.frequency = frequency;
            return this;
        }

        public Builder withActualCheckTime(LocalTime actualCheckTime) {
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
        public DeleteBloodGlucoseMeasurementRequest build() {
            return new DeleteBloodGlucoseMeasurementRequest(patientId, frequency, actualCheckTime, glucoseLevel, glucoseContext, comments);
        }
}
}
