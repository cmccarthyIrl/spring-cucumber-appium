package com.cmccarthy.app2.steps;

import com.cmccarthy.app2.config.App2AbstractTestDefinition;
import com.cmccarthy.common.utils.AppiumServer;
import com.cmccarthy.common.utils.HookUtils;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

@CucumberContextConfiguration
public class Hooks extends App2AbstractTestDefinition {

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