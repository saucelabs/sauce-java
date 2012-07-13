package com.saucelabs.common;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Stores the Sauce OnDemand authentication (retrieved from
 * either System properties/environment variables or by parsing the ~/.sauce-ondemand file).
 * The authentication information information will also be made available by the getter methods, so
 * that it can be used when constructing Sauce OnDemand request URLs.
 *
 * @author Ross Rowe
 */
public class SauceOnDemandAuthentication {

    /**
     * The username to use when connecting to Sauce OnDemand.  Defaults to empty string.
     */
    private String username = "";

    /**
     * The access key to use when connecting to Sauce OnDemand. Defaults to empty string.
     */
    private String accessKey = "";

    private static final String SAUCE_USER_NAME = "SAUCE_USER_NAME";
    private static final String SAUCE_API_KEY = "SAUCE_API_KEY";

    /**
     * Constructs a new instance, first attempting to populate the username/access key
     * from system properties/environment variables.  If none are found, then attempt
     * to parse a ~/.sauce-ondemand file.
     */
    public SauceOnDemandAuthentication() {
        //first try to retrieve information from properties/environment variables
        this.username = getPropertyOrEnvironmentVariable(SAUCE_USER_NAME);
        this.accessKey = getPropertyOrEnvironmentVariable(SAUCE_API_KEY);
        //if nothing set, try to parse ~/.sauce-ondemand
        if (username == null || accessKey == null) {
            loadCredentialsFromFile(getDefaultCredentialFile());
        }
    }

    public SauceOnDemandAuthentication(String username, String accessKey) {
        this.username = username;
        this.accessKey = accessKey;
    }

    private void loadCredentialsFromFile(File propertyFile) {
        Properties props = new Properties();
        FileInputStream in = null;
        try {
            if (propertyFile.exists()) {
                in = new FileInputStream(propertyFile);
                props.load(in);
                this.username = props.getProperty("username");
                this.accessKey = props.getProperty("key");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                //ignore error and continue
            }
        }
    }

    /**
     * Location of the default credential file. "~/.sauce-ondemand"
     * <p/>
     * <p/>
     * This common convention allows all the tools that interact with Sauce OnDemand REST API
     * to use the single credential, thereby simplifying the user configuration.
     */
    public static File getDefaultCredentialFile() {
        return new File(new File(System.getProperty("user.home")), ".sauce-ondemand");
    }

    private static String getPropertyOrEnvironmentVariable(String property) {
        String value = System.getProperty(property);
        if (value == null || value.equals("")) {
            value = System.getenv(property);
        }
        return value;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }
}
