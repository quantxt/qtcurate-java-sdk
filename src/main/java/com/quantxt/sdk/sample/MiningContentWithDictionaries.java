package com.quantxt.sdk.sample;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.dataprocess.DataProcess;
import com.quantxt.sdk.dataprocess.DataProcessCreator;
import com.quantxt.sdk.dictionary.Dictionary;

import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.quantxt.sdk.sample.FullTextSearchOnDocuments.getDocumentUuids;
import static com.quantxt.sdk.sample.MonitorJobs.wiat4Job2Finish;

public class MiningContentWithDictionaries {
    private static final String API_KEY = "123456";


    private static Dictionary getDateDictionary(){
        Map<String, String> entries = new HashMap<>();
        entries.put("Date of the Report",     "Report Date");
        entries.put("report was created on",  "Report Date");
        entries.put("Inspection was done on", "Inspection Date");
        entries.put("discovery was done on",  "Inspection Date");

        // 2- Initialize the dictionary
        Dictionary date_dictionary = Dictionary.creator()
                .name("Dates")
                .entries(entries)
                .create();
        return date_dictionary;
    }

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        QT.init(API_KEY);

        List<String> documentUUids = getDocumentUuids();

        // 2- Configure and kick off the Parser job. This will prepare the document for keyword and phrase search
        DataProcess dataProcess = DataProcess.creator("My parser job " + Instant.now())
                .excludeUttWithoutEntities(true)
                .addDictionary("", DataProcessCreator.DictionaryType.NUMBER)
                .addDictionary("", DataProcessCreator.DictionaryType.DATETIME)
                .autoTag(false)
                .files(documentUUids)
                .create();

        System.out.println(String.format("Data parser %s started", dataProcess.getIndex()));

        // 3- Track the progress of the parser job
        wiat4Job2Finish(dataProcess.getIndex());

        // 4- Wait for parsed data to be propagated into database
        Thread.sleep(5000);

    }
}
