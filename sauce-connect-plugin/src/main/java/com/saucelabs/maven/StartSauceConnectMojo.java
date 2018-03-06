package com.saucelabs.maven;

import com.saucelabs.ci.sauceconnect.SauceConnectFourManager;
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
     * @parameter property="{sauce.user}"
     */
    private String sauceUsername;

    /**
     * @parameter property="{sauce.sauceAccessKey}"
     */
    private String sauceAccessKey;

    /**
     * @parameter default-value="4445"
     */
    private int port;

    /**
     * @parameter property="{sauce.httpsProtocol}
     */
    private String httpsProtocol;

    /**
     * @parameter property="{sauce.options}
     */
    private String options;

    /**
     * @parameter property="{sauce.quietMode}
     */
    private boolean quietMode;

    /**
     * @throws MojoExecutionException Execution exception from plugin
     * @throws MojoFailureException Failure exception from plugin
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
        if (context.get(SAUCE_CONNECT_KEY) == null) {
            //find Sauce Connect jar file location

            SauceTunnelManager SauceConnectFourManager = new SauceConnectFourManager(quietMode);
            try {
                SauceConnectFourManager.openConnection(sauceUsername, sauceAccessKey, port, null, options, null, !quietMode, null);
                context.put(SAUCE_CONNECT_KEY, SauceConnectFourManager);
            } catch (IOException e) {
                getLog().error("Error generated when launching Sauce Connect", e);
            }
        }

    }
}
