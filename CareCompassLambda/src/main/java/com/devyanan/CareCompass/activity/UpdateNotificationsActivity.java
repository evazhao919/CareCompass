package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.UpdateNotificationsRequest;
import com.devyanan.CareCompass.activity.results.UpdateNotificationsResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.NotificationDao;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class UpdateNotificationsActivity {
    private final Logger log = LogManager.getLogger();
    private final NotificationDao notificationDao;
    @Inject
    public UpdateNotificationsActivity(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }
    public UpdateNotificationsResult handleRequest(final UpdateNotificationsRequest request){
        log.info("Received UpdateNotificationsRequest {}", request);

        Notification notification = notificationDao.getSingleNotificationByPatientIdAndReminderTime(request.getPatientId(), request.getReminderTime());

        return UpdateNotificationsResult.builder()
                .withNotificationModel(new ModelConverter().toNotificationModel(notification))
                .build();
    }
}
