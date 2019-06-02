package com.saucelabs.common;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

import static org.junit.Assert.assertNotNull;

public class SauceSessionAcceptaceTest {

    private WebDriver webDriver;
    @After
    public void cleanUp()
    {
        webDriver.quit();
    }
    @Test
    public void startSession_noSauceOptionsSet_returnsDriver() throws MalformedURLException, SauceEnvironmentVariableNotSetException {
        SauceSession session = new SauceSession().start();
        webDriver = session.getDriver();
        assertNotNull(webDriver);
    }
}
