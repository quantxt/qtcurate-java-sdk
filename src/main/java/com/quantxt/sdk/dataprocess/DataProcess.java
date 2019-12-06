package com.quantxt.sdk.dataprocess;

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
public class DataProcess extends Resource {

    private static final long serialVersionUID = 7089357972945039793L;

    /**
     * Create a DataProcessCreator to execute create.
     *
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
    public static DataProcessReader reader() { return new DataProcessReader(); }

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

    @JsonCreator
    private DataProcess(@JsonProperty("index") final String index, @JsonProperty("title") final String title) {
        this.index = index;
        this.title = title;
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

    @Override
    public String toString() {
        return "DataProcess{" +
                "index='" + index + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
