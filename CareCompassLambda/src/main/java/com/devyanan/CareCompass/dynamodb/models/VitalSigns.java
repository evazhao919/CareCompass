package com.devyanan.CareCompass.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents vital signs data stored in DynamoDB.
 */
@DynamoDBTable(tableName = "vitalSigns")
public class VitalSigns {
    private String patientId;
    private LocalDateTime actualCheckTime;
    private double temperature;
    private int heartRate;
    private int pulse;
    private int respiratoryRate;
    private int systolicPressure;
    private int diastolicPressure;
    private int meanArterialPressure;
    private double weight;
    private PATIENT_POSITION patientPosition;
    private int bloodOxygenLevel;
    private OXYGEN_THERAPY oxygenTherapy;
    private FLOW_DELIVERED  flowDelivered;
    private PATIENT_ACTIVITY patientActivity;
    private String comments;

    public enum PATIENT_POSITION {
        SITTING, STANDING, LAYING
    }
    public enum OXYGEN_THERAPY {
        NONE, NASAL_CANNULA, SIMPLE_FACE_MASK, NON_REBREATHER_MASK, VENTURI_MASK, HIGH_FLOW_NASAL_CANNULA
    }
    public enum FLOW_DELIVERED {
        NONE, LOW_FLOW, MEDIUM_FLOW, HIGH_FLOW
    }
    public enum PATIENT_ACTIVITY {
        SITTING, STANDING, LAYING_DOWN, POST_EXERCISE,
    }
    @DynamoDBHashKey(attributeName = "patientId")
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    @DynamoDBRangeKey(attributeName = "actualCheckTime")
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    public LocalDateTime getActualCheckTime() {
        return actualCheckTime;
    }

    public void setActualCheckTime(LocalDateTime actualCheckTime) {
        this.actualCheckTime = actualCheckTime;
    }

    @DynamoDBAttribute(attributeName = "temperature")
    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    @DynamoDBAttribute(attributeName = "heartRate")
    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }
    @DynamoDBAttribute(attributeName = "pulse")
    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }
    @DynamoDBAttribute(attributeName = "respiratoryRate")
    public int getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(int respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }
    @DynamoDBAttribute(attributeName = "systolicPressure")
    public int getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(int systolicPressure) {
        this.systolicPressure = systolicPressure;
    }
    @DynamoDBAttribute(attributeName = "diastolicPressure")
    public int getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(int diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }
    @DynamoDBAttribute(attributeName = "meanArterialPressure")
    public int getMeanArterialPressure() {
        return meanArterialPressure;
    }

    public void setMeanArterialPressure(int meanArterialPressure) {
        this.meanArterialPressure = meanArterialPressure;
    }

    @DynamoDBAttribute(attributeName = "weight")
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "patientPosition")
    public PATIENT_POSITION getPatientPosition() {
        return patientPosition;
    }

    public void setPatientPosition(PATIENT_POSITION patientPosition) {
        this.patientPosition = patientPosition;
    }

    @DynamoDBAttribute(attributeName = "bloodOxygenLevel")
    public int getBloodOxygenLevel() {
        return bloodOxygenLevel;
    }

    public void setBloodOxygenLevel(int bloodOxygenLevel) {
        this.bloodOxygenLevel = bloodOxygenLevel;
    }
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "oxygenTherapy")
    public OXYGEN_THERAPY getOxygenTherapy() {
        return oxygenTherapy;
    }

    public void setOxygenTherapy(OXYGEN_THERAPY oxygenTherapy) {
        this.oxygenTherapy = oxygenTherapy;
    }
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "flowDelivered")
    public FLOW_DELIVERED  getFlowDelivered() {
        return flowDelivered;
    }

    public void setFlowDelivered(FLOW_DELIVERED  flowDelivered) {
        this.flowDelivered = flowDelivered;
    }
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "patientActivity")
    public PATIENT_ACTIVITY getPatientActivity() {
        return patientActivity;
    }

    public void setPatientActivity(PATIENT_ACTIVITY patientActivity) {
        this.patientActivity = patientActivity;
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
        VitalSigns that = (VitalSigns) o;
        return Double.compare(temperature, that.temperature) == 0 && heartRate == that.heartRate && pulse == that.pulse && respiratoryRate == that.respiratoryRate && systolicPressure == that.systolicPressure && diastolicPressure == that.diastolicPressure && meanArterialPressure == that.meanArterialPressure && Double.compare(weight, that.weight) == 0 && bloodOxygenLevel == that.bloodOxygenLevel && Objects.equals(patientId, that.patientId) && Objects.equals(actualCheckTime, that.actualCheckTime) && patientPosition == that.patientPosition && oxygenTherapy == that.oxygenTherapy && flowDelivered == that.flowDelivered && patientActivity == that.patientActivity && Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, actualCheckTime, temperature, heartRate, pulse, respiratoryRate, systolicPressure, diastolicPressure, meanArterialPressure, weight, patientPosition, bloodOxygenLevel, oxygenTherapy, flowDelivered, patientActivity, comments);
    }
}