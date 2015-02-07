package com.bestway.client.excel;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import jxl.Cell;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.CellFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * <blockquote>
 * luosheng
 * <pre>
 *                  parameter exmaple : $P{parametername}
 *                  field exmaple     : $F{fieldname}
 * </pre>
 * 
 * </blockquote>
 */
public class Utils {

	private static String REGEX_PARAMETER = "\\$P\\{[a-z,A-Z].*?\\}";

	private static String REGEX_FIELD = "\\$F\\{[a-z,A-Z].*?\\}";

	
	
	public static void main(String[] strs) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("abc", "good");
		parameters.put("number", new Double(100.058955));
		parameters.put("date", new Date());

		List list = new ArrayList();
		for (int i = 0; i < 120; i++) {
			if (i == 0) {
				Entit e = new Entit("abc" + i, 25.5 * 9.3, new Date());
				list.add(e);
			} else {
				Entit e = new Entit("abc" + i, 25.58855, new Date());
				list.add(e);
			}
		}
		DefaultExcelDataSource ds = new DefaultExcelDataSource(list);

		exportExcelFile(new File(
				"C:\\Documents and Settings\\Administrator\\桌面\\abc.xls"),
		// new File("C:\\Documents and Settings\\Administrator\\桌面\\567.xls"),
				parameters, ds, 10, null);
	}
	
	
	
	
	private static class Entit {
		private String abc = null;
		private Double number = null;
		private Date date = null;
		
		public Entit(String abc,Double number,Date date){
			this.abc = abc;
			this.number = number;
			this.date = date;
		}
		
		public String getAbc() {
			return abc;
		}
		public void setAbc(String abc) {
			this.abc = abc;
		}
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		public Double getNumber() {
			return number;
		}
		public void setNumber(Double number) {
			this.number = number;
		}
		
	}
	
	

	public static void exportExcelFile(URL srcUrl,
			Map<String, Object> parameters,
			DefaultExcelDataSource excelDataSource, int pageSize,
			Component parentComponent) {
		exportExcelFile(srcUrl, parameters, excelDataSource, pageSize,
				parentComponent, null);
	}

	public static void exportExcelFile(URL srcUrl,
			Map<String, Object> parameters,
			DefaultExcelDataSource excelDataSource, int pageSize,
			Component parentComponent, String encoding) {
		InputStream in = null;
		try {
			in = srcUrl.openStream();
			exportExcelFile(in, parameters, excelDataSource, pageSize,
					parentComponent, encoding);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
			}
		}
	}

	public static void exportExcelFile(URL srcUrl, File destFile,
			Map<String, Object> parameters,
			DefaultExcelDataSource excelDataSource, int pageSize) {
		exportExcelFile(srcUrl, destFile, parameters, excelDataSource,
				pageSize, null);
	}

	public static void exportExcelFile(URL srcUrl, File destFile,
			Map<String, Object> parameters,
			DefaultExcelDataSource excelDataSource, int pageSize,
			String encoding) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = srcUrl.openStream();
			out = new FileOutputStream(destFile);
			exportExcelFile(in, out, parameters, excelDataSource, pageSize,
					encoding, null);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
			}
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException ex) {
			}
		}
	}

	public static void exportExcelFile(File srcFile,
			Map<String, Object> parameters,
			DefaultExcelDataSource excelDataSource, int pageSize,
			Component parentComponent) {
		exportExcelFile(srcFile, parameters, excelDataSource, pageSize,
				parentComponent, null);
	}

	public static void exportExcelFile(File srcFile,
			Map<String, Object> parameters,
			DefaultExcelDataSource excelDataSource, int pageSize,
			Component parentComponent, String encoding) {
		InputStream in = null;
		try {
			in = new FileInputStream(srcFile);
			exportExcelFile(in, parameters, excelDataSource, pageSize,
					parentComponent, encoding);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
			}
		}
	}

	public static void exportExcelFile(File srcFile, File destFile,
			Map<String, Object> parameters,
			DefaultExcelDataSource excelDataSource, int pageSize) {
		exportExcelFile(srcFile, destFile, parameters, excelDataSource,
				pageSize, null);
	}

	public static void exportExcelFile(File srcFile, File destFile,
			Map<String, Object> parameters,
			DefaultExcelDataSource excelDataSource, int pageSize,
			String encoding) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			exportExcelFile(in, out, parameters, excelDataSource, pageSize,
					encoding, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
			}
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException ex) {
			}
		}
	}

	public static void exportExcelFile(java.io.InputStream in,
			Map<String, Object> parameters,
			DefaultExcelDataSource excelDataSource, int pageSize,
			Component parentComponent) {
		exportExcelFile(in, parameters, excelDataSource, pageSize,
				parentComponent, null);
	}

	public static void exportExcelFile(java.io.InputStream in,
			Map<String, Object> parameters,
			DefaultExcelDataSource excelDataSource, int pageSize,
			Component parentComponent, String encoding) {
		File destFile = getSaveExcelFile(parentComponent);
		if (destFile == null) {
			return;
		}
		OutputStream out = null;
		try {
			out = new FileOutputStream(destFile);
			exportExcelFile(in, out, parameters, excelDataSource, pageSize,
					parentComponent, encoding, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException ex) {
			}
		
		}
	}

	public static void exportExcelFile(java.io.InputStream in,
			java.io.OutputStream out, Map<String, Object> parameters,
			DefaultExcelDataSource excelDataSource, int pageSize) {
		exportExcelFile(in, out, parameters, excelDataSource, pageSize, null,
				null);
	}

	public static void exportExcelFile(java.io.InputStream in,
			java.io.OutputStream out, Map<String, Object> parameters,
			DefaultExcelDataSource excelDataSource, int pageSize,
			String encoding, WriteExcelListener listener) {
		exportExcelFile(in, out, parameters, excelDataSource, pageSize, null,
				encoding, listener);
	}

	/**
	 * 
	 * @param in
	 *            模板流
	 * @param out
	 *            导出流
	 * @param parameters
	 *            参数
	 * @param excelDataSource
	 *            数据源
	 * @param pageSize -
	 *            sheet 的大小
	 * @param encoding -
	 *            读取 excel 文件的编码格式
	 */
	public static void exportExcelFile(java.io.InputStream in,
			java.io.OutputStream out, Map<String, Object> parameters,
			DefaultExcelDataSource excelDataSource, int pageSize,
			Component parentComponent, String encoding,
			WriteExcelListener listener) {
		//
		// 验证数据
		//
		parameters = validate(parameters, excelDataSource, pageSize);
		//
		// 加入默认的监听
		//
		if (listener == null) {
			listener = new DefaultWriteExcelListener();
		}

		Workbook readWorkbook = null;
		WritableWorkbook writableWorkbook = null;
		try {
			WorkbookSettings wbs = new WorkbookSettings();
			//
			// excel is default ISO-8859-1 encoding
			// setEncoding("UTF-8"); utf-8 no support in excel
			//
			if (encoding == null || encoding.trim().equals("")) {
				wbs.setEncoding("ISO-8859-1");
			} else {
				wbs.setEncoding(encoding);
			}

			listener.writeState(parentComponent, "正在读取Excel模块......", false, 0,
					0, 0, null);
			readWorkbook = Workbook.getWorkbook(in, wbs);

			// /////////////////////////////////////////////////////////////
			// copy read wrokbook 并改变重新写入到新的 out 中
			// 以第一个sheet == 0 进行修改
			// /////////////////////////////////////////////////////////////

			listener.writeState(parentComponent, "创建新的 Excel Sheet[0]...... ",
					false, 0, 0, 0, null);
			writableWorkbook = Workbook.createWorkbook(out, readWorkbook);
			WritableSheet sheet = writableWorkbook.getSheet(0);
			//
			// 填充所有参数值
			//			
			List<Cell> paramaterCells = findCells(sheet, REGEX_PARAMETER);
			//
			// 填充所有明细
			//
			DefaultExcelDataSource ds = excelDataSource;
			//
			// export sheet size
			//
			int count = ds.getCount();
			int sheetSize = count / pageSize + ((count % pageSize) > 0 ? 1 : 0);
			//
			// 获得所有定义字段
			//
			listener.writeState(parentComponent, "开始填充所有参数值......", false,
					sheetSize, count, 0, null);
			List<Cell> fieldCells = findCells(sheet, REGEX_FIELD);
			//
			// 分成每张 sheet 中的数据分别存在 sheetData 中
			//
			List<List<Map<String, Object>>> sheetsData = new ArrayList<List<Map<String, Object>>>();
			List<WritableSheet> sheets = new ArrayList<WritableSheet>();
			for (int p = 0; p < sheetSize; p++) {
				// 生成所有 sheet
				if (p == 0) {
					setCellParameterValue(parameters, paramaterCells, sheet);
					sheets.add(sheet);
				} else {
					WritableSheet newSheet = writableWorkbook.importSheet(sheet
							.getName()
							+ p, p, readWorkbook.getSheet(0));
					setCellParameterValue(parameters, paramaterCells, newSheet);
					sheets.add(newSheet);
				}
				// 单页数据集
				List<Map<String, Object>> sheetData = new ArrayList<Map<String, Object>>();
				for (int i = p * pageSize; i < (p + 1) * pageSize; i++) {
					if (i < count) {
						Map<String, Object> rowMap = new HashMap<String, Object>();
						//
						// 向前移动记录
						//
						if (ds.next()) {
							for (Cell cell : fieldCells) {
								String contents = cell.getContents().trim();
								String fieldName = contents.substring(3);
								fieldName = fieldName.substring(0,
										fieldName.length() - 1).trim();
								rowMap.put(fieldName, ds
										.getFieldValue(fieldName));
							}
							sheetData.add(rowMap);
						}
					}
				}
				sheetsData.add(sheetData);
			}
			listener.writeState(parentComponent, "开始获得字段填充样式......", false,
					sheetSize, count, 0, null);
			//
			// 先存取单元格格式
			//
			Map<String, WritableCellFormat> cellFormatMap = new HashMap<String, WritableCellFormat>();
			Map<String, String> fieldNameMap = new HashMap<String, String>();
			for (Cell cell : fieldCells) {
				String contents = cell.getContents().trim();
				WritableCellFormat wcfFDetail = new WritableCellFormat(cell
						.getCellFormat());
				cellFormatMap.put(contents, wcfFDetail);

				String fieldName = contents.substring(3);
				fieldName = fieldName.substring(0, fieldName.length() - 1)
						.trim();
				fieldNameMap.put(contents, fieldName);
			}
			listener.writeState(parentComponent, "开始填充所有明细字段......", true,
					sheetSize, count, 0, null);
			//
			// 创建所有 sheet ,并填充数值
			//		
			int writeExcelRow = 0;
			for (int i = 0; i < sheetSize; i++) {
				// 单页数据集
				List<Map<String, Object>> sheetData = sheetsData.get(i);
				WritableSheet tempSheet = sheets.get(i);

				for (int row = 0; row < sheetData.size(); row++) {
					Map<String, Object> rowMap = sheetData.get(row);
					if (row == 0) {
						for (Cell cell : fieldCells) {
							String contents = cell.getContents().trim();
							Object value = rowMap.get(fieldNameMap
									.get(contents));
							setCellValue(tempSheet, cell, value, cellFormatMap
									.get(contents));
						}
					} else {
						for (Cell cell : fieldCells) {
							String contents = cell.getContents().trim();
							Object value = rowMap.get(fieldNameMap
									.get(contents));
							//
							// 获得当前单元格的下面的一个单元格
							//
							int r = cell.getRow();
							int c = cell.getColumn();
							Cell nextCell = tempSheet.getCell(c, r + row);
							if (nextCell == null) {
								continue;
							}
							setCellValue(tempSheet, nextCell, value,
									cellFormatMap.get(contents));
						}
					}
					listener.writeState(parentComponent, "开始填充所有明细字段......",
							true, sheetSize, count, writeExcelRow++, null);
				}
			}
		} catch (Exception e) {
			listener.writeState(parentComponent, null, true, 0, 0, 0, false);
			e.printStackTrace();
		} finally {
			if (readWorkbook != null) {
				readWorkbook.close();
			}
			if (writableWorkbook != null) {
				try {
					writableWorkbook.write();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					writableWorkbook.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			listener.writeState(parentComponent, null, true, 0, 0, 0, true);
		}
	}

	/**
	 * 设置参数值
	 * 
	 * @param parameters
	 * @param paramaterCells
	 * @param newSheet
	 */
	private static void setCellParameterValue(Map<String, Object> parameters,
			List<Cell> paramaterCells,WritableSheet newSheet) {
		for (Cell cell : paramaterCells) {
			String contents = cell.getContents().trim();
			String key = contents.substring(3);
			key = key.substring(0, key.length() - 1).trim();
			Object value = parameters.get(key);
			setCellValue(newSheet, cell, value, null);
		}
	}

	/**
	 * 验证数据
	 * 
	 * @param parameters
	 * @param excelDataSource
	 * @param pageSize
	 * @return
	 */
	private static Map<String, Object> validate(Map<String, Object> parameters,
			DefaultExcelDataSource excelDataSource, int pageSize) {
		if (parameters == null) {
			System.out.println("parameters is null");
			parameters = new HashMap<String, Object>();
		}
		if (excelDataSource == null) {
			System.out.println("excelDataSource is null");
			throw new RuntimeException("Excel 数据源不可为空!!");
		}
		if (pageSize <= 0) {
			System.out.println("pageSize <= 0");
			throw new RuntimeException("pageSize 必须大于 0 !!");
		}
		return parameters;
	}

	/**
	 * 对单元格赋值
	 * 
	 * @param cell
	 * @param value
	 */
	private static void setCellValue(WritableSheet sheet, Cell cell,
			Object value, WritableCellFormat wcfFDetail) {
		try {
			int c = cell.getColumn();
			int r = cell.getRow();

			if (wcfFDetail == null) {
				CellFormat cellFormat = cell.getCellFormat();
				wcfFDetail = new WritableCellFormat(cellFormat);
			}

			if (value == null) {
				jxl.write.Label labelB = new jxl.write.Label(c, r, null,
						wcfFDetail);
				try {
					sheet.addCell(labelB);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}
			Class cls = value.getClass();
			if (cls.equals(Integer.class)) {
				Integer intValue = (value == null) ? 0 : (Integer) value;
				jxl.write.Number labelN = new jxl.write.Number(c, r, intValue,
						wcfFDetail);
				try {
					sheet.addCell(labelN);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (cls.equals(Double.class)) {
				Double doubleValue = (value == null) ? 0.0 : (Double) value;

				jxl.write.Number labelN = new jxl.write.Number(c, r,
						doubleValue, wcfFDetail);
				try {
					sheet.addCell(labelN);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (cls.equals(Boolean.class)) {
				jxl.write.Boolean labelB = new jxl.write.Boolean(c, r,
						value == null ? false : (Boolean) value, wcfFDetail);
				try {
					sheet.addCell(labelB);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				if (cls.equals(Date.class)) {
					java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
							"yyyy-MM-dd");
					jxl.write.Label labelB = new jxl.write.Label(c, r,
							dateFormat.format((Date) value), wcfFDetail);
					try {
						sheet.addCell(labelB);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					jxl.write.Label labelB = new jxl.write.Label(c, r, value
							.toString(), wcfFDetail);
					try {
						sheet.addCell(labelB);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 用正则表大式来获得所有参数，或字段
	 * 
	 * @param sheet
	 * @param regex
	 *            String regexByParam = "\\$P\\{[a-z,A-Z].*?\\}"; String
	 *            regexByField = "\\$F\\{[a-z,A-Z].*?\\}";
	 * @return
	 */
	private static List<Cell> findCells(WritableSheet sheet, String regex) {
		Pattern p = Pattern.compile(regex);
		List<Cell> list = new ArrayList<Cell>();
		for (int i = 0; i < sheet.getRows(); i++) {
			Cell[] row = sheet.getRow(i);
			for (int j = 0; j < row.length; j++) {
				String input = row[j].getContents();
				Matcher m = p.matcher(input);
				if (m.matches()) {
					Cell cell = row[j];
					list.add(cell);
				}
			}
		}
		return list;
	}

	/**
	 * 调出文件选择框
	 * 
	 * @param component
	 * @return
	 */
	private static File getSaveExcelFile(Component component) {
		JFileChooser fileChooser = new JFileChooser("./");
		fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
		fileChooser.addChoosableFileFilter(new ExampleFileFilter("xls"));
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		int state = fileChooser.showDialog(component, "保存 XLS 文件");
		if (state == JFileChooser.APPROVE_OPTION) {

			File f = fileChooser.getSelectedFile();
			String description = fileChooser.getFileFilter().getDescription();
			if (f.getPath().indexOf(".") > 0 || description.indexOf(".") == -1) {
				return f;
			} else {
				String suffix = description.substring(description.indexOf("."));
				return new File(f.getPath() + suffix);
			}
		}
		return null;
	}

	private static class ExampleFileFilter extends FileFilter {
		String suffix = "";

		ExampleFileFilter(String suffix) {
			this.suffix = suffix;
		}

		public boolean accept(File f) {
			String suffix = getSuffix(f);
			if (f.isDirectory() == true) {
				return true;
			}
			if (suffix != null) {
				if (suffix.toLowerCase().equals(this.suffix)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		public String getDescription() {
			return "*." + this.suffix;
		}

		private String getSuffix(File f) {
			String s = f.getPath(), suffix = null;
			int i = s.lastIndexOf('.');
			if (i > 0 && i < s.length() - 1)
				suffix = s.substring(i + 1).toLowerCase();
			return suffix;
		}
	}

}
