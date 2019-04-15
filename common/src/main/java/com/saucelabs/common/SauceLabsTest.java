package com.saucelabs.common;

public class SauceLabsTest {
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
}
