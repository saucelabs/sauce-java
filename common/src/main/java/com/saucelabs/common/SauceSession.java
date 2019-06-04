package com.saucelabs.common;

import com.saucelabs.remotedriver.DriverFactory;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

public class SauceSession {
    private WebDriver webDriver;

    public SauceSession(WebDriver driver)
    {
        this.webDriver = driver;
    }

    public SauceSession()
    {
    }

    public WebDriver getDriver() {
        return webDriver;
    }

    public String getBrowser() {
        return "Chrome";
    }

    public String getOs() {
        return "Linux";
    }

    public String getBrowserVersion() {
        return "latest";
    }

    public SauceSession start() throws MalformedURLException {
        this.webDriver = new DriverFactory().getInstance();
        return this;
    }
}
