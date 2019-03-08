package com.saucelabs.common;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class JavaScriptInvokerTest {
    @Test
    public void shouldReturnObjectForTheManager()
    {
        WebDriver mockDriver = mock(WebDriver.class);
        JavaScriptInvokerManager mockJS = mock(JavaScriptInvokerManager.class);
        when(mockJS.executeScript("test")).thenReturn(Object.class);
        JavaScriptInvokerFactory.setJavaScriptManager(mockJS);

        JavaScriptInvoker js = new JavaScriptInvoker(mockDriver);
        Object obj = js.executeScript("test");
        assertThat(js.executeScript("test"), instanceOf(Object.class));
    }

    @Test
    public void shouldReturnObject()
    {
        WebDriver mockDriver = mock(WebDriver.class);
        JavaScriptInvoker js = new JavaScriptInvoker(mockDriver);
        Object obj = js.executeScript("test");
        assertThat(obj, instanceOf(Object.class));
    }
}
