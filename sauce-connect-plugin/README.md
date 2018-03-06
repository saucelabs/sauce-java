This project contains the source code for the Sauce Connect Maven plugin.

The plugin provides two mojos:

* start-sauceconnect - invokes a Sauce Connect process
* stop-sauceconnect - stops a running Sauce Connect process

The mojo accepts the following configuration parameters:

* sauceUsername (mandatory) - the name of the Sauce user used to launch the Sauce Connect process
* sauceAccessKey (mandatory) - the access key of the Sauce user used to launch the Sauce Connect process
* port - the port which Sauce Connect will be launched on, defaults to 4445
* httpsProtocol -

The plugin can be included in a project by adding the following to your pom.xml file:

```xml
<build>
    <plugins>
        <!-- Include Sauce Connect plugin -->
        <plugin>
            <groupId>com.saucelabs.maven.plugin</groupId>
            <artifactId>sauce-connect-plugin</artifactId>
            <version>2.1.3</version>
            <configuration>
                <sauceUsername>YOUR_SAUCE_USERNAME</sauceUsername>
                <sauceAccessKey>YOUR_SAUCE_ACCESS_KEY</sauceAccessKey>
            </configuration>
            <executions>
                <!-- Start Sauce Connect prior to running the integration tests -->
                <execution>
                    <id>start-sauceconnct</id>
                    <phase>pre-integration-test</phase>
                    <goals>
                        <goal>start-sauceconnect</goal>
                    </goals>
                </execution>
                <!-- Stop the Sauce Connect process after the integration tests have finished -->
                <execution>
                    <id>stop-sauceconnect</id>
                    <phase>post-integration-test</phase>
                    <goals>
                        <goal>stop-sauceconnect</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
        ...
    </plugins>
</build>
```
