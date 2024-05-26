package com.devyanan.CareCompass.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = GetAllBloodGlucoseMeasurementsRequest.Builder.class)
public class GetAllBloodGlucoseMeasurementsRequest {
    private final String patientId;

    public GetAllBloodGlucoseMeasurementsRequest(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientId() {
        return patientId;
    }

    @Override
    public String toString() {
        return "GetAllBloodGlucoseMeasurementsRequest{" +
                "patientId='" + patientId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String patientId;

        public Builder withPatientId(String patientId) {
            this.patientId= patientId;
            return this;
        }
        public GetAllBloodGlucoseMeasurementsRequest build() {
            return new GetAllBloodGlucoseMeasurementsRequest(patientId);
        }
    }
}
