/*
 * Created on 2004-7-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.backup.logic;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.backup.SortingEntityClass;
import com.bestway.bcus.backup.dao.BackupDao;
import com.bestway.bcus.backup.entity.BackupFileInfo;
import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseEntity;
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
public class RestoreLogic {

	private Log log = LogFactory.getLog(RestoreLogic.class);

	private int filesCount = 0;

	private String backupFileSuffix = "jbcus";

	private String restoreFile = "";

	private List classes = new ArrayList();

	private String saveToDir = null;

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

	/**
	 * @return Returns the restoreFile.
	 */
	public String getRestoreFile() {
		return restoreFile;
	}

	/**
	 * @param restoreFile
	 *            The restoreFile to set.
	 */
	public void setRestoreFile(String restoreFile) {
		this.restoreFile = restoreFile;
	}

	private boolean checkFileisBackupFile(InputStream inputStream) {
		ZipInputStream zipInput = null;
		try {
			zipInput = new ZipInputStream(inputStream);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		if (zipInput == null)
			return false;
		filesCount = 0;
		classes.clear();
		try {
			zipInput.getNextEntry();
		} catch (IOException e2) {
			throw new RuntimeException(e2.getMessage());
		}
		while (true) {
			String zipEntryName = null;
			try {
				ZipEntry zipEntry = (ZipEntry) zipInput.getNextEntry();
				if (zipEntry == null) {
					break;
				}
				zipEntryName = zipEntry.getName();
			} catch (Exception e1) {
				throw new RuntimeException(e1.getMessage());
			}
			if (zipEntryName == null || "".equals(zipEntryName))
				break;
			classes.add(zipEntryName);
			log.info("Restore checked class " + zipEntryName);
			filesCount++;
		}
		try {
			inputStream.close();
		} catch (IOException e1) {
			throw new RuntimeException(e1.getMessage());
		}
		return true;
	}

	/**
	 * 根据公司,删除此公司的所有资料
	 * 
	 * @param company
	 */
	private void deleteByCompany(Company company, String taskId) {
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		List lsEntityClass = SortingEntityClass.getInstance(
				this.getBackupDao().getSessionFactory())
				.getSortedClassNameByRefDepth();
		Map hm = SortingEntityClass.getInstance(
				this.getBackupDao().getSessionFactory())
				.getEntityClassNameMap();
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(lsEntityClass.size());
			info.setMethodName("正在清空公司:" + company.getName() + "的旧资料");
		}
		for (int i = lsEntityClass.size() - 1; i >= 0; i--) {
			String clsName = lsEntityClass.get(i).toString();
			Class clsEntity = (Class) hm.get(clsName);
			log.info("Deleteing class " + clsName);
			try {
				if (Company.class.isAssignableFrom(clsEntity)) {
					this.getBackupDao().deleteEntityById(clsName,
							company.getId());
				} else if (BaseScmEntity.class.isAssignableFrom(clsEntity)) {
					this.getBackupDao().deleteEntityByCompany(clsName, company);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			if (info != null) {
				info.setCurrentNum(i);
			}
		}
	}

	private void restoreData(InputStream inputStream, String taskId) {
		try {
			ZipInputStream in = new ZipInputStream(inputStream);
			in.getNextEntry();
			BackupFileInfo backupFileInfo = (BackupFileInfo) (new ObjectInputStream(
					in)).readObject();
			// Map hmClassMap = backupFileInfo.getHmClassMap();
			Company company = backupFileInfo.getCompany();
			if (company == null) {
				throw new RuntimeException("你选择的备份文件不是按公司备份的");
			}
			if (backupFileInfo.getIsBackupAll().booleanValue()) {
				restoreBackupAllData(taskId, in, company);
			} else {
				restoreBackupPartData(in);
			}
			in.close();
		} catch (Exception e) {
			log.error("Restore has error:" + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 恢复全部备份的数据
	 * 
	 * @param taskId
	 * @param in
	 * @param company
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void restoreBackupAllData(String taskId, ZipInputStream in,
			Company company) throws IOException, ClassNotFoundException {
		if ((!CommonUtils.getCompany().equals(company))
				&& (CommonUtils.getCompany().getName().indexOf("缺省公司") > -1)) {
			deleteByCompany((Company) CommonUtils.getCompany(), taskId);
			System.out.println("删除缺省公司的资料");
		}
		deleteByCompany(company, taskId);// , hmClassMap
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(filesCount);
			info.setMethodName("正在恢复公司:" + company.getName() + "的资料");
		}
		for (int i = 0; i < filesCount; i++) {
			ZipEntry entry = in.getNextEntry();
			String zipEntryName = entry.getName();
			log.info("Restoreing class " + zipEntryName);
			ObjectInput objin = new ObjectInputStream(in);
			List list = new ArrayList();
			try {
				list = (List) objin.readObject();
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			Class clsEntity = null;
			try {
				clsEntity = Class.forName(zipEntryName);
			} catch (Exception e) {
				log.error("类" + zipEntryName + "没有找到！");
				continue;
			}
			if (clsEntity == null) {
				log.error("类" + zipEntryName + "没有找到！");
				continue;
			}
			// Class clsEntity = (Class) hmClassMap.get(zipEntryName);
			if (BaseScmEntity.class.isAssignableFrom(clsEntity)) {
				for (int j = 0; j < list.size(); j++) {
					Object obj = list.get(j);
					try {
						this.getBackupDao().save(obj,
								((BaseEntity) obj).getId());
					} catch (Exception ex) {
					}
					info.setMethodName("正在恢复公司:" + company.getName()
							+ "的资料.子项进度" + j + "/" + list.size());
					// renewalEntityWhenIsnotExists((BaseEntity) obj, 0);
				}
			} else if (CustomBaseEntity.class.isAssignableFrom(clsEntity)) {
				List lsExistCode = this.backupDao
						.findCustomBaseEntityExistCode(clsEntity.getName());
				for (int j = 0; j < list.size(); j++) {
					Object obj = list.get(j);
					// int count = this.backupDao.findEntityCountByCode(obj
					// .getClass().getName(), ((CustomBaseEntity) obj)
					// .getCode());
					// if (count < 1) {// customBaseEntity == null) {
					if (!lsExistCode.contains(((CustomBaseEntity) obj)
							.getCode())) {
						try {
							this.getBackupDao().save(obj,
									((CustomBaseEntity) obj).getCode());
						} catch (Exception ex) {

						}
					} else {
						// this.getBackupDao().saveOrUpdate(obj);
					}
					info.setMethodName("正在恢复公司:" + company.getName()
							+ "的资料.子项进度" + j + "/" + list.size());
				}
			} else if (BaseEntity.class.isAssignableFrom(clsEntity)
					&& !BaseScmEntity.class.isAssignableFrom(clsEntity)) {
				List lsExistId = this.backupDao.findBaseEntityExistId(clsEntity
						.getName());
				for (int j = 0; j < list.size(); j++) {
					Object obj = list.get(j);
//					int count = this.backupDao.findEntityCountById(obj
//							.getClass().getName(), ((BaseEntity) obj).getId());
//					if (count < 1) {// baseEntity == null) {
					if (!lsExistId.contains(((BaseEntity) obj)
							.getId())) {
//						// renewalEntityWhenIsnotExists((BaseEntity) obj, 0);
						try {
							this.getBackupDao().save(obj,
									((BaseEntity) obj).getId());
						} catch (Exception ex) {

						}
					} else {
						// this.getBackupDao().saveOrUpdate(obj);
					}
					info.setMethodName("正在恢复公司:" + company.getName()
							+ "的资料.子项进度" + j + "/" + list.size());
				}
			} else {
				log.error("Restore Obj faile! Because " + clsEntity
						+ " is not Entity class");
			}
			in.closeEntry();
			if (info != null) {
				info.setCurrentNum(i);
			}
		}
	}

	/**
	 * 恢复部分备份的数据
	 * 
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void restoreBackupPartData(ZipInputStream in) throws IOException,
			ClassNotFoundException {
		// for (int i = 0; i < filesCount; i++) {
		// ZipEntry entry = in.getNextEntry();
		// String zipEntryName = entry.getName();
		// log.info("Restoreing class " + zipEntryName);
		// ObjectInput objin = new ObjectInputStream(in);
		// List list = (List) objin.readObject();
		// Class clsEntity = null;
		// try {
		// clsEntity = Class.forName(zipEntryName);
		// } catch (Exception e) {
		// log.error("类" + zipEntryName + "没有找到！");
		// continue;
		// }
		// if (clsEntity == null) {
		// log.error("类" + zipEntryName + "没有找到！");
		// continue;
		// }
		// // Class clsEntity = (Class) hmClassMap.get(zipEntryName);
		// if (BaseEntity.class.isAssignableFrom(clsEntity)) {
		// for (int j = 0; j < list.size(); j++) {
		// Object obj = list.get(j);
		// int count = this.backupDao.findEntityCountById(obj
		// .getClass().getName(), ((BaseEntity) obj).getId());
		// if (count < 1) {// baseEntity == null) {
		// renewalEntityWhenIsnotExists((BaseEntity) obj, 0);
		// } else {
		// this.getBackupDao().saveOrUpdate(obj);
		// }
		// }
		// } else if (CustomBaseEntity.class.isAssignableFrom(clsEntity)) {
		// for (int j = 0; j < list.size(); j++) {
		// Object obj = list.get(j);
		// int count = this.backupDao.findEntityCountByCode(obj
		// .getClass().getName(), ((CustomBaseEntity) obj)
		// .getCode());
		// if (count < 1) {// customBaseEntity == null) {
		// this.getBackupDao().save(obj,
		// ((CustomBaseEntity) obj).getCode());
		// } else {
		// this.getBackupDao().saveOrUpdate(obj);
		// }
		// }
		// } else {
		// log.error("Restore Obj faile! Because " + clsEntity
		// + " is not Entity class");
		// }
		// in.closeEntry();
		// }
	}

	// /**
	// * 当在恢复备份关务基础资料的时候，如果对象相关联的其他对象在数据库中不存在的话，就把这个关联的对象新增到数据库中
	// *
	// * @param baseEntity
	// */
	// private void renewalEntityWhenIsnotExists(BaseEntity baseEntity, int
	// layer) {
	// if (layer > 10) {
	// System.out.println("-----------------------------类的关联基数超过10");
	// return;
	// }
	// PropertyDescriptor[] propertyDescriptors = PropertyUtils
	// .getPropertyDescriptors(baseEntity);
	// Object obj = null;
	// for (int i = 0; i < propertyDescriptors.length; i++) {
	// try {
	// obj = PropertyUtils.getNestedProperty(baseEntity,
	// propertyDescriptors[i].getName());
	// if (obj instanceof CustomBaseEntity) {
	// int count = this.getBackupDao().findEntityCountByCode(
	// obj.getClass().getName(),
	// ((CustomBaseEntity) obj).getCode());
	// if (count < 1) {
	// try {
	// this.getBackupDao().save(obj,
	// ((CustomBaseEntity) obj).getCode());
	// } catch (Exception ex) {
	//
	// }
	// }
	// } else if ((obj instanceof BaseEntity)
	// && !(obj instanceof BaseScmEntity)) {
	// int count = this.getBackupDao().findEntityCountById(
	// obj.getClass().getName(),
	// ((BaseEntity) obj).getId());
	// if (count < 1) {// baseEntity == null) {
	// int subLayer = layer + 1;
	// renewalEntityWhenIsnotExists((BaseEntity) obj, subLayer);
	// }
	// }
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// // throw new RuntimeException(e.getMessage());
	// }
	// }
	// try {
	// this.getBackupDao().save(baseEntity, baseEntity.getId());
	// } catch (Exception ex) {
	//
	// }
	// // log.info("renewalCustomBaseEntityWhenIsnotExists");
	// }

	/**
	 * 从客户端上传备份文件到服务器端
	 * 
	 * @param fileContent
	 * @param backupFileInfo
	 */
	public BackupFileInfo uploadBackupFile(byte[] fileContent) {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				fileContent);
		ZipInputStream in = new ZipInputStream(byteArrayInputStream);
		BackupFileInfo backupFileInfo = null;
		try {
			in.getNextEntry();
			backupFileInfo = (BackupFileInfo) (new ObjectInputStream(in))
					.readObject();
			// (new ObjectInputStream(in))
			// .readObject();
			if (checkFileIsExist(backupFileInfo.getFileName())) {
				throw new RuntimeException("备份文件"
						+ backupFileInfo.getFileName() + "在服务器已存在!");
			}
			String fileName = this.saveToDir + File.separator
					+ backupFileInfo.getFileName();
			in.close();
			File file = new File(fileName);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(fileContent);
			fileOutputStream.flush();
			fileOutputStream.close();
			long fileSize = file.length() / 1024;
			backupFileInfo.setFileSize(fileSize + "KB");
		} catch (Exception e) {
			// e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return backupFileInfo;
	}

	private boolean checkFileIsExist(String fileName) {
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
					ZipEntry entry = zipInputStream.getNextEntry();
					ObjectInputStream objectInputStream = new ObjectInputStream(
							zipInputStream);
					BackupFileInfo backupFileInfo = null;
					try {
						backupFileInfo = (BackupFileInfo) objectInputStream
								.readObject();
					} catch (Exception e) {
						fileInputStream.close();
						zipFile.delete();
						continue;
					}
					fileInputStream.close();
					if (backupFileInfo != null) {
						if (backupFileInfo.getFileName().equals(fileName)) {
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 从备份文件中恢复数据到数据库
	 * 
	 * @param backupFileInfo
	 */
	public void restoreFromFile(BackupFileInfo backupFileInfo, String taskId) {
		String fileName = this.saveToDir + File.separator
				+ backupFileInfo.getFileName();
		File file = new File(fileName);
		try {
			FileInputStream tempFileInputStream = new FileInputStream(file);
			if (!checkFileisBackupFile(tempFileInputStream)) {
				log.error("Restore File is not a Backup File!");
				return;
			}
			FileInputStream fileInputStream = new FileInputStream(file);
			restoreData(fileInputStream, taskId);
		} catch (Exception e) {
			// e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public void restoreFromFile(File file) {
		if (!file.isFile()) {
			log.error("Restore File " + file + " is not a File!");
			return;
		}
		if (!file.exists()) {
			log.error("Restore File " + file + " is not exists!");
			return;
		}

		try {
			if (!checkFileisBackupFile(new FileInputStream(file))) {
				log.error("Restore File is not a Backup File!");
				return;
			}
			restoreData(new FileInputStream(file), null);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void restore() {
		if (restoreFile == null || restoreFile.equals("")) {
			log.error("Restore file " + restoreFile
					+ " is null,To set restoreFile property.");
			return;
		}
		File file = new File(restoreFile);
		if (!file.exists()) {
			log.error("Restore file " + restoreFile + " is not exists.");
			return;
		}
		restoreFromFile(file);
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