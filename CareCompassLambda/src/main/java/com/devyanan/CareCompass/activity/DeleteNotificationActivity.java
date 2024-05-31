package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.DeleteNotificationRequest;
import com.devyanan.CareCompass.activity.results.DeleteNotificationResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.NotificationDao;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * This class handles the logic for deleting a notification.
 */
public class DeleteNotificationActivity {
    private final Logger log = LogManager.getLogger();
    private final NotificationDao notificationDao;
    private final LocalDateTimeConverter dateTimeConverter;

    /**
     * Constructor for DeleteNotificationActivity.
     * @param notificationDao DAO for notifications
     */
    @Inject
    public DeleteNotificationActivity(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
        dateTimeConverter = new LocalDateTimeConverter();
    }

    /**
     * Handles the request to delete a notification.
     * @param request The request containing the notification data for deletion
     * @return The result of the deletion operation
     */
    public DeleteNotificationResult handleRequest(final DeleteNotificationRequest request){
        log.info("Received DeleteNotificationRequest {}", request);
        // TODO






        Notification notification = notificationDao.deleteSingleNotificationByScheduledTime(request.getPatientId(),request.getNotificationId());
        return DeleteNotificationResult.builder()
                .withNotificationModel(new ModelConverter().toNotificationModel(notification))
                .build();
    }
}
