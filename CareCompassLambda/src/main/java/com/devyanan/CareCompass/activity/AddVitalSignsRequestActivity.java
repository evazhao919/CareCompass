package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.AddVitalSignsRequest;
import com.devyanan.CareCompass.activity.results.AddVitalSignsResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.VitalSignsDao;
import com.devyanan.CareCompass.dynamodb.models.VitalSigns;
import com.devyanan.CareCompass.models.VitalSignsModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class AddVitalSignsRequestActivity {
    private final Logger log = LogManager.getLogger();
    private final VitalSignsDao vitalSignsDao;
    private final LocalDateTimeConverter dateTimeConverter;
    @Inject
    public AddVitalSignsRequestActivity(final VitalSignsDao vitalSignsDao) {
        this.vitalSignsDao = vitalSignsDao;
        dateTimeConverter = new LocalDateTimeConverter();
    }
    public AddVitalSignsResult handleRequest(final AddVitalSignsRequest request) {
        log.info("Received AddVitalSignsRequest {}", request);
        String actualCheckTime = request.getActualCheckTime();
        double temperature = request.getTemperature();
        int heartRate = request.getHeartRate();
        int pulse = request.getPulse();
        int respiratoryRate = request.getRespiratoryRate();
        int systolicPressure = request.getSystolicPressure();
        int diastolicPressure = request.getDiastolicPressure();
        int meanArterialPressure = request.getMeanArterialPressure();
        double weight = request.getWeight();
        String patientPosition = request.getPatientPosition();
        int bloodOxygenLevel = request.getBloodOxygenLevel();
        String oxygenTherapy = request.getOxygenTherapy();
        String flowDelivered = request.getFlowDelivered();
        String patientActivity = request.getPatientActivity();
        String comments = request.getComments();

        //TODO check for invalid enter

        VitalSigns vitalSigns = new VitalSigns();
        vitalSigns.setPatientId(request.getPatientId());
        vitalSigns.setActualCheckTime(dateTimeConverter.unconvert(request.getActualCheckTime()));
        vitalSigns.setTemperature(request.getTemperature());
        vitalSigns.setHeartRate(request.getHeartRate());
        vitalSigns.setPulse(request.getPulse());
        vitalSigns.setRespiratoryRate(request.getRespiratoryRate());
        vitalSigns.setSystolicPressure(request.getSystolicPressure());
        vitalSigns.setDiastolicPressure(request.getDiastolicPressure());
        vitalSigns.setMeanArterialPressure(request.getMeanArterialPressure());
        vitalSigns.setWeight(request.getWeight());
        vitalSigns.setPatientPosition(request.getPatientPosition());
        vitalSigns.setBloodOxygenLevel(request.getBloodOxygenLevel());
        vitalSigns.setOxygenTherapy(request.getOxygenTherapy());
        vitalSigns.setFlowDelivered(request.getOxygenTherapy());
        vitalSigns.setPatientActivity(request.getPatientActivity());
        vitalSigns.setComments(request.getComments());

        VitalSigns result = vitalSignsDao.addVitalSigns(vitalSigns);

        ModelConverter modelConverter = new ModelConverter();
        VitalSignsModel vitalSignsModel = modelConverter.toVitalSignsModel(result);

       return AddVitalSignsResult.builder()
               .withVitalSignModel(vitalSignsModel)
               .build();
    }
}
