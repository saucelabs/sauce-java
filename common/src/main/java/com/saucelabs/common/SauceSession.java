package com.saucelabs.common;

import com.saucelabs.remotedriver.DriverFactory;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

public class SauceSession {
    private WebDriver webDriver;
    private SauceConfiguration sauceConfigurationData;

    public SauceSession(WebDriver driver)
    {
        this.webDriver = driver;
    }

    public SauceSession()
    {
        sauceConfigurationData = new SauceConfiguration();
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
    private WebDriver getChromeEnvironment() throws MalformedURLException, SauceEnvironmentVariableNotSetException {
        ChromeOptions caps = getChromeOptions();
        MutableCapabilities sauceOptions = getMutableCapabilities();
        caps.setCapability("sauce:options", sauceOptions);
        return new RemoteWebDriver(sauceConfigurationData.getSauceUrl(), caps);
    }

    public MutableCapabilities getMutableCapabilities() throws SauceEnvironmentVariableNotSetException {
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", sauceConfigurationData.getUserName());
        sauceOptions.setCapability("accessKey", sauceConfigurationData.getAccessKey());
        sauceOptions.setCapability("seleniumVersion", "3.141.59");
        return sauceOptions;
    }

    public ChromeOptions getChromeOptions() {
        ChromeOptions caps = new ChromeOptions();
        caps.setCapability("version", getBrowserVersion());
        caps.setCapability("platform", getOs());
        caps.setExperimentalOption("w3c", true);
        return caps;
    }
}
