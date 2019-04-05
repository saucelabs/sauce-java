package com.saucelabs.common;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import static org.mockito.Mockito.mock;

public class BaseUnitTest {
    WebDriver mockWebDriver;
    @Before
    public void runBeforeTest()
    {
        mockWebDriver = mock(WebDriver.class);
        resetJavaScriptInvokerState();
    }
    @After
    public void afterEveryTest()
    {
        resetJavaScriptInvokerState();
    }
    private void resetJavaScriptInvokerState() {
        JavaScriptInvokerFactory.setJavaScriptInvoker(null);
    }
}
