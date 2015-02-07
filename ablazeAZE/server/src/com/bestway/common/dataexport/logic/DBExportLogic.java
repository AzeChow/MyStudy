package com.bestway.common.dataexport.logic;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import com.bestway.common.CommonUtils;
import com.bestway.common.dataexport.dao.DBExportDao;
import com.bestway.common.dataexport.entity.JDBCDataSource;
import com.bestway.common.dataexport.entity.JDBCRegion;
import com.bestway.common.dataexport.entity.JDBCRegionSetup;
import com.bestway.common.dataexport.entity.JDBCSqlEvent;
import com.bestway.common.dataexport.entity.JDBCTask;
import com.bestway.common.dataexport.entity.JDBCTaskDetail;
import com.bestway.common.dataexport.entity.TempJDBCColumn;
import com.bestway.common.tools.entity.TempResultSet;

/**
 * @author luosheng 2006/9/1
 * 
 */
public class DBExportLogic {
	private DBExportDao dbExportDao = null;

	private DBImportExportLogic dbImportExportLogic = null;

	public DBExportDao getDbExportDao() {
		return dbExportDao;
	}

	public void setDbExportDao(DBExportDao dbExportDao) {
		this.dbExportDao = dbExportDao;
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
	 * 连接数据库
	 * 
	 * @param db
	 * @return
	 */
	public boolean isConnect(String driverClassName, String url,
			String userName, String password) {
		Connection conn = null;
		try {
			DataSource dataSource = DataSourcePools.getDataSource(
					driverClassName, url, userName, password);
			conn = dataSource.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		} finally {
			close(conn);
		}
		return true;
	}

	/** 执行 sql event */
	public void execuJDBCSqlEvent(JDBCSqlEvent sqlEvent) {
		JDBCDataSource jdbcDataSource = sqlEvent.getJdbcDataSource();

		Connection conn = null;
		try {
			DataSource dataSource = DataSourcePools.getDataSource(
					jdbcDataSource.getDriverClassName(), jdbcDataSource
							.getUrl(), jdbcDataSource.getUserName(),
					jdbcDataSource.getPassword());
			conn = dataSource.getConnection();
			String sql = dbImportExportLogic.formatSql(sqlEvent.getSqlStr(),
					CommonUtils.getCompany());
			executeSql(conn, sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		} finally {
			close(conn);
		}
	}

	/** 执行 sql */
	private void executeSql(Connection conn, String sql) throws SQLException {
		conn.setAutoCommit(true);
		Statement statement = conn.createStatement();
		statement.execute(sql);
		statement.close();
	}

	/** 获得只有列的数据集 */
	public TempResultSet getNoDataTempResultSet(JDBCDataSource jdbcDataSource,
			String sql) {
		sql = getSqlAddWhere(sql);
		return getTempResultSet2(jdbcDataSource, sql);
	}

	
	/** 获得结果集 */
	public TempResultSet getTempResultSet(JDBCDataSource jdbcDataSource,
			String sql) {
		sql = dbImportExportLogic.formatSql(sql,
				CommonUtils.getCompany());
		return getTempResultSet2(jdbcDataSource, sql);
	}
	
	/** 获得结果集 */
	public TempResultSet getTempResultSet2(JDBCDataSource jdbcDataSource,
			String sql) {
		TempResultSet tempRs = new TempResultSet();
		ResultSet resultSet = null;
		Connection connection = null;
		boolean isOracle = jdbcDataSource.getDriverClassName().contains("OracleDriver");
		try {
			DataSource dataSource = DataSourcePools.getDataSource(
					jdbcDataSource.getDriverClassName(), jdbcDataSource
							.getUrl(), jdbcDataSource.getUserName(),
					jdbcDataSource.getPassword());

			connection = dataSource.getConnection();
			connection.setAutoCommit(true);
			Statement stmt = connection.createStatement();
			resultSet = stmt.executeQuery(sql);

			ResultSetMetaData metaData = resultSet.getMetaData();

			int numberOfColumns = metaData.getColumnCount();
			String[] columnNames = new String[numberOfColumns];
			for (int column = 0; column < numberOfColumns; column++) {
				columnNames[column] = metaData.getColumnLabel(column + 1);
			}
			//
			// Get all rows.
			//
			List<List> rows = new ArrayList<List>();
			while (resultSet.next()) {
				List<Object> newRow = new ArrayList<Object>();
				for (int i = 1; i <= columnNames.length; i++) {
					Object obj = resultSet.getObject(i);
					if (isOracle) {
						if (obj instanceof oracle.sql.TIMESTAMP) {// oracle 10
							// 的兼容性
							oracle.sql.TIMESTAMP oTimestamp = (oracle.sql.TIMESTAMP) obj;
							Timestamp t = oTimestamp.timestampValue();
							obj = t;
						}
						if (obj instanceof oracle.sql.NUMBER) {// 
							oracle.sql.NUMBER number = (oracle.sql.NUMBER) obj;
							obj = number.dateValue();
						}
					}
					newRow.add(obj);
				}
				rows.add(newRow);
			}
			resultSet.close();
			tempRs.setColumnNames(columnNames);
			tempRs.setRows(rows);
			stmt.close();

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			close(connection);
		}
		return tempRs;
	}

	/** connection 由连接池管理 没有执行,只是测试 */
	public int executeUpdateSql(JDBCDataSource jdbcDataSource,String sql) {
		Connection connection = null;
		try {
			DataSource dataSource = DataSourcePools.getDataSource(
					jdbcDataSource.getDriverClassName(), jdbcDataSource
							.getUrl(), jdbcDataSource.getUserName(),
					jdbcDataSource.getPassword());

			connection = dataSource.getConnection();

			Statement stmt = connection.createStatement();
			connection.setAutoCommit(false);
			String sqls=this.dbImportExportLogic.formatSql(sql, CommonUtils.getCompany());
			int size = stmt.executeUpdate(sqls);
			// 回卷
			connection.rollback();
			return size;
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				if (connection != null) {// 回卷
					connection.rollback();
				}
			} catch (SQLException e) {
			}
			throw new RuntimeException(ex);
		} finally {
			close(connection);
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
			String sqls=this.dbImportExportLogic.formatSql(sql, CommonUtils.getCompany());
			boolean b = stmt.execute(sqls);
			// 回卷
			connection.rollback();
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

	/** 获得连接数据库表名 */
	public List<String> getTableNames(JDBCDataSource jdbcDataSource) {
		List<String> tableNames = new ArrayList<String>();
		final String[] TYPES = { "TABLE", "VIEW" };
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

	/** 获得连接数据库列名 */
	public List<String> getColumnNames(JDBCDataSource jdbcDataSource,
			String tableName) {

		List<String> columns = new ArrayList<String>();

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
			ResultSet cols = null;
			if (url.indexOf("jdbc:JdbcProgress") > -1) {
				cols = connection.getMetaData().getColumns(null, null,
						tableName, "%");
			} else {
				cols = connection.getMetaData().getColumns(
						connection.getCatalog(), null, tableName, "%");
			}

			while (cols.next()) {
				columns.add(cols.getString("COLUMN_NAME"));
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			close(connection);
		}
		Collections.sort(columns);
		return columns;
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

	/** 获得连接数据库视图列名 */
	public List<TempJDBCColumn> getSrcTempJDBCColumn(
			JDBCDataSource jdbcDataSource, String viewSql) {
		//
		// 无数据的Sql结果
		//
		String sql = getSqlAddWhere(viewSql);

		List<TempJDBCColumn> columns = new ArrayList<TempJDBCColumn>();

		Connection connection = null;
		try {
			DataSource dataSource = DataSourcePools.getDataSource(
					jdbcDataSource.getDriverClassName(), jdbcDataSource
							.getUrl(), jdbcDataSource.getUserName(),
					jdbcDataSource.getPassword());
			connection = dataSource.getConnection();
			if (!connection.getAutoCommit()) {
				connection.setAutoCommit(true);
			}
			Statement statement = connection.createStatement();
			ResultSet cols = statement.executeQuery(sql);
			ResultSetMetaData metaData = cols.getMetaData();

			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				TempJDBCColumn column = new TempJDBCColumn();
				//
				// sqlserver jdbc driver getColumnLabel == getColumnName
				// oracle jdbc driver getColumnLabel == getColumnName
				// mysql jdbc driver getColumnLabel(代表真正的别名) !=
				// getColumnName(代表实际数据库中的字段名) 并不相等
				//
				String fieldName = metaData.getColumnLabel(i);
				Integer fieldType = metaData.getColumnType(i);
				String fieldTypeDesc = metaData.getColumnTypeName(i);

				column.setFieldName(fieldName);
				column.setFieldType(fieldType);
				column.setFieldTypeDesc(fieldTypeDesc);
				columns.add(column);
			}
			cols.close();

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			close(connection);
		}
		Collections.sort(columns);
		return columns;
	}

	private static String getSqlAddWhere(String sql) {
		//
		// 要注意顺序
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
		return sql;
	}

	/** 转抄 JDBCRegion */
	public JDBCRegion copyJDBCRegion(JDBCRegion srcJDBCRegion) {

		JDBCRegion newJDBCRegion = srcJDBCRegion;
		String jdbcRegionId = newJDBCRegion.getId();
		newJDBCRegion.setId(null);
		newJDBCRegion.setRegionName("copy_" + newJDBCRegion.getRegionName());
		this.dbExportDao.saveJDBCRegion(newJDBCRegion);

		List<JDBCRegionSetup> jdbcRegionSetupList = this.dbExportDao
				.findJDBCRegionSetup(jdbcRegionId);
		for (JDBCRegionSetup jdbcRegionSetup : jdbcRegionSetupList) {
			jdbcRegionSetup.setId(null);
			jdbcRegionSetup.setJdbcRegion(newJDBCRegion);
			this.dbExportDao.saveJDBCRegionSetup(jdbcRegionSetup);
		}

		return newJDBCRegion;
	}

	/** 删除 JDBCTask */
	public void deleteJDBCTask(JDBCTask jdbcTask) {
		List<JDBCTaskDetail> listDetail = this.dbExportDao
				.findJDBCTaskDetail(jdbcTask);
		for (JDBCTaskDetail jdbcTaskDetail : listDetail) {
			this.dbExportDao.deleteJDBCTaskDetail(jdbcTaskDetail);
		}
		this.dbExportDao.deleteJDBCTask(jdbcTask);
	}

	public DBImportExportLogic getDbImportExportLogic() {
		return dbImportExportLogic;
	}

	public void setDbImportExportLogic(DBImportExportLogic importExportLogic) {
		dbImportExportLogic = importExportLogic;
	}

}
