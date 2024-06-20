package com.devyanan.CareCompass.converters;

import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import com.devyanan.CareCompass.models.BloodGlucoseMeasurementModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModelConverterTest {
    private ModelConverter modelConverter = new ModelConverter();

    @Test
    public void toBloodGlucoseMeasurementModel_withbloodGlucoseMeasurement_convertsToModel() {

        LocalDateTimeConverter timeConverter = new LocalDateTimeConverter();
        BloodGlucoseMeasurement bloodGlucoseMeasurement = new BloodGlucoseMeasurement();
        bloodGlucoseMeasurement.setPatientId("patientId");
        bloodGlucoseMeasurement.setActualCheckTime(timeConverter.unconvert("2016-09-09T09:09"));
        bloodGlucoseMeasurement.setGlucoseLevel(100.5);
        bloodGlucoseMeasurement.setGlucoseContext(BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT.BEFORE_MEAL);
        bloodGlucoseMeasurement.setComments("No comments");


        BloodGlucoseMeasurementModel model = modelConverter.toBloodGlucoseMeasurementModel(bloodGlucoseMeasurement);

        assertEquals(bloodGlucoseMeasurement.getPatientId(), model.getPatientId());
        assertEquals(bloodGlucoseMeasurement.getActualCheckTime().toString(), model.getActualCheckTime());
        assertEquals(bloodGlucoseMeasurement.getGlucoseLevel(), model.getGlucoseLevel());
        assertEquals(bloodGlucoseMeasurement.getGlucoseContext(), model.getGlucoseContext()); // Compare string representations
        assertEquals(bloodGlucoseMeasurement.getComments(), model.getComments());
    }
    @Test
    public void toBloodGlucoseMeasurementModelList_withbloodGlucoseMeasurementList_convertsToModelList() {

        LocalDateTimeConverter timeConverter = new LocalDateTimeConverter();
        BloodGlucoseMeasurement bloodGlucoseMeasurement1 = new BloodGlucoseMeasurement();
        bloodGlucoseMeasurement1.setPatientId("patientId1");
        bloodGlucoseMeasurement1.setActualCheckTime(timeConverter.unconvert("2016-09-09T09:09"));
        bloodGlucoseMeasurement1.setGlucoseLevel(100.5);
        bloodGlucoseMeasurement1.setGlucoseContext(BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT.BEFORE_MEAL);
        bloodGlucoseMeasurement1.setComments("No comments");

        BloodGlucoseMeasurement bloodGlucoseMeasurement2 = new BloodGlucoseMeasurement();
        bloodGlucoseMeasurement2.setPatientId("patientId2");
        bloodGlucoseMeasurement2.setActualCheckTime(LocalDateTime.now());
        bloodGlucoseMeasurement2.setGlucoseLevel(105.5);
        bloodGlucoseMeasurement2.setGlucoseContext(BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT.AFTER_MEAL);
        bloodGlucoseMeasurement2.setComments("No comments");

        List<BloodGlucoseMeasurement> bloodGlucoseMeasurementList = new ArrayList<>();
        bloodGlucoseMeasurementList.add(bloodGlucoseMeasurement1);
        bloodGlucoseMeasurementList.add(bloodGlucoseMeasurement2);

        List<BloodGlucoseMeasurementModel> modelList = modelConverter.toBloodGlucoseMeasurementModelList(bloodGlucoseMeasurementList);

        assertEquals(2, modelList.size());
        assertEquals(bloodGlucoseMeasurement1.getPatientId(), modelList.get(0).getPatientId());
        assertEquals(bloodGlucoseMeasurement2.getPatientId(), modelList.get(1).getPatientId());
    }
}
