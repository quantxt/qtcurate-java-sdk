package com.quantxt.sdk.document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantxt.sdk.exception.QTApiConnectionException;
import com.quantxt.sdk.exception.QTApiException;
import com.quantxt.sdk.resource.Resource;

import java.io.IOException;
import java.io.InputStream;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Document extends Resource {

    private static final long serialVersionUID = 554603237874587L;

    /**
     * Create a DocumentCreator to execute create.
     *
     * @return DocumentCreator capable of executing the create
     */
    public static DocumentCreator creator() {
        return new DocumentCreator();
    }

    /**
     * Converts a JSON InputStream into a Document object using the provided ObjectMapper.
     *
     * @param json         Raw JSON InputStream
     * @param objectMapper Jackson ObjectMapper
     * @return Dictionary object represented by the provided JSON
     */
    public static Document fromJson(final InputStream json, final ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(json, Document.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new QTApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new QTApiConnectionException(e.getMessage(), e);
        }
    }

    private String id;
    private String name;
    private String date;
    private String contentType;

    public Document(){

    }
    @JsonCreator
    private Document(@JsonProperty("uuid") final String uuid,
                     @JsonProperty("fileName") final String fileName,
                     @JsonProperty("link") final String link,
                     @JsonProperty("date") final String date,
                     @JsonProperty("contentType") final String contentType,
                     @JsonProperty("source") final String source) {
        this.id = uuid;
        this.name = fileName;
        this.date = date;
        this.contentType = contentType;
    }

    /**
     * Returns document ID.
     *
     * @return document ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns document file name.
     *
     * @return document file name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns document upload date.
     *
     * @return document upload date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns document content type.
     *
     * @return document content type.
     */
    public String getContentType() {
        return contentType;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
