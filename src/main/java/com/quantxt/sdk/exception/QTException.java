package com.quantxt.sdk.exception;

public abstract class QTException extends RuntimeException {

    private static final long serialVersionUID = 2498735680980388130L;

    public QTException(final String message) {
        this(message, null);
    }

    public QTException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
