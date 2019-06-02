package com.saucelabs.common;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class SauceConfigurationTest {
    private SauceConfiguration mockSauceEnv;
    @Before
    public void runBeforeTests() throws SauceEnvironmentVariableNotSetException {
        mockSauceEnv = new SauceConfiguration();
    }
    @Test
    public void shouldReturnDefaultUserNameEnvironmentKey() {
        Assert.assertEquals("SAUCE_USERNAME", mockSauceEnv.getUserNameEnvironmentVariableKey());
    }
    @Test
    public void shouldReturnDefaultAccessKeyEnvironmentKey() {
        Assert.assertEquals("SAUCE_ACCESS_KEY", mockSauceEnv.getEnvironmentVariableApiKeyName());
    }
    @Test(expected = SauceEnvironmentVariableNotSetException.class)
    public void shouldThrowExceptionForNullUserName() throws SauceEnvironmentVariableNotSetException {
        mockSauceEnv.setUserNameEnvironmentVariable("FAKE", "FAKEVAL");
        String key = mockSauceEnv.getUserName();
    }
    @Test(expected = SauceEnvironmentVariableNotSetException.class)
    public void shouldThrowExceptionForNullAccessKey() throws SauceEnvironmentVariableNotSetException {
        mockSauceEnv.setAccessKeyEnvironmentVariable("FAKE", "FAKEVAL");
        String key = mockSauceEnv.getAccessKey();
    }
}
