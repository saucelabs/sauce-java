package com.saucelabs.testng;

import com.saucelabs.common.SauceOnDemandAuthentication;

/**
 * @author Ross Rowe
 */
public interface SauceOnDemandAuthenticationProvider {
    /**
     *
     * @return
     */
    SauceOnDemandAuthentication getAuthentication();
}
