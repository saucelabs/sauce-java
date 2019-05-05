package com.saucelabs.common;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.safari.SafariOptions;

public class SauceCapabilities {
    private String browser;
    private String browserVersion;
    private String os;

    public SauceCapabilities() {
        browser = Browser.Chrome.toString();
        os = toOperatingSystemString(OperatingSystem.Windows10);
        browserVersion = "latest";
    }

    public void setBrowser(Browser browser) {
        this.browser = browser.toString();
    }

    public void setBrowserVersion(String version) {
        this.browserVersion = version;
    }

    public void setOS(OperatingSystem os) {
        this.os = toOperatingSystemString(os);
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    private String toOperatingSystemString(OperatingSystem operatingSystem) {
        switch (operatingSystem) {
            case Windows10:
                return "Windows 10";
            case Linux:
                return "Linux";
            default:
                return null;
        }
    }

    public String getOS() {
        return os;
    }

    public String getBrowser() {
        return browser;
    }

    public MutableCapabilities getBrowserOption(){
        return getOptions(browser);
    }
    public MutableCapabilities getOptions(String browserName) {
        switch (browserName) {
            case "firefox":
                return new FirefoxOptions();
            case "edge":
                return new EdgeOptions();
            case "safari":
                return new SafariOptions();
            case "chrome":
                return new ChromeOptions();
            default:
                return new InternetExplorerOptions();
        }
    }
}
