package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.AddBloodGlucoseMeasurementRequest;
import com.devyanan.CareCompass.activity.results.AddBloodGlucoseMeasurementResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.BloodGlucoseMeasurementDao;
import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import com.devyanan.CareCompass.models.BloodGlucoseMeasurementModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * This class handles the logic for adding a blood glucose measurement.
 */
public class AddBloodGlucoseMeasurementActivity {
    private final Logger log = LogManager.getLogger();
    private final BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao;
    private final LocalDateTimeConverter dateTimeConverter;

    /**
     * Constructor for AddBloodGlucoseMeasurementActivity.
     * @param bloodGlucoseMeasurementDao DAO for blood glucose measurements
     */
    @Inject
    public AddBloodGlucoseMeasurementActivity(BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao) {
        this.bloodGlucoseMeasurementDao = bloodGlucoseMeasurementDao;
        this.dateTimeConverter = new LocalDateTimeConverter();
    }

    /**
     * Handles the request to add a blood glucose measurement.
     * @param request The request containing the blood glucose measurement data
     * @return The result of adding the blood glucose measurement
     */
    public AddBloodGlucoseMeasurementResult handleRequest(final AddBloodGlucoseMeasurementRequest request){
        log.info("Received addBloodGlucoseMeasurementRequest {}", request);

        BloodGlucoseMeasurement bloodGlucoseMeasurement = new BloodGlucoseMeasurement();
        bloodGlucoseMeasurement.setPatientId(request.getPatientId());
        bloodGlucoseMeasurement.setActualCheckTime(dateTimeConverter.unconvert(request.getActualCheckTime()));
        bloodGlucoseMeasurement.setGlucoseLevel(request.getGlucoseLevel());
        bloodGlucoseMeasurement.setGlucoseContext(request.getGlucoseContext());
        bloodGlucoseMeasurement.setComments(request.getComments());

        BloodGlucoseMeasurement result = bloodGlucoseMeasurementDao.addBloodGlucoseMeasurement(bloodGlucoseMeasurement);

        ModelConverter modelConverter = new ModelConverter();
        BloodGlucoseMeasurementModel bloodGlucoseMeasurementModel = modelConverter.toBloodGlucoseMeasurementModel(result);

        return AddBloodGlucoseMeasurementResult.builder()
                .withBloodGlucoseMeasurementModel(bloodGlucoseMeasurementModel)
                .build();
    }
}
