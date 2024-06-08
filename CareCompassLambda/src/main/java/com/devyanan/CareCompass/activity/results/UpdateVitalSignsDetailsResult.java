package com.devyanan.CareCompass.activity.results;

import com.devyanan.CareCompass.models.VitalSignsModel;

public class UpdateVitalSignsResult {
    private final VitalSignsModel vitalSignModel;

    public UpdateVitalSignsResult(VitalSignsModel vitalSignModel) {
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

        public UpdateVitalSignsResult build() {
            return new UpdateVitalSignsResult(vitalSignModel);
        }
    }
}
