package com.saucelabs.common;

import org.junit.Test;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.junit.Assert.assertEquals;

public class SauceRemoteGridTest {

    private SauceRemoteGrid sauceGrid;

    @Test
    public void basicDefault() {
        sauceGrid = new SauceRemoteGrid();

        MutableCapabilities expectedOptions = new MutableCapabilities();

        assertEquals(sauceGrid.getOptions(), expectedOptions);
    }

    @Test
    public void desiredCapabilities(){
        DesiredCapabilities capabilities = new DesiredCapabilities();

        sauceGrid = new SauceRemoteGrid(capabilities);

        MutableCapabilities expectedOptions = new MutableCapabilities();

        assertEquals(sauceGrid.getOptions(), expectedOptions);
    }

    @Test
    public void browserOptions(){
        MutableCapabilities options = new MutableCapabilities();

        sauceGrid = new SauceRemoteGrid(options);

        MutableCapabilities expectedOptions = new MutableCapabilities();

        assertEquals(sauceGrid.getOptions(), expectedOptions);
    }

    @Test
    public void setOnlyBrowser(){
        sauceGrid = new SauceRemoteGrid("chrome");

        MutableCapabilities expectedOptions = new ChromeOptions();

        assertEquals(sauceGrid.getOptions(), expectedOptions);
    }

    @Test
    public void setOnlyMobileOS() {
        sauceGrid = new SauceRemoteGrid("android");

        MutableCapabilities expectedOptions = new MutableCapabilities();
        expectedOptions.setCapability("platformName", "Android");

        assertEquals(sauceGrid.getOptions(), expectedOptions);
    }
}
