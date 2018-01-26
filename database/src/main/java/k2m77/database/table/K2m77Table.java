package k2m77.database.table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangxm
 *
 */
public class K2m77Table {
	private String name;
	private String comment;
	private List<K2m77Column> columnList = new ArrayList<>();

	/**
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param comment comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return comment
	 */
	public String getComment() {
		return this.comment;
	}

	/**
	 * @return columns
	 */
	public List<K2m77Column> getColumnList() {
		return this.columnList;
	}
}
