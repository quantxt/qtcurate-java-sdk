package com.quantxt.sdk.sample;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.progress.Progress;

import java.util.List;

public class ProgressSample {

    private static final String API_KEY = "123456";

    public static void main(String[] args) throws InterruptedException {
        QT.init(API_KEY);

        List<Progress> progresses = Progress.reader().read();
        System.out.println("Progresses data: " + progresses);

        if (!progresses.isEmpty()) {
            Progress progress = Progress.fetcher(progresses.get(0).getIndex())
                    .fetch();
            System.out.println("Single progress data: " + progress);
        }
    }
}
