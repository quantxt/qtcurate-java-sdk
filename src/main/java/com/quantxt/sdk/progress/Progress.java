package com.quantxt.sdk.progress;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantxt.sdk.exception.QTApiConnectionException;
import com.quantxt.sdk.exception.QTApiException;
import com.quantxt.sdk.resource.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Progress extends Resource {

    private static final long serialVersionUID = 554603237874587L;

    /**
     * Create a ProgressFetcher to execute fetch.
     *
     * @param id The ID that identifies the resource to fetch
     * @return ProgressFetcher capable of executing the fetch
     */
    public static ProgressFetcher fetcher(String id) {
        return new ProgressFetcher(id);
    }

    /**
     * Create a ProgressReader to execute read.
     *
     * @return ProgressReader capable of executing the read
     */
    public static ProgressReader reader() {
        return new ProgressReader();
    }

    /**
     * Converts a JSON InputStream into a Progress object using the provided
     * ObjectMapper.
     *
     * @param json         Raw JSON InputStream
     * @param objectMapper Jackson ObjectMapper
     * @return Progress object represented by the provided JSON
     */
    public static Progress fromJson(final InputStream json, final ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(json, Progress.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new QTApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new QTApiConnectionException(e.getMessage(), e);
        }
    }

    /**
     * Converts a JSON InputStream into a list of Progress objects using the provided ObjectMapper.
     *
     * @param json         Raw JSON InputStream
     * @param objectMapper Jackson ObjectMapper
     * @return List of Progress objects represented by the provided JSON
     */
    public static List<Progress> listFromJson(final InputStream json, final ObjectMapper objectMapper) {
        try {
            return Arrays.asList(objectMapper.readValue(json, Progress[].class));
        } catch (final JsonMappingException | JsonParseException e) {
            throw new QTApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new QTApiConnectionException(e.getMessage(), e);
        }
    }

    private String id;
    private Integer progress;
    private String message;

    @JsonCreator
    private Progress(@JsonProperty("id") final String id,
                     @JsonProperty("progress") final Integer progress,
                     @JsonProperty("message") final String message) {
        this.id = id;
        this.progress = progress;
        this.message = message;
    }

    /**
     * Returns index.
     *
     * @return index.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns progress.
     *
     * @return progress.
     */
    public Integer getProgress() {
        return progress;
    }

    /**
     * Returns message.
     *
     * @return message.
     */
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Progress{" +
                "id='" + id + '\'' +
                ", progress=" + progress +
                ", message='" + message + '\'' +
                '}';
    }
}