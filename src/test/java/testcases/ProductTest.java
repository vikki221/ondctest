package testcases;

import pageobjects.Products;
import testbase.BaseClass;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.microsoft.playwright.options.LoadState;

public class ProductTest extends BaseClass {

    private Products pr;
    
    @BeforeMethod
    
    public void pp() {
    	pr = new Products(page); 
    	pr.clickCatalog(); 
        pr.clickProduct(); 
        pr.clickAddProduct();

    }

    @Test(priority = 1)
    public void testValidProductEntry() {
        
        pr.enterProductName("Product ABC");
        pr.enterBarcode("987654321");
        pr.enterProductDescription("Valid product description");
        pr.enterProductPrice("500");
        pr.enterSalePrice("450");
        pr.enterGstPercent("18");
        pr.enterQuantity("100");
        pr.enterUnitOfMeasurement("kg");
        pr.enterShipmentBufferWindow("2");
        pr.enterProductTags("food, organic");
        pr.selectc1();
        pr.selectr1();
        pr.enterManufacturerName("ABC Corp");
        pr.enterManufactureYear("2024");
        pr.enterLength("10");
        pr.enterBreadth("5");
        pr.enterHeight("3");
        pr.selectHeightUnit("cm");
        pr.enterWeight("1.2");
        pr.selectWeightUnit("kg");

        pr.clicksubmit();
        
        // Verify success message (adjust selector if needed)
        Assert.assertTrue(page.locator("text=Product added successfully").isVisible(), "Success message not displayed");
    }

    @Test(priority = 2)
    public void testEmptyProductName() {
        pr = new Products(page);
        pr.clickAddProduct();
        pr.enterProductName("");
        pr.clicksubmit();

        // Verify error message
        Assert.assertTrue(page.locator("text=Product Name is required").isVisible(), "Error message not displayed for empty Product Name");
    }

    @Test(priority = 3)
    public void testInvalidBarcode() {
        pr = new Products(page);
        pr.clickAddProduct();
        pr.enterBarcode("!@#$%");
        pr.clicksubmit();

        // Verify error message
        Assert.assertTrue(page.locator("//*[@id=\"root\"]/div/main/div/div[2]/form/div[1]/div[2]/div/div").isVisible(), "Error message not displayed for invalid barcode");
    }

    @Test(priority = 4)
    public void testSalePriceGreaterThanProductPrice() {
        pr.clickAddProduct();
        pr.enterProductPrice("100");
        pr.enterSalePrice("150");
        pr.clicksubmit();

        // Verify validation message
        Assert.assertTrue(page.locator("text=Sale Price cannot exceed Product Price").isVisible(), "Validation message not displayed for sale price exceeding product price");
    }

    @Test(priority = 5)
    public void testNegativeQuantity() {
        pr.enterQuantity("-5");
        pr.clicksubmit();

        // Verify error message
        Assert.assertTrue(page.locator("text=Quantity cannot be negative").isVisible(), "Error message not displayed for negative quantity");
    }

    @Test(priority = 6)
    public void testInvalidGSTPercent() {
        pr.enterGstPercent("150");
        pr.clicksubmit();

        // Verify error message
        Assert.assertTrue(page.locator("text=GST cannot exceed 100%").isVisible(), "Error message not displayed for invalid GST percent");
    }

    @Test(priority = 7)
    public void testInvalidProductTags() {
        pr.enterProductTags("tag1, @invalidTag#");
        pr.clicksubmit();

        // Verify error message
        Assert.assertTrue(page.locator("text=Tags must be alphanumeric").isVisible(), "Error message not displayed for invalid tags");
    }

    @Test(priority = 8)
    public void testManufactureYearFormat() {
        pr.enterManufactureYear("abcd");
        pr.clicksubmit();

        // Verify error message
        Assert.assertTrue(page.locator("text=Invalid manufacture year format").isVisible(), "Error message not displayed for incorrect year format");
    }

