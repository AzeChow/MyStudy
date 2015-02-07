package com.bestway.bcus.checkstock.entity;

import java.io.Serializable;
/**
 * @author lyh
 *
 * 盘点核查 表类型
 */
public class ECSAnalyType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *料件统计数据
	 */
	public static final int ECSCUSTOMS_COUNTIMG = 1;
	/**
	 *成品统计数据
	 */
	public static final int ECSCUSTOMS_COUNTEXG = 2;
	/**
	 *成品统计折料表
	 */
	public static final int ECSCUSTOMS_COUNTEXG_CONVERT = 3;
	/**
	 *账册分析表
	 */
	public static final int ECSCUSTOMS_ANALYSE = 4;
	

}
