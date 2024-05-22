package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.GetAllMedicationsRequest;
import com.devyanan.CareCompass.activity.results.GetAllMedicationsResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.MedicationDao;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import com.devyanan.CareCompass.exceptions.MedicationNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GetAllMedicationsActivity {
    private final Logger log = LogManager.getLogger();
    private final MedicationDao medicationDao;

    public GetAllMedicationsActivity(MedicationDao medicationDao) {
        this.medicationDao = medicationDao;
    }
    public GetAllMedicationsResult handleRequest(GetAllMedicationsRequest request){
        log.info("GetAllMedicationsRequest received {}.",request);
        List<Medication> medicationList;
        try{
            medicationList = medicationDao.getAllMedications(request.getPatientId());
        } catch (MedicationNotFoundException e){
            log.error("MedicationList with PatientId {} is not found in database.",
                    request.getPatientId());
            throw new MedicationNotFoundException(e.getMessage(), e.getCause());
        }
        return GetAllMedicationsResult.builder()
                .withMedicationModelList(new ModelConverter().toMedicationModelList(medicationList))
                .build();
        }
    }
