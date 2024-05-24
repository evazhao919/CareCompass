package com.devyanan.CareCompass.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;

import java.time.LocalDateTime;
import java.util.Objects;

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
    private String patientPosition;
    private int bloodOxygenLevel;
    private String oxygenTherapy;
    private String flowDelivered;
    private String patientActivity;
    private String comments;

    public enum PatientPosition {
        SUPINE, PRONE, LEFT_LATERAL, RIGHT_LATERAL, SITTING, STANDING
    }
    public enum OxygenTherapy {
        NASAL_CANNULA, SIMPLE_FACE_MASK, NON_REBREATHER_MASK, VENTURI_MASK, HIGH_FLOW_NASAL_CANNULA
    }
    public enum FlowDelivered {
        LOW_FLOW, MEDIUM_FLOW, HIGH_FLOW
    }
    public enum PatientActivity {
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
    @DynamoDBAttribute(attributeName = "patientPosition")
    public String getPatientPosition() {
        return patientPosition;
    }

    public void setPatientPosition(String patientPosition) {
        this.patientPosition = patientPosition;
    }
    @DynamoDBAttribute(attributeName = "bloodOxygenLevel")
    public int getBloodOxygenLevel() {
        return bloodOxygenLevel;
    }

    public void setBloodOxygenLevel(int bloodOxygenLevel) {
        this.bloodOxygenLevel = bloodOxygenLevel;
    }
    @DynamoDBAttribute(attributeName = "oxygenTherapy")
    public String getOxygenTherapy() {
        return oxygenTherapy;
    }

    public void setOxygenTherapy(String oxygenTherapy) {
        this.oxygenTherapy = oxygenTherapy;
    }
    @DynamoDBAttribute(attributeName = "flowDelivered")
    public String getFlowDelivered() {
        return flowDelivered;
    }

    public void setFlowDelivered(String flowDelivered) {
        this.flowDelivered = flowDelivered;
    }
    @DynamoDBAttribute(attributeName = "patientActivity")
    public String getPatientActivity() {
        return patientActivity;
    }

    public void setPatientActivity(String patientActivity) {
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
        return Double.compare(temperature, that.temperature) == 0 && heartRate == that.heartRate && pulse == that.pulse && respiratoryRate == that.respiratoryRate && systolicPressure == that.systolicPressure && diastolicPressure == that.diastolicPressure && meanArterialPressure == that.meanArterialPressure && Double.compare(weight, that.weight) == 0 && bloodOxygenLevel == that.bloodOxygenLevel && Objects.equals(patientId, that.patientId) && Objects.equals(actualCheckTime, that.actualCheckTime) && Objects.equals(patientPosition, that.patientPosition) && Objects.equals(oxygenTherapy, that.oxygenTherapy) && Objects.equals(flowDelivered, that.flowDelivered) && Objects.equals(patientActivity, that.patientActivity) && Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, actualCheckTime, temperature, heartRate, pulse, respiratoryRate, systolicPressure, diastolicPressure, meanArterialPressure, weight, patientPosition, bloodOxygenLevel, oxygenTherapy, flowDelivered, patientActivity, comments);
    }
}
