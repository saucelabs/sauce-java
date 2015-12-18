package com.saucelabs.testng;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.common.Utils;
import com.saucelabs.saucerest.SauceREST;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Test Listener that providers helper logic for TestNG tests.  Upon startup, the class
 * will store any SELENIUM_* environment variables (typically set by a Sauce OnDemand CI
 * plugin) as system parameters, so that they can be retrieved by tests as parameters.
 * <p>
 *
 * @author Ross Rowe / Mehmet Gerceker
 */
public class SauceOnDemandTestListener extends TestListenerAdapter {

    private static final String SELENIUM_BROWSER = "SELENIUM_BROWSER";
    private static final String SELENIUM_PLATFORM = "SELENIUM_PLATFORM";
    private static final String SELENIUM_VERSION = "SELENIUM_VERSION";
    private static final String SELENIUM_IS_LOCAL = "SELENIUM_IS_LOCAL";

    /**
     * The instance of the Sauce OnDemand Java REST API client.
     */
    private SauceREST sauceREST;

    /**
     * Treat this test as a local test or run in SauceLabs.
     */
    private boolean isLocal = false;

    /**
     * Boolean indicating whether to print the log messages to the stdout.
     */
    public static boolean verboseMode = true;

    /**
     * Check to see if environment variables that define the Selenium browser to be used have been set (typically by
     * a Sauce OnDemand CI plugin).  If so, then populate the appropriate system parameter, so that tests can use
     * these values.
     * TODO: Clarify where the env vars are coming from and the purpose.
     *
     * @param testContext
     */
    @Override
    public void onStart(ITestContext testContext) {
        super.onStart(testContext);
        String local = Utils.readPropertyOrEnv(SELENIUM_IS_LOCAL, "");
        if (local != null && !local.equals("")) {
            isLocal = true;
        }
        String browser = System.getenv(SELENIUM_BROWSER);
        if (browser != null && !browser.equals("")) {
            System.setProperty("browser", browser);
        }
        String platform = System.getenv(SELENIUM_PLATFORM);
        if (platform != null && !platform.equals("")) {
            System.setProperty("os", platform);
        }
        String version = System.getenv(SELENIUM_VERSION);
        if (version != null && !version.equals("")) {
            System.setProperty("version", version);
        }
    }

    /**
     * @param result
     */
    @Override
    public void onTestStart(ITestResult result) {
        super.onTestStart(result);

        if (isLocal) {
            return;
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
        this.sauceREST = new SauceREST(sauceOnDemandAuthentication.getUsername(), sauceOnDemandAuthentication.getAccessKey());
    }

    /**
     * @param tr
     */
    @Override
    public void onTestFailure(ITestResult tr) {
        String sessionId = ((SauceOnDemandSessionIdProvider) tr.getInstance()).getSessionId();
        super.onTestFailure(tr);
        if (isLocal) {
            return;
        }
        markJobStatus(sessionId, false);
        printOutSessionID(sessionId, tr.getMethod().getMethodName());
        printPublicJobLink(sessionId);

    }
    private void printPublicJobLink(String sessionId) {
        if (verboseMode) {
            try {
                String authLink = this.sauceREST.getPublicJobLink(sessionId);
                System.out.println("Job link: " + authLink);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void printOutSessionID(String sessionId, String testName) {
        System.out.println(String.format("SauceOnDemandSessionID=%1$s job-name=%2$s", sessionId, testName));
    }

    /**
     * @param tr
     */
    @Override
    public void onTestSuccess(ITestResult tr) {
        String sessionId = ((SauceOnDemandSessionIdProvider) tr.getInstance()).getSessionId();
        super.onTestSuccess(tr);
        if (isLocal) {
            return;
        }
        printOutSessionID(sessionId, tr.getMethod().getMethodName());
        markJobStatus(sessionId, true);
    }

    private void markJobStatus(String sessionId, boolean hasPassed) {
        try {
            Map<String, Object> updates = new HashMap<String, Object>();
            updates.put("passed", hasPassed);
            Utils.addBuildNumberToUpdate(updates);
            sauceREST.updateJobInfo(sessionId, updates);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
