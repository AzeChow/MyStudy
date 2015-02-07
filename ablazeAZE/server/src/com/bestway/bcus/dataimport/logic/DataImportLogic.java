package com.bestway.bcus.dataimport.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.hscode.CustomsComplex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.dataimport.dao.DataImportDao;
import com.bestway.bcus.dataimport.entity.ClassList;
import com.bestway.bcus.dataimport.entity.DBDataExecuSql;
import com.bestway.bcus.dataimport.entity.DBDataRoot;
import com.bestway.bcus.dataimport.entity.DBFormat;
import com.bestway.bcus.dataimport.entity.DBFormatSetup;
import com.bestway.bcus.dataimport.entity.FieldList;
import com.bestway.bcus.dataimport.entity.ParameterList;
import com.bestway.bcus.dataimport.entity.TempBillMasterKey;
import com.bestway.bcus.dataimport.entity.TempImpExpRequestBillKey;
import com.bestway.bcus.dataimport.entity.ThreadList;
import com.bestway.bcus.dataimport.entity.TxtFormat;
import com.bestway.bcus.dataimport.entity.TxtTaskEx;
import com.bestway.bcus.dataimport.entity.TxtTaskSel;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.innermerge.entity.MaterielAndHalfProduct;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonServerBig5GBConverter;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.common.constant.Gbflag;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.CurrCode;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.common.tools.entity.TempNodeItem;
import com.bestway.common.tools.logic.ToolsLogic;

