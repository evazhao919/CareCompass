package com.devyanan.CareCompass.activity.results;

import com.devyanan.CareCompass.models.VitalSignModel;

import java.util.List;

public class ViewAllVitalSignsResult {
    private final List<VitalSignModel> vitalSignModelList;

    public ViewAllVitalSignsResult(List<VitalSignModel> vitalSignModelList) {
        this.vitalSignModelList = vitalSignModelList;
    }

    public List<VitalSignModel> getVitalSignModelList() {
        return vitalSignModelList;
    }

    @Override
    public String toString() {
        return "ViewAllVitalSignsResult{" +
                "vitalSignModelList=" + vitalSignModelList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<VitalSignModel> vitalSignModelList;

        public Builder withVitalSignModelList(List<VitalSignModel> vitalSignModelList) {
            this.vitalSignModelList = vitalSignModelList;
            return this;
        }

        public ViewAllVitalSignsResult build() {
            return new ViewAllVitalSignsResult(vitalSignModelList);
        }
    }
}
