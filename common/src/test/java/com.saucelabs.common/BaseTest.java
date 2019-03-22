package com.saucelabs.common;

import org.junit.Before;
import org.openqa.selenium.WebDriver;

import static org.mockito.Mockito.mock;

public class BaseTest {
    WebDriver mockWebDriver;
    @Before
    public void runBeforeTest()
    {
        mockWebDriver = mock(WebDriver.class);
    }
}
