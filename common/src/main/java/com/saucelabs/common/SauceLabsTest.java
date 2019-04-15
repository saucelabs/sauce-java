package com.saucelabs.common;

public class SauceLabsTest {
    private OperatingSystem currentOS;

    public SauceLabsTest()
    {
        currentBrowser = Browser.Chrome;
        currentOS = OperatingSystem.Linux;
    }
    private Browser currentBrowser;

    public Browser getBrowser() {
        return currentBrowser;
    }

    public OperatingSystem getOperatingSystem() {
        return currentOS;
    }
}
