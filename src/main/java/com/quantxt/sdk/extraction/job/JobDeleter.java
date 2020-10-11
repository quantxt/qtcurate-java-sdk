package com.quantxt.sdk.extraction.job;

import com.quantxt.sdk.extraction.model.ModelDeleter;

public class JobDeleter extends ModelDeleter {

    /**
     * Construct a new ModelDeleter.
     *
     * @param id The index of the data process resource to delete
     */
    public JobDeleter(String id) {
        super(id);
    }

}
