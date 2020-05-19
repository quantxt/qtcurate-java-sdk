package com.quantxt.sdk.dictionary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DictionaryEntry {

    @JsonProperty("key")
    private String category;

    @JsonProperty("value")
    private String search_string;

    @JsonCreator
    public DictionaryEntry(@JsonProperty("key") final String category,
                           @JsonProperty("value") final String search_string) {
        this.category = category;
        this.search_string = search_string;
    }

    /**
     * Returns category of the item
     *
     * @return key.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns look up string of the item.
     *
     * @return Entry search string.
     */
    public String getSearch_string() {
        return search_string;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "category='" + category + '\'' +
                ", search_string='" + search_string + '\'' +
                '}';
    }
}
