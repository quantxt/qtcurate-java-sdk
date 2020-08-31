package com.quantxt.sdk.model;

import java.util.List;

public class SearchRequestDto {

    private String id;
    private String title;
    private List<DictionaryDto> searchDictionaries;
    private Integer numWorkers;
    private List<String> files;
    private Boolean excludeUttWithoutEntities = true;
    private String chunk = "PAGE";

    public SearchRequestDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DictionaryDto> getSearchDictionaries() {
        return searchDictionaries;
    }

    public void setSearchDictionaries(List<DictionaryDto> searchDictionaries) {
        this.searchDictionaries = searchDictionaries;
    }

    public Integer getNumWorkers() {
        return numWorkers;
    }

    public void setNumWorkers(Integer numWorkers) {
        this.numWorkers = numWorkers;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public Boolean getExcludeUttWithoutEntities() {
        return excludeUttWithoutEntities;
    }

    public void setExcludeUttWithoutEntities(Boolean excludeUttWithoutEntities) {
        this.excludeUttWithoutEntities = excludeUttWithoutEntities;
    }

    public String getChunk() {
        return chunk;
    }

    public void setChunk(String chunk) {
        this.chunk = chunk;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
