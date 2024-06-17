package com.devyanan.CareCompass.exceptions;

public class MedicationNotFoundException extends RuntimeException{
    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public MedicationNotFoundException(String message) {
        super(message);
    }
    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public MedicationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}