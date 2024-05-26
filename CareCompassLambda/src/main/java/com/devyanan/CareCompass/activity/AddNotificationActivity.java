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

/**
 * This class handles the logic for adding a notification.
 */
public class AddNotificationActivity {
    private final Logger log = LogManager.getLogger();
    private final NotificationDao notificationDao;
    private final LocalDateTimeConverter dateTimeConverter;

    /**
     * Constructor for AddNotificationActivity.
     * @param notificationDao DAO for notifications
     */
    @Inject
    public AddNotificationActivity(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
        this.dateTimeConverter = new LocalDateTimeConverter();
    }

    /**
     * Handles the request to add a notification.
     * @param request The request containing the notification data
     * @return The result of adding the notification
     */
    public AddNotificationResult handleRequest(final AddNotificationRequest request){
        log.info("Received AddVitalSignsRequest {}", request);

        Notification notification = new Notification();
        notification.setPatientId(request.getPatientId());
        notification.setNotificationTitle(request.getNotificationTitle());
        notification.setReminderContent(request.getReminderContent());
        notification.setReminderTime(dateTimeConverter.unconvert(request.getReminderTime()));
        notification.setReminderType(request.getReminderType());

        Notification result = notificationDao.addNotification(notification);

        ModelConverter modelConverter = new ModelConverter();
        NotificationModel notificationModel = modelConverter.toNotificationModel(result);

        return AddNotificationResult.builder()
                .withNotificationModel(notificationModel)
                .build();
    }
}
