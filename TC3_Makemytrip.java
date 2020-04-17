package day3.tc3;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC3_Makemytrip {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver,30);
		
		//1) Go to https://www.makemytrip.com/
		driver.get("https://www.makemytrip.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);		
		
		//2) Click Hotels
		driver.findElementByXPath("(//a[@class='makeFlex hrtlCenter column'])[1]").click();
		
		//3) Enter city as Goa, and choose Goa, India
		driver.findElementByXPath("(//div[@class='hsw_inner']//div)[1]").click();
		Thread.sleep(2000);
		driver.findElementByXPath("//input[@placeholder='Enter city/ Hotel/ Area/ Building']").sendKeys("Goa");
		Thread.sleep(2000);
		driver.findElementByXPath("(//p[@class='locusLabel appendBottom5'])[1]").click();
		
		//4) Enter Check in date as Next month 15th (May 15) and Check out as start date+5
		driver.findElementByXPath("(//div[text()='15'])[2]").click();
		String checkin = driver.findElementByXPath("(//div[text()='15'])[2]").getText();
		System.out.println(checkin);
		int startDate = Integer.parseInt(checkin);
		
		//driver.findElementByXPath("(//div[text()='20'])[2]").click();
		WebElement currentMonth = driver.findElementByXPath("(//div[@class='DayPicker-Month'])[2]");
		currentMonth.findElement(By.xpath("(//div[text()='"+(startDate+5)+"'])[2]")).click();
		
		//5) Click on ROOMS & GUESTS and click 2 Adults and one Children(age 12). Click Apply Button.
		//Click on ROOMS & GUESTS
		driver.findElementById("guest").click();
		
		//click 2 Adults and one Children(age 12)
		driver.findElementByXPath("(//li[text()='2'])[1]").click();
		driver.findElementByXPath("(//li[text()='1'])[2]").click();
		
		WebElement eleAge = driver.findElementByClassName("ageSelectBox");
		Select dropdown = new Select(eleAge);
		dropdown.selectByVisibleText("12");
		Thread.sleep(3000);
		
		//Click Apply Button
		/*JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("scroll(0, 250)");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[text()='APPLY']")).click();*/
		
		WebElement eleApply = driver.findElement(By.xpath("//button[text()='APPLY']"));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", eleApply);
		
		//6) Click Search button
		driver.findElementById("hsw_search_button").click();
		Thread.sleep(2000);
		driver.findElementById("root").click();
		executor.executeScript("scroll(0, 250)");
		
		//7) Select locality as Baga
		driver.findElementByXPath("//label[text()='Baga']").click();
		executor.executeScript("scroll(0, 1000)");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='5 Star']")));
		
		//8) Select 5 start in Star Category under Select Filters
		driver.findElementByXPath("//label[text()='5 Star']").click();
		
		//9) Click on the first resulting hotel and go to the new window
		driver.findElementByXPath("(//div[@class='makeFlex spaceBetween']/div)[1]").click();
		Set<String> winSet = driver.getWindowHandles();
		List<String> winList = new ArrayList<String>(winSet);
		driver.switchTo().window(winList.get(1));
		
		//10) Print the Hotel Name 
		String hotel = driver.findElementById("detpg_hotel_name").getText();
		System.out.println("Hotel Name: "+hotel);
		
		//11) Click MORE OPTIONS link and Select 3Months plan and close
		driver.findElementByXPath("//span[@class='latoBold font10 blueText pointer']").click();
		driver.findElementByXPath("(//span[text()='SELECT'])[1]").click();
		driver.findElementByClassName("close").click();
		
		//12) Click on BOOK THIS NOW
		driver.findElementById("detpg_headerright_book_now").click();
		
		//13) Print the Total Payable amount
		Thread.sleep(5000);
		driver.findElementByXPath("//span[@class='close']").click();
		String totalAmount = driver.findElementById("revpg_total_payable_amt").getText();
		System.out.println("Total Payable AMount: "+totalAmount);
		
		//14) Close all browsers
		driver.quit();
	}

}
