package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.UpdateBloodGlucoseMeasurementDetailsRequest;
import com.devyanan.CareCompass.activity.results.UpdateBloodGlucoseMeasurementDetailsResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.dynamodb.BloodGlucoseMeasurementDao;
import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UpdateBloodGlucoseMeasurementDetailsActivityTest {
    @Mock
    private BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao;

    private UpdateBloodGlucoseMeasurementDetailsActivity updateBloodGlucoseMeasurementDetailsActivity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        updateBloodGlucoseMeasurementDetailsActivity = new UpdateBloodGlucoseMeasurementDetailsActivity(bloodGlucoseMeasurementDao);
    }

    @Test
    public void handleRequest_request_updatesBloodGlucoseMeasurementDetail() {
        // GIVEN
        LocalDateTimeConverter timeConverter = new LocalDateTimeConverter();
        String patientId = "patientId123";
        LocalDateTime actualCheckTime = LocalDateTime.of(2024, 6, 19, 12, 50);
        double glucoseLevel = 100.0;
        BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT glucoseContext = BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT.AFTER_MEAL;
        String comments = "No comments";

        UpdateBloodGlucoseMeasurementDetailsRequest request = UpdateBloodGlucoseMeasurementDetailsRequest.builder()
                .withPatientId(patientId)
                .withActualCheckTime(timeConverter.convert(actualCheckTime))
                .withGlucoseLevel(glucoseLevel)
                .withGlucoseContext(glucoseContext)
                .withComments(comments)
                .build();

        BloodGlucoseMeasurement bloodGlucoseMeasurementToUpdate = new BloodGlucoseMeasurement();
        bloodGlucoseMeasurementToUpdate.setPatientId(patientId);
        bloodGlucoseMeasurementToUpdate.setActualCheckTime(actualCheckTime);
        bloodGlucoseMeasurementToUpdate.setGlucoseLevel(120.0);
        bloodGlucoseMeasurementToUpdate.setGlucoseContext(BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT.BEFORE_MEAL);
        bloodGlucoseMeasurementToUpdate.setComments("Check it before breakfast");

        when(bloodGlucoseMeasurementDao.getBloodGlucoseMeasurements(patientId, actualCheckTime)).thenReturn(bloodGlucoseMeasurementToUpdate);
        when(bloodGlucoseMeasurementDao.saveBloodGlucoseMeasurement(bloodGlucoseMeasurementToUpdate)).thenReturn(bloodGlucoseMeasurementToUpdate);

        // WHEN
        UpdateBloodGlucoseMeasurementDetailsResult result = updateBloodGlucoseMeasurementDetailsActivity.handleRequest(request);

        // THEN
        assertEquals(timeConverter.convert(actualCheckTime).trim(), result.getBloodGlucoseMeasurementModel().getActualCheckTime().trim());
        assertEquals(glucoseLevel, result.getBloodGlucoseMeasurementModel().getGlucoseLevel());
        assertEquals(glucoseContext, result.getBloodGlucoseMeasurementModel().getGlucoseContext());
        assertEquals(comments, result.getBloodGlucoseMeasurementModel().getComments());
    }
}
