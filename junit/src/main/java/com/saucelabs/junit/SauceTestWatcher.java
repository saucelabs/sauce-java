package com.saucelabs.junit;

import com.saucelabs.common.SauceAuthentication;
import com.saucelabs.common.SauceSessionIdProvider;
import com.saucelabs.common.Utils;
import com.saucelabs.saucerest.SauceREST;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link TestWatcher} subclass that will mark a Sauce Labs job as passed or failed depending on the result
 * of the test case being executed.
 * @author Ross Rowe - modifications to use {@link SauceAuthentication}
 */
public class SauceTestWatcher extends TestWatcher {

    /**
     * The underlying {@link SauceSessionIdProvider} instance which contains the Selenium session id.  This is typically
     * the unit test being executed.
     */
    private final SauceSessionIdProvider sessionIdProvider;

    /**
     * The instance of the Sauce Labs Java REST API client.
     */
    private final SauceREST sauceREST;

    /**
     * Boolean indicating whether to print the log messages to the stdout.
     */
    private boolean verboseMode = true;


    /**
     * @param sessionIdProvider Id provider for the current web driver session
     */
    public SauceTestWatcher(SauceSessionIdProvider sessionIdProvider) {
        this(sessionIdProvider, new SauceAuthentication());
    }

    public SauceTestWatcher(SauceSessionIdProvider sessionIdProvider, boolean verboseMode) {
        this(sessionIdProvider, new SauceAuthentication(), verboseMode);
    }

    /**
     * @param sessionIdProvider Id provider for the current web driver session
     * @param authentication Authentication provider for the current sauce labs user
     */
    public SauceTestWatcher(SauceSessionIdProvider sessionIdProvider, SauceAuthentication authentication) {
        this(sessionIdProvider,
                authentication.getUsername(),
                authentication.getAccessKey(), true);
    }

    /**
     * @param sessionIdProvider Id provider for the current web driver session
     * @param authentication Authentication provider for the current sauce labs user
     * @param verboseMode Enables verbose mode
     */
    public SauceTestWatcher(SauceSessionIdProvider sessionIdProvider, SauceAuthentication authentication, boolean verboseMode) {
        this(sessionIdProvider,
                authentication.getUsername(),
                authentication.getAccessKey(),
                verboseMode);
    }

    /**
     * @param sessionIdProvider Id provider for the current web driver session
     * @param username Sauce user name
     * @param accessKey Sauce access key
     * @param verboseMode Enables verbose mode
     */
    public SauceTestWatcher(SauceSessionIdProvider sessionIdProvider, final String username, final String accessKey, boolean verboseMode) {
        this.sessionIdProvider = sessionIdProvider;
        sauceREST = new SauceREST(username, accessKey);
        this.verboseMode = verboseMode;
    }

    /**
     * Invoked if the unit test passes without error or failure.  Invokes the Sauce REST API to mark the Sauce Job
     * as 'passed'.
     *
     * @param description not used
     */
    protected void succeeded(Description description) {
        if (sessionIdProvider.getSessionId() != null) {
            //log the session id to the system out
            printSessionId(description);
            Map<String, Object> updates = new HashMap<String, Object>();
            updates.put("passed", true);
            Utils.addBuildNumberToUpdate(updates);
            sauceREST.updateJobInfo(sessionIdProvider.getSessionId(), updates);
        }
    }

    private void printSessionId(Description description) {
        if (verboseMode) {
            String message = String.format("SauceOnDemandSessionID=%1$s job-name=%2$s.%3$s", sessionIdProvider.getSessionId(), description.getClassName(), description.getMethodName());
            System.out.println(message);
        }
    }

    /**
     * Invoked if the unit test either throws an error or fails.  Invokes the Sauce REST API to mark the Sauce Job
     * as 'failed'.
     *
     * @param e           not used
     * @param description not used
     */
    protected void failed(Throwable e, Description description) {
        if (sessionIdProvider != null && sessionIdProvider.getSessionId() != null) {
            printSessionId(description);
            Map<String, Object> updates = new HashMap<String, Object>();
            updates.put("passed", false);
            Utils.addBuildNumberToUpdate(updates);
            sauceREST.updateJobInfo(sessionIdProvider.getSessionId(), updates);

            if (verboseMode) {
                // get, and print to StdOut, the link to the job
                String authLink = sauceREST.getPublicJobLink(sessionIdProvider.getSessionId());
                System.out.println("Job link: " + authLink);
            }
        }
    }


}
