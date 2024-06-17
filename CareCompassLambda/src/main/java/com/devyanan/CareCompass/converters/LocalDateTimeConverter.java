package com.devyanan.CareCompass.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Converter class for converting LocalDateTime objects to and from their string representations.
 */
public class LocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {
    private static final DateTimeFormatter ISO_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    /**
     * Converts a LocalDateTime object to its string representation.
     *
     * @param localDateTime The LocalDateTime object to convert.
     * @return The string representation of the LocalDateTime object.
     */
    @Override
    public String convert(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return ISO_DATE_TIME_FORMATTER.format(localDateTime);
    }

    /**
     * Converts a string representation of a date-time to a LocalDateTime object.
     *
     * @param dateTimeRepresentation The string representation of the date-time.
     * @return The LocalDateTime object parsed from the string representation.
     */
    @Override
    public LocalDateTime unconvert(String dateTimeRepresentation) {
        return LocalDateTime.parse(dateTimeRepresentation, ISO_DATE_TIME_FORMATTER);
    }
}