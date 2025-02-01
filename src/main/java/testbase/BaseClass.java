package testbase;

import com.microsoft.playwright.*;


import pageobjects.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utilities.ConfigReader;

public class BaseClass {
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
        lp.enterUsername("admin");
        lp.enterPassword("admin");
        lp.clickLogin();

    }

    @AfterMethod
    public void tearDown() {
        if (browser != null) {
            browser.close();
        }
    }
}
