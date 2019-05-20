package com.saucelabs.common;

import org.junit.Test;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebDriverFactoryTest {
    WebDriverFactory factory;
    WebDriver driver;

    @Test
    public void shouldNotBeNull()
    {
        factory = new WebDriverFactory();
        assertNotNull(factory);
    }
    @Test
    public void shouldReturnDriverObject() throws MalformedURLException, SauceEnvironmentVariableNotSetException {
        WebDriver stubDriver = mock(WebDriver.class);
        factory = new WebDriverFactory(stubDriver);
        driver = factory.create("");
        assertNotNull(driver);
    }

    @Test
    public void shouldSetToChromeDriver() throws MalformedURLException, SauceEnvironmentVariableNotSetException {
        WebDriver stubDriver = mock(WebDriver.class);
        factory = new WebDriverFactory(stubDriver);
        driver = factory.create(Browser.CHROME);
        assertEquals(Browser.CHROME, factory.sauceCapabilities.getBrowser());
    }

    @Test
    public void shouldReturnRemoteWebDriver() throws MalformedURLException, SauceEnvironmentVariableNotSetException {
        WebDriver stubDriver = mock(WebDriver.class);
        WebDriverFactory mockFactory = mock(WebDriverFactory.class);
        when(mockFactory.getChromeOptions()).thenReturn(new ChromeOptions());
        when(mockFactory.getMutableCapabilities()).thenReturn(new MutableCapabilities());

        driver = factory.create(Browser.CHROME);
        assertTrue("did not return a RemoteWebDriver", driver instanceof RemoteWebDriver);
    }
}