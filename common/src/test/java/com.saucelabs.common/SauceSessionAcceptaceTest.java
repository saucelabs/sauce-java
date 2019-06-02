package com.saucelabs.common;

import org.hamcrest.core.IsNot;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

import static org.junit.Assert.assertThat;

public class SauceSessionAcceptaceTest {

    @Test
    public void startSession_noSauceOptionsSet_returnsDriver() throws MalformedURLException, SauceEnvironmentVariableNotSetException {
        SauceSession session = new SauceSession().start();
        WebDriver actualDriver = session.getDriver();
        assertThat(actualDriver, IsNot.not(null));
    }
}
