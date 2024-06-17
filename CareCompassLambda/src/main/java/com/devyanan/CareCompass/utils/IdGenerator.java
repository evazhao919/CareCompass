package com.devyanan.CareCompass.utils;

import java.util.UUID;

public class IdGenerator {
    private static final int MAX_ID_LENGTH = 10;

    /**
     * Generates a random notificationId.
     *
     * @return A random notificationId string.
     */
    public static String generateId() {
        return UUID.randomUUID().toString().substring(0, MAX_ID_LENGTH);
    }
}