package com.saucelabs.common.unit;

import com.saucelabs.common.JavaScriptExecutor;
import com.saucelabs.common.JavaScriptInvokerFactory;
import com.saucelabs.common.JavaScriptInvokerImpl;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;


public class JavaScriptInvokerFactoryTest extends BaseUnitTest {
    @Test
    public void shouldReturnObjectForTheManager()
    {
        JavaScriptExecutor jsManager = JavaScriptInvokerFactory.create(mockWebDriver);
        assertThat(jsManager, instanceOf(JavaScriptInvokerImpl.class));
    }
}
