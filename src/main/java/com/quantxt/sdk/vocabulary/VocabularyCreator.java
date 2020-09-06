package com.quantxt.sdk.vocabulary;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantxt.sdk.client.HttpMethod;
import com.quantxt.sdk.client.QTRestClient;
import com.quantxt.sdk.client.Request;
import com.quantxt.sdk.client.Response;
import com.quantxt.sdk.exception.QTApiConnectionException;
import com.quantxt.sdk.exception.QTApiException;
import com.quantxt.sdk.exception.QTRestException;
import com.quantxt.sdk.resource.Creator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(Include.NON_NULL)
public class VocabularyCreator extends Creator<Vocabulary> {

    final private static ObjectMapper objectMapper = new ObjectMapper()
            .configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);

    private String name;
    private List<VocabularyEntry> entries = new ArrayList<>();
    private InputStream inputStream;

    /**
     * The name of the vocab.
     *
     * @param name Vocabulary name.
     * @return this
     */
    public VocabularyCreator name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Entries in the vocab.
     *
     * @param entries Vocabulary entries.
     * @return this
     */
    public VocabularyCreator entries(final List<VocabularyEntry> entries) {
        this.entries = entries;
        return this;
    }

    /**
     * Append single entry in the vocab. Category is set the same as the search string.
     *
     * @param search_string   String to be searched in raw content.
     * @return this
     */

    public VocabularyCreator addEntry(String search_string) {
        this.entries.add(new VocabularyEntry(search_string, search_string));
        return this;
    }

    /**
     * Append single search string with category in the vocab.
     *
     * @param search_string   String to be searched in raw content.
     * @param category        Category.
     * @return this
     */
    public VocabularyCreator addEntry(String search_string, String category) {
        this.entries.add(new VocabularyEntry(category, search_string));
        return this;
    }

    /**
     * The inputStream of the vocab source.
     *
     * @param inputStream Vocabulary file inputStream.
     * @return this
     */
    public VocabularyCreator source(InputStream inputStream) {
        this.inputStream = inputStream;
        return this;
    }

    /**
     * Make the request to the API to perform the create.
     *
     * @param client QTClient with which to make the request
     * @return Created Vocabulary
     */
    @Override
    public Vocabulary create(QTRestClient client) {
        Request request = createRequest();
        addPayload(request, client);

        Response response = client.request(request);

        if (response == null) {
            throw new QTApiConnectionException("Vocabulary creation failed: Unable to connect to server");
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
                String body = objectMapper.writeValueAsString(this);
                request.setBody(body);
            } catch (JsonProcessingException e) {
                throw new QTApiException(e.getMessage(), e);
            }
        } else {
            request.setInputStream(this.inputStream);
     //       request.setFileName(this.fileName);
            request.addPostParam("name", name);
        }
    }
}
