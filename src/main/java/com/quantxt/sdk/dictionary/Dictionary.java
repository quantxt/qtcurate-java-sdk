package com.quantxt.sdk.dictionary;

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
public class Dictionary extends Resource {

    private static final long serialVersionUID = 554603237874587L;

    /**
     * Create a DictionaryCreator to execute create.
     *
     * @return DictionaryCreator capable of executing the create
     */
    public static DictionaryCreator creator() {
        return new DictionaryCreator();
    }

    /**
     * Create a DictionaryUpdater to execute update.
     *
     * @return DictionaryUpdater capable of executing the update
     */
    public static DictionaryUpdater updater(String id) {
        return new DictionaryUpdater(id);
    }

    /**
     * Create a DictionaryFetcher to execute fetch.
     *
     * @param id The ID that identifies the resource to fetch
     * @return DictionaryFetcher capable of executing the fetch
     */
    public static DictionaryFetcher fetcher(String id) {
        return new DictionaryFetcher(id);
    }

    /**
     * Create a DictionaryReader to execute read.
     *
     * @return DictionaryReader capable of executing the read
     */
    public static DictionaryReader reader() {
        return new DictionaryReader();
    }

    /**
     * Create a DictionaryDeleter to execute delete.
     *
     * @param id The ID of the Dictionary resource to delete
     * @return DictionaryDeleter capable of executing the delete
     */
    public static DictionaryDeleter deleter(String id) {
        return new DictionaryDeleter(id);
    }

    /**
     * Converts a JSON InputStream into a Dictionary object using the provided ObjectMapper.
     *
     * @param json         Raw JSON InputStream
     * @param objectMapper Jackson ObjectMapper
     * @return Dictionary object represented by the provided JSON
     */
    public static Dictionary fromJson(final InputStream json, final ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(json, Dictionary.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new QTApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new QTApiConnectionException(e.getMessage(), e);
        }
    }

    /**
     * Converts a JSON InputStream into a list of Dictionary objects using the provided ObjectMapper.
     *
     * @param json         Raw JSON InputStream
     * @param objectMapper Jackson ObjectMapper
     * @return Dictionary object represented by the provided JSON
     */
    public static List<Dictionary> listFromJson(final InputStream json, final ObjectMapper objectMapper) {
        try {
            return Arrays.asList(objectMapper.readValue(json, Dictionary[].class));
        } catch (final JsonMappingException | JsonParseException e) {
            throw new QTApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new QTApiConnectionException(e.getMessage(), e);
        }
    }

    private String id;
    private String key;
    private String name;
    private Boolean global;
    private List<Entry> entries;

    @JsonCreator
    private Dictionary(@JsonProperty("id") final String id,
                       @JsonProperty("key") final String key,
                       @JsonProperty("name") final String name,
                       @JsonProperty("global") final Boolean global,
                       @JsonProperty("entries") final List<Entry> entries) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.global = global;
        this.entries = entries;
    }

    public static class Entry {

        private String key;
        private String value;

        @JsonCreator
        public Entry(@JsonProperty("key") final String key,
              @JsonProperty("value") final String value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Returns dictionary entry key.
         *
         * @return Entry key.
         */
        public String getKey() {
            return key;
        }

        /**
         * Returns dictionary entry value.
         *
         * @return Entry value.
         */
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    /**
     * Returns dictionary ID.
     *
     * @return Dictionary ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns dictionary key.
     *
     * @return Dictionary key.
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns dictionary name.
     *
     * @return Dictionary name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns indicator whether this dictionary is global/available to all users.
     *
     * @return Dictionary global indicator.
     */
    public Boolean getGlobal() {
        return global;
    }

    /**
     * Returns dictionary entries.
     *
     * @return Dictionary entries.
     */
    public List<Entry> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", global=" + global +
                ", entries=" + entries +
                '}';
    }
}
