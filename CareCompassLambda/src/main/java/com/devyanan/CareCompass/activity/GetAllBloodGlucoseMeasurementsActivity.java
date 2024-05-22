package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.GetAllBloodGlucoseMeasurementsRequest;
import com.devyanan.CareCompass.activity.results.GetAllBloodGlucoseMeasurementsResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.BloodGlucoseMeasurementDao;
import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import com.devyanan.CareCompass.exceptions.BloodGlucoseMeasurementNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GetAllBloodGlucoseMeasurementsActivity {
    private final Logger log = LogManager.getLogger();
    private final BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao;

    public GetAllBloodGlucoseMeasurementsActivity(BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao) {
        this.bloodGlucoseMeasurementDao = bloodGlucoseMeasurementDao;
    }
    public GetAllBloodGlucoseMeasurementsResult handleRequest(GetAllBloodGlucoseMeasurementsRequest request){
        log.info("GetAllBloodGlucoseMeasurementsRequest received {}.",request);
        List<BloodGlucoseMeasurement> bloodGlucoseMeasurementList;
        try{
            bloodGlucoseMeasurementList = bloodGlucoseMeasurementDao.getAllBloodGlucoseMeasurements(request.getPatientId());
        } catch (BloodGlucoseMeasurementNotFoundException e){
            log.error("BloodGlucoseMeasurements with PatientId {} is not found in database.",
                    request.getPatientId());
            throw new BloodGlucoseMeasurementNotFoundException(e.getMessage(), e.getCause());
        }
        return GetAllBloodGlucoseMeasurementsResult.builder()
                .withBloodGlucoseMeasurementModelList(new ModelConverter().toBloodGlucoseMeasurementModelList(bloodGlucoseMeasurementList))
                .build();
    }
}
