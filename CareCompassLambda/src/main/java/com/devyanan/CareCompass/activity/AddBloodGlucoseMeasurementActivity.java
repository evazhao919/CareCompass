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

import java.time.LocalDateTime;

public class AddBloodGlucoseMeasurementActivity {
    private final Logger log = LogManager.getLogger();
    private final BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao;
    private final LocalDateTimeConverter dateTimeConverter;

    public AddBloodGlucoseMeasurementActivity(BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao, LocalDateTimeConverter dateTimeConverter) {
        this.bloodGlucoseMeasurementDao = bloodGlucoseMeasurementDao;
        this.dateTimeConverter = dateTimeConverter;
    }
    public AddBloodGlucoseMeasurementResult handleRequest(final AddBloodGlucoseMeasurementRequest request){
        log.info("Received addBloodGlucoseMeasurementRequest {}", request);
        String actualCheckTime = request.getActualCheckTime();
        double glucoseLevel = request.getGlucoseLevel();
        BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext = request.getGlucoseContext();
        String comments = request.getComments();

        //TODO check for invalid enter

        BloodGlucoseMeasurement bloodGlucoseMeasurement = new BloodGlucoseMeasurement();
        bloodGlucoseMeasurement.setPatientId(request.getPatientId());
        bloodGlucoseMeasurement.setActualCheckTime(dateTimeConverter.unconvert(request.getActualCheckTime()));
        bloodGlucoseMeasurement.setGlucoseLevel(request.getGlucoseLevel());
        bloodGlucoseMeasurement.setGlucoseContext(request.getGlucoseContext());
        bloodGlucoseMeasurement.setComments(request.getComments());

        BloodGlucoseMeasurement result = bloodGlucoseMeasurementDao.addBloodGlucoseMeasurement(bloodGlucoseMeasurement);

        bloodGlucoseMeasurementDao.addBloodGlucoseMeasurement(bloodGlucoseMeasurement);

        ModelConverter modelConverter = new ModelConverter();
        BloodGlucoseMeasurementModel bloodGlucoseMeasurementModel = modelConverter.toBloodGlucoseMeasurementModel(result);

        return AddBloodGlucoseMeasurementResult.builder()
                .withBloodGlucoseMeasurementModel(bloodGlucoseMeasurementModel)
                .build();
    }
}
