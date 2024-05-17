package com.devyanan.CareCompass.models;

import java.time.LocalTime;
import java.util.Objects;

public class MedicationDosageModel {
    private final LocalTime time;
    private final String dosage;

    public MedicationDosageModel(LocalTime time, String dosage) {
        this.time = time;
        this.dosage = dosage;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDosage() {
        return dosage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicationDosageModel that = (MedicationDosageModel) o;
        return Objects.equals(time, that.time) && Objects.equals(dosage, that.dosage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, dosage);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private LocalTime time;
        private String dosage;
        public Builder withTime(LocalTime time) {
            this.time = time;
            return this;
        }
        public Builder withDosage(String dosage) {
            this.dosage = dosage;
            return this;
        }

        public MedicationDosageModel build() {
            return new MedicationDosageModel(time, dosage);
        }
    }
}
