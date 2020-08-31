package com.quantxt.sdk.result;

import com.quantxt.sdk.resource.Resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

public class Result extends Resource {

    public static ResultReader reader(String id) { return new ResultReader(id); }
    public static ResultRawExporter exporter(String id) {
        return new ResultRawExporter(id);
    }
    public static ResultXlsxExporter xlsxExporter(String id) {
        return new ResultXlsxExporter(id);
    }

    private String id;
    private String documentName;
    private int unitNumber;
    private LocalDateTime creationTime;
    Field[] fields;

    public Result(){
    }

    public static byte[] toByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int reads = is.read();
        while(reads != -1){
            baos.write(reads);
            reads = is.read();
        }
        return baos.toByteArray();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(int unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Field[] getFields() {
        return fields;
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
}
