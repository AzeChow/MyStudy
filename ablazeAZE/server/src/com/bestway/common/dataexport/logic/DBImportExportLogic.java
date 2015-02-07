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
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.common.constant.GbkToBig5Flag;
import com.bestway.common.constant.ImportDataMode;
import com.bestway.common.constant.JDBCFlag;
import com.bestway.common.dataexport.dao.DBExportDao;
import com.bestway.common.dataexport.entity.JDBCDataSource;
import com.bestway.common.dataexport.entity.JDBCParameter;
import com.bestway.common.dataexport.entity.JDBCRegion;
import com.bestway.common.dataexport.entity.JDBCRegionSetup;
import com.bestway.common.dataexport.entity.JDBCSqlEvent;
import com.bestway.common.dataexport.entity.JDBCTask;
import com.bestway.common.dataexport.entity.JDBCTaskDetail;
import com.bestway.common.dataexport.entity.JDBCView;

/**
 * 工厂单据表维护 checked 2008-09-20
 * 
 * @author luosheng 2006/9/1
 *         <ul>
 *         <li>1.实现基于JDBC,DBCP的，通用性的，任意数据库之间的, 多数据源的对一个目的表的DB导入导出接口
 *         <li>2.实现基于JDBC的导入导出接口,自定义缓存查询导入导出功能
 *         <li>3.实现基于JDBC的批量导入导出功能
 *         <li>4.实现DB导入导出接口自定义批量更新导入导出功能
 *         <li>5.实现DB导入导出接口任务检查功能
 *         </ul>
 * 
 */
public class DBImportExportLogic {
	/** DB导出Dao */
	private DBExportDao dbExportDao = null;
	/** log4j 对象 */
	private static Log logger = LogFactory.getLog(DBImportExportLogic.class);
	/** 获得数据批default大小 */
	private static final int FETCH_BATCH_SIZE = 100;
	/** 更新数据批default大小 */
	private static final int UPDATE_BATCH_SIZE = 100;

	/** get DBExportDao 对象 */
	public DBExportDao getDbExportDao() {
		return dbExportDao;
	}

	/** set DBExportDao 对象 */
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

	/** 执行导入导出数据 */
	public void executeImportExportData(JDBCTask jdbcTask, Company company) {

		List<Gbtobig> gbkAndBig5 = this.dbExportDao.findGbtobig();

		if (jdbcTask.getIsParentTask() != null
				&& jdbcTask.getIsParentTask().booleanValue() == true) {
			List<JDBCTaskDetail> list = this.dbExportDao
					.findJDBCTaskDetail(jdbcTask);
			if (list.size() <= 0) {
				String info = jdbcTask.getTaskname() + " 没有任何子任务!!";
				logger.info(info);
				throw new RuntimeException(info);
			}
			for (JDBCTaskDetail jdbcTaskDetail : list) {
				JDBCTask subJDBCTask = jdbcTaskDetail.getSubJDBCTask();
				if (subJDBCTask != null) {
					executeImportExportData(subJDBCTask, gbkAndBig5, company);
				}
			}
		} else {
			//
			// 执行单任务
			//
			executeImportExportData(jdbcTask, gbkAndBig5, company);
		}
	}

