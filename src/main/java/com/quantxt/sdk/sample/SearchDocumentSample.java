package com.quantxt.sdk.sample;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.file.SearchDocument;

import java.io.InputStream;

public class SearchDocumentSample {

    private static final String API_KEY = "123456";
    private static final String FILE_NAME = "search-file.pdf";

    public static void main(String[] args) {
        QT.init(API_KEY);

        InputStream inputStream = SearchDocumentSample.class
                .getClassLoader().getResourceAsStream(FILE_NAME);

        SearchDocument searchDocument = SearchDocument.creator()
                .name(FILE_NAME)
                .inputStream(inputStream)
                .create();

        System.out.println("Uploaded search document: " + searchDocument);
    }
}
