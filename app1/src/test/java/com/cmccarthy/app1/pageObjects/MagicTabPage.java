package com.cmccarthy.app1.pageObjects;

import com.cmccarthy.common.config.PageObject;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.util.List;

@PageObject
public class MagicTabPage extends TabBar {

    @AndroidFindBy(id = "login_tout_button")
    @iOSXCUITFindBy(id = "Sign up or Log in")
    public List <MobileElement> signUpOrLogIn;

    public LoginSignUpPage clickSignUpOrLoginButton() {
        try{
            signUpOrLogIn.get(1).click();
        }catch (IndexOutOfBoundsException e ) {
            signUpOrLogIn.get(0).click();
        }
        return new LoginSignUpPage();
    }
}
