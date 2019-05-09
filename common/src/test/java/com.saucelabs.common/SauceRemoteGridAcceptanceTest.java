package com.saucelabs.common;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;


public class SauceRemoteGridAcceptanceTest {
    @Test
    public void shouldReturnRemoteWebDriverObject() throws SauceEnvironmentVariableNotSetException, MalformedURLException {
        SauceRemoteGrid sauceGrid = new SauceRemoteGrid();
        RemoteWebDriver driver = sauceGrid.startTest();
        Assert.assertNotNull(driver.getSessionId());
        driver.quit();
    }
    @Test
    public void shouldReturnRemoteWebDriverFromSauceCaps() throws SauceEnvironmentVariableNotSetException, MalformedURLException {
        SauceCapabilities sauceCaps = new SauceCapabilities();
        //sauceCaps.setBrowser(Browser.CHROME);
        sauceCaps.setBrowserVersion("latest");
        sauceCaps.setOS(OperatingSystem.Windows10);

        SauceRemoteGrid sauceGrid = new SauceRemoteGrid(sauceCaps);
        RemoteWebDriver driver = sauceGrid.startTest();
        Assert.assertNotNull(driver.getSessionId());
        driver.quit();
    }
}
