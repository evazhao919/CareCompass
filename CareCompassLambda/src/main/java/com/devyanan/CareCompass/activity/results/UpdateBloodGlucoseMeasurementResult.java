package com.devyanan.CareCompass.activity.results;

import com.devyanan.CareCompass.models.BloodGlucoseMeasurementModel;

public class UpdateBloodGlucoseMeasurementResult {
    private final BloodGlucoseMeasurementModel bloodGlucoseMeasurementModel;

    public UpdateBloodGlucoseMeasurementResult(BloodGlucoseMeasurementModel bloodGlucoseMeasurementModel) {
        this.bloodGlucoseMeasurementModel = bloodGlucoseMeasurementModel;
    }

    public BloodGlucoseMeasurementModel getBloodGlucoseMeasurementModel() {
        return bloodGlucoseMeasurementModel;
    }

    @Override
    public String toString() {
        return "UpdateBloodGlucoseMeasurementResult{" +
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

        public UpdateBloodGlucoseMeasurementResult build() {
            return new UpdateBloodGlucoseMeasurementResult(bloodGlucoseMeasurementModel);
        }
    }
}
