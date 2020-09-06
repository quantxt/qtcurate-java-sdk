package com.quantxt.sdk.vocabulary;

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

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class VocabularyUpdater extends Updater<Vocabulary> {

    @JsonIgnore
    private String id;
    private String name;
    private List<VocabularyEntry> entries = new ArrayList<>();

    /**
     * Construct a new VocabularyUpdater.
     *
     * @param id The ID that identifies the resource to update
     */
    public VocabularyUpdater(String id) {
        this.id = id;
    }

    /**
     * The name of the vocab.
     *
     * @param name Vocabulary name.
     * @return this
     */
    public VocabularyUpdater name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Add entries in the vocab.
     *
     * @param entries List of Vocabulary.Entry entries.
     * @return this
     */
    public VocabularyUpdater entries(List<VocabularyEntry> entries) {
        this.entries = entries;
        return this;
    }

    /**
     * Append single entry without category in the vocab.
     *
     * @param search_string   Vocabulary entry key.
     * @return this
     */
    public VocabularyUpdater addEntry(String search_string) {
        this.entries.add(new VocabularyEntry(search_string, search_string));
        return this;
    }

    /**
     * Append single entry in the vocab.
     *
     * @param search_string   Vocabulary entry key.
     * @param category Vocabulary entry value.
     * @return this
     */
    public VocabularyUpdater addEntry(String search_string, String category) {
        this.entries.add(new VocabularyEntry(category, search_string));
        return this;
    }

    /**
     * Append single entry in the vocab.
     *
     * @param entry Vocabulary.Entry entry.
     * @return this
     */
    public VocabularyUpdater addEntry(VocabularyEntry entry) {
        this.entries.add(entry);
        return this;
    }

    /**
     * Make the request to the API to perform the update.
     *
     * @param client QTClient with which to make the request
     * @return Updated Vocabulary
     */
    @Override
    public Vocabulary update(QTRestClient client) {
        Request request = new Request(HttpMethod.PUT, String.format("/dictionaries/%s", this.id));

        addPayload(request, client);

        Response response = client.request(request);

        if (response == null) {
            throw new QTApiConnectionException("Vocabulary update failed: Unable to connect to server");
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

        return Vocabulary.fromJson(response.getStream(), client.getObjectMapper());
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
