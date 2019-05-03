package com.saucelabs.common;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

public class SauceRemoteGrid {
    public SauceLabsSession test;
    public SauceRemoteGrid() throws SauceEnvironmentVariableNotSetException {
        test = new SauceLabsSession();
    }

    public RemoteWebDriver startSession() throws MalformedURLException, SauceEnvironmentVariableNotSetException {
        return test.start();
    }
}
