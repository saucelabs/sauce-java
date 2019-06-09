package com.saucelabs.remotedriver;

import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DriverFactoryTest {

    private DriverFactory factory;

    @Test
    public void defaultConstructor_instantiated_returnsFactory()
    {
        factory = new DriverFactory();
        assertNotNull(factory);
    }

    @Test
    public void seleniumServer_notSet_returnsNull()
    {
        //TODO is this okay to be like a property,
        //or should it be a getSeleniumServer()
        factory = new DriverFactory();
        assertNull(factory.seleniumServer);
    }

    @Test
    public void defaultConstructor_instantiated_setsConcreteDriverManager()
    {
        factory = new DriverFactory();
        assertThat(factory.getDriverManager(), instanceOf(ConcreteRemoteDriverManager.class));
    }

    @Test
    public void getInstance_serverNotSet_setsSauceSeleniumServer() throws MalformedURLException {
        factory = getFactoryWithStubRemoteManager();
        factory.getInstance();
        String expectedServer = "https://ondemand.saucelabs.com/wd/hub";
        assertEquals(expectedServer, factory.seleniumServer);
    }
    @Test
    public void getInstance_default_setsCapabilitiesToChrome() throws MalformedURLException {
        factory = getFactoryWithStubRemoteManager();

        factory.getInstance();

        String actualBrowser = factory.capabilities.getBrowserName();
        assertThat(actualBrowser, IsEqualIgnoringCase.equalToIgnoringCase("chrome"));
    }
    @Test
    public void getInstance_default_setsCapabilityToLinux() throws MalformedURLException {
        factory = getFactoryWithStubRemoteManager();

        factory.getInstance();

        String actualBrowser = factory.capabilities.getPlatform().name();
        assertThat(actualBrowser, IsEqualIgnoringCase.equalToIgnoringCase("win10"));
    }

    private DriverFactory getFactoryWithStubRemoteManager() {
        RemoteDriverInterface stubRemoteManager = mock(RemoteDriverInterface.class);
        return new DriverFactory(stubRemoteManager);
    }
    @Test
    public void sauceOptions_defaultConfiguration_latestBrowserVersion()
    {
        MutableCapabilities caps = new DriverFactory().getCapabilities();
        String actualOperatingSystem = caps.getCapability("browserVersion").toString();
        assertThat(actualOperatingSystem, IsEqualIgnoringCase.equalToIgnoringCase("latest"));
    }
    @Test
    public void sauceOptions_defaultConfiguration_setsSauceOptions()
    {
        factory = new DriverFactory();
        factory.getCapabilities();
        boolean hasAccessKey = factory.getSauceOptionsCapability().asMap().containsKey("accessKey");
        assertTrue(hasAccessKey);
    }

    @Test
    @Ignore("Having trouble testing. How do we actually validate that what came back is" +
        "what we want. Or is checking the set properties enough")
    public void getInstance_default_returnsChromeBad() throws MalformedURLException {
        RemoteDriverInterface stubRemoteManager = mock(RemoteDriverInterface.class);
        Capabilities stubCaps = mock(Capabilities.class);
        RemoteWebDriver remoteWebDriver = new RemoteWebDriver(stubCaps);
        when(stubRemoteManager.getRemoteWebDriver(anyString(),anyObject())).thenReturn(remoteWebDriver);
        factory = new DriverFactory(stubRemoteManager);

        WebDriver driver = factory.getInstance();

        String actualBrowser = (((RemoteWebDriver) driver).getCapabilities()).getBrowserName();
        assertThat(actualBrowser, IsEqualIgnoringCase.equalToIgnoringCase("chrome"));
    }
}
