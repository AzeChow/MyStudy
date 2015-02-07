package com.bestway.bcus.dataimport.logic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.sql.DataSource;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.bestway.bcus.dataimport.dao.DataImportDao;
import com.bestway.common.CommonUtils;

/**
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DataBackupLogic {

	private static Log log = LogFactory.getLog(DataBackupLogic.class);
	private DataSource dataSource = null;
	private DataImportDao dataImportDao = null;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private String[] fileNames = new String[] { "ClassList", "FieldList",
			"DBDataRoot", "DBView", "DBFormat", "DBFormatSetup", "DBTaskEx",
			"DBTaskSel" };

	public void exportBackUp(String fileName, String tableName)
			throws SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			for (int i = 0; i < fileNames.length; i++) {
				String url = fileNames[i];
				exportXml(conn, fileName, url);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			close(conn);
		}
	}

	// 输出，备份
	private void exportXml(Connection conn, String fileName, String tableName)
			throws SQLException {
		ResultSet rs = readData(conn, tableName);
		Element rootElement = new Element("table");
		Document doc = new Document(rootElement);
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		rootElement.setAttribute("createDate", (bartDateFormat.format(date)));
		rootElement.setAttribute("tableName", tableName);
		int columnCount = rs.getMetaData().getColumnCount(); // 字段数目
		while (rs.next()) {
			Element rowElement = new Element("row");
			rootElement.addContent(rowElement);
			for (int j = 1; j <= columnCount; j++) {
				String columnName = rs.getMetaData().getColumnName(j);
				if (rs.getString(columnName) != null
						&& !rs.getMetaData().getColumnName(j).equals("coid")) {
					rowElement.setAttribute(columnName,
							rs.getString(columnName));
				}
			}
		}
		XMLOutputter outputter = new XMLOutputter("	", true, "GBK");

		try {
			File file = new File(fileName + "/output");
			if (!file.exists()) {
				file.mkdir();
			}
			File file1 = new File(fileName + "output/" + tableName + ".xml");
			FileWriter writer = new FileWriter(file1);
			outputter.output(doc, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ResultSet readData(Connection conn, String tableName) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + tableName);
			return rs;
		} catch (SQLException e) {
			System.out.println("conn is null");
			e.printStackTrace();
		}
		return null;
	}

	// 还原
	public void insertTable(String fileName) {
		try {
			deleteSql();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < fileNames.length; i++) {
			String url = fileName + "output/" + fileNames[i] + ".xml";
			System.out.println(" -- URL:" + url);
			updateTable(url);
		}
		try {
			updateSql();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteSql() throws SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			for (int i = fileNames.length - 1; i >= 0; i--) {
				String tableName = fileNames[i];
				String deletesql = "Delete from " + tableName;
				Statement stmt = conn.createStatement();
				stmt.execute(deletesql);
				System.out.println(deletesql);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			close(conn);
		}
	}

	public void updateSql() throws SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			for (int i = fileNames.length - 1; i >= 0; i--) {
				String tableName = fileNames[i];
				if (tableName.equals("ClassList")
						|| tableName.equals("FieldList")) {
					continue;
				}
				String updatesql = "Update " + tableName + " Set coid='"
						+ CommonUtils.getCompany().getId() + "'";
				Statement stmt = conn.createStatement();
				stmt.execute(updatesql);
				System.out.println(updatesql);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			close(conn);
		}
	}

	public void updateTable(String url) {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			SAXParser sax;
			try {
				sax = SAXParserFactory.newInstance().newSAXParser();
				SaxParseHandler sph = new SaxParseHandler(conn);
				sax.parse(new File(url), sph);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			close(conn);
		}
	}

	/**
	 * 一定要关闭连接 否则数据库会崩溃
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
	 * @return Returns the dataImportDao.
	 */
	public DataImportDao getDataImportDao() {
		return dataImportDao;
	}

	/**
	 * @param dataImportDao
	 *            The dataImportDao to set.
	 */
	public void setDataImportDao(DataImportDao dataImportDao) {
		this.dataImportDao = dataImportDao;
	}
}

