package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.UpdateNotificationDetailsRequest;
import com.devyanan.CareCompass.activity.results.UpdateNotificationDetailsResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.dynamodb.NotificationDao;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class UpdateNotificationDetailsActivityTest {

    @Mock
    private NotificationDao notificationDao;

    private UpdateNotificationDetailsActivity updateNotificationDetailsActivity;

    @BeforeEach
    public void setup() {
        openMocks(this);
        updateNotificationDetailsActivity = new UpdateNotificationDetailsActivity(notificationDao);
    }

    @Test
    public void handleRequest_request_updatesNotificationDetail() {
        LocalDateTimeConverter timeConverter = new LocalDateTimeConverter();
        String patientId = "patientId123";
        String notificationId = "notificationId456";
        String notificationTitle = "Test Notification";
        String reminderContent = "This is a test reminder";
        String scheduledTime = "2023-06-19T10:00";
        Notification.REMINDER_TYPE reminderType = Notification.REMINDER_TYPE.MEDICATION;

        UpdateNotificationDetailsRequest request = UpdateNotificationDetailsRequest.builder()
                .withPatientId(patientId)
                .withNotificationId(notificationId)
                .withNotificationTitle(notificationTitle)
                .withReminderContent(reminderContent)
                .withScheduledTime(scheduledTime)
                .withReminderType(reminderType)
                .build();

        Notification notificationToUpdate = new Notification();
        notificationToUpdate.setPatientId(patientId);
        notificationToUpdate.setNotificationId(notificationId);
        notificationToUpdate.setNotificationTitle(notificationTitle);
        notificationToUpdate.setReminderContent(reminderContent);
        notificationToUpdate.setScheduledTime(timeConverter.unconvert(scheduledTime));
        notificationToUpdate.setReminderType(reminderType);

        when(notificationDao.getNotification(patientId, notificationId)).thenReturn(notificationToUpdate);
        when(notificationDao.saveNotification(notificationToUpdate)).thenReturn(notificationToUpdate);

        UpdateNotificationDetailsResult result = updateNotificationDetailsActivity.handleRequest(request);
        assertEquals(patientId, result.getNotificationModel().getPatientId());
        assertEquals(notificationId, result.getNotificationModel().getNotificationId());
        assertEquals(notificationTitle, result.getNotificationModel().getNotificationTitle());
        assertEquals(reminderContent, result.getNotificationModel().getReminderContent());
        assertEquals(scheduledTime, result.getNotificationModel().getScheduledTime());
        assertEquals(reminderType, result.getNotificationModel().getReminderType());
    }
}
