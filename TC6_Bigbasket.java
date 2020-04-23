package day6.tc6;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class TC6_Bigbasket {

	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		//WebDriverWait wait = new WebDriverWait(driver, 30);
		
		//1) Go to https://www.bigbasket.com/
		driver.get("https://www.bigbasket.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//2) mouse over on Shop by Category 
		Actions builder = new Actions(driver);
		WebElement eleCategory = driver.findElementByXPath("//a[text()=' Shop by Category ']");
		builder.moveToElement(eleCategory).perform();
		
		//3)Go to FOODGRAINS, OIL & MASALA --> RICE & RICE PRODUCTS 
		WebElement eleFoodgrains = driver.findElementByXPath("(//a[text()='Foodgrains, Oil & Masala'])[2]");
		builder.moveToElement(eleFoodgrains).perform();
		
		WebElement eleRice = driver.findElementByXPath("(//a[text()='Rice & Rice Products'])[2]");
		builder.moveToElement(eleRice).perform();
		
		//4) Click on Boiled & Steam Rice
		driver.findElementByXPath("(//a[text()='Boiled & Steam Rice'])[2]").click();
		Thread.sleep(2000);
		
		//5) Choose the Brand as bb Royal
		driver.findElementByXPath("(//span[text()='bb Royal'])[1]").click();
		Thread.sleep(3000);
		
		//6) Go to Ponni Boiled Rice - Super Premium and select 5kg bag from Dropdown
		driver.findElementByXPath("(//button[@data-toggle='dropdown'])[4]").click();
		Thread.sleep(5000);
		
		driver.findElementByXPath("(//ul[@class='dropdown-menu drop-select']//span[text()='5 kg'])[3]").click();
		Thread.sleep(3000);
		
		//7) print the price of Rice
		String priceRice = driver.findElementByXPath("(//span[@class='discnt-price'])[3]").getText();
		System.out.println("Price of 5 kg Rice: "+priceRice);
		
		//8) Click Add button
		driver.findElementByXPath("(//button[text()='Add '])[3]").click();
		driver.findElementByXPath("(//a[text()='Continue'])[1]").click();
		
		//9) Verify the success message displayed
		//WebElement eleSuccess = driver.findElementByXPath("//div[@class='toast toast-success']");
		//String success = eleSuccess.getText();
		//System.out.println("Success Message: "+success);
		
		//10) Type Dal in Search field and enter
		driver.findElementById("input").sendKeys("Dal",Keys.ENTER);
		Thread.sleep(3000);
		
		//12) Go to Toor/Arhar Dal and select 2kg & set Qty 2 
		driver.findElementByXPath("(//button[@class='btn btn-default dropdown-toggle form-control'])[2]").click();
		Thread.sleep(2000);
		driver.findElementByXPath("(//a[contains(text(),'Thuvaram Paruppu')])[1]/following::span[2]/following::span[text()='2 kg'][1]").click();
		Thread.sleep(2000);
		driver.findElementByXPath("(//div[@class='input-group']/input)[4]").clear();
		driver.findElementByXPath("(//div[@class='input-group']/input)[4]").sendKeys("2");
		
		//13) Print the price of Dal
		String priceDal = driver.findElementByXPath("(//span[@class='discnt-price'])[3]").getText();
		System.out.println("Price of 1 kg Toor Dal: "+priceDal);
		
		//14) Click Add button
		driver.findElementByXPath("(//button[text()='Add '])[3]").click();
		
		//Verify Success Message
		
		String success = driver.findElementByXPath("//div[@class='toast-title']").getText();
		System.out.println("Success Message: "+success);
		Thread.sleep(7000);
		
		//15) Mouse hover on My Basket 
		WebElement eleBasket = driver.findElementByXPath("//span[@title='Your Basket']");
		builder.moveToElement(eleBasket).perform();
		Thread.sleep(3000);
		
		//16) Validate the Sub Total displayed for the selected items
		
		String Total = driver.findElement(By.xpath("//span[@qa='subTotalMB']")).getText();
		System.out.println("Total Amount:"+Total);
		Double totalPrice = Double.parseDouble(Total);
		
		String item1 = driver.findElementByXPath("(//span[@qa='priceMB'])[1]").getText();
		System.out.println("Amount of Rice: "+item1);
		Double Item1 = Double.parseDouble(item1);
		
		String Qtyitem1 = driver.findElementByXPath("(//div[@qa='pcsMB'])[1]").getText();
		Qtyitem1 = Qtyitem1.substring(0,1);
		System.out.println("Quantity of item1: "+Qtyitem1);
		Double Qty1 = Double.parseDouble(Qtyitem1);
				
		String item2 = driver.findElementByXPath("(//span[@qa='priceMB'])[2]").getText();
		System.out.println("Amount of Dal: "+item2);
		Double Item2 = Double.parseDouble(item2);
		
		String Qtyitem2 = driver.findElementByXPath("(//div[@qa='pcsMB'])[2]").getText();
		Qtyitem2 = Qtyitem2.substring(0,1);
		System.out.println("Quantity of item2: "+Qtyitem2);
		Double Qty2 = Double.parseDouble(Qtyitem2);
		
		double calcTotal = (Item1*Qty1) + (Item2*Qty2);
		
		if(totalPrice == calcTotal)
			System.out.println("The sub total is correct. "+calcTotal);
		else
			System.out.println("The sub total is wrong. "+calcTotal);
		
		//17) Reduce the Quantity of Dal as 1 
		driver.findElementByXPath("(//button[@qa='decQtyMB'])[2]").click();
		Thread.sleep(3000);
		
		//18) Validate the Sub Total for the current items
		String Qtydal = driver.findElementByXPath("(//div[@qa='pcsMB'])[2]").getText();
		Qtydal = Qtydal.substring(0,1);
		System.out.println("Quantity of Dal after reduction: "+Qtydal);
		Double DalQty2 = Double.parseDouble(Qtydal);
		
		String Total1 = driver.findElement(By.xpath("//span[@qa='subTotalMB']")).getText();
		System.out.println("Updated Total Amount:"+Total1);
		Double totalPrice1 = Double.parseDouble(Total1);		
		
		double calcTotal1 = (Item1*Qty1) + (Item2*DalQty2);
		
		if(totalPrice1 == calcTotal1)
			System.out.println("The sub total is correct. "+calcTotal1);
		else
			System.out.println("The sub total is wrong. "+calcTotal1);		
		
		//19) Close the Browser
		driver.close();

	}

}
