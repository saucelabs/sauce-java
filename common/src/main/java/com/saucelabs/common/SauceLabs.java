package com.saucelabs.common;

public class SauceLabs {

    public SauceDriver driver;
    public SauceHelper helper;

    public SauceLabs(){
        this.driver = new SauceDriver();
        this.helper = new SauceHelper();
    }
}
