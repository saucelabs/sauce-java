package com.saucelabs.testng;

import com.saucelabs.common.SauceAuthentication;

/**
 * Marker interface which should be implemented by Tests that instantiate {@link SauceAuthentication}.  This
 * interface is referenced by {@link SauceTestListener} in order to construct the {@link com.saucelabs.saucerest.SauceREST}
 * instance using the authentication specified for the specific test.
 *
 * @author Ross Rowe
 */
public interface SauceAuthenticationProvider {
    /**
     *
     * @return the {@link SauceAuthentication} instance for a specific test.
     */
    SauceAuthentication getAuthentication();
}
