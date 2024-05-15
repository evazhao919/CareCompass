package com.devyanan.CareCompass.exceptions;

public class CustomDynamoDBException extends RuntimeException{
    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public CustomDynamoDBException (String message) {
        super(message);
    }
    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public CustomDynamoDBException (String message, Throwable cause) {
        super(message, cause);
    }
}
