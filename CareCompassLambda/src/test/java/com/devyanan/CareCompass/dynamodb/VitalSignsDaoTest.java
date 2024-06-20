package com.devyanan.CareCompass.dynamodb;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.devyanan.CareCompass.dynamodb.models.VitalSigns;
import com.devyanan.CareCompass.exceptions.VitalSignsNotFoundException;
import com.devyanan.CareCompass.metrics.MetricsConstants;
import com.devyanan.CareCompass.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;

class VitalSignsDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    @Mock
    private MetricsPublisher metricsPublisher;
    @InjectMocks
    private VitalSignsDao vitalSignsDao;
    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testSaveVitalSigns_NullVitalSigns_ExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> vitalSignsDao.saveVitalSigns(null));
        verify(metricsPublisher).addCount(eq(MetricsConstants.ADD_VITAL_SIGNS_NULL_OR_EMPTY_COUNT), eq(1.0));
    }

    @Test
    void testDeleteSingleVitalSignsByActualCheckTime_ValidInput_Success() {
        String patientId = "patient1";
        LocalDateTime actualCheckTime = LocalDateTime.now();

        VitalSigns vitalSigns = new VitalSigns();
        vitalSigns.setPatientId(patientId);
        vitalSigns.setActualCheckTime(actualCheckTime);

        when(dynamoDBMapper.load(eq(VitalSigns.class), eq(patientId), eq(actualCheckTime))).thenReturn(vitalSigns);

        VitalSigns result = vitalSignsDao.deleteSingleVitalSignsByActualCheckTime(patientId, actualCheckTime);

        assertNotNull(result);
        assertEquals(vitalSigns, result);
        verify(dynamoDBMapper).delete(eq(vitalSigns));
        verify(metricsPublisher).addCount(eq(MetricsConstants.DELETE_SINGLE_VITAL_SIGNS_TOTAL_COUNT), eq(1.0));
    }

    @Test
    void testDeleteSingleVitalSignsByActualCheckTime_NotFound_ExceptionThrown() {
        String patientId = "patient1";
        LocalDateTime actualCheckTime = LocalDateTime.now();

        when(dynamoDBMapper.load(eq(VitalSigns.class), eq(patientId), eq(actualCheckTime))).thenReturn(null);

        assertThrows(VitalSignsNotFoundException.class, () -> vitalSignsDao.deleteSingleVitalSignsByActualCheckTime(patientId, actualCheckTime));
    }

    @Test
    void testGetVitalSigns_NotFound_ExceptionThrown() {
        String patientId = "patient1";
        LocalDateTime actualCheckTime = LocalDateTime.now();

        when(dynamoDBMapper.load(eq(VitalSigns.class), eq(patientId), eq(actualCheckTime))).thenReturn(null);

        assertThrows(VitalSignsNotFoundException.class, () -> vitalSignsDao.getVitalSigns(patientId, actualCheckTime));
        verify(metricsPublisher).addCount(eq(MetricsConstants.GET_SINGLE_VITALSIGNS_BY_PATIENT_ID_AND_ACTUAL_CHECK_TIME_NULL_OR_EMPTY_COUNT), eq(1.0));
    }
}
