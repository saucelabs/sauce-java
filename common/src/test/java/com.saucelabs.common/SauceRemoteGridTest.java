package com.saucelabs.common;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;


public class SauceRemoteGridTest {
    @Test
    public void shouldReturnRemoteWebDriverObject() throws SauceEnvironmentVariableNotSetException, MalformedURLException {
        SauceRemoteGrid sauceGrid = new SauceRemoteGrid();
        RemoteWebDriver driver = sauceGrid.startSession();
        Assert.assertNotNull(driver.getSessionId());
        driver.quit();
    }
}
