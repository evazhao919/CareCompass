package com.devyanan.CareCompass.activity.results;

import com.devyanan.CareCompass.models.BloodGlucoseMeasurementModel;

public class AddDeleteBloodGlucoseMeasurementResult {
    private final BloodGlucoseMeasurementModel bloodGlucoseMeasurementModel;

    public AddDeleteBloodGlucoseMeasurementResult(BloodGlucoseMeasurementModel bloodGlucoseMeasurementModel) {
        this.bloodGlucoseMeasurementModel = bloodGlucoseMeasurementModel;
    }

    public BloodGlucoseMeasurementModel getBloodGlucoseMeasurementModel() {
        return bloodGlucoseMeasurementModel;
    }

    @Override
    public String toString() {
        return "AddGlucoseLevelResult{" +
                "bloodGlucoseMeasurementModel=" + bloodGlucoseMeasurementModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private BloodGlucoseMeasurementModel bloodGlucoseMeasurementModel;
        public Builder withBloodGlucoseMeasurementModel(BloodGlucoseMeasurementModel bloodGlucoseMeasurementModel) {
            this.bloodGlucoseMeasurementModel = bloodGlucoseMeasurementModel;
            return this;
        }

        public AddDeleteBloodGlucoseMeasurementResult build() {
            return new AddDeleteBloodGlucoseMeasurementResult(bloodGlucoseMeasurementModel);
        }
    }
}
