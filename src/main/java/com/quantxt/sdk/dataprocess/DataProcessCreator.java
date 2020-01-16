package com.quantxt.sdk.dataprocess;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.quantxt.sdk.client.HttpMethod;
import com.quantxt.sdk.client.QTRestClient;
import com.quantxt.sdk.client.Request;
import com.quantxt.sdk.client.Response;
import com.quantxt.sdk.exception.QTApiConnectionException;
import com.quantxt.sdk.exception.QTApiException;
import com.quantxt.sdk.exception.QTRestException;
import com.quantxt.sdk.resource.Creator;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DataProcessCreator extends Creator<DataProcess> {

    /**
     * Dictionary types.
     */
    public enum DictionaryType {
        NUMBER("DOUBLE"),
        STRING("STRING"),
        DATETIME("DATETIME"),
        NONE("NONE");

        private final String type;

        DictionaryType(final String type) {
            this.type = type;
        }

        public String toString() {
            return type;
        }
    }

    @JsonProperty("get_phrases")
    private Boolean autoTag;
    private Integer maxTokenPerUtt = 500;
    private Integer minTokenPerUtt = 6;
    private boolean excludeUttWithoutEntities = true;
    private String title;
    @JsonProperty("stitle")
    private String cmd;
    private List<SearchRule> searchDictionaries = new ArrayList<>();
    private List<String> files = new ArrayList<>();
    private List<String> urls = new ArrayList<>();

    public DataProcessCreator(String title) {
        this.title = title;
    }

    /**
     * Indicates to  perform tagging or not.
     *
     * @param autoTag Auto tagging indicator.
     * @return this
     */
    public DataProcessCreator autoTag(boolean autoTag) {
        this.autoTag = autoTag;
        return this;
    }

    /**
     * Max token per UTT.
     *
     * @param maxTokenPerUtt Max token per UTT.
     * @return this
     */
    public DataProcessCreator maxTokenPerUtt(Integer maxTokenPerUtt) {
        this.maxTokenPerUtt = maxTokenPerUtt;
        return this;
    }

    public DataProcessCreator minTokenPerUtt(Integer minTokenPerUtt) {
        this.minTokenPerUtt = minTokenPerUtt;
        return this;
    }

    public DataProcessCreator excludeUttWithoutEntities(boolean excludeUttWithoutEntities) {
        this.excludeUttWithoutEntities = excludeUttWithoutEntities;
        return this;
    }

    public DataProcessCreator cmd(String cmd) {
        this.cmd = cmd;
        return this;
    }

    public DataProcessCreator files(List<String> files) {
        this.files = files;
        return this;
    }

    public DataProcessCreator urls(List<String> urls) {
        this.urls = urls;
        return this;
    }

    /**
     * Adds a search rule.
     *
     * @param searchRule Search rule.
     * @return this
     */
    public DataProcessCreator addRule(SearchRule searchRule) {
        this.searchDictionaries.add(searchRule);
        return this;
    }

    /**
     * Specifies the search rules.
     *
     * @param searchRules Search rules.
     * @return this
     */
    public DataProcessCreator rules(List<SearchRule> searchRules) {
        this.searchDictionaries = searchRules;
        return this;
    }

    private String formatDictionary(String dictionary, DictionaryType type) {
        return dictionary + "||" + type;
    }

    /**
     * Make the request to the API to perform the create.
     *
     * @param client QTClient with which to make the request
     * @return Created DataProcess
     */
    @Override
    public DataProcess create(QTRestClient client) {
        Request request = new Request(HttpMethod.POST, "/search/new");
        addPayload(request, client);

        Response response = client.request(request);

        if (response == null) {
            throw new QTApiConnectionException("DataProcess creation failed: Unable to connect to server");
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

        return DataProcess.fromJson(response.getStream(), client.getObjectMapper());
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
