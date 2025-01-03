package BrowserStack.Demo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import Utility.BasicMethods;
import Utility.DriverSetup;
import io.github.bonigarcia.wdm.WebDriverManager;

public class demoTest extends BasicMethods {

	public demoTest() {
		super();
	}

	String baseURL = props.getProperty("baseURL");
	String AcceptCookiesXpath = props.getProperty("acceptCookiesXpath");
	String opinionXpath = props.getProperty("opinionXpath");

	
	@Test
	public void navigatetoBaseUrl() {
		navigateToUrl(baseURL);
		clickElement(findElement(AcceptCookiesXpath));
	}

	@Test
	public void navigatetoOpinionSection() {
		clickElement(findElement(opinionXpath));
	}

	@Test
	public void scrapeArticle() throws MalformedURLException, InterruptedException {
		getFirstFiveArticle();
	}

	@AfterSuite
	public void teardown() {
		closeDriver();
	}
}
