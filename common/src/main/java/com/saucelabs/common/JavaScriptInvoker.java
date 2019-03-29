package com.saucelabs.common;

import org.openqa.selenium.WebDriver;

public class JavaScriptInvoker {
    private JavaScriptInvokerImpl jsInvokerImplementation;
    public JavaScriptInvoker(WebDriver driver)
    {
        jsInvokerImplementation = JavaScriptInvokerFactory.create(driver);
    }
    public Object executeScript(String script){
        return jsInvokerImplementation.executeScript(script);
    }
}
