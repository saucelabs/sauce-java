package demo;

import com.saucelabs.remotedriver.SafariVersion;
import com.saucelabs.remotedriver.SauceSession;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;

public class TestWithSauceSession
{
	@Rule
	public TestName test = new TestName();

	@Test
	public void withSauceSessionDefaults() throws MalformedURLException
	{
		SauceSession session = new SauceSession().start();
		WebDriver driver = session.getDriver();

		driver.get("https://saucelabs.com");
		assertThat(driver.getTitle().contains("Sauce Labs"));

		session.passed();
		session.stop();
	}

	@Test
	public void withEdgeOnWindows() throws MalformedURLException
	{
		SauceSession session = new SauceSession()
				.withPlatform("Windows 10")
				.withBrowser("MicrosoftEdge")
				.withTestName(getTestName())
				.start();

		WebDriver driver = session.getDriver();

		driver.get("https://saucelabs.com");
		assertThat(driver.getTitle().contains("Google"));

		session.failed();
		session.stop();
	}

	@Test
	public void withSafariOnMac() throws MalformedURLException
	{

		SauceSession session = new SauceSession(getTestName())
				.withMacOsHighSierra()
				.withBrowserVersion(SafariVersion.elevenDotOne)
				.start();

		session.setTestName(getTestName());

		WebDriver driver = session.getDriver();

		driver.get("https://saucelabs.com");
		assertThat(driver.getTitle().contains("Sauce Labs"));

		session.passed();
		session.stop();
	}

	public String getTestName()
	{
		return this.getClass().getSimpleName() + " " + test.getMethodName();
	}
}
