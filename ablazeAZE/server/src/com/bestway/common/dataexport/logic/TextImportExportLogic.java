package com.bestway.common.dataexport.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.GbkToBig5Flag;
import com.bestway.common.constant.ImportDataMode;
import com.bestway.common.constant.JDBCFlag;
import com.bestway.common.dataexport.dao.DBExportDao;
import com.bestway.common.dataexport.dao.TextExportDao;
import com.bestway.common.dataexport.entity.DBToTxtRegion;
import com.bestway.common.dataexport.entity.JDBCDataSource;
import com.bestway.common.dataexport.entity.JDBCParameter;
import com.bestway.common.dataexport.entity.JDBCSqlEvent;
import com.bestway.common.dataexport.entity.TxtDBTaskDetail;
import com.bestway.common.dataexport.entity.TxtToDBRegion;
import com.bestway.common.dataexport.entity.TxtToDBRegionSetup;
import com.bestway.common.tools.entity.TempResultSet;

/**
 *  TextImportExportLogic 维护 checked 2008-10-11
 * @author luosheng 2006/9/15
 *         <ul>
 *         <li> 1.实现基于JDBC,DBCP的，通用性的，任意数据库与文本之间的, 文本导入导出接口
 *         <li> 2.实现基于JDBC的导入导出接口,自定义缓存查询导入导出功能
 *         <li> 3.实现基于JDBC的批量导入导出功能
 *         <li> 4.实现文本导入到DB的接口自定义批量更新导入导出功能
 *         </ul>
 * 
 */
public class TextImportExportLogic {
	/**数据导出Dao*/
	private DBExportDao			dbExportDao			= null;
	/**数据文本导出Dao*/
	private TextExportDao		textExportDao		= null;
	/**log*/
	private static Log			logger				= LogFactory
															.getLog(TextImportExportLogic.class);
	/**获得数据批default大小*/
	private static final int FETCH_BATCH_SIZE = 100;
	/**更新数据批default大小*/
	private static final int UPDATE_BATCH_SIZE = 100;
	
	/**get数据导出Dao*/
	public DBExportDao getDbExportDao() {
		return dbExportDao;
	}
	/**set数据导出Dao*/
	public void setDbExportDao(DBExportDao dbExportDao) {
		this.dbExportDao = dbExportDao;
	}

