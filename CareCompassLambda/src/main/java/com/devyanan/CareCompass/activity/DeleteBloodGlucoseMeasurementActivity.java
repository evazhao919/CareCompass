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
        log.info("Received RemoveMedicationRequest {}", request);

        String actualCheckTime = request.getActualCheckTime();

        // TODO check for invalidation

        BloodGlucoseMeasurement bloodGlucoseMeasurement = bloodGlucoseMeasurementDao.getSingleBloodGlucoseMeasurement(request.getPatientId(),request.getActualCheckTime(),request.getGlucoseLevel(),request.getGlucoseContext(),request.getComments());

        BloodGlucoseMeasurement result = bloodGlucoseMeasurementDao.deleteBloodGlucoseMeasurement(bloodGlucoseMeasurement);

        ModelConverter modelConverter = new ModelConverter();
        BloodGlucoseMeasurementModel bloodGlucoseMeasurementModel = modelConverter.toBloodGlucoseMeasurementModel(result);

        return DeleteBloodGlucoseMeasurementResult.builder()
                .withBloodGlucoseMeasurementModel(bloodGlucoseMeasurementModel)
                .build();
    }
}
