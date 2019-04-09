package com.saucelabs.common;

import org.junit.Test;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.junit.Assert.assertEquals;

public class SauceDriverTests {

    private SauceDriver driver;

    @Test
    public void basicDefault() {
        driver = new SauceDriver();

        MutableCapabilities expectedOptions = new MutableCapabilities();

        assertEquals(driver.getOptions(), expectedOptions);
    }

    @Test
    public void desiredCapabilities(){
        DesiredCapabilities capabilities = new DesiredCapabilities();

        driver = new SauceDriver(capabilities);

        MutableCapabilities expectedOptions = new MutableCapabilities();

        assertEquals(driver.getOptions(), expectedOptions);
    }

    @Test
    public void browserOptions(){
        MutableCapabilities options = new MutableCapabilities();

        driver = new SauceDriver(options);

        MutableCapabilities expectedOptions = new MutableCapabilities();

        assertEquals(driver.getOptions(), expectedOptions);
    }

    @Test
    public void setOnlyBrowser(){
        driver = new SauceDriver("chrome");

        MutableCapabilities expectedOptions = new ChromeOptions();

        assertEquals(driver.getOptions(), expectedOptions);
    }

    @Test
    public void setOnlyMobileOS() {
        driver = new SauceDriver("android");

        MutableCapabilities expectedOptions = new MutableCapabilities();

        assertEquals(driver.getOptions(), expectedOptions);
    }
}
