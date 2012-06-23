package com.saucelabs.ondemand;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * @author Ross Rowe
 */
public class SauceOnDemandTestListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