class SaxParseHandler extends DefaultHandler {
	/**
	 * Commons Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(SaxParseHandler.class);
	String tableName = null;
	String createDate = null;
	Session session = null;
	Vector insertParams = new Vector();
	ResultSet rs = null;
	Hashtable columnInfos = new Hashtable();
	Connection conn = null;
	PreparedStatement stmt = null;
	boolean noCustom = false;
	String schem = "";

	public SaxParseHandler(Connection conn) {
		super();
		this.conn = conn;
		/*
		 * try { rs =conn.getMetaData().getTables(null,"%","%",new
		 * String[]{"TABLE","VIEW"}); rs.next(); try{
		 * schem=rs.getString(2);//TABLE_SCHEM } catch (Exception e){ schem =
		 * ""; } if (schem!=null && !"".equals(schem)) { schem=schem+"."; }else
		 * { schem=""; //} } catch (SQLException e) { e.printStackTrace(); }
		 */

	}

	public void startElement(String uri, String name, String qName,
			Attributes atts) throws SAXException {
		if (qName.equals("table")) {
			tableName = atts.getValue("tableName").toLowerCase();

			createDate = atts.getValue("createDate");
			try {
				// 读取所有当前表的记录
				insertParams.clear();
				columnInfos.clear();
				Statement stmt = conn.createStatement();
				rs = stmt.executeQuery("select * from " + schem + tableName);
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					String columnName = rs.getMetaData().getColumnName(i);
					columnInfos.put(columnName, new Integer(rs.getMetaData()
							.getColumnType(i)));
				}
				logger.info("[JBCUS]  begin loop time:" + new Date());
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}

		try {
			if (qName.equals("row")) {
				List parameters = new Vector();
				for (int i = 0; i < columnInfos.keySet().toArray().length; i++) {
					String rsColumnName = (String) columnInfos.keySet()
							.toArray()[i];
					String columnName = findRowColumn(rsColumnName, atts);

					String sValue = null;
					// columnName等于空表示，在XML文档中没有与数据库对应的字段
					if (columnName != null && atts.getValue(columnName) != null) {
						sValue = atts.getValue(columnName).trim();
					}
					Object oValue = getValueByType(sValue,
							(Integer) columnInfos.get(rsColumnName));
					parameters.add(oValue);
				}
				insertParams.add(parameters);
			}
		} catch (Exception ex) {
			System.out.println("TableName: " + tableName + ex.getMessage());
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

	// 得到insertSql
	private String getInsertSql() throws NumberFormatException, SQLException {
		String sql = "insert into " + schem + tableName + "(";
		for (int i = 0; i < columnInfos.keySet().toArray().length; i++) {
			String columnName = (String) columnInfos.keySet().toArray()[i];

			if (i == 0) {
				sql += schem + tableName + "." + columnName;
			} else {
				sql += "," + schem + tableName + "." + columnName;
			}
		}
		sql += ") values(";
		for (int i = 0; i < columnInfos.keySet().toArray().length; i++) {
			if (i == 0) {
				sql += "?";
			} else {
				sql += ",?";
			}
		}
		sql += ")";
		return sql;
	}

	public Object getValueByType(String sValue, Integer type)
			throws NumberFormatException, SQLException {
		if (sValue == null) {
			return null;
		}
		Object oValue = null;
		switch (type.intValue()) {
		case Types.VARCHAR:
		case Types.CLOB:
		case Types.TIMESTAMP:
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
			if (sValue.equals("0")) {
				oValue = Boolean.valueOf(false);
			} else {
				oValue = Boolean.valueOf(true);
			}
			break;
		case Types.BIT:
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
			System.out.println("oValue" + sValue + "   " + oValue);
			throw new RuntimeException("未知类型异常：" + type);
		}
		return oValue;
	}

	public void saveAll(List insertParams) throws SQLException {
		String insertSql = getInsertSql();
		PreparedStatement stmtInsert = conn.prepareStatement(insertSql);

		for (int i = 0; i < insertParams.size(); i++) {
			List param = (List) insertParams.get(i);
			setParameters(stmtInsert, param);
			stmtInsert.addBatch();
			if ((i % 1000) == 0 || i == insertParams.size() - 1) {
				stmtInsert.executeBatch();
				stmtInsert.clearBatch();
			}
		}
		stmtInsert.close();
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

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("table")) {
			logger.info("[JBCUS]  end loop time :" + new Date());
			try {
				logger.info("[JBCUS]  begin commit:" + new Date());
				saveAll(insertParams);
				logger.info("[JBCUS]  end commit:" + new Date());
			} catch (Exception ex) {
				try {
					conn.rollback();
				} catch (Exception ex1) {
					throw new RuntimeException(ex1);
				}
				throw new RuntimeException(ex);
			}
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
