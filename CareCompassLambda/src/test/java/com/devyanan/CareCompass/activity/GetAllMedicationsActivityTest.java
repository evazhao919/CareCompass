package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.GetAllMedicationsRequest;
import com.devyanan.CareCompass.activity.results.GetAllMedicationsResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.MedicationDao;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import com.devyanan.CareCompass.models.MedicationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetAllMedicationsActivityTest {
    @Mock
    private MedicationDao medicationDao;

    private GetAllMedicationsActivity getAllMedicationsActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        getAllMedicationsActivity = new GetAllMedicationsActivity(medicationDao);
    }

    @Test
    public void handleRequest_withValidPatientId_returnAllMedications() {

        String patientId = "patient123";
        List<Medication> medicationList = new ArrayList<>();

        Medication medication1 = new Medication();
        medication1.setPatientId(patientId);
        medication1.setMedicationId("medicationId123");
        medication1.setMedicationName("medicationName1");
        medication1.setPrescription("10g");
        medication1.setInstructions("Take after meal.");
        medication1.setMedicationStatus(Medication.MEDICATION_STATUS.ACTIVE);

        Medication medication2 = new Medication();
        medication1.setPatientId(patientId);
        medication1.setMedicationId("medicationId456");
        medication1.setMedicationName("medicationName2");
        medication1.setPrescription("20g");
        medication1.setInstructions("Take after meal.");
        medication1.setMedicationStatus(Medication.MEDICATION_STATUS.ACTIVE);

        medicationList.add(medication1);
        medicationList.add(medication2);


        when(medicationDao.getAllMedications(patientId)).thenReturn(medicationList);

        GetAllMedicationsRequest request = GetAllMedicationsRequest.builder()
                .withPatientId(patientId)
                .build();

        GetAllMedicationsResult result = getAllMedicationsActivity.handleRequest(request);


        List<MedicationModel> expectedModelList = new ModelConverter().toMedicationModelList(medicationList);
        assertEquals(expectedModelList.size(), result.getMedicationModelList().size());

        MedicationModel expectedModel1 = expectedModelList.get(0);
        MedicationModel actualModel1 = result.getMedicationModelList().get(0);
        assertEquals(expectedModel1.getPatientId(), actualModel1.getPatientId());
        assertEquals(expectedModel1.getMedicationId(), actualModel1.getMedicationId());
        assertEquals(expectedModel1.getMedicationName(), actualModel1.getMedicationName());
        assertEquals(expectedModel1.getPrescription(), actualModel1.getPrescription());
        assertEquals(expectedModel1.getInstructions(), actualModel1.getInstructions());
        assertEquals(expectedModel1.getMedicationStatus(), actualModel1.getMedicationStatus());

        MedicationModel expectedModel2 = expectedModelList.get(0);
        MedicationModel actualModel2 = result.getMedicationModelList().get(0);
        assertEquals(expectedModel2.getPatientId(), actualModel2.getPatientId());
        assertEquals(expectedModel2.getMedicationId(), actualModel2.getMedicationId());
        assertEquals(expectedModel2.getMedicationName(), actualModel2.getMedicationName());
        assertEquals(expectedModel2.getPrescription(), actualModel2.getPrescription());
        assertEquals(expectedModel2.getInstructions(), actualModel2.getInstructions());
        assertEquals(expectedModel2.getMedicationStatus(), actualModel2.getMedicationStatus());
    }
}
