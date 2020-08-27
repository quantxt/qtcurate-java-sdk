package com.quantxt.sdk.model;

import java.time.LocalDateTime;

public class ExtIntervalSimple {

    private Extractor.DataType type;
    private String str;
    private Double doubleValue;
    private Long intValue;
    private LocalDateTime datetimeValue;

    public Extractor.DataType getType() {
        return type;
    }

    public void setType(Extractor.DataType type) {
        this.type = type;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Long getIntValue() {
        return intValue;
    }

    public void setIntValue(Long intValue) {
        this.intValue = intValue;
    }

    public LocalDateTime getDatetimeValue() {
        return datetimeValue;
    }

    public void setDatetimeValue(LocalDateTime datetimeValue) {
        this.datetimeValue = datetimeValue;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public ExtIntervalSimple(){

    }

}
