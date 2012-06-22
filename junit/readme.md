Sauce JUnit
==============

This project contains the source code for helper classes that can be included in JUnit test classes

Usage
-----

Examples in https://github.com/saucelabs/saucerest-java/blob/master/src/test/com/saucelabs/junit/WebDriverTest.java

JUnit
------

https://github.com/saucelabs/saucerest-java/issues/1

```java
public class XXTest implements SessionIdProvider {

public @Rule ResultReportingTestWatcher resultReportingTestWatcher =new ResultReportingTestWatcher(this,
sauceLabAccount, sauceLabAccessKey);
public @Rule TestName testName= new TestName();

private SessionId sessionId;

@Before
public void setUp() throws Exception {


    DesiredCapabilities capabillities = DesiredCapabilities.firefox();
    capabillities.setCapability("version", "5");
    capabillities.setCapability("platform", Platform.XP);
    capabillities.setCapability("name", "xxTest : "+testName.getMethodName());

    this.driver = new RemoteWebDriver(
            new URL("http://" + sauceLabAccount + ":" + sauceLabAccessKey + "@ondemand.saucelabs.com:80/wd/hub"),
            capabillities);
    sessionId=((RemoteWebDriver)driver).getSessionId();
}


@Override   
public String getSessionId() {
    return sessionId.toString();
}

// test methods
}
```