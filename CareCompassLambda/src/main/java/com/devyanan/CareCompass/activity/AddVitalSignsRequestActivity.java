package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.AddVitalSignsRequest;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.dynamodb.VitalSignsDao;
import com.devyanan.CareCompass.dynamodb.models.VitalSigns;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AddVitalSignsRequestActivity {
    private final Logger log = LogManager.getLogger();
    private final VitalSignsDao vitalSignsDao;
    private final LocalDateTimeConverter dateTimeConverter;

    public AddVitalSignsRequestActivity(VitalSignsDao vitalSignsDao) {
        this.vitalSignsDao = vitalSignsDao;
        dateTimeConverter = new LocalDateTimeConverter();
    }
    public AddVitalSignsRequestActivity handleRequest(final AddVitalSignsRequest addVitalSignsRequest) {
        log.info("Received AddVitalSignsRequest {}", addVitalSignsRequest);
        String actualCheckTime = addVitalSignsRequest.getActualCheckTime();
        double temperature = addVitalSignsRequest.getTemperature();
        int heartRate = addVitalSignsRequest.getHeartRate();
        int pulse = addVitalSignsRequest.getPulse();
        int respiratoryRate = addVitalSignsRequest.getRespiratoryRate();
        int systolicPressure = addVitalSignsRequest.getSystolicPressure();
        int diastolicPressure = addVitalSignsRequest.getDiastolicPressure();
        int meanArterialPressure = addVitalSignsRequest.getMeanArterialPressure();
        double weight = addVitalSignsRequest.getWeight();
        String patientPosition = addVitalSignsRequest.getPatientPosition();
        int bloodOxygenLevel = addVitalSignsRequest.getBloodOxygenLevel();
        String oxygenTherapy = addVitalSignsRequest.getOxygenTherapy();
        String flowDelivered = addVitalSignsRequest.getFlowDelivered();
        String patientActivity = addVitalSignsRequest.getPatientActivity();
        String comments = addVitalSignsRequest.getComments();

        //TODO check for invalid enter

        VitalSigns vitalSigns = new VitalSigns();
        vitalSigns.setPatientId(addVitalSignsRequest.getPatientId());
        vitalSigns.setActualCheckTime(dateTimeConverter.unconvert(addVitalSignsRequest.getActualCheckTime()));
        vitalSigns.setTemperature(addVitalSignsRequest.getTemperature());
        vitalSigns.setHeartRate(addVitalSignsRequest.getHeartRate());
        vitalSigns.setPulse(addVitalSignsRequest.getPulse());
        vitalSigns.setRespiratoryRate(addVitalSignsRequest.getRespiratoryRate());
        vitalSigns.setSystolicPressure(addVitalSignsRequest.getSystolicPressure());
        vitalSigns.setDiastolicPressure(addVitalSignsRequest.getDiastolicPressure());
        vitalSigns.setMeanArterialPressure(addVitalSignsRequest.getMeanArterialPressure());
        vitalSigns.setWeight(addVitalSignsRequest.getWeight());
        vitalSigns.setPatientPosition(addVitalSignsRequest.getPatientPosition());
        vitalSigns.setBloodOxygenLevel(addVitalSignsRequest.getBloodOxygenLevel());
        vitalSigns.setOxygenTherapy(addVitalSignsRequest.getOxygenTherapy());
        vitalSigns.setFlowDelivered(addVitalSignsRequest.getOxygenTherapy());
        vitalSigns.setPatientActivity(addVitalSignsRequest.getPatientActivity());
        vitalSigns.setComments(addVitalSignsRequest.getComments());

        VitalSigns result = vitalSignsDao.addVitalSigns(vitalSigns);






    }
}
