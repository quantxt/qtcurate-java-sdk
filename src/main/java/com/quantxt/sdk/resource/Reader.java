package com.quantxt.sdk.resource;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.client.QTRestClient;

import java.util.List;

/**
 * Executor for fetches of a resource.
 *
 * @param <T> type of the resource
 */
public abstract class Reader<T extends Resource> {

    /**
     * Execute a request using default client.
     *
     * @return Requested object
     */
    public List<T>  read() {
        return read(QT.getRestClient());
    }

    /**
     * Execute a request using specified client.
     *
     * @param client client used to make request
     * @return Requested object
     */
    public abstract List<T> read(final QTRestClient client);
}
