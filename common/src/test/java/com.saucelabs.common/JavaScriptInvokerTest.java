package com.saucelabs.common;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.*;


public class JavaScriptInvokerTest extends BaseTest
{
    @Test
    public void shouldExecuteScriptOneTimeWhenMockManagerIsSet()
    {
        JavaScriptInvokerManager mockJsManager = mock(JavaScriptInvokerManager.class);

        JavaScriptInvokerFactory.setJavaScriptInvoker(mockJsManager);
        JavaScriptInvoker js = new JavaScriptInvoker(mockWebDriver);
        js.executeScript("test");
        verify(mockJsManager, times(1)).executeScript("test");
    }

    @Test
    public void shouldReturnJavaScriptInvokerWhenManagerNotSet()
    {
        JavaScriptInvoker js = new JavaScriptInvoker(mockWebDriver);
        assertThat(js, instanceOf(JavaScriptInvoker.class));
    }
}
