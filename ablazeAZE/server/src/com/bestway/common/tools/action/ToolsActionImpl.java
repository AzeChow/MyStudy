/*
 * Created on 2004-9-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.tools.action;

import java.util.List;
import java.util.Map;

import com.bestway.common.BaseActionImpl;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;
import com.bestway.common.tools.dao.ToolsDao;
import com.bestway.common.tools.entity.DBIndex;
import com.bestway.common.tools.entity.EntityMutableTreeNode;
import com.bestway.common.tools.entity.FileResource;
import com.bestway.common.tools.entity.FtpConfig;
import com.bestway.common.tools.entity.PluginInfo;
import com.bestway.common.tools.entity.PluginInfoClient;
import com.bestway.common.tools.entity.PluginInfoClientReport;
import com.bestway.common.tools.entity.TempNodeItem;
import com.bestway.common.tools.entity.TempResultSet;
import com.bestway.common.tools.logic.PluginClientLogic;
import com.bestway.common.tools.logic.PluginClientReportLogic;
import com.bestway.common.tools.logic.PluginLogic;
import com.bestway.common.tools.logic.ReportFileLogic;
import com.bestway.common.tools.logic.ToolsLogic;
import com.bestway.common.warning.logic.WarningLogic;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates SQL,HSQL操作actionImpl
 *         checked by 陈井彬 2008-12.6
 */

public class ToolsActionImpl extends BaseActionImpl implements ToolsAction {
	
	/**
	 * SQL,HSQL操作DAO
	 */
	private ToolsDao toolsDao = null;

	/**
	 * SQL,HSQL操作logic 
	 */
	private ToolsLogic toolsLogic = null;

	/**
	 * 插件LOGIC
	 */
	private PluginLogic pluginLogic = null;

	/**
	 * 报表LOGIC
	 */
	private ReportFileLogic reportFileLogic = null;

	/**
	 * 客户端LOGIC
	 */
	private PluginClientLogic pluginClientLogic = null;

	/**
	 * 警告LOGIC
	 */
	private WarningLogic warningLogic = null;

	/**
	 * 插件报表LOGIC
	 */
	private PluginClientReportLogic pluginClientReportLogic = null;

	public PluginLogic getPluginLogic() {
		return pluginLogic;
	}

	public PluginClientReportLogic getPluginClientReportLogic() {
		return pluginClientReportLogic;
	}

	public void setPluginClientReportLogic(
			PluginClientReportLogic pluginClientReportLogic) {
		this.pluginClientReportLogic = pluginClientReportLogic;
	}

	public PluginClientLogic getPluginClientLogic() {
		return pluginClientLogic;
	}

	public WarningLogic getWarningLogic() {
		return warningLogic;
	}

	public void setWarningLogic(WarningLogic warningLogic) {
		this.warningLogic = warningLogic;
	}

	public void setPluginClientLogic(PluginClientLogic pluginClientLogic) {
		this.pluginClientLogic = pluginClientLogic;
	}

	public ReportFileLogic getReportFileLogic() {
		return reportFileLogic;
	}

	public void setReportFileLogic(ReportFileLogic reportFileLogic) {
		this.reportFileLogic = reportFileLogic;
	}

	public void setPluginLogic(PluginLogic pluginLogic) {
		this.pluginLogic = pluginLogic;
	}

	public ToolsLogic getToolsLogic() {
		return toolsLogic;
	}

	public void setToolsLogic(ToolsLogic toolsLogic) {
		this.toolsLogic = toolsLogic;
	}

	/**
	 * 查询SQL
	 */
	public List execHql(String hql) {
		return toolsDao.execHql(hql);
	}

	/**
	 * 获得实体信息
	 * 
	 * @return
	 */
	public List getEntityInfo() {
		return toolsDao.getEntityInfo();
	}

	/**
	 * 执行SQL
	 * 
	 * @param hql
	 * @return
	 */
	public int delete(String hql) {
		return toolsDao.delete(hql);
	}

	/**
	 * 批处理运行HSQL更新或删除
	 * 
	 * @param hql
	 * @return
	 */
	public int execute(String hql) {
		return toolsDao.execute(hql);
	}

	/**
	 * @return Returns the toolsDao.
	 */
	public ToolsDao getToolsDao() {
		return toolsDao;
	}

	/**
	 * @param toolsDao
	 *            The toolsDao to set.
	 */
	public void setToolsDao(ToolsDao toolsDao) {
		this.toolsDao = toolsDao;
	}

	/**
	 * 获得处理信息
	 * 
	 * @param id
	 * @return
	 */
	public ProgressInfo getProgressInfo(String id) {
		return ProcExeProgress.getInstance().getProgressInfo(id);
	}

	/**
	 * 删除处理信息
	 * 
	 * @param id
	 */
	public void removeProgressInfo(String id) {
		ProcExeProgress.getInstance().removeProgressInfo(id);
	}

	/**
	 * 获得class结点集合
	 * 
	 * @return
	 */
	public List<TempNodeItem> getTempNodeItems() {
		return this.toolsLogic.getTempNodeItems();
	}

