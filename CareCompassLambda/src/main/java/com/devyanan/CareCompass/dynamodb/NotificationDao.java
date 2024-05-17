package com.devyanan.CareCompass.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.devyanan.CareCompass.dynamodb.models.notification;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.devyanan.CareCompass.exceptions.*;
import com.devyanan.CareCompass.metrics.MetricsConstants;
import com.devyanan.CareCompass.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationDao {
    //String patientId;
    //String notificationId;
    //String notificationTitle;
    //String reminderType;
    //String reminderContent;
    //String additionalNotes;
    //LocalDateTime reminderTime;
    private final DynamoDBMapper dynamoDBMapper;
    private final MetricsPublisher metricsPublisher;
    private final Logger log = LogManager.getLogger();

    public NotificationDao(DynamoDBMapper dynamoDBMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.metricsPublisher = metricsPublisher;
    }
    public Notification addNotification(Notification notification){


    }
    public boolean deleteNotification(Notification notification){

    }
    public getSingleNotification(String patientId, String notificationName){

    }
    public List<Notification> getAllNotification(String patientId){

    }
    public List<Notification> getNotificationsByType(List<Notification> notifications, String reminderType) {
        return notifications.stream()
                .filter(notification -> notification.getReminderType().equals(reminderType))
                .collect(Collectors.toList());
    }
    public List<Notification> getNotificationsByReminderTime(List<Notification> notifications, LocalDateTime reminderTime) {
        return notifications.stream()
                .filter(notification -> notification.getReminderTime().isEqual(reminderTime))
                .collect(Collectors.toList());
    }

}
