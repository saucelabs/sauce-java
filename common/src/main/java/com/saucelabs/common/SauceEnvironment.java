package com.saucelabs.common;

public class SauceEnvironment {
    private String userNameEnvironmentVariableKey;
    private String apiKeyEnvironmentVariableName;
    //public String sauceUserName = getUserName();
    public String accessKey;
    private String userNameValue;
    private String apiKeyValue;

    public SauceEnvironment() throws SauceEnvironmentVariableNotSetException {
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

    public void setAccessKeyEnvironmentVariable(String key, String value) {
        apiKeyEnvironmentVariableName = key;
        apiKeyValue = value;
    }

    public void setUserNameEnvironmentVariable(String key, String value) {
        userNameEnvironmentVariableKey = key;
        userNameValue = value;
    }
    public String getUserNameEnvironmentVariableKey() {
        return userNameEnvironmentVariableKey;
    }

    public String getEnvironmentVariableApiKeyName() {
        return apiKeyEnvironmentVariableName;
    }
}
