package com.quantxt.sdk.extraction;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.quantxt.sdk.client.HttpMethod;
import com.quantxt.sdk.client.QTRestClient;
import com.quantxt.sdk.client.Request;
import com.quantxt.sdk.client.Response;
import com.quantxt.sdk.document.Document;
import com.quantxt.sdk.exception.QTApiConnectionException;
import com.quantxt.sdk.exception.QTApiException;
import com.quantxt.sdk.exception.QTRestException;
import com.quantxt.sdk.model.Extractor;
import com.quantxt.sdk.model.DictionaryDto;
import com.quantxt.sdk.model.SearchRequestDto;
import com.quantxt.sdk.resource.Creator;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ModelCreator extends Creator<Model> {

    private Model model;

    public ModelCreator(String description){
        model = new Model();
        model.setDescription(description);
    }

    public ModelCreator withNumWorkers(Integer numWorkers) {
        this.model.setNumWorkers(numWorkers);
        return this;
    }

    public ModelCreator withDocuments(List<Document> documents) {
        this.model.setDocuments(documents);
        return this;
    }

    /**
     * Adds an extractors rule.
     *
     * @param extractor Extractor to parse text.
     * @return this
     */

    public ModelCreator addExtractor(Extractor extractor) {
        this.model.getExtractors().add(extractor);
        return this;
    }

    /**
     * Specifies the Extraction Dictionaries
     *
     * @param extractors list of extractors
     * @return this
     */
    public ModelCreator withExtractors(List<Extractor> extractors) {
        this.model.setExtractors(extractors);
        return this;
    }

    /**
     * Make the request to the API to perform the create.
     *
     * @param client QTClient with which to make the request
     * @return Created Model
     */
    @Override
    public Model create(QTRestClient client) {
        Request request = new Request(HttpMethod.POST, "/search/new");
        addPayload(request, client);

        Response response = client.request(request);

        if (response == null) {
            throw new QTApiConnectionException("Model creation failed: Unable to connect to server");
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
            SearchRequestDto searchRequestDto = client.getObjectMapper().readValue(response.getStream(), SearchRequestDto.class);
            model.setId(searchRequestDto.getId());

            return model;

        } catch (Exception e){
            throw new QTApiException("Error is submitting data process job");
        }
    }

    /**
     * Add the requested JSON body to the Request.
     *
     * @param request Request to add post params to
     */
    private void addPayload(final Request request, final QTRestClient client) {
        try {
            // convert to SearchRequestDto
            SearchRequestDto searchRequestDto = new SearchRequestDto();
            List<String> files = new ArrayList<>();
            for (Document document : model.getDocuments()){
                files.add(document.getId());
            }
            searchRequestDto.setFiles(files);
            searchRequestDto.setNumWorkers(model.getNumWorkers());
            searchRequestDto.setTitle(model.getDescription());
            // convert dictionaries
            List<DictionaryDto> dictionaryDtos = new ArrayList<>();
            for (Extractor extractor : model.getExtractors()){
                DictionaryDto dictionaryDto = new DictionaryDto(extractor);
                dictionaryDtos.add(dictionaryDto);
            }
            searchRequestDto.setSearchDictionaries(dictionaryDtos);
            request.setBody(client.getObjectMapper().writeValueAsString(searchRequestDto));
        } catch (JsonProcessingException e) {
            throw new QTApiException(e.getMessage(), e);
        }
    }
}