    @Test(priority = 9)
    public void testWeightFieldValidation() {
        pr.enterWeight("abc");
        pr.clicksubmit();

        // Verify error message
        Assert.assertTrue(page.locator("text=Invalid weight value").isVisible(), "Error message not displayed for invalid weight");
    }

    @Test(priority = 10)
    public void testFieldsClearedAfterSubmission() {

        pr.enterProductName("Test Product");
        pr.enterBarcode("123456789");
        pr.enterProductPrice("100");
        pr.clicksubmit();

        page.waitForLoadState(LoadState.NETWORKIDLE);

        // Verify fields are cleared
        Assert.assertEquals(page.locator("//input[@id='productName']").inputValue(), "", "Product Name field is not cleared");
        Assert.assertEquals(page.locator("//input[@id='barcode']").inputValue(), "", "Barcode field is not cleared");
    }

    @Test(priority = 11)
    public void testSuccessMessageAfterSubmission() {

        pr.enterProductName("Test Product");
        pr.enterBarcode("123456789");
        pr.enterProductPrice("100");
        pr.clicksubmit();

        page.waitForLoadState(LoadState.NETWORKIDLE);

        // Verify success message
        Assert.assertTrue(page.locator("text=Product added successfully").isVisible(), "Success message not displayed");
    }

    @Test(priority = 12)
    public void testProductListLoadsCorrectly() {
        page.waitForLoadState(LoadState.NETWORKIDLE);

        // Verify product list is visible
        Assert.assertTrue(page.locator("//table[contains(@class, 'product-list')]").isVisible(), "Product list is not displayed");
    }

    @Test(priority = 13)
    public void testProductSearch() {

        page.locator("//input[@id='searchBar']").fill("Product XYZ");
        page.locator("//button[normalize-space()='Search']").click();

        // Verify search results
        Assert.assertTrue(page.locator("//td[contains(text(),'Product XYZ')]").isVisible(), "Search did not return expected results");
    }

    @Test(priority = 14)
    public void testDeleteProduct() {
        pr = new Products(page);
        pr.clickProduct();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        page.locator("//button[normalize-space()='Delete'][1]").click();
        page.locator("//button[normalize-space()='Confirm']").click();

        // Verify product is removed
        Assert.assertTrue(page.locator("text=Product deleted successfully").isVisible(), "Delete confirmation not displayed");
    }
    @Test(priority = 15)
    public void testLongProductDescription() {

        String longDescription = "This is a very long product description that exceeds the typical character limit. "
                + "It is used to test whether the product description field can handle large amounts of text without any issues.";
        pr.enterProductDescription(longDescription);
        pr.clicksubmit();

        // Verify no error message is shown
        Assert.assertFalse(page.locator("text=Description exceeds maximum length").isVisible(), "Error message displayed for long description");
    }
    
    @Test(priority = 16)
    public void testNumericManufacturerName() {

        pr.enterManufacturerName("12345");
        pr.clicksubmit();

        // Verify error message
        Assert.assertTrue(page.locator("text=Manufacturer Name cannot contain numbers").isVisible(), "Error message not displayed for numeric manufacturer name");
    }
    
    @Test(priority = 17)
    public void testProductTagsWithSpaces() {

        pr.enterProductTags("tag1, tag 2, tag 3");
        pr.clicksubmit();
        
        Assert.assertFalse(page.locator("text=Invalid tags format").isVisible(), "Error message displayed for tags with spaces");
    }
    @Test(priority = 18)
    public void testEmptyDimensionFields() {
        pr.enterLength("10");
        pr.enterBreadth("");
        pr.enterHeight("5");
        pr.clicksubmit();

        Assert.assertTrue(page.locator("text=All dimension fields are required").isVisible(), "Error message not displayed for empty dimension fields");
    }

