package com.cmccarthy.common.utils.services;

import com.cmccarthy.common.utils.LogManager;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;

@Service
public class AppiumService {

//    LogManager logger = new LogManager(AppiumService.class);
    private static final Logger logger = LoggerFactory.getLogger(AppiumService.class);


    private AppiumDriverLocalService server;

    public void startAppiumServer() {
        logger.debug("Starting the Appium server");
        if (server == null || !server.isRunning()) {
            AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
            serviceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "info"); // Set log level to info
            serviceBuilder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
            serviceBuilder.withArgument(GeneralServerFlag.BASEPATH, "/wd/hub/");
            serviceBuilder.withIPAddress("127.0.0.1");
            serviceBuilder.usingAnyFreePort();
//            serviceBuilder.withLogFile(new File(System.getProperty("user.dir") + "/logs/appium.log"));
            server = AppiumDriverLocalService.buildService(serviceBuilder);
//            server.clearOutPutStreams();//comment out this line to print the logs
//            server.addLogMessageConsumer(logMessage -> logger.info(logMessage));
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

    public URL getServerUrl() {
        URL url = server.getUrl();
        logger.debug("The Appium server URL is: " + url.toString());
        return url;
    }
}
