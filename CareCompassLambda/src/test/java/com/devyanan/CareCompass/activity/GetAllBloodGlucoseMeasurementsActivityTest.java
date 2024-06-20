package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.GetAllBloodGlucoseMeasurementsRequest;
import com.devyanan.CareCompass.activity.results.GetAllBloodGlucoseMeasurementsResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.BloodGlucoseMeasurementDao;
import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import com.devyanan.CareCompass.models.BloodGlucoseMeasurementModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetAllBloodGlucoseMeasurementsActivityTest {
    @Mock
    private BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao;

    private GetAllBloodGlucoseMeasurementsActivity getAllBloodGlucoseMeasurementsActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        getAllBloodGlucoseMeasurementsActivity = new GetAllBloodGlucoseMeasurementsActivity(bloodGlucoseMeasurementDao);
    }

    @Test
    public void handleRequest_withValidPatientId_returnAllBloodGlucoseMeasurements() {
        LocalDateTimeConverter timeConverter = new LocalDateTimeConverter();

        String patientId = "patient123";
        List<BloodGlucoseMeasurement> bloodGlucoseMeasurementList = new ArrayList<>();

        BloodGlucoseMeasurement bloodGlucoseMeasurement1 = new BloodGlucoseMeasurement();

        bloodGlucoseMeasurement1.setPatientId(patientId);
        bloodGlucoseMeasurement1.setActualCheckTime(timeConverter.unconvert("2023-06-18T10:00"));
        bloodGlucoseMeasurement1.setGlucoseLevel(120);
        bloodGlucoseMeasurement1.setGlucoseContext(BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT.FASTING);
        bloodGlucoseMeasurement1.setComments("No comments");

        BloodGlucoseMeasurement bloodGlucoseMeasurement2 = new BloodGlucoseMeasurement();
        bloodGlucoseMeasurement2.setPatientId(patientId);
        bloodGlucoseMeasurement2.setActualCheckTime(timeConverter.unconvert("2023-08-20T11:00"));
        bloodGlucoseMeasurement2.setGlucoseLevel(110);
        bloodGlucoseMeasurement2.setGlucoseContext(BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT.AFTER_MEAL);
        bloodGlucoseMeasurement2.setComments("No comments");

        bloodGlucoseMeasurementList.add(bloodGlucoseMeasurement1);
        bloodGlucoseMeasurementList.add(bloodGlucoseMeasurement2);


        when(bloodGlucoseMeasurementDao.getAllBloodGlucoseMeasurements(patientId)).thenReturn(bloodGlucoseMeasurementList);

        GetAllBloodGlucoseMeasurementsRequest request = GetAllBloodGlucoseMeasurementsRequest.builder()
                .withPatientId(patientId)
                .build();

        GetAllBloodGlucoseMeasurementsResult result = getAllBloodGlucoseMeasurementsActivity.handleRequest(request);


        List<BloodGlucoseMeasurementModel> expectedModelList = new ModelConverter().toBloodGlucoseMeasurementModelList(bloodGlucoseMeasurementList);
        assertEquals(expectedModelList.size(), result.getBloodGlucoseMeasurementModelList().size());

        BloodGlucoseMeasurementModel expectedModel1 = expectedModelList.get(0);//Retrieves the first BloodGlucoseMeasurementModel
        BloodGlucoseMeasurementModel actualModel1 = result.getBloodGlucoseMeasurementModelList().get(0);
        assertEquals(expectedModel1.getPatientId(), actualModel1.getPatientId());
        assertEquals(expectedModel1.getActualCheckTime(), actualModel1.getActualCheckTime());
        assertEquals(expectedModel1.getGlucoseLevel(), actualModel1.getGlucoseLevel());
        assertEquals(expectedModel1.getGlucoseContext(), actualModel1.getGlucoseContext());
        assertEquals(expectedModel1.getComments(), actualModel1.getComments());

        BloodGlucoseMeasurementModel expectedModel2 = expectedModelList.get(1);//Retrieves the second BloodGlucoseMeasurementModel
        BloodGlucoseMeasurementModel actualModel2 = result.getBloodGlucoseMeasurementModelList().get(1);
        assertEquals(expectedModel2.getPatientId(), actualModel2.getPatientId());
        assertEquals(expectedModel2.getActualCheckTime(), actualModel2.getActualCheckTime());
        assertEquals(expectedModel2.getGlucoseLevel(), actualModel2.getGlucoseLevel());
        assertEquals(expectedModel2.getGlucoseContext(), actualModel2.getGlucoseContext());
        assertEquals(expectedModel2.getComments(), actualModel2.getComments());
    }
}