    @Test(priority = 19)
    public void testLongNutritionInformation() {
        String longNutritionInfo = "This is a very long nutrition information text that exceeds the typical character limit. "
                + "It is used to test whether the nutrition information field can handle large amounts of text without any issues.";
        pr.enterNutritionInformation(longNutritionInfo);
        pr.clicksubmit();

        Assert.assertTrue(page.locator("text=Nutrition information exceeds maximum length").isVisible(), "Error message not displayed for long nutrition information");
    }

    @Test(priority = 20)
    public void testReturnableFieldDefault() {
        pr.clicksubmit();

        Assert.assertTrue(page.locator("//input[@name='returnable'][@value='No']").isChecked(), "Returnable field does not default to 'No'");
    }

    @Test(priority = 21)
    public void testGSTPercentWithDecimals() {
        pr.enterGstPercent("18.5");
        pr.clicksubmit();

        Assert.assertFalse(page.locator("text=Invalid GST percent format").isVisible(), "Error message displayed for GST percent with decimals");
    }

    @Test(priority = 22)
    public void testNonIntegerShipmentBufferWindow() {
        pr.enterShipmentBufferWindow("2.5");
        pr.clicksubmit();

        Assert.assertTrue(page.locator("text=Shipment buffer window must be an integer").isVisible(), "Error message not displayed for non-integer shipment buffer window");
    }

    @Test(priority = 23)
    public void testProductListPagination() {
        page.waitForLoadState(LoadState.NETWORKIDLE);

        page.locator("//button[normalize-space()='Next']").click();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        Assert.assertTrue(page.locator("//table[contains(@class, 'product-list')]").isVisible(), "Product list pagination failed");
    }

    @Test(priority = 24)
    public void testSortByProductName() {
        page.waitForLoadState(LoadState.NETWORKIDLE);

        page.locator("//th[normalize-space()='Product Name']").click();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        Assert.assertTrue(page.locator("//table[contains(@class, 'product-list')]").isVisible(), "Sorting by Product Name failed");
    }

    @Test(priority = 25)
    public void testInPlaceEditingOfProductPrice() {
        page.waitForLoadState(LoadState.NETWORKIDLE);

        page.locator("//td[contains(text(),'Product ABC')]/following-sibling::td[2]//button[normalize-space()='Edit']").click();
        page.locator("//input[@type='text']").fill("600");
        page.locator("//button[normalize-space()='Save']").click();
        page.waitForLoadState(LoadState.NETWORKIDLE);

        Assert.assertTrue(page.locator("//td[contains(text(),'600')]").isVisible(), "Product price not updated");
    }

    @Test(priority = 26)
    public void testValidProductNameEntry() {
        pr.enterProductName("Valid Product Name");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Product name is accepted").isVisible(), "Product name error message not displayed");
    }

    @Test(priority = 27)
    public void testInvalidProductNameEntry() {
        pr.enterProductName("Invalid@Name");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Invalid product name").isVisible(), "Error message for invalid product name not displayed");
    }

    @Test(priority = 28)
    public void testValidBarcodeEntry() {
        pr.enterBarcode("123456789");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Barcode accepted").isVisible(), "Barcode acceptance failed");
    }

    @Test(priority = 29)
    public void testInvalidBarcodeEntry() {
        pr.enterBarcode("invalid_barcode");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Invalid barcode format").isVisible(), "Error message for invalid barcode format not displayed");
    }

    @Test(priority = 30)
    public void testValidProductDescription() {
        pr.enterProductDescription("This is a valid product description.");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Product description is accepted").isVisible(), "Product description acceptance failed");
    }

    @Test(priority = 31)
    public void testValidProductPrice() {
        pr.enterProductPrice("100");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Price is accepted").isVisible(), "Price error message not displayed");
    }

    @Test(priority = 32)
    public void testInvalidProductPrice() {
        pr.enterProductPrice("InvalidPrice");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Invalid price format").isVisible(), "Price format error message not displayed");
    }

