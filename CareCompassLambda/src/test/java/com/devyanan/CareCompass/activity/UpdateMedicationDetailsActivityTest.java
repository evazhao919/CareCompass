package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.UpdateMedicationDetailsRequest;
import com.devyanan.CareCompass.activity.results.UpdateMedicationDetailsResult;
import com.devyanan.CareCompass.dynamodb.MedicationDao;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class UpdateMedicationDetailsActivityTest {
    @Mock
    private MedicationDao medicationDao;

    private UpdateMedicationDetailsActivity updateMedicationDetailsActivity;

    @BeforeEach
    public void setup() {
        openMocks(this);
        updateMedicationDetailsActivity = new UpdateMedicationDetailsActivity(medicationDao);
    }

    @Test
    public void handleRequest_request_updatesMedicationDetail() {
        // GIVEN
        String patientId = "patientId123";
        String medicationId = "medicationId";
        String medicationName = "MedicationName";
        String prescription = "Take one daily";
        String instructions = "With food";
        Medication.MEDICATION_STATUS medicationStatus = Medication.MEDICATION_STATUS.ACTIVE;

        UpdateMedicationDetailsRequest request = UpdateMedicationDetailsRequest.builder()
                .withPatientId(patientId)
                .withMedicationId(medicationId)
                .withMedicationName(medicationName)
                .withPrescription(prescription)
                .withInstructions(instructions)
                .withMedicationStatus(medicationStatus)
                .build();

        Medication medicationToUpdate = new Medication();
        medicationToUpdate.setPatientId(patientId);
        medicationToUpdate.setMedicationId(medicationId);
        medicationToUpdate.setMedicationName("ExistingMedicationName");
        medicationToUpdate.setPrescription("Take two daily");
        medicationToUpdate.setInstructions("No instructions");
        medicationToUpdate.setMedicationStatus(Medication.MEDICATION_STATUS.DISCONTINUED);

        when(medicationDao.getMedication(patientId, medicationId)).thenReturn(medicationToUpdate);
        when(medicationDao.saveMedication(medicationToUpdate)).thenReturn(medicationToUpdate);

        UpdateMedicationDetailsResult result = updateMedicationDetailsActivity.handleRequest(request);

        assertEquals(patientId, result.getMedicationModel().getPatientId());
        assertEquals(medicationId, result.getMedicationModel().getMedicationId());
        assertEquals(medicationName, result.getMedicationModel().getMedicationName());
        assertEquals(prescription, result.getMedicationModel().getPrescription());
        assertEquals(instructions, result.getMedicationModel().getInstructions());
        assertEquals(medicationStatus, result.getMedicationModel().getMedicationStatus());
    }
}
