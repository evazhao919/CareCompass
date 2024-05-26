package com.devyanan.CareCompass.converters;
import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.devyanan.CareCompass.dynamodb.models.VitalSigns;
import com.devyanan.CareCompass.models.BloodGlucoseMeasurementModel;
import com.devyanan.CareCompass.models.MedicationModel;
import com.devyanan.CareCompass.models.NotificationModel;
import com.devyanan.CareCompass.models.VitalSignsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts between Data and API models.
 */
public class ModelConverter {

    /**
     * Converts BloodGlucoseMeasurement entity to BloodGlucoseMeasurementModel.
     * @param bloodGlucoseMeasurement The BloodGlucoseMeasurement entity to convert.
     * @return The corresponding BloodGlucoseMeasurementModel.
     */
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

    /**
     * Converts a list of BloodGlucoseMeasurement entities to a list of BloodGlucoseMeasurementModel.
     * @param bloodGlucoseMeasurementList The list of BloodGlucoseMeasurement entities to convert.
     * @return The corresponding list of BloodGlucoseMeasurementModel.
     */
    public List<BloodGlucoseMeasurementModel> toBloodGlucoseMeasurementModelList(List<BloodGlucoseMeasurement> bloodGlucoseMeasurementList){
        List<BloodGlucoseMeasurementModel> bloodGlucoseMeasurementModelList = new ArrayList<>();
        for(BloodGlucoseMeasurement bloodGlucoseMeasurement : bloodGlucoseMeasurementList){
            bloodGlucoseMeasurementModelList.add(toBloodGlucoseMeasurementModel(bloodGlucoseMeasurement));
        }
        return bloodGlucoseMeasurementModelList;
    }

    /**
     * Converts Medication entity to MedicationModel.
     * @param medication The Medication entity to convert.
     * @return The corresponding MedicationModel.
     */
    public MedicationModel toMedicationModel(Medication medication){
        return MedicationModel.builder()
                .withPatientId(medication.getPatientId())
                .withMedicationName(medication.getMedicationName())
                .withPrescription(medication.getPrescription())
                .withInstructions(medication.getInstructions())
                .build();
    }

    /**
     * Converts a list of Medication entities to a list of MedicationModel.
     * @param medicationList The list of Medication entities to convert.
     * @return The corresponding list of MedicationModel.
     */
    public List<MedicationModel> toMedicationModelList(List<Medication> medicationList){
        List<MedicationModel> medicationModelList = new ArrayList<>();
        for(Medication medication : medicationList){
            medicationModelList.add(toMedicationModel(medication));
        }
        return medicationModelList;
    }

    /**
     * Converts a list of Medication entities to a list of MedicationModel.
     * @param vitalSigns The list of Medication entities to convert.
     * @return The corresponding list of MedicationModel.
     */
    public VitalSignsModel toVitalSignsModel(VitalSigns vitalSigns) {
        LocalDateTimeConverter localDateTimeConverter = new LocalDateTimeConverter();
        return VitalSignsModel.builder()
                .withPatientId(vitalSigns.getPatientId())
                .withActualCheckTime(localDateTimeConverter.convert(vitalSigns.getActualCheckTime()))
                .withTemperature(vitalSigns.getTemperature())
                .withHeartRate(vitalSigns.getHeartRate())
                .withPulse(vitalSigns.getPulse())
                .withRespiratoryRate(vitalSigns.getRespiratoryRate())
                .withSystolicPressure(vitalSigns.getSystolicPressure())
                .withDiastolicPressure(vitalSigns.getDiastolicPressure())
                .withMeanArterialPressure(vitalSigns.getMeanArterialPressure())
                .withWeight(vitalSigns.getWeight())
                .withPatientPosition(vitalSigns.getPatientPosition())
                .withBloodOxygenLevel(vitalSigns.getBloodOxygenLevel())
                .withOxygenTherapy(vitalSigns.getOxygenTherapy())
                .withFlowDelivered(vitalSigns.getFlowDelivered())
                .withPatientActivity(vitalSigns.getPatientActivity())
                .withComments(vitalSigns.getComments())
                .build();
    }

    /**
     * Converts a list of VitalSigns entities to a list of VitalSignsModel.
     * @param vitalSignsList The list of VitalSigns entities to convert.
     * @return The corresponding list of VitalSignsModel.
     */
    public List<VitalSignsModel> toVitalSignsModelList (List<VitalSigns> vitalSignsList){
        List<VitalSignsModel> vitalSignsModelList = new ArrayList<>();
        for(VitalSigns vitalSigns : vitalSignsList){
            vitalSignsModelList.add(toVitalSignsModel(vitalSigns));
        }
        return vitalSignsModelList;
    }

    /**
     * Converts Notification entity to NotificationModel.
     * @param notification The Notification entity to convert.
     * @return The corresponding NotificationModel.
     */
    public NotificationModel toNotificationModel(Notification notification){
        LocalDateTimeConverter localDateTimeConverter = new LocalDateTimeConverter();
        return NotificationModel.builder()
                .withPatientId(notification.getPatientId())
                .withNotificationTitle(notification.getNotificationTitle())
                .withReminderContent(notification.getReminderContent())
                .withScheduledTime(localDateTimeConverter.convert(notification.getScheduledTime()))
                .build();
    }

    /**
     * Converts a list of Notification entities to a list of NotificationModel.
     * @param notificationList The list of Notification entities to convert.
     * @return The corresponding list of NotificationModel.
     */
    public  List<NotificationModel> toNotificationModelList(List<Notification> notificationList){
        List<NotificationModel> notificationModelList = new ArrayList<>();
        for(Notification notification : notificationList){
            notificationModelList.add(toNotificationModel(notification));
        }
        return notificationModelList;
    }
}
