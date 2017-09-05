package com.Framework.Init;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.IResultMap;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.internal.Utils;
import com.Framework.Utility.TestData;

public class SeleniumInit {
	public String suiteName = "";
	public String testName = "";
	/* Minimum requirement for test configur ation */
	public String testUrl;
	protected String seleniumHub; // Selenium hub IP
	protected String seleniumHubPort; // Selenium hub port
	protected String targetBrowser; // Target browser
	protected static String test_data_folder_path = null;
	public static String currentWindowHandle = "";// Get Current Window handle
	public static String browserName = "";
	public static String osName = "";
	public String HomeDir="";
	public static String browserVersion = "";
	public static String browseruse = "";
	public static String Testenvironment="";
	public static String Url="";
	/*protected NCFIndexPage ncfIndexPage;*/

	protected static String screenshot_folder_path = null;
	public static String currentTest; // current running test
	protected static Logger logger = Logger.getLogger("testing");
	protected WebDriver driver;

	
	@BeforeTest(alwaysRun = true)
	public void fetchSuiteConfiguration(ITestContext testContext) throws IOException {
		seleniumHub = testContext.getCurrentXmlTest().getParameter("selenium.host");
		seleniumHubPort = testContext.getCurrentXmlTest().getParameter("selenium.port");
		
		Testenvironment=TestData.getValueFromConfig("config.properties","TestEnvironment");
		Url=TestData.getValueFromConfig("config.properties","URL");
		System.out.println("------------URL :"+Url);
		if(Testenvironment.equalsIgnoreCase("Live"))
		{
			testUrl=Url;
		
		}
		else
		{
			testUrl=Testenvironment+"."+Url;
			
		}
		
	}
	/**
	 * WebDriver initialization
	 * @return WebDriver object
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method, ITestContext testContext) throws IOException, InterruptedException {
		String path="";
		targetBrowser =TestData.getValueFromConfig("config.properties","Browser");
		browseruse = targetBrowser;
		currentTest = method.getName(); // get Name of current test.
		URL remote_grid = new URL("http://" + seleniumHub + ":" + seleniumHubPort + "/wd/hub");
		String SCREENSHOT_FOLDER_NAME = "screenshots";
		String TESTDATA_FOLDER_NAME = "test_data";
		test_data_folder_path = new File(TESTDATA_FOLDER_NAME).getAbsolutePath();
		screenshot_folder_path = new File(SCREENSHOT_FOLDER_NAME).getAbsolutePath();
		DesiredCapabilities capability = null;		
		if (targetBrowser == null || targetBrowser.contains("firefox") || targetBrowser.equalsIgnoreCase("firefox")) {
			FirefoxProfile profile = new FirefoxProfile();
			path = "C:\\Users\\KQSPL_R\\Downloads";
			profile.setPreference("dom.max_chrome_script_run_time", "999");
			profile.setPreference("dom.max_script_run_time", "999");
			profile.setPreference("browser.download.folderList", 2);
			profile.setPreference("browser.download.dir", path);
			profile.setPreference("browser.helperApps.neverAsk.openFile",
					"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
					"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
			profile.setPreference("browser.download.manager.showWhenStarting", false);
			profile.setPreference("browser.download,manager.focusWhenStarting", false);
			profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
			profile.setPreference("browser.download.manager.closeWhenDone", false);
			profile.setPreference("browser.download.manager.showAlertOnComplete", false);
			profile.setPreference("browser.download.manager.useWindow", false);
			profile.setPreference("browser.download.manager.showWhenStarting", false);
			profile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
			profile.setPreference("pdfjs.disabled", true);
			profile.setAcceptUntrustedCertificates(true);
			profile.setPreference("security.OCSP.enabled", 0);
			profile.setEnableNativeEvents(false);
			profile.setPreference("network.http.use-cache", false);
			capability = DesiredCapabilities.firefox();
			capability.setJavascriptEnabled(true);
			capability.setCapability(FirefoxDriver.PROFILE, profile);
			osName = System.getProperty("os.name");
			HomeDir=System.getProperty("user.home");
			System.out.println("---------OS NAme--------------+"+osName);
			System.out.println("---------OHomeDir--------------+"+HomeDir);
			driver = new RemoteWebDriver(remote_grid, capability);
		} else if (targetBrowser.contains("ie8") || targetBrowser.equalsIgnoreCase("IE")) {
			capability = DesiredCapabilities.internetExplorer();
			capability.setPlatform(Platform.ANY);
			capability.setBrowserName("internet explorer");
			capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			// capability.setVersion("8.0");
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capability.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			capability.setJavascriptEnabled(true);
			osName = capability.getPlatform().name();
			driver = new RemoteWebDriver(remote_grid, capability);
		} else if (targetBrowser.contains("ie9")) {
			capability = DesiredCapabilities.internetExplorer();
			capability.setBrowserName("internet explorer");
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capability.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			capability.setJavascriptEnabled(true);
			osName = capability.getPlatform().name();
		} else if (targetBrowser.contains("ie11")) {
			capability = DesiredCapabilities.internetExplorer();
			System.setProperty("webdriver.ie.driver", "/Resource/IEDriverServer_Win32_2.28.0.exe");
			capability.setBrowserName("internet explorer");
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capability.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			capability.setJavascriptEnabled(true);
			osName = capability.getPlatform().name();
			driver = new RemoteWebDriver(remote_grid, capability);
		} else if (targetBrowser.contains("chrome") || targetBrowser.equalsIgnoreCase("chrome")) {
			capability = DesiredCapabilities.chrome();
			File driverpath = new File("Resource/chromedriver");
			String path1 = driverpath.getAbsolutePath();
			System.out.println("+++++++++Chrome driver no path che :" +path1);
			 try {
			        Runtime.getRuntime().exec("sudo chmod +X /root/src/github.com/DharmeshPatelKiwiQa/Demo/Resource/chromedriver") ;           
			    } catch (IOException ex) {
			    }
			System.setProperty("webdriver.chrome.driver", path1);
			capability.setBrowserName("chrome");
			capability.setJavascriptEnabled(true);
			capability.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
			osName = capability.getPlatform().name();
			driver = new RemoteWebDriver(remote_grid, capability);
			// driver= new ChromeDriver();
		} else if (targetBrowser.contains("safari")) {
			capability = DesiredCapabilities.safari();
			capability.setJavascriptEnabled(true);
			capability.setBrowserName("safari");
			driver = new SafariDriver(capability);
		}
		suiteName = testContext.getSuite().getName();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	/*	Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		browserName = caps.getBrowserName();
		browserVersion = caps.getVersion();*/
		System.out.println("---------TestURL :"+testUrl);
		driver.get(testUrl);
		driver.manage().window().maximize();
		currentWindowHandle = driver.getWindowHandle();
		suiteName = testContext.getSuite().getName();
	/*	ncfIndexPage = new NCFIndexPage(driver);
		ncfverification = new NCFVerification(driver);
		secureAccountIndexPage = new SecureAccountIndexPage(driver);
		secureAccountVerification = new SecureAccountVerification(driver);
		completeProfileVerification = new CompleteProfileVerification(driver);
		completeProfileIndexPage = new CompleteProfileIndexPage(driver);
		userDashboardIndexPage = new UserDashboardIndexPage(driver);
		userDashboardVerification = new UserDashboardVerification(driver);
		eCFIndexPage = new ECFIndexPage(driver);
		eCFVerification = new ECFVerification(driver);
		kORNCFIndexPage = new KORNCFIndexPage(driver);
		kORNCFVerification = new KORNCFVerification(driver);
		jpnncfIndexPage = new JPNNCFIndexPage(driver);
		jpnncfVerification = new JPNNCFVerification(driver);
		 Whiteboard 
		createEnquiryIndexPage = new CreateEnquiryIndexPage(driver);
		createEnquiryVerification = new CreateEnquiryVerification(driver);
		whiteboardDashboardIndexPage = new WhiteboardDashboardIndexPage(driver);
		whiteboardDashboardVerification = new WhiteboardDashboardVerification(driver);
		enquiryViewIndexPage = new EnquiryViewIndexPage(driver);
		enquiryViewVerification = new EnquiryViewVerification(driver);
		chnncfIndexPage = new CHNNCFIndexPage(driver);
		chnncfVerification = new CHNNCFVerification(driver);
		brancfIndexPage = new BRANCFIndexPage(driver);
		  brancfVerifcation = new BRANCFVerifcation(driver);
		  App Editgae
		  appEditageIndexPage = new AppEditageIndexPage(driver);
		appEditageVerification= new AppEditageVerification(driver);*/
	}
	/**
	 * Log For Failure Test Exception.
	 * 
	 * @param tests
	 */
	private void getShortException(IResultMap tests) {
		for (ITestResult result : tests.getAllResults()) {
			Throwable exception = result.getThrowable();
			List<String> msgs = Reporter.getOutput(result);
			boolean hasReporterOutput = msgs.size() > 0;
			boolean hasThrowable = exception != null;
			if (hasThrowable) {
				boolean wantsMinimalOutput = result.getStatus() == ITestResult.SUCCESS;
				if (hasReporterOutput) {
					log("<h3>" + (wantsMinimalOutput ? "Expected Exception" : "Failure Reason:") + "</h3>");
				}
				String str = Utils.stackTrace(exception, true)[0];
				Scanner scanner = new Scanner(str);
				String firstLine = scanner.nextLine();
				log(firstLine);
			}
		}
	}
	/**
	 * After Method
	 * 
	 * @param testResult
	 */
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult testResult) {
		ITestContext ex = testResult.getTestContext();
		try {
			testName = testResult.getName();
			if (!testResult.isSuccess()) {
				/* Print test result to Jenkins Console */
				System.out.println();
				System.out.println("TEST FAILED - " + testName);
				System.out.println();
				System.out.println("ERROR MESSAGE: " + testResult.getThrowable());
				System.out.println("\n");
				Reporter.setCurrentTestResult(testResult);
				/* Make a screenshot for test that failed */
				String screenshotName = Common.getCurrentTimeStampString() + testName;
				Reporter.log("<br> <b>Please look to the screenshot - </b>");
				Common.makeScreenshot(driver, screenshotName);
				getShortException(ex.getFailedTests());
			} else {
				try {
					Common.pause(5);
					Common.pause(5);
				} catch (Exception e) {
					log("<br></br> Not able to perform logout");
				}
			}
			driver.manage().deleteAllCookies();
			driver.close();
			driver.quit();
		} catch (Throwable throwable) {
			System.out.println("message from tear down" + throwable.getMessage());
		}
	}
	/**
	 * Log given message to Reporter output.
	 * 
	 * @param msg
	 *            Message/Log to be reported.
	 */
	@AfterSuite(alwaysRun = true)
	public void postConfigue(ITestContext testContext) throws IOException {
	}
	public static void log(String msg) {
		Reporter.log(msg + "</br>");
	}
	public static void logStatus(final int test_status) {
		switch (test_status) {
		case ITestStatus.PASSED:
			log("<font color=#009900><b>--Passed</b></font></br>");
			break;
		case ITestStatus.FAILED:
			log("<font color=#FF0000><b>--Failed</b></font></br>");
			break;
		case ITestStatus.SKIPPED:
			log("<font color=#FFFF00><b>--Skipped</b></font></br>");
			break;
		default:
			break;
		}
	}
	public void MakeScreenshots()
	{
		String screenshotName = Common.getCurrentTimeStampString() + testName;
		Common.makeScreenshot2(driver, screenshotName);
	}
}