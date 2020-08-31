package com.quantxt.sdk.profile;

import com.quantxt.sdk.client.HttpMethod;
import com.quantxt.sdk.client.QTRestClient;
import com.quantxt.sdk.client.Request;
import com.quantxt.sdk.client.Response;
import com.quantxt.sdk.dataprocess.DataProcess;
import com.quantxt.sdk.document.Document;
import com.quantxt.sdk.exception.QTApiConnectionException;
import com.quantxt.sdk.exception.QTApiException;
import com.quantxt.sdk.exception.QTRestException;
import com.quantxt.sdk.model.Extractor;
import com.quantxt.sdk.model.DictionaryDto;
import com.quantxt.sdk.model.ResultConfiguration;
import com.quantxt.sdk.model.UserProfileDetailsDto;
import com.quantxt.sdk.resource.Fetcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProfileFetcher extends Fetcher<Profile> {

    public ProfileFetcher(){

    }

    @Override
    public Profile fetch(QTRestClient client) {
        Request request = new Request(HttpMethod.GET, "/users/profile");

        Response response = client.request(request);

        if (response == null) {
            throw new QTApiConnectionException("Xlsx search export failed: Unable to connect to server");
        }

        if (!QTRestClient.SUCCESS.test(response.getStatusCode())) {
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
            return getConfigurations(client, response.getStream());
        } catch (IOException e) {
            throw new QTApiException("Xlsx search export failed. Unable to read the data.", e);
        }
    }

    private Profile getConfigurations(final QTRestClient client, InputStream in) throws IOException {
        byte[] bytes = in.readAllBytes();
        UserProfileDetailsDto userProfileDetailsDto = client.getObjectMapper().readValue(bytes, UserProfileDetailsDto.class);
        Profile profile = new Profile();
        profile.setUsername(userProfileDetailsDto.getEmail());
        List<ResultConfiguration> settings = userProfileDetailsDto.getSettings();
        if (settings != null){
            DataProcess [] dataProcesses = new DataProcess[settings.size()];
            for (int i=0; i< settings.size(); i++){
                ResultConfiguration setting = settings.get(i);
                DataProcess dataProcess = new DataProcess();
                dataProcesses[i] = dataProcess;
                List<Document> documents = new ArrayList<>();
                for (String f : setting.getFiles()){
                    Document document = new Document();
                    document.setId(f);
                }
                dataProcess.setDocuments(documents);
                dataProcess.setId(setting.getId());
                dataProcess.setDescription(setting.getTitle());
                dataProcess.setNumWorkers(setting.getNumWorkers());
                List<Extractor> extractors = new ArrayList<>();
                for (DictionaryDto dictionaryDto : setting.getSearchDictionaries()){
                    Extractor extractor = new Extractor(dictionaryDto);
                    extractors.add(extractor);
                }
                dataProcess.setExtractors(extractors);
            }
            profile.setDataProcesses(dataProcesses);
        }
        return profile;
    }
}
