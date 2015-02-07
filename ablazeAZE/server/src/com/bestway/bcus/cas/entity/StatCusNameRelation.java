/*
 * Created on 2005-6-1
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.common.CommonUtils;

/**
 * 商品大类
 */
public class StatCusNameRelation extends StatCusName {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 商品序号
	 */
	private String seqNum; 
	/**
	 *  类别
	 */ 
	private String materielType; 
	

	/**
	 * 取得类别
	 * @return materielType 类别
	 */
	public String getMaterielType() {
		return materielType;
	}

	/**
	 * 取得商品序号
	 * @return seqNum  商品序号.
	 */
	public String getSeqNum() {
		return seqNum;
	}

	/**
	 * 设置类别
	 * @param materielType 类别
	 */
	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}

	/**
	 * 设置商品序号
	 * @param seqNum 商品序号
	 */
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
   
}
