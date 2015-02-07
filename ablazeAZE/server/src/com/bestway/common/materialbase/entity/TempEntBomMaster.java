package com.bestway.common.materialbase.entity;

import java.io.Serializable;


/**
 * 临时实体类，临时的工厂BOM管理父件资料
 * 
 * @author administrator
 *
 */
public class TempEntBomMaster implements Serializable{
	
	/**
	 * 料别
	 */
	private String scmCoiName;
	
	/**
	 * 父料号码
	 */
	private String ptNo;
	
	/**
	 * 备案序号
	 */
	private Integer seqNum;
	
	/**
	 * 父料名称
	 */
	private String ptName;
	
	/**
	 * 父料规格
	 */
	private String ptSpec;
	
	/**
	 * 工厂单位
	 */
	private String calUnitName;
	
		
	/**
	 * 获取料别
	 * 
	 * @return scmCoiName 料别
	 */
	public String getScmCoiName() {
		return scmCoiName;
	}
		
	/**
	 * 获取料别
	 * 
	 * @param scmCoiName 料别
	 */
	public void setScmCoiName(String scmCoiName) {
		this.scmCoiName = scmCoiName;
	}
		
	/**
	 * 获取
	 * 
	 * @return ptName 
	 */
	public String getPtName() {
		return ptName;
	}
		
	/**
	 * 获取
	 * 
	 * @param ptName 
	 */
	public void setPtName(String ptName) {
		this.ptName = ptName;
	}
		
	/**
	 * 获取父料名称
	 * 
	 * @return ptNo 父料名称
	 */
	public String getPtNo() {
		return ptNo;
	}
		
	/**
	 * 获取父料名称
	 * 
	 * @param ptNo 父料名称
	 */
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}
		
	/**
	 * 获取父料规格
	 * 
	 * @return ptSpec 父料规格
	 */
	public String getPtSpec() {
		return ptSpec;
	}
		
	/**
	 * 获取父料规格
	 * 
	 * @param ptSpec 父料规格
	 */
	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}
		
	/**
	 * 获取工厂单位
	 * 
	 * @return calUnitName 工厂单位
	 */
	public String getCalUnitName() {
		return calUnitName;
	}
		
	/**
	 * 获取工厂单位
	 * 
	 * @param calUnitName 工厂单位
	 */
	public void setCalUnitName(String calUnitName) {
		this.calUnitName = calUnitName;
	}
	
	/**
	 * 返回父料号码
	 * 
	 * @return ptNo 父料号码
	 */
	@Override
	public String toString() {
		return ptNo;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
}
