package Utility;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverSetup {

	public static WebDriver driver;
	public static Properties props;
	public static final String USERNAME = "divya_4SktZw";
	public static final String AUTOMATE_KEY = "CxRoMk2qF6YJK1bK4ryb";
	public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

	public DriverSetup() {
		try {
			props = new Properties();
			FileReader reader = new FileReader("src/main/java/TestData/TestData.properties");
			props.load(reader);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@BeforeClass
	@Parameters({ "browserName", "browserVersion" })
	public static void initializeDriver(String browserName, String browserVersion) {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("browserVersion", browserVersion);

		if (browserName.equals("Chrome")) {
			WebDriverManager.chromedriver().setup();
			caps.setCapability("browserName", "Chrome");
		} else if (browserName.equals("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			caps.setCapability("browserName", "Firefox");
		} else if (browserName.equals("Safari")) {
			WebDriverManager.safaridriver().setup();
			caps.setCapability("browserName", "Safari");
		}
		try {
			driver = new RemoteWebDriver(new URL(URL), caps);
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

}
