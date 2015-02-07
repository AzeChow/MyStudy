package com.bestway.bcs.verification.entity;

import java.io.Serializable;
/**
 * @author lyh
 *
 * 核查分析 表类型
 */
public class VFAnalyType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 料件报关明细数据
	 */
	public static final int VFCUSTOMS_IMG = 0;

	/**
	 *成品报关明细数据
	 */
	public static final int VFCUSTOMS_EXG = 1;
	/**
	 *料件报关明细统计数据
	 */
	public static final int VFCUSTOMS_COUNTIMG = 2;
	/**
	 *成品报关明细统计数据
	 */
	public static final int VFCUSTOMS_COUNTEXG = 3;
	/**
	 *成品报关数据统计折料表
	 */
	public static final int VFCUSTOMS_COUNTEXG_CONVERT = 4;
	
	/**
	 *合同分析表
	 */
	public static final int VFCONTRACT_ANALYSE = 5;

}
