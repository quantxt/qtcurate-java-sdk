package com.quantxt.sdk.client;

import com.google.common.collect.Lists;
import com.quantxt.sdk.exception.QTApiException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class NetworkHttpClient extends HttpClient {

    private static final int CONNECTION_TIMEOUT = 100000;
    private static final int SOCKET_TIMEOUT = 305000;
    private final static String API_KEY_HEADER = "X-Api-Key";

    private final org.apache.http.client.HttpClient client;

    /**
     * Create a new HTTP Client.
     */
    public NetworkHttpClient() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .build();

        Collection<Header> headers = Lists.newArrayList(
                new BasicHeader(HttpHeaders.ACCEPT, "application/json"),
                new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "utf-8")
        );

        org.apache.http.impl.client.HttpClientBuilder clientBuilder = HttpClientBuilder.create();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(10);
        connectionManager.setMaxTotal(10 * 2);

        clientBuilder
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(config)
                .setDefaultHeaders(headers);

        client = clientBuilder.build();
    }

    /**
     * Make a request.
     *
     * @param request request to make
     * @return Response of the HTTP request
     */
    @Override
    public Response makeRequest(final Request request) {
        HttpMethod method = request.getMethod();
        RequestBuilder builder = RequestBuilder.create(method.toString())
                .setUri(request.constructURL().toString())
                .setVersion(HttpVersion.HTTP_1_1)
                .setCharset(StandardCharsets.UTF_8);

        HttpResponse response = null;

        try {
            switch (method) {
                case UPLOAD:
                    builder.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_OCTET_STREAM.getMimeType());

                    MultipartEntityBuilder mBuilder = MultipartEntityBuilder.create();
                    mBuilder.addBinaryBody("file", request.getInputStream());
                    builder.setEntity(mBuilder.build());

                    for (Map.Entry<String, List<String>> entry : request.getPostParams().entrySet()) {
                        for (String value : entry.getValue()) {
                            builder.addParameter(entry.getKey(), value);
                        }
                    }
                    break;
                case POST:
                case PUT:
                    builder.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
                    builder.setEntity(new StringEntity(request.getBody()));
                    break;
                default:
                    break;
            }

            if (request.requiresAuthentication()) {
                builder.addHeader(API_KEY_HEADER, request.getApiKey());
            }

            response = client.execute(builder.build());
            HttpEntity entity = response.getEntity();
            return new Response(
                    entity == null ? null : new BufferedHttpEntity(entity).getContent(),
                    response.getStatusLine().getStatusCode()
            );
        } catch (IOException e) {
            throw new QTApiException(e.getMessage(), e);
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
    }
}
