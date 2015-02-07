/*
 * Created on 2004-6-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.logic;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.bestway.bcus.custombase.entity.CustomBaseVersion;

/**
 * @author 001 // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class UpdateCustomBase {
	/**
	 * Commons Logger for this class
	 */
	/**
	 * 小柯新增加的LicenseDocu如何企业没有则要drop掉才能升级。否则会不成功
	 */
	private static final Log logger = LogFactory.getLog(UpdateCustomBase.class);

	private String tableOwner = "";

	private DataSource dataSource = null;

	private Connection conn = null;

	private String[] fileNames = new String[] { "ClassList.xml",
			"FieldList.xml", "GbToBig.xml", "LevyKind.xml", "Balancemode.xml",
			"Levymode.xml", "Trade.xml", "Unit.xml", "CoType.xml", "Curr.xml",
			"LicenseNote.xml", "Deduc.xml", "CustomsComplex.xml",
			"InvClass.xml", "InvestMode.xml", "MachiningType.xml",
			"ApplicationMode.xml", "Brief.xml", "LicenseDocu.xml",
			"Transf.xml", "Transac.xml", "PayMode.xml", "Wrap.xml", "Uses.xml",
			"SrtJzx.xml", "ContaModel.xml", "ContaSize.xml", "SrtTj.xml",
			"PayType.xml", "PayerType.xml", "TaxCode.xml", "SaicCode.xml",
			"StsCode.xml", "RedDep.xml", "Country.xml", "Customs.xml",
			"PortInternal.xml", "PortLin.xml", "PreDock.xml", "District.xml",
			"BillType.xml", "DriverList.xml", "BargainType.xml" };

	public void init() {
		logger.info("init custom base!");
		try {
			conn = dataSource.getConnection();
			for (int i = 0; i < fileNames.length; i++) {
				String url = "com/bestway/bcus/input/" + fileNames[i];
				logger.info("[JBCUS] check url start----------------------"
						+ url);
				updateTable(url);
				logger.info("[JBCUS] check url   end----------------------"
						+ url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 修改数据
	 * 
	 * @param url
	 */
	public void updateTable(String url) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String tableName = getTableName(url);
		tableName = tableName.toLowerCase();
		if (tableName == null || tableName.equals(""))
			return;
		String fileDataStr = getVersion(url);
		Date fileData = null;
		if (fileDataStr != null) {
			fileDataStr = fileDataStr.trim();
			try {
				fileData = sdf.parse(fileDataStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String dbDateStr = this.findVersion(tableName);
		Date dbDate = null;
		if (dbDateStr != null) {
			dbDateStr = dbDateStr.trim();
			try {
				dbDate = sdf.parse(dbDateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// System.out.print("dbDateStr=="+dbDateStr+"  dbDate="+dbDate+" fileData"+fileData+"值 "+fileData.compareTo(dbDate));
		if (dbDate == null || fileData.compareTo(dbDate) > 0) {
			SAXParser sax;
			try {
				sax = SAXParserFactory.newInstance().newSAXParser();
				SaxParseHandler sph = new SaxParseHandler(this, tableOwner);
				InputStream is = this.getClass().getClassLoader()
						.getResourceAsStream(url);
				sax.parse(is, sph);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 获得当前时间版本
	 * 
	 * @param url
	 * @return
	 */
	private String getVersion(String url) {
		try {
			InputStream is = this.getClass().getClassLoader()
					.getResourceAsStream(url);
			InputStreamReader reader = new InputStreamReader(is);
			java.io.LineNumberReader lineNumberReader = new java.io.LineNumberReader(
					reader, 2);

			lineNumberReader.readLine();
			String s = lineNumberReader.readLine();
			// System.out.println("当前版本="+s);

			Pattern pattern = Pattern.compile("createDate *= *\".*?\"");
			Matcher matcher = pattern.matcher(s);
			// System.out.println("pattern="+pattern.pattern());
			boolean bFind = matcher.find();
			if (bFind) {
				String[] strs = matcher.group().split("=");
				if (strs[0].equals("createDate")) {
					strs[1] = strs[1].replaceAll("\"", "");
					// System.out.print("strs[1]"+strs[1]);
					return strs[1];
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		return null;
	}

	/**
	 * 获得表名
	 * 
	 * @param url
	 * @return
	 */
	private String getTableName(String url) {
		try {
			InputStream is = this.getClass().getClassLoader()
					.getResourceAsStream(url);
			InputStreamReader reader = new InputStreamReader(is);
			java.io.LineNumberReader lineNumberReader = new java.io.LineNumberReader(
					reader, 2);
			lineNumberReader.readLine();
			String s = lineNumberReader.readLine();

			Pattern pattern = Pattern.compile("tableName *= *\".*?\"");
			Matcher matcher = pattern.matcher(s);
			boolean bFind = matcher.find();
			if (bFind) {
				String[] strs = matcher.group().split("=");
				if (strs[0].equals("tableName")) {
					strs[1] = strs[1].replaceAll("\"", "");
					return strs[1];
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		return null;
	}

	/**
	 * 查找数据库的版本
	 * 
	 * @param tableName
	 * @return
	 */
	public String findVersion(String tableName) {
		ResultSet rs = null;
		try {
			Statement stmt = conn.createStatement();
			String tableN = tableOwner + "custombaseversion";
			rs = stmt.executeQuery("select " + tableN + ".version from "
					+ tableN + " where " + tableN + ".tableName ='" + tableName
					+ "'");
			if (rs.next())
				return (String) rs.getString(1);
			else
				return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 保存数据中的时间版本
	 * 
	 * @param tableName
	 * @param customBaseVersion
	 */
	public void saveCustomBaseVersion(String tableName,
			CustomBaseVersion customBaseVersion) {
		ResultSet rs = null;
		String sql = "";
		try {
			Statement stmt = this.conn.createStatement();
			String tableN = tableOwner + "custombaseversion";
			rs = stmt.executeQuery("select " + tableN + ".version from "
					+ tableN + " where " + tableN + ".tableName ='"
					+ tableName.toLowerCase() + "'");
			if (rs.next()) {
				sql = "update " + tableN + " set " + tableN + ".tableName='"
						+ tableName.toLowerCase() + "',";
				sql += tableN + ".version='" + customBaseVersion.getVersion()
						+ "'";
				sql += " where " + tableN + ".tableName='"
						+ tableName.toLowerCase() + "'";
			} else {
				sql = "insert into " + tableN + " (" + tableN + ".tableName,"
						+ tableN + ".version)";
				sql += " values('" + tableName.toLowerCase() + "','"
						+ customBaseVersion.getVersion() + "')";
			}
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 设置数据表名前缀
	 * 
	 * @return
	 */
	public String getTableOwner() {
		return tableOwner;
	}

	/**
	 * 设置数据表名前缀
	 * 
	 * @return
	 */
	public void setTableOwner(String tableOwner) {
		if (tableOwner != null && !"".equals(tableOwner.trim())) {
			this.tableOwner = tableOwner + ".";
		}
	}

	public Connection getConn() {
		return conn;
	}
}

class SaxParseHandler extends DefaultHandler {
	/**
	 * Commons Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(SaxParseHandler.class);

	String tableName = null;

	String tableOwner = "";

	String createDate = null;

	Session session = null;

	UpdateCustomBase dao = null;

	List<List> insertParams = new ArrayList<List>();

	List<List> updateParams = new ArrayList<List>();

	Map<String, String> deleteCodesMap = new HashMap<String, String>();

	ResultSet rs = null;

	Hashtable<Object, Object> columnInfos = new Hashtable<Object, Object>();

	Hashtable<Object, Object> records = new Hashtable<Object, Object>();

	Connection conn = null;

	PreparedStatement stmt = null;

	boolean noCustom = false;

	/**
	 * 分析构造
	 * 
	 * @param dao
	 * @param tableOwner
	 */
	public SaxParseHandler(UpdateCustomBase dao, String tableOwner) {
		super();
		this.dao = dao;
		this.tableOwner = tableOwner;
		this.conn = dao.getConn();

	}

	/**
	 * 开始
	 */
	public void startElement(String uri, String name, String qName,
			Attributes atts) throws SAXException {
		if (qName.equals("table")) {
			tableName = atts.getValue("tableName").toLowerCase();
			if (tableName.equals("classlist") || tableName.equals("fieldlist")
					|| tableName.equals("billtype")
					|| tableName.equals("driverlist")
					|| tableName.equals("gbtobig")
					|| tableName.equals("licensedocu")) {
				noCustom = true;
			} else {
				noCustom = false;
			}

			logger.info("[JBCUS]    begin update table '" + tableName + "':"
					+ new Date());
			createDate = atts.getValue("createDate");
			try {
				// 读取所有当前表的记录
				insertParams.clear();
				updateParams.clear();
				deleteCodesMap.clear();
				columnInfos.clear();
				records.clear();

				Statement stmt = conn.createStatement();
				rs = stmt.executeQuery("select * from " + tableOwner
						+ tableName);

				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					String columnName = rs.getMetaData().getColumnName(i);
					Integer columnType = new Integer(rs.getMetaData()
							.getColumnType(i));
					//
					// 针对Mysql jdbc dirver bug 进行的处理 (不会对其它的程序造成影响)
					//
					String columnTypeName = rs.getMetaData()
							.getColumnTypeName(i).trim();

					if (columnTypeName.equalsIgnoreCase("BIT")) {
						columnType = Types.BIT;
					}
					columnInfos.put(columnName, columnType);
				}

				// 列名的 key 值
				Object[] keyArray = columnInfos.keySet().toArray();

				while (rs.next()) {
					String code = "";
					if (noCustom) {
						code = rs.getString("id").trim();
					} else {
						code = rs.getString("code").trim();
					}
					deleteCodesMap.put(code, code);
					Object[] record = new Object[columnInfos.size()];
					for (int i = 0; i < keyArray.length; i++) {
						String columnName = (String) keyArray[i];
						record[i] = rs.getString(columnName);
					}
					records.put(code, record);
				}

			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}

		try {
			if (qName.equals("row")) {
				String code = "";
				if (noCustom) {
					code = atts.getValue("id").trim();
				} else {
					code = atts.getValue("code").trim();
				}
				boolean bFindCode = false;

				if (deleteCodesMap.get(code) != null) {
					bFindCode = true;
					deleteCodesMap.remove(code);
				}

				if (!bFindCode) {
					// 如果没有找到是新增该记录
					List<Object> parameters = new Vector<Object>();
					// 列名的 key 值
					Iterator iteratorKey = columnInfos.keySet().iterator();
					for (int i = 0; iteratorKey.hasNext(); i++) {
						String rsColumnName = (String) iteratorKey.next();
						String columnName = findRowColumn(rsColumnName, atts);

						String sValue = null;
						// columnName等于空表示，在XML文档中没有与数据库对应的字段
						if (columnName != null
								&& atts.getValue(columnName) != null) {
							sValue = atts.getValue(columnName).trim();
						}

						Object oValue = getValueByType(sValue,
								(Integer) columnInfos.get(rsColumnName));
						parameters.add(oValue);
					}
					insertParams.add(parameters);
				} else {
					List<Object> parameters = new ArrayList<Object>();
					// 格式:"update " + tableName + " set code=?,..., where code
					// =?";
					parameters.add(code);
					boolean update = false;
					// 列名的 key 值
					Iterator iteratorKey = columnInfos.keySet().iterator();
					for (int i = 0; iteratorKey.hasNext(); i++) {
						String rsColumnName = (String) iteratorKey.next();
						String columnName = findRowColumn(rsColumnName, atts);
						String yy = "";
						if (noCustom) {
							yy = "id";
						} else {
							yy = "code";
						}
						if (columnName != null
								&& columnName.toLowerCase().equals(
										yy.toLowerCase())) {
							continue;
						}
						String sValue = null;

						// columnName等于空表示，在XML文档中没有与数据库对应的字段
						if (columnName != null
								&& atts.getValue(columnName) != null) {
							sValue = atts.getValue(columnName).trim();
						}

						Object oValue = getValueByType(sValue,
								(Integer) columnInfos.get(rsColumnName));
						parameters.add(oValue);
						Object[] record = (Object[]) records.get(code);
						if (sValue == null) {
							if (record[i] != null) {
								update = true;
							}
						} else {
							if (!sValue.equals(record[i])) {
								update = true;
							}
						}
					}
					parameters.add(code);
					if (update) {
						updateParams.add(parameters);
					}
				}
			}
		} catch (Exception ex) {
			// System.out.println("TableName: " + tableName + ex.getMessage());
			ex.printStackTrace();
		}
	}

	private String findRowColumn(String rsColumnName, Attributes atts) {
		for (int i = 0; i < atts.getLength(); i++) {
			if (atts.getQName(i).toLowerCase()
					.equals(rsColumnName.toLowerCase())) {
				return atts.getQName(i);
			}
		}
		return null;
	}

	private String getInsertSql() throws NumberFormatException, SQLException {
		String sql = "insert into " + tableOwner + tableName + "(";

		// 列名的 key 值
		int count = columnInfos.size();
		Iterator iteratorKey = columnInfos.keySet().iterator();
		for (int i = 0; iteratorKey.hasNext(); i++) {
			String columnName = (String) iteratorKey.next();
			if (i == 0) {
				sql += tableOwner + tableName + "." + columnName;
			} else {
				sql += "," + tableOwner + tableName + "." + columnName;
			}
		}
		sql += ") values(";
		for (int i = 0; i < count; i++) {
			if (i == 0) {
				sql += "?";
			} else {
				sql += ",?";
			}
		}
		sql += ")";
		return sql;
	}

	private String getUpdateSql() throws NumberFormatException, SQLException {
		// 列名的 key 值
		Iterator iteratorKey = columnInfos.keySet().iterator();

		String sql = "";
		if (noCustom) {
			sql = "update " + tableOwner + tableName + " set " + tableOwner
					+ tableName + ".id=?";
			for (int i = 0; iteratorKey.hasNext(); i++) {
				String columnName = (String) iteratorKey.next();
				if (columnName.toLowerCase().equals("id")) {
					continue;
				}
				sql += ", " + tableOwner + tableName + "." + columnName + "=?";
			}
			sql += " where " + tableOwner + tableName + ".id=?";
			return sql;
		} else {
			sql = "update " + tableOwner + tableName + " set " + tableOwner
					+ tableName + ".code=?";
			for (int i = 0; iteratorKey.hasNext(); i++) {
				String columnName = (String) iteratorKey.next();
				if (columnName.toLowerCase().equals("code")) {
					continue;
				}
				sql += ", " + tableOwner + tableName + "." + columnName + "=?";
			}
			sql += " where " + tableOwner + tableName + ".code=?";
			return sql;
		}

	}

	public Object getValueByType(String sValue, Integer type)
			throws NumberFormatException, SQLException {
		if (sValue == null) {
			return null;
		}
		Object oValue = null;
		switch (type.intValue()) {
		case Types.VARCHAR:
			oValue = sValue;
			break;
		case Types.DECIMAL:
		case Types.NUMERIC:
		case Types.DOUBLE:
		case Types.FLOAT:
			if (sValue == null)
				oValue = null;
			else
				oValue = new Double(sValue);
			break;
		case Types.BOOLEAN:
		case Types.TINYINT:
		case Types.BIT:
			if (sValue.equals("0")) {
				oValue = Boolean.valueOf(false);
			} else {
				oValue = Boolean.valueOf(true);
			}
			break;
		case Types.INTEGER:
		case Types.SMALLINT:
			if (sValue == null)
				oValue = null;
			else
				oValue = new Integer(sValue);
			break;
		case Types.DATE:
			if (sValue == null)
				oValue = null;
			else
				oValue = java.sql.Date.valueOf(sValue);
			break;
		default:
			break;
		}
		if (oValue == null && sValue != null) {
			// System.out.println("oValue" + sValue + " " + oValue);
			throw new RuntimeException("未知类型异常：" + type);
		}
		return oValue;
	}

	public void saveAll(List insertParams, List updateParams)
			throws SQLException {
		String insertSql = getInsertSql();
		String updateSql = getUpdateSql();

		PreparedStatement stmtInsert = conn.prepareStatement(insertSql);
		PreparedStatement stmtUpdate = conn.prepareStatement(updateSql);

		logger.info("[JBCUS]          begin insert table time:" + new Date());
		for (int i = 0; i < insertParams.size(); i++) {
			List param = (List) insertParams.get(i);
			setParameters(stmtInsert, param);
			stmtInsert.addBatch();
			if ((i != 0 && (i % 1000) == 0) || i == insertParams.size() - 1) {
				stmtInsert.executeBatch();
				stmtInsert.clearBatch();
			}
		}
		stmtInsert.close();
		logger.info("[JBCUS]          end insert rowcount ["
				+ insertParams.size() + "] table time:" + new Date());
		logger.info("[JBCUS]          begin update table time:" + new Date());
		for (int i = 0; i < updateParams.size(); i++) {
			List param = (List) updateParams.get(i);
			setParameters(stmtUpdate, param);
			stmtUpdate.addBatch();
			if ((i != 0 && (i % 1000) == 0) || i == updateParams.size() - 1) {
				stmtUpdate.executeBatch();
				stmtUpdate.clearBatch();
			}
		}
		stmtUpdate.close();
		logger.info("[JBCUS]          end update rowcount ["
				+ updateParams.size() + "] table time:" + new Date());
	}

	private void setParameters(PreparedStatement stmt, List paramList)
			throws SQLException {
		for (int j = 0; j < paramList.size(); j++) {
			if (paramList.get(j) != null) {
				stmt.setObject(j + 1, paramList.get(j));
			} else {
				String columnName = (String) columnInfos.keySet().toArray()[j];
				stmt.setNull(j + 1,
						((Integer) columnInfos.get(columnName)).intValue());
			}
		}
	}

	public void deleteAllCanDeleted(final List list) throws SQLException {
		logger.info("[JBCUS]          begin delete table time:" + new Date());
		// if (!tableName.equals("customscomplex")) {
		// return;
		// }
		Statement stmt = conn.createStatement();
		String code = null;
		for (int i = 0; i < list.size(); i++) {
			try {
				code = (String) list.get(i);
				String sql = "";
				if (noCustom) {
					sql = "delete from " + tableOwner + tableName + " where "
							+ tableOwner + tableName + ".id='" + code + "'";
				} else {
					sql = "delete from " + tableOwner + tableName + " where "
							+ tableOwner + tableName + ".code='" + code + "'";
				}
				logger.info("[JBCUS]              " + sql);
				stmt.execute(sql);
			} catch (Exception ex) {
				logger.info("[JBCUS]              Can not delete record: TableName:"
						+ tableName + " Code:" + code);
				String sql = "";
				String nameValue = "";
				if (noCustom) {
					sql = "select name from " + tableOwner + tableName
							+ " where " + tableOwner + tableName + ".id='"
							+ code + "'";
				} else {
					sql = "select name from " + tableOwner + tableName
							+ " where " + tableOwner + tableName + ".code='"
							+ code + "'";
				}
				// System.out.println(sql);
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next()) {
					nameValue = rs.getString("name");
				} else {
					continue;
				}
				sql = "update " + tableOwner + tableName + " set name='"
						+ nameValue + "(已删除)' where " + tableOwner + tableName
						+ ".code='" + code + "'";
				// System.out.println(sql);
				stmt.execute(sql);

				// ex.printStackTrace();
			}
		}

		stmt.close();
		logger.info("[JBCUS]          end delete rowcount [" + list.size()
				+ "] table time:" + new Date());
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("table")) {
			try {
				logger.info("[JBCUS]      begin commit:" + new Date());
				//
				// 保存所有改动数据
				//
				saveAll(insertParams, updateParams);
				//
				// 是否删除数据库中与导入数据不同的(这里建议不删除)
				//
				List<Object> tempList = new ArrayList<Object>();
				tempList.addAll(deleteCodesMap.values());
				deleteAllCanDeleted(tempList);
				logger.info("[JBCUS]      end commit:" + new Date());

			} catch (Exception ex) {
				try {
					// conn.rollback();
				} catch (Exception ex1) {
					throw new RuntimeException(ex1);
				}
				throw new RuntimeException("uri:" + uri + ",localName:"
						+ localName + ",qName:" + qName, ex);
			}
			//
			// 保存时间版本记录
			//
			CustomBaseVersion customBaseVersion = new CustomBaseVersion();
			customBaseVersion.setTableName(this.tableName);
			customBaseVersion.setVersion(this.createDate);
			dao.saveCustomBaseVersion(tableName, customBaseVersion);
			logger.info("[JBCUS]    end update table '" + tableName + "':"
					+ new Date());
		}
	}

	public String getClassStrByTableName(String tableName) {
		String className = "com.bestway.bcus.custombase.entity.basecode."
				+ tableName;
		if (checkClassName(className) != null)
			return className;
		className = "com.bestway.bcus.custombase.entity.countrycode."
				+ tableName;
		if (checkClassName(className) != null)
			return className;
		className = "com.bestway.bcus.custombase.entity.depcode." + tableName;
		if (checkClassName(className) != null)
			return className;
		className = "com.bestway.bcus.custombase.entity.hscode." + tableName;
		if (checkClassName(className) != null)
			return className;
		className = "com.bestway.bcus.custombase.entity.parametercode."
				+ tableName;
		if (checkClassName(className) != null)
			return className;
		className = "com.bestway.bcus.dataimport.entity." + tableName;
		if (checkClassName(className) != null)
			return className;
		className = "com.bestway.bcus.cas.entity." + tableName;
		if (checkClassName(className) != null)
			return className;
		return null;
	}

	public String checkClassName(String className) {
		try {
			Class.forName(className);
			return className;
		} catch (ClassNotFoundException ex) {
			return null;
		}
	}
}