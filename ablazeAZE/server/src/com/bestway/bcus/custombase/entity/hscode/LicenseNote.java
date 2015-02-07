/*
 * Created on 2004-6-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.entity.hscode;

import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放领证商品备注表资料
 */
public class LicenseNote extends CustomBaseEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
    /**
	 * 分类说明
	 */
	private String classElucidation;
	
	/**
	 * 辅助编码
	 */
	private String assistanceCode; 
	
	/**
	 * 流水号
	 */
	private String pkSeq;
	
	/**
	 * 获取分类说明
	 * 
	 * @return classElucidation 分类说明
	 */
	public String getClassElucidation() {
		return classElucidation;
	}
	
	/**
	 * 设置分类说明
	 * 
	 * @param classElucidation 分类说明
	 */
	public void setClassElucidation(String classElucidation) {
		this.classElucidation = classElucidation;
	}
	

	/**
	 * 获取流水号
	 * 
	 * @return pkSeq 流水号
	 */
	public String getPkSeq() {
		return pkSeq;
	}
	
	/**
	 * 设置流水号
	 * 
	 * @param pkSeq T流水号
	 */
	public void setPkSeq(String pkSeq) {
		this.pkSeq = pkSeq;
	}

	/**
	 * 获取辅助编码
	 * 
	 * @return assistanceCode 辅助编码
	 */
	public String getAssistanceCode() {
		return assistanceCode;
	}
	
	/**
	 * 设置辅助编码
	 * 
	 * @param assistanceCode 辅助编码
	 */
	public void setAssistanceCode(String assistanceCode) {
		this.assistanceCode = assistanceCode;
	}
}