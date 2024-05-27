package com.devyanan.CareCompass.metrics;

/**
 * Constant values for use with metrics.
 */
public class MetricsConstants {

    // Service Metrics
    public static final String SERVICE = "Service";
    public static final String SERVICE_NAME = "CareCompassService";
    public static final String NAMESPACE_NAME = "U7/CareCompassService";

    //TODO method name and exception
    // Medication Metrics
    public static final String ADD_MEDICATION_NULL_OR_EMPTY_COUNT = "add_medication_null_or_empty_count";
    public static final String ADD_MEDICATION_SUCCESS_COUNT = "add_medication_success_count";
    public static final String DELETE_MEDICATION_NULL_OR_EMPTY_COUNT = "delete_medication_null_or_empty_count";
    public static final String DELETE_MEDICATION_SUCCESS_COUNT = "delete_medication_success_count";
    public static final String ADD_MEDICATION_TOTAL_COUNT = "add_medication_total_count";
    public static final String DELETE_MEDICATION_TOTAL_COUNT = "delete_medication_total_count";
    public static final String GET_SINGLE_MEDICATION_NULL_OR_EMPTY_COUNT = "get_single_medication_null_or_empty_count";
    public static final String GET_SINGLE_MEDICATION_FOUND_COUNT = "get_single_medication_found_count";
    public static final String GET_ALL_MEDICATIONS_NULL_OR_EMPTY_COUNT = "get_all_medications_null_or_empty_count";
    public static final String GET_ALL_MEDICATIONS_FOUND_COUNT = "get_all_medications_found_count";
    public static final String GET_SINGLE_MEDICATION_TOTAL_COUNT = "get_single_medication_total_count";
    public static final String GET_ALL_MEDICATIONS_TOTAL_COUNT = "get_all_medications_total_count";
    public static final String UPDATE_MEDICATION_NULL_OR_EMPTY_COUNT = "update_medication_null_or_empty_count";
    public static final String UPDATE_MEDICATION_SUCCESS_COUNT = "update_medication_success_count";
    public static final String RETRIEVE_BY_MEDICATION_STATUS_MEDICATION_NOT_FOUND_COUNT = "";
    public static final String RETRIEVE_BY_MEDICATION_STATUS_MEDICATION_FOUND_COUNT = "";
    public static final String DELETE_SINGLE_MEDICATION_TOTAL_COUNT = "";
    public static final String DELETE_SINGLE_MEDICATION_NULL_OR_EMPTY_COUNT = "";
    public static final String DELETE_SINGLE_MEDICATION_FOUND_COUNT = "";
    public static final String UPDATE_SINGLE_MEDICATION_TOTAL_COUNT = "";
    public static final String UPDATE_SINGLE_MEDICATION_NULL_OR_EMPTY_COUNT = "";
    public static final String UPDATE_SINGLE_MEDICATION_FOUND_COUNT = "";

    // Notification Metrics
    public static final String ADD_NOTIFICATION_NULL_OR_EMPTY_COUNT = "add_notification_null_or_empty_count";
    public static final String ADD_NOTIFICATION_SUCCESS_COUNT = "add_notification_success_count";
    public static final String DELETE_NOTIFICATION_NULL_OR_EMPTY_COUNT = "delete_notification_null_or_empty_count";
    public static final String DELETE_NOTIFICATION_SUCCESS_COUNT = "delete_notification_success_count";
    public static final String GET_SINGLE_NOTIFICATION_PATIENT_ID_AND_SCHEDULED_TIME_FOUND_COUNT = "get_single_notification_patient_id_and_scheduled_time_found_count";
    public static final String GET_SINGLE_NOTIFICATION_BY_PATIENT_ID_AND_SCHEDULED_TIME_NULL_OR_EMPTY_COUNT = "get_single_notification_by_patient_id_and_scheduledTime_time_null_or_empty_count";
    public static final String GET_ALL_NOTIFICATIONS_NULL_OR_EMPTY_COUNT = "get_all_notifications_null_or_empty_count";
    public static final String GET_ALL_NOTIFICATIONS_FOUND_COUNT = "get_all_notifications_found_count";
    public static final String UPDATE_NOTIFICATION_NULL_OR_EMPTY_COUNT = "update_notification_null_or_empty_count";
    public static final String UPDATE_NOTIFICATION_SUCCESS_COUNT = "update_notification_success_count";
    public static final String GET_ALL_UPCOMING_NOTIFICATIONS_BY_PATIENT_ID_AND_SCHEDULED_TIME_NULL_COUNT = "";
    public static final String GET_ALL_UPCOMING_NOTIFICATIONS_FOUND_COUNT = "";
    public static final String DELETE_SINGLE_NOTIFICATION_BY_PATIENT_ID_AND_SCHEDULED_TIME_NULL_OR_EMPTY_COUNT = "";
    public static final String DELETE_SINGLE_NOTIFICATION_PATIENT_ID_AND_SCHEDULED_TIME_FOUND_COUNT = "";



