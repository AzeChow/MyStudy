package com.bestway.common.materialbase.entity;

import java.io.Serializable;

/**
 * 判断Bom的结构类型
 * 
 * @author aministrator
 *
 */
public class BomStructureType implements Serializable{
	
	/**
	 * 有版本号但是没日期
	 */
	public static int HAVE_VERSION_NO_DATE=0;

	/**
	 * 没版本号但是有日期
	 */
	public static int NO_VERSION_HAS_DATE=1;
	
	/**
	 * 没版本号也没日期
	 */
	public static int NO_VERSION_NO_DATE=2;
}
