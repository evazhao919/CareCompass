package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.AddMedicationRequest;
import com.devyanan.CareCompass.activity.results.AddMedicationResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.MedicationDao;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import com.devyanan.CareCompass.models.MedicationModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * This class handles the logic for adding a medication.
 */
public class AddMedicationActivity {
    private final Logger log = LogManager.getLogger();
    private final MedicationDao medicationDao;

    /**
     * Constructor for AddMedicationActivity.
     * @param medicationDao DAO for medications
     */
    @Inject
    public AddMedicationActivity(MedicationDao medicationDao) {
        this.medicationDao = medicationDao;
    }

    /**
     * Handles the request to add a medication.
     * @param request The request containing the medication data
     * @return The result of adding the medication
     */
    public AddMedicationResult handleRequest(final AddMedicationRequest request){
        log.info("Received AddMedicationRequest {}", request);

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
