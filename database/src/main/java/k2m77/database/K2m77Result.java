package k2m77.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangxm
 *
 */
public class K2m77Result {
	int cnt;

	Map<String, List<Object>> map = new HashMap<>();
	// Map<String, Column> meta = new HashMap<String, Column>();
	String[] colNames;

	/**
	 * @return column names
	 */
	public String[] getColumnNames() {
		return this.colNames;
	}

	/**
	 * @return result count
	 */
	public int count() {
		return this.cnt;
	}

	/**
	 * @param i index from 0
	 * @param colNameEnInput column name
	 * @return object value
	 */
	public Object getObject(int i, String colNameEnInput) {
		String colNameEn = colNameEnInput == null ? null : colNameEnInput.toUpperCase();
		Object value = this.map.get(colNameEn).get(i);
		return value;
	}

	/**
	 * @param i index from 0
	 * @param colNameEnInput column name
	 * @return string value
	 */
	public String getString(int i, String colNameEnInput) {
		String colNameEn = colNameEnInput == null ? null : colNameEnInput.toUpperCase();
		if (!this.map.containsKey(colNameEn)) {
			return null;
		}
		Object value = this.map.get(colNameEn).get(i);
		return value == null ? null : String.valueOf(value);
	}

}
