package com.saucelabs.common;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class SauceRemoteBrowser implements IRemoteSession {
    @Override
    public RemoteWebDriver create(String version,  String platform,
                                  String userName, String accessKey,
                                  String sauceRemoteUrl) throws MalformedURLException {
        ChromeOptions caps;
        caps = new ChromeOptions();
        caps.setCapability("version", version);
        caps.setCapability("platform", platform);
        caps.setExperimentalOption("w3c", true);

        MutableCapabilities sauceOptions;
        sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", userName);
        sauceOptions.setCapability("accessKey", accessKey);

        caps.setCapability("sauce:options", sauceOptions);
        return new RemoteWebDriver(new URL(sauceRemoteUrl), caps);
    }
}
