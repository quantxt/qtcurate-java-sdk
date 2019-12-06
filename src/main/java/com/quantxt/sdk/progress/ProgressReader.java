package com.quantxt.sdk.progress;

import com.quantxt.sdk.client.HttpMethod;
import com.quantxt.sdk.client.QTRestClient;
import com.quantxt.sdk.client.Request;
import com.quantxt.sdk.client.Response;
import com.quantxt.sdk.exception.QTApiConnectionException;
import com.quantxt.sdk.exception.QTApiException;
import com.quantxt.sdk.exception.QTRestException;
import com.quantxt.sdk.resource.Reader;

import java.util.List;

public class ProgressReader extends Reader<Progress> {

    @Override
    public List<Progress> read(QTRestClient client) {
        Request request = new Request(HttpMethod.GET, "/search/progress");

        Response response = client.request(request);

        if (response == null) {
            throw new QTApiConnectionException("Progress fetch failed: Unable to connect to server");
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

        return Progress.listFromJson(response.getStream(), client.getObjectMapper());
    }
}
