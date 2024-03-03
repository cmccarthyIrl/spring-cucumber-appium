package com.cmccarthy.common.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@PropertySource("classpath:application.properties")
public class DeviceProperties {
    @Value("${deviceName}")
    String deviceName;
    @Value("${platformVersion}")
    String platformVersion;
    @Value("${platformName}")
    String platformName;
    @Value("${bundleId:optional}")
    String bundleId;
    @Value("${app}")
    String app;
    @Value("${appActivity:optional}")
    String appActivity;
    @Value("${appPackage:optional}")
    String appPackage;

    @Bean("deviceName")
    public String getDeviceName() {
        return deviceName;
    }
    @Bean("platformVersion")
    public String getPlatformVersion() {
        return platformVersion;
    }
    @Bean("platformName")
    public String getPlatformName() {
        return platformName;
    }
    @Bean("bundleId")
    public String getBundleId() {
        return bundleId;
    }
    @Bean("app")
    public String getApp() {
        return app;
    }
    @Bean("appActivity")
    public String getAppActivity() {
        return appActivity;
    }
    @Bean("appPackage")
    public String getAppPackage() {
        return appPackage;
    }
}
