package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.UpdateVitalSignsRequest;
import com.devyanan.CareCompass.activity.results.UpdateVitalSignsResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.VitalSignsDao;
import com.devyanan.CareCompass.dynamodb.models.VitalSigns;
import com.devyanan.CareCompass.models.VitalSignsModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * This class handles the logic for Updateing vital signs requests.
 */
public class UpdateVitalSignsRequestActivity {
    private final Logger log = LogManager.getLogger();
    private final VitalSignsDao vitalSignsDao;
    private final LocalDateTimeConverter dateTimeConverter;

    /**
     * Constructor for UpdateVitalSignsRequestActivity.
     * @param vitalSignsDao DAO for vital signs
     */
    @Inject
    public UpdateVitalSignsRequestActivity(final VitalSignsDao vitalSignsDao) {
        this.vitalSignsDao = vitalSignsDao;
        dateTimeConverter = new LocalDateTimeConverter();
    }

    /**
     * Handles the request to Update vital signs.
     * @param request The request containing the vital signs data
     * @return The result of Updateing the vital signs
     */
    public UpdateVitalSignsResult handleRequest(final UpdateVitalSignsRequest request) {
        log.info("Received UpdateVitalSignsRequest {}", request);

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
        vitalSigns.setFlowDelivered(request.getFlowDelivered());
        vitalSigns.setPatientActivity(request.getPatientActivity());
        vitalSigns.setComments(request.getComments());

        VitalSigns result = vitalSignsDao.saveVitalSigns(vitalSigns);

        ModelConverter modelConverter = new ModelConverter();
        VitalSignsModel vitalSignsModel = modelConverter.toVitalSignsModel(result);

        return UpdateVitalSignsResult.builder()
                .withVitalSignModel(vitalSignsModel)
                .build();
    }
}
/////TODO not right!