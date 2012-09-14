import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import com.thoughtworks.selenium.CommandProcessor;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import com.thoughtworks.selenium.DefaultSelenium;

import java.lang.IllegalAccessException;
import java.lang.NoSuchFieldException;
import java.lang.Object;
import java.lang.reflect.Field;
import java.net.URL;

import static junit.framework.Assert.assertEquals;

/**
 * Simple {@link org.openqa.selenium.remote.RemoteWebDriver} test that demonstrates how to run your Selenium tests with <a href="http://saucelabs.com/ondemand">Sauce OnDemand</a>.
 *
 * This test also includes the <a href="">Sauce JUnit</a> helper classes, which will use the Sauce REST API to mark the Sauce Job as passed/failed.
 *
 * In order to use the {@link SauceOnDemandTestWatcher}, the test must implement the {@link SauceOnDemandSessionIdProvider} interface.
 *
 * @author Ross Rowe
 */
public class SeleniumRCWithHelperTest implements SauceOnDemandSessionIdProvider {

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication("${sauceUserName}", "${sauceAccessKey}");

    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    public @Rule
    SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    /**
     * JUnit Rule which will record the test name of the current test.  This is referenced when creating the {@link org.openqa.selenium.remote.DesiredCapabilities},
     * so that the Sauce Job is created with the test name.
     */
    public @Rule TestName testName= new TestName();

    private DefaultSelenium selenium;

    private String sessionId;

    @Before
    public void setUp() throws Exception {

        DefaultSelenium selenium = new DefaultSelenium(
                "ondemand.saucelabs.com",
                80,
                "{\"username\": \"" + authentication.getUsername() + "\"," +
                        "\"access-key\": \"" + authentication.getAccessKey() + "\"," +
                        "\"os\": \"mac\"," +
                        "\"browser\": \"iphone\"," +
                        "\"browser-version\": \"5.0\"," +
                        "\"name\": \"Testing Selenium 1 with Java on Sauce\"}",
                "http://saucelabs.com/");
        selenium.start();
        this.selenium = selenium;
        this.sessionId = getSessionIdFromSelenium();
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    public String getSessionIdFromSelenium() {
        try {
            Field commandProcessorField = DefaultSelenium.class.getDeclaredField("commandProcessor");
            commandProcessorField.setAccessible(true);
            CommandProcessor commandProcessor = (CommandProcessor) commandProcessorField.get(selenium);
            Field f = commandProcessor.getClass().getDeclaredField("sessionId");
            f.setAccessible(true);
            Object id = f.get(commandProcessor);
            if (id != null) {
                return id.toString();
            }
        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        }
        return null;

    }

    @Test
    public void seleniumRCWithHelper() throws Exception {
        this.selenium.open("http://www.amazon.com");
        assertEquals("Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more", this.selenium.getTitle());
    }

    @After
    public void tearDown() throws Exception {
        this.selenium.stop();
    }

}
