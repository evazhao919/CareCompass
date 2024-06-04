package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.RetrieveAllUpcomingNotificationsRequest;
import com.devyanan.CareCompass.activity.results.RetrieveAllUpcomingNotificationsResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.NotificationDao;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.devyanan.CareCompass.exceptions.NotificationNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

public class RetrieveAllUpcomingNotificationsActivity {
    private final Logger log = LogManager.getLogger();
    private final NotificationDao notificationDao;
    private final LocalDateTimeConverter dateTimeConverter;

    /**
     * Constructor for RetrieveAllUpcomingNotificationsActivity.
     * @param notificationDao DAO for notifications
     */
    @Inject
    public RetrieveAllUpcomingNotificationsActivity(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
        this.dateTimeConverter = new LocalDateTimeConverter();
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
        LocalDateTime scheduledTime = LocalDateTime.now().withSecond(0).withNano(0);
        if(request.getScheduledTime() != null && !request.getScheduledTime().isBlank()){
           scheduledTime = dateTimeConverter.unconvert(request.getScheduledTime());
        }
        log.info("ScheduledTime use blank as null {}", scheduledTime);
            notificationList = notificationDao.RetrieveAllUpcomingNotifications(request.getPatientId(),scheduledTime);
        return RetrieveAllUpcomingNotificationsResult.builder()
                .withNotifications(new ModelConverter().toNotificationModelList(notificationList))
                .build();
    }
}