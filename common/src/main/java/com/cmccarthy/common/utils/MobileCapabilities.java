package com.cmccarthy.common.utils;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@ContextConfiguration(classes = {MobilePropertiesContext.class})
public class MobileCapabilities {

    @Autowired
    private String platform;

    @Autowired
    private String version;

    @Autowired
    private String name;

    @Autowired
    private String avd;

    private final DesiredCapabilities capabilities = new DesiredCapabilities();
    private final Path path = Paths.get("src", "test", "resources");

    private void setCapabilities() {
        if (platform.equals("ios")) {
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platform);
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, version);
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, name);
            capabilities.setCapability(MobileCapabilityType.APP, path.toFile().getAbsolutePath().concat("/app/KickDebug.app"));
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
            capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
            capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
        } else if (platform.equals("android")) {
            if(avd.isEmpty()) {
                throw new IllegalArgumentException("Expected an avd but was empty");
            }
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platform);
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, version);
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, name);
            capabilities.setCapability(MobileCapabilityType.APP, path.toFile().getAbsolutePath().concat("/app/base.apk"));
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
            capabilities.setCapability("avd",avd);
        } else {
            throw new IllegalArgumentException("Platform must be ios or android");
        }
    }

    protected DesiredCapabilities getCapabilities() {
        setCapabilities();
        return capabilities;
    }

    protected String getPlatform() {
        return platform;
    }
}
