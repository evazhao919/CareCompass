package com.devyanan.CareCompass.dynamodb;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import com.devyanan.CareCompass.metrics.MetricsConstants;
import com.devyanan.CareCompass.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

class BloodGlucoseMeasurementDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @Mock
    private MetricsPublisher metricsPublisher;

    @InjectMocks
    private BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testSaveBloodGlucoseMeasurement_NullMeasurement_ExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> bloodGlucoseMeasurementDao.saveBloodGlucoseMeasurement(null));
        verify(metricsPublisher).addCount(eq(MetricsConstants.ADD_BLOOD_GLUCOSE_MEASUREMENT_NULL_OR_EMPTY_COUNT), eq(1.0));
    }

    @Test
    void testDeleteSingleBloodGlucoseMeasurementByActualCheckTime_ValidInputs_Success() {
        String patientId = "patient1";
        LocalDateTime actualCheckTime = LocalDateTime.now();
        BloodGlucoseMeasurement measurementToDelete = new BloodGlucoseMeasurement();
        measurementToDelete.setPatientId(patientId);
        measurementToDelete.setActualCheckTime(actualCheckTime);

        doNothing().when(dynamoDBMapper).delete(any(BloodGlucoseMeasurement.class));

        BloodGlucoseMeasurement result = bloodGlucoseMeasurementDao.deleteSingleBloodGlucoseMeasurementByActualCheckTime(patientId, actualCheckTime);

        assertNotNull(result);
        assertEquals(measurementToDelete, result);
        verify(metricsPublisher).addCount(eq(MetricsConstants.DELETE_SINGLE_BLOOD_GLUCOSE_MEASUREMENT_SUCCESS_COUNT), eq(1.0));
    }

    @Test
    void testGetAllBloodGlucoseMeasurements_ValidPatientId_Success() {
        String patientId = "patient1";
        BloodGlucoseMeasurement measurement = new BloodGlucoseMeasurement();
        measurement.setPatientId(patientId);
        QueryResultPage<BloodGlucoseMeasurement> queryResultPage = new QueryResultPage<>();
        queryResultPage.setResults(Collections.singletonList(measurement));

        when(dynamoDBMapper.queryPage(eq(BloodGlucoseMeasurement.class), any())).thenReturn(queryResultPage);

        List<BloodGlucoseMeasurement> result = bloodGlucoseMeasurementDao.getAllBloodGlucoseMeasurements(patientId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(measurement, result.get(0));
        verify(metricsPublisher).addCount(eq(MetricsConstants.GET_ALL_BLOOD_GLUCOSE_MEASUREMENT_TOTAL_COUNT), eq(1.0));
    }

    @Test
    void testGetAllBloodGlucoseMeasurements_EmptyResults_ReturnsEmptyList() {
        String patientId = "patient1";
        QueryResultPage<BloodGlucoseMeasurement> queryResultPage = new QueryResultPage<>();
        queryResultPage.setResults(Collections.emptyList());

        when(dynamoDBMapper.queryPage(eq(BloodGlucoseMeasurement.class), any())).thenReturn(queryResultPage);

        List<BloodGlucoseMeasurement> result = bloodGlucoseMeasurementDao.getAllBloodGlucoseMeasurements(patientId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(metricsPublisher).addCount(eq(MetricsConstants.GET_ALL_BLOOD_GLUCOSE_MEASUREMENT_NULL_OR_EMPTY_COUNT), eq(1.0));
    }
}
