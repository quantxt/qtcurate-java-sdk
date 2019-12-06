package com.quantxt.sdk.search;

import com.quantxt.sdk.resource.Resource;

public class Search extends Resource {

    private static final long serialVersionUID = 554603237874532L;

    /**
     * Create a SearchExporter to execute export.
     *
     * @param id The ID that identifies the resource to export
     * @return SearchExporter capable of executing the export
     */
    public static SearchExporter exporter(String id) {
        return new SearchExporter(id);
    }
}
