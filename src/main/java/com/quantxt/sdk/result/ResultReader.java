package com.quantxt.sdk.result;

import com.quantxt.sdk.client.HttpMethod;
import com.quantxt.sdk.client.QTRestClient;
import com.quantxt.sdk.client.Request;
import com.quantxt.sdk.client.Response;
import com.quantxt.sdk.dataprocess.DataProcess;
import com.quantxt.sdk.exception.QTApiConnectionException;
import com.quantxt.sdk.exception.QTApiException;
import com.quantxt.sdk.exception.QTRestException;
import com.quantxt.sdk.model.Extractor;
import com.quantxt.sdk.model.ExtInterval;
import com.quantxt.sdk.model.ExtIntervalSimple;
import com.quantxt.sdk.model.SearchResultDto;
import com.quantxt.sdk.resource.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.quantxt.sdk.result.Result.toByteArray;

public class ResultReader extends Reader<Result> {

    final private static Logger log = LoggerFactory.getLogger(ResultReader.class);

    private String id;

    public ResultReader(String id) {
        this.id = id;
    }

    @Override
    public List<Result> read(QTRestClient client) {
        Request request = new Request(HttpMethod.GET, String.format("/reports/%s/json", this.id));

        Response response = client.request(request);

        if (response == null) {
            throw new QTApiConnectionException("JSON search export failed: Unable to connect to server");
        } else if (!QTRestClient.SUCCESS.test(response.getStatusCode())) {
            QTRestException restException = QTRestException.fromJson(response.getStream(), client.getObjectMapper());
            if (restException == null) {
                throw new QTApiException("Server Error, no content");
            }

            throw new QTApiException(
                    restException.getCode(),
                    restException.getMessage(),
                    response.getStatusCode(),
                    null
            );
        }

        try {
            // Get the settings first
            List<DataProcess> dataProcesses = DataProcess.reader().read();
            if (dataProcesses == null) return null;
            DataProcess dataProcess = null;
            for (DataProcess dp : dataProcesses){
                if (dp.getId().equals(id)) {
                    dataProcess = dp;
                    break;
                }
            }
            if (dataProcess == null) {
                return null;
            }
            // get types from the extractors
            HashMap<String, Extractor.DataType> dataTypeHashMap = new HashMap<>();
            for (Extractor extractor : dataProcess.getExtractors()){
                if (extractor.getType() != null){
                    dataTypeHashMap.putIfAbsent(extractor.getVocabulary().getId(), extractor.getType());
                }
            }

            String result_str =  new String(toByteArray(response.getStream()), StandardCharsets.UTF_8);
            SearchResultDto searchResultDto[] = client.getObjectMapper().readValue(result_str, SearchResultDto[].class);
            List<Result> results = new ArrayList<>();
            for (int i=0; i< searchResultDto.length; i++ ){
                SearchResultDto srd = searchResultDto[i];
                Result result = new Result();
                if (srd.getValues() == null) continue;
                result.setId(srd.getSearchId());
                result.setUnitNumber(srd.getPosition());
                result.setDocumentName(srd.getSource());
                result.setCreationTime(srd.getDate());
                if (srd.getValues() != null){
                    ExtInterval[] values = srd.getValues();
                    Field [] fields = new Field[values.length];
                    for (int j=0; j< values.length; j++){
                        ExtInterval value = values[j];
                        Field field = new Field();
                        Position fieldPosition = new Position();
                        field.setCategory(value.getCategory());
                        field.setStr(value.getStr());
                        field.setVocabId(value.getDict_id());
                        field.setVocabName(value.getDict_name());
                        Extractor.DataType dataType = dataTypeHashMap.get(value.getDict_id());
                        if (dataType != null){
                            field.setType(dataType);
                        }
                        fieldPosition.setEnd(value.getEnd());
                        fieldPosition.setStart(value.getStart());
                        fieldPosition.setLine(value.getLine());
                        field.setPosition(fieldPosition);
                        ExtIntervalSimple[] extIntervalSimples = value.getExtIntervalSimples();

                        if (extIntervalSimples != null){
                            FieldValue [] fieldValues = new FieldValue[extIntervalSimples.length];
                            for (int k=0 ; k < extIntervalSimples.length; k++){
                                ExtIntervalSimple extIntervalSimple = extIntervalSimples[k];
                                Position fieldValuePosition = new Position();
                                field.setCategory(value.getCategory());
                                String str = extIntervalSimple.getStr();
                                str = str.replaceAll("\\s+", " ").trim();
                                fieldValuePosition.setStart(extIntervalSimple.getStart());
                                fieldValuePosition.setEnd(extIntervalSimple.getEnd());
                                fieldValuePosition.setLine(extIntervalSimple.getLine());
                                fieldValues[k].setStr(str);
                                fieldValues[k].setPosition(fieldPosition);
                            }
                            field.setFieldValues(fieldValues);
                        }
                        fields[j] = field;
                    }
                    result.setFields(fields);
                }
                results.add(result);
            }
            return results;
        } catch (IOException e) {
            throw new QTApiException("JSON search export failed. Unable to read the data.", e);
        }
    }

    private Long processLongNumber(String number_str){
        String str = number_str.replaceAll("\\.\\d+", "");
        str = str.replaceAll("[^\\d]", "");
        try {
            if (!str.isEmpty()) {
                long long_value = Long.parseLong(str);
                return long_value;
            }
        } catch (NumberFormatException e){
            log.error("Error in processing Long number {}  {}", number_str , e.getMessage());
        }
        return null;
    }

    private Double processDoubleNumber(String number_str){
        String str = number_str.replaceAll("[^\\d\\.]", "");
        try {
            if (!str.isEmpty()) {
                double double_value = Double.parseDouble(str);
                return double_value;
            }

        } catch (NumberFormatException e){
            log.error("Error in processing Double number {}  {}", number_str , e.getMessage());
        }
        return null;
    }
}