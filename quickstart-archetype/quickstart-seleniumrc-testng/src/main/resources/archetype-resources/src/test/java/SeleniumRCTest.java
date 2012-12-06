import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.thoughtworks.selenium.DefaultSelenium;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.URL;

import static org.testng.Assert.assertEquals;


/**
 *
 * @author Ross Rowe
 */

public class SeleniumRCTest {

    private DefaultSelenium selenium;

    /**
     * If the tests can rely on the username/key to be supplied by environment variables or the existence
     * of a ~/.sauce-ondemand file, then we don't need to specify them as parameters, just create a new instance
     * of {@link SauceOnDemandAuthentication} using the no-arg constructor.
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
    public void setUp(@Optional("${sauceUserName}") String username,
                      @Optional("${sauceAccessKey}") String key,
                      @Optional("Windows 2003") String os,
                      @Optional("firefox") String browser,
                      @Optional("17") String browserVersion,
                      Method method) throws Exception {


        this.selenium = new DefaultSelenium(
                "ondemand.saucelabs.com",
                80,
                "{\"username\": \"${sauceUserName}\"," +
                        "\"access-key\": \"${sauceAccessKey}\"," +
                        "\"os\": \"" + os + "\"," +
                        "\"browser\": \"" + browser + "\"," +
                        "\"browser-version\": \"" + browserVersion + "\"," +
                        "\"name\": \"Testing Selenium 1 with Java on Sauce\"}",
                "http://saucelabs.com/");
        selenium.start();
        this.selenium = selenium;

    }

    @Test
    public void seleniumRC() throws Exception {
        this.selenium.open("http://www.amazon.com");
        assertEquals("Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more", this.selenium.getTitle());
    }

    @AfterMethod
    public void tearDown() throws Exception {
        this.selenium.stop();
    }
}
