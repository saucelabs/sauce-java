package com.saucelabs.example;

import com.saucelabs.common.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

import static org.junit.Assert.assertTrue;

/**
 * @author Ross Rowe
 */
public class VerifyExampleIT {

    private WebDriver driver;

    @Before
    public void setUp() throws Exception {

        //construct the DesiredCapabilities using the environment variables set by the Sauce CI plugin
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("version", Utils.readPropertyOrEnv("SELENIUM_VERSION", "4"));
        capabilities.setCapability("platform", Utils.readPropertyOrEnv("SELENIUM_PLATFORM", "XP"));
        capabilities.setCapability("browserName", Utils.readPropertyOrEnv("SELENIUM_BROWSER", "firefox"));
        String username = Utils.readPropertyOrEnv("SAUCE_USER_NAME", "rossco_9_9");
        String accessKey = Utils.readPropertyOrEnv("SAUCE_API_KEY", "5753db9d-792c-4635-bd2d-7c4eec823c63");
        this.driver = new RemoteWebDriver(new URL("http://" + username + ":" + accessKey + "@localhost:4445/wd/hub"),
                capabilities);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    /**
     * Start a web server locally, and have Sauce OnDemand connect to the local server.
     */
    @Test
    public void fullRun() throws Exception {
        String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
        System.out.println("SauceOnDemandSessionID=" + sessionId);
        driver.get("http://localhost:8080/cargocpc/");
        // if the server really hit our Jetty, we should see the same title that includes the secret code.
        assertTrue(driver.getPageSource().contains("Cargo Ping Component used to verify if the container is started."));
    }
}
