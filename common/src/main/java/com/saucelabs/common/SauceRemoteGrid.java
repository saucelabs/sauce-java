package com.saucelabs.common;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class SauceRemoteGrid {
    private SauceConfiguration sauceConfigurationData;
    private String currentOS;
    private String currentBrowserVersion;
    private SauceCapabilities sauceCapabilities;

    public SauceRemoteGrid() {
        sauceCapabilities = new SauceCapabilities();
        sauceConfigurationData = new SauceConfiguration();
        currentBrowserVersion = sauceCapabilities.getBrowserVersion();
    }

    public SauceRemoteGrid(SauceCapabilities sauceCaps) {
        sauceCapabilities = sauceCaps;
        currentBrowserVersion = sauceCapabilities.getBrowserVersion();
        currentOS = sauceCapabilities.getOS();
    }

    public String getBrowser() {
        return sauceCapabilities.getBrowser();
    }

    public String getOperatingSystem() {
        return sauceCapabilities.getOS();
    }

    public String getBrowserVersion() {
        return currentBrowserVersion;
    }

    public RemoteWebDriver getRemoteDriver() throws MalformedURLException, SauceEnvironmentVariableNotSetException {
        ChromeOptions caps = getChromeOptions();
        MutableCapabilities sauceOptions = getMutableCapabilities();
        caps.setCapability("sauce:options", sauceOptions);
        return new RemoteWebDriver(new URL(sauceConfigurationData.SAUCE_REMOTE_URL), caps);
    }
    private MutableCapabilities getMutableCapabilities() throws SauceEnvironmentVariableNotSetException {
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", sauceConfigurationData.getUserName());
        sauceOptions.setCapability("accessKey", sauceConfigurationData.getAccessKey());
        sauceOptions.setCapability("seleniumVersion", "3.141.59");
        return sauceOptions;
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions caps = new ChromeOptions();
        caps.setCapability("version", sauceCapabilities.getBrowserVersion());
        caps.setCapability("platform", sauceCapabilities.getOS());
        caps.setExperimentalOption("w3c", true);
        return caps;
    }

    public RemoteWebDriver startTest() throws MalformedURLException, SauceEnvironmentVariableNotSetException {
        return getRemoteDriver();
    }
}
