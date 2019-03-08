package com.saucelabs.common;

import org.openqa.selenium.WebDriver;

public class JavaScriptInvokerFactory {
    private static JavaScriptInvokerManager javaScriptInvoker = null;

    public static void setJavaScriptManager(JavaScriptInvokerManager js)
    {
        javaScriptInvoker = js;
    }
    public static JavaScriptInvokerManager create(WebDriver driver)
    {
        if (javaScriptInvoker != null)
        {
            return javaScriptInvoker;
        }
        else
            return new JavaScriptInvokerManager(driver);
    }
}
