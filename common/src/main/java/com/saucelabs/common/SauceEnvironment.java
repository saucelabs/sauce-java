package com.saucelabs.common;

public class SauceEnvironment {
    public String userNameKey = "SAUCE_USERNAME";
    public String apiKeyVariableName = "SAUCE_ACCESS_KEY";
    private String userNameValue;

    public void setUserNameEnvironmentVariable(String key, String value) {
        userNameKey = key;
        userNameValue = value;
    }

    public String getUserName() throws SauceEnvironmentVariableNotSetException {
        String userName = System.getenv(userNameKey);
        if(userName == null)
            throw new SauceEnvironmentVariableNotSetException();
        return userName;
    }
}
