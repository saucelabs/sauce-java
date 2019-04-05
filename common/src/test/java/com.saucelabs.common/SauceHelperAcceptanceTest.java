package com.saucelabs.common;

import com.saucelabs.saucerest.DataCenter;
import com.saucelabs.saucerest.SauceREST;
import io.restassured.path.json.JsonPath;
import org.junit.*;
import org.junit.rules.TestName;
import org.openqa.selenium.JavascriptExecutor;
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
    private String SAUCE_REMOTE_URL = "https://ondemand.saucelabs.com/wd/hub";
    private WebDriver driver;
    private SessionId sessionId;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void runBeforeEachTest() throws MalformedURLException {
        ChromeOptions caps = getChromeOptions();
        MutableCapabilities sauceOptions = getMutableCapabilities();
        caps.setCapability("sauce:options", sauceOptions);
        driver = new RemoteWebDriver(new URL(SAUCE_REMOTE_URL), caps);
        sessionId = ((RemoteWebDriver) driver).getSessionId();

        driver.navigate().to("https://www.saucedemo.com");
    }

    @Test
    public void shouldSetTestStatusToPassed(){
        SauceHelper sauceHelper = new SauceHelper(driver);
        sauceHelper.setTestStatus("passed");
        driver.quit();

        String jobInfo = getSauceJobInformation();
        assertAcceptanceTestPassed(jobInfo);
    }

    private void assertAcceptanceTestPassed(String jobInfo) {
        Boolean isTestPassed = checkIfTestPassed(jobInfo);
        Assert.assertTrue(isTestPassed);
    }

    private String getSauceJobInformation() {
        SauceREST sauceRest = new SauceREST(username, accesskey, DataCenter.US);
        return sauceRest.getJobInfo(sessionId.toString());
    }

    @Test
    public void shouldSetTestStatusToPassedWithSeleniumJSExecutor(){
        ((JavascriptExecutor)driver).executeScript("sauce:job-result=passed");
        driver.quit();

        String jobInfo = getSauceJobInformation();
        assertAcceptanceTestPassed(jobInfo);
    }

    @After
    public void afterTest()
    {
        if(driver != null){
            driver.quit();
        }
    }

    private MutableCapabilities getMutableCapabilities() {
        MutableCapabilities sauceOptions;
        sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", username);
        sauceOptions.setCapability("accessKey", accesskey);
        sauceOptions.setCapability("seleniumVersion", "3.141.59");
        sauceOptions.setCapability("name", getTestName());
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
    private Boolean checkIfTestPassed(String jobInfo) {
        Boolean isPassed;
        try
        {
            isPassed = JsonPath.from(jobInfo).getBoolean("passed");
        }
        catch(NullPointerException e)
        {
            isPassed = false;
        }
        return isPassed;
    }
    public String getTestName()
    {
        return this.getClass().getSimpleName() + " " + testName.getMethodName();
    }
    //TODO need a test that doesn't set the test status with JS executor, in which case "passed" should be null
}
