package com.saucelabs.common;

import org.openqa.selenium.WebDriver;

public class JavaScriptRunnerFactory {
    private static JavaScriptRunnerManager javaScriptRunnerManager = null;

    public static void setJavaScriptManager(JavaScriptRunnerManager js)
    {
        javaScriptRunnerManager = js;
    }
    public static JavaScriptRunnerManager create(WebDriver driver)
    {
        if (javaScriptRunnerManager != null)
        {
            return javaScriptRunnerManager;
        }
        return new JavaScriptRunnerManager(driver);
    }
}
