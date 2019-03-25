package com.saucelabs.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.saucelabs.saucerest.DataCenter;
import com.saucelabs.saucerest.SauceREST;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import java.net.MalformedURLException;
import java.net.URL;


public class SauceHelperAcceptanceTest
{
    private String username = System.getenv("SAUCE_USERNAME");
    private String accesskey = System.getenv("SAUCE_ACCESS_KEY");
    String SAUCE_REMOTE_URL = "https://ondemand.saucelabs.com/wd/hub";
    private WebDriver driver;

    @Test
    public void shouldSetTestStatusToPassed() throws MalformedURLException {
        ChromeOptions caps = getChromeOptions();
        MutableCapabilities sauceOptions = getMutableCapabilities();

        caps.setCapability("sauce:options", sauceOptions);
        driver = new RemoteWebDriver(new URL(SAUCE_REMOTE_URL), caps);
        SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();

        driver.navigate().to("https://www.saucedemo.com");
        SauceHelper sauceHelper = new SauceHelper(driver);
        sauceHelper.setTestStatus("passed");

        SauceREST sauceRest = new SauceREST(username, accesskey, DataCenter.US);
        String job = sauceRest.getJobInfo(sessionId.toString());
        JsonElement jsonArray = new JsonParser().parse(job);
        Assert.assertFalse("The job info request returned Null: " + sessionId.toString(), jsonArray.isJsonNull());
        Assert.assertFalse("The 'passed' element returned Null", ((JsonObject) jsonArray).get("passed").isJsonNull());
        Assert.assertTrue(((JsonObject) jsonArray).get("passed").getAsBoolean());
    }
    @After
    public void afterTest()
    {
        driver.quit();
    }

    private MutableCapabilities getMutableCapabilities() {
        MutableCapabilities sauceOptions;
        sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", username);
        sauceOptions.setCapability("accessKey", accesskey);
        sauceOptions.setCapability("seleniumVersion", "3.141.59");
        sauceOptions.setCapability("name", "shouldPassTest");
        return sauceOptions;
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions caps;
        caps = new ChromeOptions();
        caps.setCapability("version", "72.0");
        caps.setCapability("platform", "Windows 10");
        caps.setExperimentalOption("w3c", true);
        return caps;
    }
    //TODO need a test that doesn't set the test status with JS executor, in which case "passed" should be null
    //TODO need a test that makes sure that the JS executor from Selenium works as expected
//    @Test
//    public void shouldSetTestToPassUsingJSExecutor() throws MalformedURLException {
//        ChromeOptions caps = new ChromeOptions();
//        caps.setCapability("version", "72.0");
//        caps.setCapability("platform", "Windows 10");
//        caps.setExperimentalOption("w3c", true);
//
//        MutableCapabilities sauceOptions = new MutableCapabilities();
//        sauceOptions.setCapability("username", username);
//        sauceOptions.setCapability("accessKey", accesskey);
//        sauceOptions.setCapability("seleniumVersion", "3.141.59");
//        sauceOptions.setCapability("name", "shouldPassTest");
//
//        caps.setCapability("sauce:options", sauceOptions);
//        String SAUCE_REMOTE_URL = "https://ondemand.saucelabs.com/wd/hub";
//        WebDriver driver = new RemoteWebDriver(new URL(SAUCE_REMOTE_URL), caps);
//        SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
//        driver.navigate().to("https://www.saucedemo.com");
//        SauceHelper sauceHelper = new SauceHelper(driver);
//
//        //((JavascriptExecutor)driver).executeScript("sauce:job-result=passed");
//        //sauceHelper.setTestStatus("passed");
//        SauceREST sauceRest = new SauceREST(username, accesskey, DataCenter.US);
//        String job = sauceRest.getJobInfo(sessionId.toString());
////        Gson gson = new Gson();
////        String json = gson.toJson(job);
////        String[] jsonArr = gson.fromJson(json, String[].class);
//        //Assert.assertEquals();
//        driver.quit();
//    }
}
