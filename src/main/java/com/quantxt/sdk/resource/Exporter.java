package com.quantxt.sdk.resource;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.client.QTRestClient;

/**
 * Executor for exporting of a resource.
 *
 * @param <T> type of the resource
 */
public abstract class Exporter<T extends Resource> {

    /**
     * Execute a request using default client.
     *
     * @return Requested object
     */
    public byte[] export() {
        return export(QT.getRestClient());
    }

    /**
     * Execute a request using specified client.
     *
     * @param client client used to make request
     * @return Requested object data
     */
    public abstract byte[] export(final QTRestClient client);
}
