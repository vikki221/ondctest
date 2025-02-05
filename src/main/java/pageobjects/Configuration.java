package pageobjects;
import com.microsoft.playwright.Page;

public class Configuration {
    
    private final Page page;
    
    private final String stores = "//body/div[@id='root']/div[@class='MuiBox-root css-k008qs']/div[@class='MuiDrawer-root MuiDrawer-docked css-qtsr92-MuiDrawer-docked']/div[@class='MuiPaper-root MuiPaper-elevation MuiPaper-elevation0 MuiDrawer-paper MuiDrawer-paperAnchorLeft MuiDrawer-paperAnchorDockedLeft css-pnr3w9-MuiPaper-root-MuiDrawer-paper']/div[@class='MuiStack-root css-1ompt7n-MuiStack-root']/div/div[@class='sc-blHHSb bnLDKI']/div[@class='sc-gtLWhw jzpbXs']/ul[@role='list']/li[2]/div[1]/ul[1]/li[1]/div[1]/div[3]//*[name()='svg']";
    private final String configuration = "//span[normalize-space()='Configuration']";
    private final String ondc = "//input[@name='ondcEnabled']";
    private final String gst = "//input[@name='gstInclusive']";
    private final String status = "//input[@name='storeStatus']";
    private final String stock ="//input[@name='stockCheck']";
    private final String pickup = "//input[@name='pickupAvailable']";
    private final String mpa = "//input[@id='minimumPurchaseAmount']";
    private final String delivery = "//select[@id='deliveryType']";
    private final String update ="//button[@type='submit']";
    
    public Configuration(Page page) {
        if (page == null) {
            throw new IllegalArgumentException("Page object cannot be null");
        }
        this.page = page;
    }
    
    public void clickStore() {
        page.locator(stores).click();
    }
    
    public void clickConfiguration() {
        page.locator(configuration).click();
    }
    
    public void toggleOndc(boolean enable) {
        if (page.locator(ondc).isChecked() != enable) {
            page.locator(ondc).click();
        }
    }
    
    public void toggleGst(boolean enable) {
        if (page.locator(gst).isChecked() != enable) {
            page.locator(gst).click();
        }
    }
    
    public void toggleStoreStatus(boolean enable) {
        if (page.locator(status).isChecked() != enable) {
            page.locator(status).click();
        }
    }
    
    public void toggleStockCheck(boolean enable) {
        if (page.locator(stock).isChecked() != enable) {
            page.locator(stock).click();
        }
    }
    
    public void togglePickupAvailability(boolean enable) {
        if (page.locator(pickup).isChecked() != enable) {
            page.locator(pickup).click();
        }
    }
    
    public void setMinimumPurchaseAmount(String amount) {
        page.locator(mpa).fill(amount);
    }
    
    public void selectDeliveryType(String type) {
        page.locator(delivery).selectOption(type);
    }
    
    public void clickUpdate() {
        page.locator(update).click();
    }
}
