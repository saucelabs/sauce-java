package com.saucelabs.common;

public class StubSauceSession extends SauceLabsSession {
    private final IRemoteSession fakeRemoteDriver;

    public StubSauceSession(IRemoteSession remoteDriver)
    {
        fakeRemoteDriver = remoteDriver;
    }

    @Override
    public IRemoteSession getRemoteDriver() {
        return fakeRemoteDriver;
    }
}
