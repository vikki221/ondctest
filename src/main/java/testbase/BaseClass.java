package testbase;

import org.testng.annotations.AfterClass;

import org.testng.annotations.BeforeClass;
import com.microsoft.playwright.*;

import pageobjects.LoginPage;
import utilities.ConfigReader;

public class BaseClass {
    protected Playwright playwright;
    protected Browser browser;
    protected Page page;
    private LoginPage lp;
    private ConfigReader configReader;

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions().setViewportSize(1280, 720);
        page = browser.newContext(contextOptions).newPage();
        
        configReader = new ConfigReader();
        lp = new LoginPage(page);

        String url = configReader.getURL();
        page.navigate(url);

        lp.enterUsername("admin");
        lp.enterPassword("admin");
        lp.clickLogin();
    }

    @AfterClass
    public void tearDown() {
        if (browser != null) {
            browser.close();
        }
    }
}
