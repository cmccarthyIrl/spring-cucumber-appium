package com.cmccarthy.common.utils.helpers;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public interface KeyboardHelper extends TapHelper {

    default void keyboardPressKeyAction(AppiumDriver driver, String buttonText) {
        Map<String, String> action = new HashMap<>();
        action.put("action", buttonText);
        driver.executeScript("mobile:performEditorAction", action);
    }

    default void iosKeyboardClickLastButton(AppiumDriver driver) {
        tapWithoutValidation(driver, AppiumBy.iOSClassChain("**/XCUIElementTypeKeyboard[1]/**/XCUIElementTypeButton[-1]"));
    }

    default void iosKeyboardClickDoneButton(AppiumDriver driver) {
        tapWithoutValidation(driver, AppiumBy.iOSClassChain("**/XCUIElementTypeButton[`label == \"Done\"`]"));
    }
}