	/**get数据文本导出Dao*/
	public TextExportDao getTextExportDao() {
		return textExportDao;
	}
	/**set数据文本导出Dao*/
	public void setTextExportDao(TextExportDao textExportDao) {
		this.textExportDao = textExportDao;
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

	/** 获得执行DB导出到文本的数据源 */
	public TempResultSet getDBViewData(TxtDBTaskDetail txtDBTaskDetail) {
		int taskType = txtDBTaskDetail.getTaskType();
		//
		// 是域对应类型
		//
		if (taskType != TxtDBTaskDetail.DB_TO_TXT_REGION_TYPE) {
			return null;
		}
		List<Gbtobig> gbkAndBig5 = this.dbExportDao.findGbtobig();
		//
		// 获得简体为key的 gbkKeyMap
		//
		Map<String, String> gbkKeyMap = this.getGbkKeyMap(gbkAndBig5);
		//
		// 获得繁体为key的 big5KeyMap
		//
		Map<String, String> big5KeyMap = this.getBig5KeyMap(gbkAndBig5);

		DBToTxtRegion dbToTxtRegion = txtDBTaskDetail.getDbToTxtRegion();

		if (dbToTxtRegion == null) {
			return null;
		}

		return getDBViewData(dbToTxtRegion, gbkKeyMap, big5KeyMap);
	}

	/** 获得结果集 */
	private TempResultSet getDBViewData(DBToTxtRegion dbToTxtRegion,
			Map<String, String> gbkKeyMap, Map<String, String> big5KeyMap) {
		//
		// 从第几行导出
		//
		int beginRow = dbToTxtRegion.getExportRowNumber() == null ? 0
				: dbToTxtRegion.getExportRowNumber();
		//
		// 繁简转换
		//
		String gbkToBig5Flag = dbToTxtRegion.getGbkToBig5Flag() == null ? GbkToBig5Flag.NO
				: dbToTxtRegion.getGbkToBig5Flag();
		//
		// 获得视图数据源
		//
		JDBCDataSource jdbcDataSource = dbToTxtRegion.getSrcJDBCView()
				.getJdbcDataSource();
		String sql = dbToTxtRegion.getSrcJDBCView().getSqlScript();

		TempResultSet tempRs = new TempResultSet();
		ResultSet resultSet = null;
		Connection connection = null;
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
				String tempValue = metaData.getColumnLabel(column + 1);
				if (GbkToBig5Flag.BIG5_TO_GBK.equals(gbkToBig5Flag)) {// 繁转简
					tempValue = big5ToGbk((String) tempValue, big5KeyMap);
				} else if (GbkToBig5Flag.GBK_TO_BIG5.equals(gbkToBig5Flag)) { // 简转繁
					tempValue = gbkToBig5((String) tempValue, gbkKeyMap);
				}
				columnNames[column] = tempValue;
			}
			//
			// Get all rows.
			//
			List<List> rows = new ArrayList<List>();
			for (int row = 1; resultSet.next(); row++) {
				//
				// 从第几行开始导入
				//
				if (row < beginRow) {
					continue;
				}
				List<Object> newRow = new ArrayList<Object>();
				for (int i = 1; i <= columnNames.length; i++) {
					Object obj = resultSet.getObject(i);
					if (obj instanceof oracle.sql.TIMESTAMP) {// oracle 10
						// 的兼容性
						oracle.sql.TIMESTAMP oTimestamp = (oracle.sql.TIMESTAMP) obj;
						Timestamp t = oTimestamp.timestampValue();
						newRow.add(t);
					} else if (obj != null && obj instanceof String) {
						String str = ((String) obj);
						if (GbkToBig5Flag.BIG5_TO_GBK.equals(gbkToBig5Flag)) {// 繁转简
							str = big5ToGbk(str, big5KeyMap);
						} else if (GbkToBig5Flag.GBK_TO_BIG5
								.equals(gbkToBig5Flag)) { // 简转繁
							str = gbkToBig5(str, gbkKeyMap);
						}
						newRow.add(str);
					} else {
						newRow.add(obj);
					}
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

	/** 执行事件 */
	public void txtDBTaskEventExecute(Company company,
			TxtDBTaskDetail txtDBTaskDetail, int seqNum) {
		String info;
		JDBCSqlEvent jdbcSqlEvent = txtDBTaskDetail.getJdbcSqlEvent();
		if (jdbcSqlEvent == null) {
			info = "序号 = " + seqNum + " 的事件类型为空 ";
			logger.info(info);
			throw new RuntimeException(info);
		}
		JDBCDataSource eventJDBCDataSource = jdbcSqlEvent.getJdbcDataSource();
		DataSource dataSource = DataSourcePools.getDataSource(
				eventJDBCDataSource.getDriverClassName(), eventJDBCDataSource
						.getUrl(), eventJDBCDataSource.getUserName(),
				eventJDBCDataSource.getPassword());

		Connection eventConn = null;
		try {
			eventConn = dataSource.getConnection();
			if (eventConn.getAutoCommit() == false) {
				eventConn.setAutoCommit(true);
			}
			Statement stmt = eventConn.createStatement();

			logger.info("[JBCUS TEXT EXPORT]  开始执行事件 事件名 == "
					+ jdbcSqlEvent.getName());

			String eventSql = jdbcSqlEvent.getSqlStr();

			eventSql = formatSql(eventSql, company);

			stmt.execute(eventSql);

			stmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			close(eventConn);
		}
		logger.info("[JBCUS TEXT EXPORT]  结束执行事件 事件名 == "
				+ jdbcSqlEvent.getName());
	}

	/** 执行文本导出到DB */
	public void executeTxtToDBTask(TxtToDBRegion txtToDBRegion,
			TempResultSet tempResultSet) {
		List<Gbtobig> gbkAndBig5 = this.dbExportDao.findGbtobig();
		//
		// 获得简体为key的 gbkKeyMap
		//
		Map<String, String> gbkKeyMap = this.getGbkKeyMap(gbkAndBig5);
		//
		// 获得繁体为key的 big5KeyMap
		//
		Map<String, String> big5KeyMap = this.getBig5KeyMap(gbkAndBig5);
		//
		// 执行
		//
		txtToDBRegionExport(txtToDBRegion, tempResultSet, gbkKeyMap, big5KeyMap);
	}

	/** Txt To DB 域导入导出 */
	private void txtToDBRegionExport(TxtToDBRegion txtToDBRegion,
			TempResultSet tempResultSet, Map<String, String> gbkKeyMap,
			Map<String, String> big5KeyMap) {

		String info;
		String regionName = txtToDBRegion.getRegionName();
		logger.info("开始执行文本导出到DB域对应名 = " + regionName);
		Company company = (Company) CommonUtils.getCompany();
		//
		// GbkToBig5Flag 繁转简标志
		//
		String gbkToBig5Flag = txtToDBRegion.getGbkToBig5Flag();
		//
		// 获得对应明细
		//
		List<TxtToDBRegionSetup> txtToDBRegionSetupList = this.textExportDao
				.findTxtToDBRegionSetup(txtToDBRegion.getId());
		if (txtToDBRegionSetupList == null
				|| txtToDBRegionSetupList.size() <= 0) {
			info = "开始导入导出域对应 = " + regionName + " 没有明细的对应记录请检查域对应设置";
			logger.info(info);
			throw new RuntimeException(info);
		}

		//
		// 1.获得对应明细
		// 2.获得要导出到DB结果值,以及列名对应(值跟序号的对应)
		//		

		//
		// key = 导入的列的序号从 0,1,2,3,4 ...., value = txtToDBRegion 中对应的列名
		//
		Map<Integer, String> fieldMap = new HashMap<Integer, String>();
		String[] columnNames = tempResultSet.getColumnNames();
		for (int i = 0; i < columnNames.length; i++) {
			//
			// 请注意在这里转成了序号,到时候从 tempResultSet.getRows() 中获得对应的值
			//
			fieldMap.put(i, columnNames[i]);
		}

		try {
			//
			// 获得导入数据的方式
			//
			int importDataMode = txtToDBRegion.getImportDataMode() == null ? ImportDataMode.ADD_MODE
					: txtToDBRegion.getImportDataMode();

			if (importDataMode == ImportDataMode.ADD_MODE) { // 直接保存,不检查

				txtToDBRegionExportAddMode(txtToDBRegion, tempResultSet,
						gbkKeyMap, big5KeyMap, company, gbkToBig5Flag,
						txtToDBRegionSetupList, fieldMap);

			} else if (importDataMode == ImportDataMode.MODIFY_MODE) {// 更新方式导入

				txtToDBRegionExportModifyMode(txtToDBRegion, tempResultSet,
						gbkKeyMap, big5KeyMap, company, gbkToBig5Flag,
						txtToDBRegionSetupList, fieldMap);
			}
			//
			// 注意关闭
			//			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
			throw new RuntimeException(ex);
		}
	}

	/** 直接保存,不检查 */
	private void txtToDBRegionExportAddMode(TxtToDBRegion txtToDBRegion,
			TempResultSet tempResultSet, Map<String, String> gbkKeyMap,
			Map<String, String> big5KeyMap, Company company,
			String gbkToBig5Flag,
			List<TxtToDBRegionSetup> txtToDBRegionSetupList,
			Map<Integer, String> fieldMap) {
		//
		// 获得目的数据源,目的数据表名
		//
		JDBCDataSource destJDBCDataSource = txtToDBRegion
				.getDestJDBCDataSource();
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
			String destTableName = txtToDBRegion.getDestTableName();
			List<String> destColumnNames = new ArrayList<String>();
			//
			// 获得格式化后参数转换 SQL
			//
			Map<String, String> destColumnFormatSql = new HashMap<String, String>();
			//
			// 缓存 parameter sql 数据
			//
			Map<String, Map<String, Object>> cacheValuesMap = new HashMap<String, Map<String, Object>>();

			List<DataExportUtils.Item> items = new ArrayList<DataExportUtils.Item>();

			for (TxtToDBRegionSetup txtToDBRegionSetup : txtToDBRegionSetupList) {
				String destColumnName = txtToDBRegionSetup.getDestFieldName();
				destColumnNames.add(destColumnName);
				//
				// 是否缓存 转换SQL
				//
				boolean isCache = txtToDBRegionSetup.getIsCache() == null ? false
						: txtToDBRegionSetup.getIsCache();

				String jdbcFlag = txtToDBRegionSetup.getJdbcFlag();
				if (JDBCFlag.SQL.equals(jdbcFlag) && !isCache) {

					String paraSql = this.formatSql(txtToDBRegionSetup
							.getSqlStr(), company);
					items = DataExportUtils.getItems(paraSql);
					//
					// 用参数名 @parameter 转成 ?
					// 
					paraSql = DataExportUtils.getParaSql(paraSql);
					destColumnFormatSql.put(destColumnName, paraSql);

				} else if (JDBCFlag.SQL.equals(jdbcFlag) && isCache) {
					String cacheSql = this.formatSql(txtToDBRegionSetup
							.getSqlStr(), company);
					items = DataExportUtils.getItems(cacheSql);
					//
					// 用参数名 @parameter 转成 ?
					// 
					cacheSql = DataExportUtils.getCacheSql(cacheSql, "1=1");
					Map<String, Object> cacheValues = new HashMap<String, Object>();
					//
					// 获得参数SQL数据源
					//
					Connection paraConn = null;
					try {
						JDBCDataSource paraJDBCDataSource = txtToDBRegionSetup
								.getParaJDBCDataSource();
						DataSource paraDataSource = DataSourcePools
								.getDataSource(paraJDBCDataSource
										.getDriverClassName(),
										paraJDBCDataSource.getUrl(),
										paraJDBCDataSource.getUserName(),
										paraJDBCDataSource.getPassword());
						paraConn = paraDataSource.getConnection();
						if (paraConn.getAutoCommit() == false) {
							paraConn.setAutoCommit(true);
						}
						Statement stmtSelect = paraConn.createStatement();
						stmtSelect.setFetchSize(FETCH_BATCH_SIZE);
						ResultSet paraResultSet = stmtSelect
								.executeQuery(cacheSql);
						ResultSetMetaData rsMetaData = paraResultSet
								.getMetaData();
						int columnCount = rsMetaData.getColumnCount();
						while (paraResultSet.next()) {
							String key = "";
							Object value = null;
							for (int i = 1; i <= columnCount; i++) {
								Object tempValue = paraResultSet.getObject(i);
								if (tempValue != null
										&& tempValue instanceof String) {
									if (GbkToBig5Flag.BIG5_TO_GBK
											.equals(gbkToBig5Flag)) {// 繁转简
										tempValue = big5ToGbk(
												(String) tempValue, big5KeyMap);
									} else if (GbkToBig5Flag.GBK_TO_BIG5
											.equals(gbkToBig5Flag)) { // 简转繁
										tempValue = gbkToBig5(
												(String) tempValue, gbkKeyMap);
									}
								}
								if (i < columnCount) {
									if (tempValue == null) {
										continue;
									}
									if (tempValue instanceof Date) {
										SimpleDateFormat bartDateFormat = new SimpleDateFormat(
												"yyyy-MM-dd");
										key += bartDateFormat
												.format((Date) tempValue);
									} else {
										key += tempValue.toString().trim();
									}
								} else {
									value = tempValue;
								}
							}
							cacheValues.put(key, value);
						}
						paraResultSet.close();
						//
						// 缓存 parameter sql 数据
						//
						cacheValuesMap.put(destColumnName, cacheValues);

					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error(ex.toString());
						throw new RuntimeException(ex);
					} finally {
						close(paraConn);
					}
				}
			}
			String insertSql = getInsertSql(destTableName, destColumnNames,destJDBCDataSource);
			PreparedStatement stmtInsert = destConn.prepareStatement(insertSql);

			logger.info("---------" + insertSql);

			boolean hasExecuteBatch = false;
			boolean isProgress = DataExportUtils.isProgressDataSource(destJDBCDataSource);
			List<List> rows = tempResultSet.getRows();
			for (int i = 0; i < rows.size(); i++) {
				List row = rows.get(i);
				//
				// 以文本导入列名 为 key 值
				//
				Map<String, Object> rowMap = this.getRowMap(row, fieldMap,
						gbkToBig5Flag, gbkKeyMap, big5KeyMap);
				//
				// 设置参数
				//
				this.setInsertParameters(stmtInsert, txtToDBRegionSetupList,
						rowMap, destColumnFormatSql, items, cacheValuesMap,
						gbkToBig5Flag, gbkKeyMap, big5KeyMap, company);

				//
				// 如果是progressDataSource 的话那么将直接执行
				//
				if (isProgress) {					
					stmtInsert.execute();
				} else {
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
			logger.error(ex.toString());
			throw new RuntimeException(ex);
		} finally {
			close(destConn);
		}
	}

	/** 设置jdbc 导入参数 */
	private void setInsertParameters(PreparedStatement stmt,
			List<TxtToDBRegionSetup> txtToDBRegionSetupList,
			Map<String, Object> rowMap,
			Map<String, String> destColumnFormatSql,
			List<DataExportUtils.Item> items,
			Map<String, Map<String, Object>> cacheValuesMap, String gbkToBig5,
			Map<String, String> gbkKeyMap, Map<String, String> big5KeyMap,
			Company company) throws SQLException {

		for (int i = 0; i < txtToDBRegionSetupList.size(); i++) {
			TxtToDBRegionSetup txtToDBRegionSetup = txtToDBRegionSetupList
					.get(i);
			String destFieldName = txtToDBRegionSetup.getDestFieldName();
			Integer destFieldType = txtToDBRegionSetup.getDestFieldType();
			String srcFieldName = txtToDBRegionSetup.getSrcFieldName();
			boolean isCache = txtToDBRegionSetup.getIsCache() == null ? false
					: txtToDBRegionSetup.getIsCache();

			String jdbcFlag = txtToDBRegionSetup.getJdbcFlag();

			if (JDBCFlag.NO.equals(jdbcFlag)) { // 直接对应数值
				Object value = rowMap.get(srcFieldName);
				if (value != null) {
					if (value instanceof Date) {
						SimpleDateFormat bartDateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");
						value = bartDateFormat.format((Date) value);
					}
					value = getValueByType(value.toString(), destFieldType
							.intValue());
					stmt.setObject(i + 1, value);
				} else {
					stmt.setNull(i + 1, destFieldType);
				}
			} else if (JDBCFlag.CURRENT_COMPANY_ID.equals(jdbcFlag)) {// 当前公司ID

				stmt.setObject(i + 1, company.getId());

			} else if (JDBCFlag.GUID.equals(jdbcFlag)) {// 32 位 GUID

				UUID uuid = UUID.randomUUID();
				String id = uuid.toString();
				id = id.replace("-", "");
				stmt.setObject(i + 1, id);

			} else if (JDBCFlag.SQL.equals(jdbcFlag) && !isCache) { // 自定义SQL
				// 转换参数 没有缓存
				String paraSql = destColumnFormatSql.get(destFieldName);
				//
				// 获得目的数据源,目的数据表名
				//
				Connection paraConn = null;
				try {
					JDBCDataSource paraJDBCDataSource = txtToDBRegionSetup
							.getParaJDBCDataSource();
					DataSource paraDataSource = DataSourcePools.getDataSource(
							paraJDBCDataSource.getDriverClassName(),
							paraJDBCDataSource.getUrl(), paraJDBCDataSource
									.getUserName(), paraJDBCDataSource
									.getPassword());
					paraConn = paraDataSource.getConnection();
					if (paraConn.getAutoCommit() == false) {
						paraConn.setAutoCommit(true);
					}
					PreparedStatement stmtSelect = paraConn
							.prepareStatement(paraSql);
					//
					// 是否是繁体数据库
					//
					boolean isBig5DataBase = txtToDBRegionSetup
							.getIsBig5DataBase() == null ? false
							: txtToDBRegionSetup.getIsBig5DataBase();

					for (int j = 0; j < items.size(); j++) {
						DataExportUtils.Item item = items.get(j);
						Object paraValue = rowMap.get(item.getValue());

						if (paraValue != null && paraValue instanceof String) {
							if (isBig5DataBase) {// 简转繁
								paraValue = gbkToBig5((String) paraValue,
										gbkKeyMap);
							} else { // 繁转简
								paraValue = big5ToGbk((String) paraValue,
										big5KeyMap);
							}
						}
						stmtSelect.setObject(j + 1, paraValue);
					}
					stmtSelect.addBatch();
					ResultSet rs = stmtSelect.executeQuery();
					Object value = null;
					while (rs.next()) {
						value = rs.getObject(1); // 因为只能返回一列,并且一行
						break;
					}
					rs.close();
					if (value != null) {
						if (value instanceof Date) {
							SimpleDateFormat bartDateFormat = new SimpleDateFormat(
									"yyyy-MM-dd");
							value = bartDateFormat.format((Date) value);
						}
						value = getValueByType(value.toString(), destFieldType
								.intValue());
						if (value instanceof String) {
							if (GbkToBig5Flag.BIG5_TO_GBK.equals(gbkToBig5)) {// 繁转简
								value = big5ToGbk((String) value, big5KeyMap);
							} else if (GbkToBig5Flag.GBK_TO_BIG5
									.equals(gbkToBig5)) { // 简转繁
								value = gbkToBig5((String) value, gbkKeyMap);
							}
						}
						stmt.setObject(i + 1, value);
					} else {
						stmt.setNull(i + 1, destFieldType);
					}
				} catch (Exception ex) {
					logger.error(ex.toString());
					throw new RuntimeException(ex);
				} finally {
					close(paraConn);
				}

			} else if (JDBCFlag.SQL.equals(jdbcFlag) && isCache) { // 自定义SQL
				// 转换参数 有缓存
				Map<String, Object> cacheValues = cacheValuesMap
						.get(destFieldName);
				//
				// @源视图值 trim()
				//
				String key = "";
				for (int j = 0; j < items.size(); j++) {
					DataExportUtils.Item item = items.get(j);
					Object tempValue = rowMap.get(item.getValue());
					if (tempValue == null) {
						continue;
					}
					if (tempValue instanceof Date) {
						SimpleDateFormat bartDateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");
						key += bartDateFormat.format((Date) tempValue);
					} else {
						key += tempValue.toString().trim();
					}
				}
				Object value = cacheValues.get(key);
				if (value != null) {
					if (value instanceof Date) {
						SimpleDateFormat bartDateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");
						value = bartDateFormat.format((Date) value);
					}
					value = getValueByType(value.toString(), destFieldType
							.intValue());
					stmt.setObject(i + 1, value);
				} else {
					stmt.setNull(i + 1, destFieldType);
				}
			}
		}
	}

	/** 更新方式导入 */
	private void txtToDBRegionExportModifyMode(TxtToDBRegion txtToDBRegion,
			TempResultSet tempResultSet, Map<String, String> gbkKeyMap,
			Map<String, String> big5KeyMap, Company company,
			String gbkToBig5Flag,
			List<TxtToDBRegionSetup> txtToDBRegionSetupList,
			Map<Integer, String> fieldMap) {
		String info;
		//
		// 1.检查是否有目的表 key 列 ,可以确定唯一性的栏位,用于更新
		//
		List<String> keyColumns = new ArrayList<String>();
		for (TxtToDBRegionSetup txtToDBRegionSetup : txtToDBRegionSetupList) {
			String destFieldName = txtToDBRegionSetup.getDestFieldName();
			if (txtToDBRegionSetup.getIsKey() == null
					|| !txtToDBRegionSetup.getIsKey().booleanValue()) {
				continue;
			}
			keyColumns.add(destFieldName);
		}
		if (keyColumns.size() <= 0) {
			info = "文本导出到DB更新导入的域名 = " + txtToDBRegion.getRegionName()
					+ " 的明细域对应对象选择的是更新导入方式" + ",\n但是没有设置能确定数据行唯一性的列. ";
			logger.info(info);
			throw new RuntimeException(info);
		}
		//
		// 2.primary key
		//
		List<String> primaryKeys = new ArrayList<String>();
		for (TxtToDBRegionSetup txtToDBRegionSetup : txtToDBRegionSetupList) {
			String destFieldName = txtToDBRegionSetup.getDestFieldName();
			if (txtToDBRegionSetup.getIsPrimaryKey() == null
					|| !txtToDBRegionSetup.getIsPrimaryKey().booleanValue()) {
				continue;
			}
			primaryKeys.add(destFieldName);
		}

		//
		// 获得目的数据源,目的数据表名
		//
		JDBCDataSource destJDBCDataSource = txtToDBRegion
				.getDestJDBCDataSource();
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
			String destTableName = txtToDBRegion.getDestTableName();
			List<String> destColumnNames = new ArrayList<String>();
			//
			// 获得格式化后参数转换 SQL
			//
			Map<String, String> destColumnFormatSql = new HashMap<String, String>();
			//
			// 缓存 parameter sql 数据
			//
			Map<String, Map<String, Object>> cacheValuesMap = new HashMap<String, Map<String, Object>>();

			List<DataExportUtils.Item> items = new ArrayList<DataExportUtils.Item>();

			for (TxtToDBRegionSetup txtToDBRegionSetup : txtToDBRegionSetupList) {
				String destColumnName = txtToDBRegionSetup.getDestFieldName();
				destColumnNames.add(destColumnName);
				//
				// 是否缓存 转换SQL
				//
				boolean isCache = txtToDBRegionSetup.getIsCache() == null ? false
						: txtToDBRegionSetup.getIsCache();

				String jdbcFlag = txtToDBRegionSetup.getJdbcFlag();
				if (JDBCFlag.SQL.equals(jdbcFlag) && !isCache) {

					String paraSql = this.formatSql(txtToDBRegionSetup
							.getSqlStr(), company);
					items = DataExportUtils.getItemsValueNoAt(paraSql);
					//
					// 用参数名 @parameter 转成 ?
					// 
					paraSql = DataExportUtils.getParaSql(paraSql);
					destColumnFormatSql.put(destColumnName, paraSql);

				} else if (JDBCFlag.SQL.equals(jdbcFlag) && isCache) {
					String cacheSql = this.formatSql(txtToDBRegionSetup
							.getSqlStr(), company);
					items = DataExportUtils.getItemsValueNoAt(cacheSql);
					//
					// 用参数名 @parameter 转成 ?
					// 
					cacheSql = DataExportUtils.getCacheSql(cacheSql, "1=1");
					Map<String, Object> cacheValues = new HashMap<String, Object>();
					//
					// 获得参数SQL数据源
					//
					Connection paraConn = null;
					try {
						JDBCDataSource paraJDBCDataSource = txtToDBRegionSetup
								.getParaJDBCDataSource();
						DataSource paraDataSource = DataSourcePools
								.getDataSource(paraJDBCDataSource
										.getDriverClassName(),
										paraJDBCDataSource.getUrl(),
										paraJDBCDataSource.getUserName(),
										paraJDBCDataSource.getPassword());
						paraConn = paraDataSource.getConnection();
						if (paraConn.getAutoCommit() == false) {
							paraConn.setAutoCommit(true);
						}
						Statement stmtSelect = paraConn.createStatement();
						stmtSelect.setFetchSize(FETCH_BATCH_SIZE);
						ResultSet paraResultSet = stmtSelect
								.executeQuery(cacheSql);
						ResultSetMetaData paraRsMetaData = paraResultSet
								.getMetaData();
						int columnCount = paraRsMetaData.getColumnCount();
						while (paraResultSet.next()) {
							String key = "";
							Object value = null;
							for (int i = 1; i <= columnCount; i++) {
								Object tempValue = paraResultSet.getObject(i);
								if (tempValue != null
										&& tempValue instanceof String) {
									if (GbkToBig5Flag.BIG5_TO_GBK
											.equals(gbkToBig5Flag)) {// 繁转简
										tempValue = big5ToGbk(
												(String) tempValue, big5KeyMap);
									} else if (GbkToBig5Flag.GBK_TO_BIG5
											.equals(gbkToBig5Flag)) { // 简转繁
										tempValue = gbkToBig5(
												(String) tempValue, gbkKeyMap);
									}
								}
								if (i < columnCount) {
									if (tempValue == null) {
										continue;
									}
									if (tempValue instanceof Date) {
										SimpleDateFormat bartDateFormat = new SimpleDateFormat(
												"yyyy-MM-dd");
										key += bartDateFormat
												.format((Date) tempValue);
									} else {
										key += tempValue.toString().trim();
									}
								} else {
									value = tempValue;
								}
							}
							cacheValues.put(key, value);
						}
						paraResultSet.close();
						//
						// 缓存 parameter sql 数据
						//
						cacheValuesMap.put(destColumnName, cacheValues);

					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error(ex.toString());
						throw new RuntimeException(ex);
					} finally {
						close(paraConn);
					}
				}
			}
			//
			// 批量更新单据
			//
			List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();

			List<List> rows = tempResultSet.getRows();

			for (int i = 0; i < rows.size(); i++) {
				List row = rows.get(i);
				//
				// 以文本导入列名 为 key 值
				//
				Map<String, Object> rowMap = this.getRowMap(row, fieldMap,
						gbkToBig5Flag, gbkKeyMap, big5KeyMap);
				batchList.add(rowMap);
				//
				// 100 条执行一次
				//
				if (batchList.size() % UPDATE_BATCH_SIZE == 0) {

					batchUpdateData(company, txtToDBRegionSetupList,
							keyColumns, primaryKeys, destConn, destTableName,
							destColumnNames, destColumnFormatSql,
							cacheValuesMap, items, batchList, gbkToBig5Flag,
							gbkKeyMap, big5KeyMap,destJDBCDataSource);
					//
					// 清除修改过的批
					//
					batchList.clear();
				}
			}
			//
			// 批量修改剩余数据
			//
			batchUpdateData(company, txtToDBRegionSetupList, keyColumns,
					primaryKeys, destConn, destTableName, destColumnNames,
					destColumnFormatSql, cacheValuesMap, items, batchList,
					gbkToBig5Flag, gbkKeyMap, big5KeyMap,destJDBCDataSource);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
			throw new RuntimeException(ex);
		} finally {
			close(destConn);
		}
	}
	/**批量更新*/
	private void batchUpdateData(Company company,
			List<TxtToDBRegionSetup> txtToDBRegionSetupList,
			List<String> keyColumns, List<String> primaryKeys,
			Connection destConn, String destTableName,
			List<String> destColumnNames,
			Map<String, String> destColumnFormatSql,
			Map<String, Map<String, Object>> cacheValuesMap,
			List<DataExportUtils.Item> items,
			List<Map<String, Object>> batchList, String gbkToBig5,
			Map<String, String> gbkKeyMap, Map<String, String> big5KeyMap,
			JDBCDataSource destJDBCDataSource)
			throws SQLException {

		if (batchList.size() <= 0) {
			return;
		}

		List<Map<String, Object>> destRowMaps = this.getDestRowMap(
				txtToDBRegionSetupList, batchList, destColumnFormatSql, items,
				cacheValuesMap, gbkToBig5, gbkKeyMap, big5KeyMap, company);
		//
		// 生成查询语句
		//
		String selectSql = getSelectSql(keyColumns, destTableName, destRowMaps,destJDBCDataSource);

		logger.info("更新导入时开始的查询检查语句 = " + selectSql);

		PreparedStatement stmtSelect = destConn.prepareStatement(selectSql);

		int paraIndex = 1;
		for (int i = 0; i < destRowMaps.size(); i++) { // 修改
			//
			// 获得一个目的字段的为key的(Map) 它包函一行数值与字段的对应()
			//
			Map<String, Object> destRowMap = destRowMaps.get(i);

			for (int j = 0; j < keyColumns.size(); j++) {
				String keyColumn = keyColumns.get(j);
				Object keyValue = destRowMap.get(keyColumn);
				if (keyValue == null) {
					String info = "导入目的表 = " + destTableName + " 的关键列 "
							+ keyColumn + " 的源数据为空 !!";
					logger.info(info);
					throw new RuntimeException(info);
				}
				stmtSelect.setObject(paraIndex++, keyValue);
			}
		}
		stmtSelect.addBatch();
		ResultSet rs = stmtSelect.executeQuery();
		//
		// 所有条件的值
		//
		Map<String, String> existDataKeyMap = new HashMap<String, String>();

		while (rs.next()) {
			String key = "";
			for (int j = 0; j < keyColumns.size(); j++) {
				String keyColumn = keyColumns.get(j);
				Object value = rs.getObject(keyColumn);
				if (value == null) {
					String info = "目的表 = " + destTableName + " 的关键列 "
							+ keyColumn + " 的有为空的数据 !!";
					logger.info(info);
					throw new RuntimeException(info);
				}
				if (value instanceof Date) {
					SimpleDateFormat bartDateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					key += bartDateFormat.format((Date) value);
				} else {
					key += value.toString();
				}
			}
			existDataKeyMap.put(key, key);
		}
		//
		// 获得新增 或 修改的数据集合
		//
		List<Map<String, Object>> insertListMap = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> updateListMap = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < destRowMaps.size(); i++) { // 修改
			//
			// 获得一个目的字段的为key的(Map) 它包函一行数值与字段的对应()
			//
			Map<String, Object> destRowMap = destRowMaps.get(i);
			String key = "";
			for (int j = 0; j < keyColumns.size(); j++) {
				String keyColumn = keyColumns.get(j);
				Object keyValue = destRowMap.get(keyColumn);
				if (keyValue instanceof Date) {
					SimpleDateFormat bartDateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					key += bartDateFormat.format((Date) keyValue);
				} else {
					key += keyValue.toString();
				}
			}
			if (existDataKeyMap.containsKey(key)) {
				updateListMap.add(destRowMap);
			} else {
				insertListMap.add(destRowMap);
			}
		}

		//
		// 进行批量新增
		//
		String insertSql = getInsertSql(destTableName, destColumnNames,destJDBCDataSource);
		PreparedStatement stmtInsert = destConn.prepareStatement(insertSql);

		logger.info("更新导入时开始的新增语句 = " + insertSql);
		
		boolean hasInsertData = false;
		boolean isProgress = DataExportUtils.isProgressDataSource(destJDBCDataSource);
		for (int i = 0; i < insertListMap.size(); i++) {
			//
			// 以目的列 为 key 值
			//
			Map<String, Object> insertRowMap = insertListMap.get(i);
			//
			// 设置参数
			//
			this.setInsertParameters(stmtInsert, txtToDBRegionSetupList,
					insertRowMap);
			//
			// 如果是progressDataSource 的话那么将直接执行
			//
			if (isProgress) {					
				stmtInsert.execute();
			}else{
				stmtInsert.addBatch();
				hasInsertData = true;
			}
		}
		if (hasInsertData) { // 执行批量新增
			stmtInsert.executeBatch();
			stmtInsert.clearBatch();
		}
		//
		// 进行批量修改
		//
		String updateSql = getUpdateSql(destTableName, destColumnNames,
				keyColumns, primaryKeys,destJDBCDataSource);
		PreparedStatement stmtUpdate = destConn.prepareStatement(updateSql);

		logger.info("更新导入时开始的更新语句 = " + updateSql);

		boolean hasUpdateData = false;
		for (int i = 0; i < updateListMap.size(); i++) {
			//
			// 以目的列 为 key 值
			//
			Map<String, Object> updateRowMap = updateListMap.get(i);
			//
			// 设置参数
			//
			this.setUpdateParameters(stmtUpdate, txtToDBRegionSetupList,
					keyColumns, primaryKeys, updateRowMap);
			//
			// 如果是progressDataSource 的话那么将直接执行
			//
			if (isProgress) {					
				stmtUpdate.execute();
			}else{
				stmtUpdate.addBatch();
				hasUpdateData = true;
			}
		}
		if (hasUpdateData) { // 执行批量新增
			stmtUpdate.executeBatch();
			stmtUpdate.clearBatch();
		}
	}

	/** 把视图源列的值转成目的列对应的值 */
	private List<Map<String, Object>> getDestRowMap(
			List<TxtToDBRegionSetup> txtToDBRegionSetupList,
			List<Map<String, Object>> listRowMap,
			Map<String, String> destColumnFormatSql,
			List<DataExportUtils.Item> items,
			Map<String, Map<String, Object>> cacheValuesMap, String gbkToBig5,
			Map<String, String> gbkKeyMap, Map<String, String> big5KeyMap,
			Company company) throws SQLException {

		List<Map<String, Object>> listDestRowMap = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> rowMap : listRowMap) {
			//
			// 把视图源列的值转成目的列对应的值
			//
			Map<String, Object> destRowMap = new HashMap<String, Object>();

			for (int i = 0; i < txtToDBRegionSetupList.size(); i++) {
				TxtToDBRegionSetup txtToDBRegionSetup = txtToDBRegionSetupList
						.get(i);
				String destFieldName = txtToDBRegionSetup.getDestFieldName();
				Integer destFieldType = txtToDBRegionSetup.getDestFieldType();
				String srcFieldName = txtToDBRegionSetup.getSrcFieldName();
				boolean isCache = txtToDBRegionSetup.getIsCache() == null ? false
						: txtToDBRegionSetup.getIsCache();

				String jdbcFlag = txtToDBRegionSetup.getJdbcFlag();

				if (JDBCFlag.NO.equals(jdbcFlag)) { // 直接对应数值
					Object value = rowMap.get(srcFieldName);
					Object tempValue = null;
					if (value != null) {
						if (value instanceof Date) {
							SimpleDateFormat bartDateFormat = new SimpleDateFormat(
									"yyyy-MM-dd");
							value = bartDateFormat.format((Date) value);
						}
						value = getValueByType(value.toString(), destFieldType
								.intValue());
						tempValue = value;
					}
					destRowMap.put(destFieldName, tempValue);

				} else if (JDBCFlag.CURRENT_COMPANY_ID.equals(jdbcFlag)) {// 当前公司ID

					destRowMap.put(destFieldName, company.getId());

				} else if (JDBCFlag.GUID.equals(jdbcFlag)) {// 32 位 GUID

					UUID uuid = UUID.randomUUID();
					String id = uuid.toString();
					id = id.replace("-", "");
					destRowMap.put(destFieldName, id);

				} else if (JDBCFlag.SQL.equals(jdbcFlag) && !isCache) { // 自定义SQL
					// 转换参数 没有缓存
					String paraSql = destColumnFormatSql.get(destFieldName);
					//
					// 获得目的数据源,目的数据表名
					//
					Connection paraConn = null;
					try {
						JDBCDataSource paraJDBCDataSource = txtToDBRegionSetup
								.getParaJDBCDataSource();
						DataSource paraDataSource = DataSourcePools
								.getDataSource(paraJDBCDataSource
										.getDriverClassName(),
										paraJDBCDataSource.getUrl(),
										paraJDBCDataSource.getUserName(),
										paraJDBCDataSource.getPassword());
						paraConn = paraDataSource.getConnection();
						if (paraConn.getAutoCommit() == false) {
							paraConn.setAutoCommit(true);
						}
						PreparedStatement stmtSelect = paraConn
								.prepareStatement(paraSql);

						//
						// 是否是繁体数据库
						//
						boolean isBig5DataBase = txtToDBRegionSetup
								.getIsBig5DataBase() == null ? false
								: txtToDBRegionSetup.getIsBig5DataBase();
						for (int j = 0; j < items.size(); j++) {
							DataExportUtils.Item item = items.get(j);
							Object paraValue = rowMap.get(item.getValue());

							if (paraValue != null
									&& paraValue instanceof String) {
								if (isBig5DataBase) {// 简转繁
									paraValue = gbkToBig5((String) paraValue,
											gbkKeyMap);
								} else { // 繁转简
									paraValue = big5ToGbk((String) paraValue,
											big5KeyMap);
								}
							}
							stmtSelect.setObject(j + 1, paraValue);
						}

						stmtSelect.addBatch();
						ResultSet rs = stmtSelect.executeQuery();
						Object value = null;
						while (rs.next()) {
							value = rs.getObject(1); // 因为只能返回一列,并且一行
							break;
						}
						rs.close();
						if (value != null) {
							if (value instanceof Date) {
								SimpleDateFormat bartDateFormat = new SimpleDateFormat(
										"yyyy-MM-dd");
								value = bartDateFormat.format((Date) value);
							}
							value = getValueByType(value.toString(),
									destFieldType.intValue());
							if (value instanceof String) {
								if (GbkToBig5Flag.BIG5_TO_GBK.equals(gbkToBig5)) {// 繁转简
									value = big5ToGbk((String) value,
											big5KeyMap);
								} else if (GbkToBig5Flag.GBK_TO_BIG5
										.equals(gbkToBig5)) { // 简转繁
									value = gbkToBig5((String) value, gbkKeyMap);
								}
							}
						}
						destRowMap.put(destFieldName, value);

					} catch (Exception ex) {
						logger.error(ex.toString());
						throw new RuntimeException(ex);
					} finally {
						close(paraConn);
					}

				} else if (JDBCFlag.SQL.equals(jdbcFlag) && isCache) { // 自定义SQL
					// 转换参数 有缓存
					Map<String, Object> cacheValues = cacheValuesMap
							.get(destFieldName);
					//
					// @源视图值 trim()
					//
					String key = "";
					for (int j = 0; j < items.size(); j++) {
						DataExportUtils.Item item = items.get(j);
						Object tempValue = rowMap.get(item.getValue());
						if (tempValue == null) {
							continue;
						}
						if (tempValue instanceof Date) {
							SimpleDateFormat bartDateFormat = new SimpleDateFormat(
									"yyyy-MM-dd");
							key += bartDateFormat.format((Date) tempValue);
						} else {
							key += tempValue.toString().trim();
						}
					}
					Object value = cacheValues.get(key);
					if (value != null) {
						if (value instanceof Date) {
							SimpleDateFormat bartDateFormat = new SimpleDateFormat(
									"yyyy-MM-dd");
							value = bartDateFormat.format((Date) value);
						}
						value = getValueByType(value.toString(), destFieldType
								.intValue());
					}
					destRowMap.put(destFieldName, value);
				}
			}
			listDestRowMap.add(destRowMap);
		}
		return listDestRowMap;
	}

	/** 设置jdbc 导入参数 */
	private void setInsertParameters(PreparedStatement stmt,
			List<TxtToDBRegionSetup> txtToDBRegionSetupList,
			Map<String, Object> rowMap) throws SQLException {

		for (int i = 0; i < txtToDBRegionSetupList.size(); i++) {
			TxtToDBRegionSetup txtToDBRegionSetup = txtToDBRegionSetupList
					.get(i);
			String destFieldName = txtToDBRegionSetup.getDestFieldName();
			Integer destFieldType = txtToDBRegionSetup.getDestFieldType();
			Object value = rowMap.get(destFieldName);
			if (value != null) {
				value = getValueByType(value.toString(),destFieldType);
				stmt.setObject(i + 1, value);
			} else {
				stmt.setNull(i + 1, destFieldType);
			}
		}
	}

	/** 设置jdbc 导入参数 */
	private void setUpdateParameters(PreparedStatement stmt,
			List<TxtToDBRegionSetup> txtToDBRegionSetupList,
			List<String> keyColumns, List<String> primaryKeys,
			Map<String, Object> rowMap) throws SQLException {
		//
		// txtToDBRegionSetupList 改变顺序
		//
		List<TxtToDBRegionSetup> newJDBCRegionSetupList = new ArrayList<TxtToDBRegionSetup>();
		Map<String, TxtToDBRegionSetup> tempMap = new HashMap<String, TxtToDBRegionSetup>();

		for (TxtToDBRegionSetup txtToDBRegionSetup : txtToDBRegionSetupList) {
			String destFieldName = txtToDBRegionSetup.getDestFieldName();
			tempMap.put(destFieldName, txtToDBRegionSetup);
			if (keyColumns.contains(destFieldName)
					|| primaryKeys.contains(destFieldName)) {
				continue;
			}
			newJDBCRegionSetupList.add(txtToDBRegionSetup);
		}

		for (int i = 0; i < keyColumns.size(); i++) {
			TxtToDBRegionSetup txtToDBRegionSetup = tempMap.get(keyColumns
					.get(i));
			newJDBCRegionSetupList.add(txtToDBRegionSetup);
		}

		for (int i = 0; i < newJDBCRegionSetupList.size(); i++) {
			TxtToDBRegionSetup txtToDBRegionSetup = newJDBCRegionSetupList
					.get(i);
			String destFieldName = txtToDBRegionSetup.getDestFieldName();
			Integer destFieldType = txtToDBRegionSetup.getDestFieldType();
			Object value = rowMap.get(destFieldName);
			if (value != null) {
				value = getValueByType(value.toString(), destFieldType
						.intValue());
				stmt.setObject(i + 1, value);
			} else {
				stmt.setNull(i + 1, destFieldType);
			}
		}
	}
	/**获得类型*/
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
			oValue = new Integer(sValue);
			break;
		case Types.DATE:
			if(sValue.indexOf("/") > -1){
				sValue= sValue.replaceAll("/", "-");
			}
			oValue = java.sql.Date.valueOf(sValue);
			break;
		case Types.TIMESTAMP:
			if(sValue.indexOf("/") > -1){
				sValue= sValue.replaceAll("/", "-");
			}
			oValue = new java.sql.Timestamp(java.sql.Date.valueOf(sValue)
					.getTime());
			break;
		case Types.TIME:
			if(sValue.indexOf("/") > -1){
				sValue= sValue.replaceAll("/", "-");
			}
			oValue = new java.sql.Time(java.sql.Date.valueOf(sValue).getTime());
			break;
		default:
			break;
		}
		if (oValue == null && sValue != null) {
			logger.info("oValue" + sValue + "   " + oValue);
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
	private Map<String, Object> getRowMap(List row,
			Map<Integer, String> fieldMap, String gbkToBig5,
			Map<String, String> gbkKeyMap, Map<String, String> big5KeyMap)
			throws SQLException {
		Map<String, Object> rowMap = new HashMap<String, Object>();
		for (int i = 0; i < row.size(); i++) {
			String columnName = fieldMap.get(i);
			Object obj = row.get(i);
			if (obj instanceof oracle.sql.TIMESTAMP) {// oracle 10
				// 的兼容性
				oracle.sql.TIMESTAMP oTimestamp = (oracle.sql.TIMESTAMP) obj;
				Timestamp t = oTimestamp.timestampValue();
				rowMap.put(columnName, t);

			} if (obj instanceof oracle.sql.NUMBER) {// 
				oracle.sql.NUMBER number = (oracle.sql.NUMBER) obj;				
				rowMap.put(columnName, number.doubleValue());
				
			}else if (obj != null && obj instanceof String) {

				String str = ((String) obj);
				if (GbkToBig5Flag.BIG5_TO_GBK.equals(gbkToBig5)) {// 繁转简
					str = big5ToGbk(str, big5KeyMap);
				} else if (GbkToBig5Flag.GBK_TO_BIG5.equals(gbkToBig5)) { // 简转繁
					str = gbkToBig5(str, gbkKeyMap);
				}
				rowMap.put(columnName, str.trim());

			} else {

				rowMap.put(columnName, obj);

			}
		}
		return rowMap;
	}

	/** key == 简体 */
	private Map<String, String> getGbkKeyMap(List<Gbtobig> gbkAndBig5) {
		Map<String, String> map = new HashMap<String, String>();
		for (Gbtobig g : gbkAndBig5) {
			map.put(g.getName(), g.getBigname());
		}
		return map;
	}

	/** key == 繁体 */
	private Map<String, String> getBig5KeyMap(List<Gbtobig> gbkAndBig5) {
		Map<String, String> map = new HashMap<String, String>();
		for (Gbtobig g : gbkAndBig5) {
			map.put(g.getBigname(), g.getName());
		}
		return map;
	}

	/** 繁转简 */
	private String big5ToGbk(String s, Map<String, String> big5KeyMap) {
		String reutrnStr = "";
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			String word = String.valueOf(chars[i]);
			if (word.getBytes().length == 2) {
				String gbkWord = big5KeyMap.get(word);
				if (gbkWord != null) {
					word = gbkWord;
				}
			}
			reutrnStr += word;
		}
		return reutrnStr;
	}

