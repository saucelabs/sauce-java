package com.saucelabs.remotedriver;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

public interface RemoteDriverInterface {
    RemoteWebDriver getRemoteWebDriver(String seleniumServer, MutableCapabilities capabilities)
        throws MalformedURLException;
}
