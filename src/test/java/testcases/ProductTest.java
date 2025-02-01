package testcases;

import pageobjects.Products;
import testbase.BaseClass;
import org.testng.annotations.Test;

import com.microsoft.playwright.options.LoadState;

public class ProductTest extends BaseClass {

    private Products pr;

    @Test(priority = 1)
    public void testLoginAndAddProduct() {
        pr = new Products(page);  // Initialize Products page after login

        pr.clickCatalog();
        page.waitForLoadState(LoadState.NETWORKIDLE); // Wait for page load

        pr.clickProduct();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        
        pr.clickAddProduct();
        page.waitForLoadState(LoadState.NETWORKIDLE);
        
        // Fill product details
        pr.enterProductName("Product XYZ");
        pr.enterBarcode("123456789");
        pr.enterProductDescription("This is a description of Product XYZ");
        pr.enterProductPrice("100");
        pr.enterSalePrice("80");
        pr.enterGstPercent("18");
        pr.enterQuantity("50");
        pr.enterUnitOfMeasurement("kg");
        pr.enterShipmentBufferWindow("2");
        pr.enterProductTags("grocery");

        pr.selectc2();
        pr.selectr2();
        
        pr.enterManufacturerName("XYZ Manufacturer");
        pr.enterManufactureYear("2023");
        pr.enterAuthor("Author XYZ");
        pr.enterLanguage("English");
        pr.enterNutritionInformation("Contains vitamin C");
        pr.enterAdditiveInformation("None");
        pr.enterInstructions("Store in a cool, dry place");
        
        pr.enterLength("10");
        pr.enterBreadth("5");
        pr.enterHeight("3");
        pr.selectHeightUnit("cm");
        pr.enterWeight("1");
        pr.selectWeightUnit("kg");

        pr.clicksubmit();

 
    }

    @Test(priority = 2)
    public void testAddProduct() {
        pr = new Products(page);  // Initialize Products page after login

        pr.clickCatalog();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        pr.clickProduct();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        pr.clickAddProduct();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        pr.enterProductName("Product XYZ");
        pr.enterBarcode("123456789");
        pr.enterProductDescription("This is a description of Product XYZ");
        pr.enterProductPrice("100");

        pr.clicksubmit();
        
        page.waitForLoadState(LoadState.NETWORKIDLE);  // Wait for the submit to complete
    }
}
