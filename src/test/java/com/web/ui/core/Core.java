package com.web.ui.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.paulhammant.ngwebdriver.NgWebDriver;
import com.saucelabs.saucerest.SauceREST;

import cucumber.api.Scenario;
import io.github.bonigarcia.wdm.OperatingSystem;
import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.Screenshot;
public class Core {
	
	private static ThreadLocal<WebDriver> localDriver = new ThreadLocal<WebDriver>();
//	private static ThreadLocal<> androidDriver = new ThreadLocal<WebDriver>();
	
	private static ThreadLocal<NgWebDriver> ngDriver = new ThreadLocal<NgWebDriver>();
	private static ThreadLocal<WebDriverWait> wait = new ThreadLocal<WebDriverWait>();
	//Execution environment related
	private static ThreadLocal<String> EXECUTION_MACHINE = new ThreadLocal<String>();
	private static ThreadLocal<String> GRID_LOCATION = new ThreadLocal<String>();
	private static ThreadLocal<String> BROWSER = new ThreadLocal<String>();
	private static ThreadLocal<String> BROWSER_VERSION = new ThreadLocal<String>();
	private static ThreadLocal<String> OS = new ThreadLocal<String>();	
	private static ThreadLocal<Boolean> IsUItc = new ThreadLocal<Boolean>();
	
	//Application Specific
	private static ThreadLocal<String> username = new ThreadLocal<String>();
	private static ThreadLocal<String> password = new ThreadLocal<String>();
	private static ThreadLocal<String> testUrl = new ThreadLocal<String>();
	private static ThreadLocal<String> APIendpoint = new ThreadLocal<String>();
	private static ThreadLocal<Scenario> scnr = new ThreadLocal<Scenario>();
	
	Screenshot screenShoot;
	DesiredCapabilities capabilities;
	public static String SAUCE_USERNAME;
	public static String SAUCE_ACCESS_KEY;
	SauceREST sauceRest;
	
	public Core() throws Exception {
		if (getLocalDriver() == null) {
			System.err.println("Local webdriver not null, Hence initializing....");
			LoadDomainConfig.setEnv();
			if (getEXECUTION_MACHINE().toLowerCase().equals("local")) 
				setForLocal(); 
			else setForGrid();
			initializeWait(new WebDriverWait(getLocalDriver(), 30));
			setIsUItc(true);
//			localDriver.set(getlocal);
		}
	}

	private void setForLocal() throws MalformedURLException {
		if (getBROWSER().toLowerCase().equals("chrome"))setForChrome();
		else if (getBROWSER().toLowerCase().equals("firefox"))setForFirefox();
		else if (getBROWSER().toLowerCase().equals("ie") || getBROWSER().toLowerCase().contains("iexplore")
				|| getBROWSER().toLowerCase().equals("internetexplorer"))setForIE();
		else if (getBROWSER().toLowerCase().equals("apk"))setForApk();
	}
	
	private void setForGrid() throws MalformedURLException {
		capabilities = new DesiredCapabilities();
		if (getBROWSER().toLowerCase().equals("ie") || getBROWSER().toLowerCase().contains("explore")) {
			setBROWSER("internet explorer");
			// Traditional way to initialize
			WebDriverManager.iedriver().operatingSystem(OperatingSystem.WIN).version("3.12").setup();
			capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
			capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
//			 //Below is good on Grid
//			 capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,true);
		} else if (getBROWSER().toLowerCase().equals("firefox")) {
//			setForFirefox();
			// This is for grid, check before use if this is working for you
			WebDriverManager.firefoxdriver().version("0.20.1").setup();
			capabilities = DesiredCapabilities.firefox();
			 capabilities.setCapability("marionette", false);
		} else if (getBROWSER().toLowerCase().equals("chrome")) {
//			setForChrome();
			WebDriverManager.chromedriver().version("80.0.3987.16").setup();
			capabilities = DesiredCapabilities.chrome();
		}
//		capabilities.setCapability(CapabilityType.PLATFORM_NAME, "WINDOWS");
//		capabilities.setCapability(CapabilityType.BROWSER_NAME, getBROWSER());
//		capabilities.setCapability(CapabilityType.BROWSER_VERSION, getBROWSER_VERSION());
		
//		driver = new RemoteWebDriver(new URL(getGRID_LOCATION()+"/wd/hub"), 
//				capabilities);
//				new FirefoxOptions(capabilities));
//				new ChromeOptions());
		setLocalDriver(new RemoteWebDriver(new URL(getGRID_LOCATION()+"/wd/hub"), 
				capabilities));
	}
	
