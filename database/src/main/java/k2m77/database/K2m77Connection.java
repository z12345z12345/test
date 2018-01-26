/* $Id: DMConnection.java 11 2018-01-15 02:19:05Z  $ */
package k2m77.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @author huangxm
 *
 */
public class K2m77Connection implements AutoCloseable {

	private static Map<K2m77ConnectionParameter<?>, K2m77Connection> _cons = new Hashtable<>();

	@SuppressWarnings("unchecked")
	static <V extends K2m77Connection> V getConnection(K2m77ConnectionParameter<V> param) {
		if (!_cons.containsKey(param)) {
			try {
				@SuppressWarnings("resource")
				V con = (V) Class.forName(param.getConClsName()).newInstance();
				con.connect(param);
				_cons.put(param, con);

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return (V) _cons.get(param);
	}

	// 定义连接对象
	private Connection conn = null;

	protected K2m77Connection() {
		// nothing.
	}

	/**
	 * Commit
	 */
	public void commit() {
		try {
			this.conn.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/* 连接DM数据库* @throws SQLException 异常 */
	/**
	 * @param param parameters
	 */
	public void connect(K2m77ConnectionParameter<?> param) {
		try {
			System.out.println("Loading JDBC Driver..." + param.getKey());
			// 加载JDBC驱动程序
			Class.forName(param.getJdbc());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Load JDBC Driver Error : " + e.getMessage());
		} catch (Exception ex) {
			throw new RuntimeException("Load JDBC Driver Error : " + ex.getMessage());
		}
		try {
			System.out.print("Connecting to DM Server...");
			// 连接DM数据库
			this.conn = DriverManager.getConnection(param.getUrl(), param.getUserName(), param.getPassword());
			System.out.println("Sussessed!");
		} catch (SQLException e) {
			throw new RuntimeException("Connect to DM Server Error : " + e.getMessage());
		}
	}

	/**
	 * @param tablename table name
	 */
	public void dropWhenExists(String tablename) {
		try (Statement st = this.conn.createStatement()) {
			try (ResultSet rs = st.executeQuery("SELECT COUNT(1) AS CNT FROM SYS.USER_TABLES WHERE TABLE_NAME='" + tablename + "'")) {
				if (!rs.next()) {
					return;
				}
				if (rs.getInt("CNT") == 1) {
					this.execute("DROP TABLE \"" + tablename + "\"");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param string sql string
	 */
	public void execute(String string) {
		try (Statement st = this.conn.createStatement()) {
			st.execute(string);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param string sql string
	 * @return update result count
	 */
	public int executeUpdate(String string) {
		try (Statement st = this.conn.createStatement()) {
			return st.executeUpdate(string);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param string sql string
	 * @param param parameters
	 */
	public void executeUpdate(String string, List<Object> param) {
		try (PreparedStatement pst = this.conn.prepareStatement(string)) {
			for (int i = 0; i < param.size(); i++) {
				pst.setObject(i + 1, param.get(i));
			}
			pst.executeUpdate();
		} catch (SQLException e) {
			System.out.println(string);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param sql sql string
	 * @return select result data
	 */
	public K2m77Result selectStringData(String sql) {
		K2m77Result result = new K2m77Result();
		try (Statement st = this.conn.createStatement()) {
			try (ResultSet rs = st.executeQuery(sql)) {
				int cnt = rs.getMetaData().getColumnCount();
				result.colNames = new String[cnt];
				for (int row = 1; row <= cnt; row++) {
					result.map.put(rs.getMetaData().getColumnName(row), new ArrayList<>());
					result.colNames[row - 1] = rs.getMetaData().getColumnName(row);
				}
				while (rs.next()) {
					for (Map.Entry<String, List<Object>> entry : result.map.entrySet()) {
						entry.getValue().add(rs.getString(entry.getKey()));
					}
				}
				result.cnt = result.map.get(rs.getMetaData().getColumnName(1)).size();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public void close() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
