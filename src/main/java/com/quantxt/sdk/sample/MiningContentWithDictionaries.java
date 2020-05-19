package com.quantxt.sdk.sample;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.dataprocess.DataProcess;
import com.quantxt.sdk.dataprocess.DataProcessCreator;
import com.quantxt.sdk.dataprocess.SearchRule;
import com.quantxt.sdk.dictionary.Dictionary;
import com.quantxt.sdk.dictionary.DictionaryEntry;

import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.quantxt.sdk.sample.FullTextSearchOnDocuments.getDocumentUuids;
import static com.quantxt.sdk.sample.MonitorJobs.wiat4Job2Finish;

public class MiningContentWithDictionaries {
    private static final String API_KEY = "123456";


    private static Dictionary getDateDictionary(){
        List<DictionaryEntry> entries = new ArrayList<>();
        entries.add(new DictionaryEntry( "Report Date", "Date of the Report"));
        entries.add(new DictionaryEntry("Report Date", "report was created on"));
        entries.add(new DictionaryEntry("Inspection Date", "Inspection was done on"));
        entries.add(new DictionaryEntry("Inspection Date", "discovery was done on"));

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

        Dictionary dictionary = getDateDictionary();

        // 2- Configure and kick off the Parser job. This will prepare the document for keyword and phrase search
        DataProcess dataProcess = DataProcess.creator("My parser job " + Instant.now())
                .excludeUttWithoutEntities(true)
                .addRule(new SearchRule(dictionary, DataProcessCreator.DictionaryType.NUMBER))
                .addRule(new SearchRule(dictionary, DataProcessCreator.DictionaryType.DATETIME))
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
