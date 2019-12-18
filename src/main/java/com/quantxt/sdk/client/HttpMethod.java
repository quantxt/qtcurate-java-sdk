package com.quantxt.sdk.client;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    DELETE("DELETE"),
    UPLOAD("POST"),
    PUT("PUT");

    private final String method;

    HttpMethod(final String method) {
        this.method = method;
    }

    public String toString() {
        return method;
    }

}