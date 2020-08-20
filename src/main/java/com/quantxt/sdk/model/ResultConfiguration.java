package com.quantxt.sdk.model;

import java.util.List;

public class ResultConfiguration {
    private String title;
    protected String id;
    protected String username;
    protected Integer numWorkers;
    protected DictionaryDto[] searchDictionaries;
    private List<String> files;

    public ResultConfiguration(){

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getNumWorkers() {
        return numWorkers;
    }

    public void setNumWorkers(Integer numWorkers) {
        this.numWorkers = numWorkers;
    }

    public DictionaryDto[] getSearchDictionaries() {
        return searchDictionaries;
    }

    public void setSearchDictionaries(DictionaryDto[] searchDictionaries) {
        this.searchDictionaries = searchDictionaries;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
}
