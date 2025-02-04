package testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import pageobjects.LoginPage;
import utilities.ConfigReader;

public class LoginTest {
	
	protected Playwright playwright;
    protected Browser browser;
    protected Page page;
    protected LoginPage lp;
    private ConfigReader configReader;
	
	 @BeforeMethod
	    public void setUp() {
	        configReader = new ConfigReader();
	        String url = configReader.getURL();
	        playwright = Playwright.create();
	        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
	        page = browser.newPage();
	        page.navigate(url);

	        lp = new LoginPage(page);

	    }


    @Test(priority = 1)
    public void testValidLogin() throws InterruptedException {
        lp.enterUsername("admin");
        lp.enterPassword("admin");
        lp.clickLogin();
        Thread.sleep(4000);
        boolean isDashboardVisible = page.locator("xpath=//span[normalize-space()='Dashboard']").isVisible();
        Assert.assertTrue(isDashboardVisible, "Login was unsuccessful or dashboard not visible!");
    }

    @Test(priority = 2)
    public void testInvalidLogin() {
        lp.enterUsername("admin");
        lp.enterPassword("invalidPassword");
        lp.clickLogin();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message was not displayed for invalid login.");
    }

    @Test(priority = 3)
    public void testEmptyFieldsLogin() {
        lp.clearUsername();
        lp.clearPassword();
        lp.clickLogin();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message not displayed when both fields are empty.");
    }

    @Test(priority = 4)
    public void testBlankUsernameWithValidPassword() {
        lp.clearUsername();
        lp.enterPassword("admin");
        lp.clickLogin();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message not displayed for blank username.");
    }

    @Test(priority = 5)
    public void testBlankPasswordWithValidUsername() {
        lp.clearPassword();
        lp.enterUsername("admin");
        lp.clickLogin();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message not displayed for blank password.");
    }

    @Test(priority = 6)
    public void testPasswordMasking() {
        lp.enterPassword("admin");
        Assert.assertTrue(lp.isPasswordMasked(), "Password is not masked.");
    }

    @Test(priority = 7)
    public void testLoginWithRememberMe() {
        lp.enterUsername("admin");
        lp.enterPassword("admin");
        lp.clickRememberMe();
        lp.clickLogin();

        boolean isDashboardVisible = page.locator("xpath=//span[normalize-space()='Dashboard']").isVisible();
        Assert.assertTrue(isDashboardVisible, "Login was unsuccessful or dashboard not visible!");

        lp.clickProfile();
        lp.clickLogout();
        
        lp.clickLogin();
        boolean isDashboardVisibleAfterReLogin = page.locator("xpath=//span[normalize-space()='Dashboard']").isVisible();
        Assert.assertTrue(isDashboardVisibleAfterReLogin, "Login was unsuccessful or dashboard not visible after re-login!");
    }

    @Test(priority = 8)
    public void testLongUsernamePassword() {
        String longUsername = "user" + "a".repeat(256);
        String longPassword = "password" + "b".repeat(256);

        lp.enterUsername(longUsername);
        lp.enterPassword(longPassword);
        lp.clickLogin();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message should be displayed for long username and password.");
    }

    @Test(priority = 9)
    public void testCaseSensitivity() {
        lp.enterPassword("admin");
        lp.clickLogin();

        boolean isDashboardVisible = page.locator("xpath=//span[normalize-space()='Dashboard']").isVisible();
        Assert.assertTrue(isDashboardVisible, "Login was unsuccessful due to case sensitivity issues.");
    }

    @Test(priority = 10)
    public void testErrorMessageConsistency() {
        lp.enterUsername("incorrectUsername");
        lp.enterPassword("incorrectPassword");
        lp.clickLogin();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message should be consistent across invalid login attempts.");
    }

    @Test(priority = 11)
    public void testExpiredPasswordLogin() {
        lp.enterUsername("admin");
        lp.enterPassword("expiredPassword");
        lp.clickLogin();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "User should be prompted to reset their password for expired password.");
    }

    @Test(priority = 12)
    public void testPasswordVisibilityToggle() {
        lp.enterPassword("admin");
        lp.clickPasswordVisibilityToggle();

        Assert.assertTrue(lp.isPasswordVisible(), "Password should be visible after clicking the toggle.");
    }

    @Test(priority = 13)
    public void testForgotPasswordLink() {
        lp.clickForgotPassword();

        String currentUrl = page.url();
        Assert.assertTrue(currentUrl.contains("forgot-password"), "User was not redirected to Forgot Password page.");
    }

    @Test(priority = 14)
    public void testPasswordResetWithRegisteredEmail() {
        lp.clickForgotPassword();
        lp.enterEmail("registeredemail@example.com");
        lp.submitPasswordReset();

        Assert.assertTrue(lp.isResetEmailSent(), "Password reset email should be sent.");
    }

    @Test(priority = 15)
    public void testPasswordResetWithUnregisteredEmail() {
        lp.clickForgotPassword();
        lp.enterEmail("unregisteredemail@example.com");
        lp.submitPasswordReset();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message should be displayed for unregistered email.");
    }

    @Test(priority = 16)
    public void testPasswordResetEmailFormat() {
        lp.clickForgotPassword();
        lp.enterEmail("registeredemail@example.com");
        lp.submitPasswordReset();

        Assert.assertTrue(lp.isResetEmailFormatValid(), "Password reset email should contain a secure link.");
    }

    @Test(priority = 17)
    public void testResetPassword() {
        lp.clickForgotPassword();
        lp.enterEmail("registeredemail@example.com");
        lp.submitPasswordReset();

        lp.enterNewPassword("newPassword123");
        lp.submitNewPassword();

        Assert.assertTrue(lp.isPasswordUpdated(), "User should be able to log in with the new password.");
    }

    @Test(priority = 18)
    public void testSignUpLink() {
        lp.clickSignUp();

        String currentUrl = page.url();
        Assert.assertTrue(currentUrl.contains("sign-up"), "User should be redirected to the Sign-Up page.");
    }

    @Test(priority = 19)
    public void testValidGoogleRegistration() {
        lp.clickGoogleSignUp();

        Assert.assertTrue(lp.isRegistrationSuccessful(), "User should be able to register and log in successfully with Google.");
    }

    @Test(priority = 20)
    public void testRegistrationWithExistingGoogleAccount() {
        lp.clickGoogleSignUp();

        Assert.assertTrue(lp.isErrorMessageDisplayed(), "Error message should be displayed for existing Google account.");
    }


    @AfterMethod
    public void tearDown() {
        if (browser != null) {
            browser.close();
        }
    }
}
