package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.UpdateMedicationDetailsRequest;
import com.devyanan.CareCompass.activity.results.UpdateMedicationDetailsResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.MedicationDao;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import com.devyanan.CareCompass.exceptions.MedicationNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * This class handles the logic for updating medication details.
 */
public class UpdateMedicationDetailsActivity {
    private final Logger log = LogManager.getLogger();
    private final MedicationDao medicationDao;

    /**
     * Constructor for UpdateMedicationDetailsActivity.
     *
     * @param medicationDao     DAO for medications
     */
    @Inject
    public UpdateMedicationDetailsActivity(MedicationDao medicationDao) {
        this.medicationDao = medicationDao;
    }

    /**
     * Handles the request to update medication details.
     * Retrieves the medication based on patient ID and medication name,
     * then updates its prescription and instructions before persisting the changes.
     * @param request The request containing the updated medication data
     * @return The result of the update operation
     * @throws MedicationNotFoundException if the medication to be updated is not found
     */
    public UpdateMedicationDetailsResult handleRequest(final UpdateMedicationDetailsRequest request) {///TODO this one is right!!change the notification acitivity
        log.info("Received UpdateMedicationDetailsRequest {}", request);

        Medication medication = medicationDao.getMedication(request.getPatientId(),request.getMedicationId());

        if (medication == null) {
            throw new MedicationNotFoundException("Medication not found");
        }

        medication.setMedicationName(request.getMedicationName());
        medication.setPrescription(request.getPrescription());
        medication.setInstructions(request.getInstructions());
        medication.setMedicationStatus(request.getMedicationStatus());

        medicationDao.updateMedication(medication);

        return UpdateMedicationDetailsResult.builder()
                .withMedicationModel(new ModelConverter().toMedicationModel(medication))
                .build();
    }
}
