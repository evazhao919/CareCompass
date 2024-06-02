package com.devyanan.CareCompass.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

public class RetrieveNotificationsByReminderTypeRequest {
    private final String patientId;
    private final String reminderType;

    public RetrieveNotificationsByReminderTypeRequest(String patientId, String reminderType) {
        this.patientId = patientId;
        this.reminderType = reminderType;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getReminderType() {
        return reminderType;
    }

    @Override
    public String toString() {
        return "RetrieveNotificationsByReminderType{" +
                "patientId='" + patientId + '\'' +
                ", reminderType='" + reminderType + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String patientId;
        private String reminderType;

        public Builder withPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder withReminderType(String reminderType) {
            this.reminderType = reminderType;
            return this;
        }

        public RetrieveNotificationsByReminderTypeRequest build() {
            return new RetrieveNotificationsByReminderTypeRequest(patientId, reminderType);
        }
    }
}
