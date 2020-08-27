package com.quantxt.sdk.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.function.Predicate;

public class QTRestClient {

    public static final int HTTP_STATUS_CODE_CREATED = 201;
    public static final int HTTP_STATUS_CODE_NO_CONTENT = 204;
    public static final int HTTP_STATUS_CODE_OK = 200;
    public static final Predicate<Integer> SUCCESS = i -> i != null && i >= 200 && i < 300;

    private final ObjectMapper objectMapper;
    private final String apiKey;
    private final HttpClient httpClient;

    private QTRestClient(Builder b) {
        this.apiKey = b.apiKey;
        this.httpClient = b.httpClient;
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Make a request to QT API.
     *
     * @param request request to make
     * @return Response object
     */
    public Response request(final Request request) {
        request.setApiKey(apiKey);
        return httpClient.makeRequest(request);
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public static class Builder {
        private String apiKey;
        private HttpClient httpClient;

        /**
         * Create a new QT Rest Client.
         *
         * @param apiKey API key to use
         */
        public Builder(String apiKey) {
            this.apiKey = apiKey;
        }

        public Builder httpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        /**
         * Build new QTRestClient.
         *
         * @return QTRestClient instance
         */
        public QTRestClient build() {
            if (this.httpClient == null) {
                this.httpClient = new NetworkHttpClient();
            }
            return new QTRestClient(this);
        }
    }
}
