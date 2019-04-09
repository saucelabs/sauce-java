package com.saucelabs.common;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;

public class SauceDriver {

    private MutableCapabilities options;

    public SauceDriver(MutableCapabilities capabilities){
        this.options = capabilities;
    }

    public SauceDriver(){
        this(new MutableCapabilities());
    }

    public SauceDriver(String browser){
        this.options = browserNameToOptions(browser);
    }


    public MutableCapabilities getOptions() {
        return options;
    }

    private MutableCapabilities browserNameToOptions(String browser) {
        if (browser.toLowerCase().equals("chrome")) {
            return new ChromeOptions();
        }
        else {
            return new MutableCapabilities();
        }
    }
}
