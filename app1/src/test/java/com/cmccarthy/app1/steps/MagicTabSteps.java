package com.cmccarthy.app1.steps;

import com.cmccarthy.app1.pageObjects.LoginSignUpPage;
import com.cmccarthy.app1.pageObjects.MagicTabPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

public class MagicTabSteps {

    @Autowired
    private MagicTabPage magicTabPage;

    @Autowired
    private LoginSignUpPage loginSignUpPage;

    @Then("^I should see Login in Sign Up buttons$")
    public void iShouldSeeLoginButtons() {
        assertTrue(loginSignUpPage.isLogInWithFacebookButtonDisplayed());
        assertTrue(loginSignUpPage.isSignUpButtonDisplayed());
        assertTrue(loginSignUpPage.isLogInButtonDisplayed());
    }

    @Given("I tap Sign up or Log In button")
    public void iTapSignUpOrLogInButton() {
        magicTabPage.clickSignUpOrLoginButton();
    }
}
