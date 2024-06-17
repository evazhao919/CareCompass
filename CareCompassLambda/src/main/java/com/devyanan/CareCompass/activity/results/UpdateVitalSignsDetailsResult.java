package com.devyanan.CareCompass.activity.results;

import com.devyanan.CareCompass.models.VitalSignsModel;

public class UpdateVitalSignsDetailsResult {
    private final VitalSignsModel vitalSignModel;

    public UpdateVitalSignsDetailsResult(VitalSignsModel vitalSignModel) {
        this.vitalSignModel = vitalSignModel;
    }

    public VitalSignsModel getVitalSignModel() {
        return vitalSignModel;
    }

    @Override
    public String toString() {
        return "UpdateVitalSignsResult{" +
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

        public UpdateVitalSignsDetailsResult build() {
            return new UpdateVitalSignsDetailsResult(vitalSignModel);
        }
    }
}