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
	 * @param sheet Excel Sheet
	 * @param lc Cell location
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
	 * @param sheet Excel sheet
	 * @param lc cell location
	 * @param cls return type class object
	 * @return Content of cell
	 * @param <V> return type class
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
	 * @param s location string
	 * @return location
	 */
	public static Loc loc(String s) {
		if (s == null || s.length() == 0 || s.replaceAll("[a-z|A-Z]+[0-9]+", "").length() != 0) {
			return null;
		}
		return new Loc(s);
	}
}
