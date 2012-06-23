package com.saucelabs.junit;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.saucerest.SauceREST;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.IOException;

/**
 * {@link TestWatcher} subclass that will mark a Sauce OnDemand job as passed or failed depending on the result
 * of the test case being executed.
 *
 * @author see {@link github} for original
 * @author Ross Rowe - modifications to use {@link SauceOnDemandAuthentication}
 */
public class SauceOnDemandTestWatcher extends TestWatcher {

    /**
     * The underlying {@link SauceOnDemandSessionIdProvider} instance which contains the Selenium session id.  This is typically
     * the unit test being executed.
     */
    private final SauceOnDemandSessionIdProvider sessionIdProvider;

    /**
     * The instance of the Sauce OnDemand Java REST API client.
     */
    private final SauceREST sauceREST;


    /**
     *
     * @param sessionIdProvider
     */
    public SauceOnDemandTestWatcher(SauceOnDemandSessionIdProvider sessionIdProvider) {
        this(sessionIdProvider, new SauceOnDemandAuthentication());
    }

    /**
     *
     * @param sessionIdProvider
     * @param authentication
     */
    public SauceOnDemandTestWatcher(SauceOnDemandSessionIdProvider sessionIdProvider, SauceOnDemandAuthentication authentication) {
        this(sessionIdProvider,
                authentication.getUsername(),
                authentication.getAccessKey());
    }

    /**
     *
     * @param sessionIdProvider
     * @param username
     * @param accessKey
     */
    public SauceOnDemandTestWatcher(SauceOnDemandSessionIdProvider sessionIdProvider, final String username, final String accessKey) {
        this.sessionIdProvider = sessionIdProvider;
        sauceREST = new SauceREST(username, accessKey);
    }

    /**
     * Invoked if the unit test passes without error or failure.  Invokes the Sauce REST API to mark the Sauce Job
     * as 'passed'.
     *
     * @param description not used
     */
    protected void succeeded(Description description) {
        try {
            sauceREST.jobPassed(sessionIdProvider.getSessionId());
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    /**
     * Invoked if the unit test either throws an error or fails.  Invokes the Sauce REST API to mark the Sauce Job
     * as 'failed'.
     * @param e not used
     * @param description not used
     */
    protected void failed(Throwable e, Description description) {
        try {
            sauceREST.jobFailed(sessionIdProvider.getSessionId());
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
