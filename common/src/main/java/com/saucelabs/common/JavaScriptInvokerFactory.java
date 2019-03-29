package com.saucelabs.common;

import org.openqa.selenium.WebDriver;

public class JavaScriptInvokerFactory {
    private static JavaScriptInvokerImpl customManager = null;

    public static void setJavaScriptInvoker(JavaScriptInvokerImpl js)
    {
        customManager = js;
    }
    public static JavaScriptInvokerImpl create(WebDriver driver)
    {
        if (customManager != null)
        {
            return customManager;
        }
        else
            return new JavaScriptInvokerImpl(driver);
    }
}
