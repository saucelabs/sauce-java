package com.saucelabs.common;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class SauceSessionTests {
    @Test
    public void shouldReturnSessionObject()
    {
        SauceSession session = new SauceSession();
        assertNotNull(session);
    }
    @Test
    public void shouldReturnWebDriverObject()
    {
        WebDriver stubDriver = mock(WebDriver.class);
        SauceSession session = new SauceSession(stubDriver);
        assertTrue(session.getDriver() instanceof WebDriver);
    }
}
