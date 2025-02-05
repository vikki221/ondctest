package testbase;

import com.microsoft.playwright.*;

import org.testng.annotations.*;
import pageobjects.LoginPage;
import utilities.ConfigReader;

public class BaseClass {
    protected static Playwright playwright;
    protected static Browser browser;
    protected Page page;
    protected LoginPage lp;
    private ConfigReader configReader;

    @BeforeSuite
    public void initializeBrowser() {
        configReader = new ConfigReader();
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeMethod
    public void setUp() {
        String url = configReader.getURL();
        page = browser.newPage();
        page.navigate(url);

        lp = new LoginPage(page);
        lp.enterUsername("admin");
        lp.enterPassword("admin");
        lp.clickLogin();
    }

    @AfterMethod
    public void tearDown() {
        page.getByLabel("Open menu").click();
        page.getByText("Logout").click();
        page.close();  // Close only the page, not the browser
    }

    @AfterSuite
    public void closeBrowser() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
