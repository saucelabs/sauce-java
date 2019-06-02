package com.saucelabs.common;

import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class SauceSessionTests {
    @Test
    public void defaultConstructor_called_returnsObject()
    {
        SauceSession session = new SauceSession();
        assertNotNull(session);
    }
    @Test
    public void getDriver_called_returnsWebDriver()
    {
        WebDriver stubDriver = mock(WebDriver.class);
        SauceSession session = new SauceSession(stubDriver);
        assertTrue(session.getDriver() instanceof WebDriver);
    }
    @Test
    public void noSauceOptionsSet_whenCreated_defaultChrome()
    {
        SauceSession session = new SauceSession();
        String actualBrowser = session.getBrowser();
        assertThat(actualBrowser, IsEqualIgnoringCase.equalToIgnoringCase("Chrome"));
    }
}
