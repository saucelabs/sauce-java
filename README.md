Sauce Java Helper libraries
==========

This project contains helper libraries for consuming Sauce Labs services from Java using [TestNG](http://www.testng.org)
and/or [JUnit](http://www.junit.org).

The helper libraries provide the following functionality:

* Invoke the Sauce REST API to mark a Sauce Job as passed/failed, based on the test result
* Output the Sauce Session Id to the stdout, so that the Sauce Continuous Integration plugins (for Bamboo/Jenkins/Hudson)
can parse the output)
* Provide a com.saucelabs.common.SauceOnDemandAuthentication class, which handles obtaining the Sauce OnDemand user name
and access key from environment variables and/or the filesystem.

For JUnit projects, the library can be added to a project by including the following dependency:

```xml
<dependency>
    <groupId>com.saucelabs</groupId>
    <artifactId>sauce_junit</artifactId>
    <version>2.1.11</version>
    <scope>test</scope>
</dependency>
```

For TestNG projects, the library can be added to a project by including the following dependency:

```xml
<dependency>
    <groupId>com.saucelabs</groupId>
    <artifactId>sauce_testng</artifactId>
    <version>2.1.11</version>
    <scope>test</scope>
</dependency>
```

Note that the dependencies reside in the [Sauce Labs Maven repository](http://repository-saucelabs.forge.cloudbees.com/release/com/saucelabs/), which can be referenced by the following:

```xml
<repositories>
    <repository>
        <id>saucelabs-repository</id>
        <url>https://repository-saucelabs.forge.cloudbees.com/release</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>
```

[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/saucelabs/sauce-java/trend.png)](https://bitdeli.com/free "Bitdeli Badge")
