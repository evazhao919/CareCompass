package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.DeleteVitalSignsRequest;
import com.devyanan.CareCompass.activity.results.DeleteVitalSignsResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.dynamodb.VitalSignsDao;
import com.devyanan.CareCompass.dynamodb.models.VitalSigns;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class DeleteVitalSignsActivityTest {

    @Mock
    private VitalSignsDao vitalSignsDao;

    private DeleteVitalSignsActivity deleteVitalSignsActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        deleteVitalSignsActivity = new DeleteVitalSignsActivity(vitalSignsDao);
    }

    @Test
    public void handleRequest_withValidPatientIdAndActualCheckTime_removesAndReturnsVitalSigns() {
        // Given
        LocalDateTimeConverter timeConverter = new LocalDateTimeConverter();

        String patientId = "patient123";
        String actualCheckTime =  "2023-06-19T16:51";

        VitalSigns vitalSignsToDelete = new VitalSigns();
        vitalSignsToDelete.setPatientId(patientId);
        vitalSignsToDelete.setActualCheckTime(timeConverter.unconvert(actualCheckTime));

        when(vitalSignsDao.deleteSingleVitalSignsByActualCheckTime(anyString(), any(LocalDateTime.class)))
                .thenReturn(vitalSignsToDelete);

        // When
        DeleteVitalSignsRequest request = DeleteVitalSignsRequest.builder()
                .withPatientId(patientId)
                .withActualCheckTime(actualCheckTime)
                .build();

        DeleteVitalSignsResult result = deleteVitalSignsActivity.handleRequest(request);

        verify(vitalSignsDao).deleteSingleVitalSignsByActualCheckTime(patientId, timeConverter.unconvert(actualCheckTime));

        assertEquals(patientId, result.getVitalSignModel().getPatientId());
        assertEquals(actualCheckTime, result.getVitalSignModel().getActualCheckTime());
    }
}
