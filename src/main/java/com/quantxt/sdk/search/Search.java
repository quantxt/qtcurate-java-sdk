package com.quantxt.sdk.search;

import com.quantxt.sdk.resource.Resource;

public class Search extends Resource {

    private static final long serialVersionUID = 554603237874532L;

    /**
     * Create a XlsxSearchExporter to execute export.
     *
     * @param id The ID that identifies the resource to export
     * @return XlsxSearchExporter capable of executing the XLSX export
     */
    public static XlsxSearchExporter xlsxExporter(String id) {
        return new XlsxSearchExporter(id);
    }

    /**
     * Create a JsonSearchExporter to execute export in JSON format.
     *
     * @param id The ID that identifies the resource to export
     * @return JsonSearchExporter capable of executing the export
     */
    public static JsonSearchExporter jsonExporter(String id) {
        return new JsonSearchExporter(id);
    }
}
