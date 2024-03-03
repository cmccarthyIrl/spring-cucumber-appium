package com.cmccarthy.common.utils.helpers;

import com.cmccarthy.common.utils.Direction;
import com.cmccarthy.common.utils.LogManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.List;

import static java.time.Duration.ofMillis;

public interface SwipeHelper extends ElementHelper {

    LogManager logger = new LogManager(SwipeHelper.class);

    default void swipeElementLeft(AppiumDriver driver, WebElement element) {
        var size = element.getSize();
        var pointX = element.getLocation().getX() + (size.getWidth() / 2);
        var pointY = element.getLocation().getY() + (size.getHeight() / 2);
        swipe(driver, pointX, pointY, 0, pointY);
        logger.info("The User swiped the element to the left");
    }

    default void swipeElementRight(AppiumDriver driver, WebElement element) {
        var size = element.getSize();
        var pointY = element.getLocation().getY() + (size.getHeight() / 2);
        swipe(driver, 0, pointY, size.getWidth(), pointY);
        logger.info("The User swiped the element to the right");
    }


    default void swipeScreenInDirection(AppiumDriver driver, double screenTop, double screenBottom, Direction direction,
                                        double distance,
                                        int numberOfTimes) {
        for (int x = 0; x < numberOfTimes; x++) {
            swipeScreenInDirection(driver, screenTop, screenBottom, direction, distance);
        }
    }

    default void swipeScreenInDirection(AppiumDriver driver, double screenTop, double screenBottom, Direction direction, double distance) {
        if (distance < 0 || distance > 1) {
            throw new Error("Scroll distance must be between 0 and 1");
        }

        Dimension windowSize = driver.manage().window().getSize();
        Point midPoint = new Point((int) (windowSize.width * 0.5), (int) (windowSize.height * 0.5));

        int top = (int) (windowSize.height * screenTop);
        int bottom = (int) (windowSize.height * screenBottom);
        int left = midPoint.x - (int) ((windowSize.width * distance) * 0.5);
        int right = midPoint.x + (int) ((windowSize.width * distance) * 0.5);

        if (direction.equals(Direction.UP)) {
            swipe(driver, midPoint.x, top, midPoint.x, bottom);
        } else if (direction.equals(Direction.DOWN)) {
            swipe(driver, midPoint.x, bottom, midPoint.x, top);
        } else if (direction.equals(Direction.LEFT)) {
            swipe(driver, left, midPoint.y, right, midPoint.y);
        } else {
            swipe(driver, right, midPoint.y, left, midPoint.y);
        }
    }

    private void swipe(AppiumDriver driver, int fromX, int fromY, int toX, int toY) {
        logger.debug("Swiping from X=" + fromX + ", Y=" + fromY + " to X=" + toX + ", Y=" + toY);
        try {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipeFromTo = new Sequence(finger, 1);

            swipeFromTo.addAction(finger.createPointerMove(ofMillis(0), PointerInput.Origin.viewport(), fromX, fromY));
            swipeFromTo.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipeFromTo.addAction(finger.createPointerMove(ofMillis(2000), PointerInput.Origin.viewport(), toX, toY));
            swipeFromTo.addAction(new Pause(finger, Duration.ofMillis(500)));
            swipeFromTo.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            swipeFromTo.addAction(new Pause(finger, Duration.ofMillis(3000))); //wait for the scroll animation to settle
            driver.perform(List.of(swipeFromTo));

        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    default boolean swipeUntilElementIsVisible(AppiumDriver driver, double screenTop, double screenBottom, int numTimes,
                                               WebElement element, double distance, Direction direction) throws Exception {

        for (int x = 0; x < numTimes; x++) {
            try {
                String isElementDisplayed = isElementOnScreen(driver, screenTop, screenBottom, element);
                if (!isElementDisplayed.equals("true")) {
                    navigateUpOrDown(driver, screenTop, screenBottom, distance, isElementDisplayed);
                }
                return true;
            } catch (Exception e) {
                logger.debug("Scrolling " + direction + " (default) to find the element");
                swipeScreenInDirection(driver, screenTop, screenBottom, direction, distance);
            }
        }
        throw new Exception("Could not find the element: " + element.toString() + " after scrolling " + numTimes + " times");
    }

    private void navigateUpOrDown(AppiumDriver driver, double screenTop, double screenBottom, double distance, String isElementDisplayed) {
        if (isElementDisplayed.equals("up")) {
            logger.debug("Scrolling up to find the element");
            swipeScreenInDirection(driver, screenTop, screenBottom, Direction.UP, distance);
        } else {
            logger.debug("Scrolling down to find the element");
            swipeScreenInDirection(driver, screenTop, screenBottom, Direction.DOWN, distance);
        }
    }

    default boolean swipeUntilElementIsVisible(AppiumDriver driver, double screenTop, double screenBottom, int numTimes, By locator,
                                               double distance, Direction direction) throws Exception {
        WebElement element;
        for (int x = 0; x < numTimes; x++) {
            try {
                element = driver.findElement(locator);

                String isElementDisplayed = isElementOnScreen(driver, screenTop, screenBottom, element);
                if (!isElementDisplayed.equals("true")) {
                    navigateUpOrDown(driver, screenTop, screenBottom, distance, isElementDisplayed);
                }
                return true;
            } catch (Exception e) {
                logger.debug("Scrolling " + direction + " to find the element: " + locator);
                swipeScreenInDirection(driver, screenTop, screenBottom, direction, distance);
            }
        }
        throw new Exception("Could not find the locator: " + locator.toString());
    }

    default void swipeUpByElement(AppiumDriver driver, By locator) throws Exception {
        try {
            WebElement element = driver.findElement(locator);

            int pointX = element.getLocation().getX();
            int pointY = element.getLocation().getY();
            int bottomY = element.getLocation().getY() - 200;

            swipe(driver, pointX, pointY, pointX, bottomY);
            Thread.sleep(5000);// //--> wait for the swipe to settle
        } catch (Exception ex) {
            throw new Exception("Could not swipe up the element");
        }
    }

}
