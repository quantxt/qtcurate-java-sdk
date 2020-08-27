package com.quantxt.sdk.resource;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.client.QTRestClient;

/**
 * Executor for creation of a resource.
 *
 * @param <T> type of the resource
 */
public abstract class Creator<T> extends Resource {

    /**
     * Execute a request using default client.
     *
     * @return Requested object
     */
    public T create() {
        return create(QT.getRestClient());
    }

    /**
     * Execute a request using specified client.
     *
     * @param client client used to make request
     * @return Requested object
     */
    public abstract T create(final QTRestClient client);
}
