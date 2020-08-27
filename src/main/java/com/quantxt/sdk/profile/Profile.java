package com.quantxt.sdk.profile;

import com.quantxt.sdk.dataprocess.DataProcess;
import com.quantxt.sdk.resource.Resource;

public class Profile extends Resource {

    public static ProfileFetcher fetcher() { return new ProfileFetcher(); }

    private DataProcess[] dataProcesses;
    private String username;

    public Profile(){

    }

    public DataProcess[] getDataProcesses() {
        return dataProcesses;
    }

    public void setDataProcesses(DataProcess[] dataProcesses) {
        this.dataProcesses = dataProcesses;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
