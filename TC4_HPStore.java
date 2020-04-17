package day4.tc4;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC4_HPStore {

	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		
		//1) Go to https://store.hp.com/in-en/
		driver.get("https://store.hp.com/in-en/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);		
		
		//2) Mouse over on Laptops menu and click on Pavilion
		WebElement eleLaptop = driver.findElementByXPath("//span[text()='Laptops']");
		Actions builder = new Actions(driver);
		builder.moveToElement(eleLaptop).perform();
		
		driver.findElementByXPath("//span[text()='Pavilion']").click();
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("scroll(0, 250)");
		
		//3) Under SHOPPING OPTIONS -->Processor -->Select Intel Core i7
		driver.findElementByXPath("(//span[text()='Processor'])[2]").click();
		executor.executeScript("scroll(0, 250)");
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Intel Core i7']")));
		
		WebElement elei7 = driver.findElement(By.xpath("//span[text()='Intel Core i7']"));
		builder.moveToElement(elei7).click().perform();
		Thread.sleep(3000);
		executor.executeScript("scroll(0, 250)");
		
		//4) Hard Drive Capacity -->More than 1TB
		driver.findElementByXPath("//span[text()='More than 1 TB']").click();	
		Thread.sleep(3000);
		
		//5) Select Sort By: Price: Low to High
		driver.findElementById("sorter").click();
		
		WebElement eleSort = driver.findElementById("sorter");
		Select dropdown = new Select(eleSort);
		dropdown.selectByValue("bv_average_overall_rating_asc");
		Thread.sleep(3000);
		
		//6) Print the First resulting Product Name and Price
		String product = driver.findElementByXPath("(//a[@class='product-item-link'])[1]").getText();
		System.out.println("First resulting product name: "+product);
		
		String price = driver.findElementByXPath("(//span[@class='price'])[2]").getText();
		System.out.println("Price of first resulting product: "+price); 
		
		//7) Click on Add to Cart
		executor.executeScript("scroll(0, 250)");
		driver.findElementByXPath("(//button[@type='submit'])[2]").click();
		Thread.sleep(3000);
		
		//8) Click on Shopping Cart icon --> Click on View and Edit Cart
		driver.findElementByXPath("//a[@class='action showcart']").click();
		driver.findElementByXPath("//a[@class='action primary viewcart']").click();
		
		//9) Check the Shipping Option --> Check availability at Pincode
		executor.executeScript("scroll(0, 250)");
		driver.findElementByName("pincode").sendKeys("600117");
		driver.findElementByXPath("//button[text()='check']").click();
		Thread.sleep(3000);
		
		//10) Verify the order Total against the product price
		String orderTotal = driver.findElementByXPath("(//td[@class='amount'])[3]").getText();
		System.out.println("Order Total Amount: "+orderTotal);
		if(price.equals(orderTotal))
			System.out.println("No shipping charges applicable");
		else
			System.out.println("Shipping charges applicable"); 
			
		//11) Proceed to Checkout if Order Total and Product Price matches
		
		WebElement eleProceed = driver.findElementByXPath("(//button[@title='Proceed to Checkout'])[1]");
		wait.until(ExpectedConditions.visibilityOf(eleProceed));
		eleProceed.click();
		Thread.sleep(7000);
		
		//12) Click on Place Order
		executor.executeScript("scroll(0, 1250)");
		Thread.sleep(3000);
		driver.findElementByXPath("(//button[@title='Place Order'])[6]").click();
		
		//13) Capture the payment warning message and Print
		executor.executeScript("scroll(0, 500)");
		String warning = driver.findElementByXPath("//div[@class='message notice']").getText();
		System.out.println("Warning Message: "+warning);
		
		//14) Close Browser
		driver.close();

	}

}
