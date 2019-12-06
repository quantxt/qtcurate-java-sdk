package com.quantxt.sdk.client;

public abstract class HttpClient {

    public abstract Response makeRequest(final Request request);
}
