package k2m77.poi.excel;

import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * @author huangxm
 */
public class ExcelUtil {

	/**
	 * @param sheet
	 * @param lc
	 * @return Content of cell
	 */
	public static String getString(Sheet sheet, Loc lc) {
		String s = getText(sheet, lc, String.class);
		if (s == null) {
			return null;
		}
		s = s.replace("\r", "");
		s = s.replace("\n", "");
		return s;
	}

	/**
	 * @param sheet
	 * @param lc
	 * @param cls
	 * @return Content of cell
	 */
	public static <V> V getText(Sheet sheet, Loc lc, Class<V> cls) {
		if (lc == null) {
			return null;
		}
		Row row = sheet.getRow(lc.getRow());
		if (row == null) {
			return cls.cast(null);
		}
		Cell cell = row.getCell(lc.getCol());
		if (cell == null) {
			return null;
		}
		String s;
		switch (cell.getCellTypeEnum()) {
		case STRING:
			s = cell.getStringCellValue();
			break;
		case NUMERIC:
			double d = cell.getNumericCellValue();
			s = d == (int) d ? Integer.valueOf((int) d).toString() : String.valueOf(d);
			String formatter = cell.getCellStyle().getDataFormatString().replace(";@", "").replace("\"", "").replace("mm", "MM");
			if (DateUtil.isCellDateFormatted(cell)) {
				s = new SimpleDateFormat(formatter).format(cell.getDateCellValue());
			}
			break;
		case FORMULA:
			s = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator().evaluate(cell).getStringValue();
			break;
		default:
			s = null;
		}
		if (s == null) {
			return null;
		}
		s = s.trim();
		if (s.isEmpty()) {
			return null;
		}
		if (String.class.equals(cls)) {
			return cls.cast(s);
		}
		if (Boolean.class.equals(cls)) {
			return cls.cast(Boolean.valueOf("〇".equals(s)));
		}
		return cls.cast(cell.getStringCellValue());
	}

	/**
	 * @param s
	 * @return location
	 */
	public static Loc loc(String s) {
		if (s == null || s.length() == 0 || s.replaceAll("[a-z|A-Z]+[0-9]+", "").length() != 0) {
			return null;
		}
		return new Loc(s);
	}

	/**
	 * @author huangxm
	 *
	 */
	public static class Loc {
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
}