	/**
	 * 获得指定class结点
	 * 
	 * @return
	 */
	public List<TempNodeItem> getTempNodeItems(String className) {
		return this.toolsLogic.getTempNodeItems(className);
	}

	/**
	 * insert ,update , delete
	 */
	public int executeUpdateSql(String sql) {
		return this.toolsLogic.executeUpdateSql(sql);
	}

	/**
	 * 比如说 create 语名 drop 语句
	 */
	public boolean executeSql(String sql) {
		return this.toolsLogic.executeSql(sql);
	}

	/**
	 * 获得结果集
	 * 
	 * @param sql
	 * @return
	 */
	public TempResultSet getTempResultSet(String sql) {
		return this.toolsLogic.getTempResultSet(sql);
	}

	/**
	 * 获得DB表结构
	 * 
	 * @return
	 */

	public Map<TempNodeItem, List<TempNodeItem>> getTableColumnMap() {
		return this.toolsLogic.getTableColumnMap();
	}

	/**
	 * 加入注释
	 * 
	 * @param list
	 * @param parentEntityName
	 * @return
	 */
	public List<TempNodeItem> getAnnotate(List<TempNodeItem> list,
			String parentEntityName) {
		return this.toolsLogic.getAnnotate(list, parentEntityName);
	}

	/**
	 * 动态动行一个插件
	 * 
	 * @param obj
	 * @return
	 */
	public Object runPlugin(Request request, PluginInfo pluginInfo) {
		return this.pluginLogic.runPlugin(pluginInfo);
	}

	/**
	 * 载入所有插件信息
	 * 
	 * @return
	 */
	public List<PluginInfo> loadPlugin(Request request) {
		return this.pluginLogic.loadPlugin();
	}

	/**
	 * 下载插件
	 * 
	 * @return
	 */
	public byte[] downloadPlugin(Request request, PluginInfo pluginInfo) {
		return this.pluginLogic.downloadPlugin(pluginInfo);
	}

	/**
	 * 上传插件
	 * 
	 */
	public void uploadPlugin(Request request, String fileName, byte[] bytes) {
		this.pluginLogic.uploadPlugin(fileName, bytes);
	}

	/**
	 * 删除一个插件
	 * 
	 */
	public boolean deletePlugin(Request request, PluginInfo pluginInfo) {
		return this.pluginLogic.deletePlugin(pluginInfo);
	}

	/**
	 * 获取当前FTp 上的所有文件
	 * 
	 * @param filePath
	 * @return
	 */
	public List<FileResource> getResourceList(String server, String userName,
			String password, String directory) {
		return this.reportFileLogic.getResourceList(server, userName, password,
				directory);
	}

	/**
	 * 下载插件
	 * 
	 * @return
	 */
	public byte[] downloadFileResource(String server, String userName,
			String password, FileResource fileResource) {
		return this.reportFileLogic.downloadFileResource(server, userName,
				password, fileResource);
	}

	/**
	 * 上传插件
	 * 
	 */
	public StringBuffer uploadFileResource(String server, String userName,
			String password, List<String> remotes, List<byte[]> bytess) {
		return this.reportFileLogic.uploadFileResource(server, userName,
				password, remotes, bytess);
	}

	/**
	 * 删除一个报表文件
	 */
	public StringBuffer deleteAllFileResource(String server, String userName,
			String password, String directory) {
		return reportFileLogic.deleteAllFileResource(server, userName,
				password, directory);
	}

	/**
	 * 删除一个报表文件
	 */
	public StringBuffer deleteFileResource(String server, String userName,
			String password, List<FileResource> fileResources) {
		return reportFileLogic.deleteFileResource(server, userName, password,
				fileResources);
	}

	/**
	 * 查找FTP配置
	 * 
	 * @param request
	 * @return
	 */
	public FtpConfig findFtpConfig(Request request) {
		return this.toolsDao.findFtpConfig();
	}

	/**
	 * 保存FTP配置
	 * 
	 * @param request
	 * @return
	 */
	public FtpConfig saveFtpConfig(Request request, FtpConfig ftpConfig) {
		this.toolsDao.saveFtpConfig(ftpConfig);
		return ftpConfig;
	}

	/**
	 * 载入所有插件信息
	 * 
	 * @return
	 */
	public List<PluginInfoClient> loadClientPlugin(Request request) {
		return this.pluginClientLogic.loadClientPlugin();
	}

	/**
	 * 下载插件
	 * 
	 * @return
	 */
	public byte[] downloadClientPlugin(Request request,
			PluginInfoClient pluginInfoClient) {
		return this.pluginClientLogic.downloadClientPlugin(pluginInfoClient);
	}

	/**
	 * 上传插件
	 * 
	 */
	public void uploadClientPlugin(Request request, String fileName,
			byte[] bytes) {
		this.pluginClientLogic.uploadClientPlugin(fileName, bytes);
	}

	/**
	 * 删除一个插件
	 */
	public boolean deleteClientPlugin(Request request,
			PluginInfoClient pluginInfo) {
		return this.pluginClientLogic.deleteClientPlugin(pluginInfo);
	}

