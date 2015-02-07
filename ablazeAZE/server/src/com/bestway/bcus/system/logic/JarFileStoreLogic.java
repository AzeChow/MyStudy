package com.bestway.bcus.system.logic;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.system.entity.JarFile;
import com.bestway.bcus.system.entity.JarFileObject;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.authority.entity.BaseCompany;


public class JarFileStoreLogic {
	private String	jarFileSuffix	= "report";
	private String	jarFilePrefix	= "$";
	private String	jarSaveToDir	= "JarByTableData";
	private Log		loger			= LogFactory
											.getLog(JarFileStoreLogic.class);

	// ==========================================================
	// 保存文件名的构成 ==
	// companyName + keyCode + "$" + saveFileName + ".jar"
	// 
	// ==========================================================

	/**
	 * 保存数据到文件
	 * 
	 * @param fileNameNoPrefixSuffix
	 *            没有前缀的文件名
	 * @param keyString
	 * @param list
	 */
	public void saveJarFile(String fileNameNoPrefixSuffix, String keyString,boolean isKeyString,
			List<JarFileObject> list) {		
		AclUser user = CommonUtils.getAclUser();
		BaseCompany company = CommonUtils.getCompany();
		String companyName = company.getName();
		//
		// is not null -- > ok
		//
		if (fileNameNoPrefixSuffix == null || fileNameNoPrefixSuffix.equals("")) {
			return;
		}
		String hashCode =keyString;
		//
		// explain keyString paramter is not hashCode 
		//
		if(isKeyString){ 
			hashCode = String.valueOf(CommonUtils.hashCode(keyString));
		}
		String fileName = (companyName + hashCode + this.jarFilePrefix + fileNameNoPrefixSuffix)
				.trim();
		if (!fileName.endsWith("." + this.jarFileSuffix)) {
			fileName += "." + this.jarFileSuffix;
		}
		for (JarFileObject jar : list) {
			jar.setStoreDate((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
					.format(new Date()));
			jar.setStorer(user.getName());
		}
		this.saveJarFile(fileName, list);
	}
	

	/**
	 * 保存数据到文件
	 * 
	 * @param fileName
	 * @param list
	 */
	private void saveJarFile(String fileName, List<JarFileObject> list) {
		File f = new File(this.jarSaveToDir);
		if (!f.exists()) {
			f.mkdir();
		}
		File file = new File(this.jarSaveToDir + File.separator + fileName);

		ObjectOutputStream objectOutputStream = null;
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			JarOutputStream jarOutputStream = new JarOutputStream(
					fileOutputStream);
			jarOutputStream.putNextEntry(new JarEntry(fileName));
			objectOutputStream = new ObjectOutputStream(jarOutputStream);
			objectOutputStream.writeObject(list);
			objectOutputStream.flush();
			jarOutputStream.closeEntry();

		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			if (objectOutputStream != null) {
				try {
					objectOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 打开文件
	 * 
	 * @param fileName
	 * @param keyString
	 * @return
	 */
	public List<JarFileObject> openJarFile(String fileNameNoPrefix,
			String hashCode) {
		List<JarFileObject> returnList = new ArrayList<JarFileObject>();
		//
		// is not null -- > ok
		//
		if (fileNameNoPrefix == null || fileNameNoPrefix.equals("")) {
			return returnList;
		}

		BaseCompany company = CommonUtils.getCompany();
		String companyName = company.getName();

		// String hashCode = String.valueOf(CommonUtils.hashCode(keyString));
		String fileName = (companyName + hashCode + this.jarFilePrefix + fileNameNoPrefix)
				.trim();

		if (checkFileIsExist(fileName)) {
			return openJarFile(fileName);
		}
		loger.info("check file is not exist !!");
		return returnList;
	}

	/**
	 * 打开文件
	 */
	private List<JarFileObject> openJarFile(String fileName) {
		List<JarFileObject> returnList = new ArrayList<JarFileObject>();

		File f = new File(this.jarSaveToDir);
		if (!f.exists()) {
			f.mkdir();
		}

		File file = new File(this.jarSaveToDir + File.separator + fileName);
		ObjectInputStream objectInputStream = null;
		try {
			JarInputStream jarInputStream = new JarInputStream(
					new FileInputStream(file));
			jarInputStream.getNextEntry();
			objectInputStream = new ObjectInputStream(jarInputStream);
			returnList = (List<JarFileObject>) objectInputStream.readObject();
			jarInputStream.closeEntry();

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			if (objectInputStream != null) {
				try {
					objectInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return returnList;
	}

	/**
	 * 获得当前表格存入所有的 JarFile
	 * 
	 * @param keyString
	 * @return
	 */
	public List<JarFile> getJarFileName(String keyString) {
		File file = new File(this.jarSaveToDir);
		if (!file.exists()) {
			file.mkdir();
		}
		List<JarFile> fileNameList = new ArrayList<JarFile>();
		File[] files = file.listFiles(new JarFileFilter(this.jarFileSuffix));

		BaseCompany company = CommonUtils.getCompany();
		String companyName = company.getName();
		String hashCode = String.valueOf(CommonUtils.hashCode(keyString));

		for (File f : files) {
			String fileName = f.getName();
			if (fileName.startsWith(companyName + hashCode)) {
				int index = fileName.indexOf(this.jarFilePrefix);
				if (index > -1) {
					JarFile jarFile = new JarFile();

					Date date = new Date();
					date.setTime(f.lastModified());
					jarFile.setModifiyTime(new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss").format(date));

					fileName = fileName.substring(index + 1); // $
					jarFile.setFileName(fileName);

					jarFile.setHashCode(hashCode);

					fileNameList.add(jarFile);
				}
			}
		}
		return fileNameList;
	}

	/**
	 * 获得当前表格存入所有的 JarFile
	 * 
	 * @param keyString
	 * @return
	 */
	public List<JarFile> getJarFileName() {
		BaseCompany company = CommonUtils.getCompany();
		String companyName = company.getName();

		File file = new File(this.jarSaveToDir);
		if (!file.exists()) {
			file.mkdir();
		}
		List<JarFile> fileNameList = new ArrayList<JarFile>();
		File[] files = file.listFiles(new JarFileFilter(this.jarFileSuffix));

		for (File f : files) {
			String fileName = f.getName();
			int index = fileName.indexOf(this.jarFilePrefix);
			if (index > -1 && fileName.startsWith(companyName)) {

				JarFile jarFile = new JarFile();

				String hashCode = fileName.substring(0,
						fileName.indexOf(this.jarFilePrefix)).substring(
						companyName.length());
				fileName = fileName.substring(index + 1); // $

				Date date = new Date();
				date.setTime(f.lastModified());
				jarFile.setModifiyTime(new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss").format(date));
				jarFile.setHashCode(hashCode);
				jarFile.setFileName(fileName);

				fileNameList.add(jarFile);
			}
		}
		return fileNameList;
	}

	/**
	 * 检验文件是否已存在
	 * 
	 * @param fileName
	 * @return
	 */
	private boolean checkFileIsExist(String fileName) {

		String tempFileName = fileName;
		try {
			File file = new File(this.jarSaveToDir);
			if (!file.exists()) {
				file.mkdir();
			}
			File[] files = file
					.listFiles(new JarFileFilter(this.jarFileSuffix));
			for (int i = 0; i < files.length; i++) {
				if (tempFileName.equals(files[i].getName())) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteFile(String fileNameNoPrefix, String hashCode) {

		BaseCompany company = CommonUtils.getCompany();
		String companyName = company.getName();
		String tempFileName = (companyName + hashCode + this.jarFilePrefix + fileNameNoPrefix)
				.trim();
		try {
			File file = new File(this.jarSaveToDir);
			if (!file.exists()) {
				file.mkdir();
			}
			File[] files = file
					.listFiles(new JarFileFilter(this.jarFileSuffix));
			for (int i = 0; i < files.length; i++) {
				if (tempFileName.equals(files[i].getName())) {
					loger.info(tempFileName + " 文件已经删除 !!");
					files[i].delete();
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	class JarFileFilter implements FileFilter {
		private List	list	= new ArrayList();

		public JarFileFilter(String suffix) {
			this.addExtension(suffix);
		}

		public boolean accept(File f) {
			String suffix = getSuffix(f);
			if (f.isDirectory() == true) {
				return false;
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

}
