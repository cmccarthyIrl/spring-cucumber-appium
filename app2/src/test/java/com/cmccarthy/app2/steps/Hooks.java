package com.cmccarthy.app2.steps;

import com.cmccarthy.app2.config.App2AbstractTestDefinition;
import com.cmccarthy.common.utils.AppiumServer;
import com.cmccarthy.common.utils.MobileDriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Autowired;

@CucumberContextConfiguration
public class Hooks extends App2AbstractTestDefinition {

    @Autowired
    private AppiumServer server;

    @Autowired
    private MobileDriverFactory mobileDriverFactory;

    @After
    public void tearDown(Scenario scenario) {
        takeScreenShot(scenario);
        server.stopService();
    }

    private void takeScreenShot(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) mobileDriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png",scenario.getName());
        }
    }

}