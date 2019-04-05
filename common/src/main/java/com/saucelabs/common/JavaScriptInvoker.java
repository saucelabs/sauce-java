package com.saucelabs.common;

import org.openqa.selenium.WebDriver;

public class JavaScriptInvoker {
    private JavaScriptExecutor jsInvokerImplementation;
    public JavaScriptInvoker(WebDriver driver)
    {
        jsInvokerImplementation = JavaScriptInvokerFactory.create(driver);
    }
    public Object executeScript(String script){
        return jsInvokerImplementation.executeScript(script);
    }
}
