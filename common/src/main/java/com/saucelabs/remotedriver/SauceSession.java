package com.saucelabs.remotedriver;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class SauceSession {
	static String sauceSeleniumServer = "https://ondemand.saucelabs.com/wd/hub";

	static String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
	static String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");

	//todo there is some weird bug when this is set to Linux, the session can't be started
	String platformName = "Windows 10";
	String browserName = "Chrome";
	String testName;
	Boolean useSauce = true;
    String sauceOptionsTag = "sauce:options";

	public String seleniumServer;

	ChromeOptions chromeOptions;
	FirefoxOptions firefoxOptions;
	MutableCapabilities sauceOptions;
	String browserVersion = "latest";

	public MutableCapabilities capabilities;
    private RemoteDriverInterface remoteDriverManager;
    private MutableCapabilities browserOptions;
    private WebDriver webDriver;

    public SauceSession(){
        capabilities = new MutableCapabilities();
        remoteDriverManager = new ConcreteRemoteDriverManager();
    }

    public SauceSession(RemoteDriverInterface remoteManager) {
        remoteDriverManager = remoteManager;
        capabilities = new MutableCapabilities();
    }

    public RemoteWebDriver getInstance() throws MalformedURLException
    {
        seleniumServer = getSeleniumServer();
        capabilities = getCapabilities();

        return new RemoteWebDriver(new URL(seleniumServer), capabilities);
    }

    public WebDriver getInstanceOld() throws MalformedURLException
	{

        seleniumServer = getSeleniumServer();
        capabilities = getCapabilities();

		return remoteDriverManager.getRemoteWebDriver(seleniumServer, capabilities);
	}

    public String getSeleniumServer() {
        if (seleniumServer == null)
        {
            seleniumServer = sauceSeleniumServer;
        }
        return seleniumServer;
    }


    public SauceSession withSeleniumServer(String url)
	{
		if (url != null)
		{
			seleniumServer = url;
		}

		return this;
	}

	public SauceSession withSauceLabs(Boolean useSauce)
	{
		this.useSauce = useSauce;

		return this;
	}

	public SauceSession withChrome()
	{
	    chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("w3c", true);
		browserName = "Chrome";
		return this;
	}

	public SauceSession withFirefox()
	{
		firefoxOptions = new FirefoxOptions();
		browserName = "Firefox";

		return this;
	}

	public SauceSession withPlatform(String platformName)
	{
		this.platformName = platformName;

		return this;
	}

	public SauceSession withTestName(String testName)
	{
		this.testName = testName;

		return this;
	}

    public RemoteDriverInterface getDriverManager() {
        return remoteDriverManager;
    }

    public MutableCapabilities getCapabilities() {
        sauceOptions = getSauceOptions();
        browserOptions = getBrowserOptions(browserName);

        capabilities.setCapability(sauceOptionsTag, sauceOptions);
        capabilities.setCapability("browserName", browserName);
        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("browserVersion", browserVersion);

        return capabilities;
    }

    public MutableCapabilities getSauceOptions()
    {
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
        }

        return sauceOptions;
    }
    public MutableCapabilities getBrowserOptions(String browserName)
    {
        //TODO what's the deal with this? just returning an instantiated object?
        browserOptions = new MutableCapabilities();

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
        else {
            //...TODO: set other other browsers capabilities
        }

        return browserOptions;
    }

    public MutableCapabilities getSauceOptionsCapability(){
        return ((MutableCapabilities) capabilities.getCapability(sauceOptionsTag));
    }

    public SauceSession start() throws MalformedURLException {
        webDriver = getInstance();
        return this;
    }

    public WebDriver getDriver() {
        return webDriver;
    }
}
