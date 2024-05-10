package com.devyanan.CareCompass.dynamodb.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class vitalSigns {
   private String vitalSignId;
   private String userId;
   private LocalDateTime timestamp;
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
   private String additionalNotes;

    public String getVitalSignId() {
        return vitalSignId;
    }

    public void setVitalSignId(String vitalSignId) {
        this.vitalSignId = vitalSignId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public int getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(int respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    public int getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(int systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public int getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(int diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public int getMeanArterialPressure() {
        return meanArterialPressure;
    }

    public void setMeanArterialPressure(int meanArterialPressure) {
        this.meanArterialPressure = meanArterialPressure;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getPatientPosition() {
        return patientPosition;
    }

    public void setPatientPosition(String patientPosition) {
        this.patientPosition = patientPosition;
    }

    public int getBloodOxygenLevel() {
        return bloodOxygenLevel;
    }

    public void setBloodOxygenLevel(int bloodOxygenLevel) {
        this.bloodOxygenLevel = bloodOxygenLevel;
    }

    public String getOxygenTherapy() {
        return oxygenTherapy;
    }

    public void setOxygenTherapy(String oxygenTherapy) {
        this.oxygenTherapy = oxygenTherapy;
    }

    public String getFlowDelivered() {
        return flowDelivered;
    }

    public void setFlowDelivered(String flowDelivered) {
        this.flowDelivered = flowDelivered;
    }

    public String getPatientActivity() {
        return patientActivity;
    }

    public void setPatientActivity(String patientActivity) {
        this.patientActivity = patientActivity;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        vitalSigns that = (vitalSigns) o;
        return Double.compare(temperature, that.temperature) == 0 && heartRate == that.heartRate && pulse == that.pulse && respiratoryRate == that.respiratoryRate && systolicPressure == that.systolicPressure && diastolicPressure == that.diastolicPressure && meanArterialPressure == that.meanArterialPressure && Double.compare(weight, that.weight) == 0 && bloodOxygenLevel == that.bloodOxygenLevel && Objects.equals(vitalSignId, that.vitalSignId) && Objects.equals(userId, that.userId) && Objects.equals(timestamp, that.timestamp) && Objects.equals(patientPosition, that.patientPosition) && Objects.equals(oxygenTherapy, that.oxygenTherapy) && Objects.equals(flowDelivered, that.flowDelivered) && Objects.equals(patientActivity, that.patientActivity) && Objects.equals(additionalNotes, that.additionalNotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vitalSignId, userId, timestamp, temperature, heartRate, pulse, respiratoryRate, systolicPressure, diastolicPressure, meanArterialPressure, weight, patientPosition, bloodOxygenLevel, oxygenTherapy, flowDelivered, patientActivity, additionalNotes);
    }

    @Override
    public String toString() {
        return "vitalSigns{" +
                "vitalSignId='" + vitalSignId + '\'' +
                ", userId='" + userId + '\'' +
                ", timestamp=" + timestamp +
                ", temperature=" + temperature +
                ", heartRate=" + heartRate +
                ", pulse=" + pulse +
                ", respiratoryRate=" + respiratoryRate +
                ", systolicPressure=" + systolicPressure +
                ", diastolicPressure=" + diastolicPressure +
                ", meanArterialPressure=" + meanArterialPressure +
                ", weight=" + weight +
                ", patientPosition='" + patientPosition + '\'' +
                ", bloodOxygenLevel=" + bloodOxygenLevel +
                ", oxygenTherapy='" + oxygenTherapy + '\'' +
                ", flowDelivered='" + flowDelivered + '\'' +
                ", patientActivity='" + patientActivity + '\'' +
                ", additionalNotes='" + additionalNotes + '\'' +
                '}';
    }
}
