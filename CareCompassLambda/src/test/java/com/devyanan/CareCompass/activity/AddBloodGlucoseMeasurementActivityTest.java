package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.AddBloodGlucoseMeasurementRequest;
import com.devyanan.CareCompass.activity.results.AddBloodGlucoseMeasurementResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.dynamodb.BloodGlucoseMeasurementDao;
import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import com.devyanan.CareCompass.exceptions.DatabaseAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class AddBloodGlucoseMeasurementActivityTest {

    @Mock
    private BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao;
    private AddBloodGlucoseMeasurementActivity addBloodGlucoseMeasurementActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        addBloodGlucoseMeasurementActivity = new AddBloodGlucoseMeasurementActivity(bloodGlucoseMeasurementDao);
    }

    @Test
    public void handleRequest_withValidAttributes_addBloodGlucoseMeasurement() {
        // Given
        LocalDateTimeConverter timeConverter = new LocalDateTimeConverter();
        String patientId = "patientId123";
        String actualCheckTime = "2024-06-19T12:50";
        double glucoseLevel = 100.0;
        BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT glucoseContext = BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT.AFTER_MEAL;
        String comments = "No comments";

        AddBloodGlucoseMeasurementRequest request = AddBloodGlucoseMeasurementRequest.builder()
                .withPatientId(patientId)
                .withActualCheckTime(actualCheckTime)
                .withGlucoseLevel(glucoseLevel)
                .withGlucoseContext(glucoseContext)
                .withComments(comments)
                .build();

        BloodGlucoseMeasurement savedMeasurement = new BloodGlucoseMeasurement();
        savedMeasurement.setPatientId(patientId);
        savedMeasurement.setActualCheckTime(timeConverter.unconvert(actualCheckTime));
        savedMeasurement.setGlucoseLevel(glucoseLevel);
        savedMeasurement.setGlucoseContext(glucoseContext);
        savedMeasurement.setComments(comments);

        when(bloodGlucoseMeasurementDao.saveBloodGlucoseMeasurement(any(BloodGlucoseMeasurement.class))).thenReturn(savedMeasurement);
        // When
        AddBloodGlucoseMeasurementResult result = addBloodGlucoseMeasurementActivity.handleRequest(request);

        // Then
        verify(bloodGlucoseMeasurementDao).saveBloodGlucoseMeasurement(any(BloodGlucoseMeasurement.class));

        assertEquals(patientId, result.getBloodGlucoseMeasurementModel().getPatientId());
        assertEquals(glucoseLevel, result.getBloodGlucoseMeasurementModel().getGlucoseLevel());
        assertEquals(glucoseContext, result.getBloodGlucoseMeasurementModel().getGlucoseContext());
        assertEquals(comments, result.getBloodGlucoseMeasurementModel().getComments());
    }
    @Test
    public void handleRequest_databaseAccessException_throwsDatabaseAccessException() {

        String patientId = "patientId456";
        String actualCheckTime = "2024-06-19T09:50";
        double glucoseLevel = 100.0;
        BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT glucoseContext = BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT.BEFORE_MEAL;
        String comments = "No comments";

        AddBloodGlucoseMeasurementRequest request = AddBloodGlucoseMeasurementRequest.builder()
                .withPatientId(patientId)
                .withActualCheckTime(actualCheckTime)
                .withGlucoseLevel(glucoseLevel)
                .withGlucoseContext(glucoseContext)
                .withComments(comments)
                .build();

        when(bloodGlucoseMeasurementDao.saveBloodGlucoseMeasurement(any(BloodGlucoseMeasurement.class)))
                .thenThrow(new DatabaseAccessException("Database error"));

        assertThrows(DatabaseAccessException.class, () -> addBloodGlucoseMeasurementActivity.handleRequest(request));
    }
}
