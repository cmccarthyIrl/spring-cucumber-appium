package com.cmccarthy.app1.steps;

import com.cmccarthy.app1.config.App1AbstractTestDefinition;
import com.cmccarthy.common.utils.services.AppiumService;
import com.cmccarthy.common.utils.HookUtils;
import com.cmccarthy.common.utils.services.DriverService;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

@CucumberContextConfiguration
public class Hooks extends App1AbstractTestDefinition {

    @Autowired
    private AppiumService server;

    @Autowired
    private HookUtils hookUtils;

    @Autowired
    private DriverService driverFactory;

    @After
    public void tearDown(Scenario scenario) {
        try {
            hookUtils.takeScreenShot(scenario);
            driverFactory.getDriver().quit();
            server.stopService();
            hookUtils.closeEmulator();
        } catch (Exception ignored) {
        }
    }
}