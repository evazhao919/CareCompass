package com.devyanan.CareCompass.activity.requests;

import com.devyanan.CareCompass.dynamodb.models.Medication;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;

@JsonDeserialize(builder = DeleteMedicationRequest.Builder.class)
public class DeleteMedicationRequest {
    private final String patientId;
    private final String medicationName;
    private final LocalTime timeToTake;
    private final String dosage;
    private final Medication.RouteOfAdministration routeOfAdministration;
    private final Medication.FREQUENCY frequency;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Medication.MedicationStatus medicationStatus;
    private final Medication.MedicationPriority medicationPriority;
    private final Medication.MedicationForm medicationForm;
    private final String medicationInfo;
    private final String notes;
    private final LocalDateTime timeAdded;
    private final Medication.PrescriberType prescribedBy;

    public DeleteMedicationRequest(String patientId, String medicationName, String dosage, Medication.RouteOfAdministration routeOfAdministration, Medication.FREQUENCY frequency, LocalTime timeToTake, LocalDateTime startDate, LocalDateTime endDate, Medication.MedicationStatus medicationStatus, Medication.MedicationPriority medicationPriority, Medication.MedicationForm medicationForm, String medicationInfo, String notes, LocalDateTime timeAdded, Medication.PrescriberType prescribedBy) {
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
    public String toString() {
        return "DeleteMedicationRequest{" +
                "patientId='" + patientId + '\'' +
                ", medicationName='" + medicationName + '\'' +
                ", dosage='" + dosage + '\'' +
                ", routeOfAdministration=" + routeOfAdministration +
                ", frequency=" + frequency +
                ", timeToTake=" + timeToTake +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", medicationStatus=" + medicationStatus +
                ", medicationPriority=" + medicationPriority +
                ", medicationForm=" + medicationForm +
                ", medicationInfo='" + medicationInfo + '\'' +
                ", notes='" + notes + '\'' +
                ", timeAdded=" + timeAdded +
                ", prescribedBy=" + prescribedBy +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
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
        private  String notes;
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

        public DeleteMedicationRequest build() {
            return new DeleteMedicationRequest(patientId, medicationName, dosage, routeOfAdministration, frequency, timeToTake, startDate, endDate, medicationStatus, medicationPriority, medicationForm, medicationInfo, notes, timeAdded, prescribedBy);
        }
    }
}