package com.quantxt.sdk.vocabulary;

import com.quantxt.sdk.client.HttpMethod;
import com.quantxt.sdk.client.QTRestClient;
import com.quantxt.sdk.client.Request;
import com.quantxt.sdk.client.Response;
import com.quantxt.sdk.exception.QTApiConnectionException;
import com.quantxt.sdk.exception.QTApiException;
import com.quantxt.sdk.exception.QTRestException;
import com.quantxt.sdk.resource.Deleter;

public class VocabularyDeleter extends Deleter<Vocabulary> {

    private final String id;

    /**
     * Construct a new VocabularyDeleter.
     *
     * @param id The ID of the dictionary resource to delete
     */
    public VocabularyDeleter(String id) {
        this.id = id;
    }

    @Override
    public boolean delete(QTRestClient client) {
        Request request = new Request(HttpMethod.DELETE, "/dictionaries/" + id);

        Response response = client.request(request);

        if (response == null) {
            throw new QTApiConnectionException("Vocabulary delete failed: Unable to connect to server");
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

        return response.getStatusCode() == 204;
    }
}
