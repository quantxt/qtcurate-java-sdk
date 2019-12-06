package com.quantxt.sdk.exception;

public class QTApiException extends QTException {

    private static final long serialVersionUID = -5466520166876638215L;

    private final String code;
    private final Integer status;

    /**
     * Create a new QT API Exception.
     *
     * @param message exception message
     */
    public QTApiException(final String message) {
        this(message, null, null, null);
    }

    /**
     * Create a new QT API Exception.
     *
     * @param message exception message
     * @param cause   cause of the exception
     */
    public QTApiException(final String message, final Throwable cause) {
        this(message, null, null, cause);
    }

    /**
     * Create a new QT API Exception.
     *
     * @param message exception message
     * @param code    exception code
     * @param status  status code
     * @param cause   cause of the exception* @param cause
     */
    public QTApiException(final String code,
                          final String message,
                          final Integer status,
                          final Throwable cause) {
        super(message, cause);
        this.code = code;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public Integer getStatusCode() {
        return status;
    }
}
