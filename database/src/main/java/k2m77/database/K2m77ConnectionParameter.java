package k2m77.database;

import java.lang.reflect.ParameterizedType;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * @author huangxm
 *
 * @param <T>
 */
public class K2m77ConnectionParameter<T extends K2m77Connection> {

	private String conClsName;
	private String jdbc;
	private String type;
	private String ip;
	private String port;
	private String db;
	private String userName;
	private String password;
	private Properties params;
	private String url;
	private String key;

	/**
	 * @param jdbc
	 * @param type
	 * @param ip
	 * @param port
	 * @param db
	 * @param userName
	 * @param password
	 */
	public K2m77ConnectionParameter(String jdbc, String type, String ip, String port, String db, String userName, String password) {
		this(jdbc, type, ip, port, db, userName, password, null);
	}

	/**
	 * @param jdbc
	 * @param type
	 * @param ip
	 * @param port
	 * @param db
	 * @param userName
	 * @param password
	 * @param params
	 */
	public K2m77ConnectionParameter(String jdbc, String type, String ip, String port, String db, String userName, String password, Properties params) {

		super();
		this.conClsName = this.computeConClsName();
		this.jdbc = jdbc;
		this.type = type;
		this.ip = ip;
		this.port = port;
		this.db = db;
		this.userName = userName;
		this.password = password;
		this.params = params == null ? null : (Properties) params.clone();
		this.url = this.computeUrl();
		this.key = this.computeKey();
	}

	/**
	 * @return connection
	 */
	public T connect() {
		return K2m77Connection.getConnection(this);
	}

	private String computeConClsName() {
		String conddClsName = K2m77Connection.class.getName();
		try {
			java.lang.reflect.Type clstype = this.getClass().getGenericSuperclass();
			if (clstype instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) clstype;
				for (java.lang.reflect.Type t : parameterizedType.getActualTypeArguments()) {
					conddClsName = t.getTypeName();
					break;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return conddClsName;
	}

	private String computeKey() {
		return "[" + this.getConClsName() + "=" + this.getUrl() + "]";
	}

	/**
	 * @return url string
	 */
	public String computeUrl() {
		String s = "jdbc:" + this.getType() + "://" + this.getIp() + ":" + this.getPort() + "/" + this.getDb();
		if (this.params != null && !this.params.isEmpty()) {
			for (Entry<Object, Object> e : this.params.entrySet()) {
				s += ";" + e.getKey() + "=" + e.getValue();
			}
		}
		return s;
	}

	/**
	 * @return connection class name
	 */
	public String getConClsName() {
		return this.conClsName;
	}

	/**
	 * @return jdbc driver string
	 */
	public String getJdbc() {
		return this.jdbc;
	}

	/**
	 * @return connection type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @return ip
	 */
	public String getIp() {
		return this.ip;
	}

	/**
	 * @return port
	 */
	public String getPort() {
		return this.port;
	}

	/**
	 * @return database name
	 */
	public String getDb() {
		return this.db;
	}

	/**
	 * @return user name
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @return params
	 */
	public Properties getParams() {
		return this.params;
	}

	/**
	 * @return url string
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * @return database connection key
	 */
	public String getKey() {
		return this.key;
	}

}
