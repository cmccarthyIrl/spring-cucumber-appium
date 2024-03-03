package com.cmccarthy.common.utils.helpers;


import com.cmccarthy.common.utils.LogManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.NoSuchElementException;

public interface SendKeysHelper {

    LogManager logger = new LogManager(SendKeysHelper.class);

    default void sendKeys(WebElement element, String characters) {
        try {
            element.clear();
            element.sendKeys(characters);
        } catch (Exception ex) {
            try {
                element.clear();
                element.sendKeys(characters);
            } catch (Exception exx) {
                logger.warn("Could not send keys to the element");
                throw new NoSuchElementException("Could not send keys on the element : " + exx.getMessage());
            }
        }
    }

    default void sendKeysAction(AppiumDriver driver, WebElement element, String characters) {
        try {
            element.clear();
            new Actions(driver)
                    .sendKeys(element, characters)
                    .perform();
        } catch (Exception exx) {
            logger.warn("Could not send keys to the element");
            throw new NoSuchElementException("Could not send keys on the element : " + exx.getMessage());
        }
    }
}
