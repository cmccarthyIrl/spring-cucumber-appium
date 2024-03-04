package com.cmccarthy.app2.steps;

import com.cmccarthy.app2.pageObjects.LoginSignUpPage;
import com.cmccarthy.app2.pageObjects.MagicTabPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;

public class MagicTabSteps {

    @Autowired
    private MagicTabPage magicTabPage;

    @Autowired
    private LoginSignUpPage loginSignUpPage;

    @Then("^I should see Login in Sign Up buttons$")
    public void iShouldSeeLoginButtons() {
        Assert.assertTrue(loginSignUpPage.isLogInWithFacebookButtonDisplayed());
        Assert.assertTrue(loginSignUpPage.isSignUpButtonDisplayed());
        Assert.assertTrue(loginSignUpPage.isLogInButtonDisplayed());
    }

    @Given("I tap Sign up or Log In button")
    public void iTapSignUpOrLogInButton() throws Exception {
        magicTabPage.clickSignUpOrLoginButton();
    }
}