	/**
	 * 删除插件
	 * 
	 */
	public void deleteAllClientPlugin(Request request) {
		this.pluginClientLogic.deleteAllClientPlugin();
	}

	/**
	 * 动态动行一个插件 当客户端调用时,动态动行一个插件
	 * 
	 * @param obj
	 * @return
	 */
	public List runServerPlugin(Request request, String className,
			Map parameters) {
		return this.pluginClientLogic.runServerPlugin(className, parameters);
	}

	/**
	 * 动态动行一个插件 只要求方法名相同，参数个数相同，来调用某个方法
	 * 
	 * @param obj
	 * @return
	 */
	public Object runServerPlugin(Request request, String className,
			String methodName, Object... args) {
		return this.pluginClientLogic.runServerPlugin(className, methodName,
				args);
	}

	/**
	 * 动态动行一个插件 非常准确的调用某个方法
	 * 
	 * @param obj
	 * @return
	 */
	public Object runServerPlugin(Request request, String className,
			String methodName, Class[] paramTypes, Object... args) {
		return this.pluginClientLogic.runServerPlugin(className, methodName,
				paramTypes, args);
	}

	/** 停止服务器插件 */
	public void stopJbcusServerPlugin(Request request, PluginInfo pluginInfo) {
		this.pluginLogic.stopJbcusServerPlugin(pluginInfo);
	}

	/** 重启服务器插件 */
	public void restartJbcusServerPlugin(Request request, PluginInfo pluginInfo) {
		this.pluginLogic.restartJbcusServerPlugin(pluginInfo);
	}

	/** 服务端插件是否运行 */
	public boolean isRunJbcusServerPlugin(Request request, PluginInfo pluginInfo) {
		return this.pluginLogic.isRunJbcusServerPlugin(pluginInfo);
	}

	/** 保存插件信息 */
	public PluginInfo savePluginInfo(Request request, PluginInfo pluginInfo) {
		return this.pluginLogic.savePluginInfo(pluginInfo);
	}

	/**
	 * 
	 * 获得 PluginInfoClientReport by traget class name
	 * 
	 */
	public List<PluginInfoClientReport> findPluginInfoClientReport(
			Request request, String tragetClassName) {
		return pluginClientReportLogic
				.findPluginInfoClientReport(tragetClassName);
	}

	/**
	 * 动态动行一个插件 当客户端调用时,动态动行一个插件
	 * 
	 * @param obj
	 * @return
	 */
	public List runReportServerPlugin(Request request,
			String serverReportClass, Map parameters) {
		return this.pluginClientReportLogic.runReportServerPlugin(
				serverReportClass, parameters);
	}

	/**
	 * 动态动行一个插件 只要求方法名相同，参数个数相同，来调用某个方法
	 * 
	 * @param obj
	 * @return
	 */
	public Object runReportServerPlugin(Request request, String className,
			String methodName, Object... args) {
		return this.pluginClientReportLogic.runReportServerPlugin(className,
				methodName, args);
	}

	/**
	 * 动态动行一个插件 非常准确的调用某个方法
	 * 
	 * @param obj
	 * @return
	 */
	public Object runReportServerPlugin(Request request, String className,
			String methodName, Class[] paramTypes, Object... args) {
		return this.pluginClientReportLogic.runReportServerPlugin(className,
				methodName, paramTypes, args);
	}

	/**
	 * 下载插件
	 * 
	 * @return
	 */
	public byte[] downloadReportPlugin(Request request,
			PluginInfoClientReport pluginInfoClient) {
		return this.pluginClientReportLogic
				.downloadReportPlugin(pluginInfoClient);
	}

	/**
	 * 上传插件
	 * 
	 */
	public void uploadReportPlugin(Request request, String fileName,
			byte[] bytes) {
		this.pluginClientReportLogic.uploadReportPlugin(fileName, bytes);
	}

	/**
	 * 删除一个插件
	 */
	public boolean deleteReportPlugin(Request request,
			PluginInfoClientReport pluginInfo) {
		return this.pluginClientReportLogic.deleteReportPlugin(pluginInfo);
	}

	/**
	 * 删除插件
	 * 
	 */
	public void deleteAllReportPlugin(Request request) {
		this.pluginClientReportLogic.deleteAllReportPlugin();
	}

	/**
	 * 载入所有插件信息
	 * 
	 * @return
	 */
	public List<PluginInfoClientReport> loadReportPlugin(Request request) {
		return pluginClientReportLogic.loadReportPlugin();
	}

	/**
	 * 查找类的数据源
	 * 
	 * @return
	 */
	public EntityMutableTreeNode getEntityMutableTreeNod(Class cls) {
		return this.toolsLogic.getEntityMutableTreeNod(cls);
	}
	
	/**
	 * 从xml中读取索引数据
	 * @return
	 */
	public List<DBIndex> getIndexInfo(boolean isYearLimitDBTable){
		return this.toolsLogic.getIndexInfo(isYearLimitDBTable);
	}
}
