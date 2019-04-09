package com.saucelabs.common;

public class SauceLabs {

    public SauceRemoteGrid driver;
    public SauceHelper helper;

    public SauceLabs(){
        this.driver = new SauceRemoteGrid();
        this.helper = new SauceHelper();
    }
}
