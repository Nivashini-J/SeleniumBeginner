package day9.tc9;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC9_CRM {

	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver,30);
		
		//1) Go to https://demo.1crmcloud.com/
		driver.get("https://demo.1crmcloud.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//2) Give user name as admin and password as admin
		driver.findElementById("login_user").sendKeys("admin",Keys.TAB);
		driver.findElementById("login_pass").sendKeys("admin");
		
		//3) Choose theme as Claro Theme
		WebElement eleTheme = driver.findElementById("login_theme");
		Select theme = new Select(eleTheme);
		theme.selectByVisibleText("Claro Theme");
		
		driver.findElementById("login_button").click();
		
		//4) Click on Sales and Marketing 
		WebElement eleSalesMarketing = driver.findElementByLinkText("Sales & Marketing");
		wait.until(ExpectedConditions.visibilityOf(eleSalesMarketing));
		eleSalesMarketing.click();
		
		//5) Click Create contact
		WebElement eleCreateContact = driver.findElementByXPath("//div[text()='Create Contact']");
		wait.until(ExpectedConditions.visibilityOf(eleCreateContact));
		eleCreateContact.click();
		Thread.sleep(8000);
		
		//6) Select Title and type First name, Last Name, Email and Phone Numbers
		Actions builder = new Actions(driver);
		WebElement eleTitle = driver.findElementById("DetailFormsalutation-input");
		wait.until(ExpectedConditions.elementToBeClickable(eleTitle));
		builder.click(eleTitle).build().perform();
		Thread.sleep(2000);
		
		WebElement eleMrs = driver.findElementByXPath("//div[text()='Mrs.']");
		wait.until(ExpectedConditions.elementToBeClickable(eleMrs));
		builder.click(eleMrs).build().perform();
		
		driver.findElementById("DetailFormfirst_name-input").sendKeys("Sample Fname");
		driver.findElementById("DetailFormlast_name-input").sendKeys("Lname");
		driver.findElementById("DetailFormemail1-input").sendKeys("test@gmail.com");
		driver.findElementById("DetailFormphone_mobile-input").sendKeys("9988776655");
		
		//7) Select Lead Source as "Public Relations"
		WebElement eleLeadSource = driver.findElementById("DetailFormlead_source-input");
		wait.until(ExpectedConditions.elementToBeClickable(eleLeadSource));
		builder.click(eleLeadSource).build().perform();
		
		WebElement elePR = driver.findElementByXPath("//div[text()='Public Relations']");
		wait.until(ExpectedConditions.elementToBeClickable(elePR));
		builder.click(elePR).build().perform();
		
		//8) Select Business Roles as "Sales"
		WebElement eleBusinessRoles = driver.findElementById("DetailFormbusiness_role-input-label");
		wait.until(ExpectedConditions.elementToBeClickable(eleBusinessRoles));
		builder.click(eleBusinessRoles).build().perform();
		
		WebElement eleSales = driver.findElementByXPath("//div[text()='Sales']");
		wait.until(ExpectedConditions.elementToBeClickable(eleSales));
		builder.click(eleSales).build().perform();

		//9) Fill the Primary Address, City, State, Country and Postal Code and click Save

		driver.findElementById("DetailFormprimary_address_street-input").sendKeys("4053 Westfall Avenue");
		driver.findElementById("DetailFormalt_address_city-input").sendKeys("Detroit");
		driver.findElementById("DetailFormalt_address_state-input").sendKeys("Michigan"); 
		driver.findElementById("DetailFormalt_address_country-input").sendKeys("USA"); 
		driver.findElementById("DetailFormalt_address_postalcode-input").sendKeys("48300"); 
		Thread.sleep(2000);
		
		driver.findElementById("DetailForm_save2").click();
		Thread.sleep(3000);
		
		//10) Mouse over on Today's Activities and click Meetings
		WebElement eleActivities = driver.findElementByLinkText("Today's Activities");
		wait.until(ExpectedConditions.elementToBeClickable(eleActivities));
		builder.moveToElement(eleActivities).build().perform();
		Thread.sleep(1000);
		
		WebElement eleMeetings = driver.findElementByXPath("//div[(contains(text(),'Meetings'))]");
		wait.until(ExpectedConditions.elementToBeClickable(eleMeetings));
		builder.click(eleMeetings).build().perform();
		Thread.sleep(3000);
		
		//11) Click Create 
		WebElement eleCreate = driver.findElementByXPath("(//span[text()='Create'])[2]");
		wait.until(ExpectedConditions.elementToBeClickable(eleCreate));
		eleCreate.click();
		
		//12) Type Subject as "Project Status" , Status as "Planned" 
		driver.findElementById("DetailFormname-input").sendKeys("Project Status");
		Thread.sleep(2000);
		driver.findElementById("DetailFormstatus-input").click(); 
		Thread.sleep(3000);
		driver.findElementByXPath("(//div[text()='Planned'])[2]").click();
		
		//13) Start Date & Time as tomorrow 3 pm and Duration as 1hr
		driver.findElementById("DetailFormdate_start").click();
		WebElement eleTomorrow = driver.findElementByXPath("//div[@class='grid-cell number-cell text-right day inside current selected quiet responsive']/following::div[1]"); 
		wait.until(ExpectedConditions.elementToBeClickable(eleTomorrow));
		eleTomorrow.click();
		
		WebElement eleTime = driver.findElementByXPath("//div[@id='DetailFormdate_start-calendar-text']//input[@class='input-text']");
		wait.until(ExpectedConditions.visibilityOf(eleTime));
		eleTime.clear();
		eleTime.sendKeys("3:00pm", Keys.ENTER);
		
		driver.findElementById("DetailFormduration-time").clear();
		driver.findElementById("DetailFormduration-time").sendKeys("1hr",Keys.TAB);
		
		//14) Click Add paricipants, add your created Contact name and click Save
		driver.findElementByXPath("//span[text()=' Add Participants']").click();
		
		driver.findElementByXPath("//div[@id='app-search-text']//input[1]").sendKeys("Sample Fname Lname");
		Thread.sleep(3000);
		WebElement eleContact = driver.findElementByXPath("//div[@id='app-search-list']//div[(contains(text(),'Sample Fname Lname'))]");
		wait.until(ExpectedConditions.visibilityOf(eleContact));
		builder.click(eleContact).build().perform();
		Thread.sleep(3000);
		
		driver.findElement(By.id("DetailForm_save2-label")).click();
		Thread.sleep(2000);
		
		//15) Go to Sales and Marketing -> Contacts
		builder.moveToElement(eleSalesMarketing).build().perform();
		
		WebElement eleSMContact = driver.findElementByXPath("//div[text()='Contacts']");
		wait.until(ExpectedConditions.visibilityOf(eleSMContact));
		builder.click(eleSMContact).build().perform();
		Thread.sleep(2000);
		
		//16) search the lead Name and click the name from the result
		WebElement eleSearch = driver.findElementById("filter_text");
		eleSearch.sendKeys("Sample Fname Lname",Keys.ENTER);
		Thread.sleep(5000);
		
		//WebElement eleResult = driver.findElementByXPath("//a[contains(text(),'Sample Fname Lname')]");
		//builder.click(eleResult).build().perform();
		
		driver.findElementByXPath("//span[@class='detailLink']//a[1]").click();
		
		//17) Check weather the Meeting is assigned to the contact under Activities Section

		WebElement meetingRecord = driver.findElementByXPath("(//span[@id='subpanel-activities']/ancestor::div[@id='DetailForm-subpanels']//a[contains(text(),'Project Status')])[1]"); 

		if (meetingRecord.isDisplayed()) {
			System.out.println("Meeting is assigned for the Contact."); 
		} else { 
			System.out.println("Meeting is not available for the Contact.");
		}
		
		//18) Close the browser
		driver.close();

	}

}
