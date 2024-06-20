package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.AddVitalSignsRequest;
import com.devyanan.CareCompass.activity.results.AddVitalSignsResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.dynamodb.VitalSignsDao;
import com.devyanan.CareCompass.dynamodb.models.VitalSigns;
import com.devyanan.CareCompass.exceptions.DatabaseAccessException;
import com.devyanan.CareCompass.models.VitalSignsModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.MockitoAnnotations.openMocks;

public class AddVitalSignsRequestActivityTest {

    @Mock
    private VitalSignsDao vitalSignsDao;

    private AddVitalSignsRequestActivity addVitalSignsRequestActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        addVitalSignsRequestActivity = new AddVitalSignsRequestActivity(vitalSignsDao);
    }

    @Test
    public void handleRequest_withValidAttributes_addVitalSignsSuccessfully() {
        // Given
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

        AddVitalSignsRequest request = AddVitalSignsRequest.builder()
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

        VitalSigns savedVitalSigns = new VitalSigns();
        savedVitalSigns.setPatientId(patientId);
        savedVitalSigns.setActualCheckTime(timeConverter.unconvert(actualCheckTime));
        savedVitalSigns.setTemperature(temperature);
        savedVitalSigns.setHeartRate(heartRate);
        savedVitalSigns.setPulse(pulse);
        savedVitalSigns.setRespiratoryRate(respiratoryRate);
        savedVitalSigns.setSystolicPressure(systolicPressure);
        savedVitalSigns.setDiastolicPressure(diastolicPressure);
        savedVitalSigns.setMeanArterialPressure(meanArterialPressure);
        savedVitalSigns.setWeight(weight);
        savedVitalSigns.setPatientPosition(patientPosition);
        savedVitalSigns.setBloodOxygenLevel(bloodOxygenLevel);
        savedVitalSigns.setOxygenTherapy(oxygenTherapy);
        savedVitalSigns.setFlowDelivered(flowDelivered);
        savedVitalSigns.setPatientActivity(patientActivity);
        savedVitalSigns.setComments(comments);

        when(vitalSignsDao.saveVitalSigns(any(VitalSigns.class))).thenReturn(savedVitalSigns);

        // When
        AddVitalSignsResult result = addVitalSignsRequestActivity.handleRequest(request);

        // Then
        verify(vitalSignsDao).saveVitalSigns(any(VitalSigns.class));

        VitalSignsModel vitalSignsModel = result.getVitalSignModel();
        assertNotNull(vitalSignsModel);
        assertEquals(patientId, vitalSignsModel.getPatientId());
        assertEquals(actualCheckTime, vitalSignsModel.getActualCheckTime());
        assertEquals(temperature, vitalSignsModel.getTemperature());
        assertEquals(heartRate, vitalSignsModel.getHeartRate());
        assertEquals(pulse, vitalSignsModel.getPulse());
        assertEquals(respiratoryRate, vitalSignsModel.getRespiratoryRate());
        assertEquals(systolicPressure, vitalSignsModel.getSystolicPressure());
        assertEquals(diastolicPressure, vitalSignsModel.getDiastolicPressure());
        assertEquals(meanArterialPressure, vitalSignsModel.getMeanArterialPressure());
        assertEquals(weight, vitalSignsModel.getWeight());
        assertEquals(patientPosition, vitalSignsModel.getPatientPosition());
        assertEquals(bloodOxygenLevel, vitalSignsModel.getBloodOxygenLevel());
        assertEquals(oxygenTherapy, vitalSignsModel.getOxygenTherapy());
        assertEquals(flowDelivered, vitalSignsModel.getFlowDelivered());
        assertEquals(patientActivity, vitalSignsModel.getPatientActivity());
        assertEquals(comments, vitalSignsModel.getComments());
    }

    @Test
    public void handleRequest_databaseAccessException_throwsDatabaseAccessException() {
        // Given
        AddVitalSignsRequest request = new AddVitalSignsRequest("patientId456", "2023-06-19T10:00",
                98.6, 80, 70, 18, 120, 80, 90, 70.5, VitalSigns.PATIENT_POSITION.SITTING,
                95, VitalSigns.OXYGEN_THERAPY.NONE, VitalSigns.FLOW_DELIVERED.LOW_FLOW,
                VitalSigns.PATIENT_ACTIVITY.LAYING_DOWN, "Normal vital signs.");

        when(vitalSignsDao.saveVitalSigns(any(VitalSigns.class)))
                .thenThrow(new DatabaseAccessException("Database error"));

        // Then
        assertThrows(DatabaseAccessException.class, () -> addVitalSignsRequestActivity.handleRequest(request));
    }
}
