package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.DeleteMedicationRequest;
import com.devyanan.CareCompass.activity.results.DeleteMedicationResult;
import com.devyanan.CareCompass.dynamodb.MedicationDao;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class DeleteMedicationActivityTest {
    @Mock
    private MedicationDao medicationDao;

    private DeleteMedicationActivity deleteMedicationActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        deleteMedicationActivity = new DeleteMedicationActivity(medicationDao);
    }

    @Test
    public void handleRequest_withValidPatientIdAndMedicationId_removesAndReturnsMedication() {
        // Given
        String patientId = "patient123";
        String medicationId =  "2023-06-19T16:51";

        Medication medicationToDelete = new Medication();
        medicationToDelete.setPatientId(patientId);
        medicationToDelete.setMedicationId(medicationId);

        when(medicationDao.deleteMedication(medicationToDelete))
                .thenReturn(medicationToDelete);

        // When
        DeleteMedicationRequest request = DeleteMedicationRequest.builder()
                .withPatientId(patientId)
                .withMedicationId(medicationId)
                .build();

        DeleteMedicationResult result = deleteMedicationActivity.handleRequest(request);

        verify(medicationDao).deleteMedication(medicationToDelete);

        assertEquals(patientId, result.getMedicationModel().getPatientId());
        assertEquals(medicationId, result.getMedicationModel().getMedicationId());
    }
}
