package com.quantxt.sdk.resource;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.client.QTRestClient;

/**
 * Executor for updating a resource.
 *
 * @param <T> type of the resource
 */
public abstract class Updater<T> extends Resource {

    /**
     * Execute a request using default client.
     *
     * @return Requested object
     */
    public T update() {
        return update(QT.getRestClient());
    }

    /**
     * Execute a request using specified client.
     *
     * @param client client used to make request
     * @return Requested object
     */
    public abstract T update(final QTRestClient client);
}
