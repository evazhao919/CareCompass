package com.devyanan.CareCompass.activity.results;

import com.devyanan.CareCompass.models.MedicationModel;

public class CreateMedicationResult {
    private final MedicationModel medicationModel;

    public MedicationModel getMedicationModel() {
        return medicationModel;
    }

    public CreateMedicationResult(MedicationModel medicationModel) {
        this.medicationModel = medicationModel;
    }

    @Override
    public String toString() {
        return "CreateMedicationResult{" +
                "medicationModel=" + medicationModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private MedicationModel medicationModel;

        public Builder withMedicationModel(MedicationModel medicationModel) {
            this.medicationModel = medicationModel;
            return this;
        }

        public CreateMedicationResult build() {
            return new CreateMedicationResult(medicationModel);
        }
    }
}
