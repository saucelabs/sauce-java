package com.saucelabs.common;

import com.saucelabs.remotedriver.DriverFactory;
import com.saucelabs.remotedriver.RemoteDriverInterface;
import com.saucelabs.remotedriver.SauceOptions;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

public class SauceSession {
    private DriverFactory driverFactory;
    private RemoteDriverInterface remoteDriverManager;
    private SauceOptions sauceOptions;
    private WebDriver webDriver;
    private String browser;
    private String version;

    public SauceSession(WebDriver driver)
    {
        this.webDriver = driver;
    }

    public SauceSession()
    {
        driverFactory = new DriverFactory();
    }

    public SauceSession(SauceOptions options, RemoteDriverInterface remoteDriverManager) {
        this.sauceOptions = options;
        this.remoteDriverManager = remoteDriverManager;
        this.driverFactory = new DriverFactory(remoteDriverManager);
    }

    public SauceSession(RemoteDriverInterface stubRemoteDriver) {
        this.remoteDriverManager = stubRemoteDriver;
        this.driverFactory = new DriverFactory(remoteDriverManager);
    }

    public WebDriver getDriver() {
        return webDriver;
    }

    //TODO should probably return the driverfactory.capabilities value. Need to make that happen.
    public String getBrowser() {
        return driverFactory.capabilities.getBrowserName();
    }

    public String getOs() {
        return driverFactory.capabilities.getPlatform().name();
    }

    public String getBrowserVersion() {
        return driverFactory.capabilities.getVersion();
    }

    public SauceSession start() throws MalformedURLException {
        this.webDriver = driverFactory.withChrome().getInstance();
        return this;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
