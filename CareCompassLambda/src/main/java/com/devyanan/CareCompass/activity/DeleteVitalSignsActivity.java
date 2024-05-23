package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.DeleteVitalSignsRequest;
import com.devyanan.CareCompass.activity.results.DeleteVitalSignsResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.VitalSignsDao;
import com.devyanan.CareCompass.dynamodb.models.VitalSigns;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class DeleteVitalSignsActivity {
    private final Logger log = LogManager.getLogger();
    private final VitalSignsDao vitalSignsDao;
    @Inject
    public DeleteVitalSignsActivity(VitalSignsDao vitalSignsDao) {
        this.vitalSignsDao = vitalSignsDao;
    }
    public DeleteVitalSignsResult handleRequest(final DeleteVitalSignsRequest request){
        log.info("Received DeleteVitalSignsRequest {}", request);

        VitalSigns vitalSigns = vitalSignsDao.deleteSingleVitalSigns(request.getPatientId(),request.getActualCheckTime());

        return DeleteVitalSignsResult.builder()
                .withVitalSignModel(new ModelConverter().toVitalSignsModel(vitalSigns))
                .build();
    }
}
