package com.devyanan.CareCompass.activity.results;

import com.devyanan.CareCompass.models.MedicationModel;

import java.util.List;

public class RetrieveCurrentMedicationsResult {
    private final List<MedicationModel> medications;

    public RetrieveCurrentMedicationsResult(List<MedicationModel> medications) {
        this.medications = medications;
    }

    public List<MedicationModel> getMedications() {
        return medications;
    }

    @Override
    public String toString() {
        return "RetrieveCurrentMedicationsResult{" +
                "medications=" + medications +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private List<MedicationModel> medications;
        public Builder withMedications(List<MedicationModel> medications) {
            this.medications = medications;
            return this;
        }
        public RetrieveCurrentMedicationsResult build() {
            return new RetrieveCurrentMedicationsResult(medications);
        }
    }
}
