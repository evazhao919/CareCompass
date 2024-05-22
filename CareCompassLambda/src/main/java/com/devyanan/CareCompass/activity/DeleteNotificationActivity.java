package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.DeleteNotificationRequest;
import com.devyanan.CareCompass.activity.results.DeleteNotificationResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.NotificationDao;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteNotificationActivity {
    private final Logger log = LogManager.getLogger();
    private final NotificationDao notificationDao;

    public DeleteNotificationActivity(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }
    public DeleteNotificationResult handleRequest(final DeleteNotificationRequest request){
        log.info("Received DeleteNotificationRequest {}", request);

        // TODO check for invalidation

        Notification notification = notificationDao.getSingleNotificationByPatientIdAndReminderTime(request.getPatientId(),request.getReminderTime());
        return DeleteNotificationResult.builder()
                .withNotificationModel(new ModelConverter().toNotificationModel(notification))
                .build();


    }
}
