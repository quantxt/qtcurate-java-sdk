package com.quantxt.sdk.vocabulary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class Vocabulary extends Resource {

    private static final long serialVersionUID = 554603237874587L;

    private String id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<VocabularyEntry> entries;

    /**
     * Create a VocabularyCreator to execute create.
     *
     * @return VocabularyCreator capable of executing the create
     */
    public static VocabularyCreator creator() {
        return new VocabularyCreator();
    }

    /**
     * Create a VocabularyUpdater to execute update.
     *
     * @param id Id of the vocab to be updated
     * @return VocabularyUpdater capable of executing the update
     */
    public static VocabularyUpdater updater(String id) {
        return new VocabularyUpdater(id);
    }

    /**
     * Create a VocabularyFetcher to execute fetch.
     *
     * @param id The ID that identifies the resource to fetch
     * @return VocabularyFetcher capable of executing the fetch
     */
    public static VocabularyFetcher fetcher(String id) {
        return new VocabularyFetcher(id);
    }

    /**
     * Create a VocabularyReader to get list of available dictionaries.
     *
     * @return VocabularyReader capable of executing the read
     */
    public static VocabularyReader reader() {
        return new VocabularyReader();
    }

    /**
     * Create a VocabularyDeleter to execute delete.
     *
     * @param id The ID of the Vocabulary resource to delete
     * @return VocabularyDeleter capable of executing the delete
     */
    public static VocabularyDeleter deleter(String id) {
        return new VocabularyDeleter(id);
    }

    /**
     * Converts a JSON InputStream into a Vocabulary object using the provided ObjectMapper.
     *
     * @param json         Raw JSON InputStream
     * @param objectMapper Jackson ObjectMapper
     * @return Vocabulary object represented by the provided JSON
     */
    public static Vocabulary fromJson(final InputStream json, final ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(json, Vocabulary.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new QTApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new QTApiConnectionException(e.getMessage(), e);
        }
    }

    /**
     * Converts a JSON InputStream into a list of Vocabulary objects using the provided ObjectMapper.
     *
     * @param json         Raw JSON InputStream
     * @param objectMapper Jackson ObjectMapper
     * @return Vocabulary object represented by the provided JSON
     */
    public static List<Vocabulary> listFromJson(final InputStream json, final ObjectMapper objectMapper) {
        try {
            return Arrays.asList(objectMapper.readValue(json, Vocabulary[].class));
        } catch (final JsonMappingException | JsonParseException e) {
            throw new QTApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new QTApiConnectionException(e.getMessage(), e);
        }
    }

    @JsonCreator
    public Vocabulary(@JsonProperty("id") final String id,
                       @JsonProperty("name") final String name,
                       @JsonProperty("entries") final List<VocabularyEntry> entries) {
        this.id = id;
        this.name = name;
        this.entries = entries;
    }

    /**
     * Returns vocab ID.
     *
     * @return Vocabulary ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns vocab name.
     *
     * @return Vocabulary name.
     */
    public String getName() {
        return name;
    }


    /**
     * Returns vocab entries.
     *
     * @return Vocabulary entries.
     */
    public List<VocabularyEntry> getEntries() {
        return entries;
    }
}
