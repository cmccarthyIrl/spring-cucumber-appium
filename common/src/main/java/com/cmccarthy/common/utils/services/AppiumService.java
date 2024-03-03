package com.cmccarthy.common.utils.services;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class AppiumService {

    private AppiumDriverLocalService server;

    protected void startAppiumServer() {
        if (server == null || !server.isRunning()) {
            AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
            serviceBuilder.withIPAddress("127.0.0.1");
            serviceBuilder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
            serviceBuilder.usingAnyFreePort();
            server = AppiumDriverLocalService.buildService(serviceBuilder);
            server.start();
        }
    }

    public void stopService() {
        server.stop();
    }

    protected URL getServerUrl() {
        return server.getUrl();
    }
}
