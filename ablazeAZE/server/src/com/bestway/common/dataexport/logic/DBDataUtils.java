package com.bestway.common.dataexport.logic;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.bestway.common.dataexport.entity.JDBCDataSource;
import com.bestway.common.dataexport.entity.TempJDBCColumn;

/**
 * 
 * @author luosheng 2007/12/21
 * 
 */
public class DBDataUtils {

	private static final int FETCH_BATCH_SIZE = 100;

	private static final int UPDATE_BATCH_SIZE = 100;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			
		String url1 = "jdbc:jtds:sqlserver://192.168.1.191/jbcsejb;tds=8.0;lastupdatecount=true";
		String username1 = "sa";
		String password1 = "";
		String driverClassName1 = "net.sourceforge.jtds.jdbc.Driver";
		JDBCDataSource srcDS = new JDBCDataSource();
		srcDS.setUrl(url1);
		srcDS.setUserName(username1);
		srcDS.setPassword(password1);
		srcDS.setDriverClassName(driverClassName1);
		
		String url = "jdbc:mysql://192.168.1.136/test?character_set=UTF-8";
		String username = "root";
		String password = "";
		String driverClassName = "com.mysql.jdbc.Driver";
		JDBCDataSource destDS = new JDBCDataSource();
		destDS.setUrl(url);
		destDS.setUserName(username);
		destDS.setPassword(password);
		destDS.setDriverClassName(driverClassName);
		
