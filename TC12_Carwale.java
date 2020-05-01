package day12.tc12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC12_Carwale {

	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		ChromeDriver driver = new ChromeDriver(options);
		WebDriverWait wait = new WebDriverWait(driver,30);
		
		//1) Go to https://www.carwale.com/
		driver.get("https://www.carwale.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);		
		
		//2) Click on Used
		driver.findElementByXPath("//li[@data-tabs='usedCars']").click();
		
		//3) Select the City as Chennai
		WebElement eleSearch = driver.findElementById("usedCarsList");
		wait.until(ExpectedConditions.elementToBeClickable(eleSearch)).sendKeys("Chennai");
		Thread.sleep(1000);
		eleSearch.sendKeys(Keys.ENTER);
		
		//4) Select budget min (8L) and max(12L) and Click Search
		Thread.sleep(500);
		WebElement eleMin = driver.findElementByXPath("//li[@data-min-price='8']");
		wait.until(ExpectedConditions.elementToBeClickable(eleMin)).click();
		
		Thread.sleep(500);
		WebElement eleMax = driver.findElementByXPath("//li[@data-max-price='12']");
		wait.until(ExpectedConditions.elementToBeClickable(eleMax)).click();
		
		Thread.sleep(1000);
		driver.findElementById("btnFindCar").click();
		
		//Handling pop-up
		WebElement eleQuery = driver.findElementById("placesQuery");
		wait.until(ExpectedConditions.visibilityOf(eleQuery)).sendKeys("Chennai");
		Thread.sleep(500);
		driver.findElementByXPath("//a[@cityname='chennai,tamilnadu']").click();
		Thread.sleep(3000);
		
		//5) Select Cars with Photos under Only Show Cars With
		WebElement elePhotos = driver.findElementByXPath("//span[text()='Cars with Photos']");
		wait.until(ExpectedConditions.elementToBeClickable(elePhotos)).click();
		Thread.sleep(3000);
		
		//6) Select Manufacturer as "Hyundai" --> Creta
		WebElement eleHyundai = driver.findElementByXPath("//span[text()=' Hyundai ']");
		wait.until(ExpectedConditions.elementToBeClickable(eleHyundai));
		JavascriptExecutor js =(JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();",eleHyundai);
		
		WebElement eleCreta = driver.findElementByXPath("//span[text()='Creta']");
		wait.until(ExpectedConditions.visibilityOf(eleCreta));
		js.executeScript("arguments[0].click();",eleCreta);
		Thread.sleep(2000);
		
		//7) Select Fuel Type as Petrol
		js.executeScript("arguments[0].click();", driver.findElementByXPath("//h3[contains(text(),'Fuel Type')]"));
		
		WebElement elePetrol = driver.findElementByXPath("(//span[text()='Petrol'])[1]");
		wait.until(ExpectedConditions.visibilityOf(elePetrol));
		js.executeScript("arguments[0].click();",elePetrol);
		
		//8) Select Best Match as "KM: Low to High"
		WebElement eleSort = driver.findElementById("sort");
		Select sort = new Select(eleSort);
		wait.until(ExpectedConditions.elementToBeClickable(eleSort));
		sort.selectByVisibleText("KM: Low to High");
		Thread.sleep(1000);
		
		//9) Validate the Cars are listed with KMs Low to High
		List<WebElement> eleKM = driver.findElementsByXPath("//span[@class='slkms vehicle-data__item']");
		List<Integer> ListIntkm = new ArrayList<Integer>();
		System.out.println("Kilometers before sort...");
		for (int i = 0; i < eleKM.size(); i++) {
			String strKM = eleKM.get(i).getText();
			System.out.println(strKM); 
			int intKM = Integer.parseInt(strKM.replaceAll("\\D", ""));
			ListIntkm.add(intKM);
		}
		
		//Copy List ListIntkm to another List ListSortKm
		List<Integer> ListSortKm = new ArrayList<Integer>(ListIntkm);
		Collections.sort(ListSortKm);
		System.out.println("Kilometers after sort...");
		for (Integer eachKM : ListSortKm) {
			System.out.println(eachKM+" km");
		}
		
		if(ListIntkm.equals(ListSortKm))
			System.out.println("The cars are listed with KMs Low to High");			
		else
			System.out.println("The cars are not listed with KMs Low to High");		
			
		//10) Add the least KM ran car to Wishlist
		Integer leastKM = ListSortKm.get(0);
		System.out.println("Least KM utilised by Hyundai Creta car: "+leastKM);
		Thread.sleep(1000);
		//WebElement eleWish = driver.findElementByXPath("(//span[@class='shortlist-icon--inactive shortlist'])[1]");
		for (int i = 0; i < ListIntkm.size(); i++) {
			if(ListIntkm.get(i).equals(ListSortKm.get(0))) {
				WebElement eleWish = driver.findElementByXPath("(//span[@class='shortlist-icon--inactive shortlist'])["+(i+1)+"]");
				js.executeScript("arguments[0].click();", eleWish);
				Thread.sleep(1000);
				break;
			}

		}
		
		//11) Go to Wishlist and Click on More Details
		WebElement eleCompare = driver.findElementByXPath("//li[@data-action='ShortList&CompareWindow_Click']");
		wait.until(ExpectedConditions.visibilityOf(eleCompare)).click();
		Thread.sleep(500);
		
		WebElement eleMore = driver.findElementByXPath("//a[contains(text(),'More details')]");
		wait.until(ExpectedConditions.visibilityOf(eleMore)).click();
		
		Set<String> setWin = driver.getWindowHandles();
		List<String> listWin = new ArrayList<String>(setWin);
		driver.switchTo().window(listWin.get(1));
		
		//12) Print all the details under Overview 
		List<WebElement> eleDesc = driver.findElementsByXPath("//div[@id='overview']//div[@class='equal-width text-light-grey']");
		List<WebElement> eleValues = driver.findElementsByXPath("//div[@id='overview']//div[@class='equal-width dark-text']");
		Map<String,String> map = new LinkedHashMap<String,String>();
		
		for (int i = 0; i < eleDesc.size(); i++) {
			String desc = eleDesc.get(i).getText();
			String value = eleValues.get(i).getText();
			map.put(desc, value);
		}
		
		for (Entry<String,String> eachEntry : map.entrySet()) {
			System.out.print(eachEntry.getKey()+"--->"+eachEntry.getValue());
			System.out.println();
		}
		
		//13) Close all browsers.
		driver.quit();
	}

}
