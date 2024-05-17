package com.devyanan.CareCompass.activity.results;

import com.devyanan.CareCompass.models.BloodGlucoseMeasurementModel;

public class DeleteBloodGlucoseMeasurementResult {
   private final BloodGlucoseMeasurementModel bloodGlucoseMeasurementModel;

    public DeleteBloodGlucoseMeasurementResult(BloodGlucoseMeasurementModel bloodGlucoseMeasurementModel) {
        this.bloodGlucoseMeasurementModel = bloodGlucoseMeasurementModel;
    }

    public BloodGlucoseMeasurementModel getBloodGlucoseMeasurementModel() {
        return bloodGlucoseMeasurementModel;
    }

    @Override
    public String toString() {
        return "DeleteBloodGlucoseMeasurementResult{" +
                "bloodGlucoseMeasurementModel=" + bloodGlucoseMeasurementModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private BloodGlucoseMeasurementModel bloodGlucoseMeasurementModel;

        public Builder withNotificationModel(BloodGlucoseMeasurementModel bloodGlucoseMeasurementModel) {
            this.bloodGlucoseMeasurementModel = bloodGlucoseMeasurementModel;
            return this;
        }

        public DeleteBloodGlucoseMeasurementResult build() {
            return new DeleteBloodGlucoseMeasurementResult(bloodGlucoseMeasurementModel);
        }
    }
}
