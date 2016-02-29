package com.saucelabs.testng;

/**
 * @author Mehmet Gerceker
 */

import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.*;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.assertEquals;


/**
 * Somewhat malformed TestNg test to test the test listener class.
 * Capturing stdout from the test listener on success and failure requires test to complete,
 * hence we need to validate test output after the test is run in the @AfterMethod function.
 *
 * @author Mehmet Gerceker
 */
@Listeners({SauceOnDemandTestListener.class})
public class SauceOnDemandListenerTest extends SauceTestBase{


    protected PrintStream stdoutStream;
    protected ByteArrayOutputStream outputCapture;

    @BeforeMethod
    public void startOutputCapture() {
        outputCapture = new ByteArrayOutputStream();
        PrintStream outputStream = new PrintStream(outputCapture);
        //back up the actual stdout
        stdoutStream = System.out;
        //set stdout to our stream
        System.setOut(outputStream);
    }

    @AfterMethod
    public void stopOutputCapture(ITestResult testResult) {
        //Recover stdout
        System.out.flush();
        System.setOut(stdoutStream);
        String output =  this.outputCapture.toString();
        Pair<String, String> jobInfo = parseSessionIdJobName(output);
        if(jobInfo.getLeft() == null ||
            jobInfo.getLeft().toLowerCase().contentEquals("null")){
            //this seems not to work well
            testResult.setStatus(ITestResult.FAILURE);
            //test has failed!
            throw new AssertionError("SessionId is not valid!");
        }
        if(jobInfo.getRight() == null ||
            jobInfo.getRight().toLowerCase().contentEquals("null")){
            //this seems not to work well
            testResult.setStatus(ITestResult.FAILURE);
            //test has failed!
            throw new AssertionError("job-name is not valid!");

        }

    }

    protected Pair<String, String> parseSessionIdJobName(String output) {
        Pattern sessionIdPattern = Pattern.compile("SauceOnDemandSessionID=([0-9a-fA-F]+)(?:.job-name=(.*))?");
        String sessionId = null;
        String jobName = null;
        BufferedReader lineReader = new BufferedReader(new StringReader(output));
        String line;

        try {
            while ((line = lineReader.readLine()) != null && sessionId == null && jobName == null) {
                //make sure it is a possible match it can't be less than 32 chars
                if (line.length() < 32) {
                    continue;
                }
                Matcher m = sessionIdPattern.matcher(line);
                while (m.find()) {
                    //find the single line we need to find and get out.
                    if (m.groupCount() == 2) {
                        jobName = m.group(2);
                        sessionId = m.group(1);
                        break;
                    }
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return Pair.of(sessionId, jobName);
    }

    /**
     * Simple hard-coded DataProvider that explicitly sets the browser combination to be used.
     *
     * @param testMethod Test method consuming the data
     * @return 2D array of data (2D Array of strings)
     */
    @DataProvider(name = "singleBrowser", parallel = false)
    public static Object[][] sauceSingleBrowserDataProvider(Method testMethod) {
        return new Object[][]{
            new Object[]{"chrome", "43", "Windows 2012"}
        };
    }


    @Test(dataProvider = "singleBrowser", expectedExceptions = AssertionError.class)
    public void testSessionIdTestNameOutputFailure(String browser, String version, String os) throws Exception {
        WebDriver driver = createDriver(browser, version, os);
        logger.log(Level.INFO, "Running test using " + browser + " " + version + " " + os);
        driver.get("https://saucelabs.com/test/guinea-pig");
        assertEquals(driver.getTitle(), "This is not it!");
        driver.quit();
    }

    @Test(dataProvider = "singleBrowser")
    public void testSessionIdTestNameOutputSuccess(String browser, String version, String os) throws Exception {
        WebDriver driver = createDriver(browser, version, os);
        logger.log(Level.INFO, "Running test using " + browser + " " + version + " " + os);
        driver.get("https://saucelabs.com/test/guinea-pig");
        assertEquals(driver.getTitle(), "I am a page title - Sauce Labs");
        driver.quit();
    }
}
