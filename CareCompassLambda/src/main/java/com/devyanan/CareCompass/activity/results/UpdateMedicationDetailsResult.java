package com.devyanan.CareCompass.activity.results;

import com.devyanan.CareCompass.models.MedicationModel;

public class UpdateMedicationDetailsResult {
    private final MedicationModel medicationModel;

    public UpdateMedicationDetailsResult(MedicationModel medicationModel) {
        this.medicationModel = medicationModel;
    }

    public MedicationModel getMedicationModel() {
        return medicationModel;
    }

    @Override
    public String toString() {
        return "UpdateMedicationDetailsResult{" +
                "medicationModel=" + medicationModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private MedicationModel medicationModel;
        public Builder withMedicationModel(MedicationModel medicationModel){
            this.medicationModel = medicationModel;
            return this;
        }
        public UpdateMedicationDetailsResult build(){
            return new UpdateMedicationDetailsResult(medicationModel);
        }
    }
}
