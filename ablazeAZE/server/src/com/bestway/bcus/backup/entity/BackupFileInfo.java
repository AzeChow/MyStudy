/*
 * Created on 2005-1-28
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.backup.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;

/**
 * 存放备份后文件资料
 */
public class BackupFileInfo implements Serializable {

	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 备份文件名称
	 */
	private String fileName;    

	/**
	 * 备份日期
	 */
	private String backupDate;

	/**
	 * 备份人员
	 */
	private String creater;
	
	/**
	 * 文件大小
	 */
	private String fileSize; 
	
	/**
	 * 是否完全备份
	 */
	private Boolean isBackupAll =true; 
	
	/**
	 * 备份项目名称
	 */
	private List backupItem; 
	
	/**
	 * 备份项目信息
	 */
	private String backupItemInfo; 
	
	/**
	 * 备份资料所属公司
	 */
	private Company company; 
	
//	private Map hmClassMap;

	/**
	 * 取得备份项目信息
	 * @return 备份项目信息
	 */
	public String getBackupItemInfo() {
		backupItemInfo="";
		if(backupItem!=null){
			for(int i=0;i<backupItem.size();i++){
				backupItemInfo+=backupItem.get(i).toString()+";";
			}
		}
		return backupItemInfo;
	}
	/**
	 * 取得备份项目名称
	 * @return 备份项目名称
	 */
	public List getBackupItem() {
		return backupItem;
	}
	/**
	 * 设置备份项目名称
	 * @param backupItem 备份项目名称
	 */
	public void setBackupItem(List backupItem) {
		this.backupItem = backupItem;
	}
	/**
	 * 判断是否全部备份
	 * @return 是否全部备份
	 */
	public Boolean getIsBackupAll() {
		return isBackupAll;
	}
	/**
	 * 设置是否全部备份标志
	 * @param isBackupAll 是否全部备份
	 */
	public void setIsBackupAll(Boolean isBackupAll) {
		this.isBackupAll = isBackupAll;
	}
	/**
	 * 取得文件大小
	 * @return 文件大小
	 */
	public String getFileSize() {
		return fileSize;
	}
	/**
	 * 设置文件大小
	 * @param fileSize 文件大小
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	/**
	 * 取得备份人员
	 * @return 备份人员
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * 设置备份人员
	 * @param creater 备份人员
	 */
	public void setCreater(String creater) {
		this.creater = creater;
	}

	/**
	 * 取得备份日期
	 * @return 备份日期
	 */
	public String getBackupDate() {
		return backupDate;
	}

	/**
	 * 设置备份日期
	 * @param fileCreateDate 备份日期
	 */
	public void setBackupDate(String fileCreateDate) {
		this.backupDate = fileCreateDate;
	}

	/**
	 * 取得文件名称
	 * @return 文件名称
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 设置文件名称
	 * @param fileName 文件名称
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 取得备份资料公司
	 * @return 备份资料公司
	 */
	public Company getCompany() {
		return company;
	}
	/**
	 * 设置备份资料公司
	 * @param company 备份资料公司
	 */
	public void setCompany(Company company) {
		this.company = company;
	}
	
//	public Map getHmClassMap() {
//		return hmClassMap;
//	}
//	
//	public void setHmClassMap(Map hmClassMap) {
//		this.hmClassMap = hmClassMap;
//	}
	
	/**
	 * 判断备份名称是否一致
	 */
	public boolean equals(Object arg) {
		if(arg==null){
			return false;
		}
		if(!(arg instanceof BackupFileInfo)){
			return false;
		}
		return this.getFileName().equals(((BackupFileInfo)arg).getFileName());
	}
}
