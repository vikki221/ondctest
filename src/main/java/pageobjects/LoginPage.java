package pageobjects;

import com.microsoft.playwright.Page;

public class LoginPage {

    private final Page page;

    private final String usernameField = "//input[@id='username']";
    private final String passwordField = "//input[@id='password']"; 
    private final String loginButton = "//button[@type='submit']"; 
    private final String profileIcon = "//button[@aria-label='Open menu']//*[name()='svg']";
    private final String logout = "//*[@id=\"menu\"]/div[3]/ul/li[5]";
    private final String remember = "//input[@value='remember']";
    private final String errorMessageLocator = "//*[@id=\"root\"]/main/div/form/p"; 
    private final String forgotPasswordLink = "//a[@class='text-sm font-medium text-emerald-500 dark:text-emerald-400 hover:underline']"; 
    

    public LoginPage(Page page) {
        if (page == null) {
            throw new IllegalArgumentException("Page object cannot be null");
        }
        this.page = page;
    }

    // Actions
    public void enterUsername(String username) {
        page.locator(usernameField).fill(username);
    }

    public void enterPassword(String password) {
        page.locator(passwordField).fill(password);
    }

    public void clickLogin() {
        page.locator(loginButton).click(); 
    }

    public boolean isErrorMessageDisplayed() {
        try {
            page.waitForSelector(errorMessageLocator, new Page.WaitForSelectorOptions().setTimeout(5000));
            return page.locator(errorMessageLocator).isVisible(); 
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPasswordMasked() {
        String typeAttribute = page.locator(passwordField).getAttribute("type");
        return "password".equals(typeAttribute);
    }

    public void clearUsername() {
        page.locator(usernameField).clear();
    }

    public void clearPassword() {
        page.locator(passwordField).clear();
    }

    public void clickProfile() {
        page.locator(profileIcon).click();
    }

    public void clickLogout() {
        page.locator(logout).click();
    }

    public void clickrm() {
        page.locator(remember).click();
    }

    public void clickForgotPassword() {
        page.locator(forgotPasswordLink).click();
    }

	public void clickPasswordVisibilityToggle() {
		// TODO Auto-generated method stub
		
	}

	public boolean isPasswordVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	public void enterEmail(String string) {
		// TODO Auto-generated method stub
		
	}

	public void submitPasswordReset() {
		// TODO Auto-generated method stub
		
	}

	public boolean isResetEmailSent() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isResetEmailFormatValid() {
		// TODO Auto-generated method stub
		return false;
	}

	public void enterNewPassword(String string) {
		// TODO Auto-generated method stub
		
	}

	public void submitNewPassword() {
		// TODO Auto-generated method stub
		
	}

	public boolean isPasswordUpdated() {
		// TODO Auto-generated method stub
		return false;
	}

	public void clickSignUp() {
		// TODO Auto-generated method stub
		
	}

	public void clickGoogleSignUp() {
		// TODO Auto-generated method stub
		
	}

	public boolean isRegistrationSuccessful() {
		// TODO Auto-generated method stub
		return false;
	}

	public void clickRememberMe() {
		// TODO Auto-generated method stub
		
	}


   
}
