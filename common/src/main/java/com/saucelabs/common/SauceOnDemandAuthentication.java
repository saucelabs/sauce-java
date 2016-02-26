package com.saucelabs.common;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

    /**
     * Name of username environment variable set by Sauce CI plugin (Jenkins/Bamboo/TeamCity)
     */
    private static final String SAUCE_USER_NAME = "SAUCE_USER_NAME";
    /**
     * Name of username environment variable set by other Sauce plugins (PHP/Travis?)
     */
    private static final String SAUCE_USERNAME = "SAUCE_USERNAME";
    /**
     * Name of access key environment variable set by Sauce CI plugin (Jenkins/Bamboo/TeamCity)
     */
    private static final String SAUCE_API_KEY = "SAUCE_API_KEY";
    /**
     * Name of access key environment variable set by other Sauce plugins (PHP/Travis?)
     */
    private static final String SAUCE_ACCESS_KEY = "SAUCE_ACCESS_KEY";

    /**
     * Constructs a new instance, first attempting to populate the username/access key
     * from system properties/environment variables.  If none are found, then attempt
     * to parse a ~/.sauce-ondemand file.
     */
    public SauceOnDemandAuthentication() {
        //first try to retrieve information from properties/environment variables
        this.username = getPropertyOrEnvironmentVariable(SAUCE_USER_NAME);
        if (username == null || username.equals("")) {
            //try the SAUCE_USERNAME environment variable
            this.username = getPropertyOrEnvironmentVariable(SAUCE_USERNAME);
        }
        this.accessKey = getPropertyOrEnvironmentVariable(SAUCE_API_KEY);
        if (accessKey == null || accessKey.equals("")) {
            //try the SAUCE_ACCESS_KEY environment variable
            this.accessKey = getPropertyOrEnvironmentVariable(SAUCE_ACCESS_KEY);
        }

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
     * This common convention allows all the tools that interact with Sauce OnDemand REST API
     * to use the single credential, thereby simplifying the user configuration.
     * @return File object for the "~/.sauce-ondemand" credential file
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

    /**
     * Persists this credential to the disk.
     * @param propertyFile File object to save java propery style credentials to.
     * @throws IOException If the file I/O fails.
     */
    public void saveTo(File propertyFile) throws IOException {
        Properties props = new Properties();
        props.put("username", username);
        props.put("key", accessKey);
        FileOutputStream out = new FileOutputStream(propertyFile);
        try {
            props.store(out, "Sauce OnDemand access credential");
        } finally {
            out.close();
        }
    }
}
