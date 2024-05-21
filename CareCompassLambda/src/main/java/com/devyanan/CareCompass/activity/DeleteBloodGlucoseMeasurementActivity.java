package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.DeleteBloodGlucoseMeasurementRequest;
import com.devyanan.CareCompass.activity.results.DeleteBloodGlucoseMeasurementResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.BloodGlucoseMeasurementDao;
import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import com.devyanan.CareCompass.models.BloodGlucoseMeasurementModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteBloodGlucoseMeasurementActivity {
    private final Logger log = LogManager.getLogger();
    private final BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao;


    public DeleteBloodGlucoseMeasurementActivity(BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao) {
        this.bloodGlucoseMeasurementDao = bloodGlucoseMeasurementDao;
    }
    public DeleteBloodGlucoseMeasurementResult handleRequest(final DeleteBloodGlucoseMeasurementRequest request){
        log.info("Received DeleteBloodGlucoseMeasurementRequest {}", request);

        // TODO check for invalidation
        BloodGlucoseMeasurement bloodGlucoseMeasurement = bloodGlucoseMeasurementDao.deleteSingleBloodGlucoseMeasurement(request.getPatientId(),request.getActualCheckTime());

        return DeleteBloodGlucoseMeasurementResult.builder()
                .withBloodGlucoseMeasurementModel(new ModelConverter().toBloodGlucoseMeasurementModel(bloodGlucoseMeasurement))
                .build();
    }
}
