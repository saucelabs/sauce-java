Sauce Java Helper libraries
==========

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/4655f1e8e1fa4c3bae6487e8cc09b23e)](https://app.codacy.com/app/nikolay.advolodkin/sauce-java?utm_source=github.com&utm_medium=referral&utm_content=saucelabs/sauce-java&utm_campaign=Badge_Grade_Dashboard)
[![codecov.io](https://codecov.io/github/saucelabs/sauce-java/coverage.svg?branch=master)](https://codecov.io/github/saucelabs/sauce-java?branch=master)

This project contains helper libraries for consuming Sauce Labs services from Java:

How to use
------
1. Import the dependency into your pom.xml
```xml
<dependency>
    <groupId>com.saucelabs</groupId>
    <artifactId>sauce_junit</artifactId>
    <version>2.1.21</version>
    <scope>test</scope>
</dependency>
```
2. Create object

RemoteWebDriver driver = new RemoteWebDriver(new URL(SAUCE_REMOTE_URL), caps);
SauceHelper sauce = new SauceHelper(driver);

add an informational comment to your test

```sauce.comment("Open the Google page");```

tell Sauce Labs the status of your test

```sauce.setTestStatus("pass");  //pass,fail,true,false```

set the name of your test case in Sauce Labs

```sauce.setTestName("shouldOpenGooglePage");```


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
