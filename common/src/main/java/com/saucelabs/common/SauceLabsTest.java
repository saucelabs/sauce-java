package com.saucelabs.common;

public class SauceLabsTest {
    public SauceLabsTest()
    {
        currentBrowser = Browser.Chrome;
    }
    private Browser currentBrowser;

    public Browser getBrowser() {
        return currentBrowser;
    }
}
