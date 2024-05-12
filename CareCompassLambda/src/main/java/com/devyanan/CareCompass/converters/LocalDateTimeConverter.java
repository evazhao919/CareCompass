package com.devyanan.CareCompass.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public String convert(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return FORMATTER.format(localDateTime);
    }

    @Override
    public LocalDateTime unconvert(String dateTimeRepresentation) {
        return LocalDateTime.parse(dateTimeRepresentation, FORMATTER);
    }
}
