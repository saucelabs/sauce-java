package com.saucelabs.remotedriver;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class ConcreteRemoteDriverManager implements RemoteDriverInterface {
    public RemoteWebDriver getRemoteWebDriver(String seleniumServer, MutableCapabilities capabilities)
        throws MalformedURLException
    {
        return new RemoteWebDriver(new URL(seleniumServer), capabilities);
    }
}
