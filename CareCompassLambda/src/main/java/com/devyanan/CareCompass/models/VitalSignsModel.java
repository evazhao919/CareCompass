package com.devyanan.CareCompass.models;

import com.devyanan.CareCompass.dynamodb.models.VitalSigns;

import java.util.Objects;

/**
 * Represents a model for vital signs.
 */
public class VitalSignsModel {
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
    private final VitalSigns.PatientPosition patientPosition;
    private final int bloodOxygenLevel;
    private final VitalSigns.OxygenTherapy oxygenTherapy;
    private final VitalSigns.FlowDelivered flowDelivered;
    private final VitalSigns.PatientActivity patientActivity;
    private final String comments;

    /**
     * Private constructor for the VitalSignsModel.
     * Use the Builder pattern to construct instances of this class.
     */
    public VitalSignsModel(String patientId, String actualCheckTime, double temperature, int heartRate, int pulse, int respiratoryRate, int systolicPressure, int diastolicPressure, int meanArterialPressure, double weight, VitalSigns.PatientPosition patientPosition, int bloodOxygenLevel, VitalSigns.OxygenTherapy oxygenTherapy, VitalSigns.FlowDelivered flowDelivered, VitalSigns.PatientActivity patientActivity, String comments) {
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

    public String getComments() {
        return comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VitalSignsModel that = (VitalSignsModel) o;
        return Double.compare(temperature, that.temperature) == 0 && heartRate == that.heartRate && pulse == that.pulse && respiratoryRate == that.respiratoryRate && systolicPressure == that.systolicPressure && diastolicPressure == that.diastolicPressure && meanArterialPressure == that.meanArterialPressure && Double.compare(weight, that.weight) == 0 && bloodOxygenLevel == that.bloodOxygenLevel && Objects.equals(patientId, that.patientId) && Objects.equals(actualCheckTime, that.actualCheckTime) && patientPosition == that.patientPosition && oxygenTherapy == that.oxygenTherapy && flowDelivered == that.flowDelivered && patientActivity == that.patientActivity && Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, actualCheckTime, temperature, heartRate, pulse, respiratoryRate, systolicPressure, diastolicPressure, meanArterialPressure, weight, patientPosition, bloodOxygenLevel, oxygenTherapy, flowDelivered, patientActivity, comments);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
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
        private VitalSigns.PatientPosition patientPosition;
        private int bloodOxygenLevel;
        private VitalSigns.OxygenTherapy oxygenTherapy;
        private VitalSigns.FlowDelivered flowDelivered;
        private VitalSigns.PatientActivity patientActivity;
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

        public Builder withComments(String comments) {
            this.comments = comments;
            return this;
        }

        public VitalSignsModel build() {
            return new VitalSignsModel(patientId, actualCheckTime, temperature, heartRate, pulse, respiratoryRate, systolicPressure, diastolicPressure, meanArterialPressure, weight, patientPosition, bloodOxygenLevel, oxygenTherapy, flowDelivered, patientActivity, comments);
        }
    }
}