/**
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class DataImportLogic {
	private DataImportDao dataImportDao = null;
	// private Hashtable gbHash = null;
	private Integer seqNum = 1;
	private DataSource dataSource = null;
	private static Log logger = LogFactory.getLog(DataImportLogic.class);
	private Map<String, Object> existInnerMergeMap = null;
	private int newInsertCount = 0;
	private int updateCount = 0;
	private int totalCount = 0;

	private ToolsLogic toolsLogic = null;
	private Map<TempNodeItem, List<TempNodeItem>> dataDictMap = null;
	private Hashtable hs = null;

	public ToolsLogic getToolsLogic() {
		return toolsLogic;
	}

	public void setToolsLogic(ToolsLogic toolsLogic) {
		this.toolsLogic = toolsLogic;
	}

	/**
	 * @return Returns the dataImportDao.
	 */
	public DataImportDao getDataImportDao() {
		return dataImportDao;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 关闭连接
	 * 
	 * @param conn
	 */
	private void close(Connection conn) {
		try {
			//
			// 非常重要
			//

			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param dataImportDao
	 *            The dataImportDao to set.
	 */
	public void setDataImportDao(DataImportDao dataImportDao) {
		this.dataImportDao = dataImportDao;
	}

	// private void injTofHsTable(List list) {
	// if (gbHash == null) {
	// gbHash = new Hashtable();
	// }
	// for (int i = 0; i < list.size(); i++) {
	// Gbtobig gb = (Gbtobig) list.get(i);
	// gbHash.put(gb.getName(), gb.getBigname());
	// }
	// }
	//
	// private void infTojHsTable(List list) {
	// if (gbHash == null) {
	// gbHash = new Hashtable();
	// }
	// for (int i = 0; i < list.size(); i++) {
	// Gbtobig gb = (Gbtobig) list.get(i);
	// gbHash.put(gb.getBigname(), gb.getName());
	// }
	// }

	private String changeStr(String s) {
		return CommonServerBig5GBConverter.getInstance().big5ConvertToGB(s);
		// String yy = "";
		// char[] xxx = s.toCharArray();
		// for (int i = 0; i < xxx.length; i++) {
		// String z = String.valueOf(xxx[i]);
		// if (String.valueOf(xxx[i]).getBytes().length == 2) {
		// if (gbHash.get(String.valueOf(xxx[i])) != null) {
		// z = (String) gbHash.get(String.valueOf(xxx[i]));
		// }
		// }
		// yy = yy + z;
		// }
		// return yy;
	}

	public void copyComplex() {
		List list = this.dataImportDao.findCustomsComplex();
		for (int i = 0; i < list.size(); i++) {
			CustomsComplex obj = (CustomsComplex) list.get(i);
			Complex c = new Complex();
			c.setId(obj.getCode());
			c.setCode(obj.getCode());
			c.setName(obj.getName());
			c.setFirstUnit(obj.getFirstUnit());
			c.setSecondUnit(obj.getSecondUnit());
			c.setNote(obj.getNote());
			this.dataImportDao.saveObject(c);
		}
	}

	/**
	 * 连接数据库
	 * 
	 * @param db
	 * @return
	 */
	public static String isConnect(String driverClassName, String url,
			String userName, String password) {
		try {
			Class.forName(driverClassName);

			System.out.println(driverClassName + " DataImportLogic ******");

			Connection conn = DriverManager.getConnection(url, userName,
					password);
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
			throw new RuntimeException(e2);
		}
		return "成功";
	}

	// 测试Sql语句
	public String testSql(DBDataRoot db, String sql) {
		Statement stmt;
		Connection conn = getConn(db);
		try {
			stmt = conn.createStatement();
			System.out.println("--测试sql语句：" + sql);
			ResultSet rs = stmt.executeQuery(sql);
			rs.close();
			stmt.close();
			return "成功";
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			close(conn);
		}
	}

	/**
	 * 是否空数据库并得到tableName
	 */
	public Vector isNulltable(DBDataRoot db) throws SQLException {
		Connection conn = null;
		try {
			Vector list = new Vector();
			ResultSet rs = null;
			conn = getConn(db);
			rs = conn.getMetaData().getTables(null, "%", "%",
					new String[] { "TABLE", "VIEW" });
			while (rs.next()) {
				String schem = rs.getString(2);// TABLE_SCHEM
				if (schem != null && !"".equals(schem)) {
					schem = schem + ".";
				} else {
					schem = "";
				}
				String name = schem + rs.getString(3);// TABLE_NAME
				BillTemp temp = new BillTemp();
				temp.setBill1(name);
				list.addElement(temp);
			}
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new SQLException();
		} finally {
			close(conn);
		}
	}

	// 获得字段名
	public Vector getFName(DBDataRoot db, String tableName) throws SQLException {
		Vector list = new Vector();
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConn(db);
			rs = conn.getMetaData().getColumns(null, null, getNa(tableName),
					null);
			while (rs.next()) {
				String name = rs.getString(4);// COLUMNS_NAME
				BillTemp temp = new BillTemp();
				temp.setBill1(name);
				list.addElement(temp);
			}
			rs.close();
			return list;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(conn);
		}
	}

	private String getNa(String x) {
		int i = x.indexOf(".");
		return x.substring(i + 1, x.length());
	}

	/**
	 * 在视图中检查连接
	 * 
	 * @param db
	 * @return
	 */
	public String checkDB(DBDataRoot db) {
		String driverClassName = db.getDriverClassName();
		String url = db.getUrl();
		String userName = db.getUserName();
		String password = db.getPassword();
		return isConnect(driverClassName, url, userName, password);
	}

	public int getFieldsPart(DBDataRoot dbroot, String sql) throws SQLException {

		Connection conn = null;
		try {
			conn = getConn(dbroot);
			Statement stmt;
			ResultSet readDataPart = null;

			stmt = conn.createStatement();
			readDataPart = stmt.executeQuery(getSqlAddWhere(sql));
			int count = readDataPart.getMetaData().getColumnCount();
			readDataPart.close();
			return count;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(conn);
		}
	}

	public Vector getFieldName2(DBDataRoot dbroot, String sql, int columnCount) {
		Connection conn = getConn(dbroot);
		Statement stmt;
		ResultSetMetaData returnStr = null;
		Vector columns = new Vector();
		try {
			ResultSet readDataPart = null;
			stmt = conn.createStatement();
			readDataPart = stmt.executeQuery(getSqlAddWhere(sql));
			returnStr = readDataPart.getMetaData();
			for (int j = 1; j <= columnCount; j++) {
				String columnName = returnStr.getColumnLabel(j);
				columns.add(columnName);
			}
			readDataPart.close();

		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return columns;
	}

	public Vector getTableColumn(DBDataRoot db, String sql, int columnCount)
			throws SQLException {
		Statement stmt;
		ResultSet rs = null;
		Connection conn = getConn(db);
		try {
			stmt = conn.createStatement();
			if (db.getUrl().indexOf("jdbc:odbc") > -1) {// odbc
														// db.getUrl().indexOf("jdbc:odbc:drive")
														// > -1
			} else {
				stmt.setFetchSize(500);
			}

			rs = stmt.executeQuery(sql);
			Vector<Vector> vector = new Vector<Vector>();
			while (rs.next()) {
				if (rs.getRow() > 10) {
					break;
				}
				Vector<String> array = new Vector<String>();
				for (int j = 1; j <= columnCount; j++) {
					array.add(rs.getString(j));
				}
				vector.add(array);
			}
			return vector;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			close(conn);
		}

	}

	// ////////////////////////////////////////////////////////////////////////////
	//
	// 文本导入
	//
	// ///////////////////////////////////////////////////////////////////////////
	private void txtAutoRun(Hashtable hs) {
		List list = dataImportDao.findThread("txt");
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				ThreadList threadList = (ThreadList) list.get(i);
				checkImport(threadList.getCompany(), hs);
			}
		}
	}

	private void checkImport(BaseCompany company, Hashtable hs) {
		List list = dataImportDao.findTxtTaskExForRun(company);
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				TxtTaskEx head = (TxtTaskEx) list.get(i);// 任务头
				if (head.getExcutekind().equals("1")) { // 每日
					if (Time.valueOf(head.getExcutetime()).equals(nowTime())) {

						List ls = dataImportDao.findTxtTaskSelForRun(head,
								company);
						for (int j = 0; j < ls.size(); j++) {

							TxtTaskSel detail = (TxtTaskSel) ls.get(j);

							if (detail.getTxttable() != null) {// 文本导入
								System.out.println("----开始执行文本自动导入,文本导入路径："
										+ CommonUtils.getTxtInput() + " 文件名称："
										+ detail.getTxttable().getTaskname());
								findFile(detail.getTxttable().getTaskname(),
										CommonUtils.getTxtInput(), detail,
										company, hs);
							} else {// 执行事件
								DBDataExecuSql obj = detail.getExecuSql();
								try {
									String message = execuSql(obj, company,
											dataSource.getConnection());
									if (message != null
											&& message.contains("执行失败")) {
										throw new RuntimeException(message);
									}
								} catch (SQLException e) {
									throw new RuntimeException(e.getMessage());
								}
							}
						}

					}
				} /*
				 * else if (txtSel.getTxttask().getExcutekind().equals("2")) {
				 * // 每周 if
				 * (txtSel.getTxttask().getExcuteday().equals(dayOfWeek())) { if
				 * (Time.valueOf(txtSel.getTxttask().getExcutetime())
				 * .equals(nowTime())) {
				 * findFile(txtSel.getTxttask().getTaskname(),
				 * CommonUtils.getTxtInput(), txtSel, company); } } } else if
				 * (txtSel.getTxttask().getExcutekind().equals("3")) { // 每月 if
				 * (txtSel.getTxttask().getExcuteday().equals(nowDay())) { if
				 * (Time.valueOf(txtSel.getTxttask().getExcutetime())
				 * .equals(nowTime())) {
				 * findFile(txtSel.getTxttask().getTaskname(),
				 * CommonUtils.getTxtInput(), txtSel, company); } } } else {
				 * return; }
				 */
			}
		}
	}

	// private String dayOfWeek() { // 获取星期
	// Calendar cal = Calendar.getInstance();
	// cal.setTime(StrToDate());
	// int temp = cal.get(Calendar.DAY_OF_WEEK);
	// switch (temp) {
	// case 1:
	// return "7";
	// case 2:
	// return "1";
	// case 3:
	// return "2";
	// case 4:
	// return "3";
	// case 5:
	// return "4";
	// case 6:
	// return "5";
	// case 7:
	// return "6";
	// default:
	// return "";
	// }
	// }
	//
	// private String getSuffix(File f) {
	// String s = f.getPath(), suffix = null;
	// int i = s.lastIndexOf('.');
	// if (i > 0 && i < s.length() - 1)
	// suffix = s.substring(i + 1).toLowerCase();
	// return suffix;
	// }

	// 查找文本文件
	private void findFile(String txtTask, String fileStr, TxtTaskSel txtSel,
			BaseCompany company, Hashtable hs) {
		File path = new File(fileStr);
		System.out.println("----path:" + path);
		String file = path + File.separator + txtTask + ".txt";
		System.out.println("----file:" + file);
		File mfile = new File(file);
		if (mfile.exists()) {
			txtRun(new File(file), txtSel, company, hs);
		}

		/*
		 * File files[] = path.listFiles(); for (int i = 0; i < files.length;
		 * i++) { String file = files[i].getPath(); String suffix =
		 * getSuffix(files[i]); if (suffix != null &&
		 * suffix.toLowerCase().equals("txt")) { if (getTask(new
		 * File(file)).equals(txtTask)) { txtRun(new File(file), txtSel,
		 * company, hs); break; } } }
		 */
	}

	// // 得到文本第一行任务名称
	// private String getTask(File file) {
	// BufferedReader in;
	// String taskName = "";
	// try {
	// in = new BufferedReader(new FileReader(file));
	// String s = null;
	// int row = 0;
	// try {
	// while ((s = in.readLine()) != null) {
	// row++;
	// if (row == 1) {
	// taskName = s;
	// break;
	// }
	// }
	// } catch (IOException e1) {
	// e1.printStackTrace();
	// }
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// }
	// return taskName;
	// }

	private void txtRun(File file1, TxtTaskSel txtTaskSel, BaseCompany company,
			Hashtable hs) {
		/*
		 * String beginDate = ""; String endDate = ""; beginDate = nowToDate();
		 */
		/*
		 * List list = parseTxtFile(txtTaskSel, file1); // 检测 if (list != null
		 * && !list.isEmpty()) { endDate = nowToDate(); writeFailLog(list,
		 * "txt"); saveLog("文本导入", txtTaskSel.getTxttask().getTaskname(),
		 * "导入失败", txtTaskSel.getTxttable().getTaskname(), beginDate, endDate,
		 * company);// 保存日志 } else {
		 */
		System.out.println("----files:" + file1);
		saveData(txtTaskSel, file1, company, hs); // 保存
		// endDate = nowToDate();
		/*
		 * saveLog("文本导入", txtTaskSel.getTxttask().getTaskname(), "导入成功",
		 * txtTaskSel.getTxttable().getTaskname(), beginDate, endDate,
		 * company);// 保存日志
		 */// }
	}

	// 检查数据
	// private List parseTxtFile(TxtTaskSel txtTaskSel, File file) {
	// BufferedReader in;
	// ArrayList list = new ArrayList();
	// String[] strs = null;
	// int txtRow = 0;
	// int row = 0;
	// List txtFormatList = dataImportDao.findTxtFormat(txtTaskSel
	// .getTxttable());
	// Hashtable selectedValues = getHash(txtFormatList);
	// try {
	// in = new BufferedReader(new FileReader(file));
	// String s = new String();
	// try {
	// while ((s = in.readLine()) != null) {
	// row++;
	// if (row == 1
	// && CommonUtils.getIstitleRow().equals(
	// new Boolean(true))) {
	// continue;
	// }
	// if (s.trim().equals("")) {
	// continue;
	// }
	// strs = s.split(String.valueOf((char) 9));
	// for (int j = 0; j < txtFormatList.size(); j++) {
	// TxtFormat txtFormat = (TxtFormat) txtFormatList.get(j);
	// BillTemp temp = new BillTemp();
	// temp = checkIsNull(txtFormat, temp, row,
	// getFileColumnValue(strs, j + 1, selectedValues));
	// if (temp != null) {
	// list.add(temp); // 检查是否为空
	// }
	// BillTemp temp1 = new BillTemp();
	// temp1 = checkType(txtFormat, temp1, row,
	// getFileColumnValue(strs, j + 1, selectedValues));
	// if (temp1 != null) {
	// list.add(temp1); // 检查字段类型
	// }
	// BillTemp temp2 = new BillTemp();
	// int valueLenth = getFileColumnValue(strs, j + 1,
	// selectedValues).getBytes().length;
	// temp2 = checkLenth(txtFormat, temp2, row, valueLenth);
	// if (temp2 != null) {
	//
	// list.add(temp2); // 检查字段长度
	// }
	// }
	// txtRow = row;
	// }
	// } catch (IOException e1) {
	// e1.printStackTrace();
	// }
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// }
	// return list;
	// }

	// 保存数据
	private void saveData(TxtTaskSel txtTaskSel, File file,
			BaseCompany company, Hashtable hs) {
		BufferedReader in;
		String[] strs = null;
		String inputSelect = txtTaskSel.getTxttask().getInputSelect();// 导入选项
		System.out.println("---inputSelect:" + inputSelect);
		List txtFormatList = dataImportDao.findTxtFormatCompany(
				txtTaskSel.getTxttable(), company);
		Hashtable selectedValues = getHash(txtFormatList);
		String className = txtTaskSel.getTxttable().getClassList()
				.getClassPath().trim();
		System.out.println("---className:" + className);
		if (hs.get(className) != null) {
			className = (String) hs.get(className);
		}
		System.out.println("---className2:" + className);
		System.out.println("---txtFormatList:" + txtFormatList);

		int row = 0;
		try {
			in = new BufferedReader(new FileReader(file));
			String s = new String();
			try {
				while ((s = in.readLine()) != null) {
					row++;
					if (row == 1
					/*
					 * && CommonUtils.getIstitleRow().equals( new Boolean(true))
					 */) {
						continue;
					}
					if (s.trim().equals("")) {
						continue;
					}
					strs = s.split(String.valueOf((char) 9));
					// if (inputSelect.equals("0")) { // 不处理
					/*
					 * if (isRepeat(className, txtFormatList, strs, "0",
					 * selectedValues, company, hs).equals("1")) { noInsert++;
					 * continue; } else {
					 */
					save(className, txtFormatList, strs, selectedValues,
							company, hs);
					// insert++;
					// }
					// }
					/*
					 * else if (inputSelect.equals("1")) { // 覆盖 if
					 * (isRepeat(className, txtFormatList, strs, "1",
					 * selectedValues, company, hs).equals("1")) {
					 * save(className, txtFormatList, strs, selectedValues,
					 * company, hs); overInsert++; } else { save(className,
					 * txtFormatList, strs, selectedValues, company, hs);
					 * insert++; } } else if (inputSelect.equals("2")) { // 更新
					 * if (isRepeat(className, txtFormatList, strs, "2",
					 * selectedValues, company, hs).equals("1")) {
					 * changeInsert++; // save(className,txtFormatList,strs); }
					 * else { save(className, txtFormatList, strs,
					 * selectedValues, company, hs); insert++; } }
					 */
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				in.close();
				/*
				 * writeSucceed("导入任务名称: " +
				 * txtTaskSel.getTxttask().getTaskname() + "  导入目标表:" +
				 * txtTaskSel.getTxttable().getClassList().getName() +
				 * "\n文本行总数:" + row + " (行)不处理重复行数:" + noInsert +
				 * "  (行)  覆盖行数:   " + overInsert + "  (行)  更新行数:" +
				 * changeInsert + " (行)  新插入行数:   " + insert + "  (行)", "txt");
				 */
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

	}

	// 保存新数据
	private void save(String className, List txtFormatList, String[] strs,
			Hashtable selectedValues, BaseCompany company, Hashtable hs) {
		try {
			Class cls = Class.forName(className);
			Object obj = cls.newInstance();
			for (int j = 0; j < txtFormatList.size(); j++) {// 字段设置
				TxtFormat txtFormat = (TxtFormat) txtFormatList.get(j);
				for (int i = 0; i < PropertyUtils.getPropertyDescriptors(obj).length; i++) {// 字段类
					if (PropertyUtils.getPropertyDescriptors(obj)[i].getName()
							.equals(txtFormat.getFieldname())) {
						Object obj1 = getObj(
								txtFormat,
								getFileColumnValue(strs, j + 1, selectedValues),
								company, hs);
						System.out.println("-----obj1:" + obj1);
						PropertyUtils.setProperty(obj,
								txtFormat.getFieldname(), obj1);
					}
				}
			}
			dataImportDao.saveTxtInput(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 检查重复值
	// private String isRepeat(String className, List txtFormatList,
	// String[] strs, String del, Hashtable selectedValues,
	// BaseCompany company, Hashtable hs) {
	// String ss = "0";
	// for (int j = 0; j < txtFormatList.size(); j++) {// 字段设置
	// TxtFormat txtFormat = (TxtFormat) txtFormatList.get(j);
	// if (txtFormat.getIskey().equals(new Boolean(true))) { // 主键
	// List list = dataImportDao.findRepeatListCompany(className,
	// txtFormat.getFieldname(), getFileColumnValue(strs,
	// j + 1, selectedValues), txtFormat.getTxttask()
	// .getClassList().getIsCoid(), company);
	// if (list.size() > 0) {
	// ss = "1"; // 找到重复值
	// if (del.equals("1")) {
	// Object obj = list.get(0);
	// dataImportDao.deleteTxtInput(obj);
	// }
	// if (del.equals("2")) {
	// Object obj1 = list.get(0);
	// try {
	// for (int k = 0; k < txtFormatList.size(); k++) {// 字段设置
	// TxtFormat txtFormat1 = (TxtFormat) txtFormatList
	// .get(k);
	// for (int i = 0; i < PropertyUtils
	// .getPropertyDescriptors(obj1).length; i++) {// 字段类
	// if (PropertyUtils
	// .getPropertyDescriptors(obj1)[i]
	// .getName().equals(
	// txtFormat1.getFieldname())
	// && !PropertyUtils
	// .getPropertyDescriptors(obj1)[i]
	// .getName()
	// .equals(
	// txtFormat
	// .getFieldname())) {
	// Object obj2 = getObj(txtFormat1,
	// getFileColumnValue(strs, k + 1,
	// selectedValues),
	// company, hs);
	// PropertyUtils
	// .setProperty(obj1, txtFormat1
	// .getFieldname(), obj2);
	// }
	// }
	// }
	// dataImportDao.saveTxtInput(obj1);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// break;
	// }
	// }
	// }
	// return ss;
	// }

	// 得到对象值
	private Object getObj(TxtFormat txtFormat, String value,
			BaseCompany company, Hashtable hs) {
		Object obj1 = null;
		if (txtFormat.getGbflag().equals(Gbflag.NEW_COID)) { // 当前公司id
			obj1 = company;
		} else if (txtFormat.getGbflag() == null
				|| txtFormat.getGbflag().equals("")
				|| txtFormat.getGbflag().equals(Gbflag.NO)) { // 无
			obj1 = value;
		} else if (txtFormat.getGbflag().equals(Gbflag.OBJ)) { // 对象转换
			String obj = txtFormat.getObjName().getClassPath();
			if (hs.get(obj) != null) {
				obj = (String) hs.get(obj);
			}
			String field = txtFormat.getGbStr().getFieldname();
			List list = dataImportDao.findObjListCompany(obj, field, value,
					txtFormat.getObjName().getIsCoid(), company);// 查找对象
			if (list != null && !list.isEmpty()) {
				obj1 = list.get(0);
			} else {
				obj1 = null;
			}
		} else if (txtFormat.getGbflag().equals(Gbflag.SEQNUM)) { // 序号/或流水号
			//
		}
		return obj1;
	}

	private Hashtable getHash(List list) {
		Hashtable selectedValues = new Hashtable();
		for (int i = 0; i < list.size(); i++) {
			selectedValues.put(Integer.valueOf(i + 1),
					((TxtFormat) list.get(i)).getSortno());
		}
		return selectedValues;
	}

	private String getFileColumnValue(String[] fileRow, int dataIndex,
			Hashtable selectedValues) {
		int columnIndex = Integer.parseInt(selectedValues.get(
				Integer.valueOf(dataIndex)).toString()) - 1;
		if (columnIndex < 0 || columnIndex > fileRow.length - 1) {
			return "";
		} else {
			return fileRow[columnIndex];
		}
	}

	// // 检查是否为空
	// private BillTemp checkIsNull(TxtFormat txtFormat, BillTemp temp, int j,
	// String value) {
	// if (txtFormat.getIsNull().equals(new Boolean(false))) {
	// if (value.equals("")) {
	// temp.setBill1("In Text Data The line " + String.valueOf(j + 1)
	// + " error:[" + txtFormat.getName()
	// + "] Fileld Can't Null");
	// return temp;
	// }
	// }
	// return null;
	// }

	// 检查长度
	// private BillTemp checkLenth(TxtFormat txtFormat, BillTemp temp, int j,
	// int valueLenth) {
	// String fieldType = txtFormat.getFieldtype().trim();
	// if (fieldType.equals("String")) {
	// int fieldLenth = txtFormat.getFieldlen().intValue();
	// if (valueLenth > fieldLenth) {
	// temp.setBill1("In Text Data The Line " + String.valueOf(j + 1)
	// + " Error:[" + txtFormat.getName()
	// + "]Field Length too Long(allow max length："
	// + String.valueOf(fieldLenth) + "；实际长度："
	// + String.valueOf(valueLenth) + ")！");
	// return temp;
	// }
	// }
	// return null;
	// }

	// 检查类型
	// private BillTemp checkType(TxtFormat txtFormat, BillTemp temp, int j,
	// String value) {
	// String fieldType = txtFormat.getFieldtype().trim();
	// try {
	// if (fieldType.equals("Double")) {
	// Double.valueOf(value);
	// } else if (fieldType.equals("Integer")) {
	// Integer.valueOf(value);
	// } else if (fieldType.equals("boolean")) {
	// Boolean.parseBoolean(value);
	// } else if (fieldType.equals("Date")) {
	// java.sql.Date.valueOf(value);
	// } else if (fieldType.equals("int")) {
	// Integer.parseInt(value);
	// } else if (fieldType.equals("double")) {
	// Double.parseDouble(value);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// temp.setBill1("In Text Date File, The Line "
	// + String.valueOf(j + 1) + " Error:[" + txtFormat.getName()
	// + "]栏位字串类型不匹配(类型应为：" + fieldType + ")！");
	// return temp;
	// }
	// return null;
	// }

	public void changeIsTitle(Boolean isTitleRow) {
		CommonUtils.setIstitleRow(isTitleRow);
	}

	// private void writeFailLog(List list, String type) {
	// if (list != null && !list.isEmpty()) {
	// logger.info("[JBCUS DATA IMPORT] 导入类型：" + type);
	// for (int i = 0; i < list.size(); i++) { // 行
	// BillTemp txt = (BillTemp) list.get(i);
	// logger.info("[JBCUS DATA IMPORT] 错误：" + txt.getBill1());
	// }
	// }
	// }
	//
	// private void writeSucceed(String str, String type) {
	// logger.info("[JBCUS DATA IMPORT] 成功导入信息,导入类型" + type);
	// logger.info("[JBCUS DATA IMPORT]  " + str);
	// }

	private Time nowTime() {
		SimpleDateFormat bartDateFormat1 = new SimpleDateFormat("HH:mm:ss");
		String defaultDate1 = bartDateFormat1.format(new Date());
		return Time.valueOf(defaultDate1);
	}

	// private void saveLog(String type, String taskName, String result,
	// String otherNote, // 写数据库日志
	// String beginDate, String endDate, BaseCompany company) {
	// TxtLog log = new TxtLog();
	// log.setType(type);
	// log.setTaskName(taskName);
	// log.setResult(result);
	// log.setOtherNote(otherNote);
	// log.setBeginDate(beginDate);
	// log.setEndDate(endDate);
	// log.setComputer(getComputerName());
	// log.setExcuter("自动导入");
	// log.setCompany(company);
	// dataImportDao.saveTxtLog(log);
	// }

	// private String getComputerName() { // 得到计算机名
	// try {
	// InetAddress localhost = InetAddress.getLocalHost();
	// return localhost.getHostName();
	// } catch (UnknownHostException uhe) {
	// uhe.printStackTrace();
	// return "";
	// }
	// }

	// private static Date StrToDate() {
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	// Date date = new Date();
	// String str = sdf.format(date);
	// int start = str.indexOf('/');
	// String year = str.substring(0, start);
	// start++;
	// int start1 = str.indexOf('/', start);
	// String month = str.substring(start, start1);
	// String day = str.substring(start1 + 1);
	// Calendar cal = Calendar.getInstance();
	// cal.setTime(date);
	// cal.set(Calendar.YEAR, Integer.parseInt(year));
	// cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
	// cal.set(Calendar.DATE, Integer.parseInt(day));
	// return cal.getTime();
	// }
	//
	// private String nowToDate() {
	// String pattern = "yyyy-MM-dd HH:mm:ss";
	// SimpleDateFormat formater = new SimpleDateFormat(pattern);
	// Date date1 = new Date();
	// String str1 = formater.format(date1);
	// return str1;
	// }

	// private String nowDay() { // 获取日
	// SimpleDateFormat sdf = new SimpleDateFormat("dd");
	// String day = sdf.format(new Date());
	// return String.valueOf(Integer.valueOf(day));
	// }

	public void excuteStartTxt() {
		if (hs == null) {
			hs = new Hashtable();
			dataDictMap = toolsLogic.getTableColumnMap();
			List<TempNodeItem> listitem = new ArrayList<TempNodeItem>();
			listitem.addAll(dataDictMap.keySet());
			Collections.sort(listitem);
			for (int i = 0; i < listitem.size(); i++) {
				TempNodeItem obj = (TempNodeItem) listitem.get(i);
				String className = forClassName(obj.getClassName());
				hs.put(className, obj.getClassName());
			}
		}
		System.out.println("---开始启动文本导入线程");
		new StartUptxt(hs).start();
	}

	class StartUptxt extends Thread {
		Hashtable hs = null;

		public StartUptxt(Hashtable hs) {
			this.hs = hs;
		}

		public void run() {
			int interval = 1000;
			int loopTimes = 0;
			try {
				while (2 == 2) {
					// 每隔五分钟清除一下
					if (loopTimes == 300) {
						loopTimes = 0;
						System.gc();
					} else {
						loopTimes++;
					}

					Thread.sleep(interval);
					txtAutoRun(hs);// 调用方法执行
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 以下是数据导入 得到数据集
	 * 
	 * @param db
	 * @param sql
	 * @return
	 */

	public ResultSet readData(DBDataRoot db, String sql, Connection conn) {
		ResultSet rs = null;
		Statement stmt = null;
		// 2011-8-16 chl 先关掉 conn, 否则资源将耗尽
		close(conn);
		conn = getConn(db);
		try {
			stmt = conn.createStatement();
			if (db.getUrl().indexOf("jdbc:odbc") > -1) {// odbc
														// db.getUrl().indexOf("jdbc:odbc:drive")

			} else {
				stmt.setFetchSize(500);
			}
			rs = stmt.executeQuery(sql);

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
			throw new RuntimeException(e);
		}
		return rs;
	}

	// 连接数据源
	private static Connection getConn(DBDataRoot db) {
		Connection conn = null;
		String driverClassName = db.getDriverClassName();
		String url = db.getUrl();
		String userName = db.getUserName();
		String password = db.getPassword();
		try {
			Class.forName(driverClassName);
			conn = DriverManager.getConnection(url, userName, password);

			//
			// read un committed (设置事务级别)
			//
			/*
			 * if (url.indexOf("jdbc:oracle:thin") > -1) { // orcle driver
			 * 注意：对于oracle版本较低的驱动会有问题
			 * conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE
			 * ); } else if (url.indexOf("jdbc:jtds:sqlserver") > -1)
			 * {//SqlServer driver 因odbc会报错！！
			 * conn.setTransactionIsolation(Connection
			 * .TRANSACTION_READ_UNCOMMITTED); }
			 */
			if (url.indexOf("jdbc:JdbcProgress") > -1) {
				conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			logger.error(e1);
			throw new RuntimeException(e1);
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
			logger.error(e2);
			throw new RuntimeException(e2);
		}
		return conn;
	}

	/**
	 * DB导入数据（前台）
	 */
	public List intoData(DBFormat dbFormat, BaseCompany company, String deal,
			String inputSelect, List listBg, Hashtable hs) { // 字段设置
		List countList = new ArrayList();
		newInsertCount = 0;
		updateCount = 0;
		totalCount = 0;
		//
		// 要求重新生成这些Map
		//
		DataImportMaps dataImportMaps = new DataImportMaps();

		seqNum = 0;
		boolean ischange = getChange(listBg, dbFormat);

		// 判断是否编码转换
		boolean isBmTran = ((dbFormat.getIsBigToBgk() != null && dbFormat
				.getIsBigToBgk().equals(new Boolean(true))) ? true : false);
		String sqlScript = dbFormat.getDbView().getSqlScript();

		sqlScript = formatSql(sqlScript, company);
		//
		// 测试连接数据
		//
		DBDataRoot db = dbFormat.getDbView().getDb();
		String className = dbFormat.getClassList().getClassPath();
		if (hs.get(className) != null) {
			className = (String) hs.get(className);
		}
		//
		// 域设置字段
		//
		List list = dataImportDao.findDBFormatSetup(dbFormat, company);
		Map<String, List<DBFormatSetup>> moreWhereMap = getMoreWhereMap(list);
		//
		// 获得连接字符串 ,并得到数据集
		//
		Connection conn = null;

		logger.info("[JBCUS DATA IMPORT]  开始执行数据导入  导入的域名称 == :"
				+ dbFormat.getRegionName());
		// long beginTime = new Date().getTime();
		long beginTime = System.currentTimeMillis();
		try {
			ResultSet rs = readData(db, sqlScript, conn);
			logger.info("" + sqlScript);
			List<String> lsField = new ArrayList<String>();
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {

				String columnName = rs.getMetaData().getColumnLabel(i);
				lsField.add(columnName);
			}
			if (inputSelect.equals("3") || inputSelect.equals("0")) { // 直接保存,不检查主键（是否重复）
				while (rs.next()) {
					totalCount++;
					seqNum++;
					Map<String, Object> mDataMap = new HashMap<String, Object>();
					this.getDataMap(rs, lsField, mDataMap);
					try {
						saveDBData(className, list, mDataMap, company,
								ischange, moreWhereMap, dataImportMaps, hs,
								isBmTran);
					} catch (Exception e) {
						e.printStackTrace();
						if (deal.equals("终止")) {
							throw new RuntimeException(
									"[JBCUS DATA IMPORT]  Save Error：" + e);
						}
					}
				}
			} else if (inputSelect.equals("2")) { // 更新
				//
				// 海关帐单据的更新
				//
				if (BillUtil.isExistBillMaster(className)) {

					logger.info("[JBCUS DATA IMPORT]  导入的数据-----开始 批量更新海关帐单据表头 ");

					batchUpdateBillMaster(company, deal, dataImportMaps,
							ischange, className, list, moreWhereMap, rs,
							lsField, hs, isBmTran);

					logger.info("[JBCUS DATA IMPORT]  导入的数据-----结束 批量更新海关帐单据表头 ");

				} else if (BillUtil.isExistBillDetail(className)) {

					logger.info("[JBCUS DATA IMPORT]  导入的数据-----开始 批量更新海关帐单据表体 ");

					batchUpdateBillDetail(company, deal, dataImportMaps,
							ischange, className, list, moreWhereMap, rs,
							lsField, hs, isBmTran);

					logger.info("[JBCUS DATA IMPORT]  导入的数据-----结束 批量更新海关帐单据表体 ");

				} else if (className.equals(ImpExpRequestBill.class.getName())) {

					logger.info("[JBCUS DATA IMPORT]  导入的数据-----开始 批量更新进出口申请单据表头 ");

					batchUpdateImpExpRequestBill(company, deal, dataImportMaps,
							ischange, className, list, moreWhereMap, rs,
							lsField, hs, isBmTran);

					logger.info("[JBCUS DATA IMPORT]  导入的数据-----结束 批量更新进出口申请单据表头 ");

				} else if (className
						.equals(ImpExpCommodityInfo.class.getName())) {

					logger.info("[JBCUS DATA IMPORT]  导入的数据-----开始 批量更新进出口申请单对象明细 ");

					batchUpdateImpExpCommodityInfo(company, deal,
							dataImportMaps, ischange, className, list,
							moreWhereMap, rs, lsField, hs, isBmTran);

					logger.info("[JBCUS DATA IMPORT]  导入的数据-----结束 批量更新进出口申请单对象明细 ");

				} else if (className.equals(EnterpriseMaterial.class.getName())) {

					logger.info("[JBCUS DATA IMPORT]  导入的数据-----开始 批量更新企业物料对象 ");

					batchUpdateEnterpriseMateriel(company, deal,
							dataImportMaps, ischange, className, list,
							moreWhereMap, rs, lsField, hs, isBmTran);

					logger.info("[JBCUS DATA IMPORT]  导入的数据-----结束 批量更新企业物料对象 ");

				} else if (className.equals(InnerMergeData.class.getName())) {

					logger.info("[JBCUS DATA IMPORT]  导入的数据-----开始 批量更新内部归并对象 ");

					batchUpdateInnerMergeData(company, deal, dataImportMaps,
							ischange, className, list, moreWhereMap, rs,
							lsField, hs, isBmTran);

					logger.info("[JBCUS DATA IMPORT]  导入的数据-----结束 批量更新内部归并对象 ");

				} else { // 其它单据的更新 在更新数据对象中只允许更新 仓库,客户供应商,币制,工厂单位
					while (rs.next()) {
						totalCount++;
						Map<String, Object> mDataMap = new HashMap<String, Object>();
						this.getDataMap(rs, lsField, mDataMap);
						try {
							updateDBData(className, list, mDataMap, company,
									ischange, moreWhereMap, dataImportMaps, hs,
									isBmTran);
						} catch (Exception e) {
							e.printStackTrace();
							if (deal.equals("终止")) {
								throw new RuntimeException(
										"[JBCUS DATA IMPORT]  Save Error：" + e);
							}
						}
					}
				}
			}
			//
			// 注意关闭
			//
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("[JBCUS DATA IMPORT]  Get Data Error："
					+ e);
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				logger.info("[JBCUS DATA IMPORT]  数据连接对象关闭 ");
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(
						"[JBCUS DATA IMPORT]  数据连接对象关闭失败！，原因：" + ex);

			}
		}

		countList.add(totalCount);
		countList.add(updateCount);
		countList.add(newInsertCount);
		double m = (System.currentTimeMillis() - beginTime) / 1000.0;
		logger.info("[JBCUS DATA IMPORT]  结束数据导入 " + totalCount
				+ " 条记录,总用时 == " + m + " 秒 ");
		return countList;
	}

	/**
	 * 把ResultSet 的行值转换为 Map 进行存取
	 * 
	 * @param rs
	 * @param lsField
	 * @param mDataMap
	 * @throws SQLException
	 */
	private void getDataMap(ResultSet rs, List<String> lsField,
			Map<String, Object> mDataMap) throws SQLException {
		for (int i = 0; i < lsField.size(); i++) {
			String columnName = lsField.get(i);
			Object obj = rs.getObject(columnName);
			if (obj instanceof oracle.sql.TIMESTAMP) {// oracle 10
				// 的兼容性
				oracle.sql.TIMESTAMP oTimestamp = (oracle.sql.TIMESTAMP) obj;
				Timestamp t = oTimestamp.timestampValue();
				mDataMap.put(columnName, t);
			} else if (obj != null && obj instanceof String) {
				mDataMap.put(columnName, ((String) obj).trim());
			} else {
				mDataMap.put(columnName, obj);
			}
		}
	}

	/**
	 * 获得是否变更
	 * 
	 * @param listBg
	 * @param dbFormatMaster
	 * @return
	 */
	private boolean getChange(List listBg, DBFormat dbFormatMaster) {
		boolean ischange = true;
		if (dbFormatMaster.getGbflag() != null
				&& dbFormatMaster.getGbflag().equals("2")) {
			// injTofHsTable(listBg);
		} else if (dbFormatMaster.getGbflag() != null
				&& dbFormatMaster.getGbflag().equals("1")) {
			// infTojHsTable(listBg);
		} else {
			ischange = false;
		}
		return ischange;
	}

	/**
	 * 
	 * 这段代码的作用是:获得对象转换所需要的多个条件的 key = 目的字段名
	 * 
	 * @param list
	 * @return
	 */
	private Map<String, List<DBFormatSetup>> getMoreWhereMap(
			List<DBFormatSetup> list) {
		Map<String, List<DBFormatSetup>> moreWhereMap = new HashMap<String, List<DBFormatSetup>>();
		for (int i = 0; i < list.size(); i++) {
			DBFormatSetup temp = (DBFormatSetup) list.get(i);
			if (!temp.getGbflag().equals(Gbflag.OBJ)) {
				continue;
			}
			String key = temp.getAimFieldName();
			if (moreWhereMap.get(key) == null) {
				List<DBFormatSetup> tmepList = new ArrayList<DBFormatSetup>();
				tmepList.add(temp);
				moreWhereMap.put(key, tmepList);
			} else {// 多条件
				List<DBFormatSetup> tmepList = moreWhereMap.get(key);
				tmepList.add(temp);
			}
		}
		return moreWhereMap;
	}

	/**
	 * 常规保存
	 * 
	 * @param className
	 * @param list
	 * @param rs
	 * @param company
	 * @param ischange
	 */
	private void saveDBData(String className, List list,
			final Map<String, Object> mDataMap, BaseCompany company,
			boolean ischange, Map<String, List<DBFormatSetup>> moreWhereMap,
			DataImportMaps dataImportMaps, Hashtable hs, boolean isBmTran) {
		try {
			Class cls = Class.forName(className);
			Object obj = cls.newInstance();
			//
			// 存在重复的字段对照
			//
			Map<String, String> repeatFieldMap = new HashMap<String, String>();
			for (int i = 0; i < list.size(); i++) {
				Object objValue = null;
				DBFormatSetup dbFormatSetup = (DBFormatSetup) list.get(i);// 字段对照
				Object value = null;
				//
				// 以目的字段为key来获到多条件,并且是对象转换的类型
				//
				List aimNameReList = moreWhereMap.get(dbFormatSetup
						.getAimFieldName());
				if (aimNameReList != null && aimNameReList.size() > 0) { // 存在重复的字段对照
					if (repeatFieldMap.get(dbFormatSetup.getAimFieldName()) == null) {
						objValue = getCasObjectValue(aimNameReList, mDataMap,
								ischange, company, dataImportMaps, hs, isBmTran);
						repeatFieldMap.put(dbFormatSetup.getAimFieldName(),
								dbFormatSetup.getAimFieldName());
					} else {
						continue;
					}
				} else {
					value = mDataMap.get(dbFormatSetup.getSourceFieldName()); // 获得值
					if (isBmTran && value != null && value instanceof String) {// 编码转换
						value = new String(value.toString().getBytes("GBK"),
								"Big5");
					}
					if (value != null && value instanceof String && ischange) {
						value = changeStr(value.toString()); // 繁简转换
					}
					objValue = getDBObjValue(dbFormatSetup, value, company);
				}

				if (objValue == null) {
					continue;
				}
				BeanUtils.setProperty(obj, dbFormatSetup.getAimFieldName(),
						objValue);
			}
			newInsertCount++;
			dataImportDao.saveTxtInput(obj);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("DB Import Error：" + e.getMessage());
		}
	}

	/**
	 * 更新保存(这个更新只对物料
	 * 
	 * @param className
	 * @param list
	 * @param rs
	 * @param company
	 * @param ischange
	 */
	private void updateDBData(String className, List list,
			final Map<String, Object> mDataMap, BaseCompany company,
			boolean ischange, Map<String, List<DBFormatSetup>> moreWhereMap,
			DataImportMaps dataImportMaps, Hashtable hs, boolean isBmTran) {
		//
		// key = code 这是已存在的对象
		//
		Map<String, Object> existObjectMap = null;
		//
		// 明白这里浪费了小小性能,但这个并不重要!!以后再改善
		//
		className = className.trim();
		if (className.equalsIgnoreCase(WareSet.class.getName())) {// 仓库
			existObjectMap = dataImportMaps.getWareSet((Company) company);
		} else if (className.equalsIgnoreCase(ScmCoc.class.getName())) {// 客户供应商
			existObjectMap = dataImportMaps.getScmCoc((Company) company);
		} else if (className.equalsIgnoreCase(CurrCode.class.getName())) {// 币制
			// 这个没有公司// ID 字段
			existObjectMap = dataImportMaps.getCurrCode();
		} else if (className.equalsIgnoreCase(CalUnit.class.getName())) {// 工厂单位
			existObjectMap = dataImportMaps.getCalUnit((Company) company);
		} /*
		 * else if (className.equalsIgnoreCase(Materiel.class.getName()))
		 * {//常用物料主档 existObjectMap = dataImportMaps.getMateriel((Company)
		 * company); }
		 */
		else if (className.equalsIgnoreCase(MaterielAndHalfProduct.class
				.getName())) {// 即是料件又是半成的品
			existObjectMap = dataImportMaps
					.getMaterielAndHalfProductMap((Company) company);
		} else {
			logger.info("在更新数据对象中只允许更新 仓库,客户供应商,币制,工厂单位,即是料件又是半成的品 !!");
			return;
		}

		try {
			Object obj = null;
			for (int i = 0; i < list.size(); i++) { // 修改
				DBFormatSetup dbFormatSetup = (DBFormatSetup) list.get(i);// 字段对照
				if (dbFormatSetup.getOrderNo() == null) {
					continue;
				}
				String destFiledName = dbFormatSetup.getAimFieldName();
				if ("code".equals(destFiledName.toLowerCase().trim())) {// 以字段code来断定该条记录是否已存在
					Object code = mDataMap.get(dbFormatSetup
							.getSourceFieldName()); // 获得值
					if (code == null || "".equals(code.toString().trim())) {
						code = "";
					}
					if (existObjectMap.get(code) != null) {
						obj = existObjectMap.get(code);
						break;
					}
				}
			}
			boolean isAdd = false;
			if (obj == null) { // 新增
				Class cls = Class.forName(className);
				obj = cls.newInstance();
				isAdd = true;
				newInsertCount++;
			}

			//
			// 存在重复的字段对照
			//
			Map<String, String> repeatFieldMap = new HashMap<String, String>();

			for (int i = 0; i < list.size(); i++) {
				Object objValue = null;
				DBFormatSetup dbFormatSetup = (DBFormatSetup) list.get(i);// 字段对照
				if (dbFormatSetup.getOrderNo() == null) {
					continue;
				}
				Object value = null;
				String destFiledName = dbFormatSetup.getAimFieldName();
				//
				// 以目的字段为key来获到多条件,并且是对象转换的类型
				//
				List aimNameReList = moreWhereMap.get(destFiledName);

				if (aimNameReList != null && aimNameReList.size() > 0) { // 存在重复的字段对照
					if (repeatFieldMap.get(dbFormatSetup.getAimFieldName()) == null) {
						objValue = getObjectValue(aimNameReList, mDataMap,
								ischange, company, hs, isBmTran);
						repeatFieldMap.put(dbFormatSetup.getAimFieldName(),
								dbFormatSetup.getAimFieldName());
					} else {
						continue;
					}
				} else {
					value = mDataMap.get(dbFormatSetup.getSourceFieldName()); // 获得值
					if (isBmTran && value != null && value instanceof String) {// 编码转换
						value = new String(value.toString().getBytes("GBK"),
								"Big5");
					}
					if (value != null && (value instanceof String) && ischange) {
						value = changeStr(value.toString()).trim(); // 繁简转换
					}
					objValue = getDBObjValue(dbFormatSetup, value, company);
				}
				BeanUtils.setProperty(obj, dbFormatSetup.getAimFieldName(),
						objValue);
			}
			dataImportDao.getHibernateTemplate().saveOrUpdate(obj);

			if (isAdd) {
				Object value = PropertyUtils.getNestedProperty(obj, "code");
				existObjectMap.put(value == null ? "" : value.toString(), obj);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量更新进出口申请单据表头
	 * 
	 * @param company
	 * @param deal
	 * @param dataImportMaps
	 * @param ischange
	 * @param className
	 * @param list
	 * @param moreWhereMap
	 * @param rs
	 * @param lsField
	 * @throws SQLException
	 */
	private void batchUpdateImpExpRequestBill(BaseCompany company, String deal,
			DataImportMaps dataImportMaps, boolean ischange, String className,
			List list, Map<String, List<DBFormatSetup>> moreWhereMap,
			ResultSet rs, List<String> lsField, Hashtable hs, boolean isBmTran)
			throws SQLException {
		//
		// 批量更新单据
		//
		List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();

		while (rs.next()) {
			Map<String, Object> mDataMap = new HashMap<String, Object>();
			this.getDataMap(rs, lsField, mDataMap);
			batchList.add(mDataMap);
			//
			// 100 条执行一次
			//
			if (batchList.size() % 100 == 0) {
				try {
					this.updateImpExpRequestBill(className, list, batchList,
							company, ischange, moreWhereMap, dataImportMaps,
							hs, isBmTran);
				} catch (Exception e) {
					e.printStackTrace();
					if (deal.equals("终止")) {
						throw new RuntimeException(
								"[JBCUS DATA IMPORT]  Save Error：" + e);
					}
				}
				//
				// 清除修改过的批
				//
				batchList.clear();
			}
		}
		//
		// 最后一次执行剩余的 batchList
		//
		try {
			this.updateImpExpRequestBill(className, list, batchList, company,
					ischange, moreWhereMap, dataImportMaps, hs, isBmTran);
		} catch (Exception e) {
			e.printStackTrace();
			if (deal.equals("终止")) {
				throw new RuntimeException("[JBCUS DATA IMPORT]  Save Error："
						+ e);
			}
		}
	}

	/**
	 * 更新保存(CAS 单据表头更新 特别做的 )
	 * 
	 * @param className
	 * @param list
	 * @param rs
	 * @param company
	 * @param ischange
	 */
	private void updateImpExpRequestBill(String className, List list,
			final List<Map<String, Object>> batchList, BaseCompany company,
			boolean ischange, Map<String, List<DBFormatSetup>> moreWhereMap,
			DataImportMaps dataImportMaps, Hashtable hs, boolean isBmTran) {
		if (batchList.size() <= 0) {
			return;
		}
		className = className.trim();
		//
		// 单据表头的唯一性 = 单据号
		//
		try {
			//
			// 所有条件的值
			//
			List<String> whereList = new ArrayList<String>();
			Map<Integer, String> keyMap = new HashMap<Integer, String>();

			for (int i = 0, n = batchList.size(); i < n; i++) { // 修改
				//
				// 获得一个临时的值(Map) 它包函一行数值与字段的对应
				//
				Map<String, Object> tempDataMap = batchList.get(i);
				String billNo = "";
				for (int j = 0; j < list.size(); j++) { // 修改
					DBFormatSetup dbFormatSetup = (DBFormatSetup) list.get(j);// 字段对照
					if (dbFormatSetup.getOrderNo() == null) {
						continue;
					}
					String destFiledName = dbFormatSetup.getAimFieldName()
							.trim();
					if ("billNo".equals(destFiledName)) {// 以字段 billNo
						// 来断定该条记录是否已存在
						Object tempBillNo = tempDataMap.get(dbFormatSetup
								.getSourceFieldName()); // 获得值
						billNo = tempBillNo == null ? "" : tempBillNo
								.toString();
						if (billNo.equals("")) {
							logger.info("导入的申请单数据有单据号为空的记录");
						}
						break;
					}
				}
				keyMap.put(i, billNo);
				whereList.add(billNo);
			}
			//
			// 查询已存在的单据头
			//
			List<ImpExpRequestBill> existImpExpRequestBill = this.dataImportDao
					.findImpExpRequestBillByWhere(className, whereList,
							(Company) company);
			//
			// key = 单据号
			//
			Map<String, ImpExpRequestBill> existImpExpRequestBillMap = new HashMap<String, ImpExpRequestBill>();
			for (int i = 0, n = existImpExpRequestBill.size(); i < n; i++) {
				ImpExpRequestBill impExpRequestBill = existImpExpRequestBill
						.get(i);
				String key = impExpRequestBill.getBillNo();
				existImpExpRequestBillMap.put(key, impExpRequestBill);
			}
			//
			// 进行批量保存
			//
			List<Object> saveBatchList = new ArrayList<Object>();

			for (int i = 0, n = batchList.size(); i < n; i++) { // 修改
				try {
					//
					// 获得一个临时的值(Map) 它包函一行数值与字段的对应
					//
					Map<String, Object> tempDataMap = batchList.get(i);

					String key = keyMap.get(i);
					//
					// 进行修改或新增
					//
					Object obj = existImpExpRequestBillMap.get(key);
					//
					// 如果数据库中不存在就 新增
					//
					if (obj == null) {
						Class cls = Class.forName(className);
						obj = cls.newInstance();
					}
					//
					// filter 已存在的
					//
					else {
						logger.info("已存在的单据不进行保存 单据号为 == " + key);
						continue;
					}

					// //////////
					// 进行保存
					// //////////

					//
					// 存在重复的字段对照
					//
					Map<String, String> repeatFieldMap = new HashMap<String, String>();

					for (int j = 0; j < list.size(); j++) {
						Object objValue = null;
						DBFormatSetup dbFormatSetup = (DBFormatSetup) list
								.get(j);// 字段对照
						if (dbFormatSetup.getOrderNo() == null) {
							continue;
						}
						Object value = null;
						String destFiledName = dbFormatSetup.getAimFieldName();
						//
						// 以目的字段为key来获到多条件,并且是对象转换的类型
						//
						List aimNameReList = moreWhereMap.get(destFiledName);

						if (aimNameReList != null && aimNameReList.size() > 0) { // 存在重复的字段对照
							if (repeatFieldMap.get(dbFormatSetup
									.getAimFieldName()) == null) {
								objValue = getCasObjectValue(aimNameReList,
										tempDataMap, ischange, company,
										dataImportMaps, hs, isBmTran);
								repeatFieldMap.put(
										dbFormatSetup.getAimFieldName(),
										dbFormatSetup.getAimFieldName());
							} else {
								continue;
							}
						} else {
							value = tempDataMap.get(dbFormatSetup
									.getSourceFieldName()); // 获得值
							if (value != null && (value instanceof String)
									&& ischange) {
								value = changeStr(value.toString()).trim(); // 繁简转换
							}
							objValue = getDBObjValue(dbFormatSetup, value,
									company);
						}
						BeanUtils.setProperty(obj,
								dbFormatSetup.getAimFieldName(), objValue);
					}
					saveBatchList.add(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//
			// 批量保存
			//
			dataImportDao.batchSaveOrUpdate(saveBatchList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量更新CAS单据表体
	 * 
	 * @param company
	 * @param deal
	 * @param dataImportMaps
	 * @param ischange
	 * @param className
	 * @param list
	 * @param moreWhereMap
	 * @param rs
	 * @param lsField
	 * @throws SQLException
	 */
	private void batchUpdateImpExpCommodityInfo(BaseCompany company,
			String deal, DataImportMaps dataImportMaps, boolean ischange,
			String className, List list,
			Map<String, List<DBFormatSetup>> moreWhereMap, ResultSet rs,
			List<String> lsField, Hashtable hs, boolean isBmTran)
			throws SQLException {
		//
		// 批量更新单据
		//
		List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();

		while (rs.next()) {
			Map<String, Object> mDataMap = new HashMap<String, Object>();
			this.getDataMap(rs, lsField, mDataMap);
			batchList.add(mDataMap);
			//
			// 100 条执行一次
			//
			if (batchList.size() % 100 == 0) {
				try {
					this.updateImpExpCommodityInfo(className, list, batchList,
							company, ischange, moreWhereMap, dataImportMaps,
							hs, isBmTran);
				} catch (Exception e) {
					e.printStackTrace();
					if (deal.equals("终止")) {
						throw new RuntimeException(
								"[JBCUS DATA IMPORT]  Save Error：" + e);
					}
				}
				//
				// 清除修改过的批
				//
				batchList.clear();
			}
		}
		//
		// 最后一次执行剩余的 batchList
		//
		try {
			this.updateImpExpCommodityInfo(className, list, batchList, company,
					ischange, moreWhereMap, dataImportMaps, hs, isBmTran);
		} catch (Exception e) {
			e.printStackTrace();
			if (deal.equals("终止")) {
				throw new RuntimeException("[JBCUS DATA IMPORT]  Save Error："
						+ e);
			}
		}
	}

	/**
	 * 更新保存(CAS 单据表头更新 特别做的 )
	 * 
	 * @param className
	 * @param list
	 * @param rs
	 * @param company
	 * @param ischange
	 */
	private void updateImpExpCommodityInfo(String className, List list,
			final List<Map<String, Object>> batchList, BaseCompany company,
			boolean ischange, Map<String, List<DBFormatSetup>> moreWhereMap,
			DataImportMaps dataImportMaps, Hashtable hs, boolean isBmTran) {
		if (batchList.size() <= 0) {
			return;
		}
		className = className.trim();
		// //////////////////////////////////////////////
		// 单据表体更新
		// 1.存在的不再进行导入
		// /////////////////////////////////////////////
		try {

			List<DBFormatSetup> billMasterAimNameReList = moreWhereMap
					.get("impExpRequestBill");
			if (billMasterAimNameReList == null) {
				logger.info("对不起，进出口申请单表头 都没有对应！");
			}

			List<DBFormatSetup> ptNoDBFormatSetupList = moreWhereMap
					.get("materiel");
			if (ptNoDBFormatSetupList == null) {
				logger.info("进出口申请单 [料号] 没有对应");
			}

			//
			// 所有条件的值
			//
			Map<String, TempImpExpRequestBillKey> tempBillMasterKeyMap = new HashMap<String, TempImpExpRequestBillKey>();
			Map<Integer, String> keyMap = new HashMap<Integer, String>();

			for (int i = 0, n = batchList.size(); i < n; i++) { // 修改
				TempImpExpRequestBillKey temp = new TempImpExpRequestBillKey();
				//
				// 获得一个临时的值(Map) 它包函一行数值与字段的对应
				//
				Map<String, Object> tempDataMap = batchList.get(i);

				String key = "";
				//
				// 申请单表头
				//
				for (int j = 0; j < billMasterAimNameReList.size(); j++) { // 修改
					DBFormatSetup dbFormatSetup = (DBFormatSetup) billMasterAimNameReList
							.get(j);// 字段对照
					String destFiledName = dbFormatSetup.getGbStr()
							.getFieldname().trim();
					if ("billNo".equals(destFiledName)) {// 以字段 billNo
						// 来断定该条记录是否已存在
						Object billNo = tempDataMap.get(dbFormatSetup
								.getSourceFieldName()); // 获得值
						temp.setBillNo(billNo == null ? "" : billNo.toString());
						if (billNo == null) {
							logger.info("导入的数据有单据号为空的记录");
						}
						key += billNo.toString();
					}
				}

				//
				// 料号条件
				//
				DBFormatSetup ptNoDBFormatSetup = ptNoDBFormatSetupList.get(0);

				// 来断定该条记录是否已存在
				Object ptNo = tempDataMap.get(ptNoDBFormatSetup
						.getSourceFieldName()); // 获得值
				temp.setPtNo(ptNo == null ? "" : ptNo.toString());
				if (ptNo == null) {
					logger.info("导入的数据有料号为空的记录");
				}
				key += ptNo.toString();

				if (tempBillMasterKeyMap.get(key) == null) {
					tempBillMasterKeyMap.put(key, temp);
				}
				keyMap.put(i, key);
			}
			//
			// 所有条件的值
			//
			List<TempImpExpRequestBillKey> whereList = new ArrayList<TempImpExpRequestBillKey>();
			whereList.addAll(tempBillMasterKeyMap.values());
			//
			// 获取存在的表体记录
			//
			List<ImpExpCommodityInfo> existImpExpCommodityInfo = this.dataImportDao
					.findImpExpCommodityInfoByWhere(className, whereList,
							(Company) company);
			//
			// key = 单据号 + ptNo
			//
			Map<String, ImpExpCommodityInfo> existImpExpCommodityInfoMap = new HashMap<String, ImpExpCommodityInfo>();
			for (int i = 0, n = existImpExpCommodityInfo.size(); i < n; i++) {
				ImpExpCommodityInfo impExpCommodityInfo = existImpExpCommodityInfo
						.get(i);
				String key = impExpCommodityInfo.getImpExpRequestBill()
						.getBillNo();
				key += impExpCommodityInfo.getMateriel().getPtNo();
				existImpExpCommodityInfoMap.put(key, impExpCommodityInfo);
			}

			//
			// 进行批量保存
			//
			List<Object> saveBatchList = new ArrayList<Object>();

			for (int i = 0, n = batchList.size(); i < n; i++) { // 修改
				try {
					//
					// 获得一个临时的值(Map) 它包函一行数值与字段的对应
					//
					Map<String, Object> tempDataMap = batchList.get(i);
					//
					// 进行修改或新增
					//
					Object obj = existImpExpCommodityInfoMap.get(keyMap.get(i));
					//
					// 如果数据库中不存在就 新增
					//
					if (obj == null) {
						Class cls = Class.forName(className);
						obj = cls.newInstance();
					}
					//
					// filter 已存在的
					//
					else {
						ImpExpCommodityInfo temp = (ImpExpCommodityInfo) obj;
						logger.info("已存在的申请单据明细不进行保存  单据号== "
								+ temp.getImpExpRequestBill().getBillNo()
								+ "  料号 === " + temp.getMateriel().getPtNo());
						continue;
					}

					// //////////
					// 进行保存
					// //////////

					//
					// 存在重复的字段对照
					//
					Map<String, String> repeatFieldMap = new HashMap<String, String>();

					for (int j = 0; j < list.size(); j++) {
						Object objValue = null;
						DBFormatSetup dbFormatSetup = (DBFormatSetup) list
								.get(j);// 字段对照
						if (dbFormatSetup.getOrderNo() == null) {
							continue;
						}
						Object value = null;
						String destFiledName = dbFormatSetup.getAimFieldName();
						//
						// 以目的字段为key来获到多条件,并且是对象转换的类型
						//
						List aimNameReList = moreWhereMap.get(destFiledName);

						if (aimNameReList != null && aimNameReList.size() > 0) { // 存在重复的字段对照
							if (repeatFieldMap.get(dbFormatSetup
									.getAimFieldName()) == null) {
								objValue = getCasObjectValue(aimNameReList,
										tempDataMap, ischange, company,
										dataImportMaps, hs, isBmTran);
								repeatFieldMap.put(
										dbFormatSetup.getAimFieldName(),
										dbFormatSetup.getAimFieldName());
							} else {
								continue;
							}
						} else {
							value = tempDataMap.get(dbFormatSetup
									.getSourceFieldName()); // 获得值
							if (value != null && (value instanceof String)
									&& ischange) {
								value = changeStr(value.toString()).trim(); // 繁简转换
							}
							objValue = getDBObjValue(dbFormatSetup, value,
									company);
						}
						BeanUtils.setProperty(obj,
								dbFormatSetup.getAimFieldName(), objValue);
					}
					saveBatchList.add(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//
			// 批量保存
			//
			dataImportDao.batchSaveOrUpdate(saveBatchList);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 批量更新CAS单据表体
	 * 
	 * @param company
	 * @param deal
	 * @param dataImportMaps
	 * @param ischange
	 * @param className
	 * @param list
	 * @param moreWhereMap
	 * @param rs
	 * @param lsField
	 * @throws SQLException
	 */
	private void batchUpdateBillMaster(BaseCompany company, String deal,
			DataImportMaps dataImportMaps, boolean ischange, String className,
			List list, Map<String, List<DBFormatSetup>> moreWhereMap,
			ResultSet rs, List<String> lsField, Hashtable hs, boolean isBmTran)
			throws SQLException {
		//
		// 批量更新单据
		//
		List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();

		while (rs.next()) {
			Map<String, Object> mDataMap = new HashMap<String, Object>();
			this.getDataMap(rs, lsField, mDataMap);
			batchList.add(mDataMap);
			//
			// 100 条执行一次
			//
			if (batchList.size() % 100 == 0) {
				try {
					this.updateCasBillMaster(className, list, batchList,
							company, ischange, moreWhereMap, dataImportMaps,
							hs, isBmTran);
				} catch (Exception e) {
					e.printStackTrace();
					if (deal.equals("终止")) {
						throw new RuntimeException(
								"[JBCUS DATA IMPORT]  Save Error：" + e);
					}
				}
				//
				// 清除修改过的批
				//
				batchList.clear();
			}
		}
		//
		// 最后一次执行剩余的 batchList
		//
		try {
			this.updateCasBillMaster(className, list, batchList, company,
					ischange, moreWhereMap, dataImportMaps, hs, isBmTran);
		} catch (Exception e) {
			e.printStackTrace();
			if (deal.equals("终止")) {
				throw new RuntimeException("[JBCUS DATA IMPORT]  Save Error："
						+ e);
			}
		}
	}

	/**
	 * 更新保存(CAS 单据表头更新 特别做的 )
	 * 
	 * @param className
	 * @param list
	 * @param rs
	 * @param company
	 * @param ischange
	 */
	private void updateCasBillMaster(String className, List list,
			final List<Map<String, Object>> batchList, BaseCompany company,
			boolean ischange, Map<String, List<DBFormatSetup>> moreWhereMap,
			DataImportMaps dataImportMaps, Hashtable hs, boolean isBmTran) {
		if (batchList.size() <= 0) {
			return;
		}
		className = className.trim();
		//
		// 单据表头的唯一性 = 单据类型号 + 单据号 + 日期
		//
		try {
			//
			// 所有条件的值
			//
			List<TempBillMasterKey> whereList = new ArrayList<TempBillMasterKey>();
			Map<Integer, String> keyMap = new HashMap<Integer, String>();

			for (int i = 0, n = batchList.size(); i < n; i++) { // 修改
				TempBillMasterKey temp = new TempBillMasterKey();
				//
				// 获得一个临时的值(Map) 它包函一行数值与字段的对应
				//
				Map<String, Object> tempDataMap = batchList.get(i);
				String key = "";
				for (int j = 0; j < list.size(); j++) { // 修改
					DBFormatSetup dbFormatSetup = (DBFormatSetup) list.get(j);// 字段对照
					if (dbFormatSetup.getOrderNo() == null) {
						continue;
					}
					String destFiledName = dbFormatSetup.getAimFieldName()
							.trim();
					if ("billType".equals(destFiledName)) {// 以字段 billType
						// 来断定该条记录是否已存在
						Object billType = tempDataMap.get(dbFormatSetup
								.getSourceFieldName()); // 获得值
						temp.setBillTypeCode(billType == null ? "" : billType
								.toString());
						if (billType == null) {
							logger.info("导入的数据有单据类型号为空的记录");
						}
						key += billType.toString();
					} else if ("billNo".equals(destFiledName)) {// 以字段 billNo
						// 来断定该条记录是否已存在
						Object billNo = tempDataMap.get(dbFormatSetup
								.getSourceFieldName()); // 获得值
						temp.setBillNo(billNo == null ? "" : billNo.toString());
						if (billNo == null) {
							logger.info("导入的数据有单据号为空的记录");
						}
						key += billNo.toString();
					} else if ("validDate".equals(destFiledName)) {// 以字段
						// validDate
						// 来断定该条记录是否已存在
						Object validDate = tempDataMap.get(dbFormatSetup
								.getSourceFieldName()); // 获得值
						if (validDate == null) {
							logger.info("导入的数据有日期为空的记录");
						}
						// logger.info(validDate.toString());
						temp.setValidDate((Date) validDate);
						SimpleDateFormat bartDateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");
						key += bartDateFormat.format(validDate);
					}
				}
				whereList.add(temp);
				keyMap.put(i, key);
			}
			//
			// 查询已存在的单据头
			//
			List<BillMaster> existBillMaster = this.dataImportDao
					.findBillMasterByWhere(className, whereList,
							(Company) company);
			//
			// key = 单据类型号 + 单据号 + 日期
			//
			Map<String, BillMaster> existBillMasterMap = new HashMap<String, BillMaster>();
			for (int i = 0, n = existBillMaster.size(); i < n; i++) {
				BillMaster billMaster = existBillMaster.get(i);
				String key = billMaster.getBillType().getCode();
				key += billMaster.getBillNo();
				//
				// "yyyy-MM-dd" 这样的格式,要注意
				//
				key += billMaster.getValidDate() == null ? "" : billMaster
						.getValidDate().toString();
				existBillMasterMap.put(key, billMaster);
			}

			//
			// 进行批量保存
			//
			List<Object> saveBatchList = new ArrayList<Object>();

			for (int i = 0, n = batchList.size(); i < n; i++) { // 修改
				try {
					//
					// 获得一个临时的值(Map) 它包函一行数值与字段的对应
					//
					Map<String, Object> tempDataMap = batchList.get(i);
					//
					// 进行修改或新增
					//
					Object obj = existBillMasterMap.get(keyMap.get(i));
					//
					// 如果数据库中不存在就 新增
					//
					if (obj == null) {
						Class cls = Class.forName(className);
						obj = cls.newInstance();
					}

					// //
					// // filter 已存在的
					// //
					// else{
					// continue;
					// }

					// //////////
					// 进行保存
					// //////////

					//
					// 存在重复的字段对照
					//
					Map<String, String> repeatFieldMap = new HashMap<String, String>();

					for (int j = 0; j < list.size(); j++) {
						Object objValue = null;
						DBFormatSetup dbFormatSetup = (DBFormatSetup) list
								.get(j);// 字段对照
						if (dbFormatSetup.getOrderNo() == null) {
							continue;
						}
						Object value = null;
						String destFiledName = dbFormatSetup.getAimFieldName();
						//
						// 以目的字段为key来获到多条件,并且是对象转换的类型
						//
						List aimNameReList = moreWhereMap.get(destFiledName);

						if (aimNameReList != null && aimNameReList.size() > 0) { // 存在重复的字段对照
							if (repeatFieldMap.get(dbFormatSetup
									.getAimFieldName()) == null) {
								objValue = getCasObjectValue(aimNameReList,
										tempDataMap, ischange, company,
										dataImportMaps, hs, isBmTran);
								repeatFieldMap.put(
										dbFormatSetup.getAimFieldName(),
										dbFormatSetup.getAimFieldName());
							} else {
								continue;
							}
						} else {
							value = tempDataMap.get(dbFormatSetup
									.getSourceFieldName()); // 获得值
							if (value != null && (value instanceof String)
									&& ischange) {
								value = changeStr(value.toString()).trim(); // 繁简转换
							}
							objValue = getDBObjValue(dbFormatSetup, value,
									company);
						}
						BeanUtils.setProperty(obj,
								dbFormatSetup.getAimFieldName(), objValue);
					}
					saveBatchList.add(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//
			// 批量保存
			//
			dataImportDao.batchSaveOrUpdate(saveBatchList);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 批量更新CAS单据表体
	 * 
	 * @param company
	 * @param deal
	 * @param dataImportMaps
	 * @param ischange
	 * @param className
	 * @param list
	 * @param moreWhereMap
	 * @param rs
	 * @param lsField
	 * @throws SQLException
	 */
	private void batchUpdateBillDetail(BaseCompany company, String deal,
			DataImportMaps dataImportMaps, boolean ischange, String className,
			List list, Map<String, List<DBFormatSetup>> moreWhereMap,
			ResultSet rs, List<String> lsField, Hashtable hs, boolean isBmTran)
			throws SQLException {
		//
		// 批量更新单据
		//
		List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();

		while (rs.next()) {
			Map<String, Object> mDataMap = new HashMap<String, Object>();
			this.getDataMap(rs, lsField, mDataMap);
			batchList.add(mDataMap);
			//
			// 100 条执行一次
			//
			if (batchList.size() % 100 == 0) {
				try {
					this.updateCasBillDetail(className, list, batchList,
							company, ischange, moreWhereMap, dataImportMaps,
							hs, isBmTran);
				} catch (Exception e) {
					e.printStackTrace();
					if (deal.equals("终止")) {
						throw new RuntimeException(
								"[JBCUS DATA IMPORT]  Save Error：" + e);
					}
				}
				//
				// 清除修改过的批
				//
				batchList.clear();
			}
		}
		//
		// 最后一次执行剩余的 batchList
		//
		try {
			this.updateCasBillDetail(className, list, batchList, company,
					ischange, moreWhereMap, dataImportMaps, hs, isBmTran);
		} catch (Exception e) {
			e.printStackTrace();
			if (deal.equals("终止")) {
				throw new RuntimeException("[JBCUS DATA IMPORT]  Save Error："
						+ e);
			}
		}
	}

	/**
	 * 更新保存(CAS 单据表头更新 特别做的 )
	 * 
	 * @param className
	 * @param list
	 * @param rs
	 * @param company
	 * @param ischange
	 */
	private void updateCasBillDetail(String className, List list,
			final List<Map<String, Object>> batchList, BaseCompany company,
			boolean ischange, Map<String, List<DBFormatSetup>> moreWhereMap,
			DataImportMaps dataImportMaps, Hashtable hs, boolean isBmTran) {
		if (batchList.size() <= 0) {
			return;
		}
		className = className.trim();
		// //////////////////////////////////////////////
		// 单据表体更新
		// 1.删除存在的表体数据
		// 2.新增新导入的表体数据
		// /////////////////////////////////////////////
		try {
			//
			// 从明细中找到 billMaster 对应的唯一条件
			//
			List<DBFormatSetup> billMasterAimNameReList = new ArrayList<DBFormatSetup>();
			String billMasterClassName = "";
			for (int j = 0; j < list.size(); j++) { // 修改
				DBFormatSetup dbFormatSetup = (DBFormatSetup) list.get(j);// 字段对照
				if (dbFormatSetup.getOrderNo() == null) {
					continue;
				}
				String destFiledName = dbFormatSetup.getAimFieldName().trim();
				if (destFiledName.equalsIgnoreCase("billMaster")) {
					billMasterClassName = dbFormatSetup.getObjName()
							.getClassPath();
					if (hs.get(billMasterClassName) != null) {
						className = (String) hs.get(billMasterClassName);
					}
					billMasterAimNameReList = moreWhereMap.get(dbFormatSetup
							.getAimFieldName());
					break;
				}
			}
			if (billMasterClassName.equalsIgnoreCase("")) {
				logger.info("对不起！,BillMaster没有对应");
				return;
			}
			//
			// 所有条件的值
			//
			Map<String, TempBillMasterKey> tempBillMasterKeyMap = new HashMap<String, TempBillMasterKey>();
			for (int i = 0, n = batchList.size(); i < n; i++) { // 修改
				TempBillMasterKey temp = new TempBillMasterKey();
				//
				// 获得一个临时的值(Map) 它包函一行数值与字段的对应
				//
				Map<String, Object> tempDataMap = batchList.get(i);

				String key = "";

				for (int j = 0; j < billMasterAimNameReList.size(); j++) { // 修改
					DBFormatSetup dbFormatSetup = (DBFormatSetup) billMasterAimNameReList
							.get(j);// 字段对照
					String destFiledName = dbFormatSetup.getGbStr()
							.getFieldname().trim();
					if ("billType".equals(destFiledName)) {// 以字段 billType
						// 来断定该条记录是否已存在
						Object billType = tempDataMap.get(dbFormatSetup
								.getSourceFieldName()); // 获得值
						temp.setBillTypeCode(billType == null ? "" : billType
								.toString());
						if (billType == null) {
							logger.info("导入的数据有单据类型号为空的记录");
						}
						key += billType.toString();
					} else if ("billNo".equals(destFiledName)) {// 以字段 billNo
						// 来断定该条记录是否已存在
						Object billNo = tempDataMap.get(dbFormatSetup
								.getSourceFieldName()); // 获得值
						temp.setBillNo(billNo == null ? "" : billNo.toString());
						if (billNo == null) {
							logger.info("导入的数据有单据号为空的记录");
						}
						key += billNo.toString();
					} else if ("validDate".equals(destFiledName)) {// 以字段
						// validDate
						// 来断定该条记录是否已存在
						Object validDate = tempDataMap.get(dbFormatSetup
								.getSourceFieldName()); // 获得值
						temp.setValidDate((Date) validDate);
						if (validDate == null) {
							logger.info("导入的数据有日期为空的记录");
						}
						key += validDate.toString();
					}
				}
				if (tempBillMasterKeyMap.get(key) == null) {
					tempBillMasterKeyMap.put(key, temp);
				}
			}
			//
			// 所有条件的值
			//
			List<TempBillMasterKey> whereList = new ArrayList<TempBillMasterKey>();
			whereList.addAll(tempBillMasterKeyMap.values());
			//
			// 批量删除所有存在这种头的表体记录
			//
			this.dataImportDao.batchDeleteBillDetailByWhere(className,
					whereList, billMasterAimNameReList, billMasterClassName,
					(Company) company);
			//
			// 进行批量保存
			//
			List<Object> saveBatchList = new ArrayList<Object>();

			for (int i = 0, n = batchList.size(); i < n; i++) { // 修改
				try {
					//
					// 获得一个临时的值(Map) 它包函一行数值与字段的对应
					//
					Map<String, Object> tempDataMap = batchList.get(i);
					//
					// 进行(删除后)新增
					//
					Class cls = Class.forName(className);
					Object obj = cls.newInstance();
					// //////////
					// 进行保存
					// //////////
					//
					// 存在重复的字段对照
					//
					Map<String, String> repeatFieldMap = new HashMap<String, String>();

					for (int j = 0; j < list.size(); j++) {
						Object objValue = null;
						DBFormatSetup dbFormatSetup = (DBFormatSetup) list
								.get(j);// 字段对照
						if (dbFormatSetup.getOrderNo() == null) {
							continue;
						}
						Object value = null;
						String destFiledName = dbFormatSetup.getAimFieldName();
						//
						// 以目的字段为key来获到多条件,并且是对象转换的类型
						//
						List aimNameReList = moreWhereMap.get(destFiledName);

						if (aimNameReList != null && aimNameReList.size() > 0) { // 存在重复的字段对照
							if (repeatFieldMap.get(dbFormatSetup
									.getAimFieldName()) == null) {
								objValue = getCasObjectValue(aimNameReList,
										tempDataMap, ischange, company,
										dataImportMaps, hs, isBmTran);
								repeatFieldMap.put(
										dbFormatSetup.getAimFieldName(),
										dbFormatSetup.getAimFieldName());
							} else {
								continue;
							}
						} else {
							value = tempDataMap.get(dbFormatSetup
									.getSourceFieldName()); // 获得值
							if (value != null && (value instanceof String)
									&& ischange) {
								value = changeStr(value.toString()).trim(); // 繁简转换
							}
							objValue = getDBObjValue(dbFormatSetup, value,
									company);
						}
						BeanUtils.setProperty(obj,
								dbFormatSetup.getAimFieldName(), objValue);
					}
					saveBatchList.add(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//
			// 批量保存
			//
			dataImportDao.batchSaveOrUpdate(saveBatchList);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 批量修改企业物料主档
	 * 
	 * @param company
	 * @param deal
	 * @param dataImportMaps
	 * @param ischange
	 * @param className
	 * @param list
	 * @param moreWhereMap
	 * @param rs
	 * @param lsField
	 * @throws SQLException
	 */
	private void batchUpdateEnterpriseMateriel(BaseCompany company,
			String deal, DataImportMaps dataImportMaps, boolean ischange,
			String className, List list,
			Map<String, List<DBFormatSetup>> moreWhereMap, ResultSet rs,
			List<String> lsField, Hashtable hs, boolean isBmTran)
			throws SQLException {
		//
		// 批量更新单据
		//
		List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();

		while (rs.next()) {
			Map<String, Object> mDataMap = new HashMap<String, Object>();
			this.getDataMap(rs, lsField, mDataMap);
			batchList.add(mDataMap);
			//
			// 100 条执行一次
			//
			if (batchList.size() % 100 == 0) {
				try {
					this.updateEnterpriseMateriel(className, list, batchList,
							company, ischange, moreWhereMap, dataImportMaps,
							hs, isBmTran);
				} catch (Exception e) {
					e.printStackTrace();
					if (deal.equals("终止")) {
						throw new RuntimeException(
								"[JBCUS DATA IMPORT]  Save Error：" + e);
					}
				}
				//
				// 清除修改过的批
				//
				batchList.clear();
			}
		}
		//
		// 最后一次执行剩余的 batchList
		//
		try {
			this.updateEnterpriseMateriel(className, list, batchList, company,
					ischange, moreWhereMap, dataImportMaps, hs, isBmTran);
		} catch (Exception e) {
			e.printStackTrace();
			if (deal.equals("终止")) {
				throw new RuntimeException("[JBCUS DATA IMPORT]  Save Error："
						+ e);
			}
		}
	}

	/**
	 * 批量修改内部归并
	 * 
	 * @param company
	 * @param deal
	 * @param dataImportMaps
	 * @param ischange
	 * @param className
	 * @param list
	 * @param moreWhereMap
	 * @param rs
	 * @param lsField
	 * @throws SQLException
	 */
	private void batchUpdateInnerMergeData(BaseCompany company, String deal,
			DataImportMaps dataImportMaps, boolean ischange, String className,
			List list, Map<String, List<DBFormatSetup>> moreWhereMap,
			ResultSet rs, List<String> lsField, Hashtable hs, boolean isBmTran)
			throws SQLException {

		// 加载内部归并
		existInnerMergeMap = dataImportMaps.getInnerMergedata();

		//
		// 批量更新单据
		//
		List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();

		while (rs.next()) {
			totalCount++;
			Map<String, Object> mDataMap = new HashMap<String, Object>();
			this.getDataMap(rs, lsField, mDataMap);
			batchList.add(mDataMap);
			if (batchList.size() % 100 == 0) {
				try {
					updateInnerMergeData(className, list, batchList, company,
							ischange, moreWhereMap, dataImportMaps, hs,
							isBmTran);
				} catch (Exception e) {
					e.printStackTrace();
					if (deal.equals("终止")) {
						throw new RuntimeException(
								"[JBCUS DATA IMPORT]  Save Error：" + e);
					}
				}
				//
				// 清除修改过的批
				//
				batchList.clear();
			}
		}

		//
		// 最后一次执行剩余的 batchList
		//
		try {
			this.updateInnerMergeData(className, list, batchList, company,
					ischange, moreWhereMap, dataImportMaps, hs, isBmTran);
		} catch (Exception e) {
			e.printStackTrace();
			if (deal.equals("终止")) {
				throw new RuntimeException("[JBCUS DATA IMPORT]  Save Error："
						+ e);
			}
		}
	}

	/**
	 * 更新修改企业物料主档
	 * 
	 * @param className
	 * @param list
	 * @param rs
	 * @param company
	 * @param ischange
	 */
	private void updateEnterpriseMateriel(String className, List list,
			final List<Map<String, Object>> batchList, BaseCompany company,
			boolean ischange, Map<String, List<DBFormatSetup>> moreWhereMap,
			DataImportMaps dataImportMaps, Hashtable hs, boolean isBmTran) {
		if (batchList.size() <= 0) {
			return;
		}
		className = className.trim();
		// //////////////////////////////////////////////
		// 单据表体更新
		// 1.删除存在的表体数据
		// 2.新增新导入的表体数据
		// /////////////////////////////////////////////
		try {
			String sourceFiledByPtNo = "";
			for (int j = 0; j < list.size(); j++) { // 修改
				DBFormatSetup dbFormatSetup = (DBFormatSetup) list.get(j);// 字段对照
				String destFiledName = dbFormatSetup.getAimFieldName().trim();
				if ("ptNo".equals(destFiledName)) {// 以字段 ptNo
					// 来断定该条记录是否已存在
					sourceFiledByPtNo = dbFormatSetup.getSourceFieldName(); // 获得值
					if (sourceFiledByPtNo == null
							|| sourceFiledByPtNo.trim().equals("")) {
						logger.info("导入的数据中料号字段没有对应,不能导入 !! ");
						return;
					}
					break;
				}
			}
			//
			// 所有条件的值
			//
			Map<String, String> ptNoKeyMap = new HashMap<String, String>();
			for (int i = 0, n = batchList.size(); i < n; i++) { // 修改
				//
				// 获得一个临时的值(Map) 它包函一行数值与字段的对应
				//
				Map<String, Object> tempDataMap = batchList.get(i);

				Object ptNo = tempDataMap.get(sourceFiledByPtNo); // 获得值
				if (ptNo == null) {
					logger.info("导入的数据中料号有为空的记录");
					continue;
				}
				String key = ptNo.toString().trim();
				if (ptNoKeyMap.get(key) == null) {
					ptNoKeyMap.put(key, key);
				}
			}
			//
			// 所有条件的值
			//
			List<String> ptNoList = new ArrayList<String>();
			ptNoList.addAll(ptNoKeyMap.values());
			//
			// 批量删除所有存在的PtNo记录
			//
			this.dataImportDao.batchDeleteEnterpriseMatereiel(className,
					ptNoList, (Company) company);
			//
			// 进行批量保存
			//
			List<Object> saveBatchList = new ArrayList<Object>();

			for (int i = 0, n = batchList.size(); i < n; i++) { // 修改
				try {
					//
					// 获得一个临时的值(Map) 它包函一行数值与字段的对应
					//
					Map<String, Object> tempDataMap = batchList.get(i);
					//
					// 进行(删除后)新增
					//
					Class cls = Class.forName(className);
					Object obj = cls.newInstance();
					// //////////
					// 进行保存
					// //////////
					//
					// 存在重复的字段对照
					//
					Map<String, String> repeatFieldMap = new HashMap<String, String>();

					for (int j = 0; j < list.size(); j++) {
						Object objValue = null;
						DBFormatSetup dbFormatSetup = (DBFormatSetup) list
								.get(j);// 字段对照
						if (dbFormatSetup.getOrderNo() == null) {
							continue;
						}
						Object value = null;
						String destFiledName = dbFormatSetup.getAimFieldName();
						//
						// 以目的字段为key来获到多条件,并且是对象转换的类型
						//
						List aimNameReList = moreWhereMap.get(destFiledName);

						if (aimNameReList != null && aimNameReList.size() > 0) { // 存在重复的字段对照
							if (repeatFieldMap.get(dbFormatSetup
									.getAimFieldName()) == null) {
								objValue = getCasObjectValue(aimNameReList,
										tempDataMap, ischange, company,
										dataImportMaps, hs, isBmTran);
								repeatFieldMap.put(
										dbFormatSetup.getAimFieldName(),
										dbFormatSetup.getAimFieldName());
							} else {
								continue;
							}
						} else {
							value = tempDataMap.get(dbFormatSetup
									.getSourceFieldName()); // 获得值
							if (value != null && (value instanceof String)
									&& ischange) {
								value = changeStr(value.toString()).trim(); // 繁简转换
							}
							objValue = getDBObjValue(dbFormatSetup, value,
									company);
						}
						BeanUtils.setProperty(obj,
								dbFormatSetup.getAimFieldName(), objValue);
					}
					saveBatchList.add(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//
			// 批量保存
			//
			dataImportDao.batchSaveOrUpdate(saveBatchList);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 特别针对Cas 单据进行的优化
	 * 
	 * @param aimNameRe
	 * @param mDataMap
	 * @param ischange
	 * @param company
	 * @return
	 */
	private Object getCasObjectValue(List aimNameRe,
			final Map<String, Object> mDataMap, boolean ischange,
			BaseCompany company, DataImportMaps dataImportMaps, Hashtable hs,
			boolean isBmTran) {

		if (aimNameRe.size() == 1) {
			DBFormatSetup dbFormatSetup = (DBFormatSetup) aimNameRe.get(0);
			String className = dbFormatSetup.getObjName().getClassPath();
			if (hs.get(className) != null) {
				className = (String) hs.get(className);
			}
			//
			// key = code 这是已存在的对象
			//
			Object code = mDataMap.get(dbFormatSetup.getSourceFieldName());

			if (code == null || "".equals(code.toString().trim())) {
				// logger.info("导入的视图中有空的记录!!请检查视图");
				return null;
			}

			String aimFieldName = dbFormatSetup.getGbStr() == null ? ""
					: dbFormatSetup.getGbStr().getFieldname();

			if (aimFieldName.toLowerCase().equals("code")) {

				if (className.equalsIgnoreCase(WareSet.class.getName())) {// 仓库
					Map<String, Object> wareSetMap = dataImportMaps
							.getWareSet((Company) company);

					Object value = wareSetMap.get(code);
					if (value == null) { // 如果不存在自动新增一条
						WareSet wareSet = new WareSet();
						wareSet.setCode(code.toString());
						wareSet.setCompany(company);
						this.dataImportDao.saveObject(wareSet);
						wareSetMap.put(code.toString(), wareSet);
						value = wareSet;
					}
					return value;
				} else if (className.equalsIgnoreCase(ScmCoc.class.getName())) {// 客户供应商
					Map<String, Object> scmCocMap = dataImportMaps
							.getScmCoc((Company) company);
					Object value = scmCocMap.get(code);
					if (value == null) { // 如果不存在自动新增一条
						ScmCoc scmCoc = new ScmCoc();
						scmCoc.setCode(code.toString());
						scmCoc.setCompany(company);
						this.dataImportDao.saveObject(scmCoc);
						scmCocMap.put(code.toString(), scmCoc);
						value = scmCoc;
					}
					return value;
				} else if (className.equalsIgnoreCase(CurrCode.class.getName())) {// 币制
					// 这个没有公司// ID 字段
					Map<String, Object> currMap = dataImportMaps.getCurrCode();
					Object value = currMap.get(code);
					if (value == null) { // 如果不存在自动新增一条
						Curr curr = new Curr();
						curr.setCode(code.toString());
						this.dataImportDao.saveObject(curr);
						currMap.put(code.toString(), curr);
						value = curr;
					}
					return value;
				} else if (className.equalsIgnoreCase(CalUnit.class.getName())) {// 工厂单位
					Map<String, Object> calUnitMap = dataImportMaps
							.getCalUnit((Company) company);
					Object value = calUnitMap.get(code);
					if (value == null) { // 如果不存在自动新增一条
						CalUnit calUnit = new CalUnit();
						calUnit.setCode(code.toString());
						calUnit.setCompany(company);
						this.dataImportDao.saveObject(calUnit);
						calUnitMap.put(code.toString(), calUnit);
						value = calUnit;
					}
					return value;
				} else if (className.equalsIgnoreCase(Unit.class.getName())) {// 海关单位
					Map<String, Object> unitMap = dataImportMaps.getUnit();
					Object value = unitMap.get(code);
					if (value == null) { // 如果不存在自动新增一条
						Unit unit = new Unit();
						unit.setCode(code.toString());
						this.dataImportDao.saveObject(unit);
						unitMap.put(code.toString(), unit);
						value = unit;
					}
					return value;
				}
			}
			//
			// 重新到数据库中进行查询获得对象
			//
			return getObjectValue(aimNameRe, mDataMap, ischange, company, hs,
					isBmTran);
		} else {
			return getObjectValue(aimNameRe, mDataMap, ischange, company, hs,
					isBmTran);
		}
	}

	private Object getObjectValue(List aimNameRe,
			final Map<String, Object> mDataMap, boolean ischange,
			BaseCompany company, Hashtable hs, boolean isBmTran) {
		List<Object> paramters = new ArrayList<Object>();
		String classtable = "";
		String sql = "";
		Boolean isCoid = null;
		for (int i = 0; i < aimNameRe.size(); i++) {
			DBFormatSetup dbFormatSetup = (DBFormatSetup) aimNameRe.get(i);
			classtable = dbFormatSetup.getObjName().getClassPath();
			if (hs.get(classtable) != null) {
				classtable = (String) hs.get(classtable);
			}

			isCoid = dbFormatSetup.getObjName().getIsCoid();
			if (i == 0) {
				sql += "from " + classtable + " a where (1=1)";
			}
			if (dbFormatSetup.getGbStrChild() == null) {
				sql += " and a." + dbFormatSetup.getGbStr().getFieldname()
						+ "=?";
			} else {
				sql += " and a." + dbFormatSetup.getGbStr().getFieldname()
						+ "." + dbFormatSetup.getGbStrChild().getFieldname()
						+ "=?";
			}
			//
			// sourceFieldName == key
			//
			Object value = mDataMap.get(dbFormatSetup.getSourceFieldName()); // 获得值
			// if (value != null && value instanceof String && isBmTran){ //编码转换
			// try {
			// value = new String(value.toString().getBytes("GBK"),"Big5");
			// } catch (UnsupportedEncodingException e) {
			//
			// }
			// }
			if (value != null && value instanceof String && ischange) {
				value = changeStr(value.toString()); // 繁简转换
			}
			paramters.add(value);
		}
		if (isCoid.equals(new Boolean(true))) {
			sql += " and a.company.id=?";
			paramters.add(company.getId());
		}
		return dataImportDao.findObjectByPara(sql, paramters);
	}

	// 得到对象值
	private Object getDBObjValue(DBFormatSetup dbFormatSetup, Object value,
			BaseCompany company) {
		Object obj1 = null;
		if (dbFormatSetup.getGbflag().equals(Gbflag.NEW_COID)) { // 当前公司id
			obj1 = company;
		} else if (dbFormatSetup.getGbflag() == null
				|| dbFormatSetup.getGbflag().equals("")
				|| dbFormatSetup.getGbflag().equals(Gbflag.NO)) { // 无
			if (dbFormatSetup.getAimFieldType().equals("Date")) { // 日期转换
				try {
					obj1 = getDateByString(value);
				} catch (Exception e) {
					obj1 = value;
				}
			} else {
				obj1 = value;
			}
		} else if (dbFormatSetup.getGbflag().equals(Gbflag.SEQNUM)) { // 序号/或流水号

			obj1 = seqNum;
		}
		return obj1;
	}

	private static Date getDateByString(Object obj1) {
		if (obj1 == null || obj1.equals("")) {
			return null;
		}
		String obj = obj1.toString();
		String dates = obj + " 00:00:00";
		return strToStandDate(dates);
	}

	// 日期转换
	public static Date strToStandDate(String input) {
		if (input == null || input.equals("0") || input.length() == 0) {
			return null;
		}
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			return (Date) dateFormat.parse(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public String execuSql(DBDataExecuSql obj) throws SQLException {
		// System.out.println("logic execuSql");
		Connection conn = dataSource.getConnection();
		// System.out.println("isClose = " + conn.isClosed());
		String res = execuSql(obj, CommonUtils.getCompany(), conn);
		// System.out.println("isClose = " + conn.isClosed());
		// System.out.println(res);
		return res;
	}

	public void execuFileSql(String sql) throws SQLException {
		execuFileSql(sql, CommonUtils.getCompany(), dataSource.getConnection());
	}

	public String execuSql(DBDataExecuSql obj, Company company)
			throws SQLException {
		return execuSql(obj, company, dataSource.getConnection());
	}

	public String execuSql(DBDataExecuSql obj, BaseCompany company,
			Connection conn) {
		try {
			String sql = obj.getSqlStr();// 语句
			DBDataRoot dataRoot = obj.getDataRoot();// 数据源

			if (dataRoot != null) {// 连接数据源
				// 2011-8-16 chl 先关掉 conn, 否则资源将耗尽
				close(conn);
				conn = getConn(dataRoot);
			}
			logger.info("[JBCUS DATA IMPORT]  开始执行事件 事件名 == " + obj.getName());

			String[] tempArray = sql.split("--end");
			for (int i = 0; i < tempArray.length; i++) {
				String hsql = tempArray[i];
				try {
					long beginTime = System.currentTimeMillis();
					logger.info("[JBCUS DATA IMPORT]  SQL == " + hsql);
					hsql = formatSql(hsql, company);
					Statement stmt = conn.createStatement();
					stmt.execute(hsql);
					double m = (System.currentTimeMillis() - beginTime) / 1000.0;
					logger.info("[JBCUS DATA IMPORT]  事件名 == " + obj.getName()
							+ " 共用 == " + m + " S ");

					stmt.close();
				} catch (Exception ex) {
					return "执行失败：\n" + ex.getMessage();
				}
			}
			logger.info("[JBCUS DATA IMPORT]  结束执行事件 事件名 == " + obj.getName());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导入数据出错!", e);
			throw new RuntimeException("导入数据出错!", e);
		} finally {
			close(conn);
		}
		return "执行成功！";
	}

	/**
	 * 执行文件
	 * 
	 * @param dbFormat
	 * @return
	 */
	public void execuFileSql(String sql, BaseCompany company, Connection conn)
			throws SQLException {
		try {
			sql = formatSql(sql, company);
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("执行文件出错！", ex);
			throw new SQLException("执行文件出错！", ex);
		} finally {
			close(conn);
		}
	}

	public List getSourceFields(DBFormat dbFormat) {
		List<BillTemp> list = new Vector<BillTemp>();
		String sql = dbFormat.getDbView().getSqlScript();
		DBDataRoot dbroot = dbFormat.getDbView().getDb();
		Connection conn = getConn(dbroot);
		try {
			Statement stmt = conn.createStatement();
			ResultSet readDataPart = stmt.executeQuery(getSqlAddWhere(sql));
			ResultSetMetaData rs = readDataPart.getMetaData();
			int columnCount = rs.getColumnCount();
			for (int j = 1; j <= columnCount; j++) {
				BillTemp temp = new BillTemp();
				//
				// 注意要与 intoData 保持一致
				//
				// temp.setBill1(rs.getColumnName(j));
				temp.setBill1(rs.getColumnLabel(j));
				temp.setBill2(rs.getColumnTypeName(j));
				temp.setBill3(String.valueOf(rs.getColumnDisplaySize(j)));
				temp.setBill4(String.valueOf(j));
				list.add(temp);
			}
			readDataPart.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}
		return list;
	}

	private static String getSqlAddWhere(String sql) {
		//
		// 要注意顺序 As400不支持（where (1=2) 语句）
		//
		int indexByWhere = sql.toLowerCase().indexOf("where");
		int indexByGroup = sql.toLowerCase().indexOf("group");
		int indexByOrder = sql.toLowerCase().indexOf("order");
		int indexByLimit = sql.toLowerCase().indexOf("limit");// mysql 用到

		if (indexByWhere > -1) {

			sql = sql.substring(0, indexByWhere) + " where (1=2) ";

		} else if (indexByGroup > -1 || indexByOrder > -1) {

			if (indexByGroup > -1 && indexByOrder > -1) {

				sql = sql.substring(0, indexByGroup) + " where (1=2) "
						+ sql.substring(indexByGroup, sql.length());

			} else if (indexByGroup > -1) {
				sql = sql.substring(0, indexByGroup) + " where (1=2) "
						+ sql.substring(indexByGroup, sql.length());
			} else {
				sql = sql.substring(0, indexByOrder) + " where (1=2) "
						+ sql.substring(indexByOrder, sql.length());
			}

		} else if (indexByLimit > -1) {

			sql = sql.substring(0, indexByLimit) + " where (1=2) ";

		} else {

			sql = sql + " where (1=2)";

		}
		System.out.println("-- Get 'Where (1=2)' Sql:" + sql);
		return sql;
	}

	// 对照表转抄
	public ClassList emsEdiChange(ClassList obj) {
		ClassList emsHeadChange = getHeadChangeBean(obj);// 转抄后
		List listImg = getImgChangeBean(obj, emsHeadChange);
		dataImportDao.saveClassList(emsHeadChange);
		for (int i = 0; i < listImg.size(); i++) {
			dataImportDao.saveFieldList((FieldList) listImg.get(i));
		}
		return emsHeadChange;
	}

	// 表头转抄
	private ClassList getHeadChangeBean(ClassList obj) {
		ClassList emsEdiTrChanged = null; // 变更
		try {
			emsEdiTrChanged = (ClassList) BeanUtils.cloneBean(obj);// 变更
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		emsEdiTrChanged.setSortno(Integer.valueOf(dataImportDao.getNum(
				"ClassList", null)));
		emsEdiTrChanged.setId(null);
		return emsEdiTrChanged;
	}

	// 表体转抄
	private List getImgChangeBean(ClassList emsHead, ClassList emsHeadChange) {
		List list = null;
		List emsImgChanges = new Vector();
		list = dataImportDao.findFieldList(emsHead); // 取未变更料件
		for (int i = 0; i < list.size(); i++) {
			FieldList emsImg = (FieldList) list.get(i);
			FieldList emsImgChanged = null; // 变更
			try {
				emsImgChanged = (FieldList) BeanUtils.cloneBean(emsImg);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			emsImgChanged.setId(null);
			emsImgChanged.setClassList(emsHeadChange);
			emsImgChanges.add(emsImgChanged);
		}
		return emsImgChanges;
	}

	// 域转抄
	public DBFormat formatChange(DBFormat obj) {
		DBFormat emsHeadChange = getFormatChangeBean(obj);// 转抄后
		List listImg = getFormatImgChangeBean(obj, emsHeadChange);
		dataImportDao.saveDBFormat(emsHeadChange);
		for (int i = 0; i < listImg.size(); i++) {
			dataImportDao.saveDBFormatSetup((DBFormatSetup) listImg.get(i));
		}
		return emsHeadChange;
	}

	// 表头转抄
	private DBFormat getFormatChangeBean(DBFormat obj) {
		DBFormat emsEdiTrChanged = null; // 变更
		try {
			emsEdiTrChanged = (DBFormat) BeanUtils.cloneBean(obj);// 变更
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		emsEdiTrChanged.setRegionName(emsEdiTrChanged.getRegionName() + "(复制)");
		emsEdiTrChanged.setId(null);
		return emsEdiTrChanged;
	}

	// 表体转抄
	private List getFormatImgChangeBean(DBFormat emsHead, DBFormat emsHeadChange) {
		List list = null;
		List emsImgChanges = new Vector();
		list = dataImportDao.findDBFormatSetup(emsHead); // 取未变更料件
		for (int i = 0; i < list.size(); i++) {
			DBFormatSetup emsImg = (DBFormatSetup) list.get(i);
			DBFormatSetup emsImgChanged = null; // 变更
			try {
				emsImgChanged = (DBFormatSetup) BeanUtils.cloneBean(emsImg);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			emsImgChanged.setId(null);
			emsImgChanged.setDbFormat(emsHeadChange);
			emsImgChanges.add(emsImgChanged);
		}
		return emsImgChanges;
	}

	public String formatSql(String sql, BaseCompany company) {
		List list = null;
		String sqlstr = sql;
		if (sql.contains("@")) {
			if (company != null && company.getId() != null) {
				list = dataImportDao.findParameterListByCompany(company);
			} else {
				list = dataImportDao.findParameterList();
			}
			for (int i = 0; i < list.size(); i++) {
				String name = ((ParameterList) list.get(i)).getPname();
				String value = ((ParameterList) list.get(i)).getPvalue();
				Boolean isnowDate = ((ParameterList) list.get(i))
						.getIsNowDate();
				if (isnowDate != null && isnowDate) {
					sqlstr = replace(sqlstr, "@" + name,
							dateToDate(new Date()), true);
				} else {
					sqlstr = replace(sqlstr, "@" + name + "@", value, false);
				}
			}
		}
		//
		// 如果海关帐记帐年份不是2005那么下面这些 表名=表名+年份(意思就是每年单据都会有不同的表来存数据)
		//
		sqlstr = CommonUtils.replaceSqlByTableName(sqlstr);
		logger.info("[JBCUS DATA IMPORT] 格式化后的SQL  = " + sqlstr);
		return sqlstr;
	}

	// 替换字符串
	public String replace(String strSource, String strFrom, String strTo,
			Boolean isnowDate) {
		if (strSource == null || strSource.equals("") || strFrom == null
				|| strFrom.equals(""))
			return strSource;
		String strDest = "";
		int intFromLen = strFrom.length();
		int intPos;
		while ((intPos = strSource.indexOf(strFrom)) != -1) {
			strDest = strDest + strSource.substring(0, intPos);
			if (isnowDate != null && isnowDate) {// 为日期格式
				String s = strSource.substring(intPos + intFromLen);// 左边剩下部分
				int x = s.indexOf("@");
				String f = null;
				String m = null;
				if (x == 0) {
					f = "+";
					m = "0";
				} else {
					f = s.substring(0, 1);// 符号"+":"-"
					m = s.substring(1, x);// 日
				}
				String nowd = strTo;
				if (f != null && f.equals("+")) {
					strDest = strDest + jisuanDate(nowd, Integer.parseInt(m));
				} else {
					strDest = strDest
							+ jisuanDate(nowd, 0 - Integer.parseInt(m));
				}
				strSource = strSource.substring(intPos + intFromLen + x + 1);
			} else {
				strDest = strDest + strTo;
				strSource = strSource.substring(intPos + intFromLen);
			}
		}
		strDest = strDest + strSource;
		return strDest;
	}

	public static String dateToDate(Date date1) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		String str1 = formater.format(date1);
		return str1;
	}

	public static String jisuanDate(String sourceDate, int day) {
		try {
			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = s.parse(sourceDate);
			Calendar c1 = new GregorianCalendar();
			c1.setTime(d1);
			Calendar endDate = null;
			if (c1 != null) {
				endDate = (Calendar) c1.clone();
				endDate.add(Calendar.DAY_OF_MONTH, day);
			}
			return (dateToDate(endDate.getTime()));
		} catch (Exception e) {
			return "";
		}
	}

	private String forClassName(String value) {
		if (value != null) {
			String entityName = value.toString();
			int lastIndex = entityName.lastIndexOf(".");
			if (lastIndex > -1) {
				return entityName.substring(lastIndex + 1);
			}
		}
		return value;
	}

	public void tempChange() {
		List list = this.dataImportDao.findClassList();
		for (int i = 0; i < list.size(); i++) {
			ClassList obj = (ClassList) list.get(i);
			obj.setClassPath(forClassName(obj.getClassPath()));
			this.dataImportDao.saveClassList(obj);
		}
	}

	public void saveTxtList(List list) {
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			this.dataImportDao.saveTxtInput(obj);
		}
	}

	/**
	 * 更新修改内部归并
	 * 
	 * @param className
	 * @param list
	 * @param rs
	 * @param company
	 * @param ischange
	 */
	private void updateInnerMergeData(String className, List list,
			final List<Map<String, Object>> batchList, BaseCompany company,
			boolean ischange, Map<String, List<DBFormatSetup>> moreWhereMap,
			DataImportMaps dataImportMaps, Hashtable hs, boolean isBmTran) {
		if (batchList.size() <= 0) {
			return;
		}
		className = className.trim();
		try {
			String sourceFiledByMateriel = "";
			for (int j = 0; j < list.size(); j++) { // 修改
				DBFormatSetup dbFormatSetup = (DBFormatSetup) list.get(j);// 字段对照
				String destFiledName = dbFormatSetup.getAimFieldName().trim();
				if ("materiel".equals(destFiledName)) {// 以字段 materiel
					// 来断定该条记录是否已存在
					sourceFiledByMateriel = dbFormatSetup.getSourceFieldName(); // 获得值
					if (sourceFiledByMateriel == null
							|| sourceFiledByMateriel.trim().equals("")) {
						logger.info("导入的数据中料号字段没有对应,不能导入 !! ");
						return;
					}
					break;
				}
			}
			Map<String, String> ptNoKeyMap = new HashMap<String, String>();
			for (int i = 0, n = batchList.size(); i < n; i++) { // 修改
				Map<String, Object> tempDataMap = batchList.get(i);
				Object materiel = tempDataMap.get(sourceFiledByMateriel); // 获得值
				if (materiel == null) {
					logger.info("导入的数据中料号有为空的记录");
					continue;
				}
				String key = materiel.toString().trim();
				if (existInnerMergeMap.get(key) != null) { // 存在于内部归并
					ptNoKeyMap.put(key, key);
				}
			}

			//
			// 所有条件的值
			//
			List<String> materielList = new ArrayList<String>();
			materielList.addAll(ptNoKeyMap.values());
			//
			// 批量删除所有存在的PtNo记录 (只删除)
			//
			if (materielList != null && materielList.size() > 0) {
				this.dataImportDao.batchDeleteInnerMergeData(className,
						materielList, (Company) company);
			}
			//
			// 进行批量保存
			//
			List<Object> saveBatchList = new ArrayList<Object>();
			for (int i = 0, n = batchList.size(); i < n; i++) { // 修改
				try {
					Map<String, Object> tempDataMap = batchList.get(i);
					Class cls = Class.forName(className);
					Object obj = cls.newInstance();
					Map<String, String> repeatFieldMap = new HashMap<String, String>();
					for (int j = 0; j < list.size(); j++) {
						Object objValue = null;
						DBFormatSetup dbFormatSetup = (DBFormatSetup) list
								.get(j);// 字段对照
						if (dbFormatSetup.getOrderNo() == null) {
							continue;
						}
						Object value = null;
						String destFiledName = dbFormatSetup.getAimFieldName();
						//
						// 以目的字段为key来获到多条件,并且是对象转换的类型
						//
						List aimNameReList = moreWhereMap.get(destFiledName);

						if (aimNameReList != null && aimNameReList.size() > 0) { // 存在重复的字段对照
							if (repeatFieldMap.get(dbFormatSetup
									.getAimFieldName()) == null) {
								objValue = getCasObjectValue(aimNameReList,
										tempDataMap, ischange, company,
										dataImportMaps, hs, isBmTran);
								repeatFieldMap.put(
										dbFormatSetup.getAimFieldName(),
										dbFormatSetup.getAimFieldName());
							} else {
								continue;
							}
						} else {
							value = tempDataMap.get(dbFormatSetup
									.getSourceFieldName()); // 获得值
							if (value != null && (value instanceof String)
									&& ischange) {
								value = changeStr(value.toString()).trim(); // 繁简转换
							}
							objValue = getDBObjValue(dbFormatSetup, value,
									company);
						}
						BeanUtils.setProperty(obj,
								dbFormatSetup.getAimFieldName(), objValue);
					}
					String sptNo = BeanUtils.getProperty(obj, "emsSerialNo");
					if (ptNoKeyMap.get(sptNo) == null) { // 不存在，即新增
						BeanUtils.setProperty(obj, "importTimer", new Date());
						newInsertCount++;
					} else { // 存在，即修改
						BeanUtils.setProperty(obj, "updateDate", new Date());
						updateCount++;
					}
					saveBatchList.add(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			dataImportDao.batchSaveOrUpdate(saveBatchList);// 批量保存
			System.out.println("批量保存结束---------");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
