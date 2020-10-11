package com.quantxt.sdk.extraction.job;

import com.quantxt.sdk.extraction.model.ModelFetcher;

public class JobFetcher extends ModelFetcher {

    /**
     * Construct a new {@link JobFetcher}.
     *
     * @param id The ID that identifies the resource to fetch
     */
    public JobFetcher(String id) {
        super(id);
    }
}
