package com.saucelabs.testng;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.thoughtworks.selenium.CommandProcessor;
import com.thoughtworks.selenium.DefaultSelenium;
import org.apache.commons.lang.StringUtils;
import org.testng.annotations.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.testng.Assert.assertEquals;


/**
 *
 * @author Ross Rowe
 */
@Listeners({SauceOnDemandTestListener.class})
public class SeleniumRCWithHelperTest implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {

    public SauceOnDemandAuthentication authentication;

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
    public void setUp(@Optional("") String username,
                      @Optional() String key,
                      @Optional("mac") String os,
                      @Optional("iphone") String browser,
                      @Optional("5.0") String browserVersion,
                      Method method) throws Exception {

        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(key)) {
           authentication = new SauceOnDemandAuthentication(username, key);
        } else {
           authentication = new SauceOnDemandAuthentication();
        }

        this.selenium = new DefaultSelenium(
                "ondemand.saucelabs.com",
                80,
                "{\"username\": \"" + username + "\"," +
                        "\"access-key\": \"" + key + "\"," +
                        "\"os\": \"" + os + "\"," +
                        "\"browser\": \"" + browser + "\"," +
                        "\"browser-version\": \"" + browserVersion + "\"," +
                        "\"name\": \"Testing Selenium 1 with Java on Sauce\"}",
                "http://saucelabs.com/");
        selenium.start();
    }

    @Override
    public String getSessionId() {
        try {
            Field commandProcessorField = DefaultSelenium.class.getDeclaredField("commandProcessor");
            commandProcessorField.setAccessible(true);
            CommandProcessor commandProcessor = (CommandProcessor) commandProcessorField.get(selenium);
            Field f = commandProcessor.getClass().getDeclaredField("sessionId");
            f.setAccessible(true);
            Object id = f.get(commandProcessor);
            if (id != null) {
                return id.toString();
            }
        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        }
        return null;

    }

    @Test
    public void seleniumRCWithHelper() throws Exception {
        this.selenium.open("http://www.amazon.com");
        assertEquals("Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more", this.selenium.getTitle());
    }

    @AfterMethod
    public void tearDown() throws Exception {
        this.selenium.stop();
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public SauceOnDemandAuthentication getAuthentication() {
        return authentication;
    }
}
