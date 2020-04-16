package day2.tc2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class TC2_Nykaa {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		
		//1) Go to https://www.nykaa.com/
		driver.get("https://www.nykaa.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
		//2) Mouseover on Brands and Mouseover on Popular
		WebElement eleBrands = driver.findElementByXPath("//a[text()='brands']");
		WebElement elePopular = driver.findElementByXPath("//a[text()='Popular']");
		
		Actions builder = new Actions(driver);
		builder.moveToElement(eleBrands).perform();
		Thread.sleep(5000);
		builder.moveToElement(elePopular).perform();
		
		//3) Click L'Oreal Paris
		driver.findElementByXPath("//img[@src='https://adn-static2.nykaa.com/media/wysiwyg/2019/lorealparis.png']").click();
		
		//4) Go to the newly opened window and check the title contains L'Oreal Paris
		Set<String> winSet = driver.getWindowHandles();
		List<String> winList = new ArrayList<String>(winSet);
		driver.switchTo().window(winList.get(1));
		String title = driver.getTitle();
				
		if(title.contains("L'Oreal Paris"))
			System.out.println(title);
		Thread.sleep(3000);
		
		//5) Click sort By and select customer top rated
		driver.findElementByXPath("//span[@class='pull-left']").click();
		driver.findElementByXPath("//span[text()='customer top rated']").click();
		Thread.sleep(5000);
		
		//6) Click Category and click Shampoo
		driver.findElementByXPath("//div[text()='Category']").click();
		driver.findElementByXPath("(//label[@for='chk_Shampoo_undefined']//span)[1]").click();
		
		//7) check whether the Filter is applied with Shampoo
		String filter = driver.findElementByXPath("//li[text()='Shampoo']").getText();
		if(filter.contains("Shampoo"))
			System.out.println("Filter applied: "+filter);
			
		//8) Click on L'Oreal Paris Colour Protect Shampoo
		driver.findElementByXPath("(//div[@class='m-content__product-list__title']//span)[4]").click();
		
		//9) Go to the new window and select size as 175ml
		Set<String> winSet1 = driver.getWindowHandles();
		List<String> winList1 = new ArrayList<String>(winSet1);
		driver.switchTo().window(winList1.get(2));
		
		driver.findElementByXPath("//span[text()='175ml']").click();
		
		//10) Print the MRP of the product
		String mrp = driver.findElementByXPath("(//span[@class='post-card__content-price-offer'])[1]").getText();
		System.out.println("MRP of the produce: "+mrp);
		
		//11) Click on ADD to BAG
		driver.findElementByXPath("(//div[@class='pull-left']/div)[1]").click();
		
		//12) Go to Shopping Bag 
		driver.findElementByXPath("//div[@class='AddBagIcon']").click();
		Thread.sleep(3000);
		
		//13) Print the Grand Total amount
		String total = driver.findElementByXPath("(//div[@class='value'])[4]").getText();
		System.out.println("Grand Total: "+total);
		
		//14) Click Proceed
		driver.findElementByXPath("//button[@class='btn full fill no-radius proceed ']").click();
		
		//15) Click on Continue as Guest
		driver.findElementByXPath("//button[@class='btn full big']").click();
		
		//16) Print the warning message (delay in shipment)
		String warning = driver.findElementByXPath("//div[@class='message']").getText();
		System.out.println("Warning Message: "+warning);
		
		//17) Close all windows
		driver.quit();

	}

}
