package com.cmccarthy.common.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MobileDriverFactory {

    @Autowired
    private AppiumServer appiumServer;

    @Autowired
    private MobileCapabilities mobileCapabilities;

    private AppiumDriver<MobileElement> driver;

    private int WAIT_TIMEOUT = 10;

    @Bean(name = "mobileDriver")
    public AppiumDriver<MobileElement> mobileDriver() {
        appiumServer.startAppiumServer();
        switch (mobileCapabilities.getPlatform()) {
            case "ios":
                try {
                    driver = new IOSDriver<>(appiumServer.getServerUrl(), mobileCapabilities.getCapabilities());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "android":
                try {
                    driver = new AndroidDriver<>(appiumServer.getServerUrl(), mobileCapabilities.getCapabilities());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + mobileCapabilities.getPlatform().toLowerCase());
        }
        return driver;
    }

    public AppiumDriver<MobileElement> getDriver() {
        return driver;
    }

    @Bean(name = "mobileDriverWait")
    public WebDriverWait mobileDriverWait() {
        return new WebDriverWait(driver, WAIT_TIMEOUT);
    }
}
