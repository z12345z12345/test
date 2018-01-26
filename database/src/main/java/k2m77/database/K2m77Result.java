package k2m77.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class K2m77Result {
	int cnt;

	Map<String, List<Object>> map = new HashMap<String, List<Object>>();
	// Map<String, Column> meta = new HashMap<String, Column>();
	String[] colNames;

	public String[] getColumnNames() {
		return colNames;
	}

	public int count() {
		return this.cnt;
	}

	public Object getObject(int i, String colNameEnInput) {
		String colNameEn = colNameEnInput == null ? null : colNameEnInput.toUpperCase();
		Object value = map.get(colNameEn).get(i);
		return value;
	}

	public String getString(int i, String colNameEnInput) {
		String colNameEn = colNameEnInput == null ? null : colNameEnInput.toUpperCase();
		if (!map.containsKey(colNameEn)) {
			return null;
		}
		Object value = map.get(colNameEn).get(i);
		return value == null ? null : String.valueOf(value);
	}

}
