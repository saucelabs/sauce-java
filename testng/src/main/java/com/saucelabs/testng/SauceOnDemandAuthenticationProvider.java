package com.saucelabs.testng;

import com.saucelabs.common.SauceOnDemandAuthentication;

/**
 * Marker interface which should be implemented by Tests that instantiate {@link SauceOnDemandAuthentication}.  This
 * interface is referenced by {@link SauceOnDemandTestListener} in order to construct the {@link com.saucelabs.saucerest.SauceREST}
 * instance using the authentication specified for the specific test.
 *
 * @author Ross Rowe
 */
public interface SauceOnDemandAuthenticationProvider {
    /**
     *
     * @return the {@link SauceOnDemandAuthentication} instance for a specific test.
     */
    SauceOnDemandAuthentication getAuthentication();
}
