package com.devyanan.CareCompass.activity.requests;

import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@JsonDeserialize(builder = AddBloodGlucoseMeasurementRequest.Builder.class)
public class AddBloodGlucoseMeasurementRequest {
    private final String patientId;
    private final LocalTime actualCheckTime;
    private final double glucoseLevel;
    private final BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext;
    private final String comments;

    public AddBloodGlucoseMeasurementRequest(String patientId, LocalTime actualCheckTime, double glucoseLevel, BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext, String comments) {
        this.patientId = patientId;
        this.actualCheckTime = actualCheckTime;
        this.glucoseLevel = glucoseLevel;
        this.glucoseContext = glucoseContext;
        this.comments = comments;
    }

    public String getPatientId() {
        return patientId;
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
        return "AddBloodGlucoseMeasurementRequest{" +
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
        private LocalTime actualCheckTime;
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

        public AddBloodGlucoseMeasurementRequest build() {
            return new AddBloodGlucoseMeasurementRequest(patientId, actualCheckTime, glucoseLevel, glucoseContext, comments);
        }
    }
    }
