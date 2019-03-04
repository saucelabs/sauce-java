package com.saucelabs.common;

import org.openqa.selenium.WebDriver;

public class JavaScriptRunner {
    private JavaScriptRunnerManager manager;
    public JavaScriptRunner(WebDriver driver)
    {
        manager = JavaScriptRunnerFactory.create(driver);
    }
    public Object executeScript(String script){
        return manager.executeScript(script);
    }
}
