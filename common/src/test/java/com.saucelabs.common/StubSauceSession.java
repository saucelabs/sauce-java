package com.saucelabs.common;

import org.openqa.selenium.remote.RemoteWebDriver;

public class StubSauceSession extends SauceLabsSession {
    private String accessKey;
    private RemoteWebDriver fakeRemoteDriver;
    private String userName;

    public StubSauceSession(RemoteWebDriver fakeDriver)
    {
        fakeRemoteDriver = fakeDriver;
    }

    public StubSauceSession(String fakeValue) {
        this.userName = fakeValue;
        this.accessKey = fakeValue;
    }

    @Override
    public RemoteWebDriver getRemoteDriver(){
        return fakeRemoteDriver;
    }
    @Override
    public String getUserName(){
        return userName;
    }
    @Override
    public String getAccessKey(){
        return accessKey;
    }
}
