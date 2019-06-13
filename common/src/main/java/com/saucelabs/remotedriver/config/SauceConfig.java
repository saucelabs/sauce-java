package com.saucelabs.remotedriver.config;

import org.openqa.selenium.chrome.ChromeOptions;

public class SauceConfig
{
	public String username;
	public String accessKey;

	public class testConfig
	{
		public String name;
		public String build;
		public String tags;
		public String customData;
	}

	public class OSConfig
	{
		public String browserName;
	}

	public ChromeOptions chromeOptions;
	public String browserName;

	public static LinuxConfig withLinux()
	{
		return new LinuxConfig();
	}
}