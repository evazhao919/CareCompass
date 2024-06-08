package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.UpdateBloodGlucoseMeasurementDetailsRequest;
import com.devyanan.CareCompass.activity.results.UpdateBloodGlucoseMeasurementDetailsResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.BloodGlucoseMeasurementDao;
import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import com.devyanan.CareCompass.models.BloodGlucoseMeasurementModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class UpdateBloodGlucoseMeasurementDetailsActivity {
    private final Logger log = LogManager.getLogger();
    private final BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao;
    private final LocalDateTimeConverter dateTimeConverter;
    @Inject
    public UpdateBloodGlucoseMeasurementDetailsActivity(BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao) {
        this.bloodGlucoseMeasurementDao = bloodGlucoseMeasurementDao;
        this.dateTimeConverter = new LocalDateTimeConverter();
    }
    public UpdateBloodGlucoseMeasurementDetailsResult handleRequest(final UpdateBloodGlucoseMeasurementDetailsRequest request){
        log.info("Received UpdateBloodGlucoseMeasurementRequest {}", request);

        BloodGlucoseMeasurement bloodGlucoseMeasurement = bloodGlucoseMeasurementDao.getBloodGlucoseMeasurements(request.getPatientId(),dateTimeConverter.unconvert(request.getActualCheckTime()));

        bloodGlucoseMeasurement.setGlucoseLevel(request.getGlucoseLevel());
        bloodGlucoseMeasurement.setGlucoseContext(request.getGlucoseContext());
        bloodGlucoseMeasurement.setComments(request.getComments());

        BloodGlucoseMeasurement result = bloodGlucoseMeasurementDao.saveBloodGlucoseMeasurement(bloodGlucoseMeasurement);
        ModelConverter modelConverter = new ModelConverter();
        BloodGlucoseMeasurementModel bloodGlucoseMeasurementModel = modelConverter.toBloodGlucoseMeasurementModel(result);

        return UpdateBloodGlucoseMeasurementDetailsResult.builder()
                 .withBloodGlucoseMeasurementModel(bloodGlucoseMeasurementModel)
                .build();
    }
}
