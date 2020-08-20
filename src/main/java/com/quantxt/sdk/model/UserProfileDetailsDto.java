package com.quantxt.sdk.model;

import java.util.List;

public class UserProfileDetailsDto {
    private String email;
    private List<ResultConfiguration> settings;

    public UserProfileDetailsDto(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ResultConfiguration> getSettings() {
        return settings;
    }

    public void setSettings(List<ResultConfiguration> settings) {
        this.settings = settings;
    }
}
