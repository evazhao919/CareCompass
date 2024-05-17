package com.devyanan.CareCompass.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.devyanan.CareCompass.converters.LocalTimeConverter;

import java.time.LocalTime;
import java.util.Objects;
@DynamoDBTable(tableName = "medicationDosage")
public class MedicationDosage {
    private LocalTime time;
    private String dosage;

    public MedicationDosage(LocalTime time, String dosage) {
        this.time = time;
        this.dosage = dosage;
    }
    @DynamoDBHashKey(attributeName = "time")
    @DynamoDBTypeConverted(converter = LocalTimeConverter.class)
    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
    @DynamoDBRangeKey(attributeName = "dosage")
    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicationDosage that = (MedicationDosage) o;
        return Objects.equals(time, that.time) && Objects.equals(dosage, that.dosage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, dosage);
    }
}
