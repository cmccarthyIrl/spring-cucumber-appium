# Appium Test Automation Framework

## Index

| Start | Run | Report | Advanced |
|-------|-----|--------|----------|
| [Maven](#maven) \| [Quickstart](#quickstart) | [TestNG](#testng) \| [Command Line](#command-line) \| [IDE Support](#ide-support) \| [Java JDK](#java-jdk) \| [Troubleshooting](#troubleshooting) | [Configuration](#configuration) \| [Environment Switching](#environment-switching) \| [Extent Reports](#extent-reports) \| [Allure Reports](#allure-reports) \| [Logging](#logging) \| [Before / After Hooks](#before--after-hooks) | [JSON Transforms](#json-transforms) \| [Contributing](#contributing) |

# Maven


The Framework uses [Spring Boot Test](https://spring.io/guides/gs/testing-web/), [Cucumber](https://cucumber.io/) and [Appium](https://appium.io/) client implementations.

Spring `<dependencies>`:

```xml
<dependencies>
    ...
    <dependency>
        <groupId>org.springframework.amqp</groupId>
        <artifactId>spring-rabbit</artifactId>
        <version>${spring-rabbit.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-test</artifactId>
    </dependency>
    ...
</dependencies>
```

Cucumber `<dependencies>`:

```xml
<dependencies>
    ...
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-java</artifactId>
        <version>${cucumber.version}</version>
    </dependency>
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-spring</artifactId>
        <version>${cucumber.version}</version>
    </dependency>
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-testng</artifactId>
        <version>${cucumber.version}</version>
    </dependency>
    ...
</dependencies>
```

Appium `<dependencies>`:

```xml
<dependencies>
    ...
  <dependency>
    <groupId>io.appium</groupId>
    <artifactId>java-client</artifactId>
    <version>${appium.version}</version>
  </dependency>
    ...
</dependencies>
```

# Quickstart

**Install**:

- [Intellij IDE](https://www.jetbrains.com/idea/) - `Recommended`
- [Java JDK 17](https://jdk.java.net/java-se-ri/11)
- [Android Studio / SDK](https://developer.android.com/studio)
- [Apache Maven 3.6.3](https://maven.apache.org/docs/3.6.3/release-notes.html)

**Set**:

```markdown
set ANDROID_HOME=C:\Users\<USER>\AppData\Local\Android\Sdk
set ANDROID_ROOT_DIR=C:\Users\<USER>\AppData\Local\Android\Sdk
set PATH=%PATH%;%ANDROID_HOME%\tools;%ANDROID_HOME%\platform-tools
```

# TestNG

By using the [TestNG Framework](https://testng.org/doc/), we can utilize the [Cucumber Framework](https://cucumber.io/) and the `@CucumberOptions` Annotation Type to execute the `*.feature` file tests.

> Right-click the `WikipediParallelRunner` class and select `Run`

```java
@CucumberOptions(
        features = {
                "src/test/resources/feature"
        },
        plugin = {
                "pretty",
                "json:target/cucumber/report.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        })
public class RunCucumberApp1Test extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}
```

# Command Line

Normally, you will use your IDE to run a `*.feature` file directly or via the `*Test.java` class. With the `Test` class, we can run tests from the command-line as well.

Note that the `mvn test` command only runs test classes that follow the `*Test.java` naming convention.

You can run a single test or a suite or tests like so:

```
mvn test -Dtest=RunCucumberApp1Test
```

Note that the `mvn clean install` command runs all test Classes that follow the `*Test.java` naming convention.

```
mvn clean install
```

# IDE Support

To minimize the discrepancies between IDE versions and locales the `<sourceEncoding>` is set to `UTF-8`

```xml
<properties>
    ...
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    ...
</properties>
```

# Java JDK

The Java version to use is defined in the `maven-compiler-plugin`

```xml
<build>
    ...
    <pluginManagement>
        <plugins>
            ...
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>11</source>
                    <target>11</target>
                </configuration>
            </plugin>
            ...
        </plugins>
    </pluginManagement>
    ...
</build>
```

# Configuration

The `AbstractTestDefinition` class is responsible for specifying each Step class as `@SpringBootTest` and its `@ContextConfiguration`

```java
@ContextConfiguration(classes = {FrameworkContextConfiguration.class})
@SpringBootTest
public class AbstractTestDefinition {
}
```

The `FrameworkContextConfiguration` class is responsible for specifying the Spring `@Configuration`, modules to scan, properties to use, etc.

```java
@EnableRetry
@Configuration
@ComponentScan({
        "com.cmccarthy.app1", "com.cmccarthy.common",
})
@PropertySource("application.properties")
public class FrameworkContextConfiguration {
}
```

# Environment Switching

There is only one thing you need to do to switch the environment - which is to set `<activeByDefault>` property in the Master POM.

> By default, the value of `spring.profiles.active` is defined in the `application.properties` file which inherits its value from the Master POM property `<activeByDefault>`

```xml
<profiles>
    ...
    <profile>
        <id>prod</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <properties>
            <activatedProperties>prod</activatedProperties>
        </properties>
    </profile>
    ...
</profiles>
```

You can then specify the profile to use when running Maven from the command line like so:

```
mvn clean install -P dev
```

Below is an example of the `application.properties` file.

```properties
spring.profiles.active=@
```

# Extent Reports

The Framework uses [Extent Reports Framework](https://extentreports.com/) to generate the HTML Test Reports

The example below is a report generated automatically by Extent Reports open-source library.

<img src="https://github.com/cmccarthyIrl/spring-cucumber-testng-parallel-test-harness/blob/master/common/src/main/resources/demo/extent-report.jpg" height="400px"/>

# Allure Reports

The Framework uses [Allure Reports](https://docs.qameta.io/allure/) to generate the HTML Test Reports

The example below is a report generated by Allure Reports open-source library.

<img src="https://github.com/cmccarthyIrl/spring-cucumber-testng-parallel-test-harness/blob/master/common/src/main/resources/demo/allure-report.png" height="400px"/>

To generate the above report navigate to the root directory of the module under test and execute the following command

`mvn allure:serve`  or `mvn allure:generate` (for an offline report)

# Logging

The Framework uses [Log4j2](https://logging.apache.org/log4j/2.x/) You can instantiate the logging service in any Class like so

```java
private final Logger logger=LoggerFactory.getLogger(WikipediaPageSteps.class);
```

you can then use the logger like so :

```java
logger.info("This is a info message");
logger.warn("This is a warning message");
logger.debug("This is a info message");
logger.error("This is a error message");
```

# Before / After Hooks

The [Logback](http://logback.qos.ch/) logging service is initialized from the `Hooks.class`

As the Cucumber Hooks are implemented by all steps we can configure the `@CucumberContextConfiguration` like so :

```java
@CucumberContextConfiguration
public class Hooks extends AbstractTestDefinition {

    @Autowired
    private AppiumServer server;
  
    @Autowired
    private HookUtils hookUtils;
  
    @After
    public void tearDown(Scenario scenario) {
      hookUtils.takeScreenShot(scenario);
      server.stopService();
      hookUtils.closeEmulator();
    }
}
```

# Troubleshooting

- Execute the following commands to resolve any dependency issues
  1. `cd ~/install directory path/spring-cucumber-testng-parallel-test-harness`
  2. `mvn clean install -DskipTests`

# Contributing

Spotted a mistake? Questions? Suggestions?

[Open an Issue](https://github.com/cmccarthyIrl/spring-cucumber-appium/issues)
