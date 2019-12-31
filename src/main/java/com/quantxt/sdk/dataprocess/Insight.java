package com.quantxt.sdk.dataprocess;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Insight implements Serializable {

    private static final long serialVersionUID = -6238012691404059856L;

    private final Integer numberDocumentsIn;
    private final Integer numberDocumentsOut;
    private final Integer numberOfResults;
    private final Integer numberBytesIn;
    private final Integer took;
    private final Long startTime;
    private final Long qtcurateStart;
    private final Long qtcurateProcessStart;
    private final Long qtcurateEnd;

    @JsonCreator
    public Insight(@JsonProperty("number_documents_in") final Integer numberDocumentsIn,
                   @JsonProperty("number_documents_out") final Integer numberDocumentsOut,
                   @JsonProperty("number_of_results") final Integer numberOfResults,
                   @JsonProperty("number_bytes_in") final Integer numberBytesIn,
                   @JsonProperty("took") final Integer took,
                   @JsonProperty("start_time") final Long startTime,
                   @JsonProperty("qtcurate_start") final Long qtcurateStart,
                   @JsonProperty("qtcurate_process_start") final Long qtcurateProcessStart,
                   @JsonProperty("qtcurate_end") final Long qtcurateEnd) {
        this.numberDocumentsIn = numberDocumentsIn;
        this.numberDocumentsOut = numberDocumentsOut;
        this.numberOfResults = numberOfResults;
        this.numberBytesIn = numberBytesIn;
        this.took = took;
        this.startTime = startTime;
        this.qtcurateStart = qtcurateStart;
        this.qtcurateProcessStart = qtcurateProcessStart;
        this.qtcurateEnd = qtcurateEnd;
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
     * Returns numberDocumentsOut.
     *
     * @return numberDocumentsOut.
     */
    public Integer getNumberDocumentsOut() {
        return numberDocumentsOut;
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
     * Returns numberBytesIn.
     *
     * @return numberBytesIn.
     */
    public Integer getNumberBytesIn() {
        return numberBytesIn;
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

    /**
     * Returns qtcurateStart.
     *
     * @return qtcurateStart.
     */
    public Long getQtcurateStart() {
        return qtcurateStart;
    }

    /**
     * Returns qtcurateProcessStart.
     *
     * @return qtcurateProcessStart.
     */
    public Long getQtcurateProcessStart() {
        return qtcurateProcessStart;
    }

    /**
     * Returns qtcurateEnd.
     *
     * @return qtcurateEnd.
     */
    public Long getQtcurateEnd() {
        return qtcurateEnd;
    }

    @Override
    public String toString() {
        return "Insight{" +
                "numberDocumentsIn=" + numberDocumentsIn +
                ", numberDocumentsOut=" + numberDocumentsOut +
                ", numberOfResults=" + numberOfResults +
                ", numberBytesIn=" + numberBytesIn +
                ", took=" + took +
                ", startTime=" + startTime +
                ", qtcurateStart=" + qtcurateStart +
                ", qtcurateProcessStart=" + qtcurateProcessStart +
                ", qtcurateEnd=" + qtcurateEnd +
                '}';
    }
}