	/** 执行导入导出数据(不是父任务的) */
	private void executeImportExportData(JDBCTask jdbcTask,
			List<Gbtobig> gbkAndBig5, Company company) {
		//
		// 验证是否在导入
		//
		String info = "";
		boolean isExecute = this.dbExportDao.isExecute(jdbcTask);
		if (isExecute == true) {
			info = jdbcTask.getTaskname() + "该任务的状态是正在执行,请注意!!";
			logger.info(info);
			throw new RuntimeException(info);
		}
		try {
			//
			// 加入正在导入标志
			//
			jdbcTask.setIsExecute(true);
			this.dbExportDao.saveJDBCTask(jdbcTask);
			//
			// 获得简体为key的 gbkKeyMap
			//
			Map<String, String> gbkKeyMap = this.getGbkKeyMap(gbkAndBig5);
			//
			// 获得繁体为key的 big5KeyMap
			//
			Map<String, String> big5KeyMap = this.getBig5KeyMap(gbkAndBig5);
			//
			// 获得所有任务明细
			//
			List<JDBCTaskDetail> jdbcTaskDetailList = this.dbExportDao
					.findJDBCTaskDetail(jdbcTask);

			String jdbcTaskName = jdbcTask.getTaskname();
			logger.info("开始导入导出任务 = " + jdbcTaskName);
			long beginTime = System.currentTimeMillis();

			if (jdbcTaskDetailList.size() <= 0) {
				info = jdbcTaskName + " 没有任何任务明细!!";
				logger.info(info);
				throw new RuntimeException(info);
			}

			for (JDBCTaskDetail jdbcTaskDetail : jdbcTaskDetailList) {
				Integer taskType = jdbcTaskDetail.getTaskType();
				int seqNum = jdbcTaskDetail.getSeqNum();

				if (taskType == null) {
					info = "序号 = " + seqNum + " 的明细任务类型为空 ";
					logger.info(info);
					throw new RuntimeException(info);
				}
				//
				// 是域对应类型
				//
				if (taskType.intValue() == JDBCTaskDetail.JDBC_REGION_TYPE) {

					jdbcRegionExport(jdbcTask, company, gbkKeyMap, big5KeyMap,
							jdbcTaskDetail, seqNum);
					logger.info("结束导入导出任务 = " + jdbcTask.getTaskname()
							+ "  共用 "
							+ ((System.currentTimeMillis() - beginTime) / 1000)
							+ " 秒 ");

				} else if (taskType.intValue() == JDBCTaskDetail.JDBC_SQL_EVENT_TYPE) { // 是事件
					//
					// 执行事件
					//
					jdbcEventExecute(company, jdbcTaskDetail, seqNum);
					logger.info("结束导入导出任务 = " + jdbcTask.getTaskname()
							+ "  共用 "
							+ ((System.currentTimeMillis() - beginTime) / 1000)
							+ " 秒 ");
				}
			}
		} finally {
			jdbcTask.setIsExecute(false);
			this.dbExportDao.saveJDBCTask(jdbcTask);
		}

	}

