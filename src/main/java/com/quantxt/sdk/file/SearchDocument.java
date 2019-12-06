package com.quantxt.sdk.file;

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
public class SearchDocument extends Resource {

    private static final long serialVersionUID = 554603237874587L;

    /**
     * Create a SearchDocumentCreator to execute create.
     *
     * @return SearchDocumentCreator capable of executing the create
     */
    public static SearchDocumentCreator creator() {
        return new SearchDocumentCreator();
    }

    /**
     * Converts a JSON InputStream into a SearchDocumentCreator object using the provided ObjectMapper.
     *
     * @param json         Raw JSON InputStream
     * @param objectMapper Jackson ObjectMapper
     * @return Dictionary object represented by the provided JSON
     */
    public static SearchDocument fromJson(final InputStream json, final ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(json, SearchDocument.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new QTApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new QTApiConnectionException(e.getMessage(), e);
        }
    }

    private String uuid;
    private String fileName;
    private String link;
    private String date;
    private String contentType;
    private String source;

    @JsonCreator
    private SearchDocument(@JsonProperty("uuid") final String uuid,
                           @JsonProperty("fileName") final String fileName,
                           @JsonProperty("link") final String link,
                           @JsonProperty("date") final String date,
                           @JsonProperty("contentType") final String contentType,
                           @JsonProperty("source") final String source) {
        this.uuid = uuid;
        this.fileName = fileName;
        this.link = link;
        this.date = date;
        this.contentType = contentType;
        this.source = source;
    }

    /**
     * Returns search document UUID.
     *
     * @return Search document UUID.
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Returns search document file name.
     *
     * @return Search document file name.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Returns search document link.
     *
     * @return Search document link.
     */
    public String getLink() {
        return link;
    }

    /**
     * Returns search document upload date.
     *
     * @return Search document upload date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns search document content type.
     *
     * @return Search document content type.
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Returns search document source.
     *
     * @return Search document source.
     */
    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "SearchFile{" +
                "uuid='" + uuid + '\'' +
                ", fileName='" + fileName + '\'' +
                ", link='" + link + '\'' +
                ", date='" + date + '\'' +
                ", contentType='" + contentType + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
