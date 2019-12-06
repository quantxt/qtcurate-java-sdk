package com.quantxt.sdk.sample;

import com.google.common.collect.Lists;
import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.dataprocess.DataProcess;
import com.quantxt.sdk.dataprocess.DataProcessCreator;
import com.quantxt.sdk.file.SearchDocument;
import com.quantxt.sdk.progress.Progress;
import com.quantxt.sdk.search.Search;
import com.quantxt.sdk.search.SearchExporter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Instant;
import java.util.List;

import static java.lang.Thread.sleep;

public class SearchWithDocumentSample {

    private static final String API_KEY = "123456";
    private static final String FILE_NAME = "search-file.pdf";

    public static void main2(String[] args) throws InterruptedException, IOException {
        QT.init(API_KEY);

        String index = "xlgrlehrqa";

        byte[] exportData = Search.exporter(index)
                .format(SearchExporter.Format.XLSX)
                .export();
        File xlsxExport = new File(index + ".xlsx");
        OutputStream outStream = new FileOutputStream(xlsxExport);
        outStream.write(exportData);
        System.out.println(String.format("Exported %s bytes of data", exportData.length));
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        QT.init(API_KEY);

        InputStream inputStream = SearchDocumentSample.class
                .getClassLoader().getResourceAsStream(FILE_NAME);

        List<DataProcess> existingSearches = DataProcess.reader().read();
        System.out.println(String.format("Existing searches %s: %s", existingSearches.size(), existingSearches));

        SearchDocument searchDocument = SearchDocument.creator()
                .name(FILE_NAME)
                .inputStream(inputStream)
                .create();

        System.out.println("Uploaded search document: " + searchDocument);

        DataProcess dataProcess = DataProcess.creator("Branko SDK " + Instant.now())
                .excludeUttWithoutEntities(false)
                .autoTag(false)
                .mode(DataProcessCreator.Mode.MODE_2)
                .files(Lists.newArrayList(searchDocument.getUuid()))
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

        byte[] exportData = Search.exporter(dataProcess.getIndex())
                .format(SearchExporter.Format.XLSX)
                .export();
        File xlsxExport = new File(dataProcess.getIndex() + ".xlsx");
        OutputStream outStream = new FileOutputStream(xlsxExport);
        outStream.write(exportData);
        System.out.println(String.format("Exported %s bytes of data", exportData.length));

        boolean deleted = DataProcess.deleter(dataProcess.getIndex()).delete();
        System.out.println(String.format("Data process %s deleted %s", dataProcess.getIndex(), deleted));
    }
}
