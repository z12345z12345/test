/* 
 * Copyright 2018.
 * 
 * Replace Tool of MS Word. 
 * 
 * Date        Version       Author         Description
 * 2018/01/25  1.0.0         Huangxm        Created.
 */
package k2m77.poi.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * Replace Tool of MS Word.
 * 
 * @author huangxm
 * @since V1.0.0
 */
public abstract class WordReplaceTool {

	/**
	 * Replace Tool of MS Word.
	 * 
	 * @param strMap Replace strings.
	 * @param frFileName Source file name.
	 * @param toFileName Target file name.
	 */
	public static void replace(Map<String, String> strMap, String frFileName, String toFileName) {
		replace(strMap, frFileName == null ? null : new File(frFileName), toFileName == null ? null : new File(toFileName));
	}

	/**
	 * Replace Tool of MS Word.
	 * 
	 * @param strMap Replace strings.
	 * @param frFile Source file.
	 * @param toFile Target file.
	 */
	public static void replace(Map<String, String> strMap, File frFile, File toFile) {
		InputStream is = null;
		try {
			is = new FileInputStream(frFile);

			OutputStream os = null;
			try {
				os = new FileOutputStream(toFile);
				replace(strMap, is, os);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			} finally {
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * Replace Tool of MS Word.
	 * 
	 * @param strMap Replace strings.
	 * @param is Source file stream.
	 * @param os Target file stream.
	 */
	public static void replace(Map<String, String> strMap, InputStream is, OutputStream os) {
		XWPFDocument doc = null;
		try {
			doc = new XWPFDocument(is);
			replace(strMap, doc);
			doc.write(os);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (doc != null) {
				try {
					doc.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * Replace Tool of MS Word.
	 * 
	 * @param strMap Replace strings.
	 * @param doc Replace document.
	 */
	public static void replace(Map<String, String> strMap, XWPFDocument doc) {
		for (Map.Entry<String, String> entry : strMap.entrySet()) {
			replace(entry.getKey(), entry.getValue(), doc);
		}
	}

	/**
	 * Replace Tool of MS Word.
	 * 
	 * @param sourceStr Replace source string.
	 * @param targetStr Replace target string.
	 * @param doc Replace document.
	 */
	public static void replace(String sourceStr, String targetStr, XWPFDocument doc) {
		List<IBody> bodyList = new ArrayList<IBody>();
		bodyList.add(doc);
		for (XWPFTable XWPFTable : doc.getTables()) {
			for (XWPFTableRow XWPFTableRow : XWPFTable.getRows()) {
				for (XWPFTableCell XWPFTableCell : XWPFTableRow.getTableCells()) {
					bodyList.add(XWPFTableCell);
				}
			}
		}
		for (XWPFHeader XWPFHeader : doc.getHeaderList()) {
			bodyList.add(XWPFHeader);
		}
		for (XWPFFooter XWPFFooter : doc.getFooterList()) {
			bodyList.add(XWPFFooter);
		}
		List<XWPFParagraph> XWPFParagraphList = new ArrayList<XWPFParagraph>();
		for (IBody body : bodyList) {
			for (XWPFParagraph XWPFParagraph : body.getParagraphs()) {
				if (XWPFParagraph.getText().indexOf(sourceStr) < 0) {
					continue;
				}
				XWPFParagraphList.add(XWPFParagraph);
			}
		}
		for (XWPFParagraph XWPFParagraph : XWPFParagraphList) {
			String s = XWPFParagraph.getText();
			List<Integer> strIdxList = new ArrayList<Integer>();
			{
				int idx = -1;
				while ((idx = s.indexOf(sourceStr, idx + 1)) >= 0) {
					strIdxList.add(Integer.valueOf(idx));
					idx += sourceStr.length();
				}
			}
			int curNext = s.length();
			int runLast = XWPFParagraph.getRuns().size() - 1;
			IdxLoop: for (int i = strIdxList.size() - 1; i >= 0; i--) {
				int idxSta = strIdxList.get(i).intValue();
				int idxEnd = idxSta + sourceStr.length();
				for (int idxRun = runLast; idxRun >= 0;) {
					XWPFRun XWPFRun = XWPFParagraph.getRuns().get(idxRun);
					String runStr = XWPFRun.text();
					int curSta = curNext - runStr.length();
					int curEnd = curNext;
					curNext = curSta;
					idxRun--;
					if (idxEnd <= curSta) {// idxSta,idxEnd,curSta,curEnd
						continue;
					}
					// if (curEnd <= idxSta) {// curSta,curEnd,idxSta,idxEnd
					// continue;
					// }
					XWPFRun.setText(runStr.substring(0, Math.max(idxSta - curSta, 0)));
					XWPFRun.setText(XWPFRun.text() + (idxSta >= curSta ? targetStr : ""));
					XWPFRun.setText(XWPFRun.text() + runStr.substring(runStr.length() - Math.max(curEnd - idxEnd, 0), runStr.length()), 0);
					if (idxSta > curSta) {
						curNext = curSta + XWPFRun.text().length();
						runLast = idxRun + 1;
						continue IdxLoop;
					}
					// System.out.println(runStr + "=>" + XWPFRun.text());
				}
			}
			// System.out.println(s + "=>" + XWPFParagraph.getText());
		}
	}
}
