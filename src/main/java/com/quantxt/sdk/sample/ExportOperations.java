package com.quantxt.sdk.sample;

import com.quantxt.sdk.client.QT;
import com.quantxt.sdk.result.Result;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ExportOperations {

    private static final String API_KEY = "__APIKEY__";

    public static void main(String[] args) throws IOException {
        QT.init(API_KEY);
        String id = "__jobid__";
        writeAsXlsx(id);
    }

    private static void writeAsXlsx(String id) throws IOException {
        byte[] exportData = Result.xlsxExporter(id)
                .export();

        File xlsxExport = new File("export.xlsx");

        OutputStream outStream = new FileOutputStream(xlsxExport);
        outStream.write(exportData);
        System.out.println(String.format("Exported %s bytes of data in export.xlsx", exportData.length));

    }

}
