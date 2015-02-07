package com.bestway.bcus.cas.invoice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CasConvert implements Serializable {

	/**
	 * 工厂开始料号
	 */
	private String startPtPart = "";
	
	/**
	 * 工厂结束料号
	 */
	private String endPtPart = "";
	
	/**
	 * 工厂规格
	 */
	private String ptSpec= "";
	
	/**
	 * 工厂名称
	 */
	private String ptName = "";
	
	/**
	 * 报关开始料号
	 */
	private String startHsPart = "";
	
	/**
	 * 报关结束料号
	 */
	private String endHsPart = "";
	
	/**
	 * 报关规格
	 */
	private String hsSpec= "";
	
	/**
	 * 报关名称
	 */
	private String hsName = "";
	
	/**
	 * 报关名称汇总
	 */
	private Boolean ishsTaotal = false;
	/**
	 * 显示负数结存
	 */
	private Boolean isShowLess = false;
	
	/**
	 * 是否按仓库分组
	 */
	private Boolean isGroup = false;
	
	/**
	 * 仓库
	 */
	private List<String> wareSetCodes = null;
	
	/**
	 * 查询日期
	 */
	private Date date = null;
	
	/**
	 * 开始料件工厂料号
	 */
	private String startPtNo = "";
		
	/**
	 * 结束料件工厂料号
	 */
	private String endPtNo = "";
		
	/**
	 * 开始料件报关编号
	 */
	private String startComplexCode = "";
	
	/**
	 * 成品报关编码
	 */
	private String complexCode = "";
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	private Date startDate ;
	
	private Date endDate ;
	
	
	//是否存在工厂级别查询
	private boolean isFserch= false;
	
	//是否存在工厂级别查询
	private boolean isCserch= false;
		
	public String getComplexCode() {
		return complexCode;
	}

	public void setComplexCode(String complexCode) {
		this.complexCode = complexCode;
	}

	/**
	 * 制单号
	 */
	private String productNo = "";
	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	/**
	 * 结束料件报关编号
	 */
	private String endComplexCode =  "";
	
	public String getStartPtNo() {
		return startPtNo;
	}

	public void setStartPtNo(String startPtNo) {
		this.startPtNo = startPtNo;
	}

	public String getEndPtNo() {
		return endPtNo;
	}

	public void setEndPtNo(String endPtNo) {
		this.endPtNo = endPtNo;
	}

	public String getStartComplexCode() {
		return startComplexCode;
	}

	public void setStartComplexCode(String startComplexCode) {
		this.startComplexCode = startComplexCode;
	}

	public String getEndComplexCode() {
		return endComplexCode;
	}

	public void setEndComplexCode(String endComplexCode) {
		this.endComplexCode = endComplexCode;
	}


	public String getStartPtPart() {
		return startPtPart;
	}

	public void setStartPtPart(String startPtPart) {
		this.startPtPart = startPtPart;
	}

	public String getEndPtPart() {
		return endPtPart;
	}

	public void setEndPtPart(String endPtPart) {
		this.endPtPart = endPtPart;
	}

	public String getPtSpec() {
		return ptSpec;
	}

	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

	public String getPtName() {
		return ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public String getStartHsPart() {
		return startHsPart;
	}

	public void setStartHsPart(String startHsPart) {
		this.startHsPart = startHsPart;
	}

	public String getEndHsPart() {
		return endHsPart;
	}

	public void setEndHsPart(String endHsPart) {
		this.endHsPart = endHsPart;
	}

	public String getHsSpec() {
		return hsSpec;
	}

	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}

	public String getHsName() {
		return hsName;
	}

	public void setHsName(String hsName) {
		this.hsName = hsName;
	}

	public Boolean getIshsTaotal() {
		return ishsTaotal;
	}

	public void setIshsTaotal(Boolean ishsTaotal) {
		this.ishsTaotal = ishsTaotal;
	}

	public Boolean getIsShowLess() {
		return isShowLess;
	}

	public void setIsShowLess(Boolean isShowLess) {
		this.isShowLess = isShowLess;
	}

	public Boolean getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(Boolean isGroup) {
		this.isGroup = isGroup;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<String> getWareSetCodes() {
		return wareSetCodes;
	}

	public void setWareSetCodes(List<String> wareSetCodes) {
		this.wareSetCodes = wareSetCodes;
	}
	public boolean isFserch() {
		if(!"".equals(startPtNo) || !"".equals(endPtNo) ){
			return true;
		}
		return false;
	}

	public void setFserch(boolean isFserch) {
		this.isFserch = isFserch;
	}

	public boolean isCserch() {
		if(!"".equals(startHsPart) || !"".equals(endHsPart) ){
			return true;
		}
		return false;
	}

	public void setCserch(boolean isCserch) {
		this.isCserch = isCserch;
	}
	
}
