package com.quantxt.sdk.sample;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.progress.Progress;

import java.util.List;

import static java.lang.Thread.sleep;

public class MonitorJobs {
    private static final String API_KEY = "123456";

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

    public static void wiat4Job2Finish(String index) throws InterruptedException {
        int percentage = 0;
        while (percentage < 100) {
            Progress progress = Progress.fetcher(index)
                    .fetch();
            percentage = progress.getProgress();

            System.out.println(String.format("Search progress: %s", percentage));
            if (percentage < 100) {
                sleep(1000);
            }
        }
    }
}
