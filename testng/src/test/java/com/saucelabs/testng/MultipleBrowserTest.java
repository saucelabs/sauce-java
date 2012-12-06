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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.assertEquals;


/**
 * Simple TestNG test which demonstrates being instantiated via a DataProvider in order to supply multiple browser combinations.
 *
 * @author Ross Rowe
 */
@Listeners({SauceOnDemandTestListener.class})
public class MultipleBrowserTest implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {

    public SauceOnDemandAuthentication authentication;

    private WebDriver driver;
    private String browser;
    private String version;
    private String os;

    /**
     * Simple hard-coded DataProvider that explicitly sets the browser combinations to be used.
     * @param testMethod
     * @return
     */
    @DataProvider(name = "hardCodedBrowsers")
    public static Iterator<Object[]> sauceBrowserDataProvider(Method testMethod) {
        List<Object[]> browsers = new ArrayList<Object[]>();
        browsers.add(new Object[]{"firefox", "17", "Windows 2008"});
        return browsers.iterator();
    }

    /**
     *
     * @param browser
     * @param version
     * @param os
     */
    //Uncomment this factory to use the hardCodedBrowsers Data Provider
//    @Factory(dataProvider = "hardCodedBrowsers")
    //This factory uses the SauceBrowserDataProvider, which parses the SAUCE_ONDEMAND_BROWSERS environment variable
    @Factory(dataProviderClass=SauceBrowserDataProvider.class, dataProvider = "sauceBrowserDataProvider", parameters="browserJson")
    public MultipleBrowserTest(String browser, String version, String os) {
        this.browser = browser;
        this.version = version;
        this.os = os;

    }


    @Parameters({"username", "key"})
    @BeforeMethod
    public void setUp(@Optional("") String username,
                      @Optional("") String key,
                      Method method) throws Exception {

        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(key)) {
            authentication = new SauceOnDemandAuthentication(username, key);
        } else {
            authentication = new SauceOnDemandAuthentication();
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser);
        capabilities.setCapability("version", version);
        capabilities.setCapability("os", Platform.extractFromSysProperty(os));

        capabilities.setCapability("name", method.getName());
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                capabilities);

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
