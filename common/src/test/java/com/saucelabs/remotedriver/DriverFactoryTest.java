package com.saucelabs.remotedriver;

import org.junit.Test;

import java.net.MalformedURLException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

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
        RemoteDriverInterface stubRemoteManager = mock(RemoteDriverInterface.class);
        factory = new DriverFactory(stubRemoteManager);
        factory.getInstance();
        String expectedServer = "https://ondemand.saucelabs.com/wd/hub";
        assertEquals(expectedServer, factory.seleniumServer);
    }
}
