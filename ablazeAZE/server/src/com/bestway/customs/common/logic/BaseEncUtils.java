package com.bestway.customs.common.logic;

import java.util.Hashtable;
import java.util.Map;

import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.customs.common.dao.BaseEncDao;

public class BaseEncUtils {
	/**
	 * 产生报关单流水号的同步锁对象
	 */
	public static Object localForCustomsDeclarationSerialNumber = new Object();

	/**
	 * Map<业务类型,Map<公司编码+手册号,最大流水号>>
	 */
	private static Map<Integer, Map<String, Integer>> htImport = new Hashtable<Integer, Map<String, Integer>>();
	/**
	 * Map<业务类型,Map<公司编码+手册号,最大流水号>>
	 */
	private static Map<Integer, Map<String, Integer>> htExport = new Hashtable<Integer, Map<String, Integer>>();
	/**
	 * Map<业务类型,Map<公司编码,最大流水号>>
	 */
	private static Map<Integer, Map<String, Integer>> htSpecial = new Hashtable<Integer, Map<String, Integer>>();

	/**
	 * 获取最大的流水号
	 * 
	 * @param projectType
	 * @param impExpFlag
	 * @param emsHeadH2k
	 * @param baseEncDao
	 * @return
	 */
	public static int getMaxCustomsDeclarationSerialNumber(int projectType,
			int impExpFlag, String emsHeadH2k, BaseEncDao baseEncDao) {
		synchronized (localForCustomsDeclarationSerialNumber) {
			baseEncDao.setProjectType(projectType);//HH 2013.11.15 由于baseEncDao 中projectType （没有设置类型，外部未set改变，所以数据不正确）默认是 BCUS 。
			if (impExpFlag == ImpExpFlag.IMPORT) {
				String key = ((Company) CommonUtils.getCompany()).getId() + "/"
						+ emsHeadH2k.trim();
				Map<String, Integer> map = htImport.get(projectType);
				if (map == null) {
					map = new Hashtable<String, Integer>();
					int maxSerialNumber = findMaxSerialNumberFromDB(impExpFlag,
							emsHeadH2k, baseEncDao) + 1;
					map.put(key, maxSerialNumber);
					htImport.put(projectType, map);
					return maxSerialNumber;
				} else {
					Integer tempSerialNumber = map.get(key);
					if (tempSerialNumber == null) {
						int maxSerialNumber = findMaxSerialNumberFromDB(
								impExpFlag, emsHeadH2k, baseEncDao) + 1;
						map.put(key, maxSerialNumber);
						return maxSerialNumber;
					} else {
						int maxNewSerialNumber = findMaxSerialNumberFromDB(
								impExpFlag, emsHeadH2k, baseEncDao);
						if (maxNewSerialNumber > tempSerialNumber) {
							tempSerialNumber = maxNewSerialNumber;
						}
						int maxSerialNumber = tempSerialNumber + 1;
						map.put(key, maxSerialNumber);
						return maxSerialNumber;
					}
				}
			} else if (impExpFlag == ImpExpFlag.EXPORT) {
				String key = ((Company) CommonUtils.getCompany()).getId() + "/"
						+ emsHeadH2k.trim();
				Map<String, Integer> map = htExport.get(projectType);
				if (map == null) {
					map = new Hashtable<String, Integer>();
					int maxSerialNumber = findMaxSerialNumberFromDB(impExpFlag,
							emsHeadH2k, baseEncDao) + 1;
					map.put(key, maxSerialNumber);
					htExport.put(projectType, map);
					return maxSerialNumber;
				} else {
					Integer tempSerialNumber = map.get(key);
					if (tempSerialNumber == null) {
						int maxSerialNumber = findMaxSerialNumberFromDB(
								impExpFlag, emsHeadH2k, baseEncDao) + 1;
						map.put(key, maxSerialNumber);
						return maxSerialNumber;
					} else {
						int maxNewSerialNumber = findMaxSerialNumberFromDB(
								impExpFlag, emsHeadH2k, baseEncDao);
						if (maxNewSerialNumber > tempSerialNumber) {
							tempSerialNumber = maxNewSerialNumber;
						}
						int maxSerialNumber = tempSerialNumber + 1;
						map.put(key, maxSerialNumber);
						return maxSerialNumber;
					}
				}
			} else if (impExpFlag == ImpExpFlag.SPECIAL) {
				String key = ((Company) CommonUtils.getCompany()).getId();
				Map<String, Integer> map = htSpecial.get(projectType);
				if (map == null) {
					map = new Hashtable<String, Integer>();
					int maxSerialNumber = findMaxSerialNumberFromDB(impExpFlag,
							emsHeadH2k, baseEncDao) + 1;
					map.put(key, maxSerialNumber);
					htSpecial.put(projectType, map);
					return maxSerialNumber;
				} else {
					Integer tempSerialNumber = map.get(key);
					if (tempSerialNumber == null) {
						int maxSerialNumber = findMaxSerialNumberFromDB(
								impExpFlag, emsHeadH2k, baseEncDao) + 1;
						map.put(key, maxSerialNumber);
						return maxSerialNumber;
					} else {
						int maxNewSerialNumber = findMaxSerialNumberFromDB(
								impExpFlag, emsHeadH2k, baseEncDao);
						if (maxNewSerialNumber > tempSerialNumber) {
							tempSerialNumber = maxNewSerialNumber;
						}
						int maxSerialNumber = tempSerialNumber + 1;
						map.put(key, maxSerialNumber);
						return maxSerialNumber;
					}
				}
			} else {
				throw new RuntimeException("未识别的进出口类型:" + impExpFlag);
			}
		}
	}

