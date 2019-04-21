package com.saucelabs.common;

public class TestableSauceTest extends SauceLabsTest {
    private final IRemoteBrowser fakeRemoteDriver;

    public TestableSauceTest(IRemoteBrowser remoteDriver)
    {
        fakeRemoteDriver = remoteDriver;
    }

    @Override
    public IRemoteBrowser getRemoteDriver() {
        return fakeRemoteDriver;
    }
}
