package com.saucelabs.common.unit;

import com.saucelabs.common.SauceConfiguration;
import com.saucelabs.common.SauceEnvironmentVariableNotSetException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class SauceConfigurationTest {
    private SauceConfiguration mockSauceEnv;
    @Before
    public void runBeforeTests() {
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
        mockSauceEnv.setUserNameEnvironmentVariable("FAKE");
        String key = mockSauceEnv.getUserName();
    }
    @Test(expected = SauceEnvironmentVariableNotSetException.class)
    public void shouldThrowExceptionForNullAccessKey() throws SauceEnvironmentVariableNotSetException {
        mockSauceEnv.setAccessKeyEnvironmentVariable("FAKE");
        String key = mockSauceEnv.getAccessKey();
    }
}
