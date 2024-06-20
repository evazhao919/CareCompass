package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.RetrieveAllUpcomingNotificationsRequest;
import com.devyanan.CareCompass.activity.results.RetrieveAllUpcomingNotificationsResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.NotificationDao;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.devyanan.CareCompass.models.NotificationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class RetrieveAllUpcomingNotificationsActivityTest {
    @Mock
    private NotificationDao notificationDao;

    private RetrieveAllUpcomingNotificationsActivity activity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        activity = new RetrieveAllUpcomingNotificationsActivity(notificationDao);
    }

    @Test
    public void handleRequest_withValidPatientId_returnAllUpcomingNotifications() {
        LocalDateTimeConverter timeConverter = new LocalDateTimeConverter();

        String patientId = "patient123";
        List<Notification> notificationList = new ArrayList<>();

        Notification notification = new Notification();
        notification.setPatientId(patientId);
        notification.setNotificationId("notificationId123");
        notification.setNotificationTitle("Notification 1");
        notification.setReminderContent("Take the medication");
        notification.setScheduledTime(timeConverter.unconvert("2023-06-18T10:00"));
        notification.setReminderType(Notification.REMINDER_TYPE.MEDICATION);

        Notification upcomingNotification = new Notification();
        upcomingNotification.setPatientId(patientId);
        upcomingNotification.setNotificationId("notificationId456");
        upcomingNotification.setNotificationTitle("Notification 2");
        upcomingNotification.setReminderContent("Take the medication");
        upcomingNotification.setScheduledTime(timeConverter.unconvert("2026-08-20T10:00"));
        upcomingNotification.setReminderType(Notification.REMINDER_TYPE.MEDICATION);

        notificationList.add(notification);
        notificationList.add(upcomingNotification);

        when(notificationDao.RetrieveAllUpcomingNotifications(patientId, LocalDateTime.now().withSecond(0).withNano(0)))
                .thenAnswer(invocation -> notificationList.stream()
                        .filter(n -> n.getScheduledTime().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList()));

        RetrieveAllUpcomingNotificationsRequest request = RetrieveAllUpcomingNotificationsRequest.builder()
                .withPatientId(patientId)
                .build();

        RetrieveAllUpcomingNotificationsResult result = activity.handleRequest(request);

        List<Notification> expectedNotifications = notificationList.stream()
                .filter(n -> n.getScheduledTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());

        List<NotificationModel> expectedModelList = new ModelConverter().toNotificationModelList(expectedNotifications);

        assertEquals(expectedModelList.size(), result.getNotifications().size());
    }
}
