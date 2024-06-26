package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.GetAllBloodGlucoseMeasurementsRequest;
import com.devyanan.CareCompass.activity.results.GetAllBloodGlucoseMeasurementsResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.BloodGlucoseMeasurementDao;
import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import com.devyanan.CareCompass.exceptions.BloodGlucoseMeasurementNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

/**
 * This class handles the logic for retrieving all blood glucose measurements for a patient.
 */
public class GetAllBloodGlucoseMeasurementsActivity {
    private final Logger log = LogManager.getLogger();
    private final BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao;

    /**
     * Constructor for GetAllBloodGlucoseMeasurementsActivity.
     * @param bloodGlucoseMeasurementDao DAO for blood glucose measurements
     */
    @Inject
    public GetAllBloodGlucoseMeasurementsActivity(BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao) {
        this.bloodGlucoseMeasurementDao = bloodGlucoseMeasurementDao;
    }

    /**
     * Handles the request to retrieve all blood glucose measurements for a patient.
     * @param request The request containing the patient ID
     * @return The result containing the list of blood glucose measurements
     * @throws BloodGlucoseMeasurementNotFoundException if no measurements are found for the patient
     */
    public GetAllBloodGlucoseMeasurementsResult handleRequest(GetAllBloodGlucoseMeasurementsRequest request){
        log.info("GetAllBloodGlucoseMeasurementsRequest received {}.", request);
        List<BloodGlucoseMeasurement> bloodGlucoseMeasurementList;
        try{
            bloodGlucoseMeasurementList = bloodGlucoseMeasurementDao.getAllBloodGlucoseMeasurements(request.getPatientId());
        } catch (BloodGlucoseMeasurementNotFoundException e){
            log.error("BloodGlucoseMeasurements with PatientId {} are not found in the database. Error: {}", request.getPatientId(), e.getMessage());
            throw new BloodGlucoseMeasurementNotFoundException(e.getMessage(), e.getCause());
        }
        return GetAllBloodGlucoseMeasurementsResult.builder()
                .withBloodGlucoseMeasurementModelList(new ModelConverter().toBloodGlucoseMeasurementModelList(bloodGlucoseMeasurementList))
                .build();
    }
}