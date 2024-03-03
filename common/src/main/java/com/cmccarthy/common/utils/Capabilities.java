package com.cmccarthy.common.utils;

import com.cmccarthy.common.properties.DeviceProperties;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.ScreenOrientation;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class Capabilities {

    private final DeviceProperties deviceProperties;

    public Capabilities(DeviceProperties deviceProperties) {
        this.deviceProperties = deviceProperties;
    }

    public XCUITestOptions getIOSAppiumCapabilities() {
        final XCUITestOptions options = new XCUITestOptions();
        options.setDeviceName(deviceProperties.getDeviceName())
                .setPlatformVersion(deviceProperties.getPlatformVersion())
                .setPlatformName(deviceProperties.getPlatformName())
                .setBundleId(deviceProperties.getBundleId())
                .setApp(deviceProperties.getApp())
                .setAutomationName("XCUITest")
                .setFullReset(false)
                .setNoReset(false)
                .setOrientation(ScreenOrientation.PORTRAIT)
                .setNewCommandTimeout(Duration.ofSeconds(90))
                .setCommandTimeouts(Duration.ofMillis(90000))
                .setWdaConnectionTimeout(Duration.ofMillis(90000))
                .setWaitForIdleTimeout(Duration.ofMillis(90000));
        options.setUdid("auto");
        options.setCapability("appium:xcodeSigningId", "iPhone Developer");
        options.setCapability("appium:appInstallStrategy", "parallel");
        options.setCapability("appium:usePreinstalledWDA", true);
        options.setCapability("appium:prebuiltWDAPath", "/Users/" + System.getProperty("user.name") + "/IdeaProjects/spring-cucumber-appium/certs/WebDriverAgentRunner-Runner.app");
        return options;
    }

    public UiAutomator2Options getAndroidAppiumCapabilities() {
        final UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(deviceProperties.getDeviceName())
                .setPlatformVersion(deviceProperties.getPlatformVersion())
                .setPlatformName(deviceProperties.getPlatformName())
                .setAppActivity(deviceProperties.getAppActivity())
                .setAppPackage(deviceProperties.getAppPackage())
                .setApp(deviceProperties.getApp())
                .setAutomationName("UiAutomator2")
                .autoGrantPermissions()
                .setFullReset(true)
                .setNoReset(false)
                .setAllowTestPackages(true)
                .setOrientation(ScreenOrientation.PORTRAIT)
                .setNewCommandTimeout(Duration.ofMillis(90000))
                .setAvdLaunchTimeout(Duration.ofMillis(90000))
                .setAppWaitPackage(deviceProperties.getAppPackage())
                .setAppWaitDuration(Duration.ofMillis(90000))
                .setAdbExecTimeout(Duration.ofMillis(90000));
        options.setCapability("appium:appInstallStrategy", "parallel");
        return options;
    }

    public Object getCapabilities() {
        if (deviceProperties.getPlatformName().equalsIgnoreCase("ios")) {
            return getIOSAppiumCapabilities();
        } else {
            return getAndroidAppiumCapabilities();
        }
    }
}
