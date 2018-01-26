package k2m77.poi.excel;

/**
 * @author huangxm
 */
public class Loc {
	/**
	 * @param r
	 * @param c
	 */
	public Loc(int r, int c) {
		this.row = r;
		this.col = c;
		this.rowbase = this.row;
		this.colbase = this.col;

	}

	/**
	 * @param s
	 */
	public Loc(String s) {
		char[] rowChars = s.replaceAll("[0-9]+", "").toUpperCase().toCharArray();
		for (int i = 0; i < rowChars.length; i++) {
			this.col += rowChars[rowChars.length - 1 - i] - 'A' + 1 + 26 * i - 1;
		}
		this.row = Integer.parseInt(s.substring(rowChars.length)) - 1;
		this.rowbase = this.row;
		this.colbase = this.col;
	}

	int col;
	int row;
	int colbase;
	int rowbase;

	/**
	 * @param r
	 * @return location
	 */
	public Loc addRow(int r) {
		this.row = this.rowbase + r;
		return this;
	}

	/**
	 * @param c
	 * @return location
	 */
	public Loc addCol(int c) {
		this.col = this.colbase + c;
		return this;
	}

	/**
	 * @return column index startwith 0
	 */
	public int getCol() {
		return this.col;
	}

	/**
	 * @param row
	 */
	public void setCol(int row) {
		this.col = row;
	}

	/**
	 * @return row index startwith 0
	 */
	public int getRow() {
		return this.row;
	}

	/**
	 * @param row
	 * @return location
	 */
	public Loc setRow(int row) {
		this.row = row;
		return this;
	}
}