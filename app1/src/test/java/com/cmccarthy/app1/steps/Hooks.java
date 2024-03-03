package com.cmccarthy.app1.steps;

import com.cmccarthy.app1.config.App1AbstractTestDefinition;
import com.cmccarthy.common.utils.AppiumServer;
import com.cmccarthy.common.utils.HookUtils;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

@CucumberContextConfiguration
public class Hooks extends App1AbstractTestDefinition {

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