    // Blood Glucose Measurement Metrics
    public static final String ADD_BLOOD_GLUCOSE_MEASUREMENT_NULL_OR_EMPTY_COUNT = "add_blood_glucose_measurement_null_or_empty_count";
    public static final String ADD_BLOOD_GLUCOSE_MEASUREMENT_SUCCESS_COUNT = "add_blood_glucose_measurement_success_count";
    public static final String DELETE_BLOOD_GLUCOSE_MEASUREMENT_NULL_OR_EMPTY_COUNT = "delete_blood_glucose_measurement_null_or_empty_count";
    public static final String DELETE_BLOOD_GLUCOSE_MEASUREMENT_SUCCESS_COUNT = "delete_blood_glucose_measurement_success_count";
    public static final String GET_BLOOD_GLUCOSE_FOR_DATE_RANGE_TOTAL_COUNT = "get_blood_glucose_for_date_range_total_count";
    public static final String GET_ALL_BLOOD_GLUCOSE_MEASUREMENT_TOTAL_COUNT = "get_all_blood_glucose_measurement_total_count";
    public static final String GET_BLOOD_GLUCOSE_FOR_DATE_RANGE_NOT_FOUND_COUNT = "get_blood_glucose_for_date_range_not_found_count";
    public static final String GET_BLOOD_GLUCOSE_FOR_DATE_RANGE_FOUND_COUNT = "get_blood_glucose_for_date_range_found_count";
    public static final String DELETE_BLOOD_GLUCOSE_MEASUREMENT_TOTAL_COUNT = "delete_blood_glucose_measurement_total_count";
    public static final String ADD_BLOOD_GLUCOSE_MEASUREMENT_TOTAL_COUNT = "add_blood_glucose_measurement_total_count";
    public static final String GET_ALL_BLOOD_GLUCOSE_MEASUREMENT_NULL_OR_EMPTY_COUNT = "get_all_blood_glucose_measurement_null_or_empty_count";
    public static final String GET_ALL_BLOOD_GLUCOSE_MEASUREMENT_FOUND_COUNT = "get_all_blood_glucose_measurement_found_count";
    public static final String DELETE_SINGLE_BLOOD_GLUCOSE_MEASUREMENT_TOTAL_COUNT = "delete_single_blood_glucose_measurement_total_count";
    public static final String DELETE_SINGLE_BLOOD_GLUCOSE_MEASUREMENT_NULL_OR_EMPTY_COUNT = "delete_single_blood_glucose_measurement_null_or_empty_count";
    public static final String DELETE_SINGLE_BLOOD_GLUCOSE_MEASUREMENT_SUCCESS_COUNT = "delete_single_blood_glucose_measurement_success_count";

    // Vital Signs Metrics
    public static final String ADD_VITAL_SIGNS_NULL_OR_EMPTY_COUNT = "add_vital_signs_null_or_empty_count";
    public static final String ADD_VITAL_SIGNS_SUCCESS_COUNT = "add_vital_signs_success_count";
    public static final String DELETE_VITAL_SIGNS_NULL_OR_EMPTY_COUNT = "delete_vital_signs_null_or_empty_count";
    public static final String DELETE_VITAL_SIGNS_SUCCESS_COUNT = "delete_vital_signs_success_count";
    public static final String ADD_VITAL_SIGNS_TOTAL_COUNT = "add_vital_signs_total_count";
    public static final String DELETE_VITAL_SIGNS_TOTAL_COUNT = "delete_vital_signs_total_count";
    public static final String GET_ALL_VITAL_SIGNS_NULL_OR_EMPTY_COUNT = "get_all_vital_signs_null_or_empty_count";
    public static final String GET_ALL_VITAL_SIGNS_FOUND_COUNT = "get_all_vital_signs_found_count";
    public static final String GET_ALL_VITAL_SIGNS_TOTAL_COUNT = "get_all_vital_signs_total_count";
    public static final String GET_VITAL_SIGNS_FOR_DATE_RANGE_TOTAL_COUNT = "get_vital_signs_for_date_range_total_count";
    public static final String GET_VITAL_SIGNS_FOR_DATE_RANGE_FOUND_COUNT = "get_vital_signs_for_date_range_found_count";
    public static final String GET_VITAL_SIGNS_FOR_DATE_RANGE_NOT_FOUND_COUNT = "get_vital_signs_for_date_range_not_found_count";
    public static final String DELETE_SINGLE_VITAL_SIGNS_NULL_OR_EMPTY_COUNT = "delete_single_vital_signs_null_or_empty_count";
    public static final String DELETE_SINGLE_VITAL_SIGNS_SUCCESS_COUNT = "delete_single_vital_signs_success_count";
    public static final String DELETE_SINGLE_VITAL_SIGNS_TOTAL_COUNT = "delete_single_vital_signs_total_count";
}
