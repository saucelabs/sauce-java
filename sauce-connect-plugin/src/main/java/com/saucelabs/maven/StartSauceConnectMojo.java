package com.saucelabs.maven;

import com.saucelabs.ci.sauceconnect.SauceConnectTwoManager;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @goal start-sauceconnect
 * @author Ross Rowe
 */
public class StartSauceConnectMojo extends AbstractMojo {

    private static final String SAUCE_CONNECT_KEY = "SAUCE_CONNECT";

    /**
     * @parameter expression="${sauce.user}"
     */
    private String username;

    /**
     * @parameter expression="${sauce.accessKey}"
     */
    private String accessKey;

    /**
     * @parameter default-value="4445"
     */
    private int port;

    /**
     *
     */
    private String httpsProtocol;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Map context = getPluginContext();
        if (context.get(SAUCE_CONNECT_KEY) != null) {
            //process already running
        } else {
            //find Sauce Connect jar file location
            SauceConnectTwoManager sauceConnectTwoManager = new SauceConnectTwoManager();
            try {
                sauceConnectTwoManager.openConnection(username, accessKey, port, null, httpsProtocol, null);
                context.put(SAUCE_CONNECT_KEY, sauceConnectTwoManager);
            } catch (IOException e) {
                getLog().error("Error generated when launching Sauce Connect", e);
            }
        }

    }
}
