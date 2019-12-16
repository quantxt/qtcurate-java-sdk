package com.quantxt.sdk.sample;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.dataprocess.DataProcess;

import java.util.List;

public class DataProcessSample {

    private static final String API_KEY = "123456";

    public static void main(String[] args) {
        QT.init(API_KEY);

       // Reading all current indices
        List<DataProcess> dataProcessList = DataProcess.reader().read();

        System.out.println("Read data process list: " + dataProcessList);
    }
}
