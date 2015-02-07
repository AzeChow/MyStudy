package com.bestway.bcus.client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.JOptionPane;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.JarFile;
import com.bestway.bcus.system.entity.JarFileObject;
import com.bestway.client.util.JarDataItem;
import com.bestway.client.util.JarDataStoreInterface;
import com.bestway.client.util.JarItem;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.Request;

public class JarDataStoreImpl implements JarDataStoreInterface {

	private SystemAction	systemAction	= null;

	public JarDataStoreImpl() {
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
	}

	/**
	 * 保存数据到文件
	 * 
	 * @param fileNameNoPrefixSuffix
	 *            没有前缀的文件名
	 * @param keyString
	 * @param list
	 */
	public void saveJarFile(String fileNameNoPrefixSuffix, String keyString,
			boolean isKeyString, List<JarDataItem> list) {
		List<JarFileObject> jarFileObjectList = new ArrayList<JarFileObject>();
		for (JarDataItem item : list) {
			JarFileObject jarFileObject = new JarFileObject();
			jarFileObject.setColumnNames(item.getColumnNames());
			jarFileObject.setNote(item.getNote());
			jarFileObject.setRows(item.getRows());
			jarFileObject.setStoreIndex(item.getStoreIndex());
			jarFileObjectList.add(jarFileObject);
		}
		new SaveJarFileThread(fileNameNoPrefixSuffix, keyString, isKeyString,
				jarFileObjectList).start();
	}

	class SaveJarFileThread extends Thread {
		private String				fileNameNoPrefixSuffix, keyString;
		private boolean				isKeyString			= true;
		private List<JarFileObject>	jarFileObjectList	= null;

		public SaveJarFileThread(String fileNameNoPrefixSuffix,
				String keyString, boolean isKeyString,
				List<JarFileObject> jarFileObjectList) {
			this.jarFileObjectList = jarFileObjectList;
			this.fileNameNoPrefixSuffix = fileNameNoPrefixSuffix;
			this.keyString = keyString;
			this.isKeyString = isKeyString;
		}

		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {
				long beginTime = System.currentTimeMillis();
				CommonProgress.showProgressDialog(flag, FmMain.getInstance(),
						false, null, 0);
				CommonProgress.setMessage(flag, "系统正在保存表中数据到文件，请稍后...");
				systemAction.saveJarFile(new Request(CommonVars.getCurrUser()),
						fileNameNoPrefixSuffix, keyString, isKeyString,
						jarFileObjectList);
				CommonProgress.closeProgressDialog(flag);
				String info = "保存表中数据到文件任务完成,共用时:"
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
				JOptionPane.showMessageDialog(FmMain.getInstance(), info, "提示",
						2);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(FmMain.getInstance(), "获取数据失败：！"
						+ e.getMessage(), "提示", 2);
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
	public List<JarDataItem> openJarFile(String fileNameNoPrefix,
			String hashCode) {
		List<JarFileObject> jarFileObjectList = this.systemAction.openJarFile(
				new Request(CommonVars.getCurrUser()), fileNameNoPrefix,
				hashCode);
		List<JarDataItem> jarDataItemList = new ArrayList<JarDataItem>();
		for (JarFileObject item : jarFileObjectList) {
			JarDataItem jarDataItem = new JarDataItem();
			jarDataItem.setColumnNames(item.getColumnNames());
			jarDataItem.setNote(item.getNote());
			jarDataItem.setRows(item.getRows());
			jarDataItem.setStoreIndex(item.getStoreIndex());
			jarDataItemList.add(jarDataItem);
		}
		return jarDataItemList;
	}

	/**
	 * 获得当前表格存入所有的 JarFile
	 * 
	 * @param keyString
	 * @return
	 */
	public List<JarItem> getJarFileName(String keyString) {
		List<JarFile> jarFiles = this.systemAction.getJarFileName(new Request(
				CommonVars.getCurrUser()), keyString);
		List<JarItem> list = new ArrayList<JarItem>();
		for (JarFile jarFile : jarFiles) {
			JarItem item = new JarItem();
			item.setFileName(jarFile.getFileName());
			item.setHashCode(jarFile.getHashCode());
			item.setModifiyTime(jarFile.getModifiyTime());
			list.add(item);
		}
		return list;
	}

	/**
	 * 获得当前表格存入所有的 JarFile
	 * 
	 * @param keyString
	 * @return
	 */
	public List<JarItem> getJarFileName() {
		List<JarFile> jarFiles = this.systemAction.getJarFileName(new Request(
				CommonVars.getCurrUser()));
		List<JarItem> list = new ArrayList<JarItem>();
		for (JarFile jarFile : jarFiles) {
			JarItem item = new JarItem();
			item.setFileName(jarFile.getFileName());
			item.setHashCode(jarFile.getHashCode());
			item.setModifiyTime(jarFile.getModifiyTime());
			list.add(item);
		}
		return list;
	}

	public boolean deleteFile(String fileNameNoPrefix, String hashCode) {
		return this.systemAction.deleteFile(new Request(CommonVars
				.getCurrUser()), fileNameNoPrefix, hashCode);
	}

}
