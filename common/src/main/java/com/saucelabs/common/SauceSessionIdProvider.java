package com.saucelabs.common;

/**
 * Interface that should be implemented by classes using the TestNG or JUnit helper classes.
 * @author Ross Rowe - updated documentation
 */
public interface SauceSessionIdProvider {

    /**
     * Return the session id for the WebDriver instance - this equates to the Sauce Labs Job id.
     * @return string representing the session id for the SeleniumRC/WebDriver instance
     */
    String getSessionId();
}
