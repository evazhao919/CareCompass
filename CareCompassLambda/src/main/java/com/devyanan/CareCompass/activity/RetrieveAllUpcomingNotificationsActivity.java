package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.RetrieveAllUpcomingNotificationsRequest;
import com.devyanan.CareCompass.activity.results.RetrieveAllUpcomingNotificationsResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.NotificationDao;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.devyanan.CareCompass.exceptions.NotificationNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class RetrieveAllUpcomingNotificationsActivity {
    private final Logger log = LogManager.getLogger();
    private final NotificationDao notificationDao;

    /**
     * Constructor for RetrieveAllUpcomingNotificationsActivity.
     * @param notificationDao DAO for notifications
     */
    @Inject
    public RetrieveAllUpcomingNotificationsActivity(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    /**
     * Handles the request to retrieve all upcoming notifications for a patient.
     * @param request The request containing the patient ID
     * @return The result containing the list of notifications
     * @throws NotificationNotFoundException if no notifications are found for the patient
     */
    public RetrieveAllUpcomingNotificationsResult handleRequest(RetrieveAllUpcomingNotificationsRequest request){
        log.info("RetrieveAllUpcomingNotificationsRequest received {}.",request);
        List<Notification> notificationList;
        try{
            notificationList = notificationDao.RetrieveAllUpcomingNotifications(request.getPatientId());
        } catch (NotificationNotFoundException e){
            log.error("Notifications with PatientId {} is not found in database.",
                    request.getPatientId());
            throw new NotificationNotFoundException(e.getMessage(),e.getCause());
        }
        return RetrieveAllUpcomingNotificationsResult.builder()
                .withNotifications(new ModelConverter().toNotificationModelList(notificationList))
                .build();
    }

}
