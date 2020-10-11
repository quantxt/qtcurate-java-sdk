package com.quantxt.sdk.extraction.job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.quantxt.sdk.document.Document;
import com.quantxt.sdk.extraction.model.Model;
import com.quantxt.sdk.resource.Resource;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Job extends Resource {

    private static final long serialVersionUID = 7089357972945039793L;

    private Model model;
    private String id;
    private String description;
    private List<Document> documents;

    /**
     * Create a JobCreator to execute create.
     * @param description The description for the data mining job
     * @return ModelCreator capable of executing the create
     */
    public static JobCreator creator(String description) {
        return new JobCreator(description);
    }

    /**
     * Create a JobFetcher to execute fetch.
     *
     * @param id The ID that identifies the resource to fetch
     * @return JobFetcher capable of executing the fetch
     */
    public static JobFetcher fetcher(String id) {
        return new JobFetcher(id);
    }

    /**
     * Create a JobDeleter to execute delete.
     *
     * @param id The id of the Model resource to delete
     * @return JobDeleter capable of executing the delete
     */
    public static JobDeleter deleter(String id) {
        return new JobDeleter(id);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public Job(String description){
        this.description = description;
    }
    /**
     * Returns Job ID.
     *
     * @return Job ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns model
     *
     * @return extractors.
     */
    public Model getModel() {
        return model;
    }

    /**
     * Returns list of Documents
     *
     * @return files.
     */
    public List<Document> getDocuments() {
        return documents;
    }
}
