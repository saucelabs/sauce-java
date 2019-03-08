package com.saucelabs.common;

import org.openqa.selenium.WebDriver;

public class JavaScriptInvoker {
    private JavaScriptInvokerManager manager;
    public JavaScriptInvoker(WebDriver driver)
    {
        manager = JavaScriptInvokerFactory.create(driver);
    }
    public Object executeScript(String script){
        return manager.executeScript(script);
    }
}
