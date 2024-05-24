package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.DeleteBloodGlucoseMeasurementRequest;
import com.devyanan.CareCompass.activity.results.DeleteBloodGlucoseMeasurementResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.BloodGlucoseMeasurementDao;
import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class DeleteBloodGlucoseMeasurementActivity {
    private final Logger log = LogManager.getLogger();
    private final BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao;
    @Inject
    public DeleteBloodGlucoseMeasurementActivity(BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao) {
        this.bloodGlucoseMeasurementDao = bloodGlucoseMeasurementDao;
    }
    public DeleteBloodGlucoseMeasurementResult handleRequest(final DeleteBloodGlucoseMeasurementRequest request){
        log.info("Received DeleteBloodGlucoseMeasurementRequest {}", request);

        BloodGlucoseMeasurement bloodGlucoseMeasurement = bloodGlucoseMeasurementDao.deleteSingleBloodGlucoseMeasurement(request.getPatientId(),request.getActualCheckTime());

        return DeleteBloodGlucoseMeasurementResult.builder()
                .withBloodGlucoseMeasurementModel(new ModelConverter().toBloodGlucoseMeasurementModel(bloodGlucoseMeasurement))
                .build();
    }
}
