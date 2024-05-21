package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.DeleteVitalSignsRequest;
import com.devyanan.CareCompass.activity.results.DeleteVitalSignsResult;
import com.devyanan.CareCompass.dynamodb.VitalSignsDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteVitalSignsActivity {
    private final Logger log = LogManager.getLogger();
    private final VitalSignsDao vitalSignsDao;

    public DeleteVitalSignsActivity(VitalSignsDao vitalSignsDao) {
        this.vitalSignsDao = vitalSignsDao;
    }
    public DeleteVitalSignsResult handleRequest(final DeleteVitalSignsRequest request){
        log.info("Received DeleteVitalSignsRequest {}", request);

    }
}
