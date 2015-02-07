/*
 * Created on 2004-7-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.system.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.system.dao.RenderDataColumnDao;
import com.bestway.bcus.system.entity.RenderDataColumn;
import com.bestway.common.CommonUtils;

/**
 * 
 * @author ls
 * 
 */
public class RenderDataColumnLogic {
	public RenderDataColumnDao									renderDataColumnDao	= null;
	//
	// 第一个key companyId + className,第二个 key 是 hashKey
	//
	private Map<String, Map<String, List<RenderDataColumn>>>	map					= new HashMap<String, Map<String, List<RenderDataColumn>>>();

	public RenderDataColumnDao getRenderDataColumnDao() {
		return renderDataColumnDao;
	}

	public void setRenderDataColumnDao(RenderDataColumnDao renderDataCloumnDao) {
		this.renderDataColumnDao = renderDataCloumnDao;
	}

	public void init() {
		List<RenderDataColumn> list = this.renderDataColumnDao
				.findRenderDataColumn();
		putCacheMap(list);
	}

	/**
	 * 存入缓存中
	 * 
	 * @param list
	 */
	private void putCacheMap(List<RenderDataColumn> list) {
		for (RenderDataColumn rdc : list) {
			//
			// 以类名为key的第1类hashMap
			//
			String companyId = rdc.getCompany() == null ? "" : rdc.getCompany()
					.getId();
			String className = rdc.getClassName();
			Map<String, List<RenderDataColumn>> hashKeyMap = map.get(companyId
					+ className);
			if (hashKeyMap == null) {
				hashKeyMap = new HashMap<String, List<RenderDataColumn>>();
				map.put(companyId + className, hashKeyMap);
			}
			//
			// 以关键字为key 和公司id 的第2类hashMap
			//
			String hashKey = rdc.getKey();
			List<RenderDataColumn> tempList = hashKeyMap.get(hashKey);
			if (tempList == null) {
				tempList = new ArrayList<RenderDataColumn>();
				tempList.add(rdc);
				hashKeyMap.put(hashKey,tempList);
			} else {
				tempList.add(rdc);
			}
		}
	}

	/**
	 * 保存列信息并存入 自定义 cache map 中
	 * 
	 * @param dataSource
	 * @return
	 */
	public List<RenderDataColumn> saveRenderDataColumn(
			List<RenderDataColumn> dataSource) {
		if (dataSource.size() <= 0) {
			return dataSource;
		}
		RenderDataColumn rdc = dataSource.get(0);
		String companyId = rdc.getCompany() == null ? "" : rdc.getCompany()
				.getId();
		String className = rdc.getClassName();
		//
		// 如果 companyId + className key 不是空 而且 hashKey 为空
		// 说明该表或列名 有重构. 先删除 再保存
		//
		Map<String, List<RenderDataColumn>> hashKeyMap = map.get(companyId
				+ className);
		if (hashKeyMap == null) {
			this.renderDataColumnDao.saveOrUpdate(dataSource);
			putCacheMap(dataSource);
			return dataSource;
		} else {
			String hashKey = rdc.getKey();
			List<RenderDataColumn> tempList = hashKeyMap.get(hashKey);
			if (tempList == null) {
				//
				// 删除类名和该公司的所有记录
				//
				this.renderDataColumnDao
						.deleteByClassName(className, companyId);
				//
				// 删除 cache
				//
				map.remove(companyId + className);
				this.renderDataColumnDao.saveOrUpdate(dataSource);
				putCacheMap(dataSource);
				return dataSource;
			} else {
				this.renderDataColumnDao.saveOrUpdate(dataSource);
				//
				// 先删除旧的 cache
				//
				hashKeyMap.remove(hashKey);
				putCacheMap(dataSource);
				return dataSource;
			}
		}
	}

	/**
	 * 获得对应的值
	 * 
	 * @param className
	 * @param companyId
	 * @param key
	 * @return
	 */
	public List<RenderDataColumn> findRenderDataColumn(String className,
			String companyId, String key) {
		Map<String, List<RenderDataColumn>> hashKeyMap = map.get(companyId
				+ className);
		if (hashKeyMap == null) {
			return null;
		} else {		
			return hashKeyMap.get(String.valueOf(CommonUtils.hash(key)));
		}
	}

}
