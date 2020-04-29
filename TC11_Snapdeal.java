package day11.tc11;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC11_Snapdeal {

	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver,30);
	
		//1) Go to https://www.snapdeal.com/
		driver.get("https://www.snapdeal.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//‎2) Mouse over on Toys, Kids' Fashion & more and click on Toys
		WebElement eleKids = driver.findElementByLinkText("Toys, Kids' Fashion & more");
		Actions builder = new Actions(driver);
		builder.moveToElement(eleKids).build().perform();
		
		WebElement eleToys = driver.findElementByXPath("//span[text()='Toys']");
		wait.until(ExpectedConditions.visibilityOf(eleToys)).click();
		
		//3) Click Educational Toys in Toys & Games
		WebElement eleEducation = driver.findElementByXPath("//div[text()='Educational Toys']");
		wait.until(ExpectedConditions.visibilityOf(eleEducation)).click();
		
		//‎4) Click the Customer Rating 4 star and Up 
		WebElement eleRating = driver.findElementByXPath("(//input[@type='radio']/following-sibling::label)[1]");
		wait.until(ExpectedConditions.elementToBeClickable(eleRating)).click();
		Thread.sleep(2000);
		
		//5) Click the offer as 40-50
		WebElement eleOffer = driver.findElementByXPath("//input[@value='40%20-%2050']/following-sibling::label[1]");
		wait.until(ExpectedConditions.elementToBeClickable(eleOffer)).click();
		
		//6) Check the availability for the pincode
		WebElement elePin = driver.findElementByXPath("//input[@placeholder='Enter your pincode']");
		wait.until(ExpectedConditions.elementToBeClickable(elePin)).sendKeys("600117");
		Thread.sleep(1000);
		
		driver.findElementByXPath("//button[text()='Check']").click();
		Thread.sleep(1000);
			
		//7) Click the Quick View of the first product 
		WebElement eleFirstProduct = driver.findElementByXPath("(//img[@class='product-image wooble'])[1]");
		wait.until(ExpectedConditions.visibilityOf(eleFirstProduct));
		builder.moveToElement(eleFirstProduct).build().perform();
		
		WebElement eleQuick = driver.findElementByXPath("(//div[@class='clearfix row-disc']//div)[1]");
		wait.until(ExpectedConditions.visibilityOf(eleQuick)).click();
		Thread.sleep(1000);
		
		//8) Click on View Details
		WebElement eleViewDetails = driver.findElementByXPath("//a[contains(text(),'view details')]"); 
		wait.until(ExpectedConditions.visibilityOf(eleViewDetails)).click();
		 
		//9) Capture the Price of the Product and Delivery Charge
		WebElement elePrice = driver.findElementByClassName("payBlkBig");
		String price = wait.until(ExpectedConditions.visibilityOf(elePrice)).getText();
		System.out.println("Price of the product: "+price);
		price = price.replaceAll("\\D", "");
		int Price = Integer.parseInt(price);
		
		String delivery = driver.findElementByXPath("(//span[@class='availCharges'])[2]").getText();
		System.out.println("Delivery Charges: "+delivery);
		delivery = delivery.replaceAll("\\D", "");
		int Delivery = Integer.parseInt(delivery);
		
		int totalCharges = Price + Delivery;
		System.out.println("Total charges of first product in the result: "+totalCharges);
		
		//Click on Add to Cart
		driver.findElementByXPath("//span[text()='add to cart']").click();
		
		//10) Validate the You Pay amount matches the sum of (price+deliver charge)
		WebElement elePay = driver.findElementByXPath("//div[@class='you-pay']//span[1]");
		String pay = wait.until(ExpectedConditions.visibilityOf(elePay)).getText();
		System.out.println("You pay: "+pay);
		pay = pay.replaceAll("\\D", "");
		int youPay = Integer.parseInt(pay);
		
		if(totalCharges == youPay)
			System.out.println("You pay amount matches with the product price + deliver charges");
		else
			System.out.println("You pay amount does not match with the product price + deliver charges");
		
		//11) Search for Sanitizer
		driver.findElementById("inputValEnter").sendKeys("Sanitizer",Keys.ENTER);
		
		//12) Click on Product "BioAyurveda Neem Power Hand Sanitizer"
		WebElement eleSanitizer = driver.findElementByXPath("//p[@title='BioAyurveda Neem Power  Hand Sanitizer 500 mL Pack of 1']");
		wait.until(ExpectedConditions.visibilityOf(eleSanitizer)).click();
		
		Set<String> windowHandles = driver.getWindowHandles();
		List<String>windowHandlesList = new ArrayList<String>(windowHandles);
		driver.switchTo().window(windowHandlesList.get(1));
		
		//13) Capture the Price and Delivery Charge
		WebElement elePrice1 = driver.findElementByClassName("payBlkBig");
		String price1 = wait.until(ExpectedConditions.visibilityOf(elePrice1)).getText();
		System.out.println("Price of the product: "+price1);
		price1 = price1.replaceAll("\\D", "");
		int Price1 = Integer.parseInt(price1);
		
		String delivery1 = driver.findElementByXPath("(//span[@class='availCharges'])[2]").getText();
		System.out.println("Delivery Charges: "+delivery1);
		delivery1 = delivery1.replaceAll("\\D", "");
		int Delivery1 = Integer.parseInt(delivery1);
		
		int total2 = Price1 + Delivery1;
		System.out.println("Total charges of Sanitizer: "+total2);
		
		//14) Click on Add to Cart
		Thread.sleep(1000);
		//JavascriptExecutor executor = (JavascriptExecutor)driver;
		//executor.executeScript("scroll(0, 250)");
		driver.findElementById("add-cart-button-id").click();
		
		Thread.sleep(3000);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByClassName("cartTextSpan")));
		driver.findElementByClassName("cartTextSpan").click();
		
		//15) Click on Cart  
		WebElement eleCart = driver.findElementByClassName("cartTextSpan");
		wait.until(ExpectedConditions.elementToBeClickable(eleCart));
		builder.click(eleCart).build().perform();
				
		//16) Validate the Proceed to Pay matches the total amount of both the products
		WebElement eleProceed = driver.findElementByXPath("//input[@type='button']");
		String grandTotal = wait.until(ExpectedConditions.visibilityOf(eleProceed)).getAttribute("value");
		System.out.println("Proceed to Pay Amount: "+grandTotal);
		grandTotal = grandTotal.replaceAll("\\D", "");
		int proceedPay = Integer.parseInt(grandTotal);
		
		int GrandTotal = totalCharges + total2;
		System.out.println("Calculated Total Amount: "+GrandTotal);
		
		if(GrandTotal == proceedPay)
			System.out.println("The Proceed to Pay amount matches with total amount of both products");
		else
			System.out.println("The Proceed to Pay amount does not match with total amount of both products");
		
		//17) Close all the windows
		driver.quit();
	}

}
