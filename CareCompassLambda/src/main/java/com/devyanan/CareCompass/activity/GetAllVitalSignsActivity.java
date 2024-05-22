package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.GetAllVitalSignsRequest;
import com.devyanan.CareCompass.activity.results.GetAllVitalSignsResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.VitalSignsDao;
import com.devyanan.CareCompass.dynamodb.models.VitalSigns;
import com.devyanan.CareCompass.exceptions.VitalSignsNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GetAllVitalSignsActivity {
    private final Logger log = LogManager.getLogger();
    private final VitalSignsDao vitalSignsDao;

    public GetAllVitalSignsActivity(VitalSignsDao vitalSignsDao) {
        this.vitalSignsDao = vitalSignsDao;
    }
    public GetAllVitalSignsResult handleRequest(GetAllVitalSignsRequest request){
        log.info("GetAllVitalSignsRequest received {}.",request);
        List<VitalSigns> vitalSignsList;
        try{
            vitalSignsList = vitalSignsDao.getAllVitalSigns(request.getPatientId());
        } catch (VitalSignsNotFoundException e){
            log.error("VitalSigns with PatientId {} is not found in database.",
                    request.getPatientId());
            throw new VitalSignsNotFoundException(e.getMessage(),e.getCause());
        }
        return GetAllVitalSignsResult.builder()
                .withVitalSignModelList(new ModelConverter().toVitalSignsModelList(vitalSignsList))
                .build();
    }
}
