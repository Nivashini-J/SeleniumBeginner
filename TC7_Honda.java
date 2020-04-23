package day7.tc7;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC7_Honda {

	public static void main(String[] args) {
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		
		//1) Go to https://www.honda2wheelersindia.com/
		driver.get("https://www.honda2wheelersindia.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.findElementByClassName("close").click();
	
		//2) Click on scooters and click dio
		driver.findElementByXPath("(//a[text()='Scooter'])[1]").click();
		WebElement eledio = driver.findElementByXPath("(//div[@class='item']//img)[4]");
		wait.until(ExpectedConditions.visibilityOf(eledio));
		eledio.click();
		
		//3) Click on Specifications and mouse hover on ENGINE
		WebElement eleSpec = driver.findElementByXPath("//a[text()='Specifications']");
		wait.until(ExpectedConditions.visibilityOf(eleSpec));
		eleSpec.click();
		
		WebElement eleEngine = driver.findElementByXPath("//a[text()='ENGINE']");
		wait.until(ExpectedConditions.visibilityOf(eleEngine));
		Actions builder = new Actions(driver);
		builder.moveToElement(eleEngine).build().perform();
		
		//4) Get Displacement value
		WebElement eleValue = driver.findElementByXPath("//span[text()='Displacement']/following-sibling::span");
		wait.until(ExpectedConditions.visibilityOf(eleValue));
		String value = eleValue.getText();
		System.out.println("Displacement Value of Dio: "+value);
		value = value.replaceAll("c", "");
		Float val1 = Float.parseFloat(value);
		
		//5) Go to Scooter and click Activa 125
		driver.findElementByXPath("(//a[text()='Scooter'])[1]").click();
		WebElement ele125 = driver.findElementByXPath("(//div[@class='item']//img)[6]");
		wait.until(ExpectedConditions.visibilityOf(ele125));
		ele125.click();
		
		//6) Click on Specifications and mouseover on ENGINE
		WebElement eleSpec125 = driver.findElementByXPath("//a[text()='Specifications']");
		wait.until(ExpectedConditions.visibilityOf(eleSpec125));
		eleSpec125.click();
		
		WebElement eleEngine125 = driver.findElementByXPath("//a[text()='ENGINE']");
		wait.until(ExpectedConditions.visibilityOf(eleEngine125));
		builder.moveToElement(eleEngine125).build().perform();
		
		//7) Get Displacement value
		WebElement eleValue125 = driver.findElementByXPath("//span[text()='Displacement']/following-sibling::span");
		wait.until(ExpectedConditions.visibilityOf(eleValue125));
		String value125 = eleValue125.getText();
		System.out.println("Displacement Value of Activa 125: "+value125);
		value125 = value125.replaceAll("c", "");
		Float val2 = Float.parseFloat(value125);
		
		//8) Compare Displacement of Dio and Activa 125 and print the Scooter name having better Displacement.
		if(val1 > val2)
			System.out.println("Dio has better displacement value.");
		else
			System.out.println("Activa 125 has better displacement value.");
		
		//9) Click FAQ from Menu
		driver.findElementByLinkText("FAQ").click();
		
		//10) Click Activa 125 BS-VI under Browse By Product
		WebElement eleBSVI = driver.findElementByLinkText("Activa 125 BS-VI");
		wait.until(ExpectedConditions.visibilityOf(eleBSVI));
		eleBSVI.click();
		
		//11) Click  Vehicle Price 
		WebElement eleVprice = driver.findElementByXPath("//a[text()=' Vehicle Price']");
		wait.until(ExpectedConditions.visibilityOf(eleVprice));
		eleVprice.click();
		
		//12) Make sure Activa 125 BS-VI selected and click submit
		
		WebElement eleScooter = driver.findElement(By.id("ModelID6"));
		wait.until(ExpectedConditions.visibilityOf(eleScooter));
		Select dropdown = new Select(eleScooter);
		WebElement firstSelectedOption = dropdown.getFirstSelectedOption();
		String model = firstSelectedOption.getText();
		
		if(model.contains("Activa 125 BS-VI"))
			driver.findElementByXPath("//button[@id='submit6']").click();
		
		//13) click the price link
		WebElement priceLink = driver.findElementByLinkText("Click here to know the price of Activa 125 BS-VI.");
		wait.until(ExpectedConditions.elementToBeClickable(priceLink));
		priceLink.click();
		
		//14)  Go to the new Window and select the state as Tamil Nadu and  city as Chennai
		Set<String> winSet = driver.getWindowHandles();
		List<String> winList = new ArrayList<String>(winSet);
		driver.switchTo().window(winList.get(1));
		
		WebElement eleState = driver.findElementById("StateID");
		Select state = new Select(eleState);
		state.selectByVisibleText("Tamil Nadu");
		
		WebElement eleCity = driver.findElementById("CityID");
		wait.until(ExpectedConditions.elementToBeClickable(eleCity));
		Select city = new Select(eleCity);
		city.selectByVisibleText("Chennai");		
		
		//15) Click Search
		WebElement eleSearch = driver.findElementByXPath("//button[text()='Search']");
		wait.until(ExpectedConditions.elementToBeClickable(eleSearch));
		eleSearch.click();
		
		//16) Print all the 3 models and their prices
		WebElement table = driver.findElement(By.id("gvshow"));
		List<WebElement> rows = table.findElements(By.tagName("td"));

		for (WebElement eachmodel : rows) {
			System.out.println(eachmodel.getText());
		}
		
		//17) Close all Browsers
		driver.quit();;

	}

}
