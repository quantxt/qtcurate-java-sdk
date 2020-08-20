package com.quantxt.sdk.client;

import com.quantxt.sdk.exception.QTApiException;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    private final String API_URL = "api.quantxt.com";
    private final String protocol = "http://";

    private final HttpMethod method;
    private final String route;
    private String apiKey;

    private String body;
    private InputStream inputStream;
    private String fileName;
    private final Map<String, List<String>> postParams;
    /**
     * Create a new API request.
     *
     * @param method HTTP Method
     * @param route  api endpoint
     */
    public Request(final HttpMethod method,
                   final String route) {
        this.method = method;
        this.route  = route;
        this.postParams = new HashMap<>();
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }

    public boolean requiresAuthentication() {
        return apiKey != null;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(final String body) {
        if (body != null && !body.isEmpty()) {
            this.body = body;
        }
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Map<String, List<String>> getPostParams() {
        return postParams;
    }

    /**
     * Add a form parameter.
     *
     * @param name name of parameter
     * @param value value of parameter
     */
    public void addPostParam(final String name, final String value) {
        addParam(postParams, name, value);
    }

    private void addParam(final Map<String, List<String>> params, final String name, final String value) {
        if (!params.containsKey(name)) {
            params.put(name, new ArrayList<String>());
        }

        params.get(name).add(value);
    }

    /**
     * Build the URL for the request.
     *
     * @return URL for the request
     */
    public URL constructURL() {
        String env = QT.getEnviroment();
        String stringUri = protocol + env + API_URL + route;

        try {
            URI uri = new URI(stringUri);
            return uri.toURL();
        } catch (final URISyntaxException e) {
            throw new QTApiException("Bad URI: " + stringUri, e);
        } catch (final MalformedURLException e) {
            throw new QTApiException("Bad URL: " + stringUri, e);
        }
    }
}
