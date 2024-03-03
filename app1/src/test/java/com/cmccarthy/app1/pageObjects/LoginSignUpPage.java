package com.cmccarthy.app1.pageObjects;

import com.cmccarthy.common.config.PageObject;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.util.List;

@PageObject
public class LoginSignUpPage extends TabBar {

    @AndroidFindBy(id = "facebook_login_button")
    @iOSXCUITFindBy(id = "Log in with Facebook")
    private WebElement logInWithFacebookButton;

    @AndroidFindBy(id = "login_button")
    @iOSXCUITFindBy(id = "Log in")
    private List<WebElement> logInButton;

    @AndroidFindBy(id = "sign_up_button")
    @iOSXCUITFindBy(id = "Sign up")
    private WebElement signUpButton;

    public boolean isLogInWithFacebookButtonDisplayed() {
        return isElementDisplayed(logInWithFacebookButton);
    }

    public boolean isSignUpButtonDisplayed() {
        return isElementDisplayed(signUpButton);
    }

    public boolean isLogInButtonDisplayed() {
        try {
            return isElementDisplayed(logInButton.get(1));
        } catch (IndexOutOfBoundsException e) {
            return isElementDisplayed(logInButton.get(0));
        }
    }
}
