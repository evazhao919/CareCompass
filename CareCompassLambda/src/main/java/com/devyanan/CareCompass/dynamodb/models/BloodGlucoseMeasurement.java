package com.devyanan.CareCompass.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.time.LocalDateTime;
import java.util.Objects;

@DynamoDBTable(tableName = "bloodGlucoseMeasurements")
public class BloodGlucoseMeasurement {
    private String userId;
    private LocalDateTime measurementTime;
    private double glucoseLevel;
    private GlucoseMeasurementContext glucoseContext;
    private String comments;
    public enum GlucoseMeasurementContext {
        FASTING, BEFORE_MEAL, AFTER_MEAL, BEDTIME;
    }
    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    @DynamoDBRangeKey(attributeName = "measurementTime")
    public LocalDateTime getMeasurementTime() {
        return measurementTime;
    }

    public void setMeasurementTime(LocalDateTime measurementTime) {
        this.measurementTime = measurementTime;
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
        return Double.compare(glucoseLevel, that.glucoseLevel) == 0 && Objects.equals(userId, that.userId) && Objects.equals(measurementTime, that.measurementTime) && glucoseContext == that.glucoseContext && Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, measurementTime, glucoseLevel, glucoseContext, comments);
    }
}
