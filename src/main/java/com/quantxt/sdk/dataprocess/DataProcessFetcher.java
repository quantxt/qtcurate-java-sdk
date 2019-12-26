package com.quantxt.sdk.dataprocess;

import com.quantxt.sdk.client.HttpMethod;
import com.quantxt.sdk.client.QTRestClient;
import com.quantxt.sdk.client.Request;
import com.quantxt.sdk.client.Response;
import com.quantxt.sdk.exception.QTApiConnectionException;
import com.quantxt.sdk.exception.QTApiException;
import com.quantxt.sdk.exception.QTRestException;
import com.quantxt.sdk.resource.Fetcher;

public class DataProcessFetcher extends Fetcher<DataProcess> {

    private String index;

    /**
     * Construct a new {@link DataProcessFetcher}.
     *
     * @param index The ID that identifies the resource to fetch
     */
    public DataProcessFetcher(String index) {
        this.index = index;
    }

    @Override
    public DataProcess fetch(QTRestClient client) {
        Request request = new Request(HttpMethod.GET, "/search/" + this.index);

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

        return DataProcess.fromJson(response.getStream(), "meta", client.getObjectMapper());
    }
}
