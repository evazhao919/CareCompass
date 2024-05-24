package com.devyanan.CareCompass.utils;

import com.devyanan.CareCompass.dynamodb.models.BloodGlucoseMeasurement;
import com.devyanan.CareCompass.dynamodb.models.Notification;
import com.devyanan.CareCompass.exceptions.InvalidAttributeValueException;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

/**
 * Utility class for input validation.
 */
public class InputValidationUtils {
    private static final int MAX_ID_LENGTH = 8;

    /**
     * Checks if a LocalDateTime instance is valid.
     *
     * @param actualCheckTime The LocalDateTime instance to validate.
     * @throws IllegalArgumentException if actualCheckTime is null.
     */
    public static void isValidLocalDateTime(LocalDateTime actualCheckTime) {
        if (actualCheckTime == null) {
            throw new IllegalArgumentException("Actual check time cannot be null.");
        }
    }

    /**
     * Checks if a glucose level is valid.
     *
     * @param glucoseLevel The glucose level to validate.
     * @throws IllegalArgumentException if glucoseLevel is not positive or NaN.
     */
    public static void isValidGlucoseLevel(double glucoseLevel) {
        if (Double.isNaN(glucoseLevel) || glucoseLevel <= 0) {
            throw new IllegalArgumentException("Glucose level must be a positive number and not NaN.");
        }
    }

    /**
     * Checks if a glucose context is valid.
     *
     * @param glucoseContext The glucose context to validate.
     * @throws IllegalArgumentException if glucoseContext is null.
     */
    public static void isValidGlucoseContext(BloodGlucoseMeasurement.GlucoseMeasurementContext glucoseContext) {
        if (glucoseContext == null) {
            throw new IllegalArgumentException("Glucose context cannot be null.");
        }
    }

    /**
     * Checks if comments are valid.
     *
     * @param comments The comments to validate.
     * @throws IllegalArgumentException if comments are null or empty.
     */
    public static void isComments(String comments) {
        if (comments == null || comments.isEmpty()) {
            throw new IllegalArgumentException("Comments cannot be null or empty");
        }
    }

    /**
     * Checks if a medication name is valid.
     *
     * @param medicationName The medication name to validate.
     * @throws InvalidAttributeValueException if medicationName is null or empty.
     */
    public static void isValidMedicationName(String medicationName) {
        if (medicationName == null || medicationName.isEmpty()) {
            throw new InvalidAttributeValueException("Medication name cannot be null or empty");
        }
    }

    /**
     * Checks if a prescription is valid.
     *
     * @param prescription The prescription to validate.
     * @throws InvalidAttributeValueException if prescription is null or empty.
     */
    public static void isPrescriptionValid(String prescription) {
        if (prescription == null || prescription.isEmpty()) {
            throw new InvalidAttributeValueException("Prescription cannot be null or empty");
        }
    }

    /**
     * Checks if instructions are valid.
     *
     * @param instructions The instructions to validate.
     * @throws InvalidAttributeValueException if instructions are null or empty.
     */
    public static void areInstructionsValid(String instructions) {
        if (instructions == null || instructions.isEmpty()) {
            throw new InvalidAttributeValueException("Instructions cannot be null or empty");
        }
    }

    /**
     * Generates a random notification ID.
     *
     * @return A random alphanumeric ID.
     */
    public static String generateNotificationId() {
        return RandomStringUtils.randomAlphanumeric(MAX_ID_LENGTH);
    }

    /**
     * Checks if a notification title is valid.
     *
     * @param notificationTitle The notification title to validate.
     * @throws InvalidAttributeValueException if notificationTitle is null or empty.
     */
    public static void isNotificationTitleValid(String notificationTitle) {
        if (notificationTitle == null || notificationTitle.isEmpty()) {
            throw new InvalidAttributeValueException("NotificationTitle cannot be null or empty");
        }
    }

    /**
     * Checks if a reminder content is valid.
     *
     * @param reminderContent The reminder content to validate.
     * @throws InvalidAttributeValueException if reminderContent is null or empty.
     */
    public static void isReminderContentValid(String reminderContent) {
        if (reminderContent == null || reminderContent.isEmpty()) {
            throw new InvalidAttributeValueException("ReminderContent cannot be null or empty");
        }
    }

    public static void isReminderTimeValid(LocalDateTime reminderTime) {
        /**
         * Checks if a reminder time is valid.
         *
         * @param reminderTime The reminder time to validate.
         * @throws IllegalArgumentException if reminderTime is null.
         */
        if (reminderTime == null) {
            throw new IllegalArgumentException("ReminderTime cannot be null");
        }
    }

