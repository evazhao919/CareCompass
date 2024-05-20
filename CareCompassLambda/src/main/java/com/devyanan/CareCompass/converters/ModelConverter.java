package com.devyanan.CareCompass.converters;
import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import com.devyanan.CareCompass.dynamodb.models.VitalSigns;
import com.devyanan.CareCompass.models.BloodGlucoseMeasurementModel;
import com.devyanan.CareCompass.models.MedicationModel;
import com.devyanan.CareCompass.models.NotificationModel;
import com.devyanan.CareCompass.models.VitalSignsModel;

import javax.management.Notification;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts between Data and API models.
 */
public class ModelConverter {


    public MedicationModel toMedicationModel(Medication medication){
        return MedicationModel.builder()
                .withPatientId(medication.getPatientId())
                .withMedicationName(medication.getMedicationName())
                .withPrescription(medication.getPrescription())
                .withInstructions(medication.getInstructions())
                .build();
    }

    public List<MedicationModel> toMedicationModelList(List<Medication> medicationList){
        List<MedicationModel> medicationModelList = new ArrayList<>();
        for(Medication medication : medicationList){
            medicationModelList.add(toMedicationModel(medication));
        }
        return medicationModelList;
    }
    public BloodGlucoseMeasurementModel toBloodGlucoseMeasurementModel(BloodGlucoseMeasurement bloodGlucoseMeasurement){
        LocalDateTimeConverter localDateTimeConverter = new LocalDateTimeConverter();
        return BloodGlucoseMeasurementModel.builder()
                .withPatientId(bloodGlucoseMeasurement.getPatientId())
                .withActualCheckTime(localDateTimeConverter.convert(bloodGlucoseMeasurement.getActualCheckTime()))
                .withGlucoseLevel(bloodGlucoseMeasurement.getGlucoseLevel())
                .withGlucoseContext(bloodGlucoseMeasurement.getGlucoseContext())
                .withComments(bloodGlucoseMeasurement.getComments())
                .build();
    }

    public List<BloodGlucoseMeasurementModel> toBloodGlucoseMeasurementModelList(List<BloodGlucoseMeasurement> bloodGlucoseMeasurementList){
        List<BloodGlucoseMeasurementModel> bloodGlucoseMeasurementModelList = new ArrayList<>();
        for(BloodGlucoseMeasurement bloodGlucoseMeasurement : bloodGlucoseMeasurementList){
            bloodGlucoseMeasurementModelList.add(toBloodGlucoseMeasurementModel(bloodGlucoseMeasurement));
        }
        return bloodGlucoseMeasurementModelList;
    }

    public NotificationModel toNotificationModel(Notification notification){
        LocalDateTimeConverter localDateTimeConverter = new LocalDateTimeConverter();
        return NotificationModel.builder()
                .withPatientId(notification.getPatientId())
                .withNotificationId(notification.getNotificationId())
                .withNotificationTitle(notification.getNotificationTitle())
                .withReminderContent(notification.getReminderContent())
                .withReminderTime(localDateTimeConverter.convert(notification.getReminderTime()))
                .build();
    }
    public VitalSignsModel toVitalSignsModel(VitalSigns vitalSigns) {

        return null;
    }
}
