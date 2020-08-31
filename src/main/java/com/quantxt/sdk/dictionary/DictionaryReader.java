package com.quantxt.sdk.dictionary;

import com.quantxt.sdk.client.HttpMethod;
import com.quantxt.sdk.client.QTRestClient;
import com.quantxt.sdk.client.Request;
import com.quantxt.sdk.client.Response;
import com.quantxt.sdk.exception.QTApiConnectionException;
import com.quantxt.sdk.exception.QTApiException;
import com.quantxt.sdk.exception.QTRestException;
import com.quantxt.sdk.resource.Reader;

import java.util.List;

public class DictionaryReader extends Reader<Dictionary> {

    /**
     * Construct a new DictionaryReader.
     */

    public DictionaryReader() {
    }

    /**
     * Make the request to the Quantxt API to perform the fetch.
     *
     * @param client QTClient with which to make the request
     * @return List of vocabs
     */
    @Override
    public List<Dictionary> read(QTRestClient client) {
        String uri = "/dictionaries";
        Request request = new Request(HttpMethod.GET, uri);

        Response response = client.request(request);

        if (response == null) {
            throw new QTApiConnectionException("Dictionary read failed: Unable to connect to server");
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

        return Dictionary.listFromJson(response.getStream(), client.getObjectMapper());
    }
}
