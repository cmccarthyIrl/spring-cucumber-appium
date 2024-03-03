package com.cmccarthy.common.utils.services;

import com.cmccarthy.common.utils.LogManager;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class AppiumService {

    LogManager logger = new LogManager(AppiumService.class);

    private AppiumDriverLocalService server;

    protected void startAppiumServer() {
        logger.debug("Starting the Appium server");
        if (server == null || !server.isRunning()) {
            AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
            serviceBuilder.withIPAddress("127.0.0.1");
            serviceBuilder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
            serviceBuilder.usingAnyFreePort();
            server = AppiumDriverLocalService.buildService(serviceBuilder);
            server.start();
            logger.debug("Finished starting the Appium server");
        } else {
            logger.warn("The Appium server is already running");
        }

    }

    public void stopService() {
        logger.debug("Stop the Appium server");
        server.stop();
        logger.debug("Finished stopping the Appium server");
    }

    protected URL getServerUrl() {
        URL url = server.getUrl();
        logger.debug("The Appium server URL is: " + url.toString());
        return url;
    }
}
