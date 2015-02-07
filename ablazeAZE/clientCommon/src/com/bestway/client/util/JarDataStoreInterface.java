package com.bestway.client.util;

import java.util.List;

public interface JarDataStoreInterface {
	/**
	 * 保存数据到文件
	 * 
	 * @param fileNameNoPrefixSuffix
	 *            没有前缀的文件名
	 * @param keyString
	 * @param list
	 */
	void saveJarFile(String fileNameNoPrefixSuffix, String keyString,boolean isKeyString,
			List<JarDataItem> list);

	/**
	 * 打开文件
	 * 
	 * @param fileName
	 * @param keyString
	 * @return
	 */
	List<JarDataItem> openJarFile(String fileNameNoPrefix, String hashCode);

	/**
	 * 获得当前表格存入所有的 JarFile
	 * 
	 * @param keyString
	 * @return
	 */
	List<JarItem> getJarFileName(String keyString);

	/**
	 * 获得当前表格存入所有的 JarFile
	 * 
	 * @param keyString
	 * @return
	 */
	List<JarItem> getJarFileName();
	
	
	boolean deleteFile(String fileNameNoPrefix, String hashCode);
	
}
