package com.devyanan.CareCompass.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;

import java.time.LocalDateTime;
import java.util.Objects;

@DynamoDBTable(tableName = "vitalSigns")
public class VitalSigns {
    private String userId;
    private LocalDateTime actualCheckTime;
    private LocalDateTime scheduledTime;
    private LocalDateTime timeAdded;
    private double temperature;
    private int heartRate;
    private int pulse;
    private int respiratoryRate;
    private int systolicPressure;
    private int diastolicPressure;
    private int meanArterialPressure;
    private double weight;
    private PatientPosition patientPosition;
    private int bloodOxygenLevel;
    private OxygenTherapy oxygenTherapy;
    private FlowDelivered flowDelivered;
    private PatientActivity patientActivity;

    private String additionalNotes;
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

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBIndexHashKey(globalSecondaryIndexNames = {"medicationNameIndex", "vitalSignsTrackingIndex","userNotificationsIndex"}, attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    @DynamoDBRangeKey(attributeName = "actualCheckTime")
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "vitalSignsTrackingIndex", attributeName = "actualCheckTime")
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    public LocalDateTime getActualCheckTime() {
        return actualCheckTime;
    }

    public void setActualCheckTime(LocalDateTime actualCheckTime) {
        this.actualCheckTime = actualCheckTime;
    }
    @DynamoDBAttribute(attributeName = "scheduledTime")
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
    @DynamoDBAttribute(attributeName = "timeAdded")
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    public LocalDateTime getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(LocalDateTime timeAdded) {
        this.timeAdded = timeAdded;
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
    public PatientPosition  getPatientPosition() {
        return patientPosition;
    }

    public void setPatientPosition(PatientPosition  patientPosition) {
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
    public OxygenTherapy getOxygenTherapy() {
        return oxygenTherapy;
    }

    public void setOxygenTherapy(OxygenTherapy oxygenTherapy) {
        this.oxygenTherapy = oxygenTherapy;
    }
    @DynamoDBAttribute(attributeName = "flowDelivered")
    public FlowDelivered getFlowDelivered() {
        return flowDelivered;
    }

    public void setFlowDelivered(FlowDelivered flowDelivered) {
        this.flowDelivered = flowDelivered;
    }
    @DynamoDBAttribute(attributeName = "patientActivity")
    public  PatientActivity getPatientActivity() {
        return patientActivity;
    }

    public void setPatientActivity( PatientActivity patientActivity) {
        this.patientActivity = patientActivity;
    }

    @DynamoDBAttribute(attributeName = "additionalNotes")
    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        if (additionalNotes == null || additionalNotes.equals("")) {
            this.additionalNotes = "";
        } else {
            this.additionalNotes = additionalNotes;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VitalSigns that = (VitalSigns) o;
        return Double.compare(temperature, that.temperature) == 0 && heartRate == that.heartRate && pulse == that.pulse && respiratoryRate == that.respiratoryRate && systolicPressure == that.systolicPressure && diastolicPressure == that.diastolicPressure && meanArterialPressure == that.meanArterialPressure && Double.compare(weight, that.weight) == 0 && bloodOxygenLevel == that.bloodOxygenLevel &&  Objects.equals(userId, that.userId) && Objects.equals(actualCheckTime, that.actualCheckTime) && Objects.equals(scheduledTime, that.scheduledTime) && Objects.equals(timeAdded, that.timeAdded) && patientPosition == that.patientPosition && oxygenTherapy == that.oxygenTherapy && flowDelivered == that.flowDelivered && patientActivity == that.patientActivity && Objects.equals(additionalNotes, that.additionalNotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, actualCheckTime, scheduledTime, timeAdded, temperature, heartRate, pulse, respiratoryRate, systolicPressure, diastolicPressure, meanArterialPressure, weight, patientPosition, bloodOxygenLevel, oxygenTherapy, flowDelivered, patientActivity, additionalNotes);
    }

    @Override
    public String toString() {
        return "VitalSigns{" +
                "userId='" + userId + '\'' +
                ", actualCheckTime=" + actualCheckTime +
                ", scheduledTime=" + scheduledTime +
                ", timeAdded=" + timeAdded +
                ", temperature=" + temperature +
                ", heartRate=" + heartRate +
                ", pulse=" + pulse +
                ", respiratoryRate=" + respiratoryRate +
                ", systolicPressure=" + systolicPressure +
                ", diastolicPressure=" + diastolicPressure +
                ", meanArterialPressure=" + meanArterialPressure +
                ", weight=" + weight +
                ", patientPosition=" + patientPosition +
                ", bloodOxygenLevel=" + bloodOxygenLevel +
                ", oxygenTherapy=" + oxygenTherapy +
                ", flowDelivered=" + flowDelivered +
                ", patientActivity=" + patientActivity +
                ", additionalNotes='" + additionalNotes + '\'' +
                '}';
    }
}
