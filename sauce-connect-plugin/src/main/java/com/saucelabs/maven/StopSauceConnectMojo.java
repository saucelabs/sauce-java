package com.saucelabs.maven;

import com.saucelabs.ci.sauceconnect.SauceConnectTwoManager;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.util.Map;

/**
 * @author Ross Rowe
 */
public class StopSauceConnectMojo extends AbstractMojo {

    private static final String SAUCE_CONNECT_KEY = "SAUCE_CONNECT";

    /**
     *
     */
    private String username;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Map context = getPluginContext();
        SauceConnectTwoManager manager = (SauceConnectTwoManager) context.get(SAUCE_CONNECT_KEY);
        if (manager == null) {
            //no process available
        } else {
            //close running process
            manager.closeTunnelsForPlan(this.username, null);
        }

    }
}
