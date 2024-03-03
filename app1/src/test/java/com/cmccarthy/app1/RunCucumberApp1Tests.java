package com.cmccarthy.app1;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {"src/test/resources/features"},
        plugin = {
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        glue = {"com/cmccarthy/app1", "com/cmccarthy/common"})
public class RunCucumberApp1Tests extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
