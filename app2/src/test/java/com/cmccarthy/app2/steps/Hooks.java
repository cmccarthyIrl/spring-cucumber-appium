package com.cmccarthy.app2.steps;

import com.cmccarthy.app2.config.App2AbstractTestDefinition;
import com.cmccarthy.common.utils.HookUtils;
import com.cmccarthy.common.utils.LogManager;
import com.cmccarthy.common.utils.services.AppiumService;
import com.cmccarthy.common.utils.services.DriverService;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
public class Hooks extends App2AbstractTestDefinition {

    private final LogManager logger = new LogManager(Hooks.class);
    private final AppiumService server;

    private final HookUtils hookUtils;

    private final DriverService driverFactory;

    public Hooks(AppiumService server, HookUtils hookUtils, DriverService driverFactory) {
        this.server = server;
        this.hookUtils = hookUtils;
        this.driverFactory = driverFactory;
    }

    @After
    public void tearDown(Scenario scenario) {
        logger.debug("Started tearDown");
        hookUtils.takeScreenShot(scenario);
        driverFactory.getDriver().quit();
        server.stopService();
        hookUtils.closeEmulator();
        logger.debug("Finished tearDown");
    }

}