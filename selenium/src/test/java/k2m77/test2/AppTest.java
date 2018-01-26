package k2m77.test2;

import java.util.logging.Level;

import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import k2m77.selenium.Browser;
import k2m77.selenium.IE;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		assertTrue(true);
		{
			LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
			java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
			java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
			LogFactory.getFactory().setAttribute("org", "org.apache.commons.logging.impl.NoOpLog");
		}
		LoggingPreferences logs = new LoggingPreferences();
		logs.enable(LogType.BROWSER, Level.OFF);
		logs.enable(LogType.SERVER, Level.OFF);
		logs.enable(LogType.DRIVER, Level.OFF);
		logs.enable(LogType.PROFILER, Level.OFF);
		logs.enable(LogType.CLIENT, Level.OFF);

		WebDriver driver = null;
		Browser browser = Browser.ie64;
		IE.show(logs, driver, browser);
	}
}
