package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.GetAllVitalSignsRequest;
import com.devyanan.CareCompass.activity.results.GetAllVitalSignsResult;
import com.devyanan.CareCompass.converters.LocalDateTimeConverter;
import com.devyanan.CareCompass.converters.ModelConverter;
import com.devyanan.CareCompass.dynamodb.VitalSignsDao;
import com.devyanan.CareCompass.dynamodb.models.VitalSigns;
import com.devyanan.CareCompass.models.VitalSignsModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetAllVitalSignsActivityTest {
    @Mock
    private VitalSignsDao vitalSignsDao;

    private GetAllVitalSignsActivity getAllVitalSignsActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        getAllVitalSignsActivity = new GetAllVitalSignsActivity(vitalSignsDao);
    }

    @Test
    public void handleRequest_withValidPatientId_returnAllVitalSigns() {
        LocalDateTimeConverter timeConverter = new LocalDateTimeConverter();

        String patientId = "patient123";
        List<VitalSigns> vitalSignsList = new ArrayList<>();

        VitalSigns vitalSigns1 = new VitalSigns();
        vitalSigns1.setPatientId(patientId);
        vitalSigns1.setActualCheckTime(timeConverter.unconvert("2024-09-09T09:00"));
        vitalSigns1.setTemperature(97.8);
        vitalSigns1.setHeartRate(80);
        vitalSigns1.setPulse(75);
        vitalSigns1.setRespiratoryRate(16);
        vitalSigns1.setSystolicPressure(120);
        vitalSigns1.setDiastolicPressure(80);
        vitalSigns1.setMeanArterialPressure(90);
        vitalSigns1.setWeight(70.5);
        vitalSigns1.setPatientPosition(VitalSigns.PATIENT_POSITION.SITTING);
        vitalSigns1.setBloodOxygenLevel(98);
        vitalSigns1.setOxygenTherapy(VitalSigns.OXYGEN_THERAPY.NONE);
        vitalSigns1.setFlowDelivered(VitalSigns.FLOW_DELIVERED.NONE);
        vitalSigns1.setPatientActivity(VitalSigns.PATIENT_ACTIVITY.POST_EXERCISE);
        vitalSigns1.setComments("No comments");

        VitalSigns vitalSigns2 = new VitalSigns();
        vitalSigns2.setPatientId(patientId);
        vitalSigns2.setActualCheckTime(timeConverter.unconvert("2024-09-10T10:00"));
        vitalSigns2.setTemperature(98.2);
        vitalSigns2.setHeartRate(75);
        vitalSigns2.setPulse(70);
        vitalSigns2.setRespiratoryRate(18);
        vitalSigns2.setSystolicPressure(122);
        vitalSigns2.setDiastolicPressure(82);
        vitalSigns2.setMeanArterialPressure(92);
        vitalSigns2.setWeight(71.2);
        vitalSigns2.setPatientPosition(VitalSigns.PATIENT_POSITION.LAYING);
        vitalSigns2.setBloodOxygenLevel(97);
        vitalSigns2.setOxygenTherapy(VitalSigns.OXYGEN_THERAPY.NASAL_CANNULA);
        vitalSigns2.setFlowDelivered(VitalSigns.FLOW_DELIVERED.LOW_FLOW);
        vitalSigns2.setPatientActivity(VitalSigns.PATIENT_ACTIVITY.LAYING_DOWN);
        vitalSigns2.setComments("No comments");

        vitalSignsList.add(vitalSigns1);
        vitalSignsList.add(vitalSigns2);

        when(vitalSignsDao.getAllVitalSigns(patientId)).thenReturn(vitalSignsList);

        GetAllVitalSignsRequest request = GetAllVitalSignsRequest.builder()
                .withPatientId(patientId)
                .build();

        GetAllVitalSignsResult result = getAllVitalSignsActivity.handleRequest(request);

        List<VitalSignsModel> expectedModelList = new ModelConverter().toVitalSignsModelList(vitalSignsList);
        assertEquals(expectedModelList.size(), result.getVitalSignModelList().size());

        VitalSignsModel expectedModel1 = expectedModelList.get(0);
        VitalSignsModel actualModel1 = result.getVitalSignModelList().get(0);
        assertEquals(expectedModel1.getPatientId(), actualModel1.getPatientId());
        assertEquals(expectedModel1.getActualCheckTime(), actualModel1.getActualCheckTime());
        assertEquals(expectedModel1.getTemperature(), actualModel1.getTemperature());
        assertEquals(expectedModel1.getHeartRate(), actualModel1.getHeartRate());
        assertEquals(expectedModel1.getPulse(), actualModel1.getPulse());
        assertEquals(expectedModel1.getRespiratoryRate(), actualModel1.getRespiratoryRate());
        assertEquals(expectedModel1.getSystolicPressure(), actualModel1.getSystolicPressure());
        assertEquals(expectedModel1.getDiastolicPressure(), actualModel1.getDiastolicPressure());
        assertEquals(expectedModel1.getMeanArterialPressure(), actualModel1.getMeanArterialPressure());
        assertEquals(expectedModel1.getWeight(), actualModel1.getWeight());
        assertEquals(expectedModel1.getPatientPosition(), actualModel1.getPatientPosition());
        assertEquals(expectedModel1.getBloodOxygenLevel(), actualModel1.getBloodOxygenLevel());
        assertEquals(expectedModel1.getOxygenTherapy(), actualModel1.getOxygenTherapy());
        assertEquals(expectedModel1.getFlowDelivered(), actualModel1.getFlowDelivered());
        assertEquals(expectedModel1.getPatientActivity(), actualModel1.getPatientActivity());
        assertEquals(expectedModel1.getComments(), actualModel1.getComments());

        VitalSignsModel expectedModel2 = expectedModelList.get(1);
        VitalSignsModel actualModel2 = result.getVitalSignModelList().get(1);
        assertEquals(expectedModel2.getPatientId(), actualModel2.getPatientId());
        assertEquals(expectedModel2.getActualCheckTime(), actualModel2.getActualCheckTime());
        assertEquals(expectedModel2.getTemperature(), actualModel2.getTemperature());
        assertEquals(expectedModel2.getHeartRate(), actualModel2.getHeartRate());
        assertEquals(expectedModel2.getPulse(), actualModel2.getPulse());
        assertEquals(expectedModel2.getRespiratoryRate(), actualModel2.getRespiratoryRate());
        assertEquals(expectedModel2.getSystolicPressure(), actualModel2.getSystolicPressure());
        assertEquals(expectedModel2.getDiastolicPressure(), actualModel2.getDiastolicPressure());
        assertEquals(expectedModel2.getMeanArterialPressure(), actualModel2.getMeanArterialPressure());
        assertEquals(expectedModel2.getWeight(), actualModel2.getWeight());
        assertEquals(expectedModel2.getPatientPosition(), actualModel2.getPatientPosition());
        assertEquals(expectedModel2.getBloodOxygenLevel(), actualModel2.getBloodOxygenLevel());
        assertEquals(expectedModel2.getOxygenTherapy(), actualModel2.getOxygenTherapy());
        assertEquals(expectedModel2.getFlowDelivered(), actualModel2.getFlowDelivered());
        assertEquals(expectedModel2.getPatientActivity(), actualModel2.getPatientActivity());
        assertEquals(expectedModel2.getComments(), actualModel2.getComments());
    }
}
