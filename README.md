Sauce Java Helper libraries
==========

[![codecov.io](https://codecov.io/github/saucelabs/sauce-java/coverage.svg?branch=master)](https://codecov.io/github/saucelabs/sauce-java?branch=master)

This project contains helper libraries for consuming Sauce Labs services from Java using [TestNG](http://www.testng.org)
and/or [JUnit](http://www.junit.org).

The helper libraries provide the following functionality:

* Invoke the Sauce REST API to mark a Sauce Job as passed/failed, based on the test result
* Output the Sauce Session Id to the stdout, so that the Sauce Continuous Integration plugins (for Bamboo/Jenkins/Hudson)
can parse the output)
* Provide a com.saucelabs.common.SauceAuthentication class, which handles obtaining the Sauce Labs user name
and access key from environment variables or system properties.

For JUnit projects, the library can be added to a project by including the following dependency:

```xml
<dependency>
    <groupId>com.saucelabs</groupId>
    <artifactId>sauce_junit</artifactId>
    <version>2.1.21</version>
    <scope>test</scope>
</dependency>
```

For TestNG projects, the library can be added to a project by including the following dependency:

```xml
<dependency>
    <groupId>com.saucelabs</groupId>
    <artifactId>sauce_testng</artifactId>
    <version>2.1.21</version>
    <scope>test</scope>
</dependency>
```

[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/saucelabs/sauce-java/trend.png)](https://bitdeli.com/free "Bitdeli Badge")