    public static void isReminderTypeValid(Notification.ReminderType reminderType) {
        /**
         * Checks if a reminder type is valid.
         *
         * @param reminderType The reminder type to validate.
         * @throws IllegalArgumentException if reminderType is null.
         */
        if (reminderType == null) {
            throw new IllegalArgumentException("ReminderType cannot be null");
        }
    }

    public static void isTemperatureValid(double temperature) {
        /**
         * Checks if a temperature is valid.
         *
         * @param temperature The temperature to validate.
         * @throws IllegalArgumentException if temperature is not positive.
         */
        if (temperature <= 0) {
            throw new IllegalArgumentException("Temperature must be a positive number");
        }
    }

    public static void isHeartRateValid(int heartRate) {
        /**
         * Checks if a heart rate is valid.
         *
         * @param heartRate The heart rate to validate.
         * @throws IllegalArgumentException if heartRate is not positive.
         */
        if (heartRate <= 0) {
            throw new IllegalArgumentException("Heart rate must be a positive number");
        }
    }

    public static void isSystolicPressureValid(int systolicPressure) {
        /**
         * Checks if a systolic pressure is valid.
         *
         * @param systolicPressure The systolic pressure to validate.
         * @throws IllegalArgumentException if systolicPressure is not positive.
         */
        if (systolicPressure <= 0) {
            throw new IllegalArgumentException("Systolic pressure must be a positive number");
        }
    }

    public static void isDiastolicPressureValid(int diastolicPressure) {
        /**
         * Checks if a diastolic pressure is valid.
         *
         * @param diastolicPressure The diastolic pressure to validate.
         * @throws IllegalArgumentException if diastolicPressure is not positive.
         */
        if (diastolicPressure <= 0) {
            throw new IllegalArgumentException("Diastolic pressure must be a positive number");
        }
    }

    public static void isMeanArterialPressureValid(int meanArterialPressure) {
        /**
         * Checks if a mean arterial pressure is valid.
         *
         * @param meanArterialPressure The mean arterial pressure to validate.
         * @throws IllegalArgumentException if meanArterialPressure is not positive.
         */
        if (meanArterialPressure <= 0) {
            throw new IllegalArgumentException("Mean arterial pressure must be a positive number");
        }
    }

    public static void isWeightValid(double weight) {
        /**
         * Checks if a weight is valid.
         *
         * @param weight The weight to validate.
         * @throws IllegalArgumentException if weight is not positive.
         */
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be a positive number");
        }
    }

    public static void isPatientPositionValid(String patientPosition) {
        /**
         * Checks if a patient position is valid.
         *
         * @param patientPosition The patient position to validate.
         * @throws IllegalArgumentException if patientPosition is null.
         */
        if (patientPosition == null) {
            throw new IllegalArgumentException("Patient position cannot be null");
        }
    }

    public static void isBloodOxygenLevelValid(int bloodOxygenLevel) {
        /**
         * Checks if a blood oxygen level is valid.
         *
         * @param bloodOxygenLevel The blood oxygen level to validate.
         * @throws IllegalArgumentException if bloodOxygenLevel is negative.
         */
        if (bloodOxygenLevel < 0) {
            throw new IllegalArgumentException("Blood oxygen level must be a positive number");
        }
    }

    public static void isOxygenTherapyValid(String oxygenTherapy) {
        /**
         * Checks if an oxygen therapy is valid.
         *
         * @param oxygenTherapy The oxygen therapy to validate.
         * @throws IllegalArgumentException if oxygenTherapy is null.
         */
        if (oxygenTherapy == null) {
            throw new IllegalArgumentException("Oxygen therapy cannot be null");
        }
    }

    public static void isFlowDeliveredValid(String flowDelivered) {
        /**
         * Checks if a flow delivered is valid.
         *
         * @param flowDelivered The flow delivered to validate.
         * @throws IllegalArgumentException if flowDelivered is null.
         */
        if (flowDelivered == null) {
            throw new IllegalArgumentException("Flow delivered cannot be null");
        }
    }

    public static void isPatientActivityValid(String patientActivity) {
        /**
         * Checks if a patient activity is valid.
         *
         * @param patientActivity The patient activity to validate.
         * @throws IllegalArgumentException if patientActivity is null.
         */
        if (patientActivity == null) {
            throw new IllegalArgumentException("Patient activity cannot be null");
        }
    }

    public static void isCommentsValid(String comments) {
        /**
         * Checks if comments are valid.
         *
         * @param comments The comments to validate.
         * @throws IllegalArgumentException if comments are null.
         */
        if (comments == null) {
            throw new IllegalArgumentException("Comments cannot be null");
        }
    }
}

