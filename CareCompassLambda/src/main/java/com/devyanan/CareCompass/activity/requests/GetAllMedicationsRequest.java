package com.devyanan.CareCompass.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = GetAllMedicationsRequest.Builder.class)
public class GetAllMedicationsRequest {
    private final String patientId;

    public GetAllMedicationsRequest(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientId() {
        return patientId;
    }

    @Override
    public String toString() {
        return "GetAllMedicationsRequest{" +
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
        public GetAllMedicationsRequest build() {
            return new GetAllMedicationsRequest(patientId);
        }
    }
}
