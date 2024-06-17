package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.GetAllVitalSignsRequest;
import com.devyanan.CareCompass.activity.results.GetAllVitalSignsResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.VitalSignsDao;
import com.devyanan.CareCompass.dynamodb.models.VitalSigns;
import com.devyanan.CareCompass.exceptions.VitalSignsNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

/**
 * This class handles the logic for retrieving all vital signs for a patient.
 */
public class GetAllVitalSignsActivity {
    private final Logger log = LogManager.getLogger();
    private final VitalSignsDao vitalSignsDao;

    /**
     * Constructor for GetAllVitalSignsActivity.
     * @param vitalSignsDao DAO for vital signs
     */
    @Inject
    public GetAllVitalSignsActivity(VitalSignsDao vitalSignsDao) {
        this.vitalSignsDao = vitalSignsDao;
    }

    /**
     * Handles the request to retrieve all vital signs for a patient.
     * @param request The request containing the patient ID
     * @return The result containing the list of vital signs
     * @throws VitalSignsNotFoundException if no vital signs are found for the patient
     */
    public GetAllVitalSignsResult handleRequest(GetAllVitalSignsRequest request){
        log.info("GetAllVitalSignsRequest received {}.",request);
        List<VitalSigns> vitalSignsList;
        try{
            vitalSignsList = vitalSignsDao.getAllVitalSigns(request.getPatientId());
        } catch (VitalSignsNotFoundException e){
            log.error("VitalSigns with PatientId {} are not found in the database. Error: {}", request.getPatientId(), e.getMessage());
            throw new VitalSignsNotFoundException(e.getMessage(),e.getCause());
        }
        return GetAllVitalSignsResult.builder()
                .withVitalSignModelList(new ModelConverter().toVitalSignsModelList(vitalSignsList))
                .build();
    }
}