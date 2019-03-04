package com.saucelabs.common;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.mock;


public class JavaScriptRunnerTests {
    @Test
    public void shouldReturnPassedForTrueResult()
    {
        WebDriver mockDriver = mock(WebDriver.class);
        JavaScriptRunnerManager mockJS = mock(JavaScriptRunnerManager.class);
        JavaScriptRunnerFactory.setJavaScriptManager(mockJS);

        JavaScriptRunner js = new JavaScriptRunner(mockDriver);
        assertThat(js.executeScript("test"), instanceOf(Object.class));
    }

    @Test
    public void shouldReturnObject()
    {
        WebDriver mockDriver = mock(WebDriver.class);
        JavaScriptRunner js = new JavaScriptRunner(mockDriver);
        assertThat(js.executeScript("test"), instanceOf(Object.class));
    }
}
