package com.quantxt.sdk.sample;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.progress.Progress;

import java.util.List;

public class MonitorJobs {
    private static final String API_KEY = "__APIKEY__";

    public static void main(String[] args) {
        QT.init(API_KEY);

        List<Progress> progressList = Progress.reader().read();
        System.out.println("Running jobs: " + progressList.size());

        if (!progressList.isEmpty()) {
            for (Progress p : progressList) {
                String id = p.getId();
                System.out.println("Job " + id + " " + p.getProgress() +"% completed.");
            }
        }
    }
}
