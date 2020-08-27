package com.quantxt.sdk.sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.dataprocess.DataProcess;
import com.quantxt.sdk.profile.Profile;

public class ProfileOperations {
    private static final String API_KEY = "__APIKEY__";

    private static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    public static void main(String[] args) throws JsonProcessingException {
        QT.init(API_KEY);

        Profile profile = Profile.fetcher().fetch();

        // Get list of data processes
        DataProcess[] dataProcesses = profile.getDataProcesses();


        System.out.println(objectMapper.writeValueAsString(profile));

    }
}
