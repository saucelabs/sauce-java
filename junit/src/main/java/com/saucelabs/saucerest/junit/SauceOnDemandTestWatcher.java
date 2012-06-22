package com.saucelabs.saucerest.junit;

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

    private final SessionIdProvider sessionIdProvider;
    private final SauceREST sauceREST;


    /**
     *
     * @param sessionIdProvider
     */
    public SauceOnDemandTestWatcher(SessionIdProvider sessionIdProvider) {
        this(sessionIdProvider, new SauceOnDemandAuthentication());
    }

    /**
     *
     * @param sessionIdProvider
     * @param authentication
     */
    public SauceOnDemandTestWatcher(SessionIdProvider sessionIdProvider, SauceOnDemandAuthentication authentication) {
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
    public SauceOnDemandTestWatcher(SessionIdProvider sessionIdProvider, final String username, final String accessKey) {
        this.sessionIdProvider = sessionIdProvider;
        sauceREST = new SauceREST(username, accessKey);
    }

    /**
     *
     * @param description
     */
    protected void succeeded(Description description) {
        try {
            sauceREST.jobPassed(sessionIdProvider.getSessionId());
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    /**
     *
     * @param e
     * @param description
     */
    protected void failed(Throwable e, Description description) {
        try {
            sauceREST.jobFailed(sessionIdProvider.getSessionId());
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
