/*
 * Created on 2004-6-12
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.entity.parametercode;

import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放报关参数－－集装箱代码资料
 * 
 * @author Administrator
 */
public class SrtJzx extends CustomBaseEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 用途
	 */
	private String srtUsing;

	/**
	 * 重量
	 */
	private Double srtWeight;

	/**
	 * 获取用途
	 * 
	 * @return srtUsing 用途
	 */
	public String getSrtUsing() {
		return srtUsing;
	}

	/**
	 * 设置用途
	 * 
	 * @param srtUsing 用途
	 */
	public void setSrtUsing(String srtUsing) {
		this.srtUsing = srtUsing;
	}

	/**
	 * 获取重量
	 * 
	 * @return srtWeight 重量
	 */
	public Double getSrtWeight() {
		return srtWeight;
	}

	/**
	 * 设置重量
	 * 
	 * @param srtWeight 重量
	 */
	public void setSrtWeight(Double srtWeight) {
		this.srtWeight = srtWeight;
	}
}
