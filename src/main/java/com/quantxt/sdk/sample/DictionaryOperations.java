package com.quantxt.sdk.sample;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.dictionary.Dictionary;
import com.quantxt.sdk.dictionary.DictionaryEntry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DictionaryOperations {
    private static final String API_KEY = "__APIKEY__";

    public static void main(String[] args) throws FileNotFoundException {
        QT.init(API_KEY);

        //1- Create dictionaries
        Dictionary simpleDictionary = createSimpleDictionary();
        String simpleDictionary_id = simpleDictionary.getId();

        //2- Fetch simpleDictionary
        Dictionary simpleDictionary_fetched = Dictionary.fetcher(simpleDictionary_id).fetch();

        //3- Update simpleDictionary
        Dictionary simpleDictionary_updated = Dictionary.updater(simpleDictionary_id)
                .name("Companies changed")
                .addEntry(new DictionaryEntry("Tesla"))
                .update();

        //4- Delete simpleDictionary
        Dictionary.deleter(simpleDictionary_id).delete();

        //5- Create a dictionary with categhories for phrases
        Dictionary advancedDictionary = createAdvancedDictionary();
    }

    private static Dictionary createSimpleDictionary(){

        List<DictionaryEntry> vocabEntries = new ArrayList<>();
        vocabEntries.add(new DictionaryEntry("Apple Inc."));
        vocabEntries.add(new DictionaryEntry("Apple corp"));
        vocabEntries.add(new DictionaryEntry("Alphabet Inc."));

        Dictionary dictionary = Dictionary.creator()
                .name("Companies")
                .entries(vocabEntries)
                .create();
        return dictionary;
    }

    private static Dictionary createAdvancedDictionary(){

        List<DictionaryEntry> vocabEntries = new ArrayList<>();
        vocabEntries.add(new DictionaryEntry("AAPL", "Apple Inc."));
        vocabEntries.add(new DictionaryEntry("AAPL", "Apple corp"));
        vocabEntries.add(new DictionaryEntry("GOOG","Alphabet Inc."));

        Dictionary dictionary = Dictionary.creator()
                .name("Companies")
                .entries(vocabEntries)
                .create();
        return dictionary;
    }

    private static Dictionary importDictionaryFromTsv() throws FileNotFoundException {
        /*
            Import vocab from a tsv file
            AAPL    Apple Inc
            AAPL    Apple corp
            GOOG    Alphabet Inc.
         */

        InputStream vocabInputStream = new FileInputStream(new File("__path_to_tsv__"));

        Dictionary dictionary = Dictionary.creator()
                .name("Companies")
                .source(vocabInputStream)
                .create();

        return dictionary;
    }
}
