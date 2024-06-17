package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.DeleteBloodGlucoseMeasurementRequest;
import com.devyanan.CareCompass.activity.results.DeleteBloodGlucoseMeasurementResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.BloodGlucoseMeasurementDao;
import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * This class handles the logic for deleting a blood glucose measurement.
 */
public class DeleteBloodGlucoseMeasurementActivity {
    private final Logger log = LogManager.getLogger();
    private final BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao;
    private final LocalDateTimeConverter dateTimeConverter;

    /**
     * Constructor for DeleteBloodGlucoseMeasurementActivity.
     * @param bloodGlucoseMeasurementDao DAO for blood glucose measurements
     */
    @Inject
    public DeleteBloodGlucoseMeasurementActivity(BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao) {
        this.bloodGlucoseMeasurementDao = bloodGlucoseMeasurementDao;
        dateTimeConverter = new LocalDateTimeConverter();
    }

    /**
     * Handles the request to delete a blood glucose measurement.
     * @param request The request containing the data for deletion
     * @return The result of the deletion operation
     */
    public DeleteBloodGlucoseMeasurementResult handleRequest(final DeleteBloodGlucoseMeasurementRequest request){
        log.info("Received DeleteBloodGlucoseMeasurementRequest {}", request);

        BloodGlucoseMeasurement result = bloodGlucoseMeasurementDao.deleteSingleBloodGlucoseMeasurementByActualCheckTime(request.getPatientId(),dateTimeConverter.unconvert(request.getActualCheckTime()));

        return DeleteBloodGlucoseMeasurementResult.builder()
                .withBloodGlucoseMeasurementModel(new ModelConverter().toBloodGlucoseMeasurementModel(result))
                .build();
    }
}