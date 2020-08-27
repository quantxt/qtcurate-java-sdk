package com.quantxt.sdk.result;

import com.quantxt.sdk.model.Extractor;

public class Field {

    private String str;
    private String category;
    private String dictName;
    private String dictId;
    private Extractor.DataType type;
    private Object[] fieldValues;

    public Field(){

    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public Object[] getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(Object[] fieldValues) {
        this.fieldValues = fieldValues;
    }

    public Extractor.DataType getType() {
        return type;
    }

    public void setType(Extractor.DataType type) {
        this.type = type;
    }
}
