package com.devyanan.CareCompass.models;

import com.devyanan.CareCompass.dynamodb.models.Medication;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class MedicationModel {
    private final String patientId;
    private final String medicationName;
    private final String dosage;
    private final Medication.RouteOfAdministration routeOfAdministration;
    private final Medication.FREQUENCY frequency;
    private final LocalTime timeToTake;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Medication.MedicationStatus medicationStatus;
    private final Medication.MedicationPriority medicationPriority;
    private final Medication.MedicationForm medicationForm;
    private final String medicationInfo;
    private final String notes;
    private final LocalDateTime timeAdded;
    private final Medication.PrescriberType prescribedBy;

    public MedicationModel(String patientId, String medicationName, String dosage, Medication.RouteOfAdministration routeOfAdministration, Medication.FREQUENCY frequency, LocalTime timeToTake, LocalDateTime startDate, LocalDateTime endDate, Medication.MedicationStatus medicationStatus, Medication.MedicationPriority medicationPriority, Medication.MedicationForm medicationForm, String medicationInfo, String notes, LocalDateTime timeAdded, Medication.PrescriberType prescribedBy) {
        this.patientId = patientId;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.routeOfAdministration = routeOfAdministration;
        this.frequency = frequency;
        this.timeToTake = timeToTake;
        this.startDate = startDate;
        this.endDate = endDate;
        this.medicationStatus = medicationStatus;
        this.medicationPriority = medicationPriority;
        this.medicationForm = medicationForm;
        this.medicationInfo = medicationInfo;
        this.notes = notes;
        this.timeAdded = timeAdded;
        this.prescribedBy = prescribedBy;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public Medication.RouteOfAdministration getRouteOfAdministration() {
        return routeOfAdministration;
    }

    public Medication.FREQUENCY getFrequency() {
        return frequency;
    }

    public LocalTime getTimeToTake() {
        return timeToTake;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Medication.MedicationStatus getMedicationStatus() {
        return medicationStatus;
    }

    public Medication.MedicationPriority getMedicationPriority() {
        return medicationPriority;
    }

    public Medication.MedicationForm getMedicationForm() {
        return medicationForm;
    }

    public String getMedicationInfo() {
        return medicationInfo;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDateTime getTimeAdded() {
        return timeAdded;
    }

    public Medication.PrescriberType getPrescribedBy() {
        return prescribedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicationModel that = (MedicationModel) o;
        return Objects.equals(patientId, that.patientId) && Objects.equals(medicationName, that.medicationName) && Objects.equals(dosage, that.dosage) && routeOfAdministration == that.routeOfAdministration && frequency == that.frequency && Objects.equals(timeToTake, that.timeToTake) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && medicationStatus == that.medicationStatus && medicationPriority == that.medicationPriority && medicationForm == that.medicationForm && Objects.equals(medicationInfo, that.medicationInfo) && Objects.equals(notes, that.notes) && Objects.equals(timeAdded, that.timeAdded) && prescribedBy == that.prescribedBy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, medicationName, dosage, routeOfAdministration, frequency, timeToTake, startDate, endDate, medicationStatus, medicationPriority, medicationForm, medicationInfo, notes, timeAdded, prescribedBy);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String patientId;
        private String medicationName;
        private String dosage;
        private Medication.RouteOfAdministration routeOfAdministration;
        private Medication.FREQUENCY frequency;
        private LocalTime timeToTake;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Medication.MedicationStatus medicationStatus;
        private Medication.MedicationPriority medicationPriority;
        private Medication.MedicationForm medicationForm;
        private String medicationInfo;
        private String notes;
        private LocalDateTime timeAdded;
        private Medication.PrescriberType prescribedBy;
            public Builder withPatientId(String patientId) {
                this.patientId = patientId;
                return this;
            }

            public Builder withMedicationName(String medicationName) {
                this.medicationName = medicationName;
                return this;
            }

            public Builder withDosage(String dosage) {
                this.dosage = dosage;
                return this;
            }

            public Builder withRouteOfAdministration(Medication.RouteOfAdministration routeOfAdministration) {
                this.routeOfAdministration = routeOfAdministration;
                return this;
            }

            public Builder withFrequency(Medication.FREQUENCY frequency) {
                this.frequency = frequency;
                return this;
            }

            public Builder withTimeToTake(LocalTime timeToTake) {
                this.timeToTake = timeToTake;
                return this;
            }

            public Builder withStartDate(LocalDateTime startDate) {
                this.startDate = startDate;
                return this;
            }

            public Builder withEndDate(LocalDateTime endDate) {
                this.endDate = endDate;
                return this;
            }

            public Builder withMedicationStatus(Medication.MedicationStatus medicationStatus) {
                this.medicationStatus = medicationStatus;
                return this;
            }

            public Builder withMedicationPriority(Medication.MedicationPriority medicationPriority) {
                this.medicationPriority = medicationPriority;
                return this;
            }

            public Builder withMedicationForm(Medication.MedicationForm medicationForm) {
                this.medicationForm = medicationForm;
                return this;
            }

            public Builder withMedicationInfo(String medicationInfo) {
                this.medicationInfo = medicationInfo;
                return this;
            }

            public Builder withNotes(String notes) {
                this.notes = notes;
                return this;
            }

            public Builder withTimeAdded(LocalDateTime timeAdded) {
                this.timeAdded = timeAdded;
                return this;
            }

            public Builder withPrescribedBy(Medication.PrescriberType prescribedBy) {
                this.prescribedBy = prescribedBy;
                return this;
            }

            public MedicationModel build() {
                return new MedicationModel(patientId, medicationName, dosage, routeOfAdministration, frequency, timeToTake, startDate, endDate, medicationStatus, medicationPriority, medicationForm, medicationInfo, notes, timeAdded, prescribedBy);
            }
        }
    }
