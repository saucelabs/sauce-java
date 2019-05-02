package com.saucelabs.common;

public class SauceRemoteGrid {
    public SauceLabsSession test;
    public SauceRemoteGrid() throws SauceEnvironmentVariableNotSetException {
        test = new SauceLabsSession();
    }
}
