package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.RetrieveNotificationsByReminderTypeRequest;
import com.devyanan.CareCompass.activity.results.RetrieveNotificationsByReminderTypeResult;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.NotificationDao;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.devyanan.CareCompass.dynamodb.models.Notification.REMINDER_TYPE;
import com.devyanan.CareCompass.models.NotificationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class RetrieveNotificationsByReminderTypeActivityTest {
    @Mock
    private NotificationDao notificationDao;

    private RetrieveNotificationsByReminderTypeActivity activity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        activity = new RetrieveNotificationsByReminderTypeActivity(notificationDao);
    }

    @Test
    public void handleRequest_withValidPatientIdAndReminderType_returnNotificationsOfSpecificType() {
        String patientId = "patient123";
        REMINDER_TYPE reminderType = REMINDER_TYPE.MEDICATION;

        List<Notification> notificationList = new ArrayList<>();

        Notification medicationNotification = new Notification();
        medicationNotification.setPatientId(patientId);
        medicationNotification.setNotificationId("notificationId123");
        medicationNotification.setNotificationTitle("Medication Reminder");
        medicationNotification.setReminderContent("Take the medication");
        medicationNotification.setScheduledTime(LocalDateTime.now().plusDays(1));
        medicationNotification.setReminderType(REMINDER_TYPE.MEDICATION);

        Notification appointmentNotification = new Notification();
        appointmentNotification.setPatientId(patientId);
        appointmentNotification.setNotificationId("notificationId456");
        appointmentNotification.setNotificationTitle("Appointment Reminder");
        appointmentNotification.setReminderContent("Doctor's appointment");
        appointmentNotification.setScheduledTime(LocalDateTime.now().plusDays(2));
        appointmentNotification.setReminderType(REMINDER_TYPE.APPOINTMENT);

        notificationList.add(medicationNotification);
        notificationList.add(appointmentNotification);

        List<Notification> medicationNotifications = new ArrayList<>();
        medicationNotifications.add(medicationNotification);

        when(notificationDao.retrieveNotificationsByReminderType(patientId, reminderType))
                .thenReturn(medicationNotifications);

        RetrieveNotificationsByReminderTypeRequest request = RetrieveNotificationsByReminderTypeRequest.builder()
                .withPatientId(patientId)
                .withReminderType(reminderType.name())
                .build();

        RetrieveNotificationsByReminderTypeResult result = activity.handleRequest(request);

        List<NotificationModel> expectedModelList = new ModelConverter().toNotificationModelList(medicationNotifications);

        assertEquals(expectedModelList.size(), result.getNotifications().size());
    }
}
