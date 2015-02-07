/*
 * Created on 2004-6-14
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.materialbase.entity;

import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放物料通用代码－－币制代码
 * 
 * @author Administrator
 */
public class CurrCode extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 币别编号
	 */
	private String code; 

	/**
	 * 币别名称
	 */
	private String name; 

	/**
	 * 币别符号
	 */
	private String currSign; 

	/**
	 * 关务币制
	 */
	private Curr curr; 

	/**
	 * 获取币别编号
	 * 
	 * @return code 币别编号
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置币别编号
	 * 
	 * @param code 币别编号
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取关务币制
	 * 
	 * @return curr 关务币制
	 */
	public Curr getCurr() {
		return curr;
	}

	/**
	 * 设置关务币制
	 * 
	 * @param curr 关务币制
	 */
	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	/**
	 * 获取币别符号
	 * 
	 * @return currSign 币别符号
	 */
	public String getCurrSign() {
		return currSign;
	}

	/**
	 * 设置币别符号
	 * 
	 * @param currSign 币别符号
	 */
	public void setCurrSign(String currSign) {
		this.currSign = currSign;
	}

	/**
	 * 获取币别名称
	 * 
	 * @return name 币别名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置币别名称
	 * 
	 * @param name 币别名称
	 */
	public void setName(String name) {
		this.name = name;
	}
}
