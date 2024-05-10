package com.devyanan.CareCompass.dynamodb.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class medication {
    private String medicationId;
    private String userId;
    private LocalDateTime medicationTime;
    private String medicationName;
    private String medicationInfo;

    public String getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(String medicationId) {
        this.medicationId = medicationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getMedicationTime() {
        return medicationTime;
    }

    public void setMedicationTime(LocalDateTime medicationTime) {
        this.medicationTime = medicationTime;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getMedicationInfo() {
        return medicationInfo;
    }

    public void setMedicationInfo(String medicationInfo) {
        this.medicationInfo = medicationInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        medication that = (medication) o;
        return Objects.equals(medicationId, that.medicationId) && Objects.equals(userId, that.userId) && Objects.equals(medicationTime, that.medicationTime) && Objects.equals(medicationName, that.medicationName) && Objects.equals(medicationInfo, that.medicationInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicationId, userId, medicationTime, medicationName, medicationInfo);
    }

    @Override
    public String toString() {
        return "medication{" +
                "medicationId='" + medicationId + '\'' +
                ", userId='" + userId + '\'' +
                ", medicationTime=" + medicationTime +
                ", medicationName='" + medicationName + '\'' +
                ", medicationInfo='" + medicationInfo + '\'' +
                '}';
    }
}
