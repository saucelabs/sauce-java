package com.saucelabs.common;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class SauceLabsSession {
    private static final String SAUCE_REMOTE_URL = "https://ondemand.saucelabs.com/wd/hub";
    private SauceEnvironment sauceEnvironmentData;
    private OperatingSystem currentOS;
    private String currentBrowserVersion;

    public SauceLabsSession()
    {
        currentBrowser = Browser.Chrome;
        currentOS = OperatingSystem.Linux;
        currentBrowserVersion = "latest";
    }
    private Browser currentBrowser;

    public SauceLabsSession(SauceEnvironment stubSauceEnv) {
        sauceEnvironmentData = stubSauceEnv;
    }

    public Browser getBrowser() {
        return currentBrowser;
    }

    public OperatingSystem getOperatingSystem() {
        return currentOS;
    }

    public String getBrowserVersion() {
        return currentBrowserVersion;
    }

    public RemoteWebDriver start() throws MalformedURLException, SauceEnvironmentVariableNotSetException {
        return getRemoteDriver();
    }

    public RemoteWebDriver getRemoteDriver() throws MalformedURLException, SauceEnvironmentVariableNotSetException {
        ChromeOptions caps = new ChromeOptions();
        caps.setCapability("version", currentBrowserVersion);
        caps.setCapability("platform", currentOS);
        caps.setExperimentalOption("w3c", true);

        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", getUserName());
        sauceOptions.setCapability("accessKey", getAccessKey());

        caps.setCapability("sauce:options", sauceOptions);
        return new RemoteWebDriver(new URL(SAUCE_REMOTE_URL), caps);
    }

    public String getUserName() throws SauceEnvironmentVariableNotSetException {
        return sauceEnvironmentData.getUserName();
    }

    public String getAccessKey() {
        return "accessKey";
    }
}
