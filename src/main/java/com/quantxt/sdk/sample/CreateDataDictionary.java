package com.quantxt.sdk.sample;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.dictionary.Dictionary;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CreateDataDictionary {
    private static final String API_KEY = "123456";
    private static final String FILE_NAME = "revenue.tsv";

    public static void main(String[] args) {
        QT.init(API_KEY);

        // 1- Create list of words and their meanings
        Map<String, String> entries = new HashMap<>();
        entries.put("Apple Inc.", "AAPL");
        entries.put("Amazon.com", "AMZN");
        entries.put("Alphabet Inc.", "GOOG");

        // 2- Initialize the dictionary

        File dictionaryFile = new File(SearchWithDocumentSample.class
                .getClassLoader().getResource(FILE_NAME).getFile());
        InputStream dictionaryInputStream = SearchWithDocumentSample.class
                .getClassLoader().getResourceAsStream(FILE_NAME);

        Dictionary dictionary1 = Dictionary.creator()
                .name("Stock mapping dictionary")
                .source(dictionaryFile)
                .create();

        Dictionary dictionary2 = Dictionary.creator()
                .name("Stock mapping dictionary 2")
                .source(dictionaryInputStream, FILE_NAME)
                .create();

        System.out.println("Stock mapping dictionary created: " + dictionary1);
        dictionary1 = Dictionary.fetcher(dictionary1.getId()).fetch();
        System.out.println("Stock mapping dictionary 2 created: " + dictionary2);
        dictionary2 = Dictionary.fetcher(dictionary1.getId()).fetch();

        System.out.println("Stock mapping dictionary fetched: " + dictionary1);
        System.out.println("Stock mapping dictionary 2 fetched: " + dictionary2);
    }
}
