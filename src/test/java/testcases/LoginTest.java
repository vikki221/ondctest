package testcases;

import org.testng.Assert;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.microsoft.playwright.options.LoadState;
import pageobjects.LoginPage;
import testbase.BaseClass;
import utilities.ConfigReader;
import listeners.CustomListener;

@Listeners(CustomListener.class)
public class LoginTest extends BaseClass {

    private ConfigReader configReader;
    private LoginPage lp;
    private String username;
    private String password;

    @BeforeClass
    public void initialize() {
        configReader = new ConfigReader();
        username = configReader.getUsername();
        password = configReader.getPassword();
        lp = new LoginPage(page);
    }

    // TC_001: Verify valid login
    @Test(priority = 1)
    public void testValidLogin() {
        lp.enterUsername(username);
        lp.enterPassword(password);
        lp.clickLogin();

        boolean isDashboardVisible = page.locator("xpath=//span[normalize-space()='Dashboard']").isVisible();
        Assert.assertTrue(isDashboardVisible, "Login was unsuccessful or dashboard not visible!");
    }

    // TC_002: Verify login with invalid password
    @Test(priority = 2)
    public void testInvalidLogin() {
        lp.enterUsername(username);
        lp.enterPassword("invalidPassword");
        lp.clickLogin();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message was not displayed for invalid login.");
    }

    // TC_003: Verify login with empty fields
    @Test(priority = 3)
    public void testEmptyFieldsLogin() {
        lp.clearUsername();
        lp.clearPassword();
        lp.clickLogin();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message not displayed when both fields are empty.");
    }

    // TC_004: Verify login with blank username and valid password
    @Test(priority = 4)
    public void testBlankUsernameWithValidPassword() {
        lp.clearUsername();
        lp.enterPassword(password);
        lp.clickLogin();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message not displayed for blank username.");
    }

    // TC_005: Verify login with blank password and valid username
    @Test(priority = 5)
    public void testBlankPasswordWithValidUsername() {
        lp.clearPassword();
        lp.enterUsername(username);
        lp.clickLogin();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message not displayed for blank password.");
    }

    // TC_006: Verify password masking
    @Test(priority = 6)
    public void testPasswordMasking() {
        lp.enterPassword(password);
        Assert.assertTrue(lp.isPasswordMasked(), "Password is not masked.");
    }

    // TC_007: Verify login with Remember Me enabled
    @Test(priority = 7)
    public void testLoginWithRememberMe() {
        lp.enterUsername(username);
        lp.enterPassword(password);
        lp.clickrm();
        lp.clickLogin();

        page.waitForLoadState(LoadState.NETWORKIDLE);
        boolean isDashboardVisible = page.locator("xpath=//span[normalize-space()='Dashboard']").isVisible();
        Assert.assertTrue(isDashboardVisible, "Login was unsuccessful or dashboard not visible!");

        lp.clickProfile();
        lp.clickLogout();

        page.waitForLoadState(LoadState.NETWORKIDLE);
        lp.clickLogin();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        boolean isDashboardVisibleAfterReLogin = page.locator("xpath=//span[normalize-space()='Dashboard']").isVisible();
        Assert.assertTrue(isDashboardVisibleAfterReLogin, "Login was unsuccessful or dashboard not visible after re-login!");
    }

    // TC_008: Verify login with long username and password
    @Test(priority = 8)
    public void testLongUsernamePassword() {
        String longUsername = "user" + "a".repeat(256);
        String longPassword = "password" + "b".repeat(256);

        lp.enterUsername(longUsername);
        lp.enterPassword(longPassword);
        lp.clickLogin();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message should be displayed for long username and password.");
    }

    // TC_009: Verify case sensitivity of username
    @Test(priority = 9)
    public void testCaseSensitivity() {
        String alteredUsername = username.toUpperCase();
        lp.enterUsername(alteredUsername);
        lp.enterPassword(password);
        lp.clickLogin();

        boolean isDashboardVisible = page.locator("xpath=//span[normalize-space()='Dashboard']").isVisible();
        Assert.assertTrue(isDashboardVisible, "Login was unsuccessful due to case sensitivity issues.");
    }

    // TC_010: Verify error message consistency
    @Test(priority = 10)
    public void testErrorMessageConsistency() {
        lp.enterUsername("incorrectUsername");
        lp.enterPassword("incorrectPassword");
        lp.clickLogin();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message should be consistent across invalid login attempts.");
    }

    // TC_011: Verify login with expired password
    @Test(priority = 11)
    public void testExpiredPasswordLogin() {
        lp.enterUsername(username);
        lp.enterPassword("expiredPassword");
        lp.clickLogin();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "User should be prompted to reset their password for expired password.");
    }

    // TC_012: Verify password visibility toggle
    @Test(priority = 12)
    public void testPasswordVisibilityToggle() {
        lp.enterPassword(password);
        lp.clickPasswordVisibilityToggle();

        Assert.assertTrue(lp.isPasswordVisible(), "Password should be visible after clicking the toggle.");
    }

    // TC_013: Verify Forgot Password link is clickable
    @Test(priority = 13)
    public void testForgotPasswordLink() {
        lp.clickForgotPassword();

        String currentUrl = page.url();
        Assert.assertTrue(currentUrl.contains("forgot-password"), "User was not redirected to Forgot Password page.");
    }

    // TC_014: Verify password reset with registered email
    @Test(priority = 14)
    public void testPasswordResetWithRegisteredEmail() {
        lp.clickForgotPassword();
        lp.enterEmail("registeredemail@example.com");
        lp.submitPasswordReset();

        Assert.assertTrue(lp.isResetEmailSent(), "Password reset email should be sent.");
    }

    // TC_015: Verify password reset with unregistered email
    @Test(priority = 15)
    public void testPasswordResetWithUnregisteredEmail() {
        lp.clickForgotPassword();
        lp.enterEmail("unregisteredemail@example.com");
        lp.submitPasswordReset();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message should be displayed for unregistered email.");
    }

    // TC_016: Verify password reset email format
    @Test(priority = 16)
    public void testPasswordResetEmailFormat() {
        lp.clickForgotPassword();
        lp.enterEmail("registeredemail@example.com");
        lp.submitPasswordReset();

        Assert.assertTrue(lp.isResetEmailFormatValid(), "Password reset email should contain a secure link.");
    }

    // TC_017: Verify user can set a new password
    @Test(priority = 17)
    public void testResetPassword() {
        lp.clickForgotPassword();
        lp.enterEmail("registeredemail@example.com");
        lp.submitPasswordReset();

        lp.enterNewPassword("newPassword123");
        lp.submitNewPassword();

        Assert.assertTrue(lp.isPasswordUpdated(), "User should be able to log in with the new password.");
    }

    // TC_018: Verify Sign-Up link is clickable
    @Test(priority = 18)
    public void testSignUpLink() {
        lp.clickSignUp();

        String currentUrl = page.url();
        Assert.assertTrue(currentUrl.contains("sign-up"), "User should be redirected to the Sign-Up page.");
    }
 // TC_019: Verify registration with Google login
    @Test(priority = 19)
    public void testValidGoogleRegistration() {
        lp.clickGoogleSignUp();

        Assert.assertTrue(lp.isRegistrationSuccessful(), "User should be able to register and log in successfully with Google.");
    }

    // TC_020: Verify registration with an existing Google account
    @Test(priority = 20)
    public void testRegistrationWithExistingGoogleAccount() {
        lp.clickGoogleSignUp();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message should be displayed for existing Google account.");
    }

 
}
