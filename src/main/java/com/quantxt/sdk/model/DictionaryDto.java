package com.quantxt.sdk.model;

import java.util.List;

public class DictionaryDto {

    public String getVocabValueType() {
        return vocabValueType;
    }

    public void setVocabValueType(String vocabValueType) {
        this.vocabValueType = vocabValueType;
    }

    public enum SearchMode {ORDERED_SPAN, FUZZY_ORDERED_SPAN, SPAN, FUZZY_SPAN, PARTIAL_SPAN, PARTIAL_FUZZY_SPAN};
    public enum AnalyzeMode {EXACT, EXACT_CI, WHITESPACE, SIMPLE, STANDARD, STEM};

    protected String vocabId;
    protected String vocabName;
    protected String vocabValueType;
    protected Extractor.DataType dataType;
    protected List<String> stopwordList;
    protected List<String> synonymList;
    protected SearchMode searchMode;
    protected AnalyzeMode analyzeStrategy;
    protected String skipPatternBetweenKeyAndValue;
    protected String skipPatternBetweenValues;
    protected String phraseMatchingPattern;
    protected String[] phraseMatchingGroups;

    public DictionaryDto(Extractor extractor){

        this.searchMode = extractor.getSearchMode();
        this.analyzeStrategy = extractor.getAnalyzeMode();
        this.stopwordList = extractor.getStopwordList();
        this.synonymList = extractor.getSynonymList();
        this.vocabId = extractor.getVocabulary().getId();
        this.vocabName = extractor.getVocabulary().getName();

        if (extractor.getValidator() != null) {
            this.phraseMatchingPattern = extractor.getValidator().pattern();
            this.phraseMatchingGroups = new String[]{"1"};
            this.vocabValueType = "REGEX";
        }

        if (extractor.getPatternBetweenMultipleValues() != null) {
            this.skipPatternBetweenValues = extractor.getPatternBetweenMultipleValues().pattern();
        }

        this.dataType = extractor.getType();
    }

    public DictionaryDto(){

    }

    public String getVocabId() {
        return vocabId;
    }

    public void setVocabId(String vocabId) {
        this.vocabId = vocabId;
    }

    public String getVocabName() {
        return vocabName;
    }

    public void setVocabName(String vocabName) {
        this.vocabName = vocabName;
    }

    public Extractor.DataType getDataType() {
        return dataType;
    }

    public void setDataType(Extractor.DataType dataType) {
        this.dataType = dataType;
    }

    public List<String> getStopwordList() {
        return stopwordList;
    }

    public void setStopwordList(List<String> stopwordList) {
        this.stopwordList = stopwordList;
    }

    public List<String> getSynonymList() {
        return synonymList;
    }

    public void setSynonymList(List<String> synonymList) {
        this.synonymList = synonymList;
    }

    public SearchMode getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(SearchMode searchMode) {
        this.searchMode = searchMode;
    }

    public AnalyzeMode getAnalyzeStrategy() {
        return analyzeStrategy;
    }

    public void setAnalyzeStrategy(AnalyzeMode analyzeStrategy) {
        this.analyzeStrategy = analyzeStrategy;
    }

    public String getSkipPatternBetweenKeyAndValue() {
        return skipPatternBetweenKeyAndValue;
    }

    public void setSkipPatternBetweenKeyAndValue(String skipPatternBetweenKeyAndValue) {
        this.skipPatternBetweenKeyAndValue = skipPatternBetweenKeyAndValue;
    }

    public String getSkipPatternBetweenValues() {
        return skipPatternBetweenValues;
    }

    public void setSkipPatternBetweenValues(String skipPatternBetweenValues) {
        this.skipPatternBetweenValues = skipPatternBetweenValues;
    }

    public String getPhraseMatchingPattern() {
        return phraseMatchingPattern;
    }

    public void setPhraseMatchingPattern(String phraseMatchingPattern) {
        this.phraseMatchingPattern = phraseMatchingPattern;
    }

    public String[] getPhraseMatchingGroups() {
        return phraseMatchingGroups;
    }

    public void setPhraseMatchingGroups(String[] phraseMatchingGroups) {
        this.phraseMatchingGroups = phraseMatchingGroups;
    }
}
