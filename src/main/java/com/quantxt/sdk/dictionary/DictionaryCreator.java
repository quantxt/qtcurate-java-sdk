package com.quantxt.sdk.dictionary;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.quantxt.sdk.client.HttpMethod;
import com.quantxt.sdk.client.QTRestClient;
import com.quantxt.sdk.client.Request;
import com.quantxt.sdk.client.Response;
import com.quantxt.sdk.exception.QTApiConnectionException;
import com.quantxt.sdk.exception.QTApiException;
import com.quantxt.sdk.exception.QTRestException;
import com.quantxt.sdk.resource.Creator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DictionaryCreator extends Creator<Dictionary> {

    private String name;
    private List<Dictionary.Entry> entries = new ArrayList<>();

    /**
     * The name of the dictionary.
     *
     * @param name Dictionary name.
     * @return this
     */
    public DictionaryCreator name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Entries in the dictionary.
     *
     * @param entries Dictionary entries.
     * @return this
     */
    public DictionaryCreator entries(final Map<String, String> entries) {
        this.entries = entries.entrySet().stream()
                .map(entry -> new Dictionary.Entry(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return this;
    }

    /**
     * Append single entry in the dictionary.
     *
     * @param key   Dictionary entry key.
     * @param value Dictionary entry value.
     * @return this
     */
    public DictionaryCreator addEntry(String key, String value) {
        this.entries.add(new Dictionary.Entry(key, value));
        return this;
    }

    /**
     * Make the request to the API to perform the create.
     *
     * @param client QTClient with which to make the request
     * @return Created Dictionary
     */
    @Override
    public Dictionary create(QTRestClient client) {
        Request request = new Request(HttpMethod.POST, "/dictionaries");
        addPayload(request, client);

        Response response = client.request(request);

        if (response == null) {
            throw new QTApiConnectionException("Dictionary creation failed: Unable to connect to server");
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

    /**
     * Add the requested JSON body to the Request.
     *
     * @param request Request to add post params to
     */
    private void addPayload(final Request request, final QTRestClient client) {
        try {
            request.setBody(client.getObjectMapper().writeValueAsString(this));
        } catch (JsonProcessingException e) {
            throw new QTApiException(e.getMessage(), e);
        }
    }
}
