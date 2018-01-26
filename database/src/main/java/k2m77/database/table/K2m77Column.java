package k2m77.database.table;

/**
 * @author huangxm
 *
 */
public class K2m77Column {
	
	private String name;
	private String typeWithLength;

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
	 * @return length
	 */
	public String getTypeWithLength() {
		return this.typeWithLength;
	}

	/**
	 * @param typeWithLength
	 */
	public void setTypeWithLength(String typeWithLength) {
		this.typeWithLength = typeWithLength;
	}
}
