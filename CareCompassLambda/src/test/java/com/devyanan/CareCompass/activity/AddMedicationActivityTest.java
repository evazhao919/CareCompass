package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.AddMedicationRequest;
import com.devyanan.CareCompass.activity.results.AddMedicationResult;
import com.devyanan.CareCompass.dynamodb.MedicationDao;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import com.devyanan.CareCompass.exceptions.DatabaseAccessException;
import com.devyanan.CareCompass.utils.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class AddMedicationActivityTest {

    @Mock
    private MedicationDao medicationDao;

    private AddMedicationActivity addMedicationActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        addMedicationActivity = new AddMedicationActivity(medicationDao);
    }
    @Test
    public void handleRequest_withValidAttributes_addMedicationSuccessfully() {
        // Given
        String patientId = "patientId123";
        String medicationName = "MedicationName";
        String prescription = "Take one daily";
        String instructions = "With food";
        Medication.MEDICATION_STATUS medicationStatus = Medication.MEDICATION_STATUS.ACTIVE;

        AddMedicationRequest request = AddMedicationRequest.builder()
                .withPatientId(patientId)
                .withMedicationName(medicationName)
                .withPrescription(prescription)
                .withInstructions(instructions)
                .withMedicationStatus(medicationStatus)
                .build();

        Medication savedMedication = new Medication();
        savedMedication.setPatientId(patientId);
        savedMedication.setMedicationId(IdGenerator.generateId());
        savedMedication.setMedicationName(medicationName);
        savedMedication.setPrescription(prescription);
        savedMedication.setInstructions(instructions);
        savedMedication.setMedicationStatus(medicationStatus);

        when(medicationDao.saveMedication(any(Medication.class))).thenReturn(savedMedication);

        // When
        AddMedicationResult result = addMedicationActivity.handleRequest(request);

        // Then
        verify(medicationDao).saveMedication(any(Medication.class));

        assertNotNull(result.getMedicationModel().getMedicationId());
        assertEquals(patientId, result.getMedicationModel().getPatientId());
        assertEquals(medicationName, result.getMedicationModel().getMedicationName());
        assertEquals(prescription, result.getMedicationModel().getPrescription());
        assertEquals(instructions, result.getMedicationModel().getInstructions());
        assertEquals(medicationStatus, result.getMedicationModel().getMedicationStatus());
    }

    @Test
    public void handleRequest_databaseAccessException_throwsDatabaseAccessException() {
        // Given
        String patientId = "patientId456";
        String medicationName = "MedicationName";
        String prescription = "Take one daily";
        String instructions = "With food";
        Medication.MEDICATION_STATUS medicationStatus = Medication.MEDICATION_STATUS.ACTIVE;

        AddMedicationRequest request = AddMedicationRequest.builder()
                .withPatientId(patientId)
                .withMedicationName(medicationName)
                .withPrescription(prescription)
                .withInstructions(instructions)
                .withMedicationStatus(medicationStatus)
                .build();

        when(medicationDao.saveMedication(any(Medication.class)))
                .thenThrow(new DatabaseAccessException("Database error"));

        // Then
        assertThrows(DatabaseAccessException.class, () -> addMedicationActivity.handleRequest(request));
    }
}
