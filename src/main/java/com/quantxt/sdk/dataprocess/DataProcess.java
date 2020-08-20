package com.quantxt.sdk.dataprocess;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantxt.sdk.exception.QTApiConnectionException;
import com.quantxt.sdk.exception.QTApiException;
import com.quantxt.sdk.model.Extractor;
import com.quantxt.sdk.resource.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataProcess extends Resource {

    private static final long serialVersionUID = 7089357972945039793L;

    private String id;
    private String description;
    private List<Extractor> extractors;
    private List<String> files;
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
     * Create a DataProcessUpdater to execute update.
     * @param id The name of the search to be updated
     * @return DataProcessUpdater capable of executing the update
     */
    public static DataProcessUpdater updater(String id) {
        return new DataProcessUpdater(id);
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
     * Create a DataProcessReader to execute read.
     *
     * @return DataProcessReader capable of executing the read
     */
    public static DataProcessReader reader() {
        return new DataProcessReader();
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

    /**
     * Converts a JSON InputStream into a DataProcess object using the provided ObjectMapper.
     *
     * @param json         Raw JSON InputStream
     * @param objectMapper Jackson ObjectMapper
     * @return DataProcess object represented by the provided JSON
     */
    public static DataProcess fromJson(final InputStream json, final ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(json, DataProcess.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new QTApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new QTApiConnectionException(e.getMessage(), e);
        }
    }

    /**
     * Converts a JSON InputStream from element into a DataProcess object using the provided ObjectMapper.
     *
     * @param json         Raw JSON InputStream
     * @param path         Path to element
     * @param objectMapper Jackson ObjectMapper
     * @return DataProcess object represented by the provided JSON
     */
    public static DataProcess fromJson(final InputStream json, final String path, final ObjectMapper objectMapper) {
        try {
            JsonNode jsonNode = objectMapper.readValue(json, JsonNode.class);
            JsonNode metaNode = jsonNode.get(path);

            return objectMapper.treeToValue(metaNode, DataProcess.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new QTApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new QTApiConnectionException(e.getMessage(), e);
        }
    }

    /**
     * Converts a JSON InputStream into a list of DataProcess objects using the provided ObjectMapper.
     *
     * @param json         Raw JSON InputStream
     * @param objectMapper Jackson ObjectMapper
     * @return List of DataProcess objects represented by the provided JSON
     */
    public static List<DataProcess> listFromJson(final InputStream json, final ObjectMapper objectMapper) {
        try {
            return Arrays.asList(objectMapper.readValue(json, DataProcess[].class));
        } catch (final JsonMappingException | JsonParseException e) {
            throw new QTApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new QTApiConnectionException(e.getMessage(), e);
        }
    }

    public DataProcess() {
        this.extractors = new ArrayList<>();
        this.files = new ArrayList<>();
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

    public void setFiles(List<String> files) {
        this.files = files;
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
     * Returns list of file uuids processed.
     *
     * @return files.
     */
    public List<String> getFiles() {
        return files;
    }

    @Override
    public String toString() {
        return "DataProcess{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", extractors=" + extractors +
                ", files=" + files +
                '}';
    }
}
