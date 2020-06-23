package com.quantxt.sdk.dictionary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DictionaryEntry {

    private String category;
    private String str;

    @JsonCreator
    public DictionaryEntry(@JsonProperty("category") final String category,
                           @JsonProperty("str") final String search_string) {
        this.category = category;
        this.str = search_string;
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
        return str;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "category='" + category + '\'' +
                ", search_string='" + str + '\'' +
                '}';
    }
}
