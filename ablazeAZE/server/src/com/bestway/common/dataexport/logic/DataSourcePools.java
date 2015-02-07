package com.bestway.common.dataexport.logic;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import com.bestway.common.CommonUtils;
/**
 * @author luosheng 2006/9/1
 *         <ul>
 *         <li> 1.多个连接池的cache
 *         </ul>
 * 
 */
public class DataSourcePools {
	/** key = (driverClassName + url + userName + password) 的 hashcode */
	private static Map<Integer, DataSource>	pools;

	public static DataSource getDataSource(String driverClassName, String url,
			String userName, String password){
		if (pools == null) {
			pools = new HashMap<Integer, DataSource>();
		}
		int key = CommonUtils.hashCode(driverClassName + url + userName + password);
		
		BasicDataSource dataSource = (BasicDataSource)pools.get(key);
//		if(dataSource == null){
			dataSource = new BasicDataSource();
			dataSource.setDriverClassName(driverClassName);
			dataSource.setUrl(url);
			dataSource.setUsername(userName);
			dataSource.setPassword(password);
			dataSource.setMaxActive(8);
			dataSource.setMinIdle(8);
			dataSource.setInitialSize(2);	
//			pools.put(key, dataSource);
//		}
		return dataSource;
	}
}
