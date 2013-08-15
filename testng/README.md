Sauce TestNG Helper Library
============

Installation
-------------

 1. Include the following in your Maven pom.xml file

```xml
    <dependencies>
        <dependency>
            <groupId>com.saucelabs</groupId>
            <artifactId>sauce_testng</artifactId>
            <version>[1.0.0,)</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <repositories>
        <repository>
            <id>saucelabs-repository</id>
            <url>http://repository-saucelabs.forge.cloudbees.com/release</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>
```

2. Reference the SauceOnDemandTestListener via the following in your testng.xml file:

```
    <listeners>
        <listener class-name="com.saucelabs.testng.SauceOnDemandTestListener"/>
    </listeners>

```

3. Reference the listener within your test class, eg.

```java
@Listeners({SauceOnDemandTestListener.class})
public class WebDriverWithHelperTest implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {

}
```
Thanks
------

  - [Adam Goucher][1] - Bulk of the structure in this package
  - [Adam Christian][2] - Pressing me to release this publicly
  - [Sauce Labs][3] - Sponsors of this project


  [1]: http://adam.goucher.ca/
  [2]: http://adamchristian.com/
  [3]: http://saucelabs.com