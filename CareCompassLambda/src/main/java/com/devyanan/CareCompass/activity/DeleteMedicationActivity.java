package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.DeleteMedicationRequest;
import com.devyanan.CareCompass.activity.results.DeleteMedicationResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.MedicationDao;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import com.devyanan.CareCompass.models.MedicationModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class DeleteMedicationActivity {
    private final Logger log = LogManager.getLogger();
    private final MedicationDao medicationDao;
    @Inject
    public DeleteMedicationActivity(MedicationDao medicationDao) {
        this.medicationDao = medicationDao;
    }
    public DeleteMedicationResult handleRequest(final DeleteMedicationRequest request){
        log.info("Received DeleteMedicationRequest {}", request);

        Medication result = medicationDao.getSingleMedication(request.getPatientId(),request.getMedicationName());

        ModelConverter modelConverter = new ModelConverter();
        MedicationModel medicationModel = modelConverter.toMedicationModel(result);

        return DeleteMedicationResult.builder()
                .withMedicationModel(medicationModel)
                .build();
    }
}
