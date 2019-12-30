package com.quantxt.sdk.sample;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.search.Search;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ExportJson {

    private static final String API_KEY = "123456";

    public static void main(String[] args) throws IOException {
        QT.init(API_KEY);

        String index = "my_data_container";

        byte[] exportData = Search.jsonExporter(index)
                .export();
        File jsonExport = new File("export.json");

        OutputStream outStream = new FileOutputStream(jsonExport);
        outStream.write(exportData);
        System.out.println(String.format("Exported %s bytes of data in export.json", exportData.length));
    }
}
