package com.saucelabs.common;

import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;


public class SauceHelperAcceptanceTest
{
    private String username = System.getenv("SAUCE_USERNAME");
    private String accesskey = System.getenv("SAUCE_ACCESS_KEY");
    @Test
    public void shouldPassTest() throws MalformedURLException {
        ChromeOptions caps = new ChromeOptions();
        caps.setCapability("version", "72.0");
        caps.setCapability("platform", "Windows 10");
        caps.setExperimentalOption("w3c", true);

        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", username);
        sauceOptions.setCapability("accessKey", accesskey);
        sauceOptions.setCapability("seleniumVersion", "3.141.59");
        sauceOptions.setCapability("name", "shouldPassTest");

        caps.setCapability("sauce:options", sauceOptions);
        String SAUCE_REMOTE_URL = "https://ondemand.saucelabs.com/wd/hub";
        WebDriver driver = new RemoteWebDriver(new URL(SAUCE_REMOTE_URL), caps);
        driver.navigate().to("https://www.saucedemo.com");
        SauceHelper sauceHelper = new SauceHelper(driver);

        //((JavascriptExecutor)driver).executeScript("sauce:job-result=passed");
        //sauceHelper.setTestStatus("passed");
        driver.quit();
    }
}
