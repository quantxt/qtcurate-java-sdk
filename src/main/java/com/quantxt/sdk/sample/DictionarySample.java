package com.quantxt.sdk.sample;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.dictionary.Dictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictionarySample {

    private static final String API_KEY = "123456";

    public static void main(String[] args) {
        QT.init(API_KEY);

        try {
            Dictionary.creator()
                    .name("SDK Dictionary test exception")
                    .create();
        } catch (Exception e){
            System.err.println(e);
        }

        Map<String, String> entries = new HashMap<>();
        entries.put("key1", "val1");
        entries.put("key2", "val2");
        entries.put("key3", "val3");

        Dictionary dictionary1 = Dictionary.creator()
                .name("SDK Dictionary test 2")
                .entries(entries)
                .create();
        System.out.println("Created dictionary: " + dictionary1);
        Dictionary dictionary = Dictionary.fetcher(dictionary1.getId()).fetch();
        System.out.println("Fetched dictionary: " + dictionary);

        Dictionary dictionary2 = Dictionary.creator()
                .name("SDK Dictionary test 2")
                .addEntry("key1", "val1")
                .addEntry("key2", "val2")
                .addEntry("key2", "val3")
                .create();
        System.out.println("Created dictionary: " + dictionary2);
        dictionary = Dictionary.fetcher(dictionary2.getId()).fetch();
        System.out.println("Fetched dictionary: " + dictionary);

        List<Dictionary> dictionaries = Dictionary.reader().read();
        System.out.println("Fetched user dictionaries: " + dictionaries);

        List<Dictionary> globalDictionaries = Dictionary.reader()
                .global().read();
        System.out.println("Fetched global dictionaries: " + globalDictionaries);

        boolean deleted = Dictionary.deleter(dictionary.getId()).delete();
        System.out.println(String.format("Deleted dictionary %s: %s", dictionary.getId(), deleted));
    }
}
