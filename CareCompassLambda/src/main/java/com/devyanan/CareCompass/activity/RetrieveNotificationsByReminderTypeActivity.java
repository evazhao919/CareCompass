package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.RetrieveNotificationsByReminderTypeRequest;
import com.devyanan.CareCompass.activity.results.RetrieveNotificationsByReminderTypeResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.NotificationDao;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.devyanan.CareCompass.exceptions.NotificationNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class RetrieveNotificationsByReminderTypeActivity {
    private final Logger log = LogManager.getLogger();
    private final NotificationDao notificationDao;
    @Inject
    public RetrieveNotificationsByReminderTypeActivity(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }
    public RetrieveNotificationsByReminderTypeResult handleRequest(RetrieveNotificationsByReminderTypeRequest request) {
        log.info("RetrieveNotificationsByReminderTypeRequest {} received.", request);

        List<Notification> searchResults;
        try {
            searchResults = notificationDao.retrieveNotificationsByReminderType(request.getPatientId(), Notification.REMINDER_TYPE.valueOf(request.getReminderType()));
        } catch (NotificationNotFoundException e) {
            throw new NotificationNotFoundException(String.format("No %s notifications found.", request.getReminderType()),
                    e.getCause());
        }
        return RetrieveNotificationsByReminderTypeResult.builder()
                .withNotifications(new ModelConverter().toNotificationModelList(searchResults))
                .build();
    }
}