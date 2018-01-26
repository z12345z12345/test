package k2m77.database.javadb;

import java.util.Properties;

import k2m77.database.K2m77Connection;
import k2m77.database.K2m77ConnectionParameter;

/**
 * @author huangxm
 *
 */
public class JavaDBConnectioneParameter extends K2m77ConnectionParameter<K2m77Connection> {
	final static Properties TRUE;
	final static Properties FALSE;

	static {
		TRUE = new Properties();
		TRUE.put("create", Boolean.TRUE);
		FALSE = null;
	}

	/**
	 * @param ip ip
	 * @param db db
	 * @param userName user name
	 * @param password password
	 * @param isCreate iscreate
	 */
	public JavaDBConnectioneParameter(String ip, String db, String userName, String password, boolean isCreate) {
		super("org.apache.derby.jdbc.ClientDriver", "derby", ip, "1527", db, userName, password, isCreate ? TRUE : FALSE);
	}

}
