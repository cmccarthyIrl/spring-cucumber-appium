package com.cmccarthy.app2.pageObjects;

import com.cmccarthy.common.config.PageObject;
import com.cmccarthy.common.utils.LogManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@PageObject
public class MagicTabPage extends TabBar {

    private final LogManager logger = new LogManager(MagicTabPage.class);
    @Autowired
    AppiumDriver driver;

    @AndroidFindBy(id = "login_tout_button")
    @iOSXCUITFindBy(id = "Sign up or Log in")
    public List<WebElement> signUpOrLogIn;

    public void clickSignUpOrLoginButton() {
        try {
            tap(driver, signUpOrLogIn.get(1));
        } catch (IndexOutOfBoundsException e) {
            tap(driver, signUpOrLogIn.get(0));
        }
        logger.info("The user tapped sign up or login");
    }
}
