package com.saucelabs.testng;

/**
 * @author Ross Rowe
 */

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.URL;

import static org.testng.Assert.assertEquals;


/**
 * @author Ross Rowe
 */
@Listeners({SauceOnDemandTestListener.class})
public class WebDriverTest implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {

    public SauceOnDemandAuthentication authentication;

    private WebDriver driver;

    /**
     * If the tests can rely on the username/key to be supplied by environment variables or the existence
     * of a ~/.sauce-ondemand file, then we don't need to specify them as parameters, just create a new instance
     * of {@link SauceOnDemandAuthentication} using the no-arg constructor.
     *
     * @param username
     * @param key
     * @param os
     * @param browser
     * @param browserVersion
     * @param method
     * @throws Exception
     */
    @Parameters({"username", "key", "os", "browser", "browserVersion"})
    @BeforeMethod
    public void setUp(@Optional("") String username,
                      @Optional("") String key,
                      @Optional("") String os,
                      @Optional("") String browser,
                      @Optional("") String browserVersion,
                      Method method) throws Exception {

        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(key)) {
            authentication = new SauceOnDemandAuthentication(username, key);
        } else {
            authentication = new SauceOnDemandAuthentication();
        }

        DesiredCapabilities capabillities = new DesiredCapabilities();
        String browserEnv = System.getenv("SELENIUM_BROWSER");
        String versionEnv = System.getenv("SELENIUM_VERSION");
        String platformEnv = System.getenv("SELENIUM_PLATFORM");

        if (StringUtils.isNotBlank(browserEnv) && StringUtils.isNotBlank(versionEnv) && StringUtils.isNotBlank(platformEnv)) {
            capabillities.setBrowserName(browserEnv);
            capabillities.setCapability("version", versionEnv);
            capabillities.setCapability("platform", Platform.extractFromSysProperty(platformEnv));

        } else if (StringUtils.isNotBlank(browser) && StringUtils.isNotBlank(browserVersion) && StringUtils.isNotBlank(os)) {
            capabillities.setBrowserName(browser);
            capabillities.setCapability("version", browserVersion);
            capabillities.setCapability("platform", Platform.extractFromSysProperty(os));

        } else {
            capabillities = DesiredCapabilities.firefox();
        }
        capabillities.setCapability("name", method.getName());
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                capabillities);

    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public String getSessionId() {
        SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
        return (sessionId == null) ? null : sessionId.toString();
    }

    @Test
    public void webDriver() throws Exception {
        driver.get("http://www.amazon.com/");
        assertEquals(driver.getTitle(), "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more");
    }

    @AfterMethod
    public void tearDown() throws Exception {
        driver.quit();
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public SauceOnDemandAuthentication getAuthentication() {
        return authentication;
    }
}
