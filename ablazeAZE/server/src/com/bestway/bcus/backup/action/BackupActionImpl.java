/*
 * Created on 2005-1-19
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.backup.action;

import java.util.List;

import com.bestway.bcus.backup.entity.BackupFileInfo;
import com.bestway.bcus.backup.logic.BackupLogic;
import com.bestway.bcus.backup.logic.RestoreLogic;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;

/**
 * @author Administrator
 * @AuthorityClassAnnotation(caption = "数据备份管理", index = 0.1)
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

public class BackupActionImpl extends BaseActionImpl implements BackupAction {

	private BackupLogic backupLogic = null;

	private RestoreLogic restoreLogic = null;

	/**
	 * @return Returns the objectBackup.
	 */
	public BackupLogic getBackupLogic() {
		return backupLogic;
	}

	/**
	 * @param objectBackup
	 *            The objectBackup to set.
	 */
	public void setBackupLogic(BackupLogic objectBackup) {
		this.backupLogic = objectBackup;
	}

	/**
	 * @return Returns the objectRestore.
	 */
	public RestoreLogic getRestoreLogic() {
		return restoreLogic;
	}

	/**
	 * @param objectRestore
	 *            The objectRestore to set.
	 */
	public void setRestoreLogic(RestoreLogic objectRestore) {
		this.restoreLogic = objectRestore;
	}

	/**
	 * 在服务器上备份数据库到文件
	 * 
	 * @param backupFileInfo
	 */
	@AuthorityFunctionAnnotation(caption = "新增备份文件", index = 2)
	public BackupFileInfo backupToFile(Request request,
			BackupFileInfo backupFileInfo, String fileName, String taskId) {
		this.backupLogic.backupToFile(backupFileInfo, fileName,
				(Company) CommonUtils.getCompany(), taskId);
		return backupFileInfo;
	}

	/**
	 * 取得服务器端所有备份文件的信息列表
	 * 
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "浏览备份文件", index = 1)
	public List getBackupFilesInfo(Request request) {
		return this.backupLogic.getBackupFilesInfo();
	}

	/**
	 * 从服务器端下载备份文件
	 * 
	 * @param backupFileInfo
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "下载备份文件", index = 4)
	public byte[] downloadBackupFile(Request request,
			BackupFileInfo backupFileInfo) {
		return this.backupLogic.downloadBackupFile(backupFileInfo);
	}

	/**
	 * 从客户端上传备份文件到服务器端
	 * 
	 * @param fileContent
	 * @param backupFileInfo
	 */
	@AuthorityFunctionAnnotation(caption = "上传备份文件", index = 5)
	public BackupFileInfo uploadBackupFile(Request request, byte[] fileContent) {
		return this.restoreLogic.uploadBackupFile(fileContent);
	}

	/**
	 * 从备份文件中恢复数据到数据库
	 * 
	 * @param backupFileInfo
	 */
	@AuthorityFunctionAnnotation(caption = "恢复备份文件数据", index = 6)
	public void restoreFromFile(Request request, BackupFileInfo backupFileInfo) {
		this.restoreLogic.restoreFromFile(backupFileInfo, request.getTaskId());
	}

	/**
	 * 删除备份文件
	 * 
	 * @param backupFileInfo
	 */
	@AuthorityFunctionAnnotation(caption = "删除备份文件", index = 3)
	public void removeBakcupFile(Request request, BackupFileInfo backupFileInfo) {
		this.backupLogic.removeBakcupFile(backupFileInfo);
	}

	/**
	 * 在服务器上备份数据库到文件
	 * 
	 * @param backupFileInfo
	 */
	@AuthorityFunctionAnnotation(caption = "新增备份文件", index = 2)
	public BackupFileInfo backupToFile(Request request,
			BackupFileInfo backupFileInfo, List lsSelectedClasses,
			String fileName, String taskId) {
		this.backupLogic.backupToFile(backupFileInfo, lsSelectedClasses,
				fileName, (Company) CommonUtils.getCompany(), taskId);
		return backupFileInfo;
	}

	/**
	 * 取得服务器端所有备份文件的信息列表
	 * 
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "浏览备份文件", index = 1)
	public List getBackupFilesInfo(Request request, boolean isAll) {
		return this.backupLogic.getBackupFilesInfo(isAll);
	}

	/**
	 * @return 获得备份模块信息
	 */
	@AuthorityFunctionAnnotation(caption = "浏览备份文件", index = 1)
	public List getLsBackupModuleInfo(Request request) {
		return this.backupLogic.getLsBackupModuleInfo();
	}
}