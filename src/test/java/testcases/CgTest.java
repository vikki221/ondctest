package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageobjects.Configuration;
import testbase.BaseClass;

public class CgTest extends BaseClass {
    
    private Configuration cg;
    
    @BeforeMethod
    public void cp() {
        cg = new Configuration(page); 
        cg.clickStore();
        cg.clickConfiguration();
    }

    @Test(priority = 1)
    public void testToggleOndc() throws InterruptedException {
        // Toggle ONDC to true and update
        cg.toggleOndc(true);
        Thread.sleep(5000);
        cg.clickUpdate();
        page.locator("//input[@name='ondcEnabled']").waitFor();
        Assert.assertTrue(page.locator("//input[@name='ondcEnabled']").isChecked(), "ONDC should be enabled");

        // Toggle ONDC to false and update
        cg.toggleOndc(false);
        cg.clickUpdate();
        page.locator("//input[@name='ondcEnabled']").waitFor();
        Assert.assertFalse(page.locator("//input[@name='ondcEnabled']").isChecked(), "ONDC should be disabled");
    }


    @Test(priority = 2)
    public void testToggleStoreStatus() {
        cg.toggleStoreStatus(true);
        cg.clickUpdate();
        page.locator("//input[@name='storeStatus']").waitFor();
        Assert.assertTrue(page.locator("//input[@name='storeStatus']").isChecked(), "Store Status should be active");

        cg.toggleStoreStatus(false);
        cg.clickUpdate();
        page.locator("//input[@name='storeStatus']").waitFor();
        Assert.assertFalse(page.locator("//input[@name='storeStatus']").isChecked(), "Store Status should be inactive");
    }

    @Test(priority = 3)
    public void testTogglePickupAvailable() {
        cg.togglePickupAvailability(true);
        cg.clickUpdate();
        page.locator("//input[@name='pickupAvailable']").waitFor();
        Assert.assertTrue(page.locator("//input[@name='pickupAvailable']").isChecked(), "Pickup Available should be enabled");

        cg.togglePickupAvailability(false);
        cg.clickUpdate();
        page.locator("//input[@name='pickupAvailable']").waitFor();
        Assert.assertFalse(page.locator("//input[@name='pickupAvailable']").isChecked(), "Pickup Available should be disabled");
    }

    @Test(priority = 4)
    public void testToggleGstInclusive() {
        cg.toggleGst(true);
        cg.clickUpdate();
        page.locator("//input[@name='gstInclusive']").waitFor();
        Assert.assertTrue(page.locator("//input[@name='gstInclusive']").isChecked(), "GST Inclusive should be enabled");

        cg.toggleGst(false);
        cg.clickUpdate();
        page.locator("//input[@name='gstInclusive']").waitFor();
        Assert.assertFalse(page.locator("//input[@name='gstInclusive']").isChecked(), "GST Inclusive should be disabled");
    }

    @Test(priority = 5)
    public void testMinimumPurchaseAmount() {
        cg.setMinimumPurchaseAmount("100");
        cg.clickUpdate();
        page.locator("//input[@id='minimumPurchaseAmount']").waitFor();
        Assert.assertEquals(page.locator("//input[@id='minimumPurchaseAmount']").inputValue(), "100", "Minimum Purchase Amount should be 100");

        cg.setMinimumPurchaseAmount("200");
        cg.clickUpdate();
        page.locator("//input[@id='minimumPurchaseAmount']").waitFor();
        Assert.assertEquals(page.locator("//input[@id='minimumPurchaseAmount']").inputValue(), "200", "Minimum Purchase Amount should be updated to 200");
    }

    @Test(priority = 6)
    public void testDeliveryType() {
        cg.selectDeliveryType("Pan-India");
        cg.clickUpdate();
        page.locator("//select[@id='deliveryType']").waitFor();
        Assert.assertEquals(page.locator("//select[@id='deliveryType']").inputValue(), "pan-india", "Delivery Type should be Pan-India");
    }

    @Test(priority = 7)
    public void testDeliveryTypeHyperLocal() throws InterruptedException {
        cg.selectDeliveryType("Hyper Local");
        Thread.sleep(5000);
        cg.clickUpdate();
        page.locator("//select[@id='deliveryType']").waitFor();
        Assert.assertEquals(page.locator("//select[@id='deliveryType']").inputValue(), "hyper-local", "Delivery Type should be Hyper Local");
    }

    @Test(priority = 8)
    public void testAllowedPincodesDisplay() throws InterruptedException {
    	cg.selectDeliveryType("Hyper Local");
        Thread.sleep(5000);
        page.locator("#newPincode").waitFor();
        Assert.assertTrue(page.locator("#newPincode").isVisible(), "Allowed Pincodes section should be visible");
    }

    @Test(priority = 9)
    public void testAddNewPincode() {
        page.locator("#newPincode").fill("123456");
        page.locator("//button[normalize-space()='Add Pincode']").click();
        page.locator("#allowedPincodesList").waitFor();
        Assert.assertTrue(page.locator("#allowedPincodesList").textContent().contains("123456"), "New pincode should be added");
    }

    @Test(priority = 10)
    public void testInvalidPincodeError() {
        page.locator("#newPincode").fill("ABCDE");
        page.locator("//button[normalize-space()='Add Pincode']").click();
        page.locator("#pincodeError").waitFor();
        Assert.assertTrue(page.locator("#pincodeError").isVisible(), "Error message should appear for invalid pincode");
    }

    @Test(priority = 11)
    public void testMinimumPurchaseAmountLimit() {
        cg.setMinimumPurchaseAmount("50");
        cg.clickUpdate();
        page.locator("//*[@id=\"root\"]/div/main/div/form/div/div[2]/div[6]/div/div").waitFor();
        Assert.assertTrue(page.locator("//*[@id=\"root\"]/div/main/div/form/div/div[2]/div[6]/div/div").isVisible(), "Error message should appear for amount below ₹100");
    }

}
