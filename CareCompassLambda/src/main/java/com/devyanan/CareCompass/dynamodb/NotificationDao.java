package com.devyanan.CareCompass.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.devyanan.CareCompass.exceptions.*;
import com.devyanan.CareCompass.metrics.MetricsConstants;
import com.devyanan.CareCompass.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO class for managing Notification data in DynamoDB.
 */
@Singleton
public class NotificationDao {
    private final DynamoDBMapper dynamoDBMapper;
    private final MetricsPublisher metricsPublisher;
    private final Logger log = LogManager.getLogger();

    /**
     * Constructor for NotificationDao.
     *
     * @param dynamoDBMapper   The DynamoDBMapper instance.
     * @param metricsPublisher The MetricsPublisher instance.
     */
    @Inject
    public NotificationDao(DynamoDBMapper dynamoDBMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Adds a notification to the database.
     *
     * @param notification The notification object to add.
     * @return The added notification object.
     * @throws IllegalArgumentException If the notification object is null or empty.
     * @throws CustomDynamoDBException  If there is a DynamoDB-specific error.
     * @throws DatabaseAccessException  If there is an error accessing the database.
     */
    public Notification addNotification(Notification notification) {
        if (notification == null) {
            metricsPublisher.addCount(MetricsConstants.ADD_NOTIFICATION_NULL_OR_EMPTY_COUNT, 1);
            log.info("Attempted to add a null notification.");
            throw new IllegalArgumentException("notification object or name cannot be null or empty.");
        }

        try {
            log.info("Attempting to add a notification: {}", notification);
            dynamoDBMapper.save(notification);
            metricsPublisher.addCount(MetricsConstants.ADD_NOTIFICATION_SUCCESS_COUNT, 1);
            log.info("Notification added successfully for user: {}", notification.getPatientId());
        } catch (AmazonDynamoDBException e) {
            log.error("DynamoDB-specific error occurred while adding notification: {}", notification, e);
            throw new CustomDynamoDBException("Failed to add notification to the database due to DynamoDB-specific error", e);
        } catch (Exception e) {
            log.error("Failed to add notification for user: {}", notification.getPatientId(), e);
            throw new DatabaseAccessException("Failed to add notification to the database", e);
        }

        return notification;
    }

    /**
     * Deletes a notification from the database.
     *
     * @param notification The notification object to delete.
     * @return true if deletion was successful, false otherwise.
     * @throws CustomDynamoDBException If there is a DynamoDB-specific error.
     * @throws DatabaseAccessException If there is an error accessing the database.
     */
    public boolean deleteNotification(Notification notification) {
        if (notification == null) {
            log.warn("Attempted to delete a null or empty notification object.");
            metricsPublisher.addCount(MetricsConstants.DELETE_NOTIFICATION_NULL_OR_EMPTY_COUNT, 1);
            return false;
        }

        try {
            log.info("Attempting to delete notification: {}", notification);
            this.dynamoDBMapper.delete(notification);
            metricsPublisher.addCount(MetricsConstants.DELETE_NOTIFICATION_SUCCESS_COUNT, 1);
            log.info("notification deleted successfully: {}", notification);
            return true;
        } catch (AmazonDynamoDBException e) {
            log.error("DynamoDB-specific error occurred while deleting notification: {}", notification, e);
            throw new CustomDynamoDBException("Failed to delete notification from the database due to DynamoDB-specific error", e);
        } catch (Exception e) {
            log.error("Failed to delete notification: {}", notification, e);
            throw new DatabaseAccessException("Failed to delete notification from the database", e);
        }
    }

//    public Notification getSingleNotificationByPatientIdAndNotificationId(String patientId, String notificationId) {
//        try{
//            log.info("Attempting to get notification: {}", notificationId);
//            Notification singlenotification = this.dynamoDBMapper.load(Notification.class, patientId, notificationId);
//
//            if (singlenotification == null) {
//                metricsPublisher.addCount(MetricsConstants.GET_SINGLE_NOTIFICATION_BY_PATIENT_ID_AND_NOTIFICATION_ID_NULL_OR_EMPTY_COUNT, 1);
//                log.warn("No notification found for user: {} and notificationId: {}", patientId, notificationId);
//                throw new NotificationNotFoundException("No notifications found for user: " + patientId + " and notificationId : " + notificationId);
//            } else {
//                metricsPublisher.addCount(MetricsConstants.GET_SINGLE_NOTIFICATION_BY_PATIENT_ID_AND_NOTIFICATION_ID_FOUND_COUNT, 1);
//                log.info("Get a single notification successfully: {}", notificationId);
//                return singlenotification;
//            }
//        } catch (DatabaseAccessException e){
//            log.error("Failed to access the database for user: {} and notification id: {}", patientId, notificationId, e);
//            throw new DatabaseAccessException("Failed to access the database", e);
//        }
//    }

    /**
     * Retrieves a single notification based on patient ID and scheduled time.
     *
     * @param patientId     The ID of the patient.
     * @param scheduledTime The reminder time.
     * @return The retrieved notification.
     * @throws NotificationNotFoundException If the notification is not found.
     * @throws DatabaseAccessException       If there is an error accessing the database.
     */
    public Notification getSingleNotificationByScheduledTime(String patientId, String scheduledTime) {
        try {
            log.info("Attempting to get notification: {}", scheduledTime);
            Notification singleNotification = this.dynamoDBMapper.load(Notification.class, patientId, scheduledTime);

            if (singleNotification == null) {
                metricsPublisher.addCount(MetricsConstants.GET_SINGLE_NOTIFICATION_BY_PATIENT_ID_AND_SCHEDULED_TIME_NULL_OR_EMPTY_COUNT, 1);
                log.warn("No notification found for user: {} and scheduledTime: {}", patientId, scheduledTime);
                throw new NotificationNotFoundException("No notifications found for user: " + patientId + " and scheduledTime : " + scheduledTime);
            } else {
                metricsPublisher.addCount(MetricsConstants.GET_SINGLE_NOTIFICATION_PATIENT_ID_AND_SCHEDULED_TIME_FOUND_COUNT, 1);
                log.info("Get a single notification successfully: {}", scheduledTime);
                return singleNotification;
            }
        } catch (DatabaseAccessException e) {
            log.error("Failed to access the database for user: {} and scheduledTime: {}", patientId, scheduledTime, e);
            throw new DatabaseAccessException("Failed to access the database", e);
        }
    }

    /**
     * Retrieves all notifications for a specified patient.
     *
     * @param patientId The ID of the patient.
     * @return A list of notification objects.
     * @throws DatabaseAccessException If there is an error accessing the database.
     */
    public List<Notification> getAllNotifications(String patientId) {
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
            metricsPublisher.addCount(MetricsConstants.GET_ALL_NOTIFICATIONS_FOUND_COUNT, 1);
            return results.getResults();
        } catch (Exception e) {
            log.error("Failed to access the database for user: {}", patientId, e);
            throw new DatabaseAccessException("Failed to access the database", e);
        }
    }

