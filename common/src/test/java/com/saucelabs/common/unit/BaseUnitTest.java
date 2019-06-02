package com.saucelabs.common.unit;

import com.saucelabs.common.JavaScriptInvokerFactory;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import static org.mockito.Mockito.mock;

public class BaseUnitTest {
    public WebDriver mockWebDriver;
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
        JavaScriptInvokerFactory.setJavaScriptExecutor(null);
    }
}
