package com.devyanan.CareCompass.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class IdUtils {
    private static final Pattern VALID_NAME_PATTERN = Pattern.compile("[a-zA-Z0-9 ]*");
    private static final int MAX_ID_LENGTH = 8;

    /**
     * Validates a medication name.
     *
     * @param medicationName The medication name to validate.
     * @throws IllegalArgumentException if the medication name is null, blank, or contains invalid characters.
     */
    public static void validateMedicationName(String medicationName) {
        if (StringUtils.isBlank(medicationName) || !VALID_NAME_PATTERN.matcher(medicationName).matches()) {
            throw new IllegalArgumentException("Invalid medication name");
        }
    }

    /**
     * Validates a time.
     *
     * @param time The time to validate.
     * @throws IllegalArgumentException if the time is null or blank.
     */
    public static void validateTime(String time) {
        if (StringUtils.isBlank(time)) {
            throw new IllegalArgumentException("Time cannot be null or blank");
        }
    }

    /**
     * Generates a unique notification ID.
     *
     * @return A String of random alphanumeric characters with a maximum length of 8.
     */
    public static String generateNotificationId() {
        return RandomStringUtils.randomAlphanumeric(MAX_ID_LENGTH);
    }
}