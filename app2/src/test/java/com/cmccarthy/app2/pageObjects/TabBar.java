package com.cmccarthy.app2.pageObjects;

import com.cmccarthy.common.config.PageObject;
import com.cmccarthy.common.utils.Direction;
import com.cmccarthy.common.utils.LogManager;
import com.cmccarthy.common.utils.Screen;
import com.cmccarthy.common.utils.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

@PageObject
public abstract class TabBar extends Screen {

    private final LogManager logger = new LogManager(TabBar.class);

    double[] screenTop, screenBottom;

    TabBar() {
        this.screenTop = new double[]{0.2, 0.2};
        this.screenBottom = new double[]{0.9, 0.9};
    }

    @Autowired
    private AppiumDriver driver;
    @Autowired
    private DriverFactory driverFactory;
    @iOSXCUITFindBy(id = "Log in")
    private WebElement profile;

    public LoginSignUpPage clickProfileButton() throws Exception {
        swipeUntilElementIsVisible(driver, profile, screenTop[driverFactory.getPlatformIndex()], screenBottom[driverFactory.getPlatformIndex()], 2, 0.9, Direction.DOWN);
        tap(driver, profile);
        logger.info("The user tapped log in");
        return new LoginSignUpPage();
    }
}
