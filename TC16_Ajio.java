package day16.tc16;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC16_Ajio {

	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver,30);
		
		//1) Go to //www.ajio.com/shop/sale
		driver.get("https://www.ajio.com/shop/sale");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//WebElement eleClose = driver.findElementByClassName("ic-close-quickview");
		//wait.until(ExpectedConditions.visibilityOf(eleClose)).click();
		
		//2) Enter Bags in the Search field and Select Bags in Women Handbags
		driver.findElementByName("searchVal").sendKeys("Bags",Keys.ENTER);
		Thread.sleep(3000);
		driver.findElementByXPath("//input[@id='Women - Handbags']/following-sibling::label[1]").click();
		Thread.sleep(1000);
		
		//3) Click on five grid and Select SORT BY as "What's New"
		driver.findElementByClassName("five-grid").click();
		Thread.sleep(1000);
		
		WebElement eleSort = driver.findElementByXPath("//div[@class='filter-dropdown']//select[1]");
		Select sort = new Select(eleSort);
		sort.selectByVisibleText("What's New");
		
		//4) Enter Price Range Min as 2000 and Max as 5000
		driver.findElementByXPath("//span[text()='price']").click();
		Thread.sleep(500);
		driver.findElementById("minPrice").sendKeys("2000");
		driver.findElementById("maxPrice").sendKeys("5000");
		driver.findElementByXPath("//input[@id='maxPrice']/following-sibling::button[1]").click();
		Thread.sleep(1000);
		
		//5) Click on the product "Puma Ferrari LS Shoulder Bag"
		driver.findElementByXPath("//div[text()='Ferrari LS Shoulder Bag']").click();
		
		//6) Verify the Coupon code for the price above 2690 is applicable for your product, if applicable the get the Coupon Code and Calculate the discount price for the coupon
		Set<String> setWin = driver.getWindowHandles();
		List<String> listWin = new ArrayList<String>(setWin);
		driver.switchTo().window(listWin.get(1));
		
		//To get the actual price of the bag
		String actualPrice = driver.findElementByXPath("//div[@class='prod-price-section']/div[1]").getText();
		System.out.println("Actual Price of the bag: "+actualPrice);
		actualPrice = actualPrice.replaceAll("\\D","");
		int actPrice = Integer.parseInt(actualPrice);
		
		//To get the minimum eligible amount for offer
		String offerText = driver.findElementByClassName("promo-desc").getText();
		System.out.println("Available Offer: "+offerText);
		String offerEligibleAmt = offerText.substring(22, 26);
		int offerEligibleAmtInt = Integer.parseInt(offerEligibleAmt);
		System.out.println("Offer minimum amount is "+offerEligibleAmtInt);
		
		//To get the coupon code
		String codeText = driver.findElementByXPath("//div[@class='promo-title']").getText();
		codeText = codeText.replaceAll("\n", "");
		String couponCode = codeText.substring(8);
		System.out.println("The actual coupon code is "+couponCode);
		
		//To get the discount price 
		String discount = driver.findElementByXPath("//div[@class='promo-discounted-price']/span").getText();
		System.out.println("Price of bag after discount:"+discount);
		discount = discount.replaceAll("\\D", "");
		int discountPrice = Integer.parseInt(discount);
		
		//To calculate the discount amount
		int discountAmount=0;
		if(actPrice > offerEligibleAmtInt) {
			discountAmount = actPrice - discountPrice;
			System.out.println("The calculated discount amount is "+discountAmount);
		}
		
		//7) Check the availability of the product for pincode 682001, print the expected delivery date if it is available
		driver.findElementByXPath("//span[text()='Enter pin-code to know estimated delivery date.']").click();
		Thread.sleep(1000);
		driver.findElementByName("pincode").sendKeys("682001");
		driver.findElementByXPath("//button[text()='CONFIRM PINCODE']").click();
		
		if(driver.findElementByXPath("//span[@class='edd-message-success-details-highlighted']").isDisplayed()) {
			String delivery = driver.findElementByXPath("//ul[@class='edd-message-success-details']//span[1]").getText();
			System.out.println("The expected delivery date is "+delivery);
		}
		else
			System.out.println("Item not available for zip code provided");
		
		//8) Click on Other Informations under Product Details and Print the Customer Care address, phone and email
		driver.findElementByXPath("//div[@class='other-info-toggle']").click();
		Thread.sleep(2000);
		String CustAddr = driver.findElementByXPath("(//span[@class='other-info'])[6]").getText();
		System.out.println("Customer Care Address: "+CustAddr);
		
		//9) Click on ADD TO BAG and then GO TO BAG
		driver.findElementByXPath("//div[@class='pdp-addtocart-button']").click();
		Thread.sleep(1000);
		
		WebElement eleGoBag = driver.findElementByXPath("//span[text()='GO TO BAG']/parent::div/parent::a");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", eleGoBag);
		
		//10) Check the Order Total before apply coupon
		String orderTotal = driver.findElementByXPath("//span[text()='Order Total']/following-sibling::span").getText();
		System.out.println("Total Amount before coupon is applied: "+orderTotal);
			
		//11) Enter Coupon Code and Click Apply
		driver.findElementById("couponCodeInput").sendKeys(couponCode);
		driver.findElementByXPath("//button[text()='Apply']").click();
		
		//12) Verify the Coupon Savings amount(round off if it in decimal) under Order Summary and the matches the amount calculated in Product details
		String savings = driver.findElementByClassName("you-save-text").getText();
		savings = savings.replaceAll("[^0-9.]", "");
		String actualSavings = savings.substring(1);
		System.out.println("The actual savings amount is-->"+actualSavings);
		Double actSaving = Double.parseDouble(actualSavings);
		int actSavingInt = (int) Math.round(actSaving);
		System.out.println("Integer value of actual saving is "+actSavingInt);
		
		if(actSavingInt == discountAmount) 
			System.out.println("Savings amount matches with the calculated discount amount.");
		else
			System.out.println("Savings amount does not match with the calculated discount amount.");	
		
		//13) Click on Delete and Delete the item from Bag
		driver.findElementByClassName("delete-btn").click();
		Thread.sleep(500);
		WebElement eleDelete = driver.findElementByXPath("//div[text()='DELETE']");
		wait.until(ExpectedConditions.visibilityOf(eleDelete)).click();
		
		//14) Close all the browsers
		driver.quit();

	}

}
