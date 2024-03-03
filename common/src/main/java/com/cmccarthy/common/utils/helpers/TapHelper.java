package com.cmccarthy.common.utils.helpers;

import com.cmccarthy.common.utils.LogManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import static java.time.Duration.ofMillis;

public interface TapHelper extends WaitHelper {

    LogManager logger = new LogManager(TapHelper.class);

    default void tap(AppiumDriver driver, WebElement element) throws NoSuchElementException {
        try {
            getWait(driver).until(ExpectedConditions.visibilityOf(element)).click();
        } catch (Exception ignored) {
            ignored.printStackTrace();
            logger.warn("Could not tap on the element, try tapping again");
            try {
                getWait(driver).until(ExpectedConditions.visibilityOf(element)).click();
            } catch (Exception ex1) {
                logger.warn("Could not tap on the element");
                throw new NoSuchElementException("Could not tap on the element : " + ex1.getMessage());
            }
        }
    }

    default void tapAction(AppiumDriver driver, By locator) {
        try {
            WebElement element = driver.findElement(locator);
            Actions actions = new Actions(driver);
            actions.moveToElement(element).click().perform();
        } catch (Exception ex) {
            logger.warn("Could not tap on the element");
            throw new NoSuchElementException("Could not tap on the element : " + ex.getMessage());
        }
    }

    default void tapAction(AppiumDriver driver, WebElement element) {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(element).click().perform();
        } catch (Exception ex) {
            logger.warn("Could not tap on the element");
            throw new NoSuchElementException("Could not tap on the element : " + ex.getMessage());
        }
    }

    default void tapWithoutValidation(AppiumDriver driver, By locator) throws NoSuchElementException {
        try {
            driver.findElement(locator).click();
        } catch (Exception ex1) {
            throw new NoSuchElementException("Could not tap on the element : " + ex1.getMessage());
        }
    }

    default void tapWithoutValidationWebDriver(AppiumDriver driver, By locator) throws NoSuchElementException {
        try {
            ((WebDriver) driver).findElement(locator).click();
        } catch (Exception ex1) {
            throw new NoSuchElementException("Could not tap on the element : " + ex1.getMessage());
        }
    }

    /**
     * Android only
     *
     * @param element
     */
    default void clickWithJavaScript(AppiumDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    /**
     * Android only
     *
     * @param locator
     */
    default void clickWithJavaScript(AppiumDriver driver, By locator) {
        JavascriptExecutor executor = driver;
        WebElement element = driver.findElement(locator);
        executor.executeScript("arguments[0].click();", element);
    }

    default void doubleTapElement(AppiumDriver driver, By locator) {
        WebElement element = driver.findElement(locator);

        Point location = element.getLocation();
        Dimension dimension = element.getSize();

        Point centerOfElement = new Point(location.getX() + dimension.getWidth() / 2
                , location.getY() + dimension.getHeight() / 2);
        ;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);

        tap.addAction(
                        finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerOfElement))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger, Duration.ofMillis(100)))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger, Duration.ofMillis(100)));
//                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        try {
            driver.perform(List.of(tap));
        } catch (InvalidElementStateException exception) {
            exception.printStackTrace();
        }
    }

    default void tapMidPoint(AppiumDriver driver) {
        var size = driver.manage().window().getSize();
        var pointX = (size.getWidth() / 2);
        var pointY = (size.getHeight() / 2);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);

        tap.addAction(finger.createPointerMove(ofMillis(10), PointerInput.Origin.viewport(), pointX, pointY));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(tap));
        logger.debug("The User tapped the middle of the screen");
    }

}
