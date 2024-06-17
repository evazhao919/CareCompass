package com.devyanan.CareCompass.activity.results;

import com.devyanan.CareCompass.models.BloodGlucoseMeasurementModel;

import java.util.List;

public class GetAllBloodGlucoseMeasurementsResult {
    private final List<BloodGlucoseMeasurementModel> bloodGlucoseMeasurementModelList;

    public GetAllBloodGlucoseMeasurementsResult(List<BloodGlucoseMeasurementModel> bloodGlucoseMeasurementModelList) {
        this.bloodGlucoseMeasurementModelList = bloodGlucoseMeasurementModelList;
    }

    public List<BloodGlucoseMeasurementModel> getBloodGlucoseMeasurementModelList() {
        return bloodGlucoseMeasurementModelList;
    }

    @Override
    public String toString() {
        return "GetAllBloodGlucoseMeasurementsResult{" +
                "bloodGlucoseMeasurementModelList=" + bloodGlucoseMeasurementModelList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<BloodGlucoseMeasurementModel> bloodGlucoseMeasurementModelList;
        public Builder withBloodGlucoseMeasurementModelList(List<BloodGlucoseMeasurementModel> bloodGlucoseMeasurementModelList){
            this.bloodGlucoseMeasurementModelList = bloodGlucoseMeasurementModelList;
            return this;
        }
        public GetAllBloodGlucoseMeasurementsResult build(){
            return new GetAllBloodGlucoseMeasurementsResult(bloodGlucoseMeasurementModelList);
        }
    }
}