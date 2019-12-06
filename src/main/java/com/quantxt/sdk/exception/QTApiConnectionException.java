package com.quantxt.sdk.exception;

public class QTApiConnectionException extends QTException {

    private static final long serialVersionUID = -5466520166876638215L;

    public QTApiConnectionException(final String message) {
        super(message);
    }

    public QTApiConnectionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
