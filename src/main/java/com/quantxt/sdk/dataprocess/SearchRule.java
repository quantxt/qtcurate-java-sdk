package com.quantxt.sdk.dataprocess;

import com.fasterxml.jackson.annotation.*;
import com.quantxt.sdk.dictionary.Dictionary;
import com.quantxt.sdk.exception.QTApiException;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchRule implements Serializable {

    private static final long serialVersionUID = -6238012691404059856L;

    private String vocabPath;
    private String vocabValueType;
    private String phraseMatchingPattern;
    private String [] phraseMatchingGroups;

    @JsonCreator
    public SearchRule(@JsonProperty("vocabPath") String vocabPath,
                      @JsonProperty("vocabValueType") String vocabValueType,
                      @JsonProperty("vocabRegex") String phraseMatchingPattern,
                      @JsonProperty("vocabRegexGroups") String [] phraseMatchingGroupStr) {
        this.vocabPath = vocabPath;
        this.vocabValueType = vocabValueType;
        this.phraseMatchingPattern = phraseMatchingPattern;
        this.phraseMatchingGroups = phraseMatchingGroupStr;
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

    public SearchRule(Dictionary dictionary, DataProcessCreator.DictionaryType type, String regex, String [] groups) {
        this.validateDictionary(dictionary);
        this.vocabPath = dictionary.getKey();
        this.vocabValueType = type.toString();
        this.phraseMatchingPattern = regex;
        this.phraseMatchingGroups = groups;
    }

    private void validateDictionary(Dictionary dictionary) {
        if (dictionary.getKey().isEmpty()) {
            throw new QTApiException("You must provide a valid dictionary");
        }
    }
}
