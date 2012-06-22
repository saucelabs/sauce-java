TestNG Sauce
============

Installation
-------------

 1. git clone git://github.com/sgrove/testng_sauce.git
 2. Add your credentials to xml/BartOnDemand.xml
 3. Run `ant cloud`

There are two test methods that will run in parallel, in the cloud, in the browser specified in xml/BartOnDemand.xml.

TODO
----

 1. Add in easy xml-based configuration for cross-browser testing

 

Background
----------
This is meant to provide a robust starting point for Sauce Labs and TestNG users. Design decisions made in this program force tests to be written in a clean manner that will scale (in terms of parallelization) very well.

Thanks
------

  - [Adam Goucher][1] - Bulk of the structure in this package
  - [Adam Christian][2] - Pressing me to release this publicly
  - [Sauce Labs][3] - Sponsors of this project


  [1]: http://adam.goucher.ca/
  [2]: http://adamchristian.com/
  [3]: http://saucelabs.com