package com.saucelabs.common;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

public class WebDriverFactory {
    private SauceConfiguration sauceConfigurationData;
    public SauceCapabilities sauceCapabilities;
    private WebDriver driver;

    public WebDriverFactory(WebDriver driver) {
        this.driver = driver;
        this.sauceCapabilities = new SauceCapabilities();
        this.sauceConfigurationData = new SauceConfiguration();
    }

    public WebDriverFactory() {
        this.sauceCapabilities = new SauceCapabilities();
        this.sauceConfigurationData = new SauceConfiguration();
    }

    public WebDriver create(String browser) throws SauceEnvironmentVariableNotSetException, MalformedURLException {
        switch (browser)
        {
            case Browser.CHROME:
                sauceCapabilities.setBrowser(Browser.CHROME);
                return getChromeEnvironment();
            default:
                sauceCapabilities.setBrowser(Browser.CHROME);
                return driver;
        }
    }

    private WebDriver getChromeEnvironment() throws SauceEnvironmentVariableNotSetException, MalformedURLException {
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
        caps.setCapability("version", sauceCapabilities.getBrowserVersion());
        caps.setCapability("platform", sauceCapabilities.getOS());
        caps.setExperimentalOption("w3c", true);
        return caps;
    }
}
