package day13.tc13;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC13_Shiksha {

	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver,30);
		
		//1) Go to https://studyabroad.shiksha.com/
		driver.get("https://studyabroad.shiksha.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//2) Mouse over on Colleges and click MS in Computer Science &Engg under MS Colleges
		WebElement eleColleges = driver.findElementByXPath("(//label[contains(text(),\"Colleges\")])[2]");
		Actions builder = new Actions(driver);
		builder.moveToElement(eleColleges).build().perform();
		
		WebElement eleCSE = driver.findElementByLinkText("MS in Computer Science &Engg");
		wait.until(ExpectedConditions.visibilityOf(eleCSE)).click();
		
		//3) Select GRE under Exam Accepted and Score 300 & Below 
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,300)");
		
		WebElement eleGRE = driver.findElementByXPath("//p[text()='GRE']");
		wait.until(ExpectedConditions.elementToBeClickable(eleGRE)).click();
		Thread.sleep(3000);
		
		WebElement eleScore = driver.findElementByClassName("score-select-field");
		Select score = new Select(eleScore);
		wait.until(ExpectedConditions.elementToBeClickable(eleScore));
		score.selectByVisibleText("300 & below");
		
		//4) Max 10 Lakhs under 1st year Total fees, USA under countries
		Thread.sleep(2000);
		driver.findElementByXPath("//p[text()='Max 10 Lakhs']").click();
		
		Thread.sleep(5000);
		WebElement eleUSA = driver.findElementByXPath("//a[text()='USA']/parent::p");
		wait.until(ExpectedConditions.elementToBeClickable(eleUSA)).click();;
		
		//5) Select Sort By: Low to high 1st year total fees
		Thread.sleep(3000);
		WebElement eleSort = driver.findElementById("categorySorter");
		Select sort = new Select(eleSort);
		sort.selectByVisibleText("Low to high 1st year total fees");
		
		//6) Click Add to compare of the College having least fees with Public University, Scholarship and Accommodation facilities
		List<WebElement> totalCollages = driver.findElementsByXPath("//div[@class='tuple-detail']");
		List<Double> feesList = new ArrayList<Double>();
		js.executeScript("window.scrollBy(0,350)");
		Thread.sleep(2000);
		System.out.println("Fees of collages having Public University, Scholarship & Accommodation facilities: ");
		
		for (int i = 1; i <= totalCollages.size(); i++) {
			
		String pubUniv = driver.findElementByXPath("(//p[text()='Public university']/span)["+i+"]").getAttribute("class");
		String scholar = driver.findElementByXPath("(//p[text()='Scholarship']/span)["+i+"]").getAttribute("class");
		String accom = driver.findElementByXPath("(//p[text()='Accommodation']/span)["+i+"]").getAttribute("class");
		Thread.sleep(1000);
		
		if(pubUniv.equalsIgnoreCase("tick-mark") && scholar.equalsIgnoreCase("tick-mark") && accom.equalsIgnoreCase("tick-mark")) {
				String strfees = driver.findElementByXPath("(//strong[text()=' 1st Year Total Fees']/following-sibling::p)["+i+"]").getText();
				System.out.println(strfees);
				double fees = Double.parseDouble(strfees.replaceAll("[\\s+a-zA-Z ]", ""));
				feesList.add(fees);
			}
				
		}
		Thread.sleep(2000);
		Collections.sort(feesList);
		Double leastfee = feesList.get(0);
		System.out.println("Least fee amount among the collages: "+leastfee);
		
		driver.findElementByXPath("//p[contains(text(),'"+leastfee+"')]/ancestor::div[@class='tuple-box']//span[@class='common-sprite']").click();
		Thread.sleep(1000);
		
		//7) Select the first college under Compare with similar colleges 
		WebElement eleSimilarColg = driver.findElementByXPath("(//a[@class='add-tag-title'])[1]");
		wait.until(ExpectedConditions.visibilityOf(eleSimilarColg)).click();
		
		//8) Click on Compare College>
		WebElement eleCompare = driver.findElementByXPath("//strong[contains(text(), 'Compare Colleges')]");
		wait.until(ExpectedConditions.elementToBeClickable(eleCompare)).click();
		
		//9) Select When to Study as 2021
		driver.findElementByXPath("//strong[text()='2021']").click();
		
		//10) Select Preferred Countries as USA
		driver.findElementByXPath("//div[text()='Preferred Countries']/following-sibling::div[1]").click();
		
		WebElement eleUSAChk = driver.findElementByXPath("(//input[@name='destinationCountry[]']/following-sibling::label)[1]");
		wait.until(ExpectedConditions.visibilityOf(eleUSAChk)).click();
		
		WebElement eleOK = driver.findElementByLinkText("ok");
		wait.until(ExpectedConditions.visibilityOf(eleOK)).click();
		Thread.sleep(1000);
		
		//11) Select Level of Study as Masters
		driver.findElementByXPath("//strong[text()='Masters']").click();
		
		//12) Select Preferred Course as MS
		WebElement eleCourse = driver.findElementByXPath("//div[text()='Preferred Course']/following-sibling::div[1]");
		wait.until(ExpectedConditions.visibilityOf(eleCourse)).click();
		
		WebElement eleMS = driver.findElementByXPath("//li[text()='MS']");
		wait.until(ExpectedConditions.visibilityOf(eleMS)).click();
		Thread.sleep(3000);
		
		//13) Select Specialization as "Computer Science & Engineering"
		WebElement eleSpec = driver.findElementByXPath("//div[text()='Preferred Specialisations']");
		js.executeScript("arguments[0].click();", eleSpec);
				
		WebElement eleCSESpl = driver.findElementByXPath("//li[text()='Computer Science & Engineering']");
		wait.until(ExpectedConditions.elementToBeClickable(eleCSESpl)).click();
		
		//14) Click on Sign Up
		driver.findElementById("signup").click();
		
		//15) Print all the warning messages displayed on the screen for missed mandatory fields
		List<WebElement> errorElement = driver.findElementsByXPath("//div[contains(@id,'error')]//div[contains(text(),'Please')]");

		System.out.println("\nError messages displayed: ");
		for (WebElement eachElement : errorElement) {
			String error = eachElement.getText(); 
			if(error.length()>0) {
				System.out.println(error);
			} 
		}
		
		//16) close browser
		driver.close();
	}

}
