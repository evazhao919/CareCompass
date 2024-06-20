package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.DeleteNotificationRequest;
import com.devyanan.CareCompass.activity.results.DeleteNotificationResult;
import com.devyanan.CareCompass.dynamodb.NotificationDao;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class DeleteNotificationActivityTest {
    @Mock
    private NotificationDao notificationDao;

    private DeleteNotificationActivity deleteNotificationActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        deleteNotificationActivity = new DeleteNotificationActivity(notificationDao);
    }

    @Test
    public void handleRequest_withValidPatientIdAndNotificationId_removesAndReturnsNotification() {
        // Given
        String patientId = "patient123";
        String notificationId =  "2023-06-19T16:51";

        Notification notificationToDelete = new Notification();
        notificationToDelete.setPatientId(patientId);
        notificationToDelete.setNotificationId(notificationId);

        when(notificationDao.deleteNotification(notificationToDelete))
                .thenReturn(notificationToDelete);

        // When
        DeleteNotificationRequest request = DeleteNotificationRequest.builder()
                .withPatientId(patientId)
                .withNotificationId(notificationId)
                .build();

        DeleteNotificationResult result = deleteNotificationActivity.handleRequest(request);

        verify(notificationDao).deleteNotification(notificationToDelete);

        assertEquals(patientId, result.getNotificationModel().getPatientId());
        assertEquals(notificationId, result.getNotificationModel().getNotificationId());
    }
}