		new DBDataUtils().dataExchange(srcDS, destDS);
	}

	
	
	
	private String[] fileNames = new String[] { "ClassList",
			"FieldList", "GbToBig", "LevyKind", "Balancemode",
			"Levymode", "Trade", "Unit", "CoType", "Curr",
			"LicenseNote", "Deduc", "Complex",
			"InvClass", "InvestMode", "MachiningType",
			"ApplicationMode", "Brief", "LicenseDocu",
			"Transf", "Transac", "PayMode", "Wrap", "Uses",
			"SrtJzx", "ContaModel", "ContaSize", "SrtTj",
			"PayType", "PayerType", "TaxCode", "SaicCode",
			"StsCode", "RedDep", "Country", "Customs",
			"PortInternal", "PortLin", "PreDock", "District",
			"BillType", "DriverList", "BargainType" };
	
	
	public void dataExchange(JDBCDataSource srcJDBCDS, JDBCDataSource destJDBCDS) {
		List<String> destTables = new ArrayList<String>();
		List<String> srcTables = new ArrayList<String>();
		Map<String,String> mapSrcTables = new HashMap<String,String>();
		//
		// 获取目的源表
		//
		if (destTables != null) {
			destTables = getTableNames(destJDBCDS);
		}
		//
		// 获取源表
		//
		if (srcJDBCDS != null) {
			srcTables = getTableNames(srcJDBCDS);
			for(String table : srcTables){
				mapSrcTables.put(table.toLowerCase(),table);
			}
		}
		//
		// 获得所有引用外键层次的表名,按引用关系 ASC
		//
		List<String> tableNames = getTableNamesBySort(destJDBCDS, destTables);
		//
		//
		//
		List<String> exeTableNames = new ArrayList<String>();
		for(String table : fileNames){
			exeTableNames.add(table.toLowerCase());
		}
		
		//
		// delete all data in destJDBCDataSource
		//
		List<String> tableNames2 = new ArrayList<String>();
		for (String tableName : tableNames) {
			if(exeTableNames.contains(tableName.toLowerCase())){
				continue;
			}
			//
			// DESC
			//
			tableNames2.add(0,tableName);
			String deleteSql = "DELETE FROM " + tableName ;
			System.out.println("  " + deleteSql);
			executeSql(destJDBCDS, deleteSql);
		}
		
		for (String tableName : tableNames2) {			
			System.out.println("  " + tableName);
		}
		
		//
		// batch insert all data to destJDBCDataSource by srcJDBCDataSource
		//
		executeInsertBySrcDataSource(srcJDBCDS, destJDBCDS, tableNames2,mapSrcTables);
	}

	/**
	 * batch insert all data to destJDBCDataSource by srcJDBCDataSource
	 * 
	 * @param srcJDBCDS
	 * @param destJDBCDS
	 * @param tableNames
	 */
	private void executeInsertBySrcDataSource(JDBCDataSource srcJDBCDS,
			JDBCDataSource destJDBCDS, List<String> tableNames,Map<String,String> mapSrcTables) {
		for (String tableName : tableNames) {
			String viewTable = mapSrcTables.get(tableName.toLowerCase());
			if(viewTable == null){
				continue;
			}
			String viewSql = "SELECT * FROM " + viewTable;
			System.out.println(viewSql);

			ResultSet resultSet = null;
			Connection viewConn = null;
			try {
				JDBCDataSource viewJDBCDataSource = srcJDBCDS;
				DataSource dataSource = DataSourcePools.getDataSource(
						viewJDBCDataSource.getDriverClassName(),
						viewJDBCDataSource.getUrl(), viewJDBCDataSource
								.getUserName(), viewJDBCDataSource
								.getPassword());

				viewConn = dataSource.getConnection();
				if (viewConn.getAutoCommit() == false) {
					viewConn.setAutoCommit(true);
				}
				Statement stmt = viewConn.createStatement();
				stmt.setFetchSize(FETCH_BATCH_SIZE);
				resultSet = stmt.executeQuery(viewSql);

				//
				// 获得视图中的 列别名
				//
				List<String> lsField = new ArrayList<String>();
				ResultSetMetaData rsMetaData = resultSet.getMetaData();
				int columnCount = rsMetaData.getColumnCount();
				for (int i = 1; i <= columnCount; i++) {
					String columnName = rsMetaData.getColumnLabel(i);
					//
					// sqlserver jdbc driver getColumnLabel == getColumnName
					// oracle jdbc driver getColumnLabel == getColumnName
					// mysql jdbc driver getColumnLabel(代表真正的别名) !=
					// getColumnName(代表实际数据库中的字段名) 并不相等
					//
					// System.out.println("getColumnLabel == "+columnName);
					// System.out.println("getColumnName ==
					// "+rs.getMetaData().getColumnName(i));
					lsField.add(columnName);
				}

				//
				// 获得导入数据的方式
				//
				executeInsert(destJDBCDS, tableName, resultSet, lsField);
				//
				// 注意关闭
				//
				resultSet.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			} finally {
				close(viewConn);
			}

		}
	}

	private void executeInsert(JDBCDataSource destJDBCDS, String tableName,
			ResultSet resultSet, List<String> lsField) {
		//
		// 获得目的数据源,目的数据表名
		//
		JDBCDataSource destJDBCDataSource = destJDBCDS;
		DataSource destDataSource = DataSourcePools.getDataSource(
				destJDBCDataSource.getDriverClassName(), destJDBCDataSource
						.getUrl(), destJDBCDataSource.getUserName(),
				destJDBCDataSource.getPassword());
		Connection destConn = null;

		try {
			destConn = destDataSource.getConnection();
			if (destConn.getAutoCommit() == false) {
				destConn.setAutoCommit(true);
			}
			String destTableName = tableName;
			List<TempJDBCColumn> tempJDBCColumns = getDestTempJDBCColumn(
					destJDBCDataSource, destTableName);
			List<String> destColumnNames = new ArrayList<String>();
			for (TempJDBCColumn temp : tempJDBCColumns) {
				destColumnNames.add(temp.getFieldName());
			}
			//
			// 获得格式化后参数转换 SQL
			//			
			String insertSql = getInsertSql(destTableName, destColumnNames,
					destJDBCDataSource);
			PreparedStatement stmtInsert = destConn.prepareStatement(insertSql);

			boolean hasExecuteBatch = false;
			boolean isProgress = DataExportUtils
					.isProgressDataSource(destJDBCDataSource);

			for (int i = 0; resultSet.next(); i++) {
				//
				// 以视图名 为 key 值
				//
				Map<String, Object> rowMap = this.getRowMap(resultSet, lsField);

				//
				// 如果是progressDataSource 的话那么将直接执行
				//
				if (isProgress) {
					if (i % 5000 == 0) {
						stmtInsert.close();
						stmtInsert = destConn.prepareStatement(insertSql);
					}

					this.setInsertParameters(stmtInsert, tempJDBCColumns,
							rowMap);
					stmtInsert.execute();
				} else {
					//
					// 设置参数
					//
					this.setInsertParameters(stmtInsert, tempJDBCColumns,
							rowMap);
					stmtInsert.addBatch();
					hasExecuteBatch = true;
					if (i != 0 && i % UPDATE_BATCH_SIZE == 0) {
						stmtInsert.executeBatch();
						stmtInsert.clearBatch();
						hasExecuteBatch = false;
					}
				}
			}
			//
			// 如果还有未执行的语句
			//
			if (hasExecuteBatch) {
				stmtInsert.executeBatch();
				stmtInsert.clearBatch();
			}
			stmtInsert.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			close(destConn);
		}
	}

	/** 设置jdbc 导入参数 */
	private void setInsertParameters(PreparedStatement stmt,
			List<TempJDBCColumn> tempJDBCColumns, Map<String, Object> rowMap)
			throws SQLException {

		for (int i = 0; i < tempJDBCColumns.size(); i++) {
			TempJDBCColumn jdbcRegionSetup = tempJDBCColumns.get(i);
			String destFieldName = jdbcRegionSetup.getFieldName();
			Integer destFieldType = jdbcRegionSetup.getFieldType();
			
			if (jdbcRegionSetup.getFieldTypeDesc().equalsIgnoreCase("BIT")) {
				destFieldType = Types.BIT;
			}
			
			Object value = rowMap.get(destFieldName);
			if (value != null) {
				if (value instanceof Date) {
					SimpleDateFormat bartDateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					value = bartDateFormat.format((Date) value);
				}
				value = getValueByType(value.toString(), destFieldType
						.intValue());
				// stmt.setObject(i + 1, value,destFieldType);
				stmt.setObject(i + 1, value);
			} else {
				stmt.setNull(i + 1, destFieldType);
			}
		}
	}

	public Object getValueByType(String sValue, Integer type)
			throws NumberFormatException, SQLException {
		if (sValue == null || sValue.trim().equals("")) {
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
			oValue = new Double(sValue);
			break;
		case Types.BOOLEAN:
		case Types.TINYINT:
		case Types.BIT:
			if (sValue.equals("0")) {
				oValue = Boolean.valueOf(false);
			} else if (sValue.equals("1")) {
				oValue = Boolean.valueOf(true);
			} else {
				oValue = Boolean.valueOf(sValue);
			}
			break;
		case Types.INTEGER:
		case Types.SMALLINT:
		case Types.BIGINT:
			oValue = new Double(sValue).intValue();
			break;
		case Types.DATE:
			oValue = java.sql.Date.valueOf(sValue);
			break;
		case Types.TIMESTAMP:
			oValue = new java.sql.Timestamp(java.sql.Date.valueOf(sValue)
					.getTime());
			break;
		case Types.TIME:
			oValue = new java.sql.Time(java.sql.Date.valueOf(sValue).getTime());
			break;
		default:
			break;
		}
		if (oValue == null && sValue != null) {
			// logger.info("oValue" + sValue + " " + oValue);
			throw new RuntimeException("未知类型异常：" + type);
		}
		return oValue;
	}

	/**
	 * 把ResultSet 的行值转换为 Map 进行存取
	 * 
	 * @param rs
	 * @param lsField
	 * @param mDataMap
	 * @throws SQLException
	 */
	private Map<String, Object> getRowMap(ResultSet rs, List<String> lsField)
			throws SQLException {
		Map<String, Object> rowMap = new HashMap<String, Object>();
		for (int i = 0; i < lsField.size(); i++) {
			String columnName = lsField.get(i);
			Object obj = rs.getObject(columnName);
			if (obj instanceof oracle.sql.TIMESTAMP) {// oracle 10
				// 的兼容性
				oracle.sql.TIMESTAMP oTimestamp = (oracle.sql.TIMESTAMP) obj;
				Timestamp t = oTimestamp.timestampValue();
				rowMap.put(columnName, t);

			}
			if (obj instanceof oracle.sql.NUMBER) {// 
				oracle.sql.NUMBER number = (oracle.sql.NUMBER) obj;
				rowMap.put(columnName, number.doubleValue());

			} else if (obj != null && obj instanceof String) {

				String str = ((String) obj);
				// if (GbkToBig5Flag.BIG5_TO_GBK.equals(gbkToBig5)) {// 繁转简
				// str = big5ToGbk(str, big5KeyMap);
				// } else if (GbkToBig5Flag.GBK_TO_BIG5.equals(gbkToBig5)) { //
				// 简转繁
				// str = gbkToBig5(str, gbkKeyMap);
				// }
				rowMap.put(columnName, str.trim());

			} else {

				rowMap.put(columnName, obj);

			}
		}
		return rowMap;
	}

	/** 获得连接数据库表列名 */
	public List<TempJDBCColumn> getDestTempJDBCColumn(
			JDBCDataSource jdbcDataSource, String tableName) {

		List<TempJDBCColumn> columns = new ArrayList<TempJDBCColumn>();

		Connection connection = null;
		try {
			String url = jdbcDataSource.getUrl();
			DataSource dataSource = DataSourcePools.getDataSource(
					jdbcDataSource.getDriverClassName(), jdbcDataSource
							.getUrl(), jdbcDataSource.getUserName(),
					jdbcDataSource.getPassword());
			connection = dataSource.getConnection();
			if (!connection.getAutoCommit()) {
				connection.setAutoCommit(true);
			}
			DatabaseMetaData databasemetaData = connection.getMetaData();
			ResultSet cols = null;
			ResultSet rsPrimaryKey = null;
			List<String> primaryKeys = new ArrayList<String>();

			if (url.indexOf("jdbc:JdbcProgress") > -1) {
				cols = databasemetaData.getColumns(null, null, tableName, "%");
				rsPrimaryKey = databasemetaData.getPrimaryKeys(null, null,
						tableName);
			} else {
				cols = databasemetaData.getColumns(connection.getCatalog(),
						null, tableName, "%");
				rsPrimaryKey = databasemetaData.getPrimaryKeys(connection
						.getCatalog(), null, tableName);
			}

			while (rsPrimaryKey.next()) {
				primaryKeys.add(rsPrimaryKey.getString("COLUMN_NAME"));
			}

			while (cols.next()) {
				TempJDBCColumn column = new TempJDBCColumn();
				String fieldName = cols.getString("COLUMN_NAME");
				Integer fieldType = cols.getInt("DATA_TYPE");
				String fieldTypeDesc = cols.getString("TYPE_NAME");
				if (primaryKeys.contains(fieldName)) {
					column.setIsPrimaryKey(true);
				}
				column.setFieldName(fieldName);
				column.setFieldType(fieldType);
				column.setFieldTypeDesc(fieldTypeDesc);
				columns.add(column);
			}

			rsPrimaryKey.close();
			cols.close();

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			close(connection);
		}
		Collections.sort(columns);
		return columns;
	}

	/** 获得 insert sql */
	private String getInsertSql(String tableName, List<String> columnNames,
			JDBCDataSource destJDBCDataSource) throws NumberFormatException,
			SQLException {

		if (DataExportUtils.isProgressDataSource(destJDBCDataSource)) {
			String sql = "insert into " + "pub." + tableName + "(";

			// 列名的 key 值
			int count = columnNames.size();
			for (int i = 0; i < count; i++) {
				String columnName = columnNames.get(i);
				if (i == 0) {
					sql += columnName;
				} else {
					sql += "," + columnName;
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
		} else {
			String sql = "insert into " + tableName + "(";

			// 列名的 key 值
			int count = columnNames.size();
			for (int i = 0; i < count; i++) {
				String columnName = columnNames.get(i);
				if (i == 0) {
					sql += tableName + "." + columnName;
				} else {
					sql += "," + tableName + "." + columnName;
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

	}

	/**
	 * create drop .. DDL connection 由连接池管理 没有执行,只是测试
	 * 
	 * @param sql
	 * @return
	 */
	public boolean executeSql(JDBCDataSource jdbcDataSource, String sql) {
		Connection connection = null;
		try {
			DataSource dataSource = DataSourcePools.getDataSource(
					jdbcDataSource.getDriverClassName(), jdbcDataSource
							.getUrl(), jdbcDataSource.getUserName(),
					jdbcDataSource.getPassword());
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			Statement stmt = connection.createStatement();
			boolean b = stmt.execute(sql);
			// 回卷
			connection.commit();
			return b;
		} catch (Exception ex) {
			try {
				if (connection != null) {// 回卷
					connection.rollback();
				}
			} catch (SQLException e) {
			}
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			close(connection);
		}
	}

	/** 获得所有引用外键层次的表名 */
	private List<String> getTableNamesBySort(JDBCDataSource jdbcDataSource,
			List<String> tableNames) {
		List<String> returnTableNames = new ArrayList<String>();
		Connection connection = null;
		try {
			String url = jdbcDataSource.getUrl();
			String userName = jdbcDataSource.getUserName();
			DataSource dataSource = DataSourcePools.getDataSource(
					jdbcDataSource.getDriverClassName(), url, userName,
					jdbcDataSource.getPassword());
			connection = dataSource.getConnection();

			if (!connection.getAutoCommit()) {
				connection.setAutoCommit(true);
			}
			DatabaseMetaData metaData = connection.getMetaData();

			boolean isOracle = url.indexOf("jdbc:oracle:thin") > -1;
			boolean isProgress = url.indexOf("jdbc:JdbcProgress") > -1;
			boolean isMysql = url.indexOf("jdbc:mysql") > -1;

			Map<String, Integer> map = new HashMap<String, Integer>();

			for (String table : tableNames) {
				// System.out.println(" TableName = " + table );
				//
				// 外键层次 index
				//
				Integer index = new Integer(0);
				Integer tempIndex = new Integer(0);

				index = sortTableFKIndex(connection, userName, metaData,
						isOracle, isProgress, isMysql, map, table, index,
						tempIndex);
				//
				// 存入缓存
				//
				map.put(table, index);

			}

			//
			// 排序
			//
			List<DBDataUtils.Entity> tempList = new ArrayList<DBDataUtils.Entity>();
			Iterator<Map.Entry<String, Integer>> iterator = map.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Integer> e = iterator.next();
				DBDataUtils.Entity entity = new DBDataUtils.Entity(
						e.getValue(), e.getKey());
				tempList.add(entity);
			}
			Collections.sort(tempList);
			for (DBDataUtils.Entity e : tempList) {
				returnTableNames.add(e.getValue());
//				 System.out.println(e.getValue() + " = " + e.getBeginIndex()
//				 );
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			close(connection);
		}
		return returnTableNames;
	}

	private Integer sortTableFKIndex(Connection connection, String userName,
			DatabaseMetaData metaData, boolean isOracle, boolean isProgress,
			boolean isMysql, Map<String, Integer> map, String table,
			Integer index, Integer tempIndex) throws SQLException {
		ResultSet rs;
		if (isOracle) { // orcle driver 一定要用
			// schema
			String schema = userName.trim();
			if (metaData.storesUpperCaseIdentifiers()) {
				schema = schema.toUpperCase();
			} else if (metaData.storesLowerCaseIdentifiers()) {
				schema = schema.toLowerCase();
			}
			rs = metaData.getExportedKeys(connection.getCatalog(), null, table);
		} else if (isProgress) {
			rs = metaData.getExportedKeys(connection.getCatalog(), null, table);
		} else if (isMysql) {
			rs = metaData.getExportedKeys(connection.getCatalog(), null, table);
		} else {
			rs = metaData.getExportedKeys(connection.getCatalog(), null, table);
		}

		boolean isHasFK = false;
		List<String> tempTableNames = new ArrayList<String>();
		while (rs.next()) {
			isHasFK = true;
			String fkTableName = rs.getString("FKTABLE_NAME");
			// System.out.println(" " + fkTableName + " " + index);
			tempTableNames.add(fkTableName);
		}
		if (isHasFK) {
			tempIndex++;
			if (index < tempIndex) {
				index = tempIndex;
			}
			// System.out.println("" +table +" "+ index);
		}
		int temp = tempIndex.intValue();
		for (String fkTableName : tempTableNames) {
			Integer tempIndex2 = map.get(fkTableName);
			if (tempIndex2 != null) {
				if (index < temp + tempIndex2) {
					index = temp + tempIndex2;
				}
				continue;
			}
			index = sortTableFKIndex(connection, userName, metaData, isOracle,
					isProgress, isMysql, map, fkTableName, index, tempIndex);
			tempIndex = new Integer(temp);
		}
		return index;
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

	/** 获得连接数据库表名 */
	private List<String> getTableNames(JDBCDataSource jdbcDataSource) {
		List<String> tableNames = new ArrayList<String>();
		// final String[] TYPES = { "TABLE", "VIEW" };
		final String[] TYPES = { "TABLE" };
		Connection connection = null;
		try {
			String url = jdbcDataSource.getUrl();
			String userName = jdbcDataSource.getUserName();
			DataSource dataSource = DataSourcePools.getDataSource(
					jdbcDataSource.getDriverClassName(), url, userName,
					jdbcDataSource.getPassword());
			connection = dataSource.getConnection();

			if (!connection.getAutoCommit()) {
				connection.setAutoCommit(true);
			}

			ResultSet rs = null;

			DatabaseMetaData metaData = connection.getMetaData();
			if (url.indexOf("jdbc:oracle:thin") > -1) { // orcle driver 一定要用
				// schema
				String schema = userName.trim();
				if (metaData.storesUpperCaseIdentifiers()) {
					schema = schema.toUpperCase();
				} else if (metaData.storesLowerCaseIdentifiers()) {
					schema = schema.toLowerCase();
				}
				rs = metaData.getTables(connection.getCatalog(), schema, "%",
						TYPES);
			} else if (url.indexOf("jdbc:JdbcProgress") > -1) {
				rs = metaData.getTables(null, "%", "%", TYPES);
			} else {
				rs = metaData.getTables(connection.getCatalog(), "%", "%",
						TYPES);
			}

			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				tableNames.add(tableName);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			close(connection);
		}

		Collections.sort(tableNames);
		return tableNames;
	}

	//
	// int 比o小时返回-1，相等时返回0，大于时返回1
	//
	class Entity implements Comparable {
		private Integer beginIndex = 0;

		private String value = null;

		public Entity(Integer beginIndex, String value) {
			this.beginIndex = beginIndex;
			this.value = value;
		}

		public Integer getBeginIndex() {
			return beginIndex;
		}

		public String getValue() {
			return value;
		}

		public int compareTo(Object o) {
			Entity a = (Entity) o;
			if (a == null) {
				return 1;
			}
			//
			// ASC
			//
			if (this.beginIndex > a.getBeginIndex().intValue()) {
				return 1;
			} else if (this.beginIndex < a.getBeginIndex().intValue()) {
				return -1;
			}
			return 0;
		}

	}

}
