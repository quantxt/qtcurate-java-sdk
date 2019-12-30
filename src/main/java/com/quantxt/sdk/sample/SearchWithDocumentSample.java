package com.quantxt.sdk.sample;

import com.google.common.collect.Lists;
import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.dataprocess.DataProcess;
import com.quantxt.sdk.dataprocess.DataProcessCreator;
import com.quantxt.sdk.dataprocess.SearchRule;
import com.quantxt.sdk.dictionary.Dictionary;
import com.quantxt.sdk.file.SearchDocument;
import com.quantxt.sdk.progress.Progress;
import com.quantxt.sdk.search.Search;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class SearchWithDocumentSample {

    private static final String API_KEY = "123456";
    private static final String FILE_NAME = "search-file.pdf";

    public static void main(String[] args) throws InterruptedException, IOException {
        QT.init(API_KEY);

        InputStream inputStream = SearchWithDocumentSample.class
                .getClassLoader().getResourceAsStream(FILE_NAME);

        List<DataProcess> existingSearches = DataProcess.reader().read();
        System.out.println(String.format("Existing searches %s: %s", existingSearches.size(), existingSearches));

        SearchDocument searchDocument = SearchDocument.creator()
                .name(FILE_NAME)
                .inputStream(inputStream)
                .create();

        System.out.println("Uploaded search document: " + searchDocument);

        Map<String, String> entries = new HashMap<>();
        entries.put("key1", "val1");
        entries.put("key2", "val2");
        entries.put("key3", "val3");

        Dictionary dictionary = Dictionary.creator()
                .name("SDK Dictionary test 2")
                .entries(entries)
                .create();

        SearchRule rule = new SearchRule(dictionary, DataProcessCreator.DictionaryType.NUMBER);

        DataProcess dataProcess = DataProcess.creator("Branko SDK " + Instant.now())
                .excludeUttWithoutEntities(false)
                .autoTag(false)
                .files(Lists.newArrayList(searchDocument.getUuid()))
                .addRule(rule)
                .create();

        System.out.println("Data process created: " + dataProcess);

        int percentage = 0;
        while (percentage < 100) {
            Progress progress = Progress.fetcher(dataProcess.getIndex())
                    .fetch();
            percentage = progress.getProgress();

            System.out.println(String.format("Search progress: %s", percentage));
            if (percentage < 100) {
                sleep(1000);
            }
        }

        // Sleep so that data gets to the ES.
        Thread.sleep(5000);

        byte[] xlsxExportData = Search.xlsxExporter(dataProcess.getIndex())
                .export();
        File xlsxExport = new File(dataProcess.getIndex() + ".xlsx");
        OutputStream outStream = new FileOutputStream(xlsxExport);
        outStream.write(xlsxExportData);
        System.out.println(String.format("Exported %s bytes of XLSX data", xlsxExportData.length));

        byte[] jsonExportData = Search.jsonExporter(dataProcess.getIndex())
                .export();
        File jsonExport = new File(dataProcess.getIndex() + ".json");
        outStream = new FileOutputStream(jsonExport);
        outStream.write(jsonExportData);
        System.out.println(String.format("Exported %s bytes of JSON data", jsonExportData.length));

        boolean deleted = DataProcess.deleter(dataProcess.getIndex()).delete();
        System.out.println(String.format("Data process %s deleted %s", dataProcess.getIndex(), deleted));
    }
}
