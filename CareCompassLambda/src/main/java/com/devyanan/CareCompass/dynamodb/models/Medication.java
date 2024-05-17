package com.devyanan.CareCompass.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.converters.LocalTimeConverter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "medications")
public class Medication {
   private String patientId;
   private String medicationName;
   private List<MedicationDosage> dosages;
   private RouteOfAdministration routeOfAdministration;
   private FREQUENCY frequency;
   private LocalTime timeToTake;
   private LocalDateTime startDate;
   private LocalDateTime endDate;
   private MedicationStatus medicationStatus;
   private MedicationPriority medicationPriority;
   private MedicationForm medicationForm;
   private String medicationInfo;
   private String notes;
   private LocalDateTime timeAdded;
   private PrescriberType prescribedBy;

   public enum FREQUENCY {
       ONCE_A_DAY, TWICE_A_DAY, THREE_TIMES_A_DAY, FOUR_TIMES_A_DAY, ONCE_A_WEEK, AS_NEEDED
   }
    public enum PrescriberType {
        GENERAL_PRACTITIONER, PSYCHIATRIST, PEDIATRICIAN, CARDIOLOGIST, ONCOLOGIST, NURSE_PRACTITIONER, PHYSICIAN_ASSISTANT
   }
    public enum RouteOfAdministration {
        ORAL, INTRAVENOUS, TOPICAL, INHALATION, SUBCUTANEOUS, INTRAMUSCULAR
   }
    public enum MedicationStatus {
        ACTIVE, DISCONTINUED, PAUSED
   }
    public enum MedicationPriority {
        URGENT, ROUTINE, ELECTIVE
   }
    public enum MedicationForm {
        PILL, CAPSULE, LIQUID, INJECTION, TOPICAL
    }

    @DynamoDBHashKey(attributeName = "patientId")
    @DynamoDBIndexHashKey(globalSecondaryIndexNames = {"medicationIndex", "vitalSignsIndex","userNotificationsIndex"}, attributeName = "patientId")
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    @DynamoDBRangeKey(attributeName = "medicationName")
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "medicationIndex", attributeName = "medicationName")
    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    @DynamoDBAttribute(attributeName = "dosage")
    public List<MedicationDosage> getDosages() {
        return dosages;
    }

    public void setDosages(List<MedicationDosage> dosages) {
        this.dosages = dosages;
    }

    @DynamoDBAttribute(attributeName = "routeOfAdministration")
    public RouteOfAdministration getRouteOfAdministration() {
        return routeOfAdministration;
    }

    public void setRouteOfAdministration(RouteOfAdministration routeOfAdministration) {
        this.routeOfAdministration = routeOfAdministration;
    }
    @DynamoDBAttribute(attributeName = "frequency")
    public FREQUENCY getFrequency() {
        return frequency;
    }

    public void setFrequency(FREQUENCY frequency) {
        this.frequency = frequency;
    }
    @DynamoDBAttribute(attributeName = "timeToTake")
    @DynamoDBTypeConverted(converter = LocalTimeConverter.class)
    public LocalTime getTimeToTake() {
        return timeToTake;
    }

    public void setTimeToTake(LocalTime timeToTake) {
        this.timeToTake = timeToTake;
    }

    @DynamoDBAttribute(attributeName = "startDate")
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    @DynamoDBAttribute(attributeName = "endDate")
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    @DynamoDBAttribute(attributeName = "medicationStatus")
    public MedicationStatus getMedicationStatus() {
        return medicationStatus;
    }

    public void setMedicationStatus(MedicationStatus medicationStatus) {
        this.medicationStatus = medicationStatus;
    }
    @DynamoDBAttribute(attributeName = "medicationPriority")
    public MedicationPriority getMedicationPriority() {
        return medicationPriority;
    }

    public void setMedicationPriority(MedicationPriority medicationPriority) {
        this.medicationPriority = medicationPriority;
    }

    public MedicationForm getMedicationForm() {
        return medicationForm;
    }

    public void setMedicationForm(MedicationForm medicationForm) {
        this.medicationForm = medicationForm;
    }

    @DynamoDBAttribute(attributeName = "medicationInfo")
    public String getMedicationInfo() {
        return medicationInfo;
    }

    public void setMedicationInfo(String medicationInfo) {
        if (medicationInfo == null || medicationInfo.equals("")) {
            this.medicationInfo = "";
        } else {
            this.medicationInfo = medicationInfo;
        }
    }
    @DynamoDBAttribute(attributeName = "notes")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        if (notes == null || notes.equals("")) {
            this.notes = "";
        } else {
            this.notes = notes;
        }
    }
    @DynamoDBAttribute(attributeName = "timeAdded")
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    public LocalDateTime getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(LocalDateTime timeAdded) {
        this.timeAdded = timeAdded;
    }
    @DynamoDBAttribute(attributeName = "prescribedBy")
    public PrescriberType getPrescribedBy() {
        return prescribedBy;
    }

    public void setPrescribedBy(PrescriberType prescribedBy) {
        this.prescribedBy = prescribedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medication that = (Medication) o;
        return Objects.equals(patientId, that.patientId) && Objects.equals(medicationName, that.medicationName) && Objects.equals(dosages, that.dosages) && routeOfAdministration == that.routeOfAdministration && frequency == that.frequency && Objects.equals(timeToTake, that.timeToTake) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && medicationStatus == that.medicationStatus && medicationPriority == that.medicationPriority && medicationForm == that.medicationForm && Objects.equals(medicationInfo, that.medicationInfo) && Objects.equals(notes, that.notes) && Objects.equals(timeAdded, that.timeAdded) && prescribedBy == that.prescribedBy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, medicationName, dosages, routeOfAdministration, frequency, timeToTake, startDate, endDate, medicationStatus, medicationPriority, medicationForm, medicationInfo, notes, timeAdded, prescribedBy);
    }

    @Override
    public String toString() {
        return "Medication{" +
                "patientId='" + patientId + '\'' +
                ", medicationName='" + medicationName + '\'' +
                ", dosages='" + dosages + '\'' +
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
}
