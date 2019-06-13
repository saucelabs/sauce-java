package com.saucelabs.remotedriver;

import com.saucelabs.common.SauceApi;
import com.saucelabs.saucerest.SauceREST;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;

public class SauceSession {
	static String sauceSeleniumServer = "https://ondemand.saucelabs.com/wd/hub";

	static String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
	static String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");

	String BUILD_TAG = System.getenv("BUILD_TAG");

    public SauceApi test;

    //todo there is some weird bug when this is set to Linux, the session can't be started
	String operatingSystem = "Windows 10";
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
    private WebDriver webDriver;
    private SafariOptions safariOptions;
    private EdgeOptions edgeOptions;
    private InternetExplorerOptions ieOptions;

    private String sessionId;
    private SauceREST api;

    public SauceSession() {
        capabilities = new MutableCapabilities();
        remoteDriverManager = new ConcreteRemoteDriverManager();
    }

    public SauceSession(RemoteDriverInterface remoteManager) {
        remoteDriverManager = remoteManager;
        capabilities = new MutableCapabilities();
    }

    public SauceSession(String testName)
    {
        capabilities = new MutableCapabilities();
        remoteDriverManager = new ConcreteRemoteDriverManager();
        this.testName = testName;
    }

    public SauceSession start() throws MalformedURLException
	{
        seleniumServer = getSeleniumServer();
        capabilities = getCapabilities();
        webDriver = remoteDriverManager.getRemoteWebDriver(seleniumServer, capabilities);
        sessionId = ((RemoteWebDriver) webDriver).getSessionId().toString();
        test = new SauceApi(webDriver);
        api = new SauceREST(SAUCE_USERNAME, SAUCE_ACCESS_KEY);

        return this;
	}
    public String getSeleniumServer() {
        if (seleniumServer == null)
        {
            seleniumServer = sauceSeleniumServer;
        }
        return seleniumServer;
    }
    public MutableCapabilities getCapabilities() {
        sauceOptions = getSauceOptions();
        setBrowserOptions(browserName);

        capabilities.setCapability(sauceOptionsTag, sauceOptions);
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browserName);
        capabilities.setCapability(CapabilityType.PLATFORM_NAME, operatingSystem);
        capabilities.setCapability(CapabilityType.BROWSER_VERSION, browserVersion);

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

            if (BUILD_TAG != null)
            {
                sauceOptions.setCapability("build", BUILD_TAG);
            }
        }

        return sauceOptions;
    }
    //TODO this needs to be moved to it's own class because it keeps changing
    public void setBrowserOptions(String browserName)
    {
        if (browserName.equalsIgnoreCase("Chrome"))
        {
            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        }
        else if (browserName.equalsIgnoreCase("Firefox"))
        {
            capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
        }
        else if(browserName.equalsIgnoreCase("Safari"))
        {
            safariOptions = new SafariOptions();
            capabilities.setCapability(SafariOptions.CAPABILITY, safariOptions);
        }
        else if(browserName.equalsIgnoreCase("Edge"))
        {
            capabilities.setCapability("Edge", edgeOptions);
        }
        else if(browserName.equalsIgnoreCase("IE"))
        {
            capabilities.setCapability("se:ieOptions", ieOptions);
        }
        else {
            //TODO why is this so annoying??
            //throw new NoSuchBrowserExistsException();
        }
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

    public RemoteDriverInterface getDriverManager() {
        return remoteDriverManager;
    }


    public MutableCapabilities getSauceOptionsCapability(){
        return ((MutableCapabilities) capabilities.getCapability(sauceOptionsTag));
    }



    public WebDriver getDriver() {
        return webDriver;
    }

    //TODO How do we want to handle this?
    //1. withMacOsMojave(OperatingSystem.MacOs1014), aka, force the user to pass in a mac version
    //2. throw an exception for withMacOsMojave() used without withMac();
    //3. this is the method I chose below: withMacOsMojave(String browserVersion)
    public SauceSession withMacOsMojave() {
        operatingSystem = "macOS 10.14";
        browserName = "Safari";
        return this;
    }
    public SauceSession withMacOsHighSierra()
    {
        this.operatingSystem = "macOS 10.13";
        browserName = "Safari";
        return this;
    }
    public SauceSession withBrowserVersion(String browserVersion){
        this.browserVersion = browserVersion;
        return this;
    }

    public SauceSession withEdge() {
        this.browserName = "Edge";
        edgeOptions = new EdgeOptions();
        return this;
    }

    public SauceSession withIE() {
        this.browserName = "IE";
        ieOptions = new InternetExplorerOptions();
        return this;
    }

    public SauceSession withPlatform(String operatingSystem) {
        this.operatingSystem = operatingSystem;
        return this;
    }


    public SauceSession withBrowser(String browserName, String browserVersion)
    {
        this.browserName = browserName;
        this.browserVersion = browserVersion;
        setBrowserOptions(browserName);

        return this;
    }

    public SauceSession withBrowser(String browserName)
    {
        this.browserName = browserName;
        setBrowserOptions(browserName);

        return this;
    }

    public SauceSession withTestName(String testName)
    {
        this.testName = testName;
        return this;
    }

    public SauceSession withBuildName(String buildName)
    {
        this.testName = testName;
        return this;
    }

    public void stop()
    {
        if(webDriver != null)
            webDriver.quit();
    }

    public void passed()
    {
        if (webDriver != null) { test.setTestStatus("passed"); }
        else {
            api.jobPassed(sessionId);
        }
    }

    public void failed()
    {
        if (webDriver != null)  { test.setTestStatus("failed"); }
        else {
            api.jobFailed(sessionId);
        }
    }

    public void setTestName(String testName)
    {
        this.testName = testName;
    }

    public void setBuildTag(String buildTag)
    {
        this.BUILD_TAG = buildTag;
    }

    public void setBuild(String build)
    {
        test.setBuildName(build);
    }
}
