package com.saucelabs.common;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

public interface IRemoteSession {
    RemoteWebDriver create(String version,  String platform,
                           String userName, String accessKey,
                           String sauceRemoteUrl) throws MalformedURLException;
}
