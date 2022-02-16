package com.cmccarthy.common.utils;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MobileWait {

    @Autowired
    private WebDriverWait mobileDriverWait;

    protected void waitForElementToAppear(MobileElement locator) {
        mobileDriverWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForElementToDisappear(MobileElement locator) {
        mobileDriverWait.until(ExpectedConditions.invisibilityOf(locator));
    }
}
