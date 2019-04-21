package com.saucelabs.common;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class SauceLabsSession {
    private static final String SAUCE_REMOTE_URL = "https://ondemand.saucelabs.com/wd/hub";
    private OperatingSystem currentOS;
    private String currentBrowserVersion;

    public SauceLabsSession()
    {
        currentBrowser = Browser.Chrome;
        currentOS = OperatingSystem.Linux;
        currentBrowserVersion = "latest";
    }
    private Browser currentBrowser;

    public Browser getBrowser() {
        return currentBrowser;
    }

    public OperatingSystem getOperatingSystem() {
        return currentOS;
    }

    public String getBrowserVersion() {
        return currentBrowserVersion;
    }

    public RemoteWebDriver start() throws MalformedURLException {
        return getRemoteDriver();
    }

    public RemoteWebDriver getRemoteDriver() throws MalformedURLException {
        ChromeOptions caps;
        caps = new ChromeOptions();
        caps.setCapability("version", getBrowserVersion());
        caps.setCapability("platform", "Windows 10");
        caps.setExperimentalOption("w3c", true);

        MutableCapabilities sauceOptions;
        sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", getUserName());
        sauceOptions.setCapability("accessKey", getAccessKey());

        caps.setCapability("sauce:options", sauceOptions);
        return new RemoteWebDriver(new URL(SAUCE_REMOTE_URL), caps);
    }

    public String getUserName() {
        return "nikolay-a";
    }

    public String getAccessKey() {
        return "accessKey";
    }
}
