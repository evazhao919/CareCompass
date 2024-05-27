package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.UpdateNotificationDetailsRequest;
import com.devyanan.CareCompass.activity.results.UpdateNotificationDetailsResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.NotificationDao;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.devyanan.CareCompass.exceptions.NotificationNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * This class handles the logic for updating notifications.
 */
public class UpdateNotificationDetailsActivity {
    private final Logger log = LogManager.getLogger();
    private final NotificationDao notificationDao;

    /**
     * Constructor for UpdateNotificationDetailsActivity.
     * @param notificationDao DAO for notifications
     */
    @Inject
    public UpdateNotificationDetailsActivity(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    /**
     * Handles the request to update a notification.
     * Retrieves the notification based on patient ID and reminder time,
     * then updates its title, content, and type before persisting the changes.
     * @param request The request containing the updated notification data
     * @return The result of the update operation
     * @throws NotificationNotFoundException if the notification to be updated is not found
     */
    public UpdateNotificationDetailsResult handleRequest(final UpdateNotificationDetailsRequest request){
        log.info("Received UpdateNotificationDetailsRequest {}", request);

        Notification notification = notificationDao.getSingleNotificationByScheduledTime(
                request.getPatientId(),
                request.getscheduledTime()
        );

        if (notification == null) {
            throw new NotificationNotFoundException("Notification not found");
        }

        notification.setNotificationTitle(request.getNotificationTitle());
        notification.setReminderContent(request.getReminderContent());
        notification.setReminderType(request.getReminderType());

        notificationDao.updateNotification(notification);

        return UpdateNotificationDetailsResult.builder()
                .withNotificationModel(new ModelConverter().toNotificationModel(notification))
                .build();
    }
}
