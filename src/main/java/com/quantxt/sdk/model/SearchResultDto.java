package com.quantxt.sdk.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class SearchResultDto {

    private String searchId;

    private String title;

    private String link;
    private String source;
    private LocalDateTime date;
    private Integer position;
    private ExtInterval[] values;

    @JsonIgnore
    private Map<String, Object> facetMap = new HashMap<>();

    private String language;
    private String[] tags;
    private Double score;
    private Double alignscore;
    private Double w2vscore;

    @JsonAnyGetter
    public Map<String, Object> getFacets() {
        return facetMap;
    }

    @JsonAnySetter
    public void setUnknownFields(final String key, final Object value) {
        facetMap.put(key, value);
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public ExtInterval[] getValues() {
        return values;
    }

    public void setValues(ExtInterval[] values) {
        this.values = values;
    }

    public Map<String, Object> getFacetMap() {
        return facetMap;
    }

    public void setFacetMap(Map<String, Object> facetMap) {
        this.facetMap = facetMap;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getAlignscore() {
        return alignscore;
    }

    public void setAlignscore(Double alignscore) {
        this.alignscore = alignscore;
    }

    public Double getW2vscore() {
        return w2vscore;
    }

    public void setW2vscore(Double w2vscore) {
        this.w2vscore = w2vscore;
    }
}