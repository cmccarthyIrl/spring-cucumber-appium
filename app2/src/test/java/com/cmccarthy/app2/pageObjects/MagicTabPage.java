package com.cmccarthy.app2.pageObjects;

import com.cmccarthy.common.config.PageObject;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.util.List;

@PageObject
public class MagicTabPage extends TabBar {

    @AndroidFindBy(id = "login_tout_button")
    @iOSXCUITFindBy(id = "Sign up or Log in")
    public List<WebElement> signUpOrLogIn;

    public void clickSignUpOrLoginButton() {
        try {
            signUpOrLogIn.get(1).click();
        } catch (IndexOutOfBoundsException e) {
            signUpOrLogIn.get(0).click();
        }
    }
}
