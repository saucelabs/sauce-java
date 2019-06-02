package com.saucelabs.common.com.saucelabs.common.acceptance;

import com.saucelabs.common.InvalidTestStatusException;
import com.saucelabs.common.SauceHelper;
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
        MutableCapabilities sauceOptions = getMutableCapabilities();
        ChromeOptions chromeOpts = getChromeOptions(sauceOptions);

        driver = new RemoteWebDriver(new URL(SAUCE_REMOTE_URL), chromeOpts);
        sessionId = ((RemoteWebDriver) driver).getSessionId();
        driver.navigate().to("https://www.saucedemo.com");
    }

    @Test
    public void shouldSetTestStatusToPassed() throws InvalidTestStatusException {
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
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", username);
        sauceOptions.setCapability("accessKey", accesskey);
        sauceOptions.setCapability("seleniumVersion", "3.141.59");
        sauceOptions.setCapability("name", getTestName());
        return sauceOptions;
    }

    private ChromeOptions getChromeOptions(MutableCapabilities sauceOptions) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("w3c", true);
        chromeOptions.setCapability("browserVersion", "70.0");
        chromeOptions.setCapability("platformName", "windows 10");
        chromeOptions.setCapability("sauce:options", sauceOptions);

        return chromeOptions;
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
