package com.saucelabs.common;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.mock;


public class JavaScriptInvokerFactoryTest {
    WebDriver mockDriver;
    @Before
    public void runBeforeTest()
    {
        mockDriver = mock(WebDriver.class);
    }
    @Test
    public void shouldReturnObjectForTheManager()
    {
        JavaScriptInvokerManager jsManager = JavaScriptInvokerFactory.create(mockDriver);
        assertThat(jsManager, instanceOf(JavaScriptInvokerManager.class));
    }
}
