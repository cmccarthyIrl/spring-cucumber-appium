package com.cmccarthy.common.utils.extent;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Test;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExtentTests {

    private static final Map<String, ExtentTest> classTestMap = new HashMap<>();
    private static final ThreadLocal<ExtentTest> methodTest = new ThreadLocal<>();
    private static final ThreadLocal<ExtentTest> dataProviderTest = new ThreadLocal<>();

    public static synchronized ExtentTest getTest() {
        return dataProviderTest.get() == null
                ? methodTest.get()
                : dataProviderTest.get();
    }

    public static synchronized ExtentTest getTest(ITestResult result) {
        return result.getParameters() != null && result.getParameters().length > 0
                ? dataProviderTest.get()
                : methodTest.get();
    }

    public static synchronized ExtentTest createMethod(ITestResult result, Boolean createAsChild) {

        if (!createAsChild) {
            return createMethod(result);
        } else {
            String className = result.getTestContext().getName();
            String methodName = result.getMethod().getMethodName();
            String desc = result.getMethod().getDescription();
            ExtentTest classTest;

            if (classTestMap.containsKey(className)) {
                classTest = classTestMap.get(className);
            } else {
                classTest = ExtentReporter.extent.createTest(className, desc);
                classTestMap.put(className, classTest);
            }

            Optional<Test> test = classTest.getModel().getChildren().stream()
                    .filter(x -> x.getName().equals(methodName)).findFirst();

            if (result.getParameters().length > 0) {
                if (test.isEmpty()) {
                    createTest(result, classTest);
                }
                String paramName = Arrays.asList(result.getParameters()).toString();
                ExtentTest paramTest = methodTest.get().createNode(paramName);
                dataProviderTest.set(paramTest);
            } else {
                dataProviderTest.set(null);
                createTest(result, classTest);
            }

            return methodTest.get();
        }
    }

    public static synchronized ExtentTest createMethod(ITestResult result) {
        String methodName = result.getMethod().getMethodName();

        if (result.getParameters().length > 0) {
            if (methodTest.get() != null && methodTest.get().getModel().getName().equals(methodName)) {
            } else {
                createTest(result, null);
            }
            String paramName = Arrays.asList(result.getParameters()).toString();
            ExtentTest paramTest = methodTest.get().createNode(paramName);
            dataProviderTest.set(paramTest);
        } else {
            dataProviderTest.set(null);
            createTest(result, null);
        }
        return methodTest.get();
    }

    private static synchronized void createTest(ITestResult result, ExtentTest classTest) {
        String methodName = result.getMethod().getMethodName();
        String desc = result.getMethod().getDescription();

        if (methodName == null) {
            methodName = result.getMethod().getMethodName();
        }
        ExtentTest test;
        if (classTest != null) {
            test = classTest.createNode(methodName, desc);
        } else {
            test = ExtentReporter.extent.createTest(methodName, desc);
        }
        methodTest.set(test);

        String[] groups = result.getMethod().getGroups();
        assignGroups(test, groups);
    }

    private static void assignGroups(ExtentTest test, String[] groups) {
        if (groups.length > 0) {
            for (String g : groups) {
                if (g.startsWith("d:") || g.startsWith("device:")) {
                    String d = g.replace("d:", "").replace("device:", "");
                    test.assignDevice(d);
                } else if (g.startsWith("a:") || g.startsWith("author:")) {
                    String a = g.replace("a:", "").replace("author:", "");
                    test.assignAuthor(a);
                } else if (g.startsWith("t:") || g.startsWith("tag:")) {
                    String t = g.replace("t:", "").replace("tag:", "");
                    test.assignCategory(t);
                } else {
                    test.assignCategory(g);
                }
            }
        }
    }

    public static synchronized void logResult(ITestResult result, String msg) {
        Status status = getStatus(result);
        if (result.getThrowable() != null) {
            getTest(result).log(status, result.getThrowable());
            return;
        }
        getTest(result).log(status, msg);
    }

    public static synchronized void takeScreenShot(AppiumDriver driver) throws IOException {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        byte[] encoded;
        encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
        getTest().log(Status.INFO, "Snapshot",
                MediaEntityBuilder.createScreenCaptureFromBase64String(new String(encoded, StandardCharsets.US_ASCII)).build());
    }

    private static Status getStatus(ITestResult result) {
        Status status = Status.SKIP;
        if (result.getStatus() == 2) {
            status = Status.FAIL;
        }
        if (result.getStatus() == 1) {
            status = Status.PASS;
        }
        return status;

    }
}
