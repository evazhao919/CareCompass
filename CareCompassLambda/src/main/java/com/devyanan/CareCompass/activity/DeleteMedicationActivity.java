package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.DeleteMedicationRequest;
import com.devyanan.CareCompass.activity.results.DeleteMedicationResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.MedicationDao;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * This class handles the logic for deleting a medication.
 */
public class DeleteMedicationActivity {
    private final Logger log = LogManager.getLogger();
    private final MedicationDao medicationDao;

    /**
     * Constructor for DeleteMedicationActivity.
     * @param medicationDao DAO for medications
     */
    @Inject
    public DeleteMedicationActivity(MedicationDao medicationDao) {
        this.medicationDao = medicationDao;
    }

    /**
     * Handles the request to delete a medication.
     * @param request The request containing the medication data for deletion
     * @return The result of the deletion operation
     */
    public DeleteMedicationResult handleRequest(final DeleteMedicationRequest request){
        log.info("Received DeleteMedicationRequest {}", request);
        Medication medication = new Medication();
        medication.setPatientId(request.getPatientId());
        medication.setMedicationId(request.getMedicationId());


        Medication result = medicationDao.deleteMedication(medication);

        return DeleteMedicationResult.builder()
                .withMedicationModel(new ModelConverter().toMedicationModel(result))
                .build();
    }
}