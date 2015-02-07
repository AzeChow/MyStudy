package com.bestway.common.message.logic;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

public class GenerateFormatXML {

	public void doGenerate(String excelFileName) {
		File file = new File(excelFileName);
		Workbook workbook = null;
		try {

			WorkbookSettings wbs = new WorkbookSettings();
			//
			// excel is default ISO-8859-1 encoding
			// setEncoding("UTF-8"); utf-8 no support in excel
			//
			// if (encoding == null || encoding.trim().equals("")) {
			wbs.setEncoding("ISO-8859-1");
			// } else {
			// wbs.setEncoding(encoding);
			// }
			workbook = Workbook.getWorkbook(file, wbs);

			Sheet sheet = workbook.getSheet(0);

			int columns = sheet.getColumns();
			int rows = sheet.getRows();
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < rows; j++) {
				// if (beginRow > 1 && beginRow - 2 >= j) {
				// continue;
				// }
				String signNo = "";
				String tag = "";
				String tagSpec = "";
				String tagType = "";
				String tagIsNull = "";
				for (int i = 0; i < columns; i++) {
					Object obj = getValue(sheet.getCell(i, j));
					if (obj != null) {
						if (i == 0) {
							signNo = obj.toString().trim();
						} else if (i == 1) {
							tag = obj.toString().trim();
						} else if (i == 2) {
							tagSpec = obj.toString().trim();
						} else if (i == 3) {
							tagType = obj.toString().trim();
						} else if (i == 4) {
							tagIsNull = obj.toString().trim();
						}
					}
				}
				if (signNo != null && !"".equals(signNo.trim())) {
					sb.append("<field nullable=\"" + getNullable(tagIsNull)
							+ "\" signNo=\"" + signNo.toUpperCase() + "\">\n");
					sb.append("	<description>" + tagSpec.toUpperCase().trim()
							+ "</description>\n");
					sb.append("	<field-name></field-name>\n");
					sb.append("	<format>" + tagType.toUpperCase().trim()
							+ "</format>\n");
					int beginIndex = tag.indexOf("<");
					int endIndex = tag.indexOf(">");
					sb.append("	<field-flag>"
							+ tag.substring(beginIndex + 1, endIndex)
									.toUpperCase().trim() + "</field-flag>\n");
					sb.append("</field>\n");
				}
			}
			System.out.println(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
	}

	private String getNullable(String tagIsNull) {
		if ("非空".equals(tagIsNull)) {
			return "false";
		} else {
			return "true";
		}
	}

	/**
	 * @param cell
	 * @return
	 */
	private static Object getValue(Cell cell) {
		Object returnValue = null;
		if (cell.getType() == CellType.LABEL) {
			LabelCell labelCell = (LabelCell) cell;
			returnValue = labelCell.getString();
		} else if (cell.getType() == CellType.NUMBER) {
			String content = cell.getContents();
			if (content != null && !"".equals(content.trim())
					&& content.indexOf(".") < 0) {
				returnValue = content.trim();
			} else {
				NumberCell numberCell = (NumberCell) cell;
				numberCell.getNumberFormat().setMinimumFractionDigits(9);
				Double value = numberCell.getValue();
				if (value != null) {
					returnValue = value.toString().trim();
					String tempValue = returnValue.toString().trim();
					if (tempValue.substring(tempValue.length() - 2,
							tempValue.length()).equals(".0")) {
						returnValue = String.valueOf(Double.valueOf(tempValue)
								.intValue());
					}
				}
			}
		} else if (cell.getType() == CellType.DATE) {
			DateCell dateCell = (DateCell) cell;
			Date date = dateCell.getDate();
			if (date != null) {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				returnValue = bartDateFormat.format((Date) date);
			}
		} else {
			returnValue = cell.getContents();
		}
		return returnValue;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GenerateFormatXML generate = new GenerateFormatXML();
		System.out.println("请输入Excel的文件名：");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String readLine = null;
		try {
			readLine = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (readLine == null || !"Q".equals(readLine.trim())
				&& !"q".equals(readLine.trim())) {
			if (readLine != null) {
				generate.doGenerate(readLine);
			}
			System.out.println("请输入Excel的文件名：");
			try {
				readLine = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
