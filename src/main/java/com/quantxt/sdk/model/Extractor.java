package com.quantxt.sdk.model;

import com.quantxt.sdk.dictionary.Dictionary;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Pattern;

import static com.quantxt.sdk.model.Extractor.Mode.FUZZY_UNORDERED_STEM;
import static com.quantxt.sdk.model.DictionaryDto.AnalyzeMode.SIMPLE;
import static com.quantxt.sdk.model.DictionaryDto.AnalyzeMode.STEM;
import static com.quantxt.sdk.model.DictionaryDto.SearchMode.*;

public class Extractor implements Serializable {

    public enum DataType {
        KEYWORD,
        STRING,
        LONG,
        DOUBLE,
        DATETIME
    }

    public enum Mode {
        SIMPLE,
        UNORDERED,
        STEM,
        UNORDERED_STEM,
        FUZZY_UNORDERED_STEM
    };

    private Dictionary dictionary;
    private DataType type;
    private Mode mode = Mode.SIMPLE;
    private Pattern validator;
    private Pattern patternBetweenMultipleValues;
    private List<String> stopwordList;
    private List<String> synonymList;

    public Extractor(DictionaryDto  dictionaryDto){
        DictionaryDto.AnalyzeMode analyzeMode = dictionaryDto.getAnalyzeStrategy();
        DictionaryDto.SearchMode searchMode = dictionaryDto.getSearchMode();
        if (analyzeMode == SIMPLE && searchMode == ORDERED_SPAN){
            this.mode = Mode.SIMPLE;
        } else if (analyzeMode == SIMPLE && searchMode == SPAN){
            this.mode = Mode.UNORDERED;
        } else if (analyzeMode == STEM && searchMode == ORDERED_SPAN){
            this.mode = Mode.STEM;
        } else if (analyzeMode == STEM && searchMode == SPAN){
            this.mode = Mode.UNORDERED_STEM;
        } else if (analyzeMode == STEM && searchMode == FUZZY_SPAN){
            this.mode = FUZZY_UNORDERED_STEM;
        } else {
            this.mode = Mode.SIMPLE;
        }
        this.stopwordList = dictionaryDto.getStopwordList();
        this.synonymList = dictionaryDto.getSynonymList();
        this.dictionary = new Dictionary(dictionaryDto.getVocabId(), dictionaryDto.getVocabName(), null);
        if (dictionaryDto.getPhraseMatchingPattern() != null){
            this.validator = Pattern.compile(dictionaryDto.getPhraseMatchingPattern());
        }

        if (dictionaryDto.getSkipPatternBetweenValues() != null){
            this.patternBetweenMultipleValues = Pattern.compile(dictionaryDto.getSkipPatternBetweenValues());
        }

        this.type = dictionaryDto.getDataType();
    }

    public Extractor(){

    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public DataType getType() {
        return type;
    }

    public List<String> getStopwordList() {
        return stopwordList;
    }

    public List<String> getSynonymList() {
        return synonymList;
    }

    public Mode getMode() {
        return mode;
    }

    public Pattern getPatternBetweenMultipleValues() {
        return patternBetweenMultipleValues;
    }

    public Pattern getValidator() {
        return validator;
    }

    public Extractor setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
        return this;
    }


    public Extractor setDataType(DataType type) {
        this.type = type;
        return this;
    }

    public Extractor setStopwordList(List<String> stopwordList) {
        this.stopwordList = stopwordList;
        return this;
    }

    public Extractor setSynonymList(List<String> synonymList) {
        this.synonymList = synonymList;
        return this;
    }

    public Extractor setValidator(Pattern validator) {
        this.validator = validator;
        return this;
    }

    public Extractor setPatternBetweenMultipleValues(Pattern patternBetweenMultipleValues) {
        this.patternBetweenMultipleValues = patternBetweenMultipleValues;
        return this;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }
}
