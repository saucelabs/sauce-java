package com.saucelabs.common;

import org.openqa.selenium.WebDriver;

public class SauceSession {
    private WebDriver driver;

    public SauceSession(WebDriver driver)
    {
        this.driver = driver;
    }

    public SauceSession()
    {
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getBrowser() {
        return "Chrome";
    }
}
