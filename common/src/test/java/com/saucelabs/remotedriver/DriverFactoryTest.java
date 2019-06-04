package com.saucelabs.remotedriver;

import org.junit.Test;

import java.net.MalformedURLException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class DriverFactoryTest {

    @Test
    public void defaultConstructor_instantiated_returnsFactory()
    {
        DriverFactory driverFactory = new DriverFactory();
        assertNotNull(driverFactory);
    }

    @Test
    public void seleniumServer_notSet_returnsNull()
    {
        //TODO is this okay to be like a property,
        //or should it be a getSeleniumServer()
        DriverFactory factory = new DriverFactory();
        assertNull(factory.seleniumServer);
    }

    @Test
    public void defaultConstructor_instantiated_setsConcreteDriverManager()
    {
        DriverFactory factory = new DriverFactory();
        assertThat(factory.getDriverManager(), instanceOf(RemoteDriverManager.class));
    }

    @Test
    public void getInstance_serverNotSet_setsSauceSeleniumServer() throws MalformedURLException {
        RemoteDriverInterface stubRemoteManager = mock(RemoteDriverInterface.class);
        DriverFactory factory = new DriverFactory(stubRemoteManager);
        factory.getInstance();
        String expectedServer = "https://ondemand.saucelabs.com/wd/hub";
        assertEquals(expectedServer, factory.seleniumServer);
    }
}
