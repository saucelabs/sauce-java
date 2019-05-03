package com.saucelabs.common;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class SauceLabsSession {
    private static final String SAUCE_REMOTE_URL = "https://ondemand.saucelabs.com/wd/hub";
    private SauceEnvironment sauceEnvironmentData;
    private String currentOS;
    private String currentBrowserVersion;

    public SauceLabsSession() throws SauceEnvironmentVariableNotSetException {
        currentBrowser = Browser.Chrome.toString().toLowerCase();
        currentOS = "Windows 10";
        currentBrowserVersion = "latest";
        sauceEnvironmentData = new SauceEnvironment();
    }
    private String currentBrowser;

    public String getBrowser() {
        return currentBrowser;
    }

    public String getOperatingSystem() {
        return currentOS;
    }

    public String getBrowserVersion() {
        return currentBrowserVersion;
    }

    public RemoteWebDriver start() throws MalformedURLException, SauceEnvironmentVariableNotSetException {
        return getRemoteDriver();
    }

    public RemoteWebDriver getRemoteDriver() throws MalformedURLException, SauceEnvironmentVariableNotSetException {
        ChromeOptions caps = getChromeOptions();
        MutableCapabilities sauceOptions = getMutableCapabilities();
        caps.setCapability("sauce:options", sauceOptions);
        return new RemoteWebDriver(new URL(SAUCE_REMOTE_URL), caps);
    }
    private MutableCapabilities getMutableCapabilities() throws SauceEnvironmentVariableNotSetException {
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", sauceEnvironmentData.getUserName());
        sauceOptions.setCapability("accessKey", sauceEnvironmentData.getAccessKey());
        sauceOptions.setCapability("seleniumVersion", "3.141.59");
        return sauceOptions;
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions caps = new ChromeOptions();
        caps.setCapability("version", currentBrowserVersion);
        caps.setCapability("platform", currentOS);
        caps.setExperimentalOption("w3c", true);
        return caps;
    }
}
