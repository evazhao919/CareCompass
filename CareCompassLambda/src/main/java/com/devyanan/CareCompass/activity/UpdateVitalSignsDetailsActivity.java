package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.UpdateVitalSignsDetailsRequest;
import com.devyanan.CareCompass.activity.results.UpdateVitalSignsDetailsResult;
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
public class UpdateVitalSignsDetailsActivity {
    private final Logger log = LogManager.getLogger();
    private final VitalSignsDao vitalSignsDao;
    private final LocalDateTimeConverter dateTimeConverter;

    /**
     * Constructor for UpdateVitalSignsRequestActivity.
     * @param vitalSignsDao DAO for vital signs
     */
    @Inject
    public UpdateVitalSignsDetailsActivity(final VitalSignsDao vitalSignsDao) {
        this.vitalSignsDao = vitalSignsDao;
        dateTimeConverter = new LocalDateTimeConverter();
    }

    /**
     * Handles the request to Update vital signs.
     * @param request The request containing the vital signs data
     * @return The result of Updateing the vital signs
     */
    public UpdateVitalSignsDetailsResult handleRequest(final UpdateVitalSignsDetailsRequest request) {
        log.info("Received UpdateVitalSignsRequest {}", request);
        VitalSigns vitalSigns = vitalSignsDao.getVitalSigns(request.getPatientId(),dateTimeConverter.unconvert(request.getActualCheckTime()));

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

        return UpdateVitalSignsDetailsResult.builder()
                .withVitalSignModel(vitalSignsModel)
                .build();
    }
}