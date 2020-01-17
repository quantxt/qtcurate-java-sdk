package com.quantxt.sdk.dataprocess;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantxt.sdk.exception.QTApiConnectionException;
import com.quantxt.sdk.exception.QTApiException;
import com.quantxt.sdk.resource.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataProcess extends Resource {

    private static final long serialVersionUID = 7089357972945039793L;

    /**
     * Create a DataProcessCreator to execute create.
     * @param title The name for the data mining job
     * @return DataProcessCreator capable of executing the create
     */
    public static DataProcessCreator creator(String title) {
        return new DataProcessCreator(title);
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
     * @param index The ID that identifies the resource to fetch
     * @return DataProcessFetcher capable of executing the fetch
     */
    public static DataProcessFetcher fetcher(String index) {
        return new DataProcessFetcher(index);
    }

    /**
     * Create a DataProcessDeleter to execute delete.
     *
     * @param index The index of the DataProcess resource to delete
     * @return DataProcessDeleter capable of executing the delete
     */
    public static DataProcessDeleter deleter(String index) {
        return new DataProcessDeleter(index);
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

    private String index;
    private String title;
    private Boolean autoTag;
    private Integer maxTokenPerUtt;
    private Integer minTokenPerUtt;
    private boolean excludeUttWithoutEntities;
    private List<SearchRule> searchDictionaries;
    private List<String> files;
    private Insights insights;

    @JsonCreator
    private DataProcess(@JsonProperty("index") final String index,
                        @JsonProperty("title") final String title,
                        @JsonProperty("get_phrases") final Boolean autoTag,
                        @JsonProperty("maxTokenPerUtt") final Integer maxTokenPerUtt,
                        @JsonProperty("minTokenPerUtt") final Integer minTokenPerUtt,
                        @JsonProperty("excludeUttWithoutEntities") final boolean excludeUttWithoutEntities,
                        @JsonProperty("searchDictionaries") final List<SearchRule> searchDictionaries,
                        @JsonProperty("files") final List<String> files,
                        @JsonProperty("insights") final Insights insights) {
        this.index = index;
        this.title = title;
        this.autoTag = autoTag;
        this.maxTokenPerUtt = maxTokenPerUtt;
        this.minTokenPerUtt = minTokenPerUtt;
        this.excludeUttWithoutEntities = excludeUttWithoutEntities;
        this.searchDictionaries = searchDictionaries;
        this.files = files;
        this.insights = insights;
    }

    /**
     * Returns DataProcess ID.
     *
     * @return DataProcess ID.
     */
    public String getIndex() {
        return index;
    }

    /**
     * Returns title.
     *
     * @return title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns autoTag.
     *
     * @return autoTag.
     */
    public Boolean getAutoTag() {
        return autoTag;
    }

    /**
     * Returns maxTokenPerUtt.
     *
     * @return maxTokenPerUtt.
     */
    public Integer getMaxTokenPerUtt() {
        return maxTokenPerUtt;
    }

    /**
     * Returns minTokenPerUtt.
     *
     * @return minTokenPerUtt.
     */
    public Integer getMinTokenPerUtt() {
        return minTokenPerUtt;
    }

    /**
     * Returns excludeUttWithoutEntities.
     *
     * @return excludeUttWithoutEntities.
     */
    public boolean isExcludeUttWithoutEntities() {
        return excludeUttWithoutEntities;
    }

    /**
     * Returns searchDictionaries.
     *
     * @return searchDictionaries.
     */
    public List<SearchRule> getSearchDictionaries() {
        return searchDictionaries;
    }

    /**
     * Returns fields.
     *
     * @return fields.
     */
    public List<String> getFiles() {
        return files;
    }

    /**
     * Returns insights.
     *
     * @return insights.
     */
    public Insights getInsights() {
        return insights;
    }

    @Override
    public String toString() {
        return "DataProcess{" +
                "index='" + index + '\'' +
                ", title='" + title + '\'' +
                ", autoTag=" + autoTag +
                ", maxTokenPerUtt=" + maxTokenPerUtt +
                ", minTokenPerUtt=" + minTokenPerUtt +
                ", excludeUttWithoutEntities=" + excludeUttWithoutEntities +
                ", searchDictionaries=" + searchDictionaries +
                ", files=" + files +
                ", insights=" + insights +
                '}';
    }
}