	/**
	 * 重置最大的流水号
	 * 
	 * @param projectType
	 * @param impExpFlag
	 * @param emsHeadH2k
	 * @param baseEncDao
	 * @return
	 */
	public static void resetMaxCustomsDeclarationSerialNumber(int projectType,
			int impExpFlag, String emsHeadH2k, BaseEncDao baseEncDao) {
		synchronized (localForCustomsDeclarationSerialNumber) {
			if (impExpFlag == ImpExpFlag.IMPORT) {
				String key = ((Company) CommonUtils.getCompany()).getId() + "/"
						+ emsHeadH2k.trim();
				Map<String, Integer> map = htImport.get(projectType);
				if (map == null) {
					map = new Hashtable<String, Integer>();
					htImport.put(projectType, map);
				}
				int maxSerialNumber = findMaxSerialNumberFromDB(impExpFlag,
						emsHeadH2k, baseEncDao);
				map.put(key, maxSerialNumber);
			} else if (impExpFlag == ImpExpFlag.EXPORT) {
				String key = ((Company) CommonUtils.getCompany()).getId() + "/"
						+ emsHeadH2k.trim();
				Map<String, Integer> map = htExport.get(projectType);
				if (map == null) {
					map = new Hashtable<String, Integer>();
					htExport.put(projectType, map);
				}
				int maxSerialNumber = findMaxSerialNumberFromDB(impExpFlag,
						emsHeadH2k, baseEncDao);
				map.put(key, maxSerialNumber);
			} else if (impExpFlag == ImpExpFlag.SPECIAL) {
				String key = ((Company) CommonUtils.getCompany()).getId();
				Map<String, Integer> map = htSpecial.get(projectType);
				if (map == null) {
					map = new Hashtable<String, Integer>();
					htSpecial.put(projectType, map);
				}
				int maxSerialNumber = findMaxSerialNumberFromDB(impExpFlag,
						emsHeadH2k, baseEncDao);
				map.put(key, maxSerialNumber);
			} else {
				throw new RuntimeException("未识别的进出口类型:" + impExpFlag);
			}
		}
	}

	/**
	 * 第一次从数据库抓取最大值出来
	 * 
	 * @param impExpFlag
	 * @param emsHeadH2k
	 * @param baseEncDao
	 * @return
	 */
	private static int findMaxSerialNumberFromDB(int impExpFlag,
			String emsHeadH2k, BaseEncDao baseEncDao) {
		return baseEncDao.findMaxSerialNumberFromDB(impExpFlag, emsHeadH2k);
	}
}
