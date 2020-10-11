package com.quantxt.sdk.extraction.job;

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
import com.quantxt.sdk.extraction.model.Model;
import com.quantxt.sdk.model.SearchRequestDto;
import com.quantxt.sdk.resource.Creator;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class JobCreator extends Creator<Job>  {

    private Job job;

    public JobCreator(String description){
        job = new Job(description);
    }

    public JobCreator withDocuments(List<Document> documents) {
        this.job.setDocuments(documents);
        return this;
    }

    public JobCreator withModel(Model model){
        this.job.setModel(model);
        return this;
    }

    private Request createJob(QTRestClient client){
        String model_id = job.getModel().getId();
        Request request = new Request(HttpMethod.POST, "/search/new/" + model_id);
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        List<String> files = new ArrayList<>();
        for (Document document : job.getDocuments()){
            files.add(document.getId());
        }
        searchRequestDto.setTitle(job.getDescription());
        searchRequestDto.setFiles(files);
        try {
            request.setBody(client.getObjectMapper().writeValueAsString(searchRequestDto));
        } catch (JsonProcessingException e) {
            throw new QTApiException(e.getMessage(), e);
        }
        return request;
    }

    /**
     * Make the request to the API to perform the create.
     *
     * @param client QTClient with which to make the request
     * @return Created Model
     */
    @Override
    public Job create(QTRestClient client) {
        Request request = createJob(client);
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
            job.setId(searchRequestDto.getId());

            return job;

        } catch (Exception e){
            throw new QTApiException("Error is submitting data process job");
        }
    }
}
