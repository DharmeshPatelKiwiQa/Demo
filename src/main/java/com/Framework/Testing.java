package com.Framework;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;

public class Testing {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		DesiredCapabilities caps = new DesiredCapabilities();
		System.setProperty("webdriver.chrome.driver", "Resource/chromedriver.exe");
		//caps.setCapability("webdriver.chrome.driver", "source/chromedriver222.exe");
		caps.setBrowserName("chrome");
		ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
		
		WebDriver driver = new ChromeDriver(caps);
		
		driver.get("https://app.falkonry.ai/#/account/Zs6dk03l3w2rp0/datastream");
		Thread.sleep(5000);
		
		clickOn(driver, ".//button/div[contains(text(),'Log in with Google')]");
		
		Thread.sleep(5000);
		type(driver,".//input[@id='identifierId']", "tarpan.patel@kiwiqa.com");
		Thread.sleep(5000);
		//log("Enter Google Email : <b>"+e+"</b>");
		clickOn(driver, ".//span[contains(text(),'Next')]");
		Thread.sleep(3000);
		//log("Clicked on 'Next' button.");
		//Common.pause(2);
		//type(driver,".//input[@id='signIn']", p);
		//log("Enter Google Password : <b>"+p+"</b>");
		type(driver,".//input[@type='password']", "Taps@107");
		Thread.sleep(5000);
		clickOn(driver, ".//span[contains(text(),'Next')]");
		
		(new WebDriverWait(driver, 50))
		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tr[@ng-repeat='datastream in datastreamList']")));
		Thread.sleep(5000);
		clickOn(driver, "//tr[@ng-repeat='datastream in datastreamList']");
		
		
		Thread.sleep(3000);
		(new WebDriverWait(driver, 50))
		  .until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//div[contains(@class,'pull-right')]/button[contains(text(),'Facts')]")));
		Thread.sleep(5000);
		clickOn(driver, "//button[text()='Facts']");
		//log("Clicked on 'Next' button.");
		Thread.sleep(5000);
		WebElement drop_zone = driver.findElement(By.xpath("//div[@class='drop-box p-t-20 ng-pristine ng-untouched ng-valid']"));
		 JavascriptExecutor jesz = (JavascriptExecutor)driver; 
		String zone = "arguments[0].class='drop-box p-t-20 ng-untouched ng-valid ng-dirty ng-valid-parse';";
		jesz.executeScript(zone,drop_zone);
		
		
		Thread.sleep(5000);
		WebElement upload = driver.findElement(By.xpath("//html/body"));
		String bodyele = upload.getAttribute("innerHTML");
		System.out.println("======================================================================");
		System.out.println(bodyele+"\n <input type=\"file\" ngf-drop=\"\" ngf-select=\"\" ng-model=\""
		  				+ "factFilterModel.verificationFiles\" ngf-drag-over-class="
		  						+ "\"dragover\" ngf-multiple=\"false\" ngf-allow-dir=\"false\" ng-disabled=\"factFilterModel.xhrUploadVerification || factFilterModel.xhrExtractFactFile\" tabindex=\"-1\" __ngf_gen__=\"true\" style=\"display: none;\">");
		System.out.println("======================================================================");
		
		JavascriptExecutor je = (JavascriptExecutor)driver;
		  String js = "document.getElementsByClassName('falkonry theme-blue aside-open aside-with-am-fade-and-slide-left aside-with-am-slide-left')[0].innerHTML='"
		  		+ bodyele+"\n <input type=\"file\" ngf-drop=\"\" ngf-select=\"\" ng-model=\""
		  				+ "factFilterModel.verificationFiles\" ngf-drag-over-class="
		  						+ "\"dragover\" ngf-multiple=\"false\" ngf-allow-dir=\"false\" ng-disabled=\"factFilterModel.xhrUploadVerification || factFilterModel.xhrExtractFactFile\" tabindex=\"-1\" __ngf_gen__=\"true\" style=\"display: none;\">'";
		  je.executeScript(js,upload);
	
		  //AppiumDriver<WebElement> ad = (AppiumDriver<WebElement>) driver;
		  ((RemoteWebDriver) driver).getKeyboard().pressKey(Keys.ALT);
		  
		  Thread.sleep(10000);
		  WebElement up = driver.findElement(By.xpath("//input[@type='file']"));
		  JavascriptExecutor jes = (JavascriptExecutor)driver;
		  String jse = "arguments[0].style.visibility='visible';";
		  jes.executeScript(jse,up);
		  Actions action = new Actions(driver);
		  //String filepath = TestData.uploadFilePath(value);
		  action.moveToElement(up).build().perform();
		  
		//Code to perform upload.
		  up.sendKeys("E:\\Projects\\Genix\\MuTex\\Sheets\\HAN sessions.csv");
		
		  System.out.println("Execution Completed.............");
	}
	
	
	public static void clickOn(WebDriver driver,  String Xpath)
	{
		driver.findElement(By.xpath(Xpath)).click();
		
	}
	
	public static void type(WebDriver driver,  String Xpath,String Input)
	{
		driver.findElement(By.xpath(Xpath)).sendKeys(Input);
		
	}

}
