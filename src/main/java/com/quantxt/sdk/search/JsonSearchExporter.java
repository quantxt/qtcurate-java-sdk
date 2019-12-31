package com.quantxt.sdk.search;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quantxt.sdk.client.HttpMethod;
import com.quantxt.sdk.client.QTRestClient;
import com.quantxt.sdk.client.Request;
import com.quantxt.sdk.client.Response;
import com.quantxt.sdk.exception.QTApiConnectionException;
import com.quantxt.sdk.exception.QTApiException;
import com.quantxt.sdk.exception.QTRestException;
import com.quantxt.sdk.resource.Exporter;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class JsonSearchExporter extends Exporter<Search> {

    @JsonIgnore
    private String id;

    public JsonSearchExporter(String id) {
        this.id = id;
    }

    @Override
    public byte[] export(QTRestClient client) {
        Request request = new Request(HttpMethod.GET, String.format("/reports/%s/json", this.id));

        Response response = client.request(request);

        if (response == null) {
            throw new QTApiConnectionException("JSON search export failed: Unable to connect to server");
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
            return IOUtils.toByteArray(response.getStream());
        } catch (IOException e) {
            throw new QTApiException("JSON search export failed. Unable to read the data.", e);
        }
    }

}
