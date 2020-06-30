package com.quantxt.sdk.sample;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.dataprocess.DataProcess;
import com.quantxt.sdk.file.SearchDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.quantxt.sdk.sample.MonitorJobs.wiat4Job2Finish;

public class FullTextSearchOnUrls {
    private static final String API_KEY = "123456";

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        QT.init(API_KEY);

        // 1- Stream the content of the file into Theia
        List<String> urls = new ArrayList<>();
        urls.add("https://www.sec.gov/Archives/edgar/data/1329011/000118518513001996/0001185185-13-001996.txt");
        urls.add("https://www.sec.gov/Archives/edgar/data/1633917/000119312515257108/d31081dex991.htm");

        // 1- Configure and kick off the Parser job. This will prepare the document for keyword and phrase search
        DataProcess dataProcess = DataProcess.creator("My parser job " + Instant.now())
                .excludeUttWithoutEntities(false)
                .urls(urls)
                .create();

        System.out.println(String.format("Data parser %s started", dataProcess.getIndex()));

        // 2- Track the progress of the parser job
        wiat4Job2Finish(dataProcess.getIndex());

        // 3- Wait for parsed data to be propagated into database
        Thread.sleep(5000);

        System.out.println("Parser job finished.");

        // 4- Full text search and querying

    }
}
