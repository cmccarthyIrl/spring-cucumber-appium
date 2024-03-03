package com.cmccarthy.common.utils.helpers;

import com.cmccarthy.common.utils.LogManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.NoSuchElementException;

public interface WaitHelper {
    LogManager logger = new LogManager(WaitHelper.class);

    default Wait<AppiumDriver> getWait(AppiumDriver driver) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(System.getProperty("INTEGRATION").equals("local") ? 30 : 60))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
    }


    default void waitForElementClickable(AppiumDriver driver, By locator) {
        getWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    default void waitForPresenceOfElement(AppiumDriver driver, By element) {
        try {
            getWait(driver).until(ExpectedConditions.presenceOfElementLocated(element));
        } catch (Exception exception) {
            logger.warn("Something went wrong waiting for the element to be found:" + exception.getMessage());
        }
    }

    default void waitForPresenceOfNestedElement(AppiumDriver driver, By parent, By element) {
        try {
            getWait(driver).until(ExpectedConditions.presenceOfNestedElementsLocatedBy(parent, element));
        } catch (Exception exception) {
            logger.warn("Something went wrong waiting for the element to be found:" + exception.getMessage());
        }
    }

    default void waitForInVisibilityOfElement(AppiumDriver driver, By element) {
        try {
            getWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(element));
        } catch (Exception exception) {
            logger.warn("Something went wrong waiting for the element to be found:" + exception.getMessage());
        }
    }
}
