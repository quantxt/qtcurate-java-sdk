package com.quantxt.sdk.sample;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.dataprocess.DataProcess;
import com.quantxt.sdk.vocabulary.Vocabulary;
import com.quantxt.sdk.vocabulary.VocabularyEntry;
import com.quantxt.sdk.document.Document;
import com.quantxt.sdk.model.Extractor;
import com.quantxt.sdk.result.Field;
import com.quantxt.sdk.result.Result;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.quantxt.sdk.model.Extractor.DataType.DOUBLE;


public class DataProcessOperations {
    private static final String API_KEY = "__APIKEY__";

    private static Vocabulary getVocabulary() {
        List<VocabularyEntry> entries = new ArrayList<>();
        entries.add(new VocabularyEntry("Industrials"));
        entries.add(new VocabularyEntry("Quasi-Governments"));
        entries.add(new VocabularyEntry("Governments"));

        // 2- Initialize the vocabulary
        Vocabulary vocabulary = Vocabulary.creator()
                .name("Allocations (%)")
                .entries(entries)
                .create();
        return vocabulary;
    }

    public static void main(String[] args) {
        QT.init(API_KEY);

        // procee a one page file and extract numbers reported in the table
        // https://github.com/quantxt/qtcurate-java-sdk/tree/master/src/main/resources/sample.pdf
        File file = new File(DataProcessOperations.class
                .getClassLoader().getResource("sample.pdf").getFile());

        Document document = Document.creator()
                .source(file)
                .create();

        List<Document> documents = new ArrayList<>();
        documents.add(document);

        Vocabulary vocabulary = getVocabulary();

        // 2- Configure and kick off the Parser job.
        // search for entries in the vocabulary and find a value near matches that pass
        // the validator regular expression: ^ +(\d[\d\.\,]+\d)
        // Validator must have one capturing group
        DataProcess dataProcess = DataProcess.creator("My parser job " + Instant.now())
                .addExtractor(new Extractor()
                .setVocabulary(vocabulary)
                .setValidator(Pattern.compile("^ +(\\d[\\d\\.\\,]+\\d)"))
                .setDataType(DOUBLE))
                .withDocuments(documents)
                .create();

        System.out.println(String.format("Data parser %s started", dataProcess.getId()));

        // 3- Track the progress of the parser job
        DataProcess.fetcher(dataProcess.getId()).blockUntilFinish();

        /* 4- Print extraction results

        Industrials -> 41.2
        Quasi-Governments -> 2.7
        Governments -> 1.6

         */
        List<Result> results = Result.reader(dataProcess.getId()).read();
        for (Result r : results){
            for (Field f : r.getFields()){
                System.out.println(f.getStr() + " -> " + f.getFieldValues()[0]);
            }
        }

        // 5- Clean up
        boolean dataDeleted = DataProcess.deleter(dataProcess.getId()).delete();
        boolean dictionaryDeleted = Vocabulary.deleter(vocabulary.getId()).delete();

    }
}
