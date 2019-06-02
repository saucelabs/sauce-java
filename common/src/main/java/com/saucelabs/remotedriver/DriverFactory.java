package com.saucelabs.remotedriver;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory
{
	static String sauceSeleniumServer = "https://ondemand.saucelabs.com/wd/hub";
	static String localSeleniumServer = "http://localhost/wd/hub";

	static String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
	static String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");

	String platformName = "Windows 10";
	String browserName = "Chrome";
	String testName;
	Boolean useSauce = true;

	String seleniumServer;

	ChromeOptions chromeOptions;
	FirefoxOptions firefoxOptions;
	MutableCapabilities sauceOptions;
	String browserVersion = "latest";

	MutableCapabilities capabilities;

	public WebDriver getInstance() throws MalformedURLException
	{
		if (seleniumServer == null)
		{
			seleniumServer = sauceSeleniumServer;
		}

		capabilities = new MutableCapabilities();

		if (useSauce)
		{
		    seleniumServer = sauceSeleniumServer;

			sauceOptions = new MutableCapabilities();
			sauceOptions.setCapability("username", SAUCE_USERNAME);
			sauceOptions.setCapability("accessKey", SAUCE_ACCESS_KEY);
			sauceOptions.setCapability("seleniumVersion", "3.141.59");

		    if (testName != null)
			{
				sauceOptions.setCapability("name", testName);
			}

			capabilities.setCapability("sauce:options", sauceOptions);
		}

		if (browserName.equalsIgnoreCase("Chrome"))
		{
			withChrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		}
		else if (browserName.equalsIgnoreCase("Firefox"))
		{
			withFirefox();
			capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
		}

		capabilities.setCapability("browserName", browserName);
		capabilities.setCapability("platformName", platformName);
		capabilities.setCapability("browserVersion", browserVersion);

		return new RemoteWebDriver(new URL(seleniumServer), capabilities);
	}

	public DriverFactory withSeleniumServer(String url)
	{
		if (url != null)
		{
			seleniumServer = url;
		}

		return this;
	}

	public DriverFactory withSauceLabs(Boolean useSauce)
	{
		this.useSauce = useSauce;

		return this;
	}

	public DriverFactory withChrome()
	{
		chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("w3c", true);

		browserName = "Chrome";

		return this;
	}

	public  DriverFactory withFirefox()
	{
		firefoxOptions = new FirefoxOptions();
		browserName = "Firefox";

		return this;
	}

	public DriverFactory withPlatform(String platformName)
	{
		this.platformName = platformName;

		return this;
	}

	public DriverFactory withTestName(String testName)
	{
		this.testName = testName;

		return this;
	}
}
