package com.quantxt.sdk.result;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Insights implements Serializable {

    private static final long serialVersionUID = -6238012691404059856L;

    private Integer numberDocumentsIn;
    private Integer numberOfResults;
    private Integer took;
    private Long startTime;

    @JsonCreator
    public Insights(@JsonProperty("number_documents_in") final Integer numberDocumentsIn,
                    @JsonProperty("number_of_results") final Integer numberOfResults,
                    @JsonProperty("took") final Integer took,
                    @JsonProperty("start_time") final Long startTime) {
        this.numberDocumentsIn = numberDocumentsIn;
        this.numberOfResults = numberOfResults;
        this.took = took;
        this.startTime = startTime;
    }

    /**
     * Returns numberDocumentsIn.
     *
     * @return numberDocumentsIn.
     */
    public Integer getNumberDocumentsIn() {
        return numberDocumentsIn;
    }

    /**
     * Returns numberOfResults.
     *
     * @return numberOfResults.
     */
    public Integer getNumberOfResults() {
        return numberOfResults;
    }

    /**
     * Returns took.
     *
     * @return took.
     */
    public Integer getTook() {
        return took;
    }

    /**
     * Returns startTime.
     *
     * @return startTime.
     */
    public Long getStartTime() {
        return startTime;
    }
}
