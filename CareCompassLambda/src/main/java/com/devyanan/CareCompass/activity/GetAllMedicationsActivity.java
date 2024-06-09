package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.GetAllMedicationsRequest;
import com.devyanan.CareCompass.activity.results.GetAllMedicationsResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.MedicationDao;
import com.devyanan.CareCompass.dynamodb.models.Medication;
import com.devyanan.CareCompass.exceptions.MedicationNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

/**
 * This class handles the logic for retrieving all medications for a patient.
 */
public class GetAllMedicationsActivity {
    private final Logger log = LogManager.getLogger();
    private final MedicationDao medicationDao;

    /**
     * Constructor for GetAllMedicationsActivity.
     * @param medicationDao DAO for medications
     */
    @Inject
    public GetAllMedicationsActivity(MedicationDao medicationDao) {
        this.medicationDao = medicationDao;
    }

    /**
     * Handles the request to retrieve all medications for a patient.
     * @param request The request containing the patient ID
     * @return The result containing the list of medications
     * @throws MedicationNotFoundException if no medications are found for the patient
     */
    public GetAllMedicationsResult handleRequest(GetAllMedicationsRequest request){
        log.info("GetAllMedicationsRequest received {}.",request);
        List<Medication> medicationList;
        try{
            medicationList = medicationDao.getAllMedications(request.getPatientId());
        } catch (MedicationNotFoundException e){
            log.error("MedicationList with PatientId {} is not found in the database. Error: {}", request.getPatientId(), e.getMessage());
            throw new MedicationNotFoundException(e.getMessage(), e.getCause());
        }
        return GetAllMedicationsResult.builder()
                .withMedicationModelList(new ModelConverter().toMedicationModelList(medicationList))
                .build();
        }
    }
