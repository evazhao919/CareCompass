package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.DeleteVitalSignsRequest;
import com.devyanan.CareCompass.activity.results.DeleteVitalSignsResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.VitalSignsDao;
import com.devyanan.CareCompass.dynamodb.models.VitalSigns;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * This class handles the logic for deleting vital signs.
 */
public class DeleteVitalSignsActivity {
    private final Logger log = LogManager.getLogger();
    private final VitalSignsDao vitalSignsDao;
    private final LocalDateTimeConverter dateTimeConverter;

    /**
     * Constructor for DeleteVitalSignsActivity.
     *
     * @param vitalSignsDao     DAO for vital signs
     * @param dateTimeConverter
     */
    @Inject
    public DeleteVitalSignsActivity(VitalSignsDao vitalSignsDao, LocalDateTimeConverter dateTimeConverter) {
        this.vitalSignsDao = vitalSignsDao;
        this.dateTimeConverter = dateTimeConverter;
    }

    /**
     * Handles the request to delete vital signs.
     * @param request The request containing the vital signs data for deletion
     * @return The result of the deletion operation
     */
    public DeleteVitalSignsResult handleRequest(final DeleteVitalSignsRequest request){//request is String, result is String
        log.info("Received DeleteVitalSignsRequest {}", request);
        //TODO

        VitalSigns ActualCheckTime = new VitalSigns(); // POJO Notification LocalDateTime
        vitalSigns.setActualCheckTime(dateTimeConverter.unconvert(request.getActualCheckTime())); //1， 把Request的String 变成LocalDateTime 给POJO用， POJO给DAO用  请看（2）

        VitalSigns result = vitalSignsDao.deleteSingleVitalSignsByActualCheckTime(vitalSigns);//2 ， 把LocalDateTime 给POJO用, 存储到数据库里，但是 为什么dynamoDB里是String????? 因为POJO里面有：@DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)




           // log.error("No VitalSigns found with patientId {} and actualCheckTime {}.",request.getPatientId(),request.getActualCheckTime());
        return DeleteVitalSignsResult.builder()
                .withVitalSignModel(new ModelConverter().toVitalSignsModel())
                .build();
    }
}
