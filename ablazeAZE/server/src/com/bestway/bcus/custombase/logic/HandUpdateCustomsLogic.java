/*
 * Created on 2004-6-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.logic;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultAttribute;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Mappings;
import org.hibernate.mapping.PersistentClass;

import com.bestway.bcus.custombase.dao.BaseCodeDao;
import com.bestway.bcus.custombase.dao.CustomBaseVersionDao;
import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.bcus.custombase.entity.CustomBaseVersion;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.common.Big5GBConverter;
import com.bestway.common.CommonUtils;
import com.bestway.common.CspZIPUtil;
import com.bestway.common.Request;

/**
 * @author 001 // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class HandUpdateCustomsLogic {
	/**
	 * Commons Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(HandUpdateCustomsLogic.class);

	private BaseCodeDao baseCodeDao = null;

	private CustomBaseVersionDao customBaseVersionDao = null;
	/**
	 * 要导入的海关基础资料的文件名，注意：此顺序要保持，不要随便排列。
	 * 如：商品编码引用单位资料，所以，单位资料要先进行导入，因此单位资料的文件名排在前列。
	 */
	private String[] fileNameArray = new String[] { "ClassList.zip",
			"FieldList.zip", "GbToBig.zip", "LevyKind.zip", "Balancemode.zip",
			"Levymode.zip", "Trade.zip", "Unit.zip", "CoType.zip", "Curr.zip",
			"LicenseNote.zip", "Deduc.zip", "CustomsComplex.zip",
			"InvClass.zip", "InvestMode.zip", "MachiningType.zip",
			"ApplicationMode.zip", "Brief.zip", "LicenseDocu.zip",
			"Transf.zip", "Transac.zip", "PayMode.zip", "Wrap.zip", "Uses.zip",
			"SrtJzx.zip", "ContaModel.zip", "ContaSize.zip", "SrtTj.zip",
			"PayType.zip", "PayerType.zip", "TaxCode.zip", "SaicCode.zip",
			"StsCode.zip", "RedDep.zip", "Country.zip", "Customs.zip",
			"PortInternal.zip", "PortLin.zip", "PreDock.zip", "District.zip",
			"BillType.zip", "DriverList.zip", "BargainType.zip" };

	private List<String> fileNameList = new ArrayList<String>();

	private Map<String, Class> entityMap = new HashMap<String, Class>();

	private Map<String, Map<String, Object>> customsBaseDataMap = new HashMap<String, Map<String, Object>>();

	private List<String> canDeleteList = new ArrayList<String>();

	public BaseCodeDao getBaseCodeDao() {
		return baseCodeDao;
	}

	public void setBaseCodeDao(BaseCodeDao baseCodeDao) {
		this.baseCodeDao = baseCodeDao;
	}

	public CustomBaseVersionDao getCustomBaseVersionDao() {
		return customBaseVersionDao;
	}

	public void setCustomBaseVersionDao(
			CustomBaseVersionDao customBaseVersionDao) {
		this.customBaseVersionDao = customBaseVersionDao;
	}

	/**
	 * 初始化文件名列表，为了使用方便，把文件名数组转换成List
	 */
	private void initFileNameList() {
		fileNameList.clear();
		for (int i = 0; i < fileNameArray.length; i++) {
			fileNameList.add(fileNameArray[i]);
		}
	}

	/**
	 * 初始化可直接删除的海关基础资料类名，如果某些资料从不会被其他资料引用的话，海关升级后不存在的话，就从旧资料中删除
	 */
	private void initCanDeleteList() {
		canDeleteList.clear();
		canDeleteList.add("CustomsComplex");
		canDeleteList.add("ContaModel");
		canDeleteList.add("ContaSize");
		canDeleteList.add("DriverList");
		canDeleteList.add("Gbtobig");
		canDeleteList.add("InvClass");
		canDeleteList.add("LicenseDocu");
		canDeleteList.add("LicenseNote");
		canDeleteList.add("PayerType");
		canDeleteList.add("PayType");
		canDeleteList.add("PortInternal");
		canDeleteList.add("SaicCode");
		canDeleteList.add("StsCode");
		canDeleteList.add("TaxCode");
	}

	private String getCustomsBaseFolder() {
		String customsBaseFolder = "CustomsBaseData";
		File file = new File(customsBaseFolder);
		if (!file.exists()) {
			file.mkdirs();
		}
		return customsBaseFolder;
	}

	public void upgradeCustomsBase() {
		logger.info("init custom base!");
		this.initFileNameList();
		this.initCanDeleteList();
		this.initMappingClass();
		List<String> listFileName = new ArrayList<String>();
		try {
			// 将从定义的服务器端目录中把所有从客户端上传的文件名读取出来。
			File customsBaseFileFolder = new File(getCustomsBaseFolder());
			File[] files = customsBaseFileFolder
					.listFiles(new CustomsBaseFileFilter("zip"));
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					File lnsFile = files[i];
					listFileName.add(lnsFile.getName());
				}
			}
			// 将上面读取的文件名按照预定的顺序（文件名数组中定义的）排序。
			Collections.sort(listFileName, new Comparator<String>() {

				@Override
				public int compare(String str1, String str2) {

					int index1 = fileNameList.indexOf(str1);

					int index2 = fileNameList.indexOf(str2);

					return index1 - index2;
				}

			});

			// 按照排序后的文件名，分别对每个文件进行导入。
			for (int i = 0; i < listFileName.size(); i++) {
				String fileName = listFileName.get(i);
				updateTable(getCustomsBaseFolder() + File.separator + fileName);
				logger.info("[JBCUS] check url   end----------------------"
						+ fileName);
				File file = new File(getCustomsBaseFolder() + File.separator
						+ fileName);
				file.delete();
			}
			logger.info("[JBCUS] upgradeCustomsBase 升级结束  end----------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 处理本地更新 商品编码
	 * 
	 * @param request
	 * @param fileName
	 * @return 处理结果数组 包含信息 是新增条数 修改条数 处理时间
	 */
	public String[] upgradeCustomsBase(Request request, String fileName) {

		logger.info("[基础资料更新模块] ---- 更新  " + fileName
				+ " 开始  -----------------");

		// 开始时间
		long st = System.currentTimeMillis();

		if (customsBaseDataMap == null) {

			customsBaseDataMap = new HashMap<String, Map<String, Object>>();

		} else {

			customsBaseDataMap.clear();

		}

		logger.info("[基础资料更新模块] 初始化导入文件名列表！");

		initFileNameList();

		logger.info("[基础资料更新模块] 初始化可删除资料名称！");

		initCanDeleteList();

		initMappingClass();

		try {
			// 更新表数据
			int[] counts = updateTable(getCustomsBaseFolder() + File.separator
					+ fileName);

			// 结束时间
			long et = System.currentTimeMillis();

			double time = (et - st) / 1000.0;

			logger.info("[基础资料更新模块] 更新 " + fileName + ",使用时间： " + time + " s");

			logger.info("[基础资料更新模块] ---- 更新 " + fileName + " 结束 ----");

			File file = new File(getCustomsBaseFolder() + File.separator
					+ fileName);

			// 删除导入文件
			boolean isDelFile = file.delete();

			String info = "新增 " + counts[0] + " 条数据" + ",修改" + counts[1]
					+ " 条数据" + ",删除 " + counts[2] + " 条数据";

			logger.info(info);

			logger.info("[基础资料更新模块] 删除文件：" + fileName + " "
					+ (!file.exists() ? "，成功" : "，失败"));

			if (fileName != null
					&& fileName.toLowerCase().contains(
							Gbtobig.class.getSimpleName().toLowerCase())) {

				initGbToBigs(request);

			}

			return new String[] { "更新 " + fileName, info, "成功", time + "秒" };

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;
	}

	/**
	 * 根新表数据
	 *
	 * @param 服务器需要更新的
	 *            压缩文件名称 (本地上传或者是fpt下载的)
	 */
	private int[] updateTable(String fileName) {

		// 增加的条数
		int addCount = 0;

		// 更新的条数
		int updateCount = 0;

		// 删除的条数
		int deleteCount = 0;

		try {

			long beginTime = System.currentTimeMillis();

			long endTime = System.currentTimeMillis();

			// 读取需要解析的压缩文件 获取 到根节点
			Element root = DocumentHelper.parseText(
					CspZIPUtil.readZIPFile(fileName)).getRootElement();

			endTime = System.currentTimeMillis();

			long findTime = endTime - beginTime;

			// 第一次消耗的时间
			System.out.println("第一次时间   >>> " + findTime);

			// 为了兼容旧资料，所以仍用tableName作为判断导入版本的关键字
			String tableName = root.attributeValue("tableName").toLowerCase();

			// 读取 xml 中 className 属性 获取 类名
			String className = root.attributeValue("className");

			// 读取 xml 中 createDate 属性 获取 创建时间 作为 版本号
			String versionNo = root.attributeValue("createDate");

			// 读取 xml 中 keyField 属性 获取 键字段
			String keyField = root.attributeValue("keyField");

			System.out.println(" className: <" + className + "> versionNo:<"
					+ versionNo + "> keyField:<" + keyField + ">");

			if ("".equals(className)) {
				return null;
			}

			// 根据类名，将此基础资料的从数据库中全部读取出来，做缓存。
			Map<String, Object> mapTempOld = getCustomsBaseData(className,
					keyField);

			// 根据类名 获取对应 的 实体类
			Class entityClass = entityMap.get(className);

			List listData = new ArrayList();

			// xml中的全部数据记录
			List listElement = root.elements("row");

			for (int i = 0; i < listElement.size(); i++) {

				beginTime = System.currentTimeMillis();

				// 当前数据记录
				Element elementRow = (Element) listElement.get(i);

				// 主键字段
				String keyValue = elementRow.attributeValue(keyField);

				// 获取 keyValue 对应数据库中的记录
				Object entityObj = mapTempOld.get(keyValue);

				// 新增标记
				boolean isAdd = false;

				// 在mapTempOld中不存在，说明是海关新增的
				if (entityObj == null) {

					try {

						entityObj = entityClass.newInstance();

						isAdd = true;

					} catch (Exception e) {

						e.printStackTrace();

					}

				} else {

					// 将mapTempOld中的已经存在的去掉，剩下的，是新资料中没有的。
					mapTempOld.remove(keyValue);

				}

				// 当前节点原型属性 集
				List elementProperties = elementRow.attributes();

				boolean isChange = false;

				/**
				 * 设置属性 迭代 每个属性值 对比
				 */
				for (int m = 0; m < elementProperties.size(); m++) {

					DefaultAttribute elementProperty = (DefaultAttribute) elementProperties
							.get(m);

					// 获取 当前属性名<当前节点>
					String propertyName = elementProperty.getName();

					// 获取 当前属性值<当前节点>
					String value = elementProperty.getStringValue().trim();

					try {

						Class propertyType = PropertyUtils.getPropertyType(
								entityObj, propertyName);

						// if (propertyType == null) {
						// throw new RuntimeException("对象" + tableName + "没有" +
						// propertyName + "属性！");
						// }

						String casType = propertyType.getName() == null ? "null"
								: propertyType.getName();

						Object oldValue = PropertyUtils.getNestedProperty(
								entityObj, propertyName);

						if (casType.equals("java.lang.String")) {

							PropertyUtils.setNestedProperty(entityObj,
									propertyName, value);

							String oldValueStr = oldValue == null ? ""
									: oldValue.toString();

							if (!isAdd && !isChange) {

								// 相同 则没被改变 false 反之 true
								isChange = !oldValueStr.equals(value);

							}

						} else if (casType.equals("java.lang.Integer")) {

							PropertyUtils
									.setNestedProperty(
											entityObj,
											propertyName,
											(value == null || "".equals(value
													.trim())) ? null : Integer
													.valueOf(value.toString()));

							String oldValueStr = oldValue == null ? ""
									: oldValue.toString();

							if (!isAdd && !isChange) {
								isChange = !oldValueStr.equals(value);

							}

						} else if (casType.equals("java.lang.Double")) {

							Double doubleValue = (value == null || ""
									.equals(value.trim())) ? null : Double
									.valueOf(value.toString());

							PropertyUtils.setNestedProperty(entityObj,
									propertyName, doubleValue);

							if (!isAdd && !isChange && doubleValue != null) {

								isChange = !doubleValue.equals(oldValue);

							}

						} else if (CustomBaseEntity.class
								.isAssignableFrom(propertyType)) {

							if (value != null && !"".equals(value.trim())) {

								Map<String, Object> customsBaseMap = customsBaseDataMap
										.get(propertyType.getName());

								if (customsBaseMap == null) {

									customsBaseMap = this.getCustomsBaseData(
											propertyType.getName(), "code");

									customsBaseDataMap.put(
											propertyType.getName(),
											customsBaseMap);
								}

								PropertyUtils
										.setNestedProperty(entityObj,
												propertyName,
												customsBaseMap.get(value));

								String oldValueStr = oldValue == null ? ""
										: ((CustomBaseEntity) oldValue)
												.getCode();

								if (!isAdd && !isChange) {
									isChange = !oldValueStr.equals(value);

								}
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				listData.add(entityObj);

				if (isAdd) {

					addCount++;

				} else if (isChange) {

					updateCount++;

				}

			}

			endTime = System.currentTimeMillis();

			findTime = endTime - beginTime;

			System.out.println("第二次时间 >>>>>" + findTime);

			beginTime = System.currentTimeMillis();

			customBaseVersionDao.batchSaveOrUpdate(listData);

			endTime = System.currentTimeMillis();

			findTime = endTime - beginTime;

			System.out.println("第三次时间 : >>>  " + findTime);

			listData.clear();

			beginTime = System.currentTimeMillis();

			// 剩下的，是新资料中没有的,所以做删除处理。
			if (!mapTempOld.isEmpty()) {

				deleteCount = mapTempOld.size();

				List listTempOld = new ArrayList();

				listTempOld.addAll(mapTempOld.values());

				if (canDeleteList.contains(className)) {

					for (int i = 0; i < listTempOld.size(); i++) {

						Object entityObj = listTempOld.get(i);

						customBaseVersionDao.delete(entityObj);

					}

				} else {

					Object tempOld = null;

					for (int i = 0; i < listTempOld.size(); i++) {

						tempOld = listTempOld.get(i);

						if (tempOld instanceof CustomBaseEntity) {

							CustomBaseEntity entityObj = (CustomBaseEntity) tempOld;

							// 如果名称中已包含已删除标记，则不进行删除标记。
							if (entityObj.getName() != null
									&& entityObj.getName().indexOf("已删除") >= 0) {

								continue;

							}
							entityObj.setName(entityObj.getName() + "(已删除)");

							// 已删除
							((CustomBaseEntity) entityObj).setIsOut(Boolean
									.valueOf(true).toString());

							// this.customsBaseDao.saveOrUpdate(entityObj);

							listData.add(entityObj);

						} else {

							customBaseVersionDao.delete(tempOld);
						}
					}

					customBaseVersionDao.batchSaveOrUpdate(listData);

					listData.clear();

				}
			}

			endTime = System.currentTimeMillis();

			findTime = endTime - beginTime;

			System.out.println("第四次时间 : >>>  " + findTime);

			/**
			 * 这里 的 CustomBaseVersion 这个实体是硬编码 可能与其他的数据库实体不一致 CustomsBaseVersion
			 */
			// 将本次导入的基础资料的版本号记录下来
			CustomBaseVersion customBaseVersion = (CustomBaseVersion) customBaseVersionDao
					.findCustomBaseEntity("CustomBaseVersion", "tableName",
							tableName);

			if (customBaseVersion == null) {

				customBaseVersion = new CustomBaseVersion();

				customBaseVersion.setTableName(tableName);

				customBaseVersion.setVersion(versionNo);

			} else {

				customBaseVersion.setVersion(versionNo);

			}

			customBaseVersionDao.saveOrUpdate(customBaseVersion);

		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return new int[] { addCount, updateCount, deleteCount };

	}

	/**
	 * 初始化繁转简
	 */
	private void initGbToBigs(Request request) {

		List list = customBaseVersionDao.findCustomBaseEntity(
				Gbtobig.class.getSimpleName(), null, null, true);

		Big5GBConverter.getInstance().initGbToBigs(list);

	}

	class CustomsBaseFileFilter implements FileFilter {
		private List list = new ArrayList();

		CustomsBaseFileFilter(String suffix) {
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
	 * 初始化系统实体类的缓存（用map,Key是不包含包名的类名,value是实体的 Class)， 为了新增海关基础编码时生成对象时使用。
	 */
	private void initMappingClass() {
		try {
			Configuration configuration = CommonUtils.getConfiguration();

			Mappings mapping = configuration.createMappings();

			// configuration.get

			Iterator<PersistentClass> iterator = configuration
					.getClassMappings();

			while (iterator.hasNext()) {
				//
				// persistentClass 对象
				//
				PersistentClass persistentClass = iterator.next();
				String entityName = persistentClass.getEntityName();
				Class entityClass = persistentClass.getMappedClass();
				int index = entityName.lastIndexOf(".");
				if (index > 0) {
					entityName = entityName.substring(index + 1);
				}
				entityMap.put(entityName, entityClass);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	// /**
	// * 修改数据
	// *
	// * @param url
	// */
	// private void updateTable(String fileName) {
	// try {
	// Element root = DocumentHelper.parseText(
	// CspZIPUtil.readZIPFile(fileName)).getRootElement();
	// String tableName = root.attributeValue("tableName").toLowerCase();//
	// 为了兼容旧资料，所以仍用tableName作为判断导入版本的关键字
	// String className = root.attributeValue("className");
	// String versionNo = root.attributeValue("createDate");
	// String keyField = root.attributeValue("keyField");
	// System.out.println("tableName" + tableName + " className"
	// + className + " versionNo" + versionNo + " keyField"
	// + keyField);
	// if ("".equals(className)) {
	// return;
	// }
	// // 删除到CustomsComplex表中的数据
	// if ("CustomsComplex".equals(className)) {
	// baseCodeDao.batchUpdateOrDelete("delete CustomsComplex");
	// }
	//
	// // 根据类名，将此基础资料的从数据库中全部读取出来，做缓存。
	// Map<String, Object> mapTempOld = this.getCustomsBaseData(className,
	// keyField);
	// Class entityClass = entityMap.get(className);
	// List listElement = root.elements("row");
	// for (int i = 0; i < listElement.size(); i++) {
	// Element elementRow = (Element) listElement.get(i);
	// String keyValue = elementRow.attributeValue(keyField);
	// Object entityObj = mapTempOld.get(keyValue);
	// boolean isAdd = false;
	// if (entityObj == null) {// 在mapTempOld中不存在，说明是海关新增的
	// try {
	// entityObj = entityClass.newInstance();
	// isAdd = true;
	// } catch (InstantiationException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// } else {
	// mapTempOld.remove(keyValue);// 将mapTempOld中的已经存在的去掉，剩下的，是新资料中没有的。
	// }
	// List elementProperties = elementRow.attributes();
	// //
	// System.out.println("elementProperties size:"+elementProperties.size());
	// boolean isChange = false;
	// for (int m = 0; m < elementProperties.size(); m++) {
	// DefaultAttribute elementProperty = (DefaultAttribute) elementProperties
	// .get(m);
	// String propertyName = elementProperty.getName();
	// String value = elementProperty.getStringValue();
	// try {
	// Class propertyType = PropertyUtils.getPropertyType(
	// entityObj, propertyName);
	// String casType = propertyType.getName() == null ? "null"
	// : propertyType.getName();
	// Object oldValue = PropertyUtils.getNestedProperty(
	// entityObj, propertyName);
	// if (casType.equals("java.lang.String")) {
	// PropertyUtils.setNestedProperty(entityObj,
	// propertyName, value);
	// String oldValueStr = oldValue == null ? ""
	// : oldValue.toString();
	// if (!isAdd && !isChange) {
	// isChange = !oldValueStr.equals(value);
	// }
	// } else if (casType.equals("java.lang.Integer")) {
	// PropertyUtils
	// .setNestedProperty(
	// entityObj,
	// propertyName,
	// (value == null || "".equals(value
	// .trim())) ? null : Integer
	// .valueOf(value.toString()));
	// String oldValueStr = oldValue == null ? ""
	// : oldValue.toString();
	// if (!isAdd && !isChange) {
	// isChange = !oldValueStr.equals(value);
	// }
	// } else if (casType.equals("java.lang.Double")) {
	// PropertyUtils.setNestedProperty(entityObj,
	// propertyName, (value == null || ""
	// .equals(value.trim())) ? null
	// : Double.valueOf(value.toString()));
	// String oldValueStr = oldValue == null ? ""
	// : oldValue.toString();
	// if (!isAdd && !isChange) {
	// isChange = !oldValueStr.equals(value);
	// }
	// } else if (CustomBaseEntity.class
	// .isAssignableFrom(propertyType)) {
	// if (value != null && !"".equals(value.trim())) {
	// Map<String, Object> customsBaseMap = customsBaseDataMap
	// .get(propertyType.getName());
	// if (customsBaseMap == null) {
	// customsBaseMap = this.getCustomsBaseData(
	// propertyType.getName(), "code");
	// customsBaseDataMap.put(
	// propertyType.getName(),
	// customsBaseMap);
	// }
	// PropertyUtils
	// .setNestedProperty(entityObj,
	// propertyName,
	// customsBaseMap.get(value));
	// String oldValueStr = oldValue == null ? ""
	// : ((CustomBaseEntity) oldValue)
	// .getCode();
	// if (!isAdd && !isChange) {
	// isChange = !oldValueStr.equals(value);
	// }
	// }
	// }
	// } catch (IllegalAccessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (InvocationTargetException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (NoSuchMethodException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }
	// if (isAdd) {
	// if (entityObj instanceof LicenseDocu) {
	// ((LicenseDocu) entityObj).setId(null);
	// }
	// this.baseCodeDao.saveOrUpdateDirect(entityObj);
	// } else if (isChange) {
	// this.baseCodeDao.saveOrUpdateDirect(entityObj);
	// }
	// System.out.println(entityObj + "=============================");
	// }
	// if (!mapTempOld.isEmpty()) {// 剩下的，是新资料中没有的,所以做删除处理。
	// List listTempOld = new ArrayList();
	// listTempOld.addAll(mapTempOld.values());
	// if (canDeleteList.contains(className)) {
	// for (int i = 0; i < listTempOld.size(); i++) {
	// Object entityObj = listTempOld.get(i);
	// this.baseCodeDao.delete(entityObj);
	// }
	// } else {
	// for (int i = 0; i < listTempOld.size(); i++) {
	// CustomBaseEntity entityObj = (CustomBaseEntity) listTempOld
	// .get(i);
	// if (entityObj.getName() != null
	// && entityObj.getName().indexOf("已删除") >= 0) {// 如果名称中已包含已删除标记，则不进行删除标记。
	// continue;
	// }
	// entityObj.setName(entityObj.getName() + "(已删除)");
	// ((CustomBaseEntity) entityObj).setIsOut("1");// 已删除
	// this.baseCodeDao.saveOrUpdateDirect(entityObj);
	// }
	// }
	// }
	// // 将本次导入的基础资料的版本号记录下来
	// CustomBaseVersion customBaseVersion = (CustomBaseVersion)
	// this.baseCodeDao
	// .findCustomBaseEntity("CustomBaseVersion", "tableName",
	// tableName);
	// if (customBaseVersion == null) {
	// customBaseVersion = new CustomBaseVersion();
	// customBaseVersion.setTableName(tableName);
	// customBaseVersion.setVersion(versionNo);
	// this.baseCodeDao.saveOrUpdateDirect(customBaseVersion);
	// } else {
	// customBaseVersion.setVersion(versionNo);
	// this.baseCodeDao.saveOrUpdateDirect(customBaseVersion);
	// }
	// } catch (DocumentException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	/**
	 * 根据类名和关键字，查询资料并缓存。
	 * 
	 * @param className
	 * @param keyField
	 * @return
	 */
	private Map<String, Object> getCustomsBaseData(String className,
			String keyField) {

		Map<String, Object> map = new HashMap<String, Object>();

		List list = baseCodeDao.find("select a from " + className + " a");

		for (int i = 0; i < list.size(); i++) {

			Object obj = list.get(i);

			try {

				Object value = PropertyUtils.getNestedProperty(obj, keyField);

				if (value != null) {

					map.put(value.toString().trim(), obj);

				}
			} catch (IllegalAccessException e) {

				e.printStackTrace();

			} catch (InvocationTargetException e) {

				e.printStackTrace();

			} catch (NoSuchMethodException e) {

				e.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * 将客户端传过来的文件名进行过滤和排序，返回需要导入的文件名。
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getMustUpgradeFileName(Map<String, String[]> map) {

		List<CustomBaseVersion> versionList = this.customBaseVersionDao
				.findCustomBaseVersion();

		List fileNames = new ArrayList();

		Map<String, String> mapVersion = new HashMap<String, String>();

		for (CustomBaseVersion version : versionList) {

			mapVersion.put(version.getTableName().toLowerCase(),
					version.getVersion());

		}

		Iterator iterator = map.keySet().iterator();

		while (iterator.hasNext()) {

			String key = iterator.next().toString();

			String oldVersion = mapVersion.get(key);

			String newVersion = map.get(key)[1];

			String fileName = map.get(key)[0];

			if (oldVersion == null || oldVersion.compareTo(newVersion) < 0) {

				fileNames.add(fileName);

			}

			System.out.println("key:" + key + "  oldVersion:" + oldVersion
					+ "  newVersion:" + newVersion);
		}

		this.initFileNameList();

		for (int i = fileNames.size() - 1; i >= 0; i--) {

			String fileName = (String) fileNames.get(i);

			if (!fileNameList.contains(fileName)) {

				fileNames.remove(i);

			}
		}

		Collections.sort(fileNames, new Comparator<String>() {

			@Override
			public int compare(String str1, String str2) {

				// return 0;
				int index1 = fileNameList.indexOf(str1);

				int index2 = fileNameList.indexOf(str2);

				return index1 - index2;
			}

		});

		return fileNames;
	}

	/**
	 * 将客户端传过来的文件名进行过滤和排序，返回需要导入的文件名。此方法只是暂时用于Junit 单元测试 模式数据，后边可以删除
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getMustUpgradeFileNameSimulate(Map<String, String[]> map) {
		/*
		 * List<CustomBaseVersion> versionList = this.customBaseVersionDao
		 * .findCustomBaseVersion();
		 */
		List<CustomBaseVersion> versionList = new ArrayList<CustomBaseVersion>();
		CustomBaseVersion cv = new CustomBaseVersion();
		cv.setTableName("Unit");
		versionList.add(cv);
		List fileNames = new ArrayList();
		Map<String, String> mapVersion = new HashMap<String, String>();
		for (CustomBaseVersion version : versionList) {
			mapVersion.put(version.getTableName(), version.getVersion());
		}
		Iterator iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			String oldVersion = mapVersion.get(key);
			String newVersion = map.get(key)[1];
			String fileName = map.get(key)[0];
			if (oldVersion == null || oldVersion.compareTo(newVersion) < 0) {
				fileNames.add(fileName);
			}
		}
		this.initFileNameList();
		for (int i = fileNames.size() - 1; i >= 0; i--) {
			String fileName = (String) fileNames.get(i);
			if (!fileNameList.contains(fileName)) {
				fileNames.remove(i);
			}
		}
		Collections.sort(fileNames, new Comparator<String>() {

			@Override
			public int compare(String str1, String str2) {

				int index1 = fileNameList.indexOf(str1);

				int index2 = fileNameList.indexOf(str2);

				return index1 - index2;

			}

		});
		return fileNames;
	}

	/**
	 * 从客户端上传海关基础编码文件到服务器端
	 * 
	 * @param fileContent
	 */
	public void uploadCustomsBaseFileToServer(byte[] fileContent,
			String fileName) {
		// ByteArrayInputStream in = null;
		// try {
		// in = new ByteArrayInputStream(fileContent);
		// } catch (IOException e1) {
		// throw new RuntimeException(e1.getMessage());
		// }
		FileOutputStream out = null;

		try {

			out = new FileOutputStream(new File(getCustomsBaseFolder()
					+ File.separator + fileName));

			out.write(fileContent);

			out.flush();

		} catch (Exception e) {

			throw new RuntimeException(e.getMessage());

		} finally {

			try {

				out.close();

			} catch (IOException e2) {

				throw new RuntimeException(e2.getMessage());

			}
		}
	}
}