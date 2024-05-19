package com.devyanan.CareCompass.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;

import java.time.LocalDateTime;
import java.util.Objects;

@DynamoDBTable(tableName = "bloodGlucoseMeasurements")
public class BloodGlucoseMeasurement {
    private String patientId;
    private LocalDateTime actualCheckTime;
    private double glucoseLevel;
    private GlucoseMeasurementContext glucoseContext;
    private String comments;

    public enum GlucoseMeasurementContext {
        FASTING, BEFORE_MEAL, AFTER_MEAL, BEDTIME;
    }

    @DynamoDBHashKey(attributeName = "patientId")
    @DynamoDBIndexHashKey(globalSecondaryIndexNames = {"bloodGlucoseMeasurementsIndex"}, attributeName = "patientId")
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    @DynamoDBRangeKey(attributeName = "actualCheckTime")
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "bloodGlucoseMeasurementsIndex", attributeName = "actualCheckTime")
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    public LocalDateTime getActualCheckTime() {
        return actualCheckTime;
    }

    public void setActualCheckTime(LocalDateTime actualCheckTime) {
        this.actualCheckTime = actualCheckTime;
    }

    @DynamoDBAttribute(attributeName = "glucoseContext")
    public GlucoseMeasurementContext getGlucoseContext() {
        return glucoseContext;
    }

    public void setGlucoseContext(GlucoseMeasurementContext glucoseContext) {
        this.glucoseContext = glucoseContext;
    }

    @DynamoDBAttribute(attributeName = "glucoseLevel")
    public double getGlucoseLevel() {
        return glucoseLevel;
    }

    public void setGlucoseLevel(double glucoseLevel) {
        this.glucoseLevel = glucoseLevel;
    }

    @DynamoDBAttribute(attributeName = "comments")
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BloodGlucoseMeasurement that = (BloodGlucoseMeasurement) o;
        return Double.compare(glucoseLevel, that.glucoseLevel) == 0 && Objects.equals(patientId, that.patientId) && Objects.equals(actualCheckTime, that.actualCheckTime) && glucoseContext == that.glucoseContext && Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, actualCheckTime, glucoseLevel, glucoseContext, comments);
    }
}