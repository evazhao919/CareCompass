package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.DeleteNotificationRequest;
import com.devyanan.CareCompass.activity.results.DeleteNotificationResult;
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

    /**
     * Constructor for DeleteNotificationActivity.
     * @param notificationDao DAO for notifications
     */
    @Inject
    public DeleteNotificationActivity(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    /**
     * Handles the request to delete a notification.
     * @param request The request containing the notification data for deletion
     * @return The result of the deletion operation
     */
    public DeleteNotificationResult handleRequest(final DeleteNotificationRequest request){
        log.info("Received DeleteNotificationRequest {}", request);

        Notification notification = new Notification();
        notification.setPatientId(request.getPatientId());
        notification.setNotificationId(request.getNotificationId());


        Notification result = notificationDao.deleteNotification(notification);
        return DeleteNotificationResult.builder()
                .withNotificationModel(new ModelConverter().toNotificationModel(result))
                .build();
    }
}