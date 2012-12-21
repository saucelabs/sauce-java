package com.saucelabs.maven;

import com.saucelabs.ci.sauceconnect.SauceConnectTwoManager;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.IOException;
import java.util.Map;

/**
 * Maven mojo which starts a Sauce Connect process.
 *
 * @goal start-sauceconnect
 * @author Ross Rowe
 */
public class StartSauceConnectMojo extends AbstractMojo {

    private static final String SAUCE_CONNECT_KEY = "SAUCE_CONNECT";

    /**
     * @parameter expression="${sauce.user}"
     */
    private String sauceUsername;

    /**
     * @parameter expression="${sauce.sauceAccessKey}"
     */
    private String sauceAccessKey;

    /**
     * @parameter default-value="4445"
     */
    private int port;

    /**
     *
     *
     */
    private String httpsProtocol;

    /**
     *
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Starting Sauce Connect");
        if (sauceUsername == null || sauceUsername.equals("")) {
            getLog().error("Sauce sauceUsername not specified");
            return;
        }
        if (sauceAccessKey == null || sauceAccessKey.equals("")) {
            getLog().error("Sauce access key not specified");
            return;
        }
        Map context = getPluginContext();
        if (context.get(SAUCE_CONNECT_KEY) != null) {
            //process already running
        } else {
            //find Sauce Connect jar file location
            SauceConnectTwoManager sauceConnectTwoManager = new SauceConnectTwoManager();
            try {
                sauceConnectTwoManager.openConnection(sauceUsername, sauceAccessKey, port, null, httpsProtocol, null);
                context.put(SAUCE_CONNECT_KEY, sauceConnectTwoManager);
            } catch (IOException e) {
                getLog().error("Error generated when launching Sauce Connect", e);
            }
        }

    }
}
