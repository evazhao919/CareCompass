package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.UpdateVitalSignsDetailsRequest;
import com.devyanan.CareCompass.activity.results.UpdateVitalSignsDetailsResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.dynamodb.VitalSignsDao;
import com.devyanan.CareCompass.dynamodb.models.VitalSigns;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class UpdateVitalSignsDetailsActivityTest {

    @Mock
    private VitalSignsDao vitalSignsDao;

    private UpdateVitalSignsDetailsActivity updateVitalSignsDetailsActivity;

    @BeforeEach
    public void setup() {
        openMocks(this);
        updateVitalSignsDetailsActivity = new UpdateVitalSignsDetailsActivity(vitalSignsDao);
    }

    @Test
    public void handleRequest_request_updatesVitalSignsDetails() {
        // GIVEN
        LocalDateTimeConverter timeConverter = new LocalDateTimeConverter();
        String patientId = "patientId123";
        String actualCheckTime = "2023-06-19T10:00";
        double temperature = 98.6;
        int heartRate = 80;
        int pulse = 70;
        int respiratoryRate = 18;
        int systolicPressure = 120;
        int diastolicPressure = 80;
        int meanArterialPressure = 90;
        double weight = 70.5;
        VitalSigns.PATIENT_POSITION patientPosition = VitalSigns.PATIENT_POSITION.SITTING;
        int bloodOxygenLevel = 95;
        VitalSigns.OXYGEN_THERAPY oxygenTherapy = VitalSigns.OXYGEN_THERAPY.NONE;
        VitalSigns.FLOW_DELIVERED flowDelivered = VitalSigns.FLOW_DELIVERED.LOW_FLOW;
        VitalSigns.PATIENT_ACTIVITY patientActivity = VitalSigns.PATIENT_ACTIVITY.SITTING;
        String comments = "Normal.";

        UpdateVitalSignsDetailsRequest request = UpdateVitalSignsDetailsRequest.builder()
                .withPatientId(patientId)
                .withActualCheckTime(actualCheckTime)
                .withTemperature(temperature)
                .withHeartRate(heartRate)
                .withPulse(pulse)
                .withRespiratoryRate(respiratoryRate)
                .withSystolicPressure(systolicPressure)
                .withDiastolicPressure(diastolicPressure)
                .withMeanArterialPressure(meanArterialPressure)
                .withWeight(weight)
                .withPatientPosition(patientPosition)
                .withBloodOxygenLevel(bloodOxygenLevel)
                .withOxygenTherapy(oxygenTherapy)
                .withFlowDelivered(flowDelivered)
                .withPatientActivity(patientActivity)
                .withComments(comments)
                .build();

        VitalSigns vitalSignsToUpdate = new VitalSigns();
        vitalSignsToUpdate.setPatientId(patientId);
        vitalSignsToUpdate.setActualCheckTime(timeConverter.unconvert(actualCheckTime));

        when(vitalSignsDao.getVitalSigns(patientId, timeConverter.unconvert(actualCheckTime))).thenReturn(vitalSignsToUpdate);
        when(vitalSignsDao.saveVitalSigns(vitalSignsToUpdate)).thenReturn(vitalSignsToUpdate);

        // WHEN
        UpdateVitalSignsDetailsResult result = updateVitalSignsDetailsActivity.handleRequest(request);

        // THEN
        assertEquals(actualCheckTime, result.getVitalSignModel().getActualCheckTime());
        assertEquals(temperature, result.getVitalSignModel().getTemperature());
        assertEquals(heartRate, result.getVitalSignModel().getHeartRate());
        assertEquals(pulse, result.getVitalSignModel().getPulse());
        assertEquals(respiratoryRate, result.getVitalSignModel().getRespiratoryRate());
        assertEquals(systolicPressure, result.getVitalSignModel().getSystolicPressure());
        assertEquals(diastolicPressure, result.getVitalSignModel().getDiastolicPressure());
        assertEquals(meanArterialPressure, result.getVitalSignModel().getMeanArterialPressure());
        assertEquals(weight, result.getVitalSignModel().getWeight());
        assertEquals(patientPosition, result.getVitalSignModel().getPatientPosition());
        assertEquals(bloodOxygenLevel, result.getVitalSignModel().getBloodOxygenLevel());
        assertEquals(oxygenTherapy, result.getVitalSignModel().getOxygenTherapy());
        assertEquals(flowDelivered, result.getVitalSignModel().getFlowDelivered());
        assertEquals(patientActivity, result.getVitalSignModel().getPatientActivity());
        assertEquals(comments, result.getVitalSignModel().getComments());
    }
}
