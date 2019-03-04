package com.saucelabs.common;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class JavaScriptRunnerManager implements JavaScriptExecutor {
    private final WebDriver webDriver;


    public JavaScriptRunnerManager(WebDriver driver)
    {
        webDriver = driver;
    }

    @Override
    public Object executeScript(String script) {
        JavascriptExecutor js = (JavascriptExecutor)webDriver;
        return js.executeScript(script);
    }
}
