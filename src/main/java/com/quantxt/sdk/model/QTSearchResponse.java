package com.quantxt.sdk.model;

/**
 * Created by matin on 4/9/17.
 */

public class QTSearchResponse {

    private ResultConfiguration meta;

    public QTSearchResponse(){

    }

    public ResultConfiguration getMeta() {
        return meta;
    }

    public void setMeta(ResultConfiguration meta) {
        this.meta = meta;
    }
}
