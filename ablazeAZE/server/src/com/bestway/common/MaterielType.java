/*
 * Created on 2004-7-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common;

import java.io.Serializable;

/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 * 物料类型
 */
public class MaterielType implements Serializable{

	/**
	 * 成品
	 */
	public final static String FINISHED_PRODUCT="0";
	/**
	 * 半成品
	 */
	public final static String SEMI_FINISHED_PRODUCT="1";
	/**
	 * 材料--主料
	 */
	public final static String MATERIEL="2";
	/**
	 * 设备
	 */
	public final static String MACHINE="3";
	/**
	 * 边角料
	 */
	public final static String REMAIN_MATERIEL="4";
	/**
	 * 残次品
	 */
	public final static String BAD_PRODUCT="5";       
    /**
     * 辅料
     */
    public static final String ASSISTANT_MATERIAL = "6";
    /**
     * 消耗料
     */
    public static final String WASTE_MATERIAL = "7";
}
