package com.devyanan.CareCompass.activity.results;

import com.devyanan.CareCompass.models.VitalSignModel;

public class DeleteVitalSignsResult {
    private final VitalSignModel vitalSignModel;

    public DeleteVitalSignsResult(VitalSignModel vitalSignModel) {
        this.vitalSignModel = vitalSignModel;
    }

    public VitalSignModel getVitalSignModel() {
        return vitalSignModel;
    }

    @Override
    public String toString() {
        return "DeleteVitalSignsResult{" +
                "vitalSignModel=" + vitalSignModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private VitalSignModel vitalSignModel;

        public Builder withVitalSignModel(VitalSignModel vitalSignModel) {
            this.vitalSignModel = vitalSignModel;
            return this;
        }

        public DeleteVitalSignsResult build() {
            return new DeleteVitalSignsResult(vitalSignModel);
        }
    }
}
