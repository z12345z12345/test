/* 
 * Copyright 2018.
 * 
 * Replace Tool of MS Word. 
 * 
 * Date        Version       Author         Description
 * 2018/01/25  1.0.0         Huangxm        Created.
 */
package k2m77.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
	 * @param frFile Source file.
	 * @param toFile Target file.
	 * @throws Exception
	 */
	public static void replace(Map<String, String> strMap, File frFile, File toFile) throws Exception {
		try (InputStream is = new FileInputStream(frFile)) {
			try (XWPFDocument doc = new XWPFDocument(is)) {
				replace(strMap, doc);
				try (OutputStream out = new FileOutputStream(toFile)) {
					doc.write(out);
				} catch (Exception e) {
					System.out.println(e);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		try (InputStream is = new FileInputStream(toFile)) {
			try (XWPFDocument doc = new XWPFDocument(is)) {
				doc.getComments();
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
		Map<String, List<XWPFParagraph>> XWPFParagraphMap = new HashMap<String, List<XWPFParagraph>>();
		for (Map.Entry<String, String> entry : strMap.entrySet()) {
			List<XWPFParagraph> XWPFParagraphList = new ArrayList<XWPFParagraph>();
			for (IBody body : bodyList) {
				for (XWPFParagraph XWPFParagraph : body.getParagraphs()) {
					if (XWPFParagraph.getText().indexOf(entry.getKey()) < 0) {
						continue;
					}
					XWPFParagraphList.add(XWPFParagraph);
				}
			}
			XWPFParagraphMap.put(entry.getKey(), XWPFParagraphList);
		}
		for (Map.Entry<String, List<XWPFParagraph>> entry : XWPFParagraphMap.entrySet()) {
			for (XWPFParagraph XWPFParagraph : entry.getValue()) {
				String s = XWPFParagraph.getText();
				List<Integer> strIdxList = new ArrayList<Integer>();
				{
					int idx = -1;
					while ((idx = s.indexOf(entry.getKey(), idx + 1)) >= 0) {
						strIdxList.add(idx);
						idx += entry.getKey().length();
					}
				}
				int curNext = s.length();
				for (int i = strIdxList.size() - 1; i >= 0; i--) {
					int idxSta = strIdxList.get(i);
					int idxEnd = idxSta + entry.getKey().length();
					for (int idxRun = XWPFParagraph.getRuns().size() - 1; idxRun >= 0;) {
						XWPFRun XWPFRun = XWPFParagraph.getRuns().get(idxRun);
						String runStr = XWPFRun.text();
						int curSta = curNext - runStr.length();
						int curEnd = curNext;
						curNext = curSta;
						idxRun--;
						if (idxEnd <= curSta) {// idxSta,idxEnd,curSta,curEnd
							continue;
						}
						if (curEnd <= idxSta) {// curSta,curEnd,idxSta,idxEnd
							continue;
						}
						XWPFRun.setText(runStr.substring(0, Math.max(idxSta - curSta, 0))
								+ (idxSta >= curSta && idxSta <= curEnd ? strMap.get(entry.getKey()) : "")
								+ runStr.substring(runStr.length() - Math.max(curEnd - idxEnd, 0), runStr.length()), 0);
						// System.out.println(runStr + "=>" + XWPFRun.text());
					}
				}
				// System.out.println(s + "=>" + XWPFParagraph.getText());
			}
		}
	}

	/**
	 * Do nothing.
	 */
	private WordReplaceTool() {
		// Do nothing.
	}

}
