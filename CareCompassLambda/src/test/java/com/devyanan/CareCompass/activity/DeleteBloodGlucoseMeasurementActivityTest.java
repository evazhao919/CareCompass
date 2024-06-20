package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.DeleteBloodGlucoseMeasurementRequest;
import com.devyanan.CareCompass.activity.results.DeleteBloodGlucoseMeasurementResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.dynamodb.BloodGlucoseMeasurementDao;
import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class DeleteBloodGlucoseMeasurementActivityTest {

    @Mock
    private BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao;

    private DeleteBloodGlucoseMeasurementActivity deleteBloodGlucoseMeasurementActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        deleteBloodGlucoseMeasurementActivity = new DeleteBloodGlucoseMeasurementActivity(bloodGlucoseMeasurementDao);
    }

    @Test
    public void handleRequest_withValidPatientIdAndActualCheckTime_removesAndReturnsBloodGlucoseMeasurement() {
        LocalDateTimeConverter timeConverter = new LocalDateTimeConverter();

        String patientId = "patientId123";
        String actualCheckTime = "2023-06-19T16:18";

        BloodGlucoseMeasurement expectedMeasurement = new BloodGlucoseMeasurement();
        expectedMeasurement.setPatientId(patientId);
        expectedMeasurement.setActualCheckTime(timeConverter.unconvert(actualCheckTime));

        when(bloodGlucoseMeasurementDao.deleteSingleBloodGlucoseMeasurementByActualCheckTime(patientId, timeConverter.unconvert(actualCheckTime)))
                .thenReturn(expectedMeasurement);

        DeleteBloodGlucoseMeasurementRequest request = DeleteBloodGlucoseMeasurementRequest.builder()
                .withPatientId(patientId)
                .withActualCheckTime(actualCheckTime)
                .build();

        DeleteBloodGlucoseMeasurementResult result = deleteBloodGlucoseMeasurementActivity.handleRequest(request);

        verify(bloodGlucoseMeasurementDao).deleteSingleBloodGlucoseMeasurementByActualCheckTime(patientId, timeConverter.unconvert(actualCheckTime));

        assertEquals(patientId, result.getBloodGlucoseMeasurementModel().getPatientId());
        assertEquals(actualCheckTime, result.getBloodGlucoseMeasurementModel().getActualCheckTime());
    }
}
