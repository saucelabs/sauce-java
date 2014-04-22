package com.saucelabs.maven;

import com.saucelabs.ci.sauceconnect.SauceConnectFourManager;
import com.saucelabs.ci.sauceconnect.SauceConnectTwoManager;
import com.saucelabs.ci.sauceconnect.SauceTunnelManager;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.IOException;
import java.util.Map;

/**
 * Maven mojo which starts a Sauce Connect process.
 *
 * @author Ross Rowe
 * @goal start-sauceconnect
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
     * @parameter expression="${sauce.httpsProtocol}
     */
    private String httpsProtocol;

    /**
     * @parameter expression="${sauce.options}
     */
    private String options;

    /**
     * @parameter expression="${sauce.quietMode}
     */
    private boolean quietMode;

    /**
     * @parameter expression="${sauce.launchSauceConnect3}
     */
    private boolean launchSauceConnect3;

    /**
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

            SauceTunnelManager sauceConnectTwoManager =
                    launchSauceConnect3 ? new SauceConnectTwoManager(quietMode) : new SauceConnectFourManager(quietMode);
            try {
                sauceConnectTwoManager.openConnection(sauceUsername, sauceAccessKey, port, null, options, httpsProtocol, null);
                context.put(SAUCE_CONNECT_KEY, sauceConnectTwoManager);
            } catch (IOException e) {
                getLog().error("Error generated when launching Sauce Connect", e);
            }
        }

    }
}
