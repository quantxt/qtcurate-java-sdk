package com.quantxt.sdk.dictionary;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(Include.NON_NULL)
public class DictionaryCreator extends Creator<Dictionary> {

    private String name;
    private List<DictionaryEntry> entries = new ArrayList<>();
    private InputStream inputStream;
    private String fileName;

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
    public DictionaryCreator entries(final List<DictionaryEntry> entries) {
        this.entries = entries;
        return this;
    }

    /**
     * Append single entry in the dictionary. Category is set the same as the search string.
     *
     * @param search_string   String to be searched in raw content.
     * @return this
     */

    public DictionaryCreator addEntry(String search_string) {
        this.entries.add(new DictionaryEntry(search_string, search_string));
        return this;
    }

    /**
     * Append single search string with category in the dictionary.
     *
     * @param search_string   String to be searched in raw content.
     * @param category        Category.
     * @return this
     */
    public DictionaryCreator addEntry(String search_string, String category) {
        this.entries.add(new DictionaryEntry(category, search_string));
        return this;
    }

    /**
     * The inputStream of the dictionary source.
     *
     * @param inputStream Dictionary inputStream.
     * @param sourceName  Dictionary source name.
     * @return this
     */
    public DictionaryCreator source(InputStream inputStream, String sourceName) {
        this.inputStream = inputStream;
        this.fileName = sourceName;
        return this;
    }

    /**
     * Specifies a file as a dictionary input.
     *
     * @param file Dictionary file.
     * @return this
     */
    public DictionaryCreator source(File file) {
        try {
            this.inputStream = new FileInputStream(file);
            this.fileName = file.getName();

            return this;
        } catch (FileNotFoundException e) {
            throw new QTApiException("Error processing specified dictionary source.", e);
        }
    }

    /**
     * Make the request to the API to perform the create.
     *
     * @param client QTClient with which to make the request
     * @return Created Dictionary
     */
    @Override
    public Dictionary create(QTRestClient client) {
        Request request = createRequest();
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
     * Create {@link Request} object depending on parameters contained.
     *
     * @return Request object
     */
    private Request createRequest() {
        if (this.inputStream == null) {
            return new Request(HttpMethod.POST, "/dictionaries");
        } else {
            return new Request(HttpMethod.UPLOAD, "/dictionaries/upload");
        }
    }

    /**
     * Add the requested JSON body to the Request.
     *
     * @param request Request to add post params to
     */
    private void addPayload(final Request request, final QTRestClient client) {
        if (this.inputStream == null) {
            try {
                request.setBody(client.getObjectMapper().writeValueAsString(this));
            } catch (JsonProcessingException e) {
                throw new QTApiException(e.getMessage(), e);
            }
        } else {
            request.setInputStream(this.inputStream);
            request.setFileName(this.fileName);
            request.addPostParam("name", name);
        }
    }
}
