package com.cmccarthy.common.utils;

import io.restassured.response.Response;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import java.io.FileWriter;
import java.security.SecureRandom;
import java.util.Base64;

public class Utility {
    LogManager logger = new LogManager(Utility.class);

    public void saveResponseToFile(Response response) {
        try {
            FileWriter file = new FileWriter(System.getProperty("user.dir") + "/src/test/java/debug.json");
            file.write(response.prettyPrint());
            file.flush();
            file.close();
        } catch (Exception ignored) {
        }
    }

    public void saveTextToFile(String response) {
        try {
            FileWriter file = new FileWriter(System.getProperty("user.dir") + "/src/test/java/debug.txt");
            file.write(response);
            file.flush();
            file.close();
        } catch (Exception ignored) {
        }
    }

    public void validateResponse(Response response, SoftAssert softAssert, int httpURLConnection) {

        if (response.getStatusCode() != httpURLConnection) {
            logger.warn("");
            softAssert.assertTrue(false, response.getHeaders().toString());
            if (response.getBody().asString().length() > 0) {
                Reporter.log("TRACEID" + response.prettyPrint());
                softAssert.assertAll();
            }
        }
    }

    public String base64Encoder(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }

    public String generateRandomString(int size) {
        final String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder builder = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            builder.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return builder.toString();
    }

}
