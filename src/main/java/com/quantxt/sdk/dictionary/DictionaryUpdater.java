package com.quantxt.sdk.dictionary;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.quantxt.sdk.client.HttpMethod;
import com.quantxt.sdk.client.QTRestClient;
import com.quantxt.sdk.client.Request;
import com.quantxt.sdk.client.Response;
import com.quantxt.sdk.exception.QTApiConnectionException;
import com.quantxt.sdk.exception.QTApiException;
import com.quantxt.sdk.exception.QTRestException;
import com.quantxt.sdk.resource.Updater;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DictionaryUpdater extends Updater<Dictionary> {

    @JsonIgnore
    private String id;
    private String name;
    private List<Dictionary.Entry> entries = new ArrayList<>();

    /**
     * Construct a new DictionaryUpdater.
     *
     * @param id The ID that identifies the resource to update
     */
    public DictionaryUpdater(String id) {
        this.id = id;
    }

    /**
     * The name of the dictionary.
     *
     * @param name Dictionary name.
     * @return this
     */
    public DictionaryUpdater name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Add entries in the dictionary.
     *
     * @param entries Dictionary entries.
     * @return this
     */
    public DictionaryUpdater entries(final Map<String, String> entries) {
        this.entries = entries.entrySet().stream()
                .map(entry -> new Dictionary.Entry(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return this;
    }

    /**
     * Add entries in the dictionary.
     *
     * @param entries List of Dictionary.Entry entries.
     * @return this
     */
    public DictionaryUpdater entries(List<Dictionary.Entry> entries) {
        this.entries = entries;
        return this;
    }

    /**
     * Append single entry in the dictionary.
     *
     * @param key   Dictionary entry key.
     * @param value Dictionary entry value.
     * @return this
     */
    public DictionaryUpdater addEntry(String key, String value) {
        this.entries.add(new Dictionary.Entry(key, value));
        return this;
    }

    /**
     * Append single entry in the dictionary.
     *
     * @param entry Dictionary.Entry entry.
     * @return this
     */
    public DictionaryUpdater addEntry(Dictionary.Entry entry) {
        this.entries.add(entry);
        return this;
    }

    /**
     * Make the request to the API to perform the update.
     *
     * @param client QTClient with which to make the request
     * @return Updated Dictionary
     */
    @Override
    public Dictionary update(QTRestClient client) {
        Request request = new Request(HttpMethod.PUT, String.format("/dictionaries/%s", this.id));

        addPayload(request, client);

        Response response = client.request(request);

        if (response == null) {
            throw new QTApiConnectionException("Dictionary update failed: Unable to connect to server");
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