package com.cmccarthy.common.utils.helpers;

import com.cmccarthy.common.utils.LogManager;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public interface ElementHelper extends WaitHelper {

    LogManager logger = new LogManager(ElementHelper.class);

    default boolean isNestedElementDisplayed(WebElement element, By locator) {
        try {
            return element.findElement(locator).isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }

    default boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }

    default boolean isElementDisplayed(AppiumDriver driver, By locator) {
        boolean displayed;
        try {
            displayed = driver.findElement(locator).isDisplayed();
            return displayed;
        } catch (Exception ex) {
            return false;
        }
    }

    default boolean isElementSelected(AppiumDriver driver, By locator) {
        try {
            return driver.findElement(locator).isSelected();
        } catch (Exception ex) {

            return false;
        }
    }

    default boolean isElementEnabled(AppiumDriver driver, By locator) {
        try {
            return driver.findElement(locator).isEnabled();
        } catch (Exception ex) {

            return false;
        }
    }

    default String getElementAttribute(AppiumDriver driver, By locator, String attribute) {
        try {
            return driver.findElement(locator).getAttribute(attribute);
        } catch (Exception ex) {

            return "false";
        }
    }

    default String getElementAttribute(WebElement element, String attribute) {
        try {
            return element.getAttribute(attribute);
        } catch (Exception ex) {

            return "false";
        }
    }

    default String getElementText(WebElement ele) {
        List<String> locators = new ArrayList<>();
        locators.add("label");
        locators.add("name");
        locators.add("content-desc");
        locators.add("text");

        String attributeValue;
        for (String s : locators) {
            try {
                attributeValue = ele.getAttribute(s);
                if (!attributeValue.isEmpty()) {
                    return attributeValue;
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    default Point getElementCentre(Point location, Dimension dimension) {
        return new Point(location.getX() + dimension.getWidth() / 2
                , location.getY() + dimension.getHeight() / 2);
    }

    default Point getElementBottom(Point location, Dimension dimension) {
        Point centerPoint = getElementCentre(location, dimension);
        return new Point(centerPoint.getX()
                , centerPoint.getY() + dimension.getHeight() / 2);
    }

    default Point getElementTop(Point location, Dimension dimension) {
        Point centerPoint = getElementCentre(location, dimension);
        return new Point(centerPoint.getX()
                , centerPoint.getY() - dimension.getHeight() / 2);
    }

    /**
     * @return - return 'up' if the element is above the top of the page
     * - return 'down' if the element is below the bottom of the page
     * - return 'true' if all the element is displayed on screen
     */
    default String isElementOnScreen(AppiumDriver driver, double screenTop, double screenBottom, WebElement element) {
        var windowSize = driver.manage().window().getSize();
        int top = (int) (windowSize.height * screenTop);
        int bottom = (int) (windowSize.height * screenBottom);
        Point elementTop = getElementTop(element.getLocation(), element.getSize());
        Point elementBottom = getElementBottom(element.getLocation(), element.getSize());

        if (elementBottom.getY() > bottom) {
            logger.debug("Determined that the bottom: " + elementBottom.getY() + " of the element is below the bottom: " + bottom + " of the page");
            return "down";
        } else if (elementTop.getY() < top) {
            logger.debug("Determined that the top: " + elementTop.getY() + " of the element is above the top: " + top + " of the page");
            return "up";
        } else {
            logger.debug("Determined that all of the element is displayed on the screen");
            logger.debug("Determined that the bottom: " + elementBottom.getY() + " of the element is above the bottom: " + bottom + " of the page");
            logger.debug("Determined that the top: " + elementTop.getY() + " of the element is below the top: " + top + " of the page");
            return "true";
        }
    }

    default WebElement refreshElement(AppiumDriver driver, WebElement element) throws Exception {
        String elementInfo = element.toString();
        String[] locatorTypes =
                {"xpath", "css", "id", "name", "link text", "partial link text", "tag name", "class name", "accessibility id", "ios class chain",
                        "ios predicate string", "android ui automator", "android view tag"};

        //get the By locator from the element
        //if the element is a nested element, extract the locators and append them
        try {
            StringBuilder extractLocators = getStringBuilder(locatorTypes, elementInfo);

            //find the element based on the new By locator
            WebElement retWebEl;
            if (elementInfo.contains("-> link text:")) {
                retWebEl = driver.findElement(AppiumBy.linkText(extractLocators.toString()));
            } else if (elementInfo.contains("-> name:")) {
                retWebEl = driver.findElement(AppiumBy.name(extractLocators.toString()));
            } else if (elementInfo.contains("-> id:")) {
                retWebEl = driver.findElement(AppiumBy.id(extractLocators.toString()));
            } else if (elementInfo.contains("-> xpath:")) {
                retWebEl = driver.findElement(AppiumBy.xpath(extractLocators.toString()));
            } else if (elementInfo.contains("-> class name:")) {
                retWebEl = driver.findElement(AppiumBy.className(extractLocators.toString()));
            } else if (elementInfo.contains("-> tag name:")) {
                retWebEl = driver.findElement(AppiumBy.tagName(extractLocators.toString()));
            } else if (elementInfo.contains("-> android ui automator:")) {
                retWebEl = driver.findElement(AppiumBy.androidUIAutomator(extractLocators.toString()));
            } else if (elementInfo.contains("-> accessibility id:")) {
                retWebEl = driver.findElement(AppiumBy.accessibilityId(extractLocators.toString()));
            } else if (elementInfo.contains("-> ios class chain:")) {
                retWebEl = driver.findElement(AppiumBy.iOSClassChain(extractLocators.toString()));
            } else if (elementInfo.contains("-> ios predicate string:")) {
                retWebEl = driver.findElement(AppiumBy.iOSNsPredicateString(extractLocators.toString()));
            } else {
                throw new Exception("Could not find a match for the element locator: " + extractLocators);
            }
            return retWebEl;
        } catch (Exception exception) {
            throw new Exception("Could not refresh the elements location: " + exception.getMessage());
        }
    }

    private static StringBuilder getStringBuilder(String[] locatorTypes, String elementInfo) {
        StringBuilder extractLocators = new StringBuilder();
        for (String locatorType : locatorTypes) {
            String separator = "-> " + locatorType + ": ";
            int index = elementInfo.indexOf(separator);
            while (index != -1) {
                int endIndex = elementInfo.indexOf("]", index);
                if (endIndex != -1) {
                    String locator = elementInfo.substring(index + separator.length(), endIndex + 1);
                    extractLocators.append(locator);
                    index = elementInfo.indexOf(separator, endIndex);
                } else {
                    break;
                }
            }
        }
        return extractLocators;
    }
}
