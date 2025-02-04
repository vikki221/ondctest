package pageobjects;

import com.microsoft.playwright.Page;

public class Products {

    private final Page page;

    private final String catalog = "//li[3]//div[1]//ul[1]//li[1]//div[1]//div[3]//*[name()='svg']";
    private final String products = "//span[normalize-space()='Products']";
    private final String addproduct = "//button[normalize-space()='Add Product']";
    private final String productName = "//input[@id='productName']";
    private final String barcode = "//input[@id='barcode']";
    private final String productDescription = "//textarea[@id='description']";
    private final String productPrice = "//input[@id='productPrice']";
    private final String salePrice = "//input[@id='salePrice']";
    private final String gstPercent = "//input[@id='gstPercent']";
    private final String quantity = "//input[@id='quantity']";
    private final String unitOfMeasurement = "//input[@id='unitOfMeasurement']";
    private final String shipmentBufferWindow = "//input[@id='shipmentBufferWindow']";
    private final String productTags = "//input[@id='productTags']";
    private final String cancellable1 = "//label[normalize-space()='Yes']//input[@name='cancellable']";
    private final String cancellable2 = "//label[normalize-space()='No']//input[@name='cancellable']";
    private final String returnable1 = "//label[normalize-space()='Yes']//input[@name='returnable']";
    private final String returnable2 = "//label[normalize-space()='No']//input[@name='returnable']";
    private final String manufacturerName = "//input[@id='manufacturerName']";
    private final String manufactureYear = "//input[@id='manufacturerYear']";
    private final String author = "//input[@id='manufacturerAuthor']";
    private final String language = "//input[@id='manufacturerLanguage']";
    private final String nutritionInformation = "//input[@id='nutritionInfo']";
    private final String additiveInformation = "//input[@id='additiveInfo']";
    private final String instructions = "//input[@id='instructions']";
    private final String length = "//input[@id='length']";
    private final String breadth = "//input[@id='breadth']";
    private final String height = "//input[@id='height']";
    private final String unit = "//select[@id='heightUnit']";
    private final String weight = "//input[@id='weight']";
    private final String weightUnit = "//select[@id='weightUnit']";
    private final String submit = "//button[@type='submit']";

    public Products(Page page) {
        if (page == null) {
            throw new IllegalArgumentException("Page object cannot be null");
        }
        this.page = page;
    }

    public void clickCatalog() {
        page.locator(catalog).click();
    }

    public void clickProduct() {
        page.locator(products).click();
    }

    public void clickAddProduct() {
        page.locator(addproduct).click();
    }

    public void enterProductName(String name) {
        page.locator(productName).fill(name);
    }

    public void enterBarcode(String code) {
        page.locator(barcode).fill(code);
    }

    public void enterProductDescription(String description) {
        page.locator(productDescription).fill(description);
    }

    public void enterProductPrice(String price) {
        page.locator(productPrice).fill(price);
    }

    public void enterSalePrice(String price) {
        page.locator(salePrice).fill(price);
    }

    public void enterGstPercent(String gst) {
        page.locator(gstPercent).fill(gst);
    }

    public void enterQuantity(String qty) {
        page.locator(quantity).fill(qty);
    }

    public void enterUnitOfMeasurement(String unit) {
        page.locator(unitOfMeasurement).fill(unit);
    }

    public void enterShipmentBufferWindow(String window) {
        page.locator(shipmentBufferWindow).fill(window);
    }

    public void enterProductTags(String tags) {
        page.locator(productTags).fill(tags);
    }

    public void selectc1() {
        page.locator(cancellable1).click();
        
    }
    
    public void selectc2() {
        page.locator(cancellable2).click();
        
    }

    public void selectr1() {
            page.locator(returnable1).click();
       
    }
    
    public void selectr2() {
        page.locator(returnable2).click();
   
}

    public void enterManufacturerName(String name) {
        page.locator(manufacturerName).fill(name);
    }

    public void enterManufactureYear(String year) {
        page.locator(manufactureYear).fill(year);
    }

    public void enterAuthor(String authorName) {
        page.locator(author).fill(authorName);
    }

    public void enterLanguage(String lang) {
        page.locator(language).fill(lang);
    }

    public void enterNutritionInformation(String nutrition) {
        page.locator(nutritionInformation).fill(nutrition);
    }

    public void enterAdditiveInformation(String additives) {
        page.locator(additiveInformation).fill(additives);
    }

    public void enterInstructions(String instr) {
        page.locator(instructions).fill(instr);
    }

    public void enterLength(String len) {
        page.locator(length).fill(len);
    }

    public void enterBreadth(String brd) {
        page.locator(breadth).fill(brd);
    }

    public void enterHeight(String hgt) {
        page.locator(height).fill(hgt);
    }

    public void selectHeightUnit(String heightUnitValue) {
        page.locator(unit).selectOption(heightUnitValue);
    }

    public void enterWeight(String wt) {
        page.locator(weight).fill(wt);
    }

    public void selectWeightUnit(String weightUnitValue) {
        page.locator(weightUnit).selectOption(weightUnitValue);
    }
    
    public void clicksubmit() {
        page.locator(submit).click();;
    }

	public void clickNextField() {
		// TODO Auto-generated method stub
		
	}

	public void enterDimensions(String string, String string2, String string3) {
		// TODO Auto-generated method stub
		
	}

}
