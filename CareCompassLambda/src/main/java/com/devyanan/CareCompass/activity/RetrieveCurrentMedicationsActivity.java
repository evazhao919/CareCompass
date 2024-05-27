package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.RetrieveCurrentMedicationsRequest;
import com.devyanan.CareCompass.activity.results.RetrieveCurrentMedicationsResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.MedicationDao;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import com.devyanan.CareCompass.exceptions.MedicationNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class RetrieveCurrentMedicationsActivity {
    private final Logger log = LogManager.getLogger();
    private final MedicationDao medicationDao;
    @Inject
    public RetrieveCurrentMedicationsActivity(MedicationDao medicationDao) {
        this.medicationDao = medicationDao;
    }
    public RetrieveCurrentMedicationsResult handleRequest(RetrieveCurrentMedicationsRequest request) {
        log.info("RetrieveCurrentMedicationsRequest {} received.", request);

        List<Medication> searchResults;
        try {
            searchResults = medicationDao.retrieveCurrentMedicationsByMedicationStatus(Medication.MEDICATION_STATUS.valueOf(request.getMedicationStatus()));
        } catch (MedicationNotFoundException e) {
            throw new MedicationNotFoundException(String.format("No %s medications found.", request.getMedicationStatus()),
                    e.getCause());
        }
        return RetrieveCurrentMedicationsResult.builder()
                .withMedications(new ModelConverter().toMedicationModelList(searchResults))
                .build();
    }
}
