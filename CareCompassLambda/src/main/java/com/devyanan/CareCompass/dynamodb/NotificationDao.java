package com.devyanan.CareCompass.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.devyanan.CareCompass.exceptions.*;
import com.devyanan.CareCompass.metrics.MetricsConstants;
import com.devyanan.CareCompass.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class NotificationDao {
    private final DynamoDBMapper dynamoDBMapper;
    private final MetricsPublisher metricsPublisher;
    private final Logger log = LogManager.getLogger();

    public NotificationDao(DynamoDBMapper dynamoDBMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.metricsPublisher = metricsPublisher;
    }
    public Notification addNotification(Notification notification){
        if(notification == null){
            metricsPublisher.addCount(MetricsConstants.ADD_NOTIFICATION_NULL_OR_EMPTY_COUNT,1);
            log.info("Attempted to add a null notification.");
            throw new IllegalArgumentException("notification object or name cannot be null or empty.");
        }

        try {
            log.info("Attempting to add a notification: {}", notification);
            dynamoDBMapper.save(notification);
            metricsPublisher.addCount(MetricsConstants.ADD_NOTIFICATION_SUCCESS_COUNT,1);
            //log.info("Notification added successfully for user: {}", notification.getPatientId());
        } catch (AmazonDynamoDBException e) {
            //log.error("DynamoDB-specific error occurred while adding notification: {}", notification, e);
            throw new CustomDynamoDBException("Failed to add notification to the database due to DynamoDB-specific error", e);
        }catch (Exception e) {
            //log.error("Failed to add notification for user: {}", notification.getPatientId(), e);
            throw new DatabaseAccessException("Failed to add notification to the database", e);
        }

        return notification;
    }

    public boolean deleteNotification(Notification notification){
        if (notification == null) {
            log.warn("Attempted to delete a null or empty notification object.");
            metricsPublisher.addCount(MetricsConstants.DELETE_NOTIFICATION_NULL_OR_EMPTY_COUNT,1);
            return false;
        }

        try {
            log.info("Attempting to delete notification: {}", notification);
            this.dynamoDBMapper.delete(notification);
            metricsPublisher.addCount(MetricsConstants.DELETE_NOTIFICATION_SUCCESS_COUNT,1);
            //log.info("notification deleted successfully: {}", notification);
            return true;
        }catch (AmazonDynamoDBException e) {
            //log.error("DynamoDB-specific error occurred while deleting notification: {}", notification, e);
            throw new CustomDynamoDBException("Failed to delete notification from the database due to DynamoDB-specific error", e);
        } catch (Exception e) {
            //log.error("Failed to delete notification: {}", notification, e);
            throw new DatabaseAccessException("Failed to delete notification from the database", e);
        }
    }
    public Notification getSingleNotificationByPatientIdAndNotificationId(String patientId, String notificationId) {
        try{
            log.info("Attempting to get notification: {}", notificationId);
            Notification singlenotification = this.dynamoDBMapper.load(Notification.class, patientId, notificationId);

            if (singlenotification == null || singlenotification.getNotificationId() == null || singlenotification.getNotificationId().isEmpty()) {
                metricsPublisher.addCount(MetricsConstants.GET_SINGLE_NOTIFICATION_BY_NOTIFICATION_ID_NULL_OR_EMPTY_COUNT, 1);
                log.warn("No notification found for user: {} and notificationId: {}", patientId, notificationId);
                throw new NotificationNotFoundException("No notifications found for user: " + patientId + " and notificationId : " + notificationId);
            } else {
                metricsPublisher.addCount(MetricsConstants.GET_SINGLE_NOTIFICATION_BY_NOTIFICATION_ID__FOUND_COUNT, 1);
                log.info("Get a single notification successfully: {}", notificationId);
                return singlenotification;
            }
        } catch (DatabaseAccessException e){
            //log.error("Failed to access the database for user: {} and notification name: {}", patientId, notificationId), e);
            throw new DatabaseAccessException("Failed to access the database", e);
        }
    }

    public List<Notification> getAllNotifications(String patientId){
        try {
            log.info("Attempting to get all notifications for user: {}", patientId);
            Notification notification = new Notification();
            notification.setPatientId(patientId);

            DynamoDBQueryExpression<Notification> queryExpression = new DynamoDBQueryExpression<Notification>()
                    .withHashKeyValues(notification);
            QueryResultPage<Notification> results = dynamoDBMapper
                    .queryPage(Notification.class, queryExpression);

            if (results.getResults().isEmpty()) {
                metricsPublisher.addCount(MetricsConstants.GET_ALL_NOTIFICATIONS_NULL_OR_EMPTY_COUNT, 1);
                log.warn("No notifications found for user: {}", patientId);
                return Collections.emptyList();
            }
            metricsPublisher.addCount(MetricsConstants.GET_ALL_NOTIFICATIONS_NOTIFICATION_FOUND_COUNT, 1);
            return results.getResults();
        } catch (Exception e) {
            //log.error("Failed to access the database for user: {}", patientId, e);
            throw new DatabaseAccessException("Failed to access the database", e);
        }
    }
}
