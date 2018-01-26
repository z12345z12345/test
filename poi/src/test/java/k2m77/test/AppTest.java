package k2m77.test;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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
		assertNotNull(new WordReplaceTool() {
			// do nothing
		});
		assertTrue(true);
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("QQQwwwwQQQ", "123");
		map.put("TTTwwwwTTT", "456");
		String path = AppTest.class.getResource("").getPath();
		WordReplaceTool.replace(map, new File(path + "WordReplaceToolSrc.docx"), new File(path + "WordReplaceToolTrg.docx"));
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp2() {
		assertTrue(true);
		Map<String, String> map = new HashMap<String, String>();
		String path = AppTest.class.getResource("").getPath();
		try {
			WordReplaceTool.replace(map, path + "WordReplaceToolSrc.docx", null);
			fail();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// do nothing
		}
		try {
			WordReplaceTool.replace(map, null, "");
			fail();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// do nothing
		}
		try {
			WordReplaceTool.replace(map, path + "WordReplaceToolErr.txt", path + "WordReplaceToolTrg.docx");
			fail();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// do nothing
		}
		try {
			WordReplaceTool.replace(map, "z:\\xx.doc", path + "WordReplaceToolTrg.docx");
			fail();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// do nothing
		}
		try {
			WordReplaceTool.replace(map, path + "WordReplaceToolSrc.docx", path + "z:\\xx.doc");
			fail();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// do nothing
		}
	}
}
