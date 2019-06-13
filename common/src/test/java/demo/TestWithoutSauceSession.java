package demo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class TestWithoutSauceSession
{
	WebDriver driver;

	@Rule
	public TestName test = new TestName();

	Boolean testStatus;
	Boolean PASSED = true;
	Boolean FAILED = false;

	@Before
	public void setup() throws MalformedURLException
	{
		String SAUCE_URL = "https://ondemand.saucelabs.com/wd/hub";
		URL url = new URL(SAUCE_URL);

		String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
		String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
		String BUILD_TAG = System.getenv("BUILD_TAG");

		String SELENIUM_PLATFORM = "Windows 10";
		String SELENIUM_BROWSER = "Chrome";
		String SELENIUM_VERSION = "latest";

		String testName = getTestName();

		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("w3c", true);

		MutableCapabilities sauceOptions = new MutableCapabilities();
		sauceOptions.setCapability("username", SAUCE_USERNAME);
		sauceOptions.setCapability("accessKey", SAUCE_ACCESS_KEY);
		sauceOptions.setCapability("seleniumVersion", "3.141.59");

		if (testName != null)
		{
			sauceOptions.setCapability("name", testName);
		}

		if (BUILD_TAG != null)
		{
			sauceOptions.setCapability("build", BUILD_TAG);
		}

		MutableCapabilities capabilities = new MutableCapabilities();
		capabilities.setCapability(CapabilityType.PLATFORM_NAME, SELENIUM_PLATFORM);
		capabilities.setCapability(CapabilityType.BROWSER_VERSION, SELENIUM_VERSION);
		capabilities.setCapability(CapabilityType.BROWSER_NAME, SELENIUM_BROWSER);
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		capabilities.setCapability("sauce:options", sauceOptions);

		driver = new RemoteWebDriver(url, capabilities);
	}

	@Test
	public void openSauceLabsHomePage()
	{
		driver.get("https://saucelabs.com");
		String title = driver.getTitle();

		try {
			assertThat(title.contains("Sauce Labs"));
			testStatus = PASSED;
		}
		catch (AssertionError e)
		{
			e.printStackTrace();
			testStatus = FAILED;
		}
	}

	@After
	public void teardown()
	{
		((RemoteWebDriver)driver).executeScript("sauce:job-result=" + (testStatus ? "passed" : "failed"));

		driver.quit();
	}

	public String getTestName()
	{
		return this.getClass().getSimpleName() + " " + test.getMethodName();
	}
}
