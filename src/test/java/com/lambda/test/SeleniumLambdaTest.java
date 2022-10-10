package com.lambda.test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class SeleniumLambdaTest {
	protected RemoteWebDriver driver = null;
	protected String Status = "passed";
	protected Actions act;
	protected SoftAssert soft;
	protected Select select;
	protected WebDriverWait wait;
	protected JavascriptExecutor js;
	
	// locators using By class
	By intgrateLink = By.xpath("//a[contains(text(), 'All Integrations')]");
	By codelessHeader = By.xpath("//h2[text()='Codeless Automation']");
	By testingWizeLink = By.xpath("//a[contains(text(), 'Integrate Testing')]");

	@BeforeMethod
	@Parameters(value = { "browser", "version", "platform" })
	public void setup(Method m, ITestContext ctx, String browser, String version, String platform)
			throws MalformedURLException {
		Reporter.log("<===== Start of Test Method =====>");
			String username = "wawaresanjay1";
			String accesskey = "lc2MNq8U6nqrMn4govcSLAkUKIaRJvhAVllyRkpDZpoVKz8kY0";			
			String hub = "@hub.lambdatest.com/wd/hub";
		Reporter.log("Setting desired Capabilities", true);

		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("browserName", browser);
		caps.setCapability("version", version);
		caps.setCapability("platform", platform);
		caps.setCapability("network", true); // To enable network logs
		caps.setCapability("visual", true); // To enable step by step screenshot
		caps.setCapability("video", true); // To enable video recording
		caps.setCapability("console", true); // To capture console logs
		caps.setCapability("build", "advanced-Selenium-LambdaTest");
		caps.setCapability("name", m.getName() + this.getClass().getName());
		caps.setCapability("plugin", "git-testng");

		Reporter.log("Connecting to hub/ Grid", true);
		try {
			driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + hub), caps);
		} catch (MalformedURLException e) {
			System.out.println("Invalid URL");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Parameters("url")
	@Test(testName = "Advanced Selenium Lambda Test ")
	public void AdvancedSeleniumTestValidation(String url) throws InterruptedException {

		Reporter.log("Step1: Navigate to https://www.lambdatest.com/", true);
		try {
			driver.get(url);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Reporter.log("Step2.Perform an Explicitly wait till the time all the elements in the DOM are available.", true);
		wait = new WebDriverWait(driver, Duration.ofSeconds(40), Duration.ofMillis(500));

		act = new Actions(driver);
		js = ((JavascriptExecutor) driver);
		soft = new SoftAssert();

		String currentPageurl = driver.getCurrentUrl();
		System.out.println(currentPageurl);

		Reporter.log("Step3. Scroll to the WebElement SEE ALL INTEGRATIONS using the scrollIntoView() method.", true);
		wait.until(ExpectedConditions.presenceOfElementLocated(intgrateLink));
		WebElement integrations = driver.findElement(intgrateLink);
		js.executeScript("arguments[0].scrollIntoView(true);", integrations);

		Reporter.log("Step4. Click on the link and and validate url", true);
		js.executeScript("arguments[0].click();", integrations);

		String newTabUrl = driver.getCurrentUrl();
		System.out.println(currentPageurl);
		System.out.println(newTabUrl);
		soft.assertNotEquals(currentPageurl, newTabUrl);

		Reporter.log("Step6. Verify whether the URL is the same as the expected URL-->", true);
		soft.assertEquals(newTabUrl, "https://www.lambdatest.com/integrations");

		Reporter.log("Step7. On that page, scroll to the page where the WebElement Codeless Automation is present",
				true);
		WebElement codeless = driver.findElement(codelessHeader);
		wait.until(ExpectedConditions.presenceOfElementLocated((codelessHeader)));
		// act.click(codeless).perform();
		js.executeScript("arguments[0].scrollIntoView(true);", codeless);

		Reporter.log("Step8. Click the LEARN MORE link for Testing Whiz. The page should open in the same window.",
				true);
		WebElement wize = driver.findElement(testingWizeLink);
		wait.until(ExpectedConditions.elementToBeClickable(testingWizeLink));
		js.executeScript("arguments[0].click();", wize);

		Reporter.log("Step9. Check if the title of the page is ‘TestingWhiz Integration | LambdaTest", true);
		String pageTitle = driver.findElement(By.xpath("//h1[contains(text(), 'TestingWhiz Integration')]")).getText();
		System.out.println(pageTitle);
		soft.assertEquals(pageTitle, "TestingWhiz Integration With LambdaTest");

		Reporter.log("STep12. On the current window, set the URL to https://www.lambdatest.com/blog ", true);
		driver.navigate().to("https://www.lambdatest.com/blog");

		Reporter.log(
				"Step13. Click on the Community link and verify whether the URL is --> https://community.lambdatest.com/",
				true);
		driver.findElement(By.linkText("Community")).click();
		String pageUrl = driver.getCurrentUrl();
		System.out.println(url);

		soft.assertEquals(pageUrl, "https://community.lambdatest.com/");
		Reporter.log(
				"**Note:- Steps 4,5,10 and 11 not applicable, since the test flow doesn't contain wondow handler things",
				true);
		soft.assertAll();
	}

	@AfterMethod
	public void tearDown() {
		Reporter.log("<+======Ending the Test Method =====>", true);
		Reporter.log("Step14. Close the current browser window", true);
		Reporter.log("Closing the browser session", true);
		driver.executeScript("lambda-status=" + Status);
		driver.quit();
	}

}