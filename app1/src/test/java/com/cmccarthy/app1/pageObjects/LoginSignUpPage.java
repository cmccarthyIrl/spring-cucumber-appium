package com.cmccarthy.app1.pageObjects;

import com.cmccarthy.common.config.PageObject;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.util.List;

@PageObject
public class LoginSignUpPage extends TabBar {

    @AndroidFindBy(id = "facebook_login_button")
    @iOSXCUITFindBy(id = "Log in with Facebook")
    private MobileElement logInWithFacebookButton;

    @AndroidFindBy(id = "login_button")
    @iOSXCUITFindBy(id = "Log in")
    private List<MobileElement> logInButton;

    @AndroidFindBy(id = "sign_up_button")
    @iOSXCUITFindBy(id = "Sign up")
    private MobileElement signUpButton;

    public boolean isLogInWithFacebookButtonDisplayed() {
        return logInWithFacebookButton.isDisplayed();
    }

    public boolean isSignUpButtonDisplayed() {
        return signUpButton.isDisplayed();
    }

    public boolean isLogInButtonDisplayed() {
        try {
            return logInButton.get(1).isDisplayed();
        } catch (IndexOutOfBoundsException e) {
            return logInButton.get(0).isDisplayed();
        }
    }
}
