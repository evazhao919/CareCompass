package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.UpdateNotificationsRequest;
import com.devyanan.CareCompass.activity.results.UpdateNotificationsResult;
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
public class UpdateNotificationsActivity {
    private final Logger log = LogManager.getLogger();
    private final NotificationDao notificationDao;

    /**
     * Constructor for UpdateNotificationsActivity.
     * @param notificationDao DAO for notifications
     */
    @Inject
    public UpdateNotificationsActivity(NotificationDao notificationDao) {
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
    public UpdateNotificationsResult handleRequest(final UpdateNotificationsRequest request){
        log.info("Received UpdateNotificationsRequest {}", request);

        Notification notification = notificationDao.getSingleNotificationByPatientIdAndReminderTime(
                request.getPatientId(),
                request.getReminderTime()
        );

        if (notification == null) {
            throw new NotificationNotFoundException("Notification not found");
        }

        notification.setNotificationTitle(request.getNotificationTitle());
        notification.setReminderContent(request.getReminderContent());
        notification.setReminderType(request.getReminderType());

        notificationDao.updateNotification(notification);

        return UpdateNotificationsResult.builder()
                .withNotificationModel(new ModelConverter().toNotificationModel(notification))
                .build();
    }
}
