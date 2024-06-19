package com.devyanan.CareCompass.activity;

import com.devyanan.CareCompass.activity.requests.AddBloodGlucoseMeasurementRequest;
import com.devyanan.CareCompass.dynamodb.BloodGlucoseMeasurementDao;
import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import com.devyanan.CareCompass.exceptions.DatabaseAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class AddBloodGlucoseMeasurementActivityTest {

    @Mock // 使用 @Mock 注解模拟 BloodGlucoseMeasurementDao
    private BloodGlucoseMeasurementDao bloodGlucoseMeasurementDao;
    private AddBloodGlucoseMeasurementActivity addBloodGlucoseMeasurementActivity;

    @BeforeEach // 在每个测试方法之前运行此方法
    void setUp() {
        openMocks(this);
        addBloodGlucoseMeasurementActivity = new AddBloodGlucoseMeasurementActivity(bloodGlucoseMeasurementDao);
    }
//
//    @Test
//    public void handleRequest_withValidAttributes_addBloodGlucoseMeasurement() {
//        // Given
//        String patientId = "patientId123"; // 定义患者 ID
//        String actualCheckTime = "2024-06-19T12:50"; // 定义实际检查时间
//        double glucoseLevel = 100.0; // 定义血糖水平
//        BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT glucoseContext = BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT.AFTER_MEAL; // 定义血糖测量上下文
//        String comments = "No comments"; // 定义备注
//
//        // 创建 AddBloodGlucoseMeasurementRequest 对象
//        AddBloodGlucoseMeasurementRequest request = AddBloodGlucoseMeasurementRequest.builder()
//                .withPatientId(patientId)
//                .withActualCheckTime(actualCheckTime)
//                .withGlucoseLevel(glucoseLevel)
//                .withGlucoseContext(glucoseContext)
//                .withComments(comments)
//                .build();
//
//        AddBloodGlucoseMeasurementResult result = addBloodGlucoseMeasurementActivity.handleRequest(request);
//
//        assertEquals(patientId, result.getBloodGlucoseMeasurementModel().getPatientId());
//        assertEquals(glucoseLevel, result.getBloodGlucoseMeasurementModel().getGlucoseLevel());
//        assertEquals(glucoseContext, result.getBloodGlucoseMeasurementModel().getGlucoseContext());
//        assertEquals(comments, result.getBloodGlucoseMeasurementModel().getComments());
//
//        // 验证 saveBloodGlucoseMeasurement 方法是否被调用了一次
//        verify(bloodGlucoseMeasurementDao, times(1)).saveBloodGlucoseMeasurement(any(BloodGlucoseMeasurement.class));
//    }

    @Test
    public void handleRequest_databaseAccessException_throwsDatabaseAccessException() {
        // Given
        String patientId = "patientId456"; // 定义患者 ID
        String actualCheckTime = "2024-06-19T09:50"; // 定义实际检查时间
        double glucoseLevel = 100.0; // 定义血糖水平
        BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT glucoseContext = BloodGlucoseMeasurement.GLUCOSE_MEASUREMENT_CONTEXT.BEFORE_MEAL; // 定义血糖测量上下文
        String comments = "No comments"; // 定义备注

        // 创建 AddBloodGlucoseMeasurementRequest 对象
        AddBloodGlucoseMeasurementRequest request = AddBloodGlucoseMeasurementRequest.builder()
                .withPatientId(patientId)
                .withActualCheckTime(actualCheckTime)
                .withGlucoseLevel(glucoseLevel)
                .withGlucoseContext(glucoseContext)
                .withComments(comments)
                .build();

        // 模拟保存血糖测量数据的方法抛出 DatabaseAccessException 异常
        when(bloodGlucoseMeasurementDao.saveBloodGlucoseMeasurement(any(BloodGlucoseMeasurement.class)))
                .thenThrow(new DatabaseAccessException("Database error"));

        // 验证调用 handleRequest 方法时抛出 DatabaseAccessException 异常
        assertThrows(DatabaseAccessException.class, () -> addBloodGlucoseMeasurementActivity.handleRequest(request));
    }
}
