package com.quantxt.sdk.dataprocess;

import com.quantxt.sdk.client.HttpMethod;
import com.quantxt.sdk.client.QTRestClient;
import com.quantxt.sdk.client.Request;
import com.quantxt.sdk.client.Response;
import com.quantxt.sdk.document.Document;
import com.quantxt.sdk.exception.QTApiConnectionException;
import com.quantxt.sdk.exception.QTApiException;
import com.quantxt.sdk.exception.QTRestException;
import com.quantxt.sdk.model.*;
import com.quantxt.sdk.progress.Progress;
import com.quantxt.sdk.resource.Fetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class DataProcessFetcher extends Fetcher<DataProcess> {

    final private static Logger log = LoggerFactory.getLogger(DataProcessFetcher.class);

    private String id;

    /**
     * Construct a new {@link DataProcessFetcher}.
     *
     * @param id The ID that identifies the resource to fetch
     */
    public DataProcessFetcher(String id) {
        this.id = id;
    }

    @Override
    public DataProcess fetch(QTRestClient client) {
        Request request = new Request(HttpMethod.GET, "/search/config/" + this.id);

        Response response = client.request(request);

        if (response == null) {
            throw new QTApiConnectionException("DataProcess fetch failed: Unable to connect to server");
        } else if (response.getStream() == null) {
            throw new QTApiException("Server Error, no content");
        } else if (!QTRestClient.SUCCESS.test(response.getStatusCode())) {
            QTRestException restException = QTRestException.fromJson(response.getStream(), client.getObjectMapper());
            if (restException == null) {
                throw new QTApiException("Server Error, no content");
            }

            throw new QTApiException(
                    restException.getCode(),
                    restException.getMessage(),
                    response.getStatusCode(),
                    null
            );
        }

        try {
            ResultConfiguration resultConfiguration = client.getObjectMapper().readValue(response.getStream(), ResultConfiguration.class);
            DataProcess dataProcess = new DataProcess();
            dataProcess.setId(id);
            dataProcess.setDescription(resultConfiguration.getTitle());
            dataProcess.setNumWorkers(resultConfiguration.getNumWorkers());
            List<Document> documentList = new ArrayList<>();
            if (resultConfiguration.getFiles() != null){
                for (String file : resultConfiguration.getFiles()){
                    Document document = new Document();
                    document.setId(file);
                }
                dataProcess.setDocuments(documentList);
            }
            List<Extractor> extractors = new ArrayList<>();
            if (resultConfiguration.getSearchDictionaries() != null){
                for (DictionaryDto dictionaryDto : resultConfiguration.getSearchDictionaries()){
                    Extractor extractor = new Extractor(dictionaryDto);
                    extractors.add(extractor);
                }
                dataProcess.setExtractors(extractors);
            }
            return dataProcess;

        } catch (Exception e){
            throw new QTApiException("Error is submitting data process job");
        }
    }

    public void blockUntilFinish() {
        int percentage = 0;
        try {
            sleep(2000);
            while (percentage < 100) {
                Progress progress = Progress.fetcher(id).fetch();
                percentage = progress.getProgress();
                if (percentage < 100) {
                    sleep(1000);
                }
            }
            // Wait for data to get propagated into elastic or final database
            sleep(4000);
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
