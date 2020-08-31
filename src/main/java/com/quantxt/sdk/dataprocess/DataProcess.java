package com.quantxt.sdk.dataprocess;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.quantxt.sdk.document.Document;
import com.quantxt.sdk.model.Extractor;
import com.quantxt.sdk.resource.Resource;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataProcess extends Resource {

    private static final long serialVersionUID = 7089357972945039793L;

    private String id;
    private String description;
    private List<Extractor> extractors;
    private List<Document> documents;
    private Integer numWorkers = 8;

    /**
     * Create a DataProcessCreator to execute create.
     * @param description The description for the data mining job
     * @return DataProcessCreator capable of executing the create
     */
    public static DataProcessCreator creator(String description) {
        return new DataProcessCreator(description);
    }

    /**
     * Create a DataProcessDuplicator from an existing id
     * @param id The name of the existing search id
     * @return DataProcessDuplicator capable of executing the update
     */
    public static DataProcessDuplicator duplicator(String id) {
        return new DataProcessDuplicator(id);
    }

    /**
     * Create a DataProcessFetcher to execute fetch.
     *
     * @param id The ID that identifies the resource to fetch
     * @return DataProcessFetcher capable of executing the fetch
     */
    public static DataProcessFetcher fetcher(String id) {
        return new DataProcessFetcher(id);
    }

    /**
     * Create a DataProcessDeleter to execute delete.
     *
     * @param id The id of the DataProcess resource to delete
     * @return DataProcessDeleter capable of executing the delete
     */
    public static DataProcessDeleter deleter(String id) {
        return new DataProcessDeleter(id);
    }

    public DataProcess() {
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
     * Returns DataProcess ID.
     *
     * @return DataProcess ID.
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
        return "DataProcess{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", extractors=" + extractors +
                ", documents=" + documents +
                '}';
    }
}