	/** 简转繁 */
	private String gbkToBig5(String s, Map<String, String> gbkKeyMap) {
		String reutrnStr = "";
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			String word = String.valueOf(chars[i]);
			if (word.getBytes().length == 2) {
				String big5Word = gbkKeyMap.get(word);
				if (big5Word != null) {
					word = big5Word;
				}
			}
			reutrnStr += word;
		}
		return reutrnStr;
	}

	/** 用参数值替代参数 */
	public String formatSql(String sql, Company company) {
		if (sql == null || sql.trim().equals("")) {
			return "";
		}
		List<JDBCParameter> list = this.dbExportDao.findJDBCParameter(company);
		for (int i = 0; i < list.size(); i++) {
			JDBCParameter p = list.get(i);
			String name = p.getPname();
			String value = p.getPvalue();

			Boolean isnowDate = p.getIsNowDate();
			if (isnowDate != null && isnowDate) {
				String replaceValue = "";
				//
				// 获得增减天数
				//
				int addSubtractDay = p.getAddSubtractDay() == null ? 0 : p
						.getAddSubtractDay();

				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				if (addSubtractDay == 0) {
					replaceValue = bartDateFormat.format(new Date());
				} else {
					Calendar crrentDate = Calendar.getInstance();
					crrentDate.add(Calendar.DAY_OF_MONTH, addSubtractDay);
					replaceValue = bartDateFormat.format(crrentDate.getTime());
				}
				sql = sql.replace("@" + name + "@", replaceValue);
			} else {
				sql = sql.replace("@" + name + "@", value);
			}
		}
		logger.info("[JBCUS DATA EXPORT] 格式化后的SQL  = " + sql);
		return sql;
	}

	/** 获得 insert sql */
	private String getInsertSql(String tableName, List<String> columnNames,JDBCDataSource destJDBCDataSource)
			throws NumberFormatException, SQLException {
		
		if (DataExportUtils.isProgressDataSource(destJDBCDataSource)) {
			String sql = "insert into " + "pub."+tableName + "(";

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
		}else{
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

	/** 获得修改的Sql */
	private String getUpdateSql(String tableName, List<String> columnNames,
			List<String> keyColumns, List<String> primaryKeys,JDBCDataSource destJDBCDataSource)
			throws NumberFormatException, SQLException {

		List<String> updateColumns = new ArrayList<String>();
		for (int i = 0; i < columnNames.size(); i++) {
			String columnName = columnNames.get(i);
			if (keyColumns.contains(columnName)
					|| primaryKeys.contains(columnName)) {
				continue;
			}
			updateColumns.add(columnName);
		}

		if (DataExportUtils.isProgressDataSource(destJDBCDataSource)) {
//			 列名的 key 值
			String sql = "update " + "pub."+tableName;
			for (int i = 0; i < updateColumns.size(); i++) {
				String columnName = updateColumns.get(i);
				if (i == 0) {
					sql += " set " + columnName + "=?";
				} else {
					sql += "," + columnName + "=?";
				}
			}

			for (int i = 0; i < keyColumns.size(); i++) {
				if (i == 0) {
					sql += " where " + keyColumns.get(i) + "=?";
				} else {
					sql += " and " + keyColumns.get(i) + "=?";
				}
			}
			return sql;	
		}else{
//			 列名的 key 值
			String sql = "update " + tableName;
			for (int i = 0; i < updateColumns.size(); i++) {
				String columnName = updateColumns.get(i);
				if (i == 0) {
					sql += " set " + tableName + "." + columnName + "=?";
				} else {
					sql += "," + tableName + "." + columnName + "=?";
				}
			}

			for (int i = 0; i < keyColumns.size(); i++) {
				if (i == 0) {
					sql += " where " + tableName + "." + keyColumns.get(i) + "=?";
				} else {
					sql += " and " + tableName + "." + keyColumns.get(i) + "=?";
				}
			}
			return sql;
		}
		
	}
	/**获得sql*/
	private String getSelectSql(List<String> keyColumns, String tableName,
			List<Map<String, Object>> destRowMaps,JDBCDataSource destJDBCDataSource) {

		if (DataExportUtils.isProgressDataSource(destJDBCDataSource)) {
			String sql = "select ";
			for (int i = 0; i < keyColumns.size(); i++) {
				String keyColumn = keyColumns.get(i);
				if (i < keyColumns.size() - 1) {
					sql += keyColumn + ",";
				} else {
					sql += keyColumn + " from " + "pub."+tableName;
				}
			}
			for (int i = 0; i < destRowMaps.size(); i++) {
				for (int j = 0; j < keyColumns.size(); j++) {
					String keyColumn = keyColumns.get(j);
					if (i == 0) {
						sql += " where " + keyColumn + "=?";
					} else {
						sql += " or " + keyColumn + "=?";
					}
				}
			}
			return sql;
		}else{
			String sql = "select ";
			for (int i = 0; i < keyColumns.size(); i++) {
				String keyColumn = keyColumns.get(i);
				if (i < keyColumns.size() - 1) {
					sql += tableName + "." + keyColumn + ",";
				} else {
					sql += tableName + "." + keyColumn + " from " + tableName;
				}
			}
			for (int i = 0; i < destRowMaps.size(); i++) {
				for (int j = 0; j < keyColumns.size(); j++) {
					String keyColumn = keyColumns.get(j);
					if (i == 0) {
						sql += " where " + tableName + "." + keyColumn + "=?";
					} else {
						sql += " or " + tableName + "." + keyColumn + "=?";
					}
				}
			}
			return sql;
		}
		
		
		
	}

}
