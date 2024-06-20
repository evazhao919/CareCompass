package com.devyanan.CareCompass.dynamodb;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.devyanan.CareCompass.exceptions.*;
import com.devyanan.CareCompass.metrics.MetricsConstants;
import com.devyanan.CareCompass.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class NotificationDaoTest {

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @Mock
    private MetricsPublisher metricsPublisher;

    @InjectMocks
    private NotificationDao notificationDao;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testSaveNotification_NullNotification_ExceptionThrown() {

        assertThrows(IllegalArgumentException.class, () -> notificationDao.saveNotification(null));
        verify(metricsPublisher).addCount(eq(MetricsConstants.ADD_NOTIFICATION_NULL_OR_EMPTY_COUNT), eq(1.0));
    }

    @Test
    void testUpdateSingleNotificationByScheduledTime_NotFound_ExceptionThrown() {
        String patientId = "patient1";
        String scheduledTime = "2024-06-20T10:00:00";

        when(dynamoDBMapper.load(eq(Notification.class), eq(patientId), eq(scheduledTime))).thenReturn(null);

        assertThrows(NotificationNotFoundException.class, () -> notificationDao.updateSingleNotificationByScheduledTime(patientId, scheduledTime));
        verify(metricsPublisher).addCount(eq(MetricsConstants.GET_SINGLE_NOTIFICATION_BY_PATIENT_ID_AND_SCHEDULED_TIME_NULL_OR_EMPTY_COUNT), eq(1.0));
    }

    @Test
    void testDeleteNotification_ValidNotification_Success() {
        Notification notification = new Notification();
        notification.setPatientId("patient1");
        notification.setNotificationId("notif123");

        doNothing().when(dynamoDBMapper).delete(any(Notification.class));

        Notification result = notificationDao.deleteNotification(notification);

        assertNotNull(result);
        assertEquals(notification, result);
        verify(metricsPublisher).addCount(eq(MetricsConstants.DELETE_SINGLE_NOTIFICATION_ID_FOUND_COUNT), eq(1.0));
    }
    @Test
    void testUpdateNotification_ValidNotification_Success() {
        Notification notification = new Notification();
        notification.setPatientId("patient1");
        notification.setNotificationId("notif123");

        notificationDao.updateNotification(notification);

        verify(dynamoDBMapper).save(eq(notification));
        verify(metricsPublisher).addCount(eq(MetricsConstants.UPDATE_NOTIFICATION_SUCCESS_COUNT), eq(1.0));
    }

    @Test
    void testUpdateNotification_NullNotification_ExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> notificationDao.updateNotification(null));
        verify(metricsPublisher).addCount(eq(MetricsConstants.UPDATE_NOTIFICATION_NULL_OR_EMPTY_COUNT), eq(1.0));
    }

    @Test
    void testGetNotification_ValidInput_Success() {
        String patientId = "patient1";
        String notificationId = "notif123";

        Notification notification = new Notification();
        notification.setPatientId(patientId);
        notification.setNotificationId(notificationId);

        when(dynamoDBMapper.load(eq(Notification.class), eq(patientId), eq(notificationId))).thenReturn(notification);

        Notification result = notificationDao.getNotification(patientId, notificationId);

        assertNotNull(result);
        assertEquals(notification, result);
        verify(metricsPublisher).addCount(eq(MetricsConstants.GET_SINGLE_NOTIFICATION_BY_PATIENT_ID_AND_NOTIFICATION_ID_FOUND_COUNT), eq(1.0));
    }

    @Test
    void testGetNotification_NotFound_ExceptionThrown() {
        String patientId = "patient1";
        String notificationId = "notif123";

        when(dynamoDBMapper.load(eq(Notification.class), eq(patientId), eq(notificationId))).thenReturn(null);

        assertThrows(NotificationNotFoundException.class, () -> notificationDao.getNotification(patientId, notificationId));
        verify(metricsPublisher).addCount(eq(MetricsConstants.GET_SINGLE_NOTIFICATION_BY_PATIENT_ID_AND_NOTIFICATION_ID_NULL_OR_EMPTY_COUNT), eq(1.0));
    }
}
