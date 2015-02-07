/*
 * Created on 2005-1-19
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.backup.action;

import java.util.List;

import com.bestway.bcus.backup.entity.BackupFileInfo;
import com.bestway.common.Request;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface BackupAction {
	// /**
	// * 取得备份的数据 注意：返回的list的第一位置保存的是数据的顺序
	// *
	// * @return
	// */
	// byte[] getBackupData(Request request);
	//
	// /**
	// * 恢复数据
	// *
	// * @param request
	// */
	// void restoreData(Request request,byte[] fileContext);

	/**
	 * 在服务器上备份数据库到文件
	 * 
	 * @param backupFileInfo
	 */
	BackupFileInfo backupToFile(Request request, BackupFileInfo backupFileInfo,
			String fileName, String taskId);

	/**
	 * 取得服务器端所有备份文件的信息列表
	 * 
	 * @return
	 */
	List getBackupFilesInfo(Request request);

	/**
	 * 从服务器端下载备份文件
	 * 
	 * @param backupFileInfo
	 * @return
	 */
	byte[] downloadBackupFile(Request request, BackupFileInfo backupFileInfo);

	/**
	 * 从客户端上传备份文件到服务器端
	 * 
	 * @param fileContent
	 * @param backupFileInfo
	 */
	BackupFileInfo uploadBackupFile(Request request, byte[] fileContent);

	/**
	 * 从备份文件中恢复数据到数据库
	 * 
	 * @param backupFileInfo
	 */
	void restoreFromFile(Request request, BackupFileInfo backupFileInfo);

	/**
	 * 删除备份文件
	 * 
	 * @param backupFileInfo
	 */
	void removeBakcupFile(Request request, BackupFileInfo backupFileInfo);

	/**
	 * 在服务器上备份数据库到文件
	 * 
	 * @param backupFileInfo
	 */
	BackupFileInfo backupToFile(Request request, BackupFileInfo backupFileInfo,
			List lsSelectedClasses, String fileName, String taskId);

	/**
	 * 取得服务器端所有备份文件的信息列表
	 * 
	 * @return
	 */
	List getBackupFilesInfo(Request request, boolean isAll);

	/**
	 * @return 获得备份模块信息
	 */
	List getLsBackupModuleInfo(Request request);

}