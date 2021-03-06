package com.quantxt.sdk.result;

import com.quantxt.sdk.model.Extractor;

public class Field {

    private String str;
    private String category;
    private String vocabName;
    private String vocabId;
    private Extractor.DataType type;
    private Position position;
    private FieldValue[] fieldValues;

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

    public String getVocabName() {
        return vocabName;
    }

    public void setVocabName(String vocabName) {
        this.vocabName = vocabName;
    }

    public String getVocabId() {
        return vocabId;
    }

    public void setVocabId(String vocabId) {
        this.vocabId = vocabId;
    }

    public FieldValue[] getFieldValues() {
        return fieldValues;
    }

    public Extractor.DataType getType() {
        return type;
    }

    public void setType(Extractor.DataType type) {
        this.type = type;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setFieldValues(FieldValue[] fieldValues) {
        this.fieldValues = fieldValues;
    }
}
