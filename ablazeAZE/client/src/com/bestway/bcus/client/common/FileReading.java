package com.bestway.bcus.client.common;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.support.StaticApplicationContext;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.CellValue;


public class FileReading {
 
	/**
	 * ��ȡ excel return Object[]
	 * 
	 * @param beginRow
	 *            1,2,3,4,5....
	 * @param fileName
	 * @return
	 */
	public static List<List> readExcel(String fileName, int beginRow,
			String encoding) {
		return readExcel(new File(fileName), beginRow, encoding);
	}

	public static List<List> readExcel(String fileName, int beginRow,
			int endRow, String encoding) {
		return readExcel(new File(fileName), beginRow, endRow, encoding);
	}

	public static List<List> readExcel(File file, int beginRow, String encoding) {
		return FileReading.readExcel(file, beginRow, Integer.MAX_VALUE,
				encoding);
	}

	public static List<List> readExcel(File file, int beginRow, int endRow,
			String encoding) {
		List<List> list = new ArrayList<List>();
		Workbook workbook = null;
		try {

			WorkbookSettings wbs = new WorkbookSettings();

			// excel is default ISO-8859-1 encoding
			// setEncoding("UTF-8"); utf-8 no support in excel  
			//String encodings = System.getProperty("file.encoding");

			if (encoding == null || encoding.trim().equals("")) {
				wbs.setEncoding("ISO-8859-1");
			} else {
				wbs.setEncoding(encoding);
			}
			workbook = Workbook.getWorkbook(file, wbs);
			//第一个工作表
			Sheet sheet = workbook.getSheet(0);
			
			int columns = sheet.getColumns();
			int rows = sheet.getRows();

			for (int j = 0; j < rows; j++) {
				// 从beginRow开始读取
				if (beginRow > 1 && beginRow > (j + 1)) {
					continue;
				}
				// 读取到endRow行为止
				if (endRow < (j + 1)) {
					break;
				}
				List<Object> row = new ArrayList<Object>();
				String key = "";
				for (int i = 0; i < columns; i++) {
					key += (getValue(sheet.getCell(i, j)) == null ? "" 
							: getValue(sheet.getCell(i, j)).toString().trim());		
					Object tmp = null;
					tmp = getValue(sheet.getCell(i, j));
					
					if (tmp instanceof String) { 
//						System.out.println(" index i"+i+"  Tem >>> "+tmp+" new String >>> "+ new String(((String) tmp).getBytes(), "UTF-8").trim());
						//考虑是否只转第一列 即：i=0 的时候
//						row.add(new String(((String) tmp).getBytes(), "UTF-8").trim());
						row.add(tmp.toString().trim());
					} else {
						row.add(tmp);
					}
				}

				if (!key.trim().equals("")) {
					// System.out.println("key--->:"+key);
					list.add(row);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return list;
		
	}

	/**
	 * ��ȡ excel return String[]
	 * 
	 * @param row
	 *            1,2,3,4,5....
	 * @param fileName
	 * @return
	 */
	public static String[] readExcelCaption(File file, int row, String encoding) {
		String[] columnNames = null;
		Workbook workbook = null;
		try {
			WorkbookSettings wbs = new WorkbookSettings();
			//
			// excel is default ISO-8859-1 encoding
			// setEncoding("UTF-8"); utf-8 no support in excel
			//
			if (encoding == null || encoding.trim().equals("")) {
				wbs.setEncoding("ISO-8859-1");
//				wbs.setEncoding(wbs.getEncoding()); //使用Excel 默认的编码格式 不做转换
			} else {
				wbs.setEncoding(encoding);
			}
			workbook = Workbook.getWorkbook(file, wbs);
			//
			// ��ȡ�Sheet�� default 0
			//
			Sheet sheet = workbook.getSheet(0);

			int columns = sheet.getColumns();
			int rows = sheet.getRows();

			//
			// init Object[]
			//
			columnNames = new String[columns];

			if (row > rows || row <= 0) {
				for (int i = 0; i < columnNames.length; i++) {
					columnNames[i] = String.valueOf(i + 1);
				}
			} else {
				for (int i = 0; i < columns; i++) {
					String contents = sheet.getCell(i, row - 1).getContents();
					contents = (contents == null && contents.trim().equals("")) ? String
							.valueOf(i + 1)
							: contents;
					columnNames[i] = contents;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return columnNames;
	}

	public static String[] readExcelCaption(String fileName, int row,
			String encoding) {
		return readExcelCaption(new File(fileName), row, encoding);
	}

	/**
	 * 判断小数点后面是否全部为0，是返回True,否返回False.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkAllIsZeroAfterDot(String str) {
		String s = str.substring(str.indexOf(".") + 1);
		for (int i = 0; i < s.length(); i++) {
			String temp = String.valueOf(s.charAt(i));
			if (!temp.equals("0")) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param cell
	 * @return
	 */
	private static Object getValue(Cell cell) {
		
		Object returnValue = null;
		if (cell.getType() == CellType.LABEL) {
			LabelCell labelCell = (LabelCell) cell;
			returnValue = labelCell.getString().trim();
		} else if (cell.getType() == CellType.NUMBER) {
			/**
			 * @author zyy date 2012-04-18 修改原因：只能抓取小数点后面三位小数
			 */
			NumberCell numberCell = (NumberCell) cell;
			numberCell.getNumberFormat().setMinimumFractionDigits(9);
			double value = numberCell.getValue();
			// 科学计数法转普通计数法
			BigDecimal bd = new BigDecimal(value);
			String content = bd.toPlainString();
			if (content == null || "".equals(content.trim())) {
				return "";
			}
			if (content.indexOf(".") < 0) {
				returnValue = content.trim();
			} else {
				if (checkAllIsZeroAfterDot(content.trim())) {// 判断小数点后面是否全部为0，为True则截取小数点之前的内容
					returnValue = content.trim().substring(0,
							content.trim().indexOf("."));
				} else {
					// NumberCell numberCell = (NumberCell) cell;
					// numberCell.getNumberFormat().setMinimumFractionDigits(9);
					Double value1 = value;
					if (value1 != null) {
						returnValue = value1.toString().trim();
						String tempValue = returnValue.toString().trim();
						if (tempValue.substring(tempValue.length() - 2,
								tempValue.length()).equals(".0")) {
							returnValue = String.valueOf(Double.valueOf(
									tempValue).intValue());
						}
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
		}else {
			returnValue = cell.getContents();
		}
		return returnValue;
	}

	/**
	 * ��ȡ excel return Object[]
	 * 
	 * @param beginRow
	 *            1,2,3,4,5....
	 * @param fileName
	 * @return
	 */
	public static List<List> readTxt(File file, int beginRow, int endRow,
			String encoding) {
		BufferedReader in = null;
		List<List> list = new ArrayList<List>();
		try {
			if (encoding != null && !encoding.trim().equals("")) {
				in = new BufferedReader(new InputStreamReader(
						new FileInputStream(file), encoding));//
			} else {
				in = new BufferedReader(new FileReader(file));
			}
			String s = "";
			for (int row = 0; (s = in.readLine()) != null; row++) {
				if (beginRow > 1 && beginRow > row + 1) {
					continue;
				}
				// 读取到endRow行为止
				if (endRow < (row + 1)) {
					break;
				}
				if (s.trim().equals("")) {
					continue;
				}
				List<Object> rows = new ArrayList<Object>();
				String[] strs = s.split(String.valueOf((char) KeyEvent.VK_TAB));
				for (int column = 0; column < strs.length; column++) {
					if (strs[column] != null) {
						rows.add(strs[column].trim());
					} else {
						rows.add(strs[column]);
					}

				}
				list.add(rows);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception ex) {

			}
		}
		return list;
	}

	/**
	 * ��ȡ excel return Object[]
	 * 
	 * @param beginRow
	 *            1,2,3,4,5....
	 * @param fileName
	 * @return
	 */
	public static List<List> readTxt(File file, int beginRow, String encoding) {
		return FileReading.readTxt(file, beginRow, Integer.MAX_VALUE, encoding);
		// BufferedReader in = null;
		// List<List> list = new ArrayList<List>();
		// try {
		// if (encoding != null && !encoding.trim().equals("")) {
		// in = new BufferedReader(new InputStreamReader(
		// new FileInputStream(file), encoding));//
		// } else {
		// in = new BufferedReader(new FileReader(file));
		// }
		// String s = "";
		// for (int row = 0; (s = in.readLine()) != null; row++) {
		// if (beginRow > 1 && beginRow - 2 >= row) {
		// continue;
		// }
		// if (s.trim().equals("")) {
		// continue;
		// }
		// List<Object> rows = new ArrayList<Object>();
		// String[] strs = s.split(String.valueOf((char) KeyEvent.VK_TAB));
		// for (int column = 0; column < strs.length; column++) {
		// rows.add(strs[column]);
		// }
		// list.add(rows);
		// }
		//
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// } finally {
		// try {
		// if (in != null) {
		// in.close();
		// }
		// } catch (Exception ex) {
		//
		// }
		// }
		// return list;
	}

	public static List<List> readTxt(String fileName, int beginRow,
			String encoding) {
		return readTxt(new File(fileName), beginRow, encoding);
	}

	/**
	 * ��ȡ excel return String[]
	 * 
	 * @param row
	 *            1,2,3,4,5....
	 * @param fileName
	 * @return
	 */
	public static String[] readTxtCaption(File file, int row, String encoding) {
		String[] columnNames = new String[0];
		BufferedReader in = null;
		try {
			if (encoding != null && !encoding.trim().equals("")) {
				in = new BufferedReader(new InputStreamReader(
						new FileInputStream(file), encoding));
			} else {
				in = new BufferedReader(new FileReader(file));
			}
			int columns = 0;
			boolean isFirst = true;
			boolean isExistCaption = false;
			String s = "";
			for (int i = 0; (s = in.readLine()) != null; i++) {
				if (s.trim().equals("")) {
					continue;
				}
				if (isFirst) {
					String[] strs = s.split(String
							.valueOf((char) KeyEvent.VK_TAB));
					columns = strs.length;
					isFirst = false;
				}
				if (i == row - 1) {
					String[] strs = s.split(String
							.valueOf((char) KeyEvent.VK_TAB));
					columnNames = strs;
					isExistCaption = true;
					break;
				}
			}
			if (isExistCaption == false) {
				columnNames = new String[columns];
				for (int i = 0; i < columns; i++) {
					columnNames[i] = String.valueOf(i + 1);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception ex) {

			}
		}
		return columnNames;
	}

	public static String[] readTxtCaption(String fileName, int row,
			String encoding) {
		return readTxtCaption(new File(fileName), row, encoding);
	}

}
