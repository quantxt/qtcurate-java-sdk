package com.quantxt.sdk.dataprocess;

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
import com.quantxt.sdk.model.SearchRequestDto;
import com.quantxt.sdk.model.UpdateSearchRequestDto;
import com.quantxt.sdk.resource.Creator;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DataProcessDuplicator extends Creator<DataProcess> {

    private String id;
    private List<Document> documents;

    public DataProcessDuplicator(String id){
        this.id = id;
    }

    public DataProcessDuplicator withDocuments(List<Document> documents) {
        this.documents = documents;
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
        Request request = new Request(HttpMethod.POST, "/search/new/" + this.id);
        addPayload(request, client);

        Response response = client.request(request);

        if (response == null) {
            throw new QTApiConnectionException("DataProcess update failed: Unable to connect to server");
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
            DataProcess dataProcess = new DataProcess();
            dataProcess.setId(searchRequestDto.getId());
            dataProcess.setDocuments(documents);
            return dataProcess;

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
        if (documents == null) return;
        try {
            UpdateSearchRequestDto updateSearchRequestDto = new UpdateSearchRequestDto();
            List<String> uuids = new ArrayList<>();
            for (Document document : documents){
                uuids.add(document.getId());
            }
            String requestBody = client.getObjectMapper().writeValueAsString(updateSearchRequestDto);
            if (requestBody != null && !requestBody.isEmpty()) {
                request.setBody(client.getObjectMapper().writeValueAsString(this));
            }
        } catch (JsonProcessingException e) {
            throw new QTApiException(e.getMessage(), e);
        }
    }
}
