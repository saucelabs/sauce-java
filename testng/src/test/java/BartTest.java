import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.HashMap;

import static com.saucelabs.ondemand.DownloadArtifacts.DownloadLog;
import static com.saucelabs.ondemand.DownloadArtifacts.DownloadVideo;

public class BartTest {
    private ThreadLocal< Selenium > seleniumControl = new ThreadLocal< Selenium >();
    private String sessionId;
    private HashMap<String, String> runInfo = new HashMap<String, String>();

    // Get the ThreadLocal selenium browser
    private Selenium selenium() {
        return seleniumControl.get();
    }

    // in a 'real' script, the username and key would be fed from an external property file
    // not embedded in testng xml files
    @Parameters({"ondemand", "username", "key", "os", "browser", "browserVersion"})
    @BeforeMethod
    public void startSelenium(@Optional("false") String ondemand,
                              @Optional("") String username,
                              @Optional("") String key,
                              @Optional("") String os,
                              @Optional("") String browser,
                              @Optional("") String browserVersion,
                              Method method) {
        // get the method name to be used later in the fetched video name
        runInfo.put("methodName", method.getName());

        DefaultSelenium s;

        // it is a good idea to build in switches for your scripts to run locally while
        // debugging but then run it in the cloud later
        if (ondemand.equals("true")){
            // also need the this information for the fetch video name
            runInfo.put("username", username);
            runInfo.put("key", key);
            runInfo.put("os", os);
            runInfo.put("browser", browser);
            runInfo.put("browserVersion", browserVersion);
            
            // in a 'real' script, this would be done programatically using gson it similar
            String json = "{\"username\": \"" + username + "\"," +
                "\"access-key\": \"" + key + "\"," +
                "\"os\": \"" + os + "\"," +
                "\"browser\": \"" + browser + "\"," +
                "\"browser-version\": \"" + browserVersion + "\"," +
                "\"job-name\": \"Running: " + runInfo.get("methodName") + "\"}";

            s = new DefaultSelenium("saucelabs.com", 4444, json, "http://www.bart.gov/");
        } else {
            s = new DefaultSelenium("localhost", 4444, "*firefox", "http://www.bart.gov/");
        }
        s.start();
        seleniumControl.set(s);

        runInfo.put("sessionId", selenium().getEval("selenium.sessionId"));
    }

    @Test(groups = {"bart", "user"})
    public void testBartDirections() {
        selenium().open("/");
        selenium().windowMaximize();
        selenium().select("departing-station", "label=Civic Center (SF)");
        selenium().select("arriving-station", "label=12th St. Oakland City Center");
        selenium().select("travel-time", "label=7:30 AM");
        selenium().click("//form[@id='quick-planner']/fieldset/div[12]/a/span");
        selenium().waitForPageToLoad("30000");
    }
    
    @Test(groups = {"bart", "user"})
    public void testBartReverseDirections() {
        selenium().open("/");
        selenium().windowMaximize();
        selenium().select("departing-station", "label=12th St. Oakland City Center");
        selenium().select("arriving-station", "label=Civic Center (SF)");
        selenium().select("travel-time", "label=7:30 AM");
        selenium().click("//form[@id='quick-planner']/fieldset/div[12]/a/span");
        selenium().waitForPageToLoad("30000");
    }
    
    @Parameters({"ondemand"})
    @AfterMethod(alwaysRun=true)
    public void teardown(@Optional("false") String ondemand) throws MalformedURLException, IOException, InterruptedException {
        selenium().stop();
        if (ondemand.equals("true")) {
            DownloadVideo(runInfo, "reports");
            DownloadLog(runInfo, "reports");
        }
    }
}
