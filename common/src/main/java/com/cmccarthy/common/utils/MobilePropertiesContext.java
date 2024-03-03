package com.cmccarthy.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class MobilePropertiesContext {

    @Value("${platform.name}")
    private String platform;

    @Value("${platform.version}")
    private String version;

    @Value("${device.name}")
    private String name;

    @Value("${avd}")
    private String avd;

    @Bean("platform")
    public String getPlatform() {
        return platform;
    }

    @Bean("version")
    public String getVersion() {
        return version;
    }

    @Bean("name")
    public String getName() {
        return name;
    }

    @Bean("avd")
    public String getAvd() {
        return avd;
    }
}
