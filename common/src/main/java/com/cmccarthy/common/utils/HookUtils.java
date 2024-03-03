package com.cmccarthy.common.utils;

import com.cmccarthy.common.utils.services.DriverService;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

@Component
public class HookUtils {

    private final LogManager logger = new LogManager(HookUtils.class);

    private final DriverService driverService;

    public HookUtils(DriverService driverService) {
        this.driverService = driverService;
    }

    public void takeScreenShot(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) driverService.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
    }

    public void closeEmulator() {
        logger.debug("Started close emulator");
        try {
            Process process;
            if (getOperatingSystem().equals("win")) {
                process = Runtime.getRuntime().exec("cmd.exe /c adb -s emulator-5554 emu kill", null);
            } else {
                process = Runtime.getRuntime().exec(new String[]{"sh", "-c adb -s emulator-5554 emu kill"});
            }
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            while (line != null) {
                System.out.println("line = " + line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.debug("Finished closed emulator");
    }


    private String getOperatingSystem() {
        String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ROOT);
        if (os.contains("mac") || os.contains("darwin")) {
            logger.debug("The operating system is Mac");
            return "mac";
        } else if (os.contains("win")) {
            logger.debug("The operating system is Windows");
            return "win";
        } else {
            logger.debug("The operating system is Linux");
            return "linux";
        }
    }

}
