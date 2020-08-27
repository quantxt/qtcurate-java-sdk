package com.quantxt.sdk.result;

import java.time.LocalDateTime;

public class FieldValue {

    private String str;
    private Double doubleValue;
    private Long intValue;
    private LocalDateTime datetimeValue;

    public FieldValue(){
    }

    public String getStr(){
        return str;
    }
    public LocalDateTime getDatetimeValue(){
        return datetimeValue;
    }
    public Long getIntValue(){return intValue;}
    public Double getDoubleValue(){return doubleValue;}

    public void setStr(String str) {
        this.str = str;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public void setIntValue(Long intValue) {
        this.intValue = intValue;
    }

    public void setDatetimeValue(LocalDateTime datetimeValue) {
        this.datetimeValue = datetimeValue;
    }
}
