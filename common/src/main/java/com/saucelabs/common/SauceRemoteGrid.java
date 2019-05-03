package com.saucelabs.common;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class SauceRemoteGrid {
    private static final String SAUCE_REMOTE_URL = "https://ondemand.saucelabs.com/wd/hub";
    private SauceEnvironment sauceEnvironmentData;
    private OperatingSystem currentOS;
    private String currentBrowserVersion;
    private SauceCapabilities sauceCapabilities;

    public SauceRemoteGrid() throws SauceEnvironmentVariableNotSetException {
        currentBrowser = Browser.Chrome;
        currentOS = OperatingSystem.Windows10;
        currentBrowserVersion = "latest";
        sauceEnvironmentData = new SauceEnvironment();
    }

    public SauceRemoteGrid(SauceCapabilities sauceCaps) {
        sauceCapabilities = sauceCaps;
    }

    private String toOperatingSystemString(OperatingSystem operatingSystem) {
        switch (operatingSystem)
        {
            case Windows10:
                return "Windows 10";
            case Linux:
                return "Linux";
            default:
                return null;
        }
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
        caps.setCapability("platform", toOperatingSystemString(currentOS));
        caps.setExperimentalOption("w3c", true);
        return caps;
    }

    public RemoteWebDriver startTest() throws MalformedURLException, SauceEnvironmentVariableNotSetException {
        return getRemoteDriver();
    }
}
