//package com.devyanan.CareCompass.activity.requests;
//
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
//@JsonDeserialize(builder = GetPlaylistRequest.Builder.class)
//public class GetPlaylistRequest {
//    private final String id;
//
//    private GetPlaylistRequest(String id) {
//        this.id = id;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    @Override
//    public String toString() {
//        return "GetPlaylistRequest{" +
//                "id='" + id + '\'' +
//                '}';
//    }
//
//    //CHECKSTYLE:OFF:Builder
//    public static Builder builder() {
//        return new Builder();
//    }
//    @JsonPOJOBuilder
//    public static class Builder {
//        private String id;
//
//        public Builder withId(String id) {
//            this.id = id;
//            return this;
//        }
//
//        public GetPlaylistRequest build() {
//            return new GetPlaylistRequest(id);
//        }
//    }
//}
