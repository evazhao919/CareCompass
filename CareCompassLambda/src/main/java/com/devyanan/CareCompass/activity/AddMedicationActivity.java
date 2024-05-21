package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.AddMedicationRequest;
import com.devyanan.CareCompass.activity.results.AddMedicationResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.MedicationDao;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import com.devyanan.CareCompass.models.MedicationModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddMedicationActivity {
    private final Logger log = LogManager.getLogger();
    private final MedicationDao medicationDao;

    public AddMedicationActivity(MedicationDao medicationDao) {
        this.medicationDao = medicationDao;
    }
    public AddMedicationResult handleRequest(final AddMedicationRequest request){
        log.info("Received AddMedicationRequest {}", request);
        String medicationName = request.getMedicationName();
        String prescription = request.getPrescription();
        String instructions = request.getInstructions();

        //TODO check for invalid enter

        Medication medication = new Medication();
        medication.setPatientId(request.getPatientId());
        medication.setMedicationName(request.getMedicationName());
        medication.setPrescription(request.getPrescription());
        medication.setInstructions(request.getInstructions());

        Medication result = medicationDao.addMedication(medication);

        ModelConverter modelConverter = new ModelConverter();
        MedicationModel medicationModel = modelConverter.toMedicationModel(result);

        return AddMedicationResult.builder()
                .withMedicationModel(medicationModel)
                .build();
    }
}
