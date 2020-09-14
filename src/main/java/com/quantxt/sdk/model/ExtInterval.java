package com.quantxt.sdk.model;

public class ExtInterval {

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    private int start;
    private int end;
    private int line;
    private String str;
    private String category;
    private String dict_name;
    private String dict_id;
    private ExtIntervalSimple[] extIntervalSimples;

    public ExtInterval(){

    }

    public String getDict_name() {
        return dict_name;
    }

    public String getDict_id() {
        return dict_id;
    }

    public String getCategory() {
        return category;
    }

    public ExtIntervalSimple[] getExtIntervalSimples() {
        return extIntervalSimples;
    }

    public void setDict_name(String dict_name) {
        this.dict_name = dict_name;
    }

    public void setDict_id(String dict_id) {
        this.dict_id = dict_id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setExtIntervalSimples(ExtIntervalSimple[] extIntervalSimples) {
        this.extIntervalSimples = extIntervalSimples;
    }
}