	private void setForSaucelabs() throws Exception{
		// TODO Auto-generated method stub 
		capabilities = new DesiredCapabilities();
		String sauceConfig = System.getenv("SAUCE_ONDEMAND_BROWSERS").replace("[", "").replace("]", "");
		System.out.println(System.getenv("SAUCE_ONDEMAND_BROWSERS"));
		JSONParser parser = new JSONParser();
		JSONObject js = (JSONObject) parser.parse(sauceConfig);
		setBROWSER(js.get("browser").toString());
		setBROWSER_VERSION(js.get("browser-version").toString());
		setOS(js.get("platform").toString());
//		SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
//		SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
		
		sauceRest = new SauceREST(SAUCE_USERNAME, SAUCE_ACCESS_KEY);
		String tunnels = sauceRest.getTunnels();
		System.out.println("TUNNEL INFO: " + tunnels.toString());
		String jsonResponse = sauceRest.getConcurrency();
		org.json.JSONObject job = new org.json.JSONObject(jsonResponse);
		int sauceMaxConcurrent = Integer.parseInt(job.getJSONObject("concurrency").getJSONObject(SAUCE_USERNAME)
				.getJSONObject("remaining").get("overall").toString());
		System.out.println(">>>>>>>>>>>>>>>>>SAUCE CONCURRENT SESSIONS ALLOWED: " + job.getJSONObject("concurrency")
				.getJSONObject(SAUCE_USERNAME).getJSONObject("remaining").get("overall").toString());
		
        if (getBROWSER() != null) capabilities.setCapability(CapabilityType.BROWSER_NAME, getBROWSER());
        if (getBROWSER_VERSION() != null) capabilities.setCapability(CapabilityType.VERSION, getBROWSER_VERSION());
        capabilities.setCapability(CapabilityType.PLATFORM, getOS());		
		
        /*****This sets the timeout if no input******/
//		capabilities.setCapability("maxDuration", 10800);
//      capabilities.setCapability("commandTimeout", 300);
//      capabilities.setCapability("idleTimeout", 300);
        
        /******Way to increase performance***********/
        capabilities.setCapability("recordVideo", false);
        capabilities.setCapability("videoUploadOnPass", false);
        capabilities.setCapability("recordScreenshots", false);
        capabilities.setCapability("recordLogs", false);
        try {
//			driver = new RemoteWebDriver(new URL("http://" + SAUCE_USERNAME + ":" + SAUCE_ACCESS_KEY 
//					+ "@ondemand.saucelabs.com:80/wd/hub"), capabilities);
        	setLocalDriver(new RemoteWebDriver(new URL("http://" + SAUCE_USERNAME + ":" + SAUCE_ACCESS_KEY 
					+ "@ondemand.saucelabs.com:80/wd/hub"), capabilities));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error with Sauce Labs setup");
			e.printStackTrace();	
		}	
	}

	private void setForChrome() throws MalformedURLException {
		WebDriverManager.chromedriver().version("80.0.3987.16").setup();
		capabilities = DesiredCapabilities.chrome();
//		driver = new ChromeDriver(capabilities);
		setLocalDriver(new ChromeDriver(capabilities));
	}
	
	private void setForApk() throws MalformedURLException {
		capabilities = new DesiredCapabilities();
		capabilities.setCapability("appPackage", "com.move.realtor");
//		capabilities.setCapability("appActivity", "com.move.realtor.onboarding.OnBoardingActivity");
		capabilities.setCapability("appActivity", "com.move.realtor.splash.SplashActivity");
		capabilities.setCapability("deviceName", "emulator-555");
		capabilities.setPlatform(Platform.ANDROID);
		setLocalDriver(new RemoteWebDriver(new URL("http://localhost:4723/wd/hub"), capabilities));
	}

	private void setForFirefox() throws MalformedURLException {	
		WebDriverManager.firefoxdriver()//.version("0.26.0")
		.setup();
		FirefoxOptions options = new FirefoxOptions();
		options.setCapability("marionette",true);
//		capabilities = DesiredCapabilities.firefox();
//		capabilities.setCapability("marionette", false);
//		driver = new FirefoxDriver(capabilities);
		setLocalDriver(new FirefoxDriver(options));
	}