	/** 执行事件 */
	private void jdbcEventExecute(Company company,
			JDBCTaskDetail jdbcTaskDetail, int seqNum) {
		String info;
		JDBCSqlEvent jdbcSqlEvent = jdbcTaskDetail.getJdbcSqlEvent();
		if (jdbcSqlEvent == null) {
			info = "序号 = " + seqNum + " 的事件类型为空 ";
			logger.info(info);
			throw new RuntimeException(info);
		}
		JDBCDataSource eventJDBCDataSource = jdbcSqlEvent.getJdbcDataSource();
		DataSource dataSource = DataSourcePools.getDataSource(
				eventJDBCDataSource.getDriverClassName(),
				eventJDBCDataSource.getUrl(),
				eventJDBCDataSource.getUserName(),
				eventJDBCDataSource.getPassword());

		Connection eventConn = null;
		try {
			eventConn = dataSource.getConnection();
			if (eventConn.getAutoCommit() == false) {
				eventConn.setAutoCommit(true);
			}
			Statement stmt = eventConn.createStatement();

			logger.info("[JBCUS DATA EXPORT]  开始执行事件 事件名 == "
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
		logger.info("[JBCUS DATA EXPORT]  结束执行事件 事件名 == "
				+ jdbcSqlEvent.getName());
	}

	/** jdbc 域导入导出 */
	private void jdbcRegionExport(JDBCTask jdbcTask, Company company,
			Map<String, String> gbkKeyMap, Map<String, String> big5KeyMap,
			JDBCTaskDetail jdbcTaskDetail, int seqNum) {

		String info;
		JDBCRegion jdbcRegion = jdbcTaskDetail.getJdbcRegion();
		if (jdbcRegion == null) {
			info = "序号 = " + seqNum + " 的明细域对应对象为空 ";
			logger.info(info);
			throw new RuntimeException(info);
		}
		String regionName = jdbcRegion.getRegionName();
		logger.info("开始导入导出域对应 = " + regionName);
		//
		// GbkToBig5Flag 繁转简标志
		//
		String gbkToBig5Flag = jdbcRegion.getGbkToBig5Flag();
		//
		// 1.获得对应明细
		// 2.格式化视图中的 sql 语句 并获得结果值
		//

		//
		// 获得对应明细
		//
		List<JDBCRegionSetup> jdbcRegionSetupList = this.dbExportDao
				.findJDBCRegionSetup(jdbcRegion.getId(), company);
		if (jdbcRegionSetupList == null || jdbcRegionSetupList.size() <= 0) {
			info = "开始导入导出域对应 = " + regionName + " 没有明细的对应记录请检查域对应设置";
			logger.info(info);
			throw new RuntimeException(info);
		}
		//
		// 格式化视图中的 sql 语句 并获得结果值
		//
		JDBCView jdbcView = jdbcRegion.getSrcJDBCView();
		String viewSql = formatSql(jdbcView.getSqlScript(), company);

		ResultSet resultSet = null;
		Connection viewConn = null;
		try {
			JDBCDataSource viewJDBCDataSource = jdbcView.getJdbcDataSource();
			DataSource dataSource = DataSourcePools.getDataSource(
					viewJDBCDataSource.getDriverClassName(),
					viewJDBCDataSource.getUrl(),
					viewJDBCDataSource.getUserName(),
					viewJDBCDataSource.getPassword());

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
			int importDataMode = jdbcTask.getImportDataMode() == null ? ImportDataMode.ADD_MODE
					: jdbcTask.getImportDataMode();

			if (importDataMode == ImportDataMode.ADD_MODE) { // 直接保存,不检查

				jdbcRegionExportAddMode(company, gbkKeyMap, big5KeyMap,
						jdbcRegion, gbkToBig5Flag, jdbcRegionSetupList,
						resultSet, lsField);

			} else if (importDataMode == ImportDataMode.MODIFY_MODE) {// 更新方式导入

				jdbcRegionExportModifyMode(company, gbkKeyMap, big5KeyMap,
						seqNum, jdbcRegion, gbkToBig5Flag, jdbcRegionSetupList,
						resultSet, lsField);
			}
			//
			// 注意关闭
			//
			resultSet.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
			throw new RuntimeException(ex);
		} finally {
			close(viewConn);
		}
	}

	/** 直接保存,不检查 */
	private void jdbcRegionExportAddMode(Company company,
			Map<String, String> gbkKeyMap, Map<String, String> big5KeyMap,
			JDBCRegion jdbcRegion, String gbkToBig5Flag,
			List<JDBCRegionSetup> jdbcRegionSetupList, ResultSet resultSet,
			List<String> lsField) throws SQLException {
		//
		// 获得目的数据源,目的数据表名
		//
		JDBCDataSource destJDBCDataSource = jdbcRegion.getDestJDBCDataSource();
		DataSource destDataSource = DataSourcePools.getDataSource(
				destJDBCDataSource.getDriverClassName(),
				destJDBCDataSource.getUrl(), destJDBCDataSource.getUserName(),
				destJDBCDataSource.getPassword());
		Connection destConn = null;

		try {
			destConn = destDataSource.getConnection();
			if (destConn.getAutoCommit() == false) {
				destConn.setAutoCommit(true);
			}
			String destTableName = jdbcRegion.getDestTableName();
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

			for (JDBCRegionSetup jdbcRegionSetup : jdbcRegionSetupList) {
				String destColumnName = jdbcRegionSetup.getDestFieldName();
				destColumnNames.add(destColumnName);
				//
				// 是否缓存 转换SQL
				//
				boolean isCache = jdbcRegionSetup.getIsCache() == null ? false
						: jdbcRegionSetup.getIsCache();

				String jdbcFlag = jdbcRegionSetup.getJdbcFlag();
				if (JDBCFlag.SQL.equals(jdbcFlag) && !isCache) {

					String paraSql = this.formatSql(
							jdbcRegionSetup.getSqlStr(), company);
					items = DataExportUtils.getItemsValueNoAt(paraSql);
					//
					// 用参数名 @parameter 转成 ?
					//
					paraSql = DataExportUtils.getParaSql(paraSql);
					destColumnFormatSql.put(destColumnName, paraSql);

				} else if (JDBCFlag.SQL.equals(jdbcFlag) && isCache) {
					String cacheSql = this.formatSql(
							jdbcRegionSetup.getSqlStr(), company);
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
						JDBCDataSource paraJDBCDataSource = jdbcRegionSetup
								.getParaJDBCDataSource();
						DataSource paraDataSource = DataSourcePools
								.getDataSource(
										paraJDBCDataSource.getDriverClassName(),
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
			String insertSql = getInsertSql(destTableName, destColumnNames,
					destJDBCDataSource);
			PreparedStatement stmtInsert = destConn.prepareStatement(insertSql);

			logger.info("---------" + insertSql);

			boolean hasExecuteBatch = false;
			boolean isProgress = DataExportUtils
					.isProgressDataSource(destJDBCDataSource);

			for (int i = 0; resultSet.next(); i++) {
				//
				// 以视图名 为 key 值
				//
				Map<String, Object> rowMap = this.getRowMap(resultSet, lsField,
						gbkToBig5Flag, gbkKeyMap, big5KeyMap);

				//
				// 如果是progressDataSource 的话那么将直接执行
				//
				if (isProgress) {
					if (i % 5000 == 0) {
						stmtInsert.close();
						stmtInsert = destConn.prepareStatement(insertSql);
						logger.info("insert data ----- " + i);
					}
					//
					// 设置参数
					//
					this.setInsertParameters(stmtInsert, jdbcRegionSetupList,
							rowMap, destColumnFormatSql, items, cacheValuesMap,
							gbkToBig5Flag, gbkKeyMap, big5KeyMap, company);
					stmtInsert.execute();
				} else {
					//
					// 设置参数
					//
					this.setInsertParameters(stmtInsert, jdbcRegionSetupList,
							rowMap, destColumnFormatSql, items, cacheValuesMap,
							gbkToBig5Flag, gbkKeyMap, big5KeyMap, company);
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
			List<JDBCRegionSetup> jdbcRegionSetupList,
			Map<String, Object> rowMap,
			Map<String, String> destColumnFormatSql,
			List<DataExportUtils.Item> items,
			Map<String, Map<String, Object>> cacheValuesMap, String gbkToBig5,
			Map<String, String> gbkKeyMap, Map<String, String> big5KeyMap,
			Company company) throws SQLException {

		for (int i = 0; i < jdbcRegionSetupList.size(); i++) {
			JDBCRegionSetup jdbcRegionSetup = jdbcRegionSetupList.get(i);
			String destFieldName = jdbcRegionSetup.getDestFieldName();
			Integer destFieldType = jdbcRegionSetup.getDestFieldType();
			String srcFieldName = jdbcRegionSetup.getSrcFieldName();
			Integer srcFieldType = jdbcRegionSetup.getSrcFieldType();
			boolean isCache = jdbcRegionSetup.getIsCache() == null ? false
					: jdbcRegionSetup.getIsCache();

			String jdbcFlag = jdbcRegionSetup.getJdbcFlag();

			if (JDBCFlag.NO.equals(jdbcFlag)) { // 直接对应数值
				Object value = rowMap.get(srcFieldName);
				if (value != null) {
					if (value instanceof Date) {
						SimpleDateFormat bartDateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");
						value = bartDateFormat.format((Date) value);
					} else if (value instanceof oracle.sql.TIMESTAMP) {
						Timestamp t = ((oracle.sql.TIMESTAMP)value).timestampValue();
						SimpleDateFormat bartDateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");
						value = bartDateFormat.format(t);
					}
					value = getValueByType(value.toString(),
							destFieldType.intValue());
					stmt.setObject(i + 1, value);
				} else {
					stmt.setNull(i + 1, destFieldType);
				}
			} else if (JDBCFlag.CURRENT_COMPANY_ID.equals(jdbcFlag)) {// 当前公司ID

				// stmt.setObject(i + 1, company.getId(),destFieldType);
				stmt.setObject(i + 1, company.getId());

			} else if (JDBCFlag.GUID.equals(jdbcFlag)) {// 32 位 GUID

				UUID uuid = UUID.randomUUID();
				String id = uuid.toString();
				id = id.replace("-", "");
				// stmt.setObject(i + 1, id,destFieldType);
				stmt.setObject(i + 1, id);

			} else if (JDBCFlag.SQL.equals(jdbcFlag) && !isCache) { // 自定义SQL
				// 转换参数 没有缓存
				String paraSql = destColumnFormatSql.get(destFieldName);
				//
				// 获得目的数据源,目的数据表名
				//
				Connection paraConn = null;
				try {
					JDBCDataSource paraJDBCDataSource = jdbcRegionSetup
							.getParaJDBCDataSource();
					DataSource paraDataSource = DataSourcePools.getDataSource(
							paraJDBCDataSource.getDriverClassName(),
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
					boolean isBig5DataBase = jdbcRegionSetup
							.getIsBig5DataBase() == null ? false
							: jdbcRegionSetup.getIsBig5DataBase();

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
						value = getValueByType(value.toString(),
								destFieldType.intValue());
						if (value instanceof String) {
							if (GbkToBig5Flag.BIG5_TO_GBK.equals(gbkToBig5)) {// 繁转简
								value = big5ToGbk((String) value, big5KeyMap);
							} else if (GbkToBig5Flag.GBK_TO_BIG5
									.equals(gbkToBig5)) { // 简转繁
								value = gbkToBig5((String) value, gbkKeyMap);
							}
						}
						// stmt.setObject(i + 1, value,destFieldType);
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
					value = getValueByType(value.toString(),
							destFieldType.intValue());
					// stmt.setObject(i + 1, value,destFieldType);
					stmt.setObject(i + 1, value);
				} else {
					stmt.setNull(i + 1, destFieldType);
				}
			}
		}
	}

	/** 数据导入导出更新模式 */
	private void jdbcRegionExportModifyMode(Company company,
			Map<String, String> gbkKeyMap, Map<String, String> big5KeyMap,
			int seqNum, JDBCRegion jdbcRegion, String gbkToBig5Flag,
			List<JDBCRegionSetup> jdbcRegionSetupList, ResultSet resultSet,
			List<String> lsField) {
		String info;
		//
		// 1.检查是否有目的表 key 列 ,可以确定唯一性的栏位,用于更新
		//
		List<String> keyColumns = new ArrayList<String>();
		for (JDBCRegionSetup jdbcRegionSetup : jdbcRegionSetupList) {
			String destFieldName = jdbcRegionSetup.getDestFieldName();
			if (jdbcRegionSetup.getIsKey() == null
					|| !jdbcRegionSetup.getIsKey().booleanValue()) {
				continue;
			}
			keyColumns.add(destFieldName);
		}
		if (keyColumns.size() <= 0) {
			info = "序号 = " + seqNum + " 的明细域对应对象选择的是更新导入方式"
					+ ",\n但是没有设置能确定数据行唯一性的列. ";
			logger.info(info);
			throw new RuntimeException(info);
		}
		//
		// 2.primary key
		//
		List<String> primaryKeys = new ArrayList<String>();
		for (JDBCRegionSetup jdbcRegionSetup : jdbcRegionSetupList) {
			String destFieldName = jdbcRegionSetup.getDestFieldName();
			if (jdbcRegionSetup.getIsPrimaryKey() == null
					|| !jdbcRegionSetup.getIsPrimaryKey().booleanValue()) {
				continue;
			}
			primaryKeys.add(destFieldName);
		}

		//
		// 获得目的数据源,目的数据表名
		//
		JDBCDataSource destJDBCDataSource = jdbcRegion.getDestJDBCDataSource();
		DataSource destDataSource = DataSourcePools.getDataSource(
				destJDBCDataSource.getDriverClassName(),
				destJDBCDataSource.getUrl(), destJDBCDataSource.getUserName(),
				destJDBCDataSource.getPassword());
		Connection destConn = null;

		try {
			destConn = destDataSource.getConnection();
			if (destConn.getAutoCommit() == false) {
				destConn.setAutoCommit(true);
			}
			String destTableName = jdbcRegion.getDestTableName();
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

			for (JDBCRegionSetup jdbcRegionSetup : jdbcRegionSetupList) {
				String destColumnName = jdbcRegionSetup.getDestFieldName();
				destColumnNames.add(destColumnName);
				//
				// 是否缓存 转换SQL
				//
				boolean isCache = jdbcRegionSetup.getIsCache() == null ? false
						: jdbcRegionSetup.getIsCache();

				String jdbcFlag = jdbcRegionSetup.getJdbcFlag();
				if (JDBCFlag.SQL.equals(jdbcFlag) && !isCache) {

					String paraSql = this.formatSql(
							jdbcRegionSetup.getSqlStr(), company);
					items = DataExportUtils.getItemsValueNoAt(paraSql);
					//
					// 用参数名 @parameter 转成 ?
					//
					paraSql = DataExportUtils.getParaSql(paraSql);
					destColumnFormatSql.put(destColumnName, paraSql);

				} else if (JDBCFlag.SQL.equals(jdbcFlag) && isCache) {
					String cacheSql = this.formatSql(
							jdbcRegionSetup.getSqlStr(), company);
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
						JDBCDataSource paraJDBCDataSource = jdbcRegionSetup
								.getParaJDBCDataSource();
						DataSource paraDataSource = DataSourcePools
								.getDataSource(
										paraJDBCDataSource.getDriverClassName(),
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

			while (resultSet.next()) {
				//
				// 以视图名 为 key 值
				//
				Map<String, Object> rowMap = this.getRowMap(resultSet, lsField,
						gbkToBig5Flag, gbkKeyMap, big5KeyMap);
				batchList.add(rowMap);
				//
				// 100 条执行一次
				//
				if (batchList.size() % UPDATE_BATCH_SIZE == 0) {

					batchUpdateData(company, jdbcRegionSetupList, keyColumns,
							primaryKeys, destConn, destTableName,
							destColumnNames, destColumnFormatSql,
							cacheValuesMap, items, batchList, gbkToBig5Flag,
							gbkKeyMap, big5KeyMap, destJDBCDataSource);
					//
					// 清除修改过的批
					//
					batchList.clear();
				}
			}
			//
			// 批量修改剩余数据
			//
			batchUpdateData(company, jdbcRegionSetupList, keyColumns,
					primaryKeys, destConn, destTableName, destColumnNames,
					destColumnFormatSql, cacheValuesMap, items, batchList,
					gbkToBig5Flag, gbkKeyMap, big5KeyMap, destJDBCDataSource);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
			throw new RuntimeException(ex);
		} finally {
			close(destConn);
		}
	}

	/** 批量更新数据 */
	private void batchUpdateData(Company company,
			List<JDBCRegionSetup> jdbcRegionSetupList, List<String> keyColumns,
			List<String> primaryKeys, Connection destConn,
			String destTableName, List<String> destColumnNames,
			Map<String, String> destColumnFormatSql,
			Map<String, Map<String, Object>> cacheValuesMap,
			List<DataExportUtils.Item> items,
			List<Map<String, Object>> batchList, String gbkToBig5,
			Map<String, String> gbkKeyMap, Map<String, String> big5KeyMap,
			JDBCDataSource destJDBCDataSource) throws SQLException {

		if (batchList.size() <= 0) {
			return;
		}

		List<Map<String, Object>> destRowMaps = this.getDestRowMap(
				jdbcRegionSetupList, batchList, destColumnFormatSql, items,
				cacheValuesMap, gbkToBig5, gbkKeyMap, big5KeyMap, company);
		//
		// 生成查询语句
		//
		String selectSql = getSelectSql(keyColumns, destTableName, destRowMaps,
				destJDBCDataSource);

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
		String insertSql = getInsertSql(destTableName, destColumnNames,
				destJDBCDataSource);
		PreparedStatement stmtInsert = destConn.prepareStatement(insertSql);

		logger.info("更新导入时开始的新增语句 = " + insertSql);

		boolean hasInsertData = false;
		boolean isProgress = DataExportUtils
				.isProgressDataSource(destJDBCDataSource);
		for (int i = 0; i < insertListMap.size(); i++) {
			//
			// 以目的列 为 key 值
			//
			Map<String, Object> insertRowMap = insertListMap.get(i);
			//
			// 设置参数
			//
			this.setInsertParameters(stmtInsert, jdbcRegionSetupList,
					insertRowMap);
			//
			// 如果是progressDataSource 的话那么将直接执行
			//
			if (isProgress) {
				stmtInsert.execute();
			} else {
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
				keyColumns, primaryKeys, destJDBCDataSource);
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
			this.setUpdateParameters(stmtUpdate, jdbcRegionSetupList,
					keyColumns, primaryKeys, updateRowMap);
			//
			// 如果是progressDataSource 的话那么将直接执行
			//
			if (isProgress) {
				stmtUpdate.execute();
			} else {
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
			List<JDBCRegionSetup> jdbcRegionSetupList,
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

			for (int i = 0; i < jdbcRegionSetupList.size(); i++) {
				JDBCRegionSetup jdbcRegionSetup = jdbcRegionSetupList.get(i);
				String destFieldName = jdbcRegionSetup.getDestFieldName();
				Integer destFieldType = jdbcRegionSetup.getDestFieldType();
				String srcFieldName = jdbcRegionSetup.getSrcFieldName();
				Integer srcFieldType = jdbcRegionSetup.getSrcFieldType();
				boolean isCache = jdbcRegionSetup.getIsCache() == null ? false
						: jdbcRegionSetup.getIsCache();

				String jdbcFlag = jdbcRegionSetup.getJdbcFlag();

				if (JDBCFlag.NO.equals(jdbcFlag)) { // 直接对应数值
					Object value = rowMap.get(srcFieldName);
					Object tempValue = null;
					if (value != null) {
						// if (destFieldType.intValue() ==
						// srcFieldType.intValue()) {
						// tempValue = value;
						// } else {
						if (value instanceof Date) {
							SimpleDateFormat bartDateFormat = new SimpleDateFormat(
									"yyyy-MM-dd");
							value = bartDateFormat.format((Date) value);
						}
						value = getValueByType(value.toString(),
								destFieldType.intValue());
						tempValue = value;
						// }
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
						JDBCDataSource paraJDBCDataSource = jdbcRegionSetup
								.getParaJDBCDataSource();
						DataSource paraDataSource = DataSourcePools
								.getDataSource(
										paraJDBCDataSource.getDriverClassName(),
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
						boolean isBig5DataBase = jdbcRegionSetup
								.getIsBig5DataBase() == null ? false
								: jdbcRegionSetup.getIsBig5DataBase();
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
						value = getValueByType(value.toString(),
								destFieldType.intValue());
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
			List<JDBCRegionSetup> jdbcRegionSetupList,
			Map<String, Object> rowMap) throws SQLException {

		for (int i = 0; i < jdbcRegionSetupList.size(); i++) {
			JDBCRegionSetup jdbcRegionSetup = jdbcRegionSetupList.get(i);
			String destFieldName = jdbcRegionSetup.getDestFieldName();
			Integer destFieldType = jdbcRegionSetup.getDestFieldType();
			Object value = rowMap.get(destFieldName);
			if (value != null) {
				value = getValueByType(value.toString(),
						destFieldType.intValue());
				// stmt.setObject(i + 1, value,destFieldType);
				stmt.setObject(i + 1, value);
			} else {
				stmt.setNull(i + 1, destFieldType);
			}
		}
	}

	/** 设置jdbc 导入参数 */
	private void setUpdateParameters(PreparedStatement stmt,
			List<JDBCRegionSetup> jdbcRegionSetupList, List<String> keyColumns,
			List<String> primaryKeys, Map<String, Object> rowMap)
			throws SQLException {
		//
		// jdbcRegionSetupList 改变顺序
		//
		List<JDBCRegionSetup> newJDBCRegionSetupList = new ArrayList<JDBCRegionSetup>();
		Map<String, JDBCRegionSetup> tempMap = new HashMap<String, JDBCRegionSetup>();

		for (JDBCRegionSetup jdbcRegionSetup : jdbcRegionSetupList) {
			String destFieldName = jdbcRegionSetup.getDestFieldName();
			tempMap.put(destFieldName, jdbcRegionSetup);
			if (keyColumns.contains(destFieldName)
					|| primaryKeys.contains(destFieldName)) {
				continue;
			}
			newJDBCRegionSetupList.add(jdbcRegionSetup);
		}

		for (int i = 0; i < keyColumns.size(); i++) {
			JDBCRegionSetup jdbcRegionSetup = tempMap.get(keyColumns.get(i));
			newJDBCRegionSetupList.add(jdbcRegionSetup);
		}

		for (int i = 0; i < newJDBCRegionSetupList.size(); i++) {
			JDBCRegionSetup jdbcRegionSetup = newJDBCRegionSetupList.get(i);
			String destFieldName = jdbcRegionSetup.getDestFieldName();
			Integer destFieldType = jdbcRegionSetup.getDestFieldType();
			Object value = rowMap.get(destFieldName);
			if (value != null) {
				value = getValueByType(value.toString(),
						destFieldType.intValue());
				// stmt.setObject(i + 1, value,destFieldType);
				stmt.setObject(i + 1, value);
			} else {
				stmt.setNull(i + 1, destFieldType);
			}
		}
	}

	/** 获得类型 */
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
	private Map<String, Object> getRowMap(ResultSet rs, List<String> lsField,
			String gbkToBig5, Map<String, String> gbkKeyMap,
			Map<String, String> big5KeyMap) throws SQLException {
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
	public String formatSql(String sql, BaseCompany company) {
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

	/** 获得修改的Sql */
	private String getUpdateSql(String tableName, List<String> columnNames,
			List<String> keyColumns, List<String> primaryKeys,
			JDBCDataSource destJDBCDataSource) throws NumberFormatException,
			SQLException {

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
			// 列名的 key 值
			String sql = "update " + "pub." + tableName;
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
		} else {
			// 列名的 key 值
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
					sql += " where " + tableName + "." + keyColumns.get(i)
							+ "=?";
				} else {
					sql += " and " + tableName + "." + keyColumns.get(i) + "=?";
				}
			}
			return sql;
		}

	}

	/** 获得查询的Sql */
	private String getSelectSql(List<String> keyColumns, String tableName,
			List<Map<String, Object>> destRowMaps,
			JDBCDataSource destJDBCDataSource) {

		if (DataExportUtils.isProgressDataSource(destJDBCDataSource)) {
			String sql = "select ";
			for (int i = 0; i < keyColumns.size(); i++) {
				String keyColumn = keyColumns.get(i);
				if (i < keyColumns.size() - 1) {
					sql += keyColumn + ",";
				} else {
					sql += keyColumn + " from " + "pub." + tableName;
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
		} else {
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
