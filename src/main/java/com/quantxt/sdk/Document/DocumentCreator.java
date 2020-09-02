package com.quantxt.sdk.document;

import com.quantxt.sdk.client.HttpMethod;
import com.quantxt.sdk.client.QTRestClient;
import com.quantxt.sdk.client.Request;
import com.quantxt.sdk.client.Response;
import com.quantxt.sdk.exception.QTApiConnectionException;
import com.quantxt.sdk.exception.QTApiException;
import com.quantxt.sdk.exception.QTRestException;
import com.quantxt.sdk.resource.Creator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class DocumentCreator extends Creator<Document> {

    private String name;
    private InputStream inputStream;

    /**
     * The inputStream of the file.
     *
     * @param inputStream input file inputStream.
     * @param name        input file name.
     * @return this
     */
    public DocumentCreator source(InputStream inputStream, String name) {
        this.inputStream = inputStream;
        this.name = name;
        return this;
    }

    /**
     * Specifies a file as a input document input.
     *
     * @param file input document file.
     * @return this
     */
    public DocumentCreator source(File file) {
        try {
            this.inputStream = new FileInputStream(file);
            this.name = file.getName();

            return this;
        } catch (FileNotFoundException e) {
            throw new QTApiException("Error processing specified search document source.", e);
        }
    }

    /**
     * Make the request to the API to perform the create.
     *
     * @param client QTClient with which to make the request
     * @return Created Dictionary
     */
    @Override
    public Document create(QTRestClient client) {
        Request request = new Request(HttpMethod.UPLOAD, "/search/file");
        addPayload(request);

        Response response = client.request(request);

        if (response == null) {
            throw new QTApiConnectionException("Search document creation failed: Unable to connect to server");
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

        return Document.fromJson(response.getStream(), client.getObjectMapper());
    }

    private void addPayload(Request request) {
        request.setInputStream(this.inputStream);
        request.setFileName(this.name);
    }
}
