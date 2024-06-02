package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.RetrieveMedicationsByStatusRequest;
import com.devyanan.CareCompass.activity.results.RetrieveMedicationsByStatusResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.MedicationDao;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import com.devyanan.CareCompass.exceptions.MedicationNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class RetrieveMedicationsByStatusActivity {
    private final Logger log = LogManager.getLogger();
    private final MedicationDao medicationDao;
    @Inject
    public RetrieveMedicationsByStatusActivity(MedicationDao medicationDao) {
        this.medicationDao = medicationDao;
    }
    public RetrieveMedicationsByStatusResult handleRequest(RetrieveMedicationsByStatusRequest request) {
        log.info("RetrieveMedicationsByStatusRequest {} received.", request);

        List<Medication> searchResults;
        try {
            searchResults = medicationDao.retrieveMedicationsByMedicationStatus(request.getPatientId(), Medication.MEDICATION_STATUS.valueOf(request.getMedicationStatus()));
        } catch (MedicationNotFoundException e) {
            throw new MedicationNotFoundException(String.format("No %s medications found.", request.getMedicationStatus()),
                    e.getCause());
        }
        return RetrieveMedicationsByStatusResult.builder()
                .withMedications(new ModelConverter().toMedicationModelList(searchResults))
                .build();
    }
}
