package com.saucelabs.testng;

/**
 * @author Ross Rowe
 */

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.testng.Assert.assertEquals;


/**
 * Simple TestNG test which demonstrates being instantiated via a DataProvider in order to supply multiple browser combinations.
 *
 * @author Ross Rowe
 */
public class MultipleBrowserTest {

    private static final Logger logger = Logger.getLogger(MultipleBrowserTest.class.getName());
    public static final String USER_NAME = "your_sauce_username";
    public static final String ACCESS_KEY = "your_sauce_accesskey";

    /**
     * Simple hard-coded DataProvider that explicitly sets the browser combinations to be used.
     *
     * @param testMethod
     * @return
     */
    @DataProvider(name = "hardCodedBrowsers", parallel = true)
    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"firefox", "17", "Windows 2003", USER_NAME, ACCESS_KEY},
                new Object[]{"firefox", "3.6", "Windows 2003", USER_NAME, ACCESS_KEY},
                new Object[]{"safari", "5", "windows 7", USER_NAME, ACCESS_KEY},
                new Object[]{"chrome", "", "Windows 2003", USER_NAME, ACCESS_KEY},
                new Object[]{"opera", "12", "Linux", USER_NAME, ACCESS_KEY},
                new Object[]{"firefox", "8", "Linux", USER_NAME, ACCESS_KEY}
        };
    }

    private WebDriver createDriver(String browser, String version, String os, String username, String key) throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser);
        capabilities.setCapability("version", version);
        capabilities.setCapability("platform", Platform.extractFromSysProperty(os));
        return new RemoteWebDriver(
                new URL("http://" + username + ":" + key + "@ondemand.saucelabs.com:80/wd/hub"),
                capabilities);
    }

    @Test(dataProvider = "hardCodedBrowsers")
    public void webDriver(String browser, String version, String os, String username, String key) throws Exception {
        WebDriver driver = createDriver(browser, version, os, username, key);
        logger.log(Level.INFO, "Running test using " + browser + " " + version + " " + os);
        driver.get("http://www.amazon.com/");
        assertEquals(driver.getTitle(), "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more");
        driver.quit();
    }

}
