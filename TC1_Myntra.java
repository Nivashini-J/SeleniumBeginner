package day1.tc1;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class TC1_Myntra {

	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		
		//1) Open https://www.myntra.com/
		driver.get("https://www.myntra.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//2) Mouse over on WOMEN
		Actions builder = new Actions(driver);
		WebElement eleWomen = driver.findElementByXPath("(//a[text()='Women'])[1]");
		builder.moveToElement(eleWomen).perform();
				
		//3) Click Jackets & Coats
		driver.findElementByLinkText("Jackets & Coats").click();
		Thread.sleep(3000);
		
		//4) Find the total count of item (top) -> getText -> String
		String total = driver.findElementByXPath("//span[@class='title-count']").getText();
		System.out.println("Total count of item:"+total);
		total = total.replaceAll("\\D", "");
		int totalCount = Integer.parseInt(total);
				
		//5) Validate the sum of categories count matches
		String jacketsCount = driver.findElementByXPath("(//span[@class='categories-num'])[1]").getText();
		String coatsCount = driver.findElementByXPath("(//span[@class='categories-num'])[2]").getText();
		jacketsCount = jacketsCount.replaceAll("\\D", "");
		coatsCount = coatsCount.replaceAll("\\D", "");
		int jCount = Integer.parseInt(jacketsCount);
		int cCount = Integer.parseInt(coatsCount);
		int jacketCoats = jCount + cCount;
		
		System.out.println("Jackets count + Coats count = "+jacketCoats);
		if(totalCount==jacketCoats)
			System.out.println("The sum of jackets and coats matches with the total.");
		else
			System.out.println("Count not matched.");
		
		//6) Check Coats
		driver.findElementByXPath("(//label[@class='common-customCheckbox vertical-filters-label'])[2]").click();
		
		//7) Click + More option under BRAND
		driver.findElementByXPath("//div[@class='brand-more']").click();
		
		//8) Type MANGO and click checkbox
		driver.findElementByClassName("FilterDirectory-searchInput").sendKeys("MANGO");
		driver.findElementByXPath("//label[@class=' common-customCheckbox']//div[1]").click();
		
		//9) Close the pop-up x
		driver.findElementByXPath("//div[@class='FilterDirectory-titleBar']/span").click();
		Thread.sleep(5000);
		
		//10) Confirm all the Coats are of brand MANGO
		List<WebElement> brandList = driver.findElementsByXPath("//h3[@class='product-brand']");
		
		for (WebElement eachBrand : brandList) {
			String brand = eachBrand.getText();
			System.out.println("Brand Name:"+brand);
			}
				
		//11) Sort by Better Discount
		WebElement sortBy = driver.findElementByClassName("sort-sortBy");
		builder.moveToElement(sortBy).perform();
		driver.findElementByXPath("//label[text()='Better Discount']").click();
		
		//12) Find the price of first displayed item
		List<WebElement> priceList = driver.findElementsByXPath("//span[@class='product-discountedPrice']");
		String price = priceList.get(0).getText();
		System.out.println("Price of the first displayed item: "+price);
			   
		//13) Mouse over on size of the first item
		WebElement eleSize = driver.findElementByXPath("(//div[@class='product-productMetaInfo'])[1]");
		builder.moveToElement(eleSize).perform();
		
		//14) Click on WishList Now
		driver.findElementByXPath("//span[text()='wishlist now']").click();
		
		//15) Close Browser
		driver.close();
	}

}
