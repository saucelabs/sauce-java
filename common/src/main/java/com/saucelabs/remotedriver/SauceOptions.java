package com.saucelabs.remotedriver;

import org.openqa.selenium.MutableCapabilities;

public class SauceOptions extends MutableCapabilities
{

    private String browser;
    private String browserVersion;
    private String os;

    public SauceOptions(String username, String accessKey)
	{
		setCapability("username", username);
		setCapability("accessKey", accessKey);

		setCapability("name", "mytest");
	}

    public SauceOptions() {
        browser = "Chrome";
        os = "windows 10";
        browserVersion = "latest";
    }

    public void setOs(String operatingSystem) {
	    this.os = operatingSystem;
    }
}
