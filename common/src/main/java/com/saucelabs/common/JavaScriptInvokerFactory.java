package com.saucelabs.common;

import org.openqa.selenium.WebDriver;

public class JavaScriptInvokerFactory {
    private static JavaScriptInvokerManager customManager = null;

    public static void setJavaScriptInvoker(JavaScriptInvokerManager js)
    {
        customManager = js;
    }
    public static JavaScriptInvokerManager create(WebDriver driver)
    {
        if (customManager != null)
        {
            return customManager;
        }
        else
            return new JavaScriptInvokerManager(driver);
    }
}
