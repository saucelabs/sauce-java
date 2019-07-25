package com.saucelabs.remotedriver.config;

import org.openqa.selenium.chrome.ChromeOptions;

public class LinuxConfig extends SauceConfig
{
	public LinuxConfig withChrome()
	{
		chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("w3c", true);
		browserName = "Chrome";

		return this;
	}
}