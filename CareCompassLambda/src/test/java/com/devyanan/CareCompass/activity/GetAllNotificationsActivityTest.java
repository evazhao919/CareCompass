package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.GetAllNotificationsRequest;
import com.devyanan.CareCompass.activity.results.GetAllNotificationsResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.NotificationDao;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.devyanan.CareCompass.models.NotificationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetAllNotificationsActivityTest {
    @Mock
    private NotificationDao notificationDao;

    private GetAllNotificationsActivity getAllNotificationsActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        getAllNotificationsActivity = new GetAllNotificationsActivity(notificationDao);
    }

    @Test
    public void handleRequest_withValidPatientId_returnAllNotifications() {
        LocalDateTimeConverter timeConverter = new LocalDateTimeConverter();

        String patientId = "patient123";
        List<Notification> notificationList = new ArrayList<>();

        Notification notification1 = new Notification();
        notification1.setPatientId(patientId);
        notification1.setNotificationId("notificationId123");
        notification1.setNotificationTitle("Notification 1");
        notification1.setReminderContent("Take the medication");
        notification1.setScheduledTime(timeConverter.unconvert("2023-06-18T10:00"));
        notification1.setReminderType(Notification.REMINDER_TYPE.MEDICATION);

        Notification notification2 = new Notification();
        notification2.setPatientId(patientId);
        notification2.setNotificationId("notificationId456");
        notification2.setNotificationTitle("Notification 2");
        notification2.setReminderContent("Take the medication");
        notification2.setScheduledTime(timeConverter.unconvert("2023-08-20T10:00"));
        notification2.setReminderType(Notification.REMINDER_TYPE.MEDICATION);

        notificationList.add(notification1);
        notificationList.add(notification2);

        when(notificationDao.getAllNotifications(patientId)).thenReturn(notificationList);

        GetAllNotificationsRequest request = GetAllNotificationsRequest.builder()
                .withPatientId(patientId)
                .build();

        GetAllNotificationsResult result = getAllNotificationsActivity.handleRequest(request);

        List<NotificationModel> expectedModelList = new ModelConverter().toNotificationModelList(notificationList);
        assertEquals(expectedModelList.size(), result.getNotificationModelList().size());

        NotificationModel expectedModel1 = expectedModelList.get(0);
        NotificationModel actualModel1 = result.getNotificationModelList().get(0);
        assertEquals(expectedModel1.getPatientId(), actualModel1.getPatientId());
        assertEquals(expectedModel1.getNotificationId(), actualModel1.getNotificationId());
        assertEquals(expectedModel1.getNotificationTitle(), actualModel1.getNotificationTitle());
        assertEquals(expectedModel1.getReminderContent(), actualModel1.getReminderContent());
        assertEquals(expectedModel1.getScheduledTime(), actualModel1.getScheduledTime());
        assertEquals(expectedModel1.getReminderType(), actualModel1.getReminderType());

        NotificationModel expectedModel2 = expectedModelList.get(1);
        NotificationModel actualModel2 = result.getNotificationModelList().get(1);
        assertEquals(expectedModel2.getPatientId(), actualModel2.getPatientId());
        assertEquals(expectedModel2.getNotificationId(), actualModel2.getNotificationId());
        assertEquals(expectedModel2.getNotificationTitle(), actualModel2.getNotificationTitle());
        assertEquals(expectedModel2.getReminderContent(), actualModel2.getReminderContent());
        assertEquals(expectedModel2.getScheduledTime(), actualModel2.getScheduledTime());
        assertEquals(expectedModel2.getReminderType(), actualModel2.getReminderType());
    }
}
