package com.saucelabs.testng;

/**
 * @author Russ Rowe / Mehmet Gerceker
 */

import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.*;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;


/**
 * Simple TestNG test which demonstrates being instantiated via a DataProvider in order to supply multiple browser combinations.
 *
 * @author Russ Rowe / Mehmet Gerceker
 */
@Listeners({SauceOnDemandTestListener.class})
public class SampleSauceTest extends SauceTestBase {

    /**
     * Simple hard-coded DataProvider that explicitly sets the browser combinations to be used.
     *
     * @param testMethod
     * @return
     */
    @DataProvider(name = "hardCodedBrowsers", parallel = true)
    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
        return new Object[][]{
            new Object[]{"chrome", "43", "Windows 2012"},
            new Object[]{"firefox", "42", "Linux"},
        };
    }
    @Test(dataProvider = "hardCodedBrowsers")
    public void webDriver(String browser, String version, String os) throws Exception {
        WebDriver driver = createDriver(browser, version, os);
        logger.log(Level.INFO, "Running test using " + browser + " " + version + " " + os);
        driver.get("https://saucelabs.com/test/guinea-pig");
        assertEquals(driver.getTitle(), "I am a page title - Sauce Labs");
        driver.quit();
    }

}
