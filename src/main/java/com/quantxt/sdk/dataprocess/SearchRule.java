package com.quantxt.sdk.dataprocess;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.quantxt.sdk.dictionary.Dictionary;
import com.quantxt.sdk.exception.QTApiException;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SearchRule implements Serializable {

    private static final long serialVersionUID = -6238012691404059856L;

    private String vocabPath;
    private String vocabValueType;

    @JsonCreator
    public SearchRule(@JsonProperty("vocabPath") String vocabPath,
                      @JsonProperty("vocabValueType") String vocabValueType) {
        this.vocabPath = vocabPath;
        this.vocabValueType = vocabValueType;
    }

    public SearchRule(Dictionary dictionary) {
        this.validateDictionary(dictionary);
        this.vocabPath = dictionary.getKey();
    }

    public SearchRule(Dictionary dictionary, DataProcessCreator.DictionaryType type) {
        this.validateDictionary(dictionary);
        this.vocabPath = dictionary.getKey();
        this.vocabValueType = type.toString();
    }

    private void validateDictionary(Dictionary dictionary) {
        if (dictionary.getKey().isEmpty()) {
            throw new QTApiException("You must provide a valid dictionary");
        }
    }
}
