package day17.tc17;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC17_Azure {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		
		HashMap<String, Object> chromePref = new HashMap<String, Object>();
		chromePref.put("download.default_directory", "F:\\CheckDownload");
		options.setExperimentalOption("prefs", chromePref);
		
		ChromeDriver driver = new ChromeDriver(options);
		//WebDriverWait wait = new WebDriverWait(driver,30);
		
		//1) Go to https://azure.microsoft.com/en-in/
		driver.get("https://azure.microsoft.com/en-in/");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//2) Click on Pricing
		driver.findElementByLinkText("Pricing").click();
		Thread.sleep(1000);
		
		//3) Click on Pricing Calculator
		driver.findElementByXPath("//a[text()[normalize-space()='Pricing calculator']]").click();
		Thread.sleep(1000);
		
		//4) Click on Containers
		driver.findElementByXPath("//button[@value='containers']").click();
		Thread.sleep(1000);
		
		//5) Select Container Instances
		driver.findElementByXPath("(//span[text()='Container Instances'])[3]").click();
		Thread.sleep(500);
		
		//6) Click on Container Instance Added View
		driver.findElementByLinkText("View").click();
		
		//7) Select Region as "South India"
		WebElement eleRegion = driver.findElementByName("region");
		Select region = new Select(eleRegion);
		region.selectByVisibleText("South India");
		Thread.sleep(500);
		
		//8) Set the Duration as 180000 seconds
		driver.findElementByName("seconds").clear();
		Thread.sleep(1000);
		driver.findElementByName("seconds").sendKeys("80000");
		
		//9) Select the Memory as 4GB
		WebElement eleMemory = driver.findElementByName("memory");
		Select memory = new Select(eleMemory);
		memory.selectByVisibleText("4 GB");
		Thread.sleep(500);
		
		//10) Enable SHOW DEV/TEST PRICING
		driver.findElementById("devtest-toggler").click();
		
		//11) Select Indian Rupee  as currency
		WebElement eleCurrency = driver.findElementByXPath("//select[@class='select currency-dropdown']");
		Select currency = new Select(eleCurrency);
		currency.selectByVisibleText("Indian Rupee (₹)");
		Thread.sleep(500);
		
		//12) Print the Estimated monthly price
		String monthlyPrice = driver.findElementByXPath("(//div[@class='column large-3 text-right total']//span)[4]").getText();
		System.out.println("Estimated monthly price: "+monthlyPrice);
		Thread.sleep(500);
		
		//13) Click on Export to download the estimate as excel
		driver.findElementByXPath("//button[text()='Export']").click();
		Thread.sleep(500);
		
		//14) Verify the downloded file in the local folder
		File excel = new File("F:\\CheckDownload");
		boolean file = excel.exists();
		if(file == true)
			System.out.println("File downloaded successfully.");
		else
			System.out.println("File not downloaded successfully.");
		Thread.sleep(2000);
		
		//15) Navigate to Example Scenarios and Select CI/CD for Containers
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-700)");
		WebElement eleExample = driver.findElementByLinkText("Example Scenarios");
		js.executeScript("arguments[0].click()", eleExample);
		Thread.sleep(2000);
		
		//16) Click Add to Estimate
		WebElement eleAdd = driver.findElementByXPath("//button[text()='Add to estimate']");
		js.executeScript("arguments[0].click()", eleAdd);
		Thread.sleep(5000);
		
		js.executeScript("window.scrollBy(0,500)");
		
		//17) Change the Currency as Indian Rupee
		WebElement eleCurr = driver.findElementByXPath("//select[@class='select currency-dropdown']");
		Select currency1 = new Select(eleCurr);
		currency1.selectByVisibleText("Indian Rupee (₹)");
		Thread.sleep(2000);
		
		//18) Enable SHOW DEV/TEST PRICING
		WebElement eleToggle = driver.findElementById("devtest-toggler");
		js.executeScript("arguments[0].click()", eleToggle);
		Thread.sleep(3000);
		
		//19) Export the Estimate
		chromePref.put("download.default_directory", "F:\\Download1");
		driver.findElementByXPath("//button[text()='Export']").click();
		Thread.sleep(2000);
		
		//20) Verify the downloded file in the local folder
		File excel1 = new File("F:\\CheckDownload");
		boolean file1 = excel1.exists();
		if(file1 == true)
			System.out.println("File downloaded successfully.");
		else
			System.out.println("File not downloaded successfully.");

	}

}
