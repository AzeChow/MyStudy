package com.bestway.common.fpt.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.ScmCoc;
/**
 * 商品单价维护
 * @author Administrator
 *
 */
public class FptBillPriceMaintenance extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
	.getSerialVersionUID();
	/**
	 * 客户供应商
	 */
	private ScmCoc scmCoc = null; 
	/**
	 * 帐册/手册序号
	 */
	private Integer seqNum = null;
	/**
	 * 海关商品编码
	 */
	private Complex complex = null;
	/**
	 * 商品名称
	 */
	private String ptName = null;
	/**
	 * 商品规格
	 */
	private String ptSpec = null; 
	/**
	 * 单价
	 */
	private Double unitPrice = null; 
	/**
	 * 币制
	 */
	private Curr curr = null;
	/**
	 * 项目类型
	 * BCUS = 0;	电子帐册
	 * BCS = 1;	电子化手册
	 * DZSC = 3;	电子手册
	 */
	private int projectType;
	/**
	 * 是否是客户
	 */
	private Boolean isCustomer = false;

	public Complex getComplex() {
		return complex;

	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public Curr getCurr() {
		return curr;
	}

	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	public String getPtName() {
		return ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public String getPtSpec() {
		return ptSpec;
	}

	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getProjectType() {
		return projectType;
	}

	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	public String getProjectTypeAndSeqNum() {
		String reStr = "";
		if (this.getProjectType() == 0) {
			reStr = "联网监管";
		} else if (this.getProjectType() == 1) {
			reStr = "纸质手册";
		} else if (this.getProjectType() == 3) {
			reStr = "电子手册";
		} else
			reStr = "";
		return reStr + "(" + getSeqNum().toString() + ")";
	}

	public Boolean getIsCustomer() {
		return isCustomer;
	}

	public void setIsCustomer(Boolean isCustomer) {
		this.isCustomer = isCustomer;
	}
}
