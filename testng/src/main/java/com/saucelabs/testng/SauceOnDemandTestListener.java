package com.saucelabs.testng;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.saucerest.SauceREST;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.IOException;

/**
 * TODO include method to download log/video?
 *
 * @author Ross Rowe
 */
public class SauceOnDemandTestListener extends TestListenerAdapter {

    /**
     * The underlying {@link com.saucelabs.common.SauceOnDemandSessionIdProvider} instance which contains the Selenium session id.  This is typically
     * the unit test being executed.
     */
    private SauceOnDemandSessionIdProvider sessionIdProvider;

    /**
     * The instance of the Sauce OnDemand Java REST API client.
     */
    private SauceREST sauceREST;

    public SauceOnDemandTestListener() {
    }

    @Override
    public void onStart(ITestContext testContext) {
        super.onStart(testContext);
    }

    /**
     *
     * @param result
     */
    @Override
    public void onTestStart(ITestResult result) {
        super.onTestStart(result);

        if (result.getInstance() instanceof SauceOnDemandSessionIdProvider) {
            this.sessionIdProvider = (SauceOnDemandSessionIdProvider) result.getInstance();
            //log the session id to the system out
            System.out.println(String.format("SauceOnDemandSessionID=%1 job-name=%2", sessionIdProvider, result.getMethod().getMethodName()));
        }
        SauceOnDemandAuthentication sauceOnDemandAuthentication;
        if (result.getInstance() instanceof SauceOnDemandAuthenticationProvider) {
            //use the authentication information provided by the test class
            SauceOnDemandAuthenticationProvider provider = (SauceOnDemandAuthenticationProvider) result.getInstance();
            sauceOnDemandAuthentication = provider.getAuthentication();
        } else {
            //otherwise use the default authentication
            sauceOnDemandAuthentication = new SauceOnDemandAuthentication();
        }
        sauceREST = new SauceREST(sauceOnDemandAuthentication.getUsername(), sauceOnDemandAuthentication.getAccessKey());
    }

    /**
     *
     * @param tr
     */
    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        try {
            if (sessionIdProvider != null) {
                sauceREST.jobFailed(sessionIdProvider.getSessionId());
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    /**
     *
     * @param tr
     */
    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
        try {
            if (sessionIdProvider != null) {
                sauceREST.jobPassed(sessionIdProvider.getSessionId());
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
