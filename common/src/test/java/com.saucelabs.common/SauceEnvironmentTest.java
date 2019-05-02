package com.saucelabs.common;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class SauceEnvironmentTest {
    private SauceEnvironment sauceEnv;
    @Before
    public void runBeforeTests()
    {
        sauceEnv = new SauceEnvironment();
    }
    @Test
    public void shouldReturnDefaultUserNameEnvironmentKey() {
        Assert.assertEquals("SAUCE_USERNAME", sauceEnv.userNameKey);
    }
    @Test
    public void shouldReturnDefaultAccessKeyEnvironmentKey() {
        Assert.assertEquals("SAUCE_ACCESS_KEY", sauceEnv.apiKeyVariableName);
    }
}
