package com.cmccarthy.common.utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.cmccarthy.common.utils.extent.ExtentTests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogManager {

    private final Logger logger;

    public LogManager(Class<?> aClass) {
        try {
            logger = LoggerFactory.getLogger(aClass);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void info(String string) {
        ExtentTest currentTest = ExtentTests.getTest();
        if (currentTest != null) {
            ExtentTests.getTest().log(Status.INFO, string);
        }
        logger.info(string);
    }

    public void debug(String string) {
        ExtentTest currentTest = ExtentTests.getTest();
        if (currentTest != null) {
            ExtentTests.getTest().log(Status.INFO, string);
        }
        logger.debug(string);
    }

    public void warn(String string) {
        ExtentTest currentTest = ExtentTests.getTest();
        if (currentTest != null) {
            ExtentTests.getTest().log(Status.INFO, string);
        }
        logger.warn(string);
    }

    public void error(String string) {
        ExtentTest currentTest = ExtentTests.getTest();
        if (currentTest != null) {
            ExtentTests.getTest().log(Status.INFO, string);
        }
        logger.error(string);
    }

    public void info(Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
    }

    public void error(Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
    }

    public void warn(Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
    }

    public void debug(Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
    }
}
