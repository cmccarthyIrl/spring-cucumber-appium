package com.cmccarthy.app1.pageObjects;

import com.cmccarthy.common.config.PageObject;
import com.cmccarthy.common.utils.MobileWait;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

@PageObject
public abstract class TabBar extends MobileWait {

    @iOSXCUITFindBy(id = "Log in")
    private MobileElement profile;

    public LoginSignUpPage clickProfileButton() {
        profile.click();
        return new LoginSignUpPage();
    }
}
