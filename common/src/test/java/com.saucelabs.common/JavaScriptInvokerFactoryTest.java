package com.saucelabs.common;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;


public class JavaScriptInvokerFactoryTest extends BaseUnitTest {
    @Test
    public void shouldReturnObjectForTheManager()
    {
        JavaScriptInvokerImpl jsManager = JavaScriptInvokerFactory.create(mockWebDriver);
        assertThat(jsManager, instanceOf(JavaScriptInvokerImpl.class));
    }
}
