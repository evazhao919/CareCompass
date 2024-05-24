package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.AddNotificationRequest;
import com.devyanan.CareCompass.activity.results.AddNotificationResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.NotificationDao;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.devyanan.CareCompass.models.NotificationModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class AddNotificationActivity {
    private final Logger log = LogManager.getLogger();
    private final NotificationDao notificationDao;
    private final LocalDateTimeConverter dateTimeConverter;

    @Inject
    public AddNotificationActivity(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
        this.dateTimeConverter = new LocalDateTimeConverter();
    }

    public AddNotificationResult handleRequest(final AddNotificationRequest request){
        log.info("Received AddVitalSignsRequest {}", request);
        String notificationId = request.getNotificationId();
        String notificationTitle = request.getNotificationTitle();
        String reminderContent = request.getReminderContent();
        String reminderTime = request.getReminderTime();

        //TODO check for invalid enter

        Notification notification = new Notification();
        notification.setPatientId(request.getPatientId());
        notification.setNotificationTitle(request.getNotificationTitle());
        notification.setReminderContent(request.getReminderContent());
        notification.setReminderTime(dateTimeConverter.unconvert(request.getReminderTime()));

        Notification result = notificationDao.addNotification(notification);

        ModelConverter modelConverter = new ModelConverter();
        NotificationModel notificationModel = modelConverter.toNotificationModel(result);

        return AddNotificationResult.builder()
                .withNotificationModel(notificationModel)
                .build();
    }
}
