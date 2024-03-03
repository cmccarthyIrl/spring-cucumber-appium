package com.cmccarthy.app2.pageObjects;

import com.cmccarthy.common.config.PageObject;
import com.cmccarthy.common.utils.LogManager;
import com.cmccarthy.common.utils.Screen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

@PageObject
public abstract class TabBar extends Screen {

    private final LogManager logger = new LogManager(TabBar.class);

    @Autowired
    AppiumDriver driver;

    @iOSXCUITFindBy(id = "Log in")
    private WebElement profile;

    public LoginSignUpPage clickProfileButton() {
        tap(driver, profile);
        logger.info("The user tapped log in");
        return new LoginSignUpPage();
    }
}
