/* $Id: DMConnection.java 11 2018-01-15 02:19:05Z  $ */
package k2m77.database.dm;

import k2m77.database.K2m77ConnectionParameter;

/**
 * @author huangxm
 *
 */
public class DMConnectionParameter extends K2m77ConnectionParameter<DMConnection> {

	/**
	 * @param ip ip
	 * @param db database name
	 * @param userName user
	 * @param password password
	 */
	public DMConnectionParameter(String ip, String db, String userName, String password) {
		super("dm.jdbc.driver.DmDriver", "dm", ip, "5236", db, userName, password);
	}

}