    @Test(priority = 33)
    public void testSalePriceExceedsProductPrice() {
        pr.enterProductPrice("1000");
        pr.enterSalePrice("1200");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Sale price cannot exceed product price").isVisible(), "Sale price error message not displayed");
    }

    @Test(priority = 34)
    public void testValidGSTPercent() {
        pr.enterGstPercent("18");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=GST percent accepted").isVisible(), "GST percent acceptance failed");
    }

    @Test(priority = 35)
    public void testGSTPercentCannotExceed100() {
        pr.enterGstPercent("105");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=GST percent cannot exceed 100").isVisible(), "GST percent exceeding 100 error message not displayed");
    }

    @Test(priority = 36)
    public void testValidQuantity() {
        pr.enterQuantity("50");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Quantity accepted").isVisible(), "Quantity acceptance failed");
    }

    @Test(priority = 37)
    public void testNegativeQuantity1() {
        pr.enterQuantity("-5");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Quantity cannot be negative").isVisible(), "Error message for negative quantity not displayed");
    }

    @Test(priority = 38)
    public void testUnitOfMeasurementDropdown() {
        pr.enterUnitOfMeasurement("kg");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=kg").isVisible(), "Selected unit of measurement not displayed");
    }

    @Test(priority = 39)
    public void testShipmentBufferWindow() {
        pr.enterShipmentBufferWindow("5");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Shipment buffer window accepted").isVisible(), "Shipment buffer window acceptance failed");
    }

    @Test(priority = 40)
    public void testProductTags() {
        pr.enterProductTags("Tag1, Tag2, Tag3");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Tags accepted").isVisible(), "Tags acceptance failed");
    }

    @Test(priority = 41)
    public void testCancellableField() {
        pr.selectc1();
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Yes").isVisible(), "Cancellable option not saved correctly");
    }

    @Test(priority = 42)
    public void testReturnableField() {
        pr.selectr2();
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=No").isVisible(), "Returnable option not saved correctly");
    }

    @Test(priority = 43)
    public void testManufacturerName() {
        pr.enterManufacturerName("ABC Corp");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Manufacturer name accepted").isVisible(), "Manufacturer name acceptance failed");
    }

    @Test(priority = 44)
    public void testManufactureYear() {
        pr.enterManufactureYear("2023");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Manufacture year accepted").isVisible(), "Manufacture year acceptance failed");
    }

    @Test(priority = 45)
    public void testAdditiveInformation() {
        pr.enterAdditiveInformation("Contains sugar");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Additive information accepted").isVisible(), "Additive information acceptance failed");
    }

    @Test(priority = 46)
    public void testDimensionDetails() {
        pr.enterDimensions("10", "5", "2");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Dimensions accepted").isVisible(), "Dimension acceptance failed");
    }

    @Test(priority = 47)
    public void testValidWeight() {
        pr.enterWeight("10");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Weight accepted").isVisible(), "Weight acceptance failed");
    }

    @Test(priority = 48)
    public void testWeightUnitDropdown() {
        pr.selectWeightUnit("kg");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=kg").isVisible(), "Selected weight unit not displayed");
    }

    @Test(priority = 49)
    public void testProductNameNotEmpty() {
        pr.enterProductName("");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Product name is required").isVisible(), "Product name error message not displayed");
    }

    @Test(priority = 50)
    public void testPriceWithinRange() {
        pr.enterProductPrice("0");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Price must be within acceptable range").isVisible(), "Price range error message not displayed");
    }

    @Test(priority = 51)
    public void testSalePriceNotExceedProductPrice() {
        pr.enterProductPrice("100");
        pr.enterSalePrice("150");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Sale price cannot exceed product price").isVisible(), "Sale price exceeds product price error message not displayed");
    }

    @Test(priority = 52)
    public void testUnitOfMeasurementRequired() {
        pr.enterUnitOfMeasurement("");
        pr.clickNextField();

        Assert.assertTrue(page.locator("text=Unit of measurement is required").isVisible(), "Unit of measurement required error message not displayed");
    }
}