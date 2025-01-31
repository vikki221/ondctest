package testcases;

import org.testng.annotations.BeforeClass;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageobjects.Products;
import testbase.BaseClass;
import listeners.CustomListener;

@Listeners(CustomListener.class)
public class ProductTest extends BaseClass {


    private Products pr;

    @BeforeClass
    public void initialize() {
        pr = new Products(page);
    }

    @Test(priority = 1)
    public void testLoginAndAddProduct() throws InterruptedException {

    	pr.clickCatalog();
    	page.waitForTimeout(1000);

    	pr.clickProduct();
    	page.waitForTimeout(1000);

    	pr.clickAddProduct();
    	page.waitForTimeout(1000);

    	pr.enterProductName("Product XYZ");
    	page.waitForTimeout(1000);

    	pr.enterBarcode("123456789");
    	page.waitForTimeout(1000);

    	pr.enterProductDescription("This is a description of Product XYZ");
    	page.waitForTimeout(1000);

    	pr.enterProductPrice("100");
    	page.waitForTimeout(1000);

    	pr.enterSalePrice("80");
    	page.waitForTimeout(1000);

    	pr.enterGstPercent("18");
    	page.waitForTimeout(1000);

    	pr.enterQuantity("50");
    	page.waitForTimeout(1000);

    	pr.enterUnitOfMeasurement("kg");
    	page.waitForTimeout(1000);

    	pr.enterShipmentBufferWindow("2");
    	page.waitForTimeout(1000);

    	pr.enterProductTags("grocery");
    	page.waitForTimeout(1000);

    	pr.selectc2();
    	page.waitForTimeout(1000);

    	pr.selectr2();
    	page.waitForTimeout(1000);

    	pr.enterManufacturerName("XYZ Manufacturer");
    	page.waitForTimeout(1000);

    	pr.enterManufactureYear("2023");
    	page.waitForTimeout(1000);

    	pr.enterAuthor("Author XYZ");
    	page.waitForTimeout(1000);

    	pr.enterLanguage("English");
    	page.waitForTimeout(1000);

    	pr.enterNutritionInformation("Contains vitamin C");
    	page.waitForTimeout(1000);

    	pr.enterAdditiveInformation("None");
    	page.waitForTimeout(1000);

    	pr.enterInstructions("Store in a cool, dry place");
    	page.waitForTimeout(1000);

    	pr.enterLength("10");
    	page.waitForTimeout(1000);

    	pr.enterBreadth("5");
    	page.waitForTimeout(1000);

    	pr.enterHeight("3");
    	page.waitForTimeout(1000);

    	pr.selectHeightUnit("cm");
    	page.waitForTimeout(1000);

    	pr.enterWeight("1");
    	page.waitForTimeout(1000);

    	pr.selectWeightUnit("kg");
    	page.waitForTimeout(1000);
    	
    	pr.clicksubmit();

        Thread.sleep(5000);
    }
}
