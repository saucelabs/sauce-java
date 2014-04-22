package com.saucelabs.maven;

import com.saucelabs.ci.sauceconnect.SauceTunnelManager;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.util.Map;

/**
 * Maven Mojo which stops a Sauce Connect process started by the {@link StartSauceConnectMojo}.
 *
 * @author Ross Rowe
 * @goal stop-sauceconnect
 */
public class StopSauceConnectMojo extends AbstractMojo {

    private static final String SAUCE_CONNECT_KEY = "SAUCE_CONNECT";

    /**
     * @parameter expression="${sauce.user}"
     */
    private String sauceUsername;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Stopping Sauce Connect");
        Map context = getPluginContext();

        SauceTunnelManager manager = (SauceTunnelManager) context.get(SAUCE_CONNECT_KEY);
        if (manager == null) {
            //no process available
            getLog().warn("Unable to find Sauce Connect Manager instance");
        } else {
            //close running process
            manager.closeTunnelsForPlan(this.sauceUsername, null);
            getLog().info("Sauce Connect stopped");
        }

    }
}
