Sauce Java Helper libraries
==========

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/4655f1e8e1fa4c3bae6487e8cc09b23e)](https://app.codacy.com/app/nikolay.advolodkin/sauce-java?utm_source=github.com&utm_medium=referral&utm_content=saucelabs/sauce-java&utm_campaign=Badge_Grade_Dashboard)
[![codecov.io](https://codecov.io/github/saucelabs/sauce-java/coverage.svg?branch=master)](https://codecov.io/github/saucelabs/sauce-java?branch=master)

This project contains helper libraries for consuming Sauce Labs services from Java using [TestNG](http://www.testng.org)
and/or [JUnit](http://www.junit.org).

The helper libraries provide the following functionality:
* Invoke the Sauce REST API to mark a Sauce Job as passed/failed, based on the test result
* Output the Sauce Session Id to the stdout, so that the Sauce Continuous Integration plugins (for Bamboo/Jenkins/Hudson)
can parse the output)

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

Thanks and Acknowledgements
------

  - [Adam Goucher][1]
  - [Adam Christian][2]
  - [Ross Row][3]
  - [Mehmet Gerceker][3]
  - [Sauce Labs][3] 


  [1]: http://adam.goucher.ca/
  [2]: http://adamchristian.com/
  [3]: http://saucelabs.com
