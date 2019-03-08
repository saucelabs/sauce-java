package com.saucelabs.common;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class JavaScriptInvokerFactoryTest {
    WebDriver mockDriver;
    @Before
    public void runBeforeTest()
    {
    }
    @Test
    public void shouldReturnObjectForTheManager()
    {
        mockDriver = mock(WebDriver.class);
        JavaScriptInvokerManager jsManager = JavaScriptInvokerFactory.create(mockDriver);
        Assert.assertNotNull(jsManager);
    }
}