	private void setForIE() throws MalformedURLException {
//		System.setProperty("webdriver.ie.driver", "C:\\IEDriver\\msedgedriver.exe");
		WebDriverManager.edgedriver().setup();//.arch32().version("3.141.59.2").setup();
//		capabilities = DesiredCapabilities.internetExplorer();
//		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
//		capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
//		capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
//		capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
//		capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
//		driver = new InternetExplorerDriver(capabilities);
//		setLocalDriver(new InternetExplorerDriver(capabilities));
//		setLocalDriver(new EdgeDriver(capabilities));
		
		EdgeOptions options = new EdgeOptions();
//		option.setCapability(.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
//		option.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
//		option.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
//		option.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
//		option.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
		options.setPageLoadStrategy("eager");
		setLocalDriver(new EdgeDriver(options));
	}

	public static void closeAlreadyExistingBrowserExeTraces() {
		try {
			while (ProcessRunning("geckodriver.exe")) {
				killProcess("geckodriver.exe");
			}
			while (ProcessRunning("IEDriverServer.exe")) {
				killProcess("IEDriverServer.exe");
			}
			while (ProcessRunning("chromedriver.exe")) {
				killProcess("chromedriver.exe");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.err.println("Error closing browser backend Exes..!!!");
		}

	}
	
	private static void killProcess(String serviceName) throws Exception {
		Runtime.getRuntime().exec("taskkill /F /IM " + serviceName);
	}

	private static boolean ProcessRunning(String serviceName) throws Exception {
		try {
			Process p = Runtime.getRuntime().exec("tasklist");
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				// System.out.println(line);
				if (line.contains(serviceName)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			System.err.println("May be we are on linux system");
			return false;
		}
	}

	public static Scenario getScnr() {
		return scnr.get();
	}

	public static void setScnr(Scenario scr) {
		scnr = new ThreadLocal<Scenario>();
		scnr.set(scr);
	}
	
	public static String getUrl() {
		return testUrl.get();
	}

	public static void setUrl(String url) {
		testUrl = new ThreadLocal<String>();
		testUrl.set(url);
	}
	
	public static String getUsername() {
		return username.get();
	}

	public static void setUsername(String userName) {
		username.set(userName);
	}
	
	public static String getPassword() {
		return password.get();
	}

	public static void setPassword(String passWord) {
		password.set(passWord);
	}
	
	public static String getAPIendpoint() {
		return APIendpoint.get();
	}

	public static void setAPIendpoint(String passWord) {
		APIendpoint.set(passWord);
	}
	
	public static String getEXECUTION_MACHINE() {
		return EXECUTION_MACHINE.get();
	}

	public static void setEXECUTION_MACHINE(String executionMachine) {
		EXECUTION_MACHINE.set(executionMachine);
	}
	
	public static String getGRID_LOCATION() {
		return GRID_LOCATION.get();
	}

	public static void setGRID_LOCATION(String gridLocation) {
		GRID_LOCATION.set(gridLocation);
	}
	
	public static String getBROWSER() {
		return BROWSER.get();
	}

	public static void setBROWSER(String browser) {
		BROWSER.set(browser);
	}
	
	public static String getBROWSER_VERSION() {
		return BROWSER_VERSION.get();
	}

	public static void setBROWSER_VERSION(String browserVersion) {
		BROWSER_VERSION.set(browserVersion);
	}
	
	public static String getOS() {
		return OS.get();
	}

	public static void setOS(String os) {
		OS.set(os);
	}
	
	public static Boolean getIsUItc() {
		return IsUItc.get();
	}

	public static void setIsUItc(Boolean val) {
		IsUItc.set(val);
	}
	
	public static WebDriver getLocalDriver() {
		return localDriver.get();
	}
	
	public static NgWebDriver getAngularDriver() {
		return new NgWebDriver((JavascriptExecutor) localDriver.get());
	}
	
	public static void setLocalDriver(WebDriver driver) {
		localDriver.set(driver);
	}
	
	public static WebDriverWait getWait() {
		return wait.get();
	}
	
	public static void initializeWait(WebDriverWait driverWait) {
		wait.set(driverWait);
	}
	
	public static void cleanDriverSession() {
//		getLocalDriver().close();
		getLocalDriver().quit();
		System.err.println("********************** Current thread webdriver cleaned **************************");
	}
}
