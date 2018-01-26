package k2m77.selenium;

import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Browser {
	
	public static void show(WebDriver driver, BrowserType browser) {
		try {
			switch (browser) {
			case ie64: {
				System.setProperty("webdriver.ie.driver", "C:\\Programs\\IEDriverServer_x64_3.8.0\\IEDriverServer.exe");
				InternetExplorerOptions option = new InternetExplorerOptions();
				option.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				option.setCapability(CapabilityType.ENABLE_PROFILING_CAPABILITY, false);
				driver = new InternetExplorerDriver(option);
				break;
			}
			case ie32: {
				System.setProperty("webdriver.ie.driver", "C:\\Programs\\IEDriverServer_Win32_3.8.0\\IEDriverServer.exe");
				InternetExplorerOptions ieo = new InternetExplorerOptions();
				ieo.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				driver = new InternetExplorerDriver(ieo);
				break;
			}
			case chrome:
				ChromeOptions option = new ChromeOptions();
				// option.setCapability(CapabilityType.LOGGING_PREFS, logs);
				option.setCapability("marionette", true);
				option.setCapability(CapabilityType.ENABLE_PROFILING_CAPABILITY, false);
				System.setProperty("webdriver.chrome.driver", "C:\\Programs\\chromedriver_win32\\chromedriver.exe");
				driver = new ChromeDriver(option);
				break;
			case edge:
				System.setProperty("webdriver.edge.driver", "C:\\Programs\\MicrosoftWebDriver16299\\MicrosoftWebDriver.exe");
				driver = new EdgeDriver();
				break;
			case firefox:
				System.setProperty("webdriver.gecko.driver", "C:\\Programs\\MicrosoftWebDriver16299\\MicrosoftWebDriver.exe"); {
				// System.out.println(logs.getEnabledLogTypes());
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				// capabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
				capabilities.setCapability("marionette", true);
			}
				driver = new FirefoxDriver();
				break;
			default:
				driver = new HtmlUnitDriver();
				break;
			}
//			for (String logtype : driver.manage().logs().getAvailableLogTypes()) {
//				System.out.println(logtype);
//				driver.manage().logs().get(logtype).filter(Level.OFF);
//			}

			driver.navigate().to("https://www.baidu.com");
			driver.manage().window().maximize();
			// System.out.println(driver.getWindowHandle());
			WebElement webElement = driver.findElement(By.name("wd"));
			System.out.println(webElement.getText());
			System.out.println(webElement.getAttribute("id"));
			webElement.sendKeys(browser.name());
			webElement.sendKeys(Keys.ENTER);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			if (driver instanceof RemoteWebDriver) {
				byte[] dd = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.BYTES);
				FileOutputStream fos = new FileOutputStream("d:\\temp\\1.png");
				fos.write(dd);
				fos.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (driver != null) {
				driver.quit();
			}
		}
	}
}
