package com.quantxt.sdk.resource;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.client.QTRestClient;

/**
 * Executor for deletes of a resource.
 *
 * @param <T> type of the resource
 */
public abstract class Deleter<T extends Resource> {

    /**
     * Execute a request using default client.
     *
     * @return true if the object was deleted
     */
    public boolean delete() {
        return delete(QT.getRestClient());
    }

    /**
     * Execute a request using specified client.
     *
     * @param client client used to make request
     * @return true if the object was deleted
     */
    public abstract boolean delete(final QTRestClient client);
}
