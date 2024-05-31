package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.AddNotificationRequest;
import com.devyanan.CareCompass.activity.results.AddNotificationResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.NotificationDao;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.devyanan.CareCompass.models.NotificationModel;
import com.devyanan.CareCompass.utils.IdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.UUID;

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
    public AddNotificationResult handleRequest(final AddNotificationRequest request){//request is String, result is String
        log.info("Received AddVitalSignsRequest {}", request);

        Notification notification = new Notification();   // POJO Notification LocalDateTime
        notification.setPatientId(request.getPatientId());
        notification.setNotificationId(IdGenerator.generateId());
        notification.setNotificationTitle(request.getNotificationTitle());
        notification.setReminderContent(request.getReminderContent());
        notification.setScheduledTime(dateTimeConverter.unconvert(request.getScheduledTime()));//1， 把Request的String 变成LocalDateTime 给POJO用， POJO给DAO用  请看（2）
        notification.setReminderType(request.getReminderType()); //

        Notification result = notificationDao.addNotification(notification); //2 ， 把LocalDateTime 给POJO用, 存储到数据库里，但是 为什么dynamoDB里是String????? 因为POJO里面有：@DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)

        ModelConverter modelConverter = new ModelConverter();
        NotificationModel notificationModel = modelConverter.toNotificationModel(result);  //3 ， 把LocalDateTime结果 用modelConverter 转换成 String

        return AddNotificationResult.builder()
                .withNotificationModel(notificationModel)   //4, 返回String 结果 所以result is String
                .build();
    }
}
