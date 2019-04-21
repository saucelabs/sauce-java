package com.saucelabs.common;

import org.openqa.selenium.remote.RemoteWebDriver;

public class StubSauceSession extends SauceLabsSession {
    private final RemoteWebDriver fakeRemoteDriver;

    public StubSauceSession(RemoteWebDriver remoteDriver)
    {
        fakeRemoteDriver = remoteDriver;
    }
    @Override
    public RemoteWebDriver getRemoteDriver(){
        return fakeRemoteDriver;
    }
}
