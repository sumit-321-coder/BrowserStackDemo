package Utility;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BasicMethods extends DriverSetup {

	String EnglishHeaderTitle;
	String SpanishHeaderTitle;
	String CombinedTitle = "";
	Path imageDirectory = Paths.get("src/main/java/Images");
	String articleXpath = props.getProperty("artcileXpath");
	String headerXpath = props.getProperty("headerXpath");
	String contentXpath = props.getProperty("contentXpath");
	String imgXpath = props.getProperty("imgXpath");
	int imgcount = 1;

	public void navigateToUrl(String URL) {
		driver.get(URL);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public void clickElement(WebElement element) {
		element.click();
	}

	public WebElement findElement(String xpath) {
		return driver.findElement(By.xpath(xpath));
	}

	public List<WebElement> findElements(String xpath) {
		return driver.findElements(By.xpath(xpath));
	}

	public String getElementText(WebElement element) {
		return element.getText();
	}

	public String getElementAttribute(WebElement element, String Attribute) {
		return element.getDomAttribute(Attribute);
	}

	public void getFirstFiveArticle() throws InterruptedException, MalformedURLException {
		List<WebElement> articlelist = findElements(articleXpath);
		ArrayList<String> hreflink = new ArrayList();

		for (int i = 0; i < 5; i++) {
			hreflink.add(getElementAttribute(articlelist.get(i), "href"));
		}
		int imageCount = 1;
		for (String link : hreflink) {
			navigateToUrl(link);
			try {
				SpanishHeaderTitle = getElementText(findElement(headerXpath)); // printing title
				System.out.println("Title - " + SpanishHeaderTitle);
				String Content = getElementText(findElement(contentXpath)); // printing content
				System.out.println("Content - " + Content);

			} catch (NoSuchElementException Exception) {

			}
			try {
				saveImage(); // saving image
			} catch (IOException e) {

				e.printStackTrace();
			}
			EnglishHeaderTitle = textTranslate(SpanishHeaderTitle);
			CombinedTitle = EnglishHeaderTitle + " " + CombinedTitle;
		}
		System.out.println("Combined Title - " + CombinedTitle);
		countRepeatingWords(CombinedTitle);
	}

	public void saveImage() throws IOException {
		String SrcUrl = getElementAttribute(findElement(imgXpath), "src");

		URL imageUrl = new URL(SrcUrl);
		Path outputPath = imageDirectory.resolve("image_" + imgcount + ".jpg");
		InputStream in;
		in = imageUrl.openStream();
		FileOutputStream out;
		out = new FileOutputStream(outputPath.toFile());
		byte[] buffer = new byte[4096];
		int bytesRead;
		while ((bytesRead = in.read(buffer)) != -1) {
			out.write(buffer, 0, bytesRead);

		}

		System.out.println("Downloaded image " + imgcount + " to " + outputPath.toString());
		imgcount++;

	}

	public static String textTranslate(String SpanishText) {
		RestAssured.baseURI = "https://google-translate113.p.rapidapi.com";
		RequestSpecification request = RestAssured.given();
		JSONObject requestParams = new JSONObject();
		request.header("Content-Type", "application/json");
		request.header("x-rapidapi-key", "92d07edec1msh232fa74a4cb856dp159ed9jsn0aea49fde5cb");
		request.header("x-rapidapi-host", "google-translate113.p.rapidapi.com");
		requestParams.put("from", "es");
		requestParams.put("to", "en");
		requestParams.put("text", SpanishText);
		request.body(requestParams.toJSONString());
		Response response = request.post("/api/v1/translator/text");
		JsonPath jsonPathEvaluator = response.jsonPath();
		String translation = jsonPathEvaluator.get("trans");
		System.out.println("Header in English - " + translation);
		return translation;
	}

	public static void countRepeatingWords(String headertext) {
		Map<String, Integer> hashMap = new HashMap<>();
		String delims = "\\W+";
		String[] word = headertext.toLowerCase().split(delims);
		for (String eachword : word) {
			int count = 0;
			for (int i = 0; i < word.length; i++) {
				if (word[i].equalsIgnoreCase(eachword)) {
					++count;
				}
			}
			if (count > 2) {
				hashMap.put(eachword, count);
			}
		}
		System.out.println("repeated word more than twice is - " + hashMap);

	}

	public void closeDriver() {
		driver.quit();
	}
}
