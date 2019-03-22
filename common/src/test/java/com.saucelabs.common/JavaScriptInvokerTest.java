package com.saucelabs.common;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class JavaScriptInvokerTest
{
    WebDriver mockDriver;
    @Before
    public void runBeforeTest()
    {
        mockDriver = mock(WebDriver.class);
    }
    @Test
    public void shouldReturnObjectForTheManager()
    {
        JavaScriptInvokerManager mockJsManager = mock(JavaScriptInvokerManager.class);
        when(mockJsManager.executeScript("test")).thenReturn(Object.class);

        JavaScriptInvokerFactory.setJavaScriptManager(mockJsManager);
        JavaScriptInvoker js = new JavaScriptInvoker(mockDriver);
        Object obj = js.executeScript("test");
        assertThat(obj, instanceOf(Object.class));
    }

    @Test
    public void shouldReturnJavaScriptInvokerWhenManagerNotSet()
    {
        JavaScriptInvoker js = new JavaScriptInvoker(mockDriver);
        assertThat(js, instanceOf(JavaScriptInvoker.class));
    }
}
