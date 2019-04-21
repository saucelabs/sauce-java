package com.saucelabs.common;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

public class SauceLabsTest {
    private static final String SAUCE_REMOTE_URL = "https://ondemand.saucelabs.com/wd/hub";
    private OperatingSystem currentOS;
    private String currentBrowserVersion;

    public SauceLabsTest()
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
        return getRemoteDriver().create(getBrowserVersion(),
            "Windows 10", getUserName(), getAccessKey(),
            SAUCE_REMOTE_URL);
    }

    public IRemoteBrowser getRemoteDriver() {
        return new SauceRemoteBrowser();
    }

    public String getUserName() {
        return "nikolay-a";
    }

    public String getAccessKey() {
        return "accessKey";
    }
}
