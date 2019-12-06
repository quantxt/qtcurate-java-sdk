package com.quantxt.sdk.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * QT Rest Exceptions.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QTRestException {

    private final String code;
    private final String message;

    /**
     * Initialize a QT Rest Exception.
     *
     * @param message message of exception
     * @param code    QT status code
     */
    @JsonCreator
    private QTRestException(@JsonProperty("code") final String code,
                            @JsonProperty("message") final String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Build an exception from a JSON blob.
     *
     * @param json         JSON blob
     * @param objectMapper JSON reader
     * @return Rest Exception as an object
     */
    public static QTRestException fromJson(final InputStream json, final ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(json, QTRestException.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new QTApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new QTApiConnectionException(e.getMessage(), e);
        }
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
