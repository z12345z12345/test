package k2m77.database;

import java.lang.reflect.ParameterizedType;
import java.util.Map.Entry;
import java.util.Properties;

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

	public K2m77ConnectionParameter(String jdbc, String type, String ip, String port, String db, String userName, String password) {
		this(jdbc, type, ip, port, db, userName, password, null);
	}

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

	public T connect() {
		return K2m77Connection.getConnection(this);
	}

	private String computeConClsName() {
		String conClsName = K2m77Connection.class.getName();
		try {
			java.lang.reflect.Type type = this.getClass().getGenericSuperclass();
			if (type instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) type;
				for (java.lang.reflect.Type t : parameterizedType.getActualTypeArguments()) {
					conClsName = t.getTypeName();
					break;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return conClsName;
	}

	private String computeKey() {
		return "[" + this.getConClsName() + "=" + this.getUrl() + "]";
	}

	public String computeUrl() {
		String s = "jdbc:" + this.getType() + "://" + this.getIp() + ":" + this.getPort() + "/" + this.getDb();
		if (params != null && !params.isEmpty()) {
			for (Entry<Object, Object> e : params.entrySet()) {
				s += ";" + e.getKey() + "=" + e.getValue();
			}
		}
		return s;
	}

	public String getConClsName() {
		return conClsName;
	}

	public String getJdbc() {
		return jdbc;
	}

	public String getType() {
		return type;
	}

	public String getIp() {
		return ip;
	}

	public String getPort() {
		return port;
	}

	public String getDb() {
		return db;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public Properties getParams() {
		return params;
	}

	public String getUrl() {
		return url;
	}

	public String getKey() {
		return key;
	}

}
