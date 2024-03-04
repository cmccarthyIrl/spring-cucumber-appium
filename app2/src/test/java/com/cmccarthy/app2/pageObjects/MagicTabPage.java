package com.cmccarthy.app2.pageObjects;

import com.cmccarthy.common.config.PageObject;
import com.cmccarthy.common.utils.Direction;
import com.cmccarthy.common.utils.LogManager;
import com.cmccarthy.common.utils.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@PageObject
public class MagicTabPage extends TabBar {

    double[] screenTop, screenBottom;

    MagicTabPage() {
        this.screenTop = new double[]{0.2, 0.2};
        this.screenBottom = new double[]{0.9, 0.9};
    }

    private final LogManager logger = new LogManager(MagicTabPage.class);
    @Autowired
    private AppiumDriver driver;
    @Autowired
    private DriverFactory driverFactory;

    @AndroidFindBy(id = "login_tout_button")
    @iOSXCUITFindBy(id = "Sign up or Log in")
    public List<WebElement> signUpOrLogIn;

    public void clickSignUpOrLoginButton() throws Exception {
        try {
            swipeUntilElementIsVisible(driver, signUpOrLogIn.get(1), screenTop[driverFactory.getPlatformIndex()], screenBottom[driverFactory.getPlatformIndex()], 2, 0.9, Direction.DOWN);
            tap(driver, signUpOrLogIn.get(1));
        } catch (IndexOutOfBoundsException e) {
            swipeUntilElementIsVisible(driver, signUpOrLogIn.get(0), screenTop[driverFactory.getPlatformIndex()], screenBottom[driverFactory.getPlatformIndex()], 2, 0.9, Direction.DOWN);
            tap(driver, signUpOrLogIn.get(0));
        }
        logger.info("The user tapped sign up or login");
    }
}
