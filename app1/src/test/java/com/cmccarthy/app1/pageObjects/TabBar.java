package com.cmccarthy.app1.pageObjects;

import com.cmccarthy.common.config.PageObject;
import com.cmccarthy.common.utils.Screen;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

@PageObject
public abstract class TabBar extends Screen {

    @iOSXCUITFindBy(id = "Log in")
    private WebElement profile;

    public LoginSignUpPage clickProfileButton() {
        profile.click();
        return new LoginSignUpPage();
    }
}
