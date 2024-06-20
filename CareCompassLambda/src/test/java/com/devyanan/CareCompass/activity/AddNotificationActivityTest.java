package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.AddNotificationRequest;
import com.devyanan.CareCompass.activity.results.AddNotificationResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.dynamodb.NotificationDao;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.devyanan.CareCompass.exceptions.DatabaseAccessException;
import com.devyanan.CareCompass.models.NotificationModel;
import com.devyanan.CareCompass.utils.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class AddNotificationActivityTest {

    @Mock
    private NotificationDao notificationDao;
    private AddNotificationActivity addNotificationActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        addNotificationActivity = new AddNotificationActivity(notificationDao);
    }

    @Test
    public void handleRequest_withValidAttributes_addNotificationSuccessfully() {
        // Given
        LocalDateTimeConverter timeConverter = new LocalDateTimeConverter();
        String patientId = "patientId123";
        String notificationTitle = "Test Notification";
        String reminderContent = "This is a test reminder";
        String scheduledTime = "2023-06-19T10:00";
        Notification.REMINDER_TYPE reminderType = Notification.REMINDER_TYPE.MEDICATION;

        AddNotificationRequest request = AddNotificationRequest.builder()
                .withPatientId(patientId)
                .withNotificationTitle(notificationTitle)
                .withReminderContent(reminderContent)
                .withScheduledTime(scheduledTime)
                .withReminderType(reminderType)
                .build();

        Notification savedNotification = new Notification();
        savedNotification.setPatientId(patientId);
        savedNotification.setNotificationId(IdGenerator.generateId());
        savedNotification.setNotificationTitle(notificationTitle);
        savedNotification.setReminderContent(reminderContent);
        savedNotification.setScheduledTime(timeConverter.unconvert(scheduledTime));
        savedNotification.setReminderType(reminderType);

        when(notificationDao.saveNotification(any(Notification.class))).thenReturn(savedNotification);

        // When
        AddNotificationResult result = addNotificationActivity.handleRequest(request);

        // Then
        verify(notificationDao).saveNotification(any(Notification.class));

        NotificationModel notificationModel = result.getNotificationModel();
        assertNotNull(notificationModel.getNotificationId());
        assertEquals(patientId, notificationModel.getPatientId());
        assertEquals(notificationTitle, notificationModel.getNotificationTitle());
        assertEquals(reminderContent, notificationModel.getReminderContent());
        assertEquals(scheduledTime, notificationModel.getScheduledTime());
        assertEquals(reminderType, notificationModel.getReminderType());
    }

    @Test
    public void handleRequest_databaseAccessException_throwsDatabaseAccessException() {
        // Given
        String patientId = "patientId456";
        String notificationTitle = "Test Notification";
        String reminderContent = "This is a test reminder";
        String scheduledTime = "2023-06-19T10:00";
        Notification.REMINDER_TYPE reminderType = Notification.REMINDER_TYPE.APPOINTMENT;

        AddNotificationRequest request = AddNotificationRequest.builder()
                .withPatientId(patientId)
                .withNotificationTitle(notificationTitle)
                .withReminderContent(reminderContent)
                .withScheduledTime(scheduledTime)
                .withReminderType(reminderType)
                .build();

        when(notificationDao.saveNotification(any(Notification.class)))
                .thenThrow(new DatabaseAccessException("Database error"));

        // Then
        assertThrows(DatabaseAccessException.class, () -> addNotificationActivity.handleRequest(request));
    }
}
