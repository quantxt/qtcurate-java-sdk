package com.quantxt.sdk.dictionary;

import com.quantxt.sdk.client.HttpMethod;
import com.quantxt.sdk.client.QTRestClient;
import com.quantxt.sdk.client.Request;
import com.quantxt.sdk.client.Response;
import com.quantxt.sdk.exception.QTApiConnectionException;
import com.quantxt.sdk.exception.QTApiException;
import com.quantxt.sdk.exception.QTRestException;
import com.quantxt.sdk.resource.Fetcher;

public class DictionaryFetcher extends Fetcher<Dictionary> {

    private String id;

    /**
     * Construct a new DictionaryFetcher.
     *
     * @param id The ID that identifies the resource to fetch
     */
    public DictionaryFetcher(String id) {
        this.id = id;
    }

    /**
     * Make the request to the Quantxt API to perform the fetch.
     *
     * @param client QTClient with which to make the request
     * @return Created Dictionary
     */
    @Override
    public Dictionary fetch(QTRestClient client) {
        Request request = new Request(HttpMethod.GET, "/dictionaries/" + this.id);

        Response response = client.request(request);

        if (response == null) {
            throw new QTApiConnectionException("Dictionary fetch failed: Unable to connect to server");
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

        return Dictionary.fromJson(response.getStream(), client.getObjectMapper());
    }
}
