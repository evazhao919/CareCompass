package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.GetAllNotificationsRequest;
import com.devyanan.CareCompass.activity.results.GetAllNotificationsResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.NotificationDao;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.devyanan.CareCompass.exceptions.NotificationNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;

/**
 * This class handles the logic for retrieving all notifications for a patient.
 */
public class GetAllNotificationsActivity {
    private final Logger log = LogManager.getLogger();
    private final NotificationDao notificationDao;

    /**
     * Constructor for GetAllNotificationsActivity.
     * @param notificationDao DAO for notifications
     */
    @Inject
    public GetAllNotificationsActivity(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    /**
     * Handles the request to retrieve all notifications for a patient.
     * @param request The request containing the patient ID
     * @return The result containing the list of notifications
     * @throws NotificationNotFoundException if no notifications are found for the patient
     */
    public GetAllNotificationsResult handleRequest(GetAllNotificationsRequest request){
        log.info("GetAllNotificationsRequest received {}.",request);
        List<Notification> notificationList;
        try{
            notificationList = notificationDao.getAllNotifications(request.getPatientId());
            notificationList.sort(new Comparator<Notification>() {
                @Override
                public int compare(Notification n1, Notification n2) {
                    return n1.getScheduledTime().compareTo(n2.getScheduledTime());
                }
            });
        } catch (NotificationNotFoundException e){
            log.error("Notifications with PatientId {} are not found in the database. Error: {}", request.getPatientId(), e.getMessage());
            throw new NotificationNotFoundException(e.getMessage(),e.getCause());
        }
        return GetAllNotificationsResult.builder()
                .withNotificationModelList(new ModelConverter().toNotificationModelList(notificationList))
                .build();
    }
}