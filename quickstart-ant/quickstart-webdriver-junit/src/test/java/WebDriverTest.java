import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.text.MessageFormat;

import static org.junit.Assert.assertEquals;

/**
 * Simple {@link org.openqa.selenium.remote.RemoteWebDriver} test that demonstrates how to run your Selenium tests with <a href="http://saucelabs.com/ondemand">Sauce OnDemand</a>.
 * *
 * @author Ross Rowe
 */
public class WebDriverTest {

    private WebDriver driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = DesiredCapabilities.iphone();
        capabilities.setCapability("version", "5.0");
        capabilities.setCapability("platform", Platform.MAC);
        String username = System.getenv("SAUCE_USER_NAME");
        String accessKey = System.getenv("SAUCE_ACCESS_KEY");
        this.driver = new RemoteWebDriver(
                new URL(MessageFormat.format("http://{0}:{1}@ondemand.saucelabs.com:80/wd/hub", username, accessKey)),
                capabilities);
    }

    @Test
    public void webDriver() throws Exception {
        driver.get("http://www.amazon.com/");
        assertEquals("Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more", driver.getTitle());
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

}
