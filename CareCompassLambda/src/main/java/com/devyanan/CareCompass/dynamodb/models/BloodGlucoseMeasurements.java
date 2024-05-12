package com.devyanan.CareCompass.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.time.LocalDateTime;
import java.util.Objects;

@DynamoDBTable(tableName = "BloodGlucoseMeasurements")
public class BloodGlucoseMeasurements {
    private String userId;
    private LocalDateTime measurementTime;
    private double glucoseLevel;
    private GlucoseMeasurementContext glucoseContext;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BloodGlucoseMeasurements that = (BloodGlucoseMeasurements) o;
        return Double.compare(glucoseLevel, that.glucoseLevel) == 0 && Objects.equals(userId, that.userId) && Objects.equals(measurementTime, that.measurementTime) && glucoseContext == that.glucoseContext;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, measurementTime, glucoseLevel, glucoseContext);
    }
}
