package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.GetAllNotificationsRequest;
import com.devyanan.CareCompass.activity.results.GetAllNotificationsResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.NotificationDao;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.devyanan.CareCompass.exceptions.NotificationNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GetAllNotificationsActivity {
    private final Logger log = LogManager.getLogger();
    private final NotificationDao notificationDao;

    public GetAllNotificationsActivity(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }
    public GetAllNotificationsResult handleRequest(GetAllNotificationsRequest request){
        log.info("GetAllNotificationsRequest received {}.",request);
        List<Notification> notificationList;
        try{
            notificationList = notificationDao.getAllNotifications(request.getPatientId());
        } catch (NotificationNotFoundException e){
            log.error("Notifications with PatientId {} is not found in database.",
                    request.getPatientId());
            throw new NotificationNotFoundException(e.getMessage(),e.getCause());
        }
        return GetAllNotificationsResult.builder()
                .withNotificationModelList(new ModelConverter().toNotificationModelList(notificationList))
                .build();

    }
}
