package com.cmccarthy.common.utils;

import com.cmccarthy.common.properties.DeviceProperties;
import com.cmccarthy.common.utils.services.AppiumService;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.time.Duration;

@Configuration
@Lazy
public class DriverFactory {

    private final LogManager logger = new LogManager(DriverFactory.class);
    private final AppiumService appiumService;
    private final DeviceProperties deviceProperties;
    private final Capabilities capabilities;
    private AppiumDriver driver;

    public DriverFactory(AppiumService appiumService, DeviceProperties deviceProperties, Capabilities capabilities) {
        this.appiumService = appiumService;
        this.deviceProperties = deviceProperties;
        this.capabilities = capabilities;
    }

    @Bean(name = "mobileDriver")
    public AppiumDriver mobileDriver() {
        appiumService.startAppiumServer();
        switch (deviceProperties.getPlatformName().toLowerCase()) {
            case "ios":
                try {
                    driver = new IOSDriver(appiumService.getServerUrl(), ((XCUITestOptions) capabilities.getCapabilities()));
                } catch (Exception exception) {
                    logger.error(exception.getMessage());
                }
                break;
            case "android":
                try {
                    driver = new AndroidDriver(appiumService.getServerUrl(), ((UiAutomator2Options) capabilities.getCapabilities()));
                } catch (Exception exception) {
                    logger.error(exception.getMessage());

                }
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + deviceProperties.getPlatformName());
        }
        return driver;
    }

    public AppiumDriver getDriver() {
        return driver;
    }


    public boolean isAndroid() {
        return deviceProperties.getPlatformName().equalsIgnoreCase("Android");
    }

    public boolean isIOS() {
        return deviceProperties.getPlatformName().equalsIgnoreCase("iOS");
    }

    public void setDriverTimeout(int timeout) {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
    }

    public void setDriverTimeoutDefault() {
        setDriverTimeout(System.getProperty("INTEGRATION").equals("local") ? 30 : 60);
    }

    public int getPlatformIndex() {
        if(isAndroid()){
            return 0;
        }else{
            return 1;
        }
    }
}
