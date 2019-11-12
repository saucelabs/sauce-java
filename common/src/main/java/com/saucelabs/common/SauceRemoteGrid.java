package com.saucelabs.common;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class SauceRemoteGrid {

    private static final String SAUCE_ONDEMAND_URL = "https://ondemand.saucelabs.com/wd/hub";

    private MutableCapabilities options;
    private WebDriver driver;

    public SauceRemoteGrid(MutableCapabilities capabilities){
        this.options = capabilities;
        //this.driver = connect(capabilities);
    }

    public SauceRemoteGrid(){
        this(new MutableCapabilities());
    }

    public SauceRemoteGrid(String browser){
        this.options = browserNameToOptions(browser);
        //this.driver = connect(this.options);
    }


    public MutableCapabilities getOptions() {
        return options;
    }

    public WebDriver getDriver() {
        return driver;
    }

    private MutableCapabilities browserNameToOptions(String browser) {
        if (browser.toLowerCase().equals("chrome")) {
            return new ChromeOptions();
        }
        else {
            return new MutableCapabilities();
        }
    }

    private WebDriver connect(MutableCapabilities options) {
        try {
            return new RemoteWebDriver(new URL(SAUCE_ONDEMAND_URL), options);
        } catch (MalformedURLException e) {
            System.out.println("Did you change the Sauce URL?");
            return null;
        }
    }
}
