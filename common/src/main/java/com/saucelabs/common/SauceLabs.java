package com.saucelabs.common;

public class SauceLabs {

    public SauceRemoteGrid grid;
    public SauceHelper helper;

    public SauceLabs(){
        this.grid = new SauceRemoteGrid();
        this.helper = new SauceHelper();
    }
}
