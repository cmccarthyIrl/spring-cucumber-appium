package com.cmccarthy.common.utils.services;

import com.cmccarthy.common.properties.DeviceProperties;
import com.cmccarthy.common.utils.Capabilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.time.Duration;

@Configuration
@Lazy
public class DriverService {

    @Autowired
    @Lazy
    private AppiumService appiumService;

    @Autowired
    @Lazy
    private DeviceProperties deviceProperties;

    @Autowired
    @Lazy
    private Capabilities capabilities;

    private AppiumDriver driver;

    @Bean(name = "mobileDriver")
    public AppiumDriver mobileDriver() {
        appiumService.startAppiumServer();
        switch (deviceProperties.getPlatformName().toLowerCase()) {
            case "ios":
                try {
                    driver = new IOSDriver(appiumService.getServerUrl(), ((XCUITestOptions) capabilities.getCapabilities()));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                break;
            case "android":
                try {
                    driver = new AndroidDriver(appiumService.getServerUrl(), ((UiAutomator2Options) capabilities.getCapabilities()));
                } catch (Exception exception) {
                    exception.printStackTrace();
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

    @Bean(name = "mobileDriverWait")
    public WebDriverWait mobileDriverWait() {
        int WAIT_TIMEOUT = 10;
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT));
    }

    public void setDriverTimeout(int timeout) {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
    }

    public void setDriverTimeoutDefault() {
        setDriverTimeout(System.getProperty("INTEGRATION").equals("local") ? 30 : 60);
    }

}
