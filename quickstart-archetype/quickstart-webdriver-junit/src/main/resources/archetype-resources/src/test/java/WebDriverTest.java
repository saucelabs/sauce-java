package ${groupId};

import com.saucelabs.common.SauceAuthentication;
import com.saucelabs.common.SauceSessionIdProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * Simple {@link RemoteWebDriver} test that demonstrates how to run your Selenium tests with
 * <a href="https://saucelabs.com/ondemand">Sauce Labs</a>.
 *
 * @author Ross Rowe
 */
public class WebDriverTest {

    private WebDriver driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("version", "17");
        capabilities.setCapability("platform", Platform.XP);
        this.driver = new RemoteWebDriver(
                new URL("http://${userName}:${accessKey}@ondemand.saucelabs.com:80/wd/hub"),
                capabilities);
    }

    @Test
    public void webDriver() throws Exception {
        driver.get("https://saucelabs.com/test/guinea-pig");
        assertEquals("I am a page title - Sauce Labs", driver.getTitle());
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

}
