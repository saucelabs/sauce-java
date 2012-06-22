package com.saucelabs.junit;

import com.saucelabs.saucerest.junit.SauceOnDemandAuthentication;
import com.saucelabs.saucerest.junit.SauceOnDemandTestWatcher;
import com.saucelabs.saucerest.junit.SessionIdProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import java.net.URL;

/**
 * @author Ross Rowe
 */
public class WebDriverTest implements SessionIdProvider {

    public @Rule
    SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication();

    public @Rule
    SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    public @Rule TestName testName= new TestName();

    private SessionId sessionId;

    private WebDriver driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabillities = DesiredCapabilities.firefox();
        capabillities.setCapability("version", "5");
        capabillities.setCapability("platform", Platform.XP);
        capabillities.setCapability("name", "xxTest : "+testName.getMethodName());

        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                capabillities);
        sessionId=((RemoteWebDriver)driver).getSessionId();
    }

    @Override
    public String getSessionId() {
        return sessionId.toString();
    }

    @Test
    public void basic() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

}
