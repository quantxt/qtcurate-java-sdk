package com.quantxt.sdk.dataprocess;

import com.fasterxml.jackson.annotation.*;
import com.quantxt.sdk.dictionary.Dictionary;
import com.quantxt.sdk.exception.QTApiException;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchRule implements Serializable {

    public enum SearchMode {ORDERED_SPAN, FUZZY_ORDERED_SPAN, SPAN, FUZZY_SPAN, PARTIAL_SPAN,
        PARTIAL_FUZZY_SPAN};

    public enum AnalyzeMode {EXACT, EXACT_CI, WHITESPACE, SIMPLE, STANDARD, STEM};

    public enum ChunkMode {NONE, SENTENCE, PARAGRAPH, PAGE};

    private static final long serialVersionUID = -6238012691404059856L;

    private String vocabPath;
    private String vocabValueType;
    private String phraseMatchingPattern;
    private String [] phraseMatchingGroups;

    private SearchMode searchMode;
    private AnalyzeMode analyzeStrategy;

    private String skipPatternBetweenKeyAndValue;
    private String skipPatternBetweenValues;

    private String [] stopwordList;
    private String [] synonymList;

    @JsonCreator
    public SearchRule(@JsonProperty("vocabPath") String vocabPath,
                      @JsonProperty("vocabValueType") String vocabValueType,
                      @JsonProperty("vocabRegex") String phraseMatchingPattern,
                      @JsonProperty("vocabRegexGroups") String [] phraseMatchingGroupStr,
                      @JsonProperty("skipPatternBetweenKeyAndValue") String skipPatternBetweenKeyAndValue,
                      @JsonProperty("skipPatternBetweenValues") String skipPatternBetweenValues,
                      @JsonProperty("searchMode") String searchMode,
                      @JsonProperty("analyzeMode") String analyzeMode,
                      @JsonProperty("stopwordList") String [] stopwordList,
                      @JsonProperty("synonymList") String [] synonymList)
    {
        this.vocabPath = vocabPath;
        this.vocabValueType = vocabValueType;
        this.phraseMatchingPattern = phraseMatchingPattern;
        this.phraseMatchingGroups = phraseMatchingGroupStr;
        this.skipPatternBetweenKeyAndValue = skipPatternBetweenKeyAndValue;
        this.skipPatternBetweenValues = skipPatternBetweenValues;

        if (searchMode != null) {
            this.searchMode = SearchMode.valueOf(searchMode);
        }
        if (analyzeMode != null) {
            this.analyzeStrategy = AnalyzeMode.valueOf(analyzeMode);
        }

        this.stopwordList = stopwordList;
        this.synonymList = synonymList;
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

    public SearchRule(Dictionary dictionary, DataProcessCreator.DictionaryType type,
                      String regex, String [] groups) {
        this.validateDictionary(dictionary);
        this.vocabPath = dictionary.getKey();
        this.vocabValueType = type.toString();
        this.phraseMatchingPattern = regex;
        this.phraseMatchingGroups = groups;
    }

    public SearchRule setSkipPatternBetweenKeyAndValue(String skipPatternBetweenKeyAndValue){
        this.skipPatternBetweenKeyAndValue = skipPatternBetweenKeyAndValue;
        return this;
    }

    public SearchRule setSkipPatternBetweenValues(String skipPatternBetweenValues){
        this.skipPatternBetweenValues = skipPatternBetweenValues;
        return this;
    }

    public SearchRule setSearchMode(String searchMode){
        this.searchMode = SearchMode.valueOf(searchMode);
        return this;
    }

    public SearchRule setAnlyzeMode(String analyzeMode){
        this.analyzeStrategy = AnalyzeMode.valueOf(analyzeMode);
        return this;
    }

    public SearchRule setStopwordList(String [] stopwordList){
        this.stopwordList = stopwordList;
        return this;
    }

    public SearchRule setSynonymList(String [] synonymList){
        this.synonymList = synonymList;
        return this;
    }

    private void validateDictionary(Dictionary dictionary) {
        if (dictionary.getKey().isEmpty()) {
            throw new QTApiException("You must provide a valid dictionary");
        }
    }
}
