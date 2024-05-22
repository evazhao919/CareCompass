package com.devyanan.CareCompass.activity.results;

import com.devyanan.CareCompass.models.VitalSignsModel;

import java.util.List;

public class GetAllVitalSignsResult {
    private final List<VitalSignsModel> vitalSignModelList;

    public GetAllVitalSignsResult(List<VitalSignsModel> vitalSignModelList) {
        this.vitalSignModelList = vitalSignModelList;
    }

    public List<VitalSignsModel> getVitalSignModelList() {
        return vitalSignModelList;
    }

    @Override
    public String toString() {
        return "GetAllVitalSignsResult{" +
                "vitalSignModelList=" + vitalSignModelList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<VitalSignsModel> vitalSignModelList;

        public Builder withVitalSignModelList(List<VitalSignsModel> vitalSignModelList) {
            this.vitalSignModelList = vitalSignModelList;
            return this;
        }

        public GetAllVitalSignsResult build() {
            return new GetAllVitalSignsResult(vitalSignModelList);
        }
    }
}