    /**
     * Updates a notification in the database.
     *
     * @param updatedNotification The updated notification object.
     * @return The updated notification object.
     * @throws IllegalArgumentException If the updated notification object is null or empty.
     * @throws CustomDynamoDBException  If there is a DynamoDB-specific error.
     * @throws DatabaseAccessException  If there is an error accessing the database.
     */
    public Notification updateNotification(Notification updatedNotification) {
        if (updatedNotification == null) {
            log.warn("Attempted to update a null or empty notification object.");
            metricsPublisher.addCount(MetricsConstants.UPDATE_NOTIFICATION_NULL_OR_EMPTY_COUNT, 1);
            throw new IllegalArgumentException("Updated notification object cannot be null or empty.");
        }

        try {
            log.info("Attempting to update notification: {}", updatedNotification);
            dynamoDBMapper.save(updatedNotification);
            metricsPublisher.addCount(MetricsConstants.UPDATE_NOTIFICATION_SUCCESS_COUNT, 1);
            log.info("Notification updated successfully: {}", updatedNotification);
            return updatedNotification;
        } catch (AmazonDynamoDBException e) {
            log.error("DynamoDB-specific error occurred while updating notification: {}", updatedNotification, e);
            throw new CustomDynamoDBException("Failed to update notification in the database due to DynamoDB-specific error", e);
        } catch (Exception e) {
            log.error("Failed to update notification: {}", updatedNotification, e);
            throw new DatabaseAccessException("Failed to update notification in the database", e);
        }
    }

    public List<Notification> RetrieveAllUpcomingNotifications(String patientId) {
        try {
            log.info("Attempting to retrieve all upcoming notifications for user: {}", patientId);
            LocalDateTime currentTime = LocalDateTime.now();

            Map<String, Condition> rangeKeyConditions = new HashMap<>();
            Condition rangeCondition = new Condition()
                    .withComparisonOperator(ComparisonOperator.GT)
                    .withAttributeValueList(new AttributeValue().withS(currentTime.toString()));
            rangeKeyConditions.put("scheduledTime", rangeCondition);

            DynamoDBQueryExpression<Notification> queryExpression = new DynamoDBQueryExpression<Notification>()
                    .withHashKeyValues(new Notification())
                    .withRangeKeyConditions(rangeKeyConditions);

            QueryResultPage<Notification> results = dynamoDBMapper.queryPage(Notification.class, queryExpression);

            if (results.getResults().isEmpty()) {
                metricsPublisher.addCount(MetricsConstants.GET_ALL_UPCOMING_NOTIFICATIONS_BY_PATIENT_ID_AND_SCHEDULED_TIME_NULL_COUNT, 1);
                log.warn("No notifications found for user: {}", patientId);
                return Collections.emptyList();
            }

            metricsPublisher.addCount(MetricsConstants.GET_ALL_UPCOMING_NOTIFICATIONS_FOUND_COUNT, 1);
            return results.getResults();
        } catch (Exception e) {
            log.error("Failed to access the database for user: {}", patientId, e);
            throw new DatabaseAccessException("Failed to access the database", e);
        }
    }
}
