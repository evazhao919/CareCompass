package com.devyanan.CareCompass.activity.requests;

import com.devyanan.CareCompass.dynamodb.models.VitalSigns;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDateTime;
@JsonDeserialize(builder = CreateVitalSignsRequest.Builder.class)
public class CreateVitalSignsRequest {
    private final String userId;
    private final LocalDateTime actualCheckTime;
    private final LocalDateTime scheduledTime;
    private final LocalDateTime timeAdded;
    private final double temperature;
    private final int heartRate;
    private final int pulse;
    private final int respiratoryRate;
    private final int systolicPressure;
    private final int diastolicPressure;
    private final int meanArterialPressure;
    private final double weight;
    private final VitalSigns.PatientPosition patientPosition;
    private final int bloodOxygenLevel;
    private final VitalSigns.OxygenTherapy oxygenTherapy;
    private final VitalSigns.FlowDelivered flowDelivered;
    private final VitalSigns.PatientActivity patientActivity;
    private final String additionalNotes;
    private final String comments;

    public CreateVitalSignsRequest(String userId, LocalDateTime actualCheckTime, LocalDateTime scheduledTime, LocalDateTime timeAdded, double temperature, int heartRate, int pulse, int respiratoryRate, int systolicPressure, int diastolicPressure, int meanArterialPressure, double weight, VitalSigns.PatientPosition patientPosition, int bloodOxygenLevel, VitalSigns.OxygenTherapy oxygenTherapy, VitalSigns.FlowDelivered flowDelivered, VitalSigns.PatientActivity patientActivity, String additionalNotes, String comments) {
        this.userId = userId;
        this.actualCheckTime = actualCheckTime;
        this.scheduledTime = scheduledTime;
        this.timeAdded = timeAdded;
        this.temperature = temperature;
        this.heartRate = heartRate;
        this.pulse = pulse;
        this.respiratoryRate = respiratoryRate;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.meanArterialPressure = meanArterialPressure;
        this.weight = weight;
        this.patientPosition = patientPosition;
        this.bloodOxygenLevel = bloodOxygenLevel;
        this.oxygenTherapy = oxygenTherapy;
        this.flowDelivered = flowDelivered;
        this.patientActivity = patientActivity;
        this.additionalNotes = additionalNotes;
        this.comments = comments;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getActualCheckTime() {
        return actualCheckTime;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public LocalDateTime getTimeAdded() {
        return timeAdded;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public int getPulse() {
        return pulse;
    }

    public int getRespiratoryRate() {
        return respiratoryRate;
    }

    public int getSystolicPressure() {
        return systolicPressure;
    }

    public int getDiastolicPressure() {
        return diastolicPressure;
    }

    public int getMeanArterialPressure() {
        return meanArterialPressure;
    }

    public double getWeight() {
        return weight;
    }

    public VitalSigns.PatientPosition getPatientPosition() {
        return patientPosition;
    }

    public int getBloodOxygenLevel() {
        return bloodOxygenLevel;
    }

    public VitalSigns.OxygenTherapy getOxygenTherapy() {
        return oxygenTherapy;
    }

    public VitalSigns.FlowDelivered getFlowDelivered() {
        return flowDelivered;
    }

    public VitalSigns.PatientActivity getPatientActivity() {
        return patientActivity;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public String getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "CreateVitalSignsRequest{" +
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
                ", comments='" + comments + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
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
        private VitalSigns.PatientPosition patientPosition;
        private int bloodOxygenLevel;
        private VitalSigns.OxygenTherapy oxygenTherapy;
        private VitalSigns.FlowDelivered flowDelivered;
        private VitalSigns.PatientActivity patientActivity;
        private String additionalNotes;
        private String comments;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public Builder withActualCheckTime(LocalDateTime actualCheckTime) {
            this.actualCheckTime = actualCheckTime;
            return this;
        }

        public Builder withScheduledTime(LocalDateTime scheduledTime) {
            this.scheduledTime = scheduledTime;
            return this;
        }

        public Builder withTimeAdded(LocalDateTime timeAdded) {
            this.timeAdded = timeAdded;
            return this;
        }

        public Builder withTemperature(double temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder withHeartRate(int heartRate) {
            this.heartRate = heartRate;
            return this;
        }

        public Builder withPulse(int pulse) {
            this.pulse = pulse;
            return this;
        }

        public Builder withRespiratoryRate(int respiratoryRate) {
            this.respiratoryRate = respiratoryRate;
            return this;
        }

        public Builder withSystolicPressure(int systolicPressure) {
            this.systolicPressure = systolicPressure;
            return this;
        }

        public Builder withDiastolicPressure(int diastolicPressure) {
            this.diastolicPressure = diastolicPressure;
            return this;
        }

        public Builder withMeanArterialPressure(int meanArterialPressure) {
            this.meanArterialPressure = meanArterialPressure;
            return this;
        }

        public Builder withWeight(double weight) {
            this.weight = weight;
            return this;
        }

        public Builder withPatientPosition(VitalSigns.PatientPosition patientPosition) {
            this.patientPosition = patientPosition;
            return this;
        }

        public Builder withBloodOxygenLevel(int bloodOxygenLevel) {
            this.bloodOxygenLevel = bloodOxygenLevel;
            return this;
        }

        public Builder withOxygenTherapy(VitalSigns.OxygenTherapy oxygenTherapy) {
            this.oxygenTherapy = oxygenTherapy;
            return this;
        }

        public Builder withFlowDelivered(VitalSigns.FlowDelivered flowDelivered) {
            this.flowDelivered = flowDelivered;
            return this;
        }

        public Builder withPatientActivity(VitalSigns.PatientActivity patientActivity) {
            this.patientActivity = patientActivity;
            return this;
        }

        public Builder withAdditionalNotes(String additionalNotes) {
            this.additionalNotes = additionalNotes;
            return this;
        }
        public Builder withComments(String comments) {
            this.comments = comments;
            return this;
        }

        public CreateVitalSignsRequest build() {
            return new CreateVitalSignsRequest(userId, actualCheckTime, scheduledTime, timeAdded, temperature, heartRate, pulse, respiratoryRate, systolicPressure, diastolicPressure, meanArterialPressure, weight, patientPosition, bloodOxygenLevel, oxygenTherapy, flowDelivered, patientActivity, additionalNotes, comments);
        }
    }
}
