package com.quantxt.sdk.model;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO representing update search request.
 *
 * @author Branko Ostojic
 */

public class UpdateSearchRequestDto {
    private List<String> files = new ArrayList<>();

    public UpdateSearchRequestDto(){

    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
}
