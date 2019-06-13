package com.saucelabs.common;

public class SauceConfiguration {
    private String userNameEnvironmentVariableKey;
    private String apiKeyEnvironmentVariableName;
    public String accessKey;

    public SauceConfiguration() {
        userNameEnvironmentVariableKey = "SAUCE_USERNAME";
        apiKeyEnvironmentVariableName = "SAUCE_ACCESS_KEY";
    }

    public String getUserName() throws SauceEnvironmentVariableNotSetException {
        String userName = System.getenv(userNameEnvironmentVariableKey);
        return checkIfEmpty(userName);
    }
    public String getAccessKey() throws SauceEnvironmentVariableNotSetException {
        String apiKey = System.getenv(apiKeyEnvironmentVariableName);
        return checkIfEmpty(apiKey);
    }

    private String checkIfEmpty(String userName) throws SauceEnvironmentVariableNotSetException {
        if (userName == null)
            throw new SauceEnvironmentVariableNotSetException();
        return userName;
    }

    public void setAccessKeyEnvironmentVariable(String key) {
        apiKeyEnvironmentVariableName = key;
    }

    public void setUserNameEnvironmentVariable(String key) {
        userNameEnvironmentVariableKey = key;
    }
    public String getUserNameEnvironmentVariableKey() {
        return userNameEnvironmentVariableKey;
    }

    public String getEnvironmentVariableApiKeyName() {
        return apiKeyEnvironmentVariableName;
    }

}
