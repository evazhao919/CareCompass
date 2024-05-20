package com.devyanan.CareCompass.activity.requests;

import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = DeleteBloodGlucoseMeasurementRequest.Builder.class)
public class DeleteBloodGlucoseMeasurementRequest {
    private final String patientId;
    private final String actualCheckTime;
    private final double glucoseLevel;
    private final BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext;
    private final String comments;

    public DeleteBloodGlucoseMeasurementRequest(String patientId, String actualCheckTime, double glucoseLevel, BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext, String comments) {
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
        private BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext;
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

        public Builder withGlucoseContext(BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext) {
            this.glucoseContext = glucoseContext;
            return this;
        }

        public Builder withComments(String comments) {
            this.comments = comments;
            return this;
        }
        public DeleteBloodGlucoseMeasurementRequest build() {
            return new DeleteBloodGlucoseMeasurementRequest(patientId, actualCheckTime, glucoseLevel, glucoseContext, comments);
        }
}
}
