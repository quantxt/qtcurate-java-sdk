package com.quantxt.sdk.sample;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.vocabulary.Vocabulary;
import com.quantxt.sdk.vocabulary.VocabularyEntry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class VocabularyOperations {
    private static final String API_KEY = "__APIKEY__";

    public static void main(String[] args) throws FileNotFoundException {
        QT.init(API_KEY);

        //1- Create dictionaries
        Vocabulary simpleVocabulary = createSimpleVocabulary();
        String simpleVocabulary_id = simpleVocabulary.getId();

        //2- Fetch simpleVocabulary
        Vocabulary simpleVocabulary_fetched = Vocabulary.fetcher(simpleVocabulary_id).fetch();

        //3- Update simpleVocabulary
        Vocabulary simpleVocabulary_updated = Vocabulary.updater(simpleVocabulary_id)
                .name("Companies changed")
                .addEntry(new VocabularyEntry("Tesla"))
                .update();

        //4- Delete simpleVocabulary
        Vocabulary.deleter(simpleVocabulary_id).delete();

        //5- Create a dictionary with categhories for phrases
        Vocabulary advancedVocabulary = createAdvancedVocabulary();
    }

    private static Vocabulary createSimpleVocabulary(){

        List<VocabularyEntry> vocabEntries = new ArrayList<>();
        vocabEntries.add(new VocabularyEntry("Apple Inc."));
        vocabEntries.add(new VocabularyEntry("Apple corp"));
        vocabEntries.add(new VocabularyEntry("Alphabet Inc."));

        Vocabulary dictionary = Vocabulary.creator()
                .name("Companies")
                .entries(vocabEntries)
                .create();
        return dictionary;
    }

    private static Vocabulary createAdvancedVocabulary(){

        List<VocabularyEntry> vocabEntries = new ArrayList<>();
        vocabEntries.add(new VocabularyEntry("AAPL", "Apple Inc."));
        vocabEntries.add(new VocabularyEntry("AAPL", "Apple corp"));
        vocabEntries.add(new VocabularyEntry("GOOG","Alphabet Inc."));

        Vocabulary vocabulary = Vocabulary.creator()
                .name("Companies")
                .entries(vocabEntries)
                .create();
        return vocabulary;
    }

    private static Vocabulary importVocabularyFromTsv() throws FileNotFoundException {
        /*
            Import vocab from a tsv file
            AAPL    Apple Inc
            AAPL    Apple corp
            GOOG    Alphabet Inc.
         */

        InputStream vocabInputStream = new FileInputStream(new File("__path_to_tsv__"));

        Vocabulary dictionary = Vocabulary.creator()
                .name("Companies")
                .source(vocabInputStream)
                .create();

        return dictionary;
    }
}
