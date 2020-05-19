package com.quantxt.sdk.sample;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.dictionary.Dictionary;
import com.quantxt.sdk.dictionary.DictionaryEntry;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateDataDictionary {
    private static final String API_KEY = "123456";
    private static final String FILE_NAME = "revenue.tsv";

    public static void main(String[] args) {
        QT.init(API_KEY);

//        Create dictionary with file upload:
        File file = new File(UpdateDataDictionary.class
                .getClassLoader().getResource(FILE_NAME).getFile());

        Dictionary dictionaryTest = Dictionary.creator()
                .name(FILE_NAME)
                .source(file)
                .create();

        // 1- Create list of words and their meanings
        List<DictionaryEntry> entries = new ArrayList<>();
        //Search for `Apple Inc.` and map categorize/map it as/to AAPL
        entries.add(new DictionaryEntry("AAPL", "Apple Inc."));
        entries.add(new DictionaryEntry("AMZN", "Amazon.com"));

        // 2- Initialize the dictionary
        Dictionary dictionary1 = Dictionary.creator()
                .name("Stock mapping dictionary")
                .entries(entries)
                .create();

        System.out.println("Stock mapping dictionary created: " + dictionary1);
        Dictionary dictionary = Dictionary.fetcher(dictionary1.getId()).fetch();
        System.out.println("Stock mapping dictionary fetched: " + dictionary);

        Dictionary.updater(dictionary.getId())
                .name("Stock Mapping Dictionary")
                .entries(dictionary.getEntries())
                .addEntry("GOOG", "Alphabet Inc.")
                .addEntry(new DictionaryEntry("AWS", "Amazon.com"));

        System.out.println("Stock mapping dictionary updated: " + dictionary);
    }
}
