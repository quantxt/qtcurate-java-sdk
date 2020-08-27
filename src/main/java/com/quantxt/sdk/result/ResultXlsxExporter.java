package com.quantxt.sdk.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.quantxt.sdk.client.HttpMethod;
import com.quantxt.sdk.client.QTRestClient;
import com.quantxt.sdk.client.Request;
import com.quantxt.sdk.client.Response;
import com.quantxt.sdk.exception.QTApiConnectionException;
import com.quantxt.sdk.exception.QTApiException;
import com.quantxt.sdk.exception.QTRestException;
import com.quantxt.sdk.resource.Exporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.quantxt.sdk.result.Result.toByteArray;

public class ResultXlsxExporter extends Exporter<byte[]> {

    /**
     * Available sort properties.
     */
    public enum Sort {
        DATE("date");

        private final String sort;

        Sort(final String sort) {
            this.sort = sort;
        }

        public String toString() {
            return sort;
        }
    }

    /**
     * Available sort directions.
     */
    public enum SortOrder {
        ASC("0"),
        DESC("1");

        private final String order;

        SortOrder(final String sortOrder) {
            this.order = sortOrder;
        }

        public String toString() {
            return order;
        }
    }

    @JsonIgnore
    private String id;
    private String query;
    private Sort sort = Sort.DATE;
    private SortOrder sortOrder = SortOrder.ASC;
    @JsonProperty("startDate")
    private Date from;
    @JsonProperty("endDate")
    private Date to;
    private List<String> filters = new ArrayList<>();
    private Integer size = 5000;
    private List<String> columns = new ArrayList<>();

    public ResultXlsxExporter(String id) {
        this.id = id;
    }

    /**
     * The query for filtering the export.
     *
     * @param query Export filtering query.
     * @return this
     */
    public ResultXlsxExporter query(String query) {
        this.query = query;
        return this;
    }

    /**
     * The sort property of the export.
     *
     * @param sort Export sort by property.
     * @return this
     */
    public ResultXlsxExporter sortBy(Sort sort) {
        this.sort = sort;
        return this;
    }

    /**
     * The sort order of the export.
     *
     * @param sortOrder Export sort order.
     * @return this
     */
    public ResultXlsxExporter sortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    /**
     * The date from which to include the data in the export.
     *
     * @param from Export data from date.
     * @return this
     */
    public ResultXlsxExporter from(Date from) {
        this.from = from;
        return this;
    }

    /**
     * The date to which to include the data in the export.
     *
     * @param to Export data to date.
     * @return this
     */
    public ResultXlsxExporter to(Date to) {
        this.to = to;
        return this;
    }

    /**
     * The results limit in the data export.
     *
     * @param size Export results number limit.
     * @return this
     */
    public ResultXlsxExporter size(Integer size) {
        this.size = size;
        if (size > 5000){
            this.size = 5000;
        }
        return this;
    }

    /**
     * The specific columns to be included in the export.
     *
     * @param columns Columns to be included in the export.
     * @return this
     */
    public ResultXlsxExporter columns(List<String> columns) {
        this.columns = columns;
        return this;
    }

    /**
     * The filters to be used for narrowing the results in the export.
     *
     * @param filters Filters for export.
     * @return this
     */
    public ResultXlsxExporter filters(List<String> filters) {
        this.filters = filters;
        return this;
    }


    @Override
    public byte[] export(QTRestClient client) {
        Request request = new Request(HttpMethod.POST, String.format("/reports/%s/xlsx", this.id));
        addPayload(request, client);

        Response response = client.request(request);

        if (response == null) {
            throw new QTApiConnectionException("Xlsx search export failed: Unable to connect to server");
        } else if (!QTRestClient.SUCCESS.test(response.getStatusCode())) {
            QTRestException restException = QTRestException.fromJson(response.getStream(), client.getObjectMapper());
            if (restException == null) {
                throw new QTApiException("Server Error, no content");
            }

            throw new QTApiException(
                    restException.getCode(),
                    restException.getMessage(),
                    response.getStatusCode(),
                    null
            );
        }

        try {
            return toByteArray(response.getStream());
        } catch (IOException e) {
            throw new QTApiException("Xlsx search export failed. Unable to read the data.", e);
        }
    }

    /**
     * Add the requested JSON body to the Request.
     *
     * @param request Request to add post params to
     */
    private void addPayload(final Request request, final QTRestClient client) {
        try {
            request.setBody(client.getObjectMapper().writeValueAsString(this));
        } catch (JsonProcessingException e) {
            throw new QTApiException(e.getMessage(), e);
        }
    }
}