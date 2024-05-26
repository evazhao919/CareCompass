package com.devyanan.CareCompass.activity.requests;

import com.devyanan.CareCompass.dynamodb.models.VitalSigns;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = AddVitalSignsRequest.Builder.class)
public class AddVitalSignsRequest {
    private final String patientId;
    private final String actualCheckTime;
    private final double temperature;
    private final int heartRate;
    private final int pulse;
    private final int respiratoryRate;
    private final int systolicPressure;
    private final int diastolicPressure;
    private final int meanArterialPressure;
    private final double weight;
    private VitalSigns.PATIENT_POSITION patientPosition;
    private int bloodOxygenLevel;
    private VitalSigns.OXYGEN_THERAPY oxygenTherapy;
    private VitalSigns.FLOW_DELIVERED  flowDelivered;
    private VitalSigns.PATIENT_ACTIVITY patientActivity;
    private String comments;

    public AddVitalSignsRequest(String patientId, String actualCheckTime, double temperature, int heartRate, int pulse, int respiratoryRate, int systolicPressure, int diastolicPressure, int meanArterialPressure, double weight, VitalSigns.PATIENT_POSITION patientPosition, int bloodOxygenLevel, VitalSigns.OXYGEN_THERAPY oxygenTherapy, VitalSigns.FLOW_DELIVERED  flowDelivered, VitalSigns.PATIENT_ACTIVITY patientActivity, String comments) {
        this.patientId = patientId;
        this.actualCheckTime = actualCheckTime;
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
        this.comments = comments;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getActualCheckTime() {
        return actualCheckTime;
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

    public VitalSigns.PATIENT_POSITION getPatientPosition() {
        return patientPosition;
    }

    public int getBloodOxygenLevel() {
        return bloodOxygenLevel;
    }

    public VitalSigns.OXYGEN_THERAPY getOxygenTherapy() {
        return oxygenTherapy;
    }

    public VitalSigns.FLOW_DELIVERED  getFlowDelivered() {
        return flowDelivered;
    }

    public VitalSigns.PATIENT_ACTIVITY getPatientActivity() {
        return patientActivity;
    }

    public String getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "AddVitalSignsRequest{" +
                "patientId='" + patientId + '\'' +
                ", actualCheckTime='" + actualCheckTime + '\'' +
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
        private double temperature;
        private int heartRate;
        private int pulse;
        private int respiratoryRate;
        private int systolicPressure;
        private int diastolicPressure;
        private int meanArterialPressure;
        private double weight;
        private VitalSigns.PATIENT_POSITION patientPosition;
        private int bloodOxygenLevel;
        private VitalSigns.OXYGEN_THERAPY oxygenTherapy;
        private VitalSigns.FLOW_DELIVERED  flowDelivered;
        private VitalSigns.PATIENT_ACTIVITY patientActivity;
        private String comments;

        public Builder withPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder withActualCheckTime(String actualCheckTime) {
            this.actualCheckTime = actualCheckTime;
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

        public Builder withPatientPosition(VitalSigns.PATIENT_POSITION patientPosition) {
            this.patientPosition = patientPosition;
            return this;
        }

        public Builder withBloodOxygenLevel(int bloodOxygenLevel) {
            this.bloodOxygenLevel = bloodOxygenLevel;
            return this;
        }

        public Builder withOxygenTherapy(VitalSigns.OXYGEN_THERAPY oxygenTherapy) {
            this.oxygenTherapy = oxygenTherapy;
            return this;
        }

        public Builder withFlowDelivered(VitalSigns.FLOW_DELIVERED  flowDelivered) {
            this.flowDelivered = flowDelivered;
            return this;
        }

        public Builder withPatientActivity(VitalSigns.PATIENT_ACTIVITY patientActivity) {
            this.patientActivity = patientActivity;
            return this;
        }

        public Builder withComments(String comments) {
            this.comments = comments;
            return this;
        }

        public AddVitalSignsRequest build() {
            return new AddVitalSignsRequest(patientId, actualCheckTime, temperature, heartRate, pulse, respiratoryRate, systolicPressure, diastolicPressure, meanArterialPressure, weight, patientPosition, bloodOxygenLevel, oxygenTherapy, flowDelivered, patientActivity, comments);
        }
    }
}
