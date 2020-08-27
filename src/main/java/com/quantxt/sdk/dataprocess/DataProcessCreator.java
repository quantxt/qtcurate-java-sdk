package com.quantxt.sdk.dataprocess;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.quantxt.sdk.client.HttpMethod;
import com.quantxt.sdk.client.QTRestClient;
import com.quantxt.sdk.client.Request;
import com.quantxt.sdk.client.Response;
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
public class DataProcessCreator extends Creator<DataProcess> {

    private DataProcess dataProcess;

    public DataProcessCreator(String description){
        dataProcess = new DataProcess();
        dataProcess.setDescription(description);
    }

    public DataProcessCreator withNumWorkers(Integer numWorkers) {
        this.dataProcess.setNumWorkers(numWorkers);
        return this;
    }

    public DataProcessCreator withFiles(List<String> files) {
        this.dataProcess.setFiles(files);
        return this;
    }

    /**
     * Adds an extractors rule.
     *
     * @param extractor Extractor to parse text.
     * @return this
     */
    public DataProcessCreator addExtractor(Extractor extractor) {
        this.dataProcess.getExtractors().add(extractor);
        return this;
    }

    /**
     * Specifies the Extraction Dictionaries
     *
     * @param extractors list of extractors
     * @return this
     */
    public DataProcessCreator withExtractors(List<Extractor> extractors) {
        this.dataProcess.setExtractors(extractors);
        return this;
    }

    /**
     * Make the request to the API to perform the create.
     *
     * @param client QTClient with which to make the request
     * @return Created DataProcess
     */
    @Override
    public DataProcess create(QTRestClient client) {
        Request request = new Request(HttpMethod.POST, "/search/new");
        addPayload(request, client);

        Response response = client.request(request);

        if (response == null) {
            throw new QTApiConnectionException("DataProcess creation failed: Unable to connect to server");
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

        return DataProcess.fromJson(response.getStream(), client.getObjectMapper());
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
            searchRequestDto.setFiles(dataProcess.getFiles());
            searchRequestDto.setNumWorkers(dataProcess.getNumWorkers());
            searchRequestDto.setTitle(dataProcess.getDescription());
            // convert dictionaries
            List<DictionaryDto> dictionaryDtos = new ArrayList<>();
            for (Extractor extractor : dataProcess.getExtractors()){
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
