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

public class FullTextSearchOnDocuments {
    private static final String API_KEY = "123456";
    private static final String FILE_NAME_1 = "file1.pdf";
    private static final String FILE_NAME_2 = "file2.pdf";

    public static List<String> getDocumentUuids() throws FileNotFoundException {
        InputStream inputStream_1 = new FileInputStream(new File(FILE_NAME_1));
        InputStream inputStream_2 = new FileInputStream(new File(FILE_NAME_2));

        SearchDocument searchDocument_1 = SearchDocument.creator()
                .name(FILE_NAME_1)
                .inputStream(inputStream_1)
                .create();
        SearchDocument searchDocument_2 = SearchDocument.creator()
                .name(FILE_NAME_2)
                .inputStream(inputStream_2)
                .create();

        System.out.println("Content is streamed");

        List<String> documentUUids = new ArrayList<>();
        documentUUids.add(searchDocument_1.getUuid());
        documentUUids.add(searchDocument_2.getUuid());
        return documentUUids;
    }

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        QT.init(API_KEY);

        // 1- Stream the content of the file into Theia
        List<String> documentUUids = getDocumentUuids();

        // 2- Configure and kick off the Parser job. This will prepare the document for keyword and phrase search
        DataProcess dataProcess = DataProcess.creator("My parser job " + Instant.now())
                .excludeUttWithoutEntities(false)
                .autoTag(false)
                .files(documentUUids)
                .create();

        System.out.println(String.format("Data parser %s started", dataProcess.getIndex()));

        // 3- Track the progress of the parser job
        wiat4Job2Finish(dataProcess.getIndex());

        // 4- Wait for parsed data to be propagated into database
        Thread.sleep(5000);

        System.out.println("Parser job finished.");

        // 5- Full text search and querying

    }
}
