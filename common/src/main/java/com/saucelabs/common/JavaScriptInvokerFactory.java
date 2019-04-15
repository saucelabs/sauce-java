package com.saucelabs.common;

import org.openqa.selenium.WebDriver;

public class JavaScriptInvokerFactory {
    private static JavaScriptExecutor customManager = null;

    public static void setJavaScriptExecutor(JavaScriptExecutor js)
    {
        customManager = js;
    }
    public static JavaScriptExecutor create(WebDriver driver)
    {
        if (customManager != null)
        {
            return customManager;
        }
        else
            return new JavaScriptInvokerImpl(driver);
    }
}
