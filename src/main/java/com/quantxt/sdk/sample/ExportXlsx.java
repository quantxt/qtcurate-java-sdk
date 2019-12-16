package com.quantxt.sdk.sample;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.search.Search;
import com.quantxt.sdk.search.SearchExporter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ExportXlsx {

    private static final String API_KEY = "123456";


    public static void main(String[] args) throws IOException {
        QT.init(API_KEY);

        String index = "my_data_container";

        byte[] exportData = Search.exporter(index)
                .format(SearchExporter.Format.XLSX)
                .export();
        File xlsxExport = new File("export.xlsx");

        OutputStream outStream = new FileOutputStream(xlsxExport);
        outStream.write(exportData);
        System.out.println(String.format("Exported %s bytes of data in export.xlsx", exportData.length));
    }
}
