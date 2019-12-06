package com.quantxt.sdk.client;

import com.quantxt.sdk.exception.AuthenticationException;

/**
 * Singleton class to initialize QT environment.
 */
public class QT {
    private static String apiKey;
    private static QTRestClient restClient;

    private QT() {}

    /**
     * Initialize the QT environment.
     *
     * @param apiKey API key to use
     */
    public static void init(final String apiKey) {
        QT.setApiKey(apiKey);
    }

    /**
     * Set the API key.
     *
     * @param apiKey API key to use
     * @throws AuthenticationException if accountSid is null
     */
    public static void setApiKey(final String apiKey) {
        if (apiKey == null) {
            throw new AuthenticationException("API key can not be null");
        }

        if (!apiKey.equals(QT.apiKey)) {
            QT.invalidate();
        }

        QT.apiKey = apiKey;
    }

    /**
     * Invalidates the volatile state held in the QTClient singleton.
     */
    private static void invalidate() {
        QT.restClient = null;
    }

    /**
     * Returns (and initializes if not initialized) the QT Rest Client.
     *
     * @return the QT Rest Client
     * @throws AuthenticationException if initialization required and API key is null
     */
    public static QTRestClient getRestClient() {
        if (QT.restClient == null) {
            if (QT.apiKey == null) {
                throw new AuthenticationException(
                        "QTRestClient was used before API key were set, please call QTClient.init()"
                );
            }

            QTRestClient.Builder builder = new QTRestClient.Builder(QT.apiKey);
            QT.restClient = builder.build();
        }

        return QT.restClient;
    }
}
