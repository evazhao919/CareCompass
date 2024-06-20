package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.RetrieveMedicationsByStatusRequest;
import com.devyanan.CareCompass.activity.results.RetrieveMedicationsByStatusResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.MedicationDao;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import com.devyanan.CareCompass.dynamodb.models.Medication.MEDICATION_STATUS;
import com.devyanan.CareCompass.models.MedicationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class RetrieveMedicationsByStatusActivityTest {
    @Mock
    private MedicationDao medicationDao;

    private RetrieveMedicationsByStatusActivity activity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        activity = new RetrieveMedicationsByStatusActivity(medicationDao);
    }

    @Test
    public void handleRequest_withValidPatientIdAndMedicationStatus_returnMedicationsOfSpecificStatus() {
        String patientId = "patient123";
        MEDICATION_STATUS medicationStatus = MEDICATION_STATUS.ACTIVE;

        List<Medication> medicationList = new ArrayList<>();

        Medication activeMedication = new Medication();
        activeMedication.setPatientId(patientId);
        activeMedication.setMedicationId("medicationId123");
        activeMedication.setMedicationName("Medication A");
        activeMedication.setMedicationStatus(MEDICATION_STATUS.ACTIVE);

        Medication inactiveMedication = new Medication();
        inactiveMedication.setPatientId(patientId);
        inactiveMedication.setMedicationId("medicationId456");
        inactiveMedication.setMedicationName("Medication B");
        inactiveMedication.setMedicationStatus(MEDICATION_STATUS.ACTIVE);

        medicationList.add(activeMedication);
        medicationList.add(inactiveMedication);

        List<Medication> activeMedications = new ArrayList<>();
        activeMedications.add(activeMedication);

        when(medicationDao.retrieveMedicationsByMedicationStatus(patientId, medicationStatus))
                .thenReturn(activeMedications);

        RetrieveMedicationsByStatusRequest request = RetrieveMedicationsByStatusRequest.builder()
                .withPatientId(patientId)
                .withMedicationStatus(medicationStatus.name())
                .build();

        RetrieveMedicationsByStatusResult result = activity.handleRequest(request);

        List<MedicationModel> expectedModelList = new ModelConverter().toMedicationModelList(activeMedications);

        assertEquals(expectedModelList.size(), result.getMedications().size());
    }
}
