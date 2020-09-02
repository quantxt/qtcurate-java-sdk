package com.quantxt.sdk.sample;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.dataprocess.DataProcess;
import com.quantxt.sdk.dictionary.Dictionary;
import com.quantxt.sdk.dictionary.DictionaryEntry;
import com.quantxt.sdk.document.Document;
import com.quantxt.sdk.model.Extractor;
import com.quantxt.sdk.result.Field;
import com.quantxt.sdk.result.Result;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.quantxt.sdk.model.Extractor.DataType.DOUBLE;

public class DataProcessOperations {
    private static final String API_KEY = "__APIKEY__";

    private static Dictionary getDictionary() {
        List<DictionaryEntry> entries = new ArrayList<>();
        entries.add(new DictionaryEntry("Industrials"));
        entries.add(new DictionaryEntry("Quasi-Governments"));
        entries.add(new DictionaryEntry("Governments"));

        // 2- Initialize the dictionary
        Dictionary date_dictionary = Dictionary.creator()
                .name("Allocations (%)")
                .entries(entries)
                .create();
        return date_dictionary;
    }

    public static void main(String[] args) throws IOException {
        QT.init(API_KEY);
        
        // https://github.com/quantxt/qtcurate-java-sdk/blob/master/src/main/resources/search-file.pdf
        File file = new File(DataProcessOperations.class
                .getClassLoader().getResource("search-file.pdf").getFile());

        Document document = Document.creator()
                .source(file)
                .create();

        List<Document> documents = new ArrayList<>();
        documents.add(document);

        Dictionary dictionary = getDictionary();

        // 2- Configure and kick off the Parser job. This will prepare the document for keyword and phrase search
        DataProcess dataProcess = DataProcess.creator("My parser job " + Instant.now())
                .addExtractor(new Extractor()
                        .setDictionary(dictionary)
                        .setValidator(Pattern.compile("^ +(\\d[\\d\\.\\,]+\\d)"))
                        .setDataType(DOUBLE))
                .withDocuments(documents)
                .create();

        System.out.println(String.format("Data parser %s started", dataProcess.getId()));

        // 3- Track the progress of the parser job
        DataProcess.fetcher(dataProcess.getId()).blockUntilFinish();

        // 4- Export results as a Excel spreadsheet
        byte[] xlsxExportData = Result.xlsxExporter(dataProcess.getId())
                .export();

        File xlsxExport = new File(dataProcess.getId() + ".xlsx");
        OutputStream outStream = new FileOutputStream(xlsxExport);
        outStream.write(xlsxExportData);
        System.out.println(String.format("Exported %s bytes of XLSX data", xlsxExportData.length));

        // 5- Export results in json
        List<Result> results = Result.reader(dataProcess.getId())
                .read();

        for (Result r : results){
            for (Field f : r.getFields()){
                System.out.println(f.getDictId() + " " + f.getStr());
            }
        }
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        System.out.println(objectMapper.writeValueAsString(results));

        // clean up
        boolean dataDeleted = DataProcess.deleter(dataProcess.getId()).delete();
        boolean dictionaryDeleted = Dictionary.deleter(dictionary.getId()).delete();

    }
}
