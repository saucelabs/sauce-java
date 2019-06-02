package com.saucelabs.remotedriver;

import org.openqa.selenium.MutableCapabilities;

public class SauceOptions extends MutableCapabilities
{

	public SauceOptions(String username, String accessKey)
	{
		setCapability("username", username);
		setCapability("accessKey", accessKey);

		setCapability("name", "mytest");
	}
}
