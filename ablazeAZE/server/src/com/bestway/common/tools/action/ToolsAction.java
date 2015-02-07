/*
 * Created on 2004-9-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.tools.action;

import java.util.List;
import java.util.Map;

import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;
import com.bestway.common.tools.entity.DBIndex;
import com.bestway.common.tools.entity.EntityMutableTreeNode;
import com.bestway.common.tools.entity.FileResource;
import com.bestway.common.tools.entity.FtpConfig;
import com.bestway.common.tools.entity.PluginInfo;
import com.bestway.common.tools.entity.PluginInfoClient;
import com.bestway.common.tools.entity.PluginInfoClientReport;
import com.bestway.common.tools.entity.TempNodeItem;
import com.bestway.common.tools.entity.TempResultSet;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates SQL,HSQL操作action
 *         checked by 陈井彬 2008-12.6
 */
public interface ToolsAction {
	/**
	 * 运行HSQL
	 * 
	 * @param hql
	 * @return
	 */
	List execHql(String hql);

	/**
	 * 执行SQL
	 * 
	 * @param hql
	 * @return
	 */
	int delete(String hql);

	/**
	 * 获得实体信息
	 * 
	 * @return
	 */
	List getEntityInfo();

	/**
	 * 批处理运行HSQL更新或删除
	 * 
	 * @param hql
	 * @return
	 */
	int execute(String hql);

	/**
	 * 获得处理信息
	 * 
	 * @param id
	 * @return
	 */
	ProgressInfo getProgressInfo(String id);

	/**
	 * 删除处理信息
	 * 
	 * @param id
	 */
	void removeProgressInfo(String id);

	/**
	 * 获得class结点集合
	 * 
	 * @return
	 */
	List<TempNodeItem> getTempNodeItems();

	/**
	 * 获得指定class结点
	 * 
	 * @return
	 */
	List<TempNodeItem> getTempNodeItems(String className);

	/**
	 * insert ,update , delete
	 */
	int executeUpdateSql(String sql);

	/**
	 * 比如说 create 语名 drop 语句
	 */
	boolean executeSql(String sql);

	/**
	 * 获得结果集
	 * 
	 * @param sql
	 * @return
	 */
	TempResultSet getTempResultSet(String sql);

	/**
	 * 获得DB表结构
	 * 
	 * @return
	 */
	Map<TempNodeItem, List<TempNodeItem>> getTableColumnMap();

	/**
	 * 加入注释
	 * 
	 * @param list
	 * @param parentEntityName
	 * @return
	 */
	List<TempNodeItem> getAnnotate(List<TempNodeItem> list,
			String parentEntityName);

	/**
	 * 动态动行一个插件
	 * 
	 * @param obj
	 * @return
	 */
	Object runPlugin(Request request, PluginInfo pluginInfo);

	/**
	 * 载入所有插件信息
	 * 
	 * @return
	 */
	List<PluginInfo> loadPlugin(Request request);

	/**
	 * 下载插件
	 * 
	 * @return
	 */
	byte[] downloadPlugin(Request request, PluginInfo pluginInfo);

	/**
	 * 上传插件
	 * 
	 */
	void uploadPlugin(Request request, String fileName, byte[] bytes);

	/**
	 * 删除一个插件
	 * 
	 */
	boolean deletePlugin(Request request, PluginInfo pluginInfo);

	/**
	 * 获取当前FTp 上的所有文件
	 * 
	 * @param filePath
	 * @return
	 */
	List<FileResource> getResourceList(String server, String userName,
			String password, String directory);

	/**
	 * 下载插件
	 * 
	 * @return
	 */
	byte[] downloadFileResource(String server, String userName,
			String password, FileResource fileResource);

	/**
	 * 上传插件
	 * 
	 */
	StringBuffer uploadFileResource(String server, String userName,
			String password, List<String> remotes, List<byte[]> bytess);

	/**
	 * 删除一个报表文件
	 */
	StringBuffer deleteAllFileResource(String server, String userName,
			String password, String directory);

	/**
	 * 删除一个报表文件
	 */
	StringBuffer deleteFileResource(String server, String userName,
			String password, List<FileResource> fileResources);

