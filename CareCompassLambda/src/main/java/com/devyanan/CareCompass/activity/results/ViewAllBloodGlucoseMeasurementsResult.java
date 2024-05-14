package com.devyanan.CareCompass.activity.results;

import com.devyanan.CareCompass.models.BloodGlucoseMeasurementModel;

import java.util.List;

public class ViewAllBloodGlucoseMeasurementsResult {
    private final List<BloodGlucoseMeasurementModel> bloodGlucoseMeasurementModelList;

    public ViewAllBloodGlucoseMeasurementsResult(List<BloodGlucoseMeasurementModel> bloodGlucoseMeasurementModelList) {
        this.bloodGlucoseMeasurementModelList = bloodGlucoseMeasurementModelList;
    }

    public List<BloodGlucoseMeasurementModel> getBloodGlucoseMeasurementModelList() {
        return bloodGlucoseMeasurementModelList;
    }

    @Override
    public String toString() {
        return "ViewAllBloodGlucoseMeasurementsResult{" +
                "bloodGlucoseMeasurementModelList=" + bloodGlucoseMeasurementModelList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<BloodGlucoseMeasurementModel> bloodGlucoseMeasurementModelList;
        public Builder withBloodGlucoseMeasurementModels(List<BloodGlucoseMeasurementModel> bloodGlucoseMeasurementModelList){
            this.bloodGlucoseMeasurementModelList = bloodGlucoseMeasurementModelList;
            return this;
        }
        public ViewAllBloodGlucoseMeasurementsResult build(){
            return new ViewAllBloodGlucoseMeasurementsResult(bloodGlucoseMeasurementModelList);
        }
    }
}
