package com.devyanan.CareCompass.activity.results;

import com.devyanan.CareCompass.models.VitalSignsModel;

public class AddVitalSignsResult {
    private final VitalSignsModel vitalSignModel;

    public AddVitalSignsResult(VitalSignsModel vitalSignModel) {
        this.vitalSignModel = vitalSignModel;
    }

    public VitalSignsModel getVitalSignModel() {
        return vitalSignModel;
    }

    @Override
    public String toString() {
        return "AddVitalSignsResult{" +
                "vitalSignModel=" + vitalSignModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private VitalSignsModel vitalSignModel;

        public Builder withVitalSignModel(VitalSignsModel vitalSignModel) {
            this.vitalSignModel = vitalSignModel;
            return this;
        }

        public AddVitalSignsResult build() {
            return new AddVitalSignsResult(vitalSignModel);
        }
    }
}