	/**
	 * 查找FTP配置
	 * 
	 * @param request
	 * @return
	 */
	FtpConfig findFtpConfig(Request request);

	/**
	 * 保存FTP配置
	 * 
	 * @param request
	 * @return
	 */
	FtpConfig saveFtpConfig(Request request, FtpConfig ftpConfig);

	/**
	 * 载入所有插件信息
	 * 
	 * @return
	 */
	List<PluginInfoClient> loadClientPlugin(Request request);

	/**
	 * 下载插件
	 * 
	 * @return
	 */
	byte[] downloadClientPlugin(Request request,
			PluginInfoClient pluginInfoClient);

	/**
	 * 上传插件
	 * 
	 */
	void uploadClientPlugin(Request request, String fileName, byte[] bytes);

	/**
	 * 删除一个插件
	 */
	boolean deleteClientPlugin(Request request, PluginInfoClient pluginInfo);

	/**
	 * 删除插件
	 * 
	 */
	void deleteAllClientPlugin(Request request);

	/**
	 * 动态动行一个插件 当客户端调用时,动态动行一个插件
	 * 
	 * @param obj
	 * @return
	 */
	List runServerPlugin(Request request, String className, Map parameters);

	/**
	 * 动态动行一个插件 只要求方法名相同，参数个数相同，来调用某个方法
	 * 
	 * @param obj
	 * @return
	 */
	Object runServerPlugin(Request request, String className,
			String methodName, Object... args);

	/**
	 * 动态动行一个插件 非常准确的调用某个方法
	 * 
	 * @param obj
	 * @return
	 */
	Object runServerPlugin(Request request, String className,
			String methodName, Class[] paramTypes, Object... args);

	/** 停止服务器插件 */
	void stopJbcusServerPlugin(Request request, PluginInfo pluginInfo);

	/** 重启服务器插件 */
	void restartJbcusServerPlugin(Request request, PluginInfo pluginInfo);

	/** 服务端插件是否运行 */
	boolean isRunJbcusServerPlugin(Request request, PluginInfo pluginInfo);

	/** 保存插件信息 */
	PluginInfo savePluginInfo(Request request, PluginInfo pluginInfo);

	/**
	 * 
	 * 获得 PluginInfoClientReport by traget class name
	 * 
	 */
	List<PluginInfoClientReport> findPluginInfoClientReport(Request request,
			String tragetClassName);

	/**
	 * 动态动行一个插件 当客户端调用时,动态动行一个插件
	 * 
	 * @param obj
	 * @return
	 */
	List runReportServerPlugin(Request request, String serverReportClass,
			Map parameters);

	/**
	 * 动态动行一个插件 只要求方法名相同，参数个数相同，来调用某个方法
	 * 
	 * @param obj
	 * @return
	 */
	Object runReportServerPlugin(Request request, String className,
			String methodName, Object... args);

	/**
	 * 动态动行一个插件 非常准确的调用某个方法
	 * 
	 * @param obj
	 * @return
	 */
	Object runReportServerPlugin(Request request, String className,
			String methodName, Class[] paramTypes, Object... args);

	/**
	 * 下载插件
	 * 
	 * @return
	 */
	byte[] downloadReportPlugin(Request request,
			PluginInfoClientReport pluginInfoClient);

	/**
	 * 上传插件
	 * 
	 */
	void uploadReportPlugin(Request request, String fileName, byte[] bytes);

	/**
	 * 删除一个插件
	 */
	boolean deleteReportPlugin(Request request,
			PluginInfoClientReport pluginInfo);

	/**
	 * 删除插件
	 * 
	 */
	void deleteAllReportPlugin(Request request);

	/**
	 * 载入所有插件信息
	 * 
	 * @return
	 */
	List<PluginInfoClientReport> loadReportPlugin(Request request);

	/**
	 * 查找类的数据源
	 * 
	 * @return
	 */
	EntityMutableTreeNode getEntityMutableTreeNod(Class cls);
	
	/**
	 * 从xml中读取索引数据
	 * @return
	 */
	List<DBIndex> getIndexInfo(boolean isYearLimitDBTable);
}