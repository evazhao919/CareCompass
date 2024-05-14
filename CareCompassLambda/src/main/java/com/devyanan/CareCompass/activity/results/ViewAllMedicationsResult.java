package com.devyanan.CareCompass.activity.results;

import com.devyanan.CareCompass.models.MedicationModel;

import java.util.List;

public class ViewAllMedicationsResult {
    private final List<MedicationModel> medicationModelList;

    public ViewAllMedicationsResult(List<MedicationModel> medicationModelList) {
        this.medicationModelList = medicationModelList;
    }

    public List<MedicationModel> getMedicationModelList() {
        return medicationModelList;
    }

    @Override
    public String toString() {
        return "ViewAllMedicationsResult{" +
                "medicationModelList=" + medicationModelList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<MedicationModel> medicationModelList;
        public Builder withMedicationModelList(List<MedicationModel> medicationModelList){
            this.medicationModelList = medicationModelList;
            return this;
        }
        public ViewAllMedicationsResult  build(){
            return new ViewAllMedicationsResult (medicationModelList);
        }
    }
}
