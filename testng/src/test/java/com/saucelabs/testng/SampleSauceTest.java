package com.saucelabs.testng;

/**
 * @author Russ Rowe / Mehmet Gerceker
 */

import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.logging.Level;

import static org.testng.Assert.assertEquals;


/**
 * Simple TestNG test which demonstrates being instantiated via a DataProvider in order to supply multiple browser combinations.
 *
 * @author Russ Rowe / Mehmet Gerceker
 */
@Listeners({SauceTestListener.class})
public class SampleSauceTest extends SauceTestBase {

    /**
     * Simple hard-coded DataProvider that explicitly sets the browser combinations to be used.
     *
     * @param testMethod Test method consuming the data
     * @return 2D Array of data provider (2D String Array)
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
