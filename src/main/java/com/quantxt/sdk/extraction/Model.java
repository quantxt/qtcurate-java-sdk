package com.quantxt.sdk.extraction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.quantxt.sdk.document.Document;
import com.quantxt.sdk.model.Extractor;
import com.quantxt.sdk.resource.Resource;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Model extends Resource {

    private static final long serialVersionUID = 7089357972945039793L;

    private String id;
    private String description;
    private List<Extractor> extractors;
    private List<Document> documents;
    private Integer numWorkers = 8;

    /**
     * Create a ModelCreator to execute create.
     * @param description The description for the data mining job
     * @return ModelCreator capable of executing the create
     */
    public static ModelCreator creator(String description) {
        return new ModelCreator(description);
    }

    /**
     * Create a ModelFetcher to execute fetch.
     *
     * @param id The ID that identifies the resource to fetch
     * @return ModelFetcher capable of executing the fetch
     */
    public static ModelFetcher fetcher(String id) {
        return new ModelFetcher(id);
    }

    /**
     * Create a ModelDeleter to execute delete.
     *
     * @param id The id of the Model resource to delete
     * @return ModelDeleter capable of executing the delete
     */

    /**
     * Create a ModelReader to get list of available dataprocesses.
     *
     * @return ModelReader capable of executing the read
     */
    public static ModelReader reader() {
        return new ModelReader();
    }

    public static ModelDeleter deleter(String id) {
        return new ModelDeleter(id);
    }

    public Model() {
        this.extractors = new ArrayList<>();
        this.documents = new ArrayList<>();
    }

    public Integer getNumWorkers() {
        return numWorkers;
    }

    public void setNumWorkers(Integer numWorkers) {
        this.numWorkers = numWorkers;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExtractors(List<Extractor> extractors) {
        this.extractors = extractors;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    /**
     * Returns Model ID.
     *
     * @return Model ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns list of extractors.
     *
     * @return extractors.
     */
    public List<Extractor> getExtractors() {
        return extractors;
    }

    /**
     * Returns list of Documents
     *
     * @return files.
     */
    public List<Document> getDocuments() {
        return documents;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", extractors=" + extractors +
                ", documents=" + documents +
                '}';
    }
}
