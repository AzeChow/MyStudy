/*
 * Created on 2004-7-20
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.backup.logic;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.backup.SortingEntityClass;
import com.bestway.bcus.backup.dao.BackupDao;
import com.bestway.bcus.backup.entity.BackupFileInfo;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;

/**
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BackupLogic {

	private String saveToDir = null;

	private ZipOutputStream zipOutputStream = null;

	private Log log = LogFactory.getLog(BackupLogic.class);

	private String backupFileSuffix = "jbcus";

	private List lsBackupModuleInfo = null;

	private BackupDao backupDao = null;

	/**
	 * @return Returns the saveToDir.
	 */
	public String getSaveToDir() {
		return saveToDir;
	}

	/**
	 * @param saveToDir
	 *            The saveToDir to set.
	 */
	public void setSaveToDir(String saveToDir) {
		this.saveToDir = saveToDir;
	}

	private boolean backupData(OutputStream outputStream,
			BackupFileInfo backupFileInfo, List lsSelectedClasses,
			Company company, String taskId) {
		log.info("Backup Datebase start.");
		List saveClasses = new ArrayList();
		if (lsSelectedClasses == null || lsSelectedClasses.size() < 1) {
			saveClasses = SortingEntityClass.getInstance(
					this.getBackupDao().getSessionFactory())
					.getSortedClassNameByRefDepth();
		} else {
			saveClasses = SortingEntityClass.getInstance(
					this.getBackupDao().getSessionFactory())
					.getSortedClassNameByRefDepth(lsSelectedClasses);
		}
		if (saveClasses == null || saveClasses.size() == 0) {
			log.info("No Object need BackUp,To set saveClasses property.");
		}
		Map hm = SortingEntityClass.getInstance(
				this.getBackupDao().getSessionFactory())
				.getEntityClassNameMap();
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(saveClasses.size());
			info.setMethodName("正在备份公司:" + company.getName() + "的资料");
		}
		try {
			ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
			// 
			zipOutputStream
					.putNextEntry(new ZipEntry(backupFileInfo.toString()));
			ObjectOutputStream outtemp = new ObjectOutputStream(zipOutputStream);
			// backupFileInfo.setHmClassMap(hm);
			outtemp.writeObject(backupFileInfo);
			outtemp.flush();
			zipOutputStream.closeEntry();
			// 
			for (int i = 0; i < saveClasses.size(); i++) {
				String cls = saveClasses.get(i).toString();
				Class clsEntity = (Class) hm.get(cls);
				List objs = new ArrayList();
				log.info("Backuping " + cls);
				try {
					if (BaseScmEntity.class.isAssignableFrom(clsEntity)) {
						objs = this.getBackupDao().findEntityByCompany(cls,
								company);
					} else if (Company.class.isAssignableFrom(clsEntity)) {
						objs = this.getBackupDao().findEntityById(cls,
								company.getId());
					} else {
						objs = this.getBackupDao().findAllEntity(cls);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					continue;
				}
				zipOutputStream.putNextEntry(new ZipEntry(cls));
				ObjectOutputStream out = new ObjectOutputStream(zipOutputStream);
				out.writeObject(objs);
				out.flush();
				zipOutputStream.closeEntry();
				if (info != null) {
					info.setCurrentNum(i);
				}
			}
			zipOutputStream.close();
			log.info("Backup Datebase end.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
			// return false;
		}
		return true;
	}

	/**
	 * 备份数据到文件
	 * 
	 * @param zipFile
	 */
	public void backupToFile(String zipFileName, String taskId) {
		List list = this.getBackupDao().findAllEntity("Company");
		for (int i = 0; i < list.size(); i++) {
			Company company = (Company) list.get(i);
			try {
				BackupFileInfo backupFileInfo = new BackupFileInfo();
				backupFileInfo.setIsBackupAll(new Boolean(true));
				backupFileInfo.setCompany(company);
				backupFileInfo.setFileName(zipFileName + String.valueOf(i)
						+ "." + this.backupFileSuffix);
				backupToFile(backupFileInfo, zipFileName, company, taskId);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	/**
	 * 在服务器上备份数据库到文件
	 * 
	 * @param backupFileInfo
	 */
	public void backupToFile(BackupFileInfo backupFileInfo, String fileName,
			Company company, String taskId) {
		this.backupToFile(backupFileInfo, null, fileName, company, taskId);
	}

	/**
	 * 在服务器上备份数据库到文件
	 * 
	 * @param backupFileInfo
	 */
	public void backupToFile(BackupFileInfo backupFileInfo,
			List lsSelectedClasses, String fileName, Company company,
			String taskId) {
		Date currDate = new Date();
		backupFileInfo.setFileName(fileName);
		backupFileInfo.setBackupDate((new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss")).format(currDate));
		try {
			backupFileInfo.setCreater(CommonUtils.getRequest().getUser()
					.getUserName());
		} catch (Exception e) {
			backupFileInfo.setCreater("Server Backup");
		}
		String zipFileName = "";
		int lastIndex = fileName.lastIndexOf(File.separator);
		if (lastIndex < 0) {
			zipFileName = this.saveToDir + File.separator + fileName;
		} else {
			zipFileName = fileName;
			backupFileInfo.setFileName(fileName.substring(lastIndex + 1,
					fileName.length()));
		}
		File file = new File(zipFileName);
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			backupData(fileOutputStream, backupFileInfo, lsSelectedClasses,
					company, taskId);
			long fileSize = file.length() / 1024;
			backupFileInfo.setFileSize(fileSize + "KB");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 取得服务器端所有备份文件的信息列表
	 * 
	 * @return
	 */
	public List getBackupFilesInfo() {
		return this.getBackupFilesInfo(true);
	}

	/**
	 * 取得服务器端所有备份文件的信息列表
	 * 
	 * @return
	 */
	public List getBackupFilesInfo(boolean isAll) {
		List list = new ArrayList();
		try {
			File file = new File(this.saveToDir);
			if (!file.exists()) {
				file.mkdir();
			}
			File[] files = file.listFiles(new BackupFileFilter(
					this.backupFileSuffix));
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					File zipFile = files[i];
					FileInputStream fileInputStream = new FileInputStream(
							zipFile);
					ZipInputStream zipInputStream = new ZipInputStream(
							fileInputStream);
					try {
						ZipEntry entry = zipInputStream.getNextEntry();
					} catch (Exception ex) {
						// System.out.println("-------------------------------eeeeeeeeeeeeeeee");
						continue;
					}
					ObjectInputStream objectInputStream = new ObjectInputStream(
							zipInputStream);
					BackupFileInfo backupFileInfo = null;
					try {
						backupFileInfo = (BackupFileInfo) objectInputStream
								.readObject();
						// System.out.println("-------------------------------bbbbbbbbbbbbbb");
					} catch (Exception e) {
						fileInputStream.close();
						zipFile.delete();
						// System.out.println("-------------------------------aaaaaaaaaaaa");
						continue;
					}
					long fileSize = zipFile.length() / 1024;
					backupFileInfo.setFileSize(fileSize + "KB");
					// if (!"缺省公司".equals(CommonUtils.getCompany().getName())
					// && !CommonUtils.getCompany().equals(
					// backupFileInfo.getCompany())) {
					// continue;
					// }
					if (isAll) {
						if (backupFileInfo.getIsBackupAll() == null
								|| backupFileInfo.getIsBackupAll()
										.booleanValue()) {
							// System.out.println("-------------------------------ccccccccccccccccccccc");
							list.add(backupFileInfo);
						}
					} else {
						if (backupFileInfo.getIsBackupAll() != null
								&& !backupFileInfo.getIsBackupAll()
										.booleanValue()) {
							// System.out.println("-------------------------------ddddddddddddddddddddddd");
							list.add(backupFileInfo);
						}
					}
					fileInputStream.close();
				}
			}
		} catch (Exception e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 从服务器端下载备份文件
	 * 
	 * @param backupFileInfo
	 * @return
	 */
	public byte[] downloadBackupFile(BackupFileInfo backupFileInfo) {
		String fileName = this.saveToDir + File.separator
				+ backupFileInfo.getFileName();
		File file = new File(fileName);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			byte[] fileContent = new byte[(int) file.length()];
			fileInputStream.read(fileContent);
			return fileContent;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				fileInputStream.close();
			} catch (IOException e1) {
				throw new RuntimeException(e1.getMessage());
			}
		}
	}

	public void backUp(String taskId) {
		if (saveToDir == null || saveToDir.equals("")) {
			log.error("SaveDir is null,To set saveToDir property.");
			return;
		}
		File saveDir = new File(saveToDir);
		if (!saveDir.exists()) {
			if (!saveDir.mkdirs()) {
				log.error("SaveDir is not exists,and mkdir " + saveDir
						+ " failue.");
				return;
			}
		} else {
			if (saveDir.isFile()) {
				log
						.error("SaveDir must is dir, but " + saveDir
								+ " is a file.");
				return;
			}
		}
		String zipFileName = (new SimpleDateFormat("yyyy_MM_dd_H_m_s"))
				.format(new Date())
				+ "全部数据备份";
		backupToFile(zipFileName, taskId);
	}

	class BackupFileFilter implements FileFilter {
		private List list = new ArrayList();

		BackupFileFilter(String suffix) {
			this.addExtension(suffix);
		}

		public boolean accept(File f) {
			String suffix = getSuffix(f);
			if (f.isDirectory() == true) {
				return true;
			}
			if (suffix != null) {
				if (isAcceptSuffix(suffix)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		public String getDescription() {
			String description = "*.";
			for (int i = 0; i < list.size(); i++) {
				description += list.get(i).toString() + " & *.";
			}
			return description.substring(0, description.length() - 5);
		}

		private String getSuffix(File f) {
			String s = f.getPath(), suffix = null;
			int i = s.lastIndexOf('.');
			if (i > 0 && i < s.length() - 1)
				suffix = s.substring(i + 1).toLowerCase();
			return suffix;
		}

		private boolean isAcceptSuffix(String suffix) {
			boolean isAccept = false;
			for (int i = 0; i < list.size(); i++) {
				if (suffix.equals(list.get(i).toString())) {
					isAccept = true;
					break;
				}
			}
			return isAccept;
		}

		public void addExtension(String extensionName) {
			if (extensionName.equals("")) {
				return;
			}
			list.add(extensionName.toLowerCase().trim());
		}
	}

	/**
	 * 删除备份文件
	 * 
	 * @param backupFileInfo
	 */
	public void removeBakcupFile(BackupFileInfo backupFileInfo) {
		String fileName = this.saveToDir + File.separator
				+ backupFileInfo.getFileName();
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * @return 获得备份模块信息
	 */
	public List getLsBackupModuleInfo() {
		return lsBackupModuleInfo;
	}

	/**
	 * @param lsBackupModuleInfo
	 *            The lsBackupModuleInfo to set.
	 */
	public void setLsBackupModuleInfo(List lsBackupModuleInfo) {
		this.lsBackupModuleInfo = lsBackupModuleInfo;
	}

	/**
	 * @return Returns the backupDao.
	 */
	public BackupDao getBackupDao() {
		return backupDao;
	}

	/**
	 * @param backupDao
	 *            The backupDao to set.
	 */
	public void setBackupDao(BackupDao backupDao) {
		this.backupDao = backupDao;
	}
}