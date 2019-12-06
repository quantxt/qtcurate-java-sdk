package com.quantxt.sdk.progress;

import com.quantxt.sdk.client.HttpMethod;
import com.quantxt.sdk.client.QTRestClient;
import com.quantxt.sdk.client.Request;
import com.quantxt.sdk.client.Response;
import com.quantxt.sdk.exception.QTApiConnectionException;
import com.quantxt.sdk.exception.QTApiException;
import com.quantxt.sdk.exception.QTRestException;
import com.quantxt.sdk.resource.Fetcher;

public class ProgressFetcher extends Fetcher<Progress> {

    private String id;

    /**
     * Construct a new ProgressFetcher.
     *
     * @param id The ID that identifies the resource to fetch
     */
    public ProgressFetcher(String id) {
        this.id = id;
    }

    @Override
    public Progress fetch(QTRestClient client) {
        Request request = new Request(HttpMethod.GET, "/search/progress/" + this.id);

        Response response = client.request(request);

        if (response == null) {
            throw new QTApiConnectionException("Progress fetch failed: Unable to connect to server");
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

        return Progress.fromJson(response.getStream(), client.getObjectMapper());
    }
}
