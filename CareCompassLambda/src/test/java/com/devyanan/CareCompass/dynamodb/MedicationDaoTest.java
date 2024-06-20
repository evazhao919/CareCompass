package com.devyanan.CareCompass.dynamodb;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import com.devyanan.CareCompass.exceptions.*;
import com.devyanan.CareCompass.metrics.MetricsConstants;
import com.devyanan.CareCompass.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class MedicationDaoTest {

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @Mock
    private MetricsPublisher metricsPublisher;

    @InjectMocks
    private MedicationDao medicationDao;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testSaveMedication_NullMedication_ExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> medicationDao.saveMedication(null));
        verify(metricsPublisher).addCount(eq(MetricsConstants.ADD_MEDICATION_NULL_OR_EMPTY_COUNT), eq(1.0));
    }

    @Test
    void testDeleteMedication_ValidMedication_Success() {
        Medication medication = new Medication();
        medication.setPatientId("patient1");
        medication.setMedicationId("med123");

        doNothing().when(dynamoDBMapper).delete(any(Medication.class));

        Medication result = medicationDao.deleteMedication(medication);

        assertNotNull(result);
        assertEquals(medication, result);
        verify(metricsPublisher).addCount(eq(MetricsConstants.DELETE_SINGLE_MEDICATION_BY_MEDICATION_ID_FOUND_COUNT), eq(1.0));
    }

    @Test
    void testGetMedication_MedicationNotFound_ExceptionThrown() {

        String patientId = "patient1";
        String medicationId = "med123";

        when(dynamoDBMapper.load(eq(Medication.class), eq(patientId), eq(medicationId))).thenReturn(null);

        assertThrows(MedicationNotFoundException.class, () -> medicationDao.getMedication(patientId, medicationId));
        verify(metricsPublisher).addCount(eq(MetricsConstants.UPDATE_SINGLE_MEDICATION_NULL_OR_EMPTY_COUNT), eq(1.0));
    }
